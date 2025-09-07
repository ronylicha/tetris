// Puzzle Mode - Solve 150 unique challenges
import { GameMode } from './gameMode.js';
import { PUZZLES, getPuzzleById, getUnlockedPuzzles, getNextPuzzle, PUZZLE_OBJECTIVES } from '../puzzles/puzzleData.js';
import { puzzleValidator } from '../puzzles/puzzleValidator.js';
import { storage } from '../storage-adapter.js';
import { HintSystem } from '../puzzles/hintSystem.js';

export class PuzzleMode extends GameMode {
    constructor(game) {
        super(game);
        this.name = 'Puzzle';
        this.description = 'Solve 150 unique Tetris challenges!';
        this.icon = '🧩';
        this.themeColor = '#ff8800';
        
        this.currentPuzzle = null;
        this.puzzleId = 1;
        this.completedPuzzles = [];
        this.puzzleStats = {};
        // Load async data after initialization
        this.loadCompletedPuzzles().then(data => {
            this.completedPuzzles = data;
        });
        this.availablePieces = [];
        this.usedPieces = 0;
        this.timeElapsed = 0;
        this.timerInterval = null;
        this.pendingCompletion = false;
        this.hintsUsed = 0;
        this.hintSystem = new HintSystem(game);
    }

    async initialize() {
        // Wait for completed puzzles to load
        this.completedPuzzles = await this.loadCompletedPuzzles();
        
        // Try to load saved progress first
        const progress = await this.loadCurrentProgress();
        if (progress && progress.currentPuzzleId) {
            this.puzzleId = progress.currentPuzzleId;
        } else {
            // Load first puzzle or continue from last
            this.puzzleId = this.getLastUncompletedPuzzle();
        }
        this.loadPuzzle(this.puzzleId);
        await this.saveCurrentProgress(); // Save current puzzle being played
    }

    loadPuzzle(puzzleId) {
        let puzzle = getPuzzleById(puzzleId);
        if (!puzzle) {
            console.error(`Puzzle ${puzzleId} not found`);
            return;
        }
        
        // Validate and auto-fix puzzle before loading
        puzzle = this.validateAndFixPuzzle(puzzle);
        this.currentPuzzle = puzzle;
        
        // Reset game state
        this.game.lines = 0;
        this.game.score = 0;
        this.game.level = 1;
        this.game.combo = 0;
        
        // Load puzzle grid
        if (this.game.grid && this.currentPuzzle.initialGrid) {
            this.game.grid.loadFromArray(this.currentPuzzle.initialGrid);
        }
        
        // Set available pieces
        if (this.currentPuzzle.pieces === 'random') {
            this.availablePieces = [];
        } else {
            this.availablePieces = [...this.currentPuzzle.pieces];
        }
        
        // Initialize puzzle stats
        this.puzzleStats = {
            puzzleId: puzzleId,
            objective: this.currentPuzzle.objective,
            targetLines: this.currentPuzzle.targetLines || 0,
            targetTSpins: this.currentPuzzle.targetTSpins || 0,
            targetTetris: this.currentPuzzle.targetTetris || 0,
            targetCombo: this.currentPuzzle.targetCombo || 0,
            minScore: this.currentPuzzle.minScore || 0,
            linesCleared: 0,
            tspinsPerformed: 0,
            tetrisPerformed: 0,
            maxCombo: 0,
            piecesUsed: 0,
            timeElapsed: 0
        };
        
        this.usedPieces = 0;
        this.timeElapsed = 0;
        this.pendingCompletion = false;
        this.isComplete = false;
        this.hintsUsed = 0;
        
        // Initialize hint system for this puzzle
        this.hintSystem.initialize(this.currentPuzzle);
        
        // Start timer if puzzle has time limit
        if (this.currentPuzzle.timeLimit > 0) {
            this.startTimer();
        }
        
        // Show puzzle info with validation status
        if (this.game.uiManager) {
            const validationMsg = this.currentPuzzle._wasFixed ? ' (Auto-adjusted)' : '';
            this.game.uiManager.showMessage(
                `Puzzle ${puzzleId}: ${this.currentPuzzle.name}${validationMsg}`,
                'info',
                3000
            );
        }
    }

    startTimer() {
        this.timeElapsed = 0;
        if (this.timerInterval) {
            clearInterval(this.timerInterval);
        }
        
        this.timerInterval = setInterval(() => {
            if (!this.isPaused && !this.isComplete) {
                this.timeElapsed++;
                this.puzzleStats.timeElapsed = this.timeElapsed;
                
                // Check time limit
                if (this.currentPuzzle.timeLimit > 0 && 
                    this.timeElapsed >= this.currentPuzzle.timeLimit) {
                    this.handlePuzzleFailed('Time limit exceeded');
                }
            }
        }, 1000);
    }

    stopTimer() {
        if (this.timerInterval) {
            clearInterval(this.timerInterval);
            this.timerInterval = null;
        }
    }

    update(deltaTime) {
        // If puzzle is already complete, don't process further
        if (this.isComplete) {
            return false;
        }
        
        if (!this.currentPuzzle) return false;
        
        // Handle pending completion from line clear
        if (this.pendingCompletion) {
            this.pendingCompletion = false;
            this.handlePuzzleComplete();
            return false;
        }
        
        // Check objective completion
        if (this.checkObjectiveComplete()) {
            this.handlePuzzleComplete();
            return false;
        }
        
        // Don't check failure conditions if objective is met
        if (!this.checkObjectiveComplete() && this.checkFailureConditions()) {
            this.handlePuzzleFailed('Objective failed');
            return false;
        }
        
        return true;
    }

    handleLineClears(linesCleared, specialClear) {
        // Don't process if puzzle is already complete
        if (this.isComplete) return;
        
        if (!linesCleared || linesCleared === 0) {
            // Reset combo
            if (this.game.combo > this.puzzleStats.maxCombo) {
                this.puzzleStats.maxCombo = this.game.combo;
            }
            this.game.combo = 0;
            return;
        }
        
        // Update stats BEFORE checking completion
        this.puzzleStats.linesCleared += linesCleared;
        this.game.lines += linesCleared;
        this.game.combo++;
        
        if (this.game.combo > this.puzzleStats.maxCombo) {
            this.puzzleStats.maxCombo = this.game.combo;
        }
        
        // Check for special clears
        if (specialClear) {
            if (specialClear.type === 'tspin') {
                this.puzzleStats.tspinsPerformed++;
            }
        }
        
        if (linesCleared === 4) {
            this.puzzleStats.tetrisPerformed++;
        }
        
        // Calculate score
        let score = linesCleared * 100;
        if (specialClear && specialClear.type === 'tspin') {
            score += 400;
        }
        if (linesCleared === 4) {
            score += 400;
        }
        this.game.score += score;
        
        // Check if objective is completed IMMEDIATELY
        if (this.checkObjectiveComplete()) {
            // Mark as complete immediately to prevent game over
            this.isComplete = true;
            this.pendingCompletion = true;
            
            // Call handlePuzzleComplete directly with a small delay
            setTimeout(() => {
                this.handlePuzzleComplete();
            }, 100);
        }
    }

    handlePiecePlaced() {
        // Don't process if puzzle is already complete
        if (this.isComplete) return;
        
        this.usedPieces++;
        this.puzzleStats.piecesUsed = this.usedPieces;
        
        // Check piece limit only if objective is not complete
        if (this.currentPuzzle.maxPieces > 0 && 
            this.usedPieces >= this.currentPuzzle.maxPieces) {
            // Give time for line clears to process
            setTimeout(() => {
                if (!this.isComplete && !this.checkObjectiveComplete()) {
                    this.handlePuzzleFailed('Piece limit exceeded');
                }
            }, 100);
        }
    }

    checkObjectiveComplete() {
        const stats = this.puzzleStats;
        const puzzle = this.currentPuzzle;
        
        switch (puzzle.objective) {
            case 'clear':
                return stats.linesCleared >= (puzzle.targetLines || 1);
                
            case 'tspin':
                return stats.tspinsPerformed >= (puzzle.targetTSpins || 1);
                
            case 'tetris':
                return stats.tetrisPerformed >= (puzzle.targetTetris || 1);
                
            case 'perfectclear':
                return this.game.grid && this.game.grid.isEmpty();
                
            case 'combo':
                return stats.maxCombo >= (puzzle.targetCombo || 3);
                
            case 'mixed':
                return (
                    stats.linesCleared >= (puzzle.targetLines || 0) &&
                    stats.tspinsPerformed >= (puzzle.targetTSpins || 0) &&
                    stats.maxCombo >= (puzzle.targetCombo || 0)
                );
                
            case 'survival':
                return (
                    stats.linesCleared >= (puzzle.targetLines || 0) &&
                    this.game.score >= (puzzle.minScore || 0)
                );
                
            default:
                return false;
        }
    }

    checkFailureConditions() {
        // Check if pieces exhausted without completing objective
        if (this.currentPuzzle.maxPieces > 0 && 
            this.usedPieces >= this.currentPuzzle.maxPieces) {
            return !this.checkObjectiveComplete();
        }
        
        // Check if specific pieces are exhausted
        if (this.availablePieces.length > 0 && 
            this.usedPieces >= this.availablePieces.length) {
            return !this.checkObjectiveComplete();
        }
        
        return false;
    }

    handlePuzzleComplete() {
        // Prevent multiple calls
        if (this.isComplete && this.game.state === 'victory') {
            return;
        }
        
        this.stopTimer();
        this.isComplete = true;
        
        // Calculate stars (1-3 based on performance)
        const stars = this.calculateStars();
        
        // Save completion
        this.savePuzzleCompletion(this.puzzleId, stars);
        
        // Show completion message
        if (this.game.uiManager) {
            this.game.uiManager.showPuzzleComplete(
                this.currentPuzzle,
                stars,
                this.puzzleStats
            );
            
            // Show success message
            this.game.uiManager.showMessage(
                `✨ Puzzle Complete! ${'\u2b50'.repeat(stars)}`,
                'success',
                3000
            );
        }
        
        // Play victory sound
        if (this.game.audioManager) {
            this.game.audioManager.playSFX('victory');
        }
        
        // Store results but don't end game immediately
        if (this.game) {
            // Store puzzle results for the game over screen
            this.game.puzzleResults = {
                puzzleId: this.puzzleId,
                puzzleName: this.currentPuzzle.name,
                stars: stars,
                stats: this.puzzleStats,
                nextPuzzle: getNextPuzzle(this.puzzleId, this.completedPuzzles),
                isVictory: true
            };
            
            // Set game state to prevent further input
            this.game.state = 'victory';
            
            // The UI will handle the next action (next puzzle or retry)
            // Don't call gameOver here
        }
        
        return {
            puzzleId: this.puzzleId,
            puzzleName: this.currentPuzzle.name,
            stars: stars,
            stats: this.puzzleStats,
            nextPuzzle: getNextPuzzle(this.puzzleId, this.completedPuzzles)
        };
    }

    handlePuzzleFailed(reason) {
        this.stopTimer();
        this.isComplete = true;
        
        // Show failure message
        if (this.game.uiManager) {
            this.game.uiManager.showPuzzleFailed(
                this.currentPuzzle,
                reason,
                this.puzzleStats
            );
        }
        
        // End the game with failure
        if (this.game) {
            // Store puzzle results for the game over screen
            this.game.puzzleResults = {
                puzzleId: this.puzzleId,
                puzzleName: this.currentPuzzle.name,
                failed: true,
                reason: reason,
                stats: this.puzzleStats
            };
            this.game.gameOver();
        }
        
        return {
            puzzleId: this.puzzleId,
            puzzleName: this.currentPuzzle.name,
            failed: true,
            reason: reason,
            stats: this.puzzleStats
        };
    }

    calculateStars() {
        // Start with base star for completion
        let stars = 1;
        let bonusEarned = [];
        
        // Calculate efficiency score (pieces used vs max allowed)
        if (this.currentPuzzle.maxPieces > 0) {
            const efficiency = this.usedPieces / this.currentPuzzle.maxPieces;
            
            if (efficiency <= 0.6) {
                stars = 3; // Perfect efficiency - 60% or less pieces used
                bonusEarned.push('Perfect Efficiency');
            } else if (efficiency <= 0.8) {
                stars = 2; // Good efficiency - 80% or less pieces used
                bonusEarned.push('Good Efficiency');
            }
        }
        
        // Time bonus (if applicable)
        if (this.currentPuzzle.timeLimit > 0 && stars < 3) {
            const timeRatio = this.timeElapsed / (this.currentPuzzle.timeLimit * 1000);
            if (timeRatio <= 0.5) {
                stars = Math.min(3, stars + 1);
                bonusEarned.push('Speed Bonus');
            }
        }
        
        // Perfect clear automatic 3 stars
        if (this.currentPuzzle.objective === 'perfectclear' && 
            this.game.grid && this.game.grid.isEmpty()) {
            stars = 3;
            bonusEarned.push('Perfect Clear');
        }
        
        // Get hints used from hint system
        this.hintsUsed = this.hintSystem.getHintsUsed();
        
        // Deduct stars for hints used
        if (this.hintsUsed > 0) {
            stars = Math.max(1, stars - this.hintsUsed);
            bonusEarned.push(`Hints Used: -${this.hintsUsed}⭐`);
        }
        
        // Store bonus info for display
        this.starsInfo = {
            stars: stars,
            maxStars: 3,
            bonuses: bonusEarned,
            hintsUsed: this.hintsUsed || 0,
            efficiency: this.currentPuzzle.maxPieces > 0 
                ? Math.round((this.usedPieces / this.currentPuzzle.maxPieces) * 100) 
                : 100,
            timePercent: this.currentPuzzle.timeLimit > 0
                ? Math.round((this.timeElapsed / (this.currentPuzzle.timeLimit * 1000)) * 100)
                : 0
        };
        
        return Math.min(3, Math.max(1, stars));
    }

    getNextPiece() {
        if (this.currentPuzzle.pieces === 'random') {
            // Random pieces
            return null; // Let game generate random piece
        } else if (this.availablePieces.length > 0) {
            // Specific piece sequence
            if (this.usedPieces < this.availablePieces.length) {
                return this.availablePieces[this.usedPieces];
            }
            // After using all specified pieces, generate random ones
            return null; // Let game generate random piece
        }
        return null;
    }

    async loadCompletedPuzzles() {
        const saved = await storage.load('puzzle_completed');
        if (saved) {
            return saved;
        }
        // Try legacy localStorage fallback
        const legacySaved = localStorage.getItem('tetris_puzzle_completed');
        if (legacySaved) {
            try {
                const data = JSON.parse(legacySaved);
                // Migrate to new storage
                await storage.save('puzzle_completed', data);
                return data;
            } catch (e) {
                console.error('Failed to load completed puzzles:', e);
            }
        }
        return [];
    }

    savePuzzleCompletion(puzzleId, stars) {
        const completion = {
            puzzleId: puzzleId,
            stars: stars,
            timestamp: Date.now()
        };
        
        // Update completed list
        const existing = this.completedPuzzles.findIndex(p => p.puzzleId === puzzleId);
        if (existing >= 0) {
            // Update if better stars
            if (stars > this.completedPuzzles[existing].stars) {
                this.completedPuzzles[existing] = completion;
            }
        } else {
            this.completedPuzzles.push(completion);
        }
        
        storage.save('puzzle_completed', this.completedPuzzles);
        
        // Also save current progress
        this.saveCurrentProgress();
    }
    
    async saveCurrentProgress() {
        const progress = {
            currentPuzzleId: this.puzzleId,
            highestUnlocked: Math.max(...this.completedPuzzles.map(p => p.puzzleId), this.puzzleId),
            lastPlayed: Date.now()
        };
        await storage.save('puzzle_progress', progress);
    }
    
    async loadCurrentProgress() {
        const saved = await storage.load('puzzle_progress');
        if (saved) {
            return saved;
        }
        // Try legacy localStorage fallback
        const legacySaved = localStorage.getItem('tetris_puzzle_progress');
        if (legacySaved) {
            try {
                const data = JSON.parse(legacySaved);
                // Migrate to new storage
                await storage.save('puzzle_progress', data);
                return data;
            } catch (e) {
                console.error('Failed to load puzzle progress:', e);
            }
        }
        return null;
    }

    getLastUncompletedPuzzle() {
        const unlocked = getUnlockedPuzzles(this.completedPuzzles.map(p => p.puzzleId));
        for (let puzzle of unlocked) {
            if (!this.completedPuzzles.find(p => p.puzzleId === puzzle.id)) {
                return puzzle.id;
            }
        }
        return 1; // Default to first puzzle
    }

    selectPuzzle(puzzleId) {
        const unlocked = getUnlockedPuzzles(this.completedPuzzles.map(p => p.puzzleId));
        if (unlocked.find(p => p.id === puzzleId)) {
            this.loadPuzzle(puzzleId);
            return true;
        }
        return false;
    }

    getObjective() {
        if (!this.currentPuzzle) return 'Select a puzzle';
        
        // Return the current objective with progress
        return this.getObjectiveProgress();
    }

    getModeUI() {
        return {
            showScore: true,
            showLines: true,
            showLevel: false,
            showHold: true,
            showNext: true,
            showTimer: this.currentPuzzle && this.currentPuzzle.timeLimit > 0,
            showObjective: true,
            customDisplay: this.currentPuzzle ? {
                puzzle: `#${this.currentPuzzle.id}: ${this.currentPuzzle.name}`,
                pieces: this.currentPuzzle.maxPieces > 0 && this.currentPuzzle.maxPieces < 999 ? 
                    `Pieces: ${this.usedPieces}/${this.currentPuzzle.maxPieces}` : 
                    `Pieces: ${this.usedPieces}`,
                objective: this.getObjectiveProgress(),
                hint: this.currentPuzzle.hint || null
            } : {}
        };
    }

    getObjectiveProgress() {
        if (!this.currentPuzzle) return '';
        
        switch (this.currentPuzzle.objective) {
            case 'lines':
            case 'clearLines':
            case 'clear':
                const targetLines = this.currentPuzzle.targetLines || 1;
                return `🎯 Clear: ${this.puzzleStats.linesCleared}/${targetLines} line${targetLines > 1 ? 's' : ''}`;
            
            case 'clearAll':
            case 'clearBoard':
                const totalBlocks = this.countRemainingBlocks();
                return `🧹 Clear all blocks (${totalBlocks} left)`;
            
            case 'score':
            case 'minScore':
                const targetScore = this.currentPuzzle.minScore || 1000;
                return `💯 Score: ${this.game.score}/${targetScore}`;
            
            case 'tspin':
                const targetTSpins = this.currentPuzzle.targetTSpins || 1;
                return `🌀 T-Spins: ${this.puzzleStats.tspinsPerformed}/${targetTSpins}`;
            
            case 'tetris':
                const targetTetris = this.currentPuzzle.targetTetris || 1;
                return `⚡ Tetris: ${this.puzzleStats.tetrisPerformed}/${targetTetris}`;
            
            case 'combo':
                const targetCombo = this.currentPuzzle.targetCombo || 5;
                return `🔥 Combo: ${this.puzzleStats.maxCombo}/${targetCombo}`;
            
            case 'perfect':
                return `✨ Perfect Clear required`;
            
            case 'survive':
                const timeLimit = this.currentPuzzle.timeLimit || 60;
                const elapsed = Math.floor(this.timeElapsed / 1000);
                return `⏱️ Time: ${elapsed}/${timeLimit}s`;
            
            default:
                return this.currentPuzzle.description || 'Complete objective';
        }
    }
    
    countRemainingBlocks() {
        if (!this.game.grid || !this.game.grid.cells) return 0;
        let count = 0;
        for (let row = 0; row < 20; row++) {
            for (let col = 0; col < 10; col++) {
                if (this.game.grid.cells[row][col] !== 0) {
                    count++;
                }
            }
        }
        return count;
    }

    pause() {
        super.pause();
    }

    resume() {
        super.resume();
    }

    cleanup() {
        this.stopTimer();
        // Cleanup hint system
        if (this.hintSystem) {
            this.hintSystem.cleanup();
        }
    }
    
    validateAndFixPuzzle(puzzle) {
        // Convert puzzle to validator format
        const puzzleConfig = {
            name: puzzle.name,
            grid: puzzle.initialGrid || Array(20).fill(null).map(() => Array(10).fill(0)),
            pieces: puzzle.pieces === 'random' ? ['I', 'O', 'T', 'S', 'Z', 'J', 'L'] : puzzle.pieces,
            maxPieces: puzzle.maxPieces || 999,
            objective: this.convertObjective(puzzle),
            hint: puzzle.hint
        };
        
        // Validate the puzzle
        const validation = puzzleValidator.validatePuzzle(puzzleConfig);
        
        if (!validation.valid) {
            console.warn(`Puzzle ${puzzle.id} "${puzzle.name}" has issues:`, validation.issues);
            
            // Auto-fix the puzzle
            const fixed = this.autoFixPuzzle(puzzle, validation);
            fixed._wasFixed = true;
            fixed._validationIssues = validation.issues;
            
            console.log(`Auto-fixed puzzle ${puzzle.id}:`, validation.suggestions);
            return fixed;
        }
        
        return puzzle;
    }
    
    convertObjective(puzzle) {
        const objective = { type: puzzle.objective };
        
        switch (puzzle.objective) {
            case 'lines':
            case 'clearLines':
                objective.type = 'lines';
                objective.count = puzzle.targetLines || 1;
                break;
            case 'score':
            case 'minScore':
                objective.type = 'score';
                objective.target = puzzle.minScore || 1000;
                break;
            case 'clearAll':
            case 'clearBoard':
                objective.type = 'clear';
                break;
            case 'tspin':
                objective.type = 'lines';
                objective.count = puzzle.targetTSpins || 1;
                break;
            case 'tetris':
                objective.type = 'lines';
                objective.count = (puzzle.targetTetris || 1) * 4;
                break;
            case 'combo':
                objective.type = 'lines';
                objective.count = puzzle.targetCombo || 3;
                break;
            default:
                objective.type = 'lines';
                objective.count = 1;
        }
        
        return objective;
    }
    
    autoFixPuzzle(puzzle, validation) {
        const fixed = { ...puzzle };
        
        // Fix piece count if needed
        if (validation.minPiecesRequired > 0) {
            if (!fixed.pieces || fixed.pieces === 'random') {
                fixed.pieces = ['I', 'O', 'T', 'S', 'Z', 'J', 'L'];
            }
            
            // Ensure we have enough pieces
            while (fixed.pieces.length < validation.minPiecesRequired) {
                const allPieces = ['I', 'O', 'T', 'S', 'Z', 'J', 'L'];
                const randomPiece = allPieces[Math.floor(Math.random() * allPieces.length)];
                fixed.pieces.push(randomPiece);
            }
            
            // Adjust maxPieces if too restrictive
            if (fixed.maxPieces < validation.minPiecesRequired + 2) {
                fixed.maxPieces = validation.minPiecesRequired + 5;
            }
        }
        
        // Fix objectives that are impossible
        if (validation.issues.some(issue => issue.includes('Objective requires'))) {
            switch (puzzle.objective) {
                case 'lines':
                case 'clearLines':
                    // Reduce line requirement
                    fixed.targetLines = Math.max(1, Math.floor((fixed.targetLines || 1) * 0.75));
                    break;
                case 'score':
                case 'minScore':
                    // Reduce score requirement
                    fixed.minScore = Math.max(100, Math.floor((fixed.minScore || 1000) * 0.5));
                    break;
                case 'tetris':
                    // Reduce tetris requirement or add I pieces
                    if (!fixed.pieces.includes('I')) {
                        fixed.pieces.push('I', 'I', 'I', 'I');
                    }
                    fixed.targetTetris = Math.max(1, Math.floor((fixed.targetTetris || 1) * 0.75));
                    break;
                case 'tspin':
                    // Add T pieces for T-spins
                    if (!fixed.pieces.includes('T')) {
                        fixed.pieces.push('T', 'T', 'T');
                    }
                    fixed.targetTSpins = Math.max(1, Math.floor((fixed.targetTSpins || 1) * 0.75));
                    break;
            }
        }
        
        // Fix grid issues (holes, impossible clears)
        if (validation.issues.some(issue => issue.includes('grid'))) {
            // Simplify the grid if it's too complex
            if (fixed.initialGrid) {
                fixed.initialGrid = this.simplifyGrid(fixed.initialGrid);
            }
        }
        
        return fixed;
    }
    
    simplifyGrid(grid) {
        const simplified = grid.map(row => [...row]);
        
        // Remove isolated blocks that create impossible situations
        for (let row = 0; row < 20; row++) {
            for (let col = 0; col < 10; col++) {
                if (simplified[row][col] !== 0) {
                    // Check if this block is isolated (no neighbors)
                    let hasNeighbor = false;
                    
                    // Check adjacent cells
                    const neighbors = [
                        [row - 1, col], [row + 1, col],
                        [row, col - 1], [row, col + 1]
                    ];
                    
                    for (const [r, c] of neighbors) {
                        if (r >= 0 && r < 20 && c >= 0 && c < 10 && simplified[r][c] !== 0) {
                            hasNeighbor = true;
                            break;
                        }
                    }
                    
                    // Remove isolated blocks in upper rows (they're problematic)
                    if (!hasNeighbor && row < 10) {
                        simplified[row][col] = 0;
                    }
                }
            }
        }
        
        return simplified;
    }

    getLeaderboardCategory() {
        return 'puzzle';
    }

    supportsSaving() {
        return false; // Puzzles are quick challenges
    }

    getIcon() {
        return '🧩';
    }

    getThemeColor() {
        return '#ff8800';
    }
}