// Daily Challenge Mode - Dynamic daily puzzles with modifiers
import { GameMode } from './gameMode.js';
import { dailyChallenge } from '../challenges/dailyChallenge.js';

export class DailyChallengeMode extends GameMode {
    constructor(game) {
        super(game);
        this.name = 'Daily Challenge';
        this.challenge = null;
        this.startTime = 0;
        this.elapsedTime = 0;
        this.objectiveProgress = 0;
        this.modifiersActive = [];
        this.isCompleted = false;
        this.pieceCount = 0;
        
        // Modifier states
        this.modifierStates = {
            invisible: false,
            speedRamp: 1,
            mirrorControls: false,
            shakeIntensity: 0,
            fogActive: false,
            monochromeActive: false,
            giantPieces: false,
            clockwiseOnly: false,
            tinyGrid: false
        };
    }
    
    initialize() {
        // Don't call super.initialize() as it's an abstract method
        
        // Get today's challenge
        this.challenge = dailyChallenge.getTodaysChallenge();
        
        if (!this.challenge) {
            console.error('No daily challenge available');
            return;
        }
        
        // Check if already completed today
        if (!dailyChallenge.isAvailable()) {
            this.showAlreadyCompleted();
            return;
        }
        
        // Set up challenge parameters
        this.startTime = Date.now();
        this.setupChallenge();
        
        // Show challenge info
        this.showChallengeInfo();
    }
    
    setupChallenge() {
        // Apply difficulty
        const baseSpeed = 1000;
        const speedMultiplier = 1 - (this.challenge.difficulty * 0.15);
        this.game.dropInterval = baseSpeed * speedMultiplier;
        
        // Apply modifiers
        if (this.challenge.modifiers) {
            this.challenge.modifiers.forEach(modifier => {
                this.applyModifier(modifier);
            });
        }
        
        // Set up special rules
        if (this.challenge.specialRules) {
            this.challenge.specialRules.forEach(rule => {
                this.applySpecialRule(rule);
            });
        }
    }
    
    applyModifier(modifier) {
        this.modifiersActive.push(modifier);
        
        switch(modifier.effect) {
            case 'invisible_grid':
                this.modifierStates.invisible = true;
                // Store original render method to make pieces invisible after placement
                this.originalRenderGrid = this.game.renderGrid;
                break;
                
            case 'speed_ramp':
                // Speed increases every 45 seconds
                this.modifierStates.speedRamp = 1.5;
                this.baseDropInterval = this.game.dropInterval;
                break;
                
            case 'no_hold':
                // Disable hold feature
                this.game.canHold = false;
                // Hide hold display
                const holdContainer = document.querySelector('.hold-container');
                if (holdContainer) {
                    holdContainer.style.opacity = '0.3';
                    holdContainer.style.pointerEvents = 'none';
                }
                break;
                
            case 'mirror_controls':
                this.modifierStates.mirrorControls = true;
                break;
                
            case 'shake':
                this.modifierStates.shakeIntensity = 5;
                this.shakeStartTime = Date.now();
                break;
                
            case 'fog':
                this.modifierStates.fogActive = true;
                break;
                
            case 'giant_pieces':
                // Giant pieces modifier removed - skip
                break;
                
            case 'tiny_grid':
                // Reduce grid width to 6 columns
                this.modifierStates.tinyGrid = true;
                if (this.game.grid) {
                    // Store original grid dimensions
                    this.originalGridWidth = this.game.grid.width;
                    // Create a narrower grid
                    this.game.grid.width = 6;
                    // Re-initialize grid cells with new width
                    const newCells = [];
                    for (let y = 0; y < this.game.grid.height; y++) {
                        newCells[y] = [];
                        for (let x = 0; x < 6; x++) {
                            newCells[y][x] = null;
                        }
                    }
                    this.game.grid.cells = newCells;
                }
                break;
                
            case 'clockwise_only':
                // Restrict rotation direction - disable counter-clockwise
                this.modifierStates.clockwiseOnly = true;
                this.game.canRotateCounterClockwise = false;
                break;
                
            case 'monochrome':
                this.modifierStates.monochromeActive = true;
                break;
        }
    }
    
    applySpecialRule(rule) {
        switch(rule.id) {
            case 'no_line_clear':
                // Track this for validation
                this.noLineClearRule = true;
                break;
                
            case 'all_pieces_once':
                // Track piece usage
                this.pieceUsageRule = true;
                this.usedPieces = new Set();
                break;
                
            case 'increasing_speed':
                // Speed increases with each piece
                this.speedIncreaseRule = true;
                break;
        }
    }
    
    update(deltaTime) {
        if (this.isCompleted) return false;
        
        // Update elapsed time
        this.elapsedTime = Date.now() - this.startTime;
        
        // Check time limit
        if (this.challenge.timeLimit > 0) {
            const timeRemaining = (this.challenge.timeLimit * 1000) - this.elapsedTime;
            if (timeRemaining <= 0) {
                this.failChallenge('Time limit exceeded!');
                return false;
            }
        }
        
        // Check piece limit
        if (this.challenge.maxPieces > 0 && this.pieceCount >= this.challenge.maxPieces) {
            this.failChallenge('Piece limit reached!');
            return false;
        }
        
        // Apply speed ramp modifier
        if (this.modifierStates.speedRamp > 1) {
            const rampTime = Math.floor(this.elapsedTime / 45000); // Every 45 seconds
            const speedMultiplier = Math.pow(this.modifierStates.speedRamp, rampTime);
            const baseInterval = this.baseDropInterval || 1000;
            this.game.dropInterval = Math.max(50, baseInterval / speedMultiplier);
        }
        
        // Apply shake effect
        if (this.modifierStates.shakeIntensity > 0) {
            this.applyShakeEffect();
        }
        
        // Check objective progress
        this.updateObjectiveProgress();
        
        // Check if objective is completed
        if (this.checkObjectiveComplete()) {
            this.completeChallenge();
            return false;
        }
        
        return true;
    }
    
    updateObjectiveProgress() {
        const objective = this.challenge.objective;
        
        switch(objective.type) {
            case 'lines':
                this.objectiveProgress = this.game.linesCleared;
                break;
                
            case 'score':
                this.objectiveProgress = this.game.score;
                break;
                
            case 'combo':
                this.objectiveProgress = this.game.maxCombo || 0;
                break;
                
            case 'tetris':
                this.objectiveProgress = this.game.tetrisCount || 0;
                break;
                
            case 'tspin':
                this.objectiveProgress = this.game.tspinCount || 0;
                break;
                
            case 'perfect':
                // Check if grid has perfect clear
                this.objectiveProgress = this.checkPerfectClear() ? 1 : 0;
                break;
                
            case 'cascade':
                this.objectiveProgress = this.game.cascadeCount || 0;
                break;
                
            case 'speed':
                // For speed challenges, just track time
                this.objectiveProgress = Math.floor(this.elapsedTime / 1000);
                break;
                
            case 'survival':
                // Track survival time
                this.objectiveProgress = Math.floor(this.elapsedTime / 1000);
                break;
                
            case 'pattern':
                // Check if pattern is created
                this.objectiveProgress = this.checkPattern(objective.pattern) ? 1 : 0;
                break;
        }
    }
    
    checkObjectiveComplete() {
        const objective = this.challenge.objective;
        
        switch(objective.type) {
            case 'lines':
            case 'score':
            case 'combo':
            case 'tetris':
            case 'tspin':
            case 'cascade':
                return this.objectiveProgress >= objective.target;
                
            case 'perfect':
                return this.objectiveProgress >= 1;
                
            case 'speed':
                // Complete when target is reached within time limit
                return this.game.linesCleared >= (objective.target || 40);
                
            case 'survival':
                return this.objectiveProgress >= objective.duration;
                
            case 'pattern':
                return this.objectiveProgress >= 1;
                
            default:
                return false;
        }
    }
    
    checkPerfectClear() {
        // Check if grid is completely empty
        for (let y = 0; y < this.game.grid.height; y++) {
            for (let x = 0; x < this.game.grid.width; x++) {
                if (this.game.grid.cells[y][x] !== null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    checkPattern(patternType) {
        // Simplified pattern checking
        // Would need more complex logic for real pattern detection
        return false;
    }
    
    applyShakeEffect() {
        // Apply visual shake to canvas
        if (this.game.canvas) {
            const time = (Date.now() - this.shakeStartTime) / 1000;
            // Shake periodically (every 3 seconds)
            if (time % 3 < 0.5) {
                const shakeX = (Math.random() - 0.5) * this.modifierStates.shakeIntensity * 2;
                const shakeY = (Math.random() - 0.5) * this.modifierStates.shakeIntensity;
                this.game.canvas.style.transform = `translate(${shakeX}px, ${shakeY}px)`;
            } else {
                this.game.canvas.style.transform = '';
            }
        }
    }
    
    handleLineClears(lines, specialClear = {}) {
        // Check for no line clear rule violation
        if (this.noLineClearRule && lines > 0) {
            this.failChallenge('Line clear rule violated!');
            return 0;
        }
        
        // Calculate score (implement base scoring logic)
        let baseScore = 0;
        const level = this.game.level || 1;
        
        switch (lines) {
            case 1:
                baseScore = specialClear.type === 'tspin' ? 
                    (specialClear.mini ? 200 : 800) : 100;
                break;
            case 2:
                baseScore = specialClear.type === 'tspin' ? 1200 : 300;
                break;
            case 3:
                baseScore = specialClear.type === 'tspin' ? 1600 : 500;
                break;
            case 4:
                baseScore = 800; // Tetris
                this.game.tetrisCount = (this.game.tetrisCount || 0) + 1;
                break;
        }
        
        const score = baseScore * level;
        this.game.score += score;
        this.game.lines += lines;
        
        return score;
    }
    
    onPiecePlaced() {
        this.pieceCount++;
        
        // Check piece usage rule
        if (this.pieceUsageRule) {
            const pieceType = this.game.currentPiece?.type;
            if (pieceType) {
                if (this.usedPieces.has(pieceType)) {
                    this.failChallenge('Piece reuse rule violated!');
                    return;
                }
                this.usedPieces.add(pieceType);
            }
        }
        
        // Apply speed increase rule
        if (this.speedIncreaseRule) {
            this.game.dropInterval = Math.max(50, this.game.dropInterval * 0.98);
        }
    }
    
    async completeChallenge() {
        this.isCompleted = true;
        
        // Calculate stats
        const stats = {
            time: Math.floor(this.elapsedTime / 1000),
            score: this.game.score,
            lines: this.game.linesCleared,
            pieces: this.pieceCount,
            perfectClear: this.checkPerfectClear()
        };
        
        // Complete challenge and get rewards
        try {
            const result = await dailyChallenge.completeChallenge(stats);
            if (result && result.success) {
                this.showCompletionScreen(result);
            } else if (result && result.alreadyCompleted) {
                this.showAlreadyCompleted();
            }
        } catch (error) {
            console.error('Error completing challenge:', error);
            // Still show completion screen with local data
            this.showCompletionScreen({
                success: true,
                xpEarned: this.challenge.baseXP || 500,
                streak: dailyChallenge.streak || 1,
                nextChallenge: dailyChallenge.getTimeUntilNextChallenge()
            });
        }
    }
    
    failChallenge(reason) {
        this.isCompleted = true;
        this.game.gameOver();
        
        // Show failure message
        const overlay = document.getElementById('game-overlay');
        const title = document.getElementById('overlay-title');
        const message = document.getElementById('overlay-message');
        
        if (overlay && title && message) {
            title.textContent = 'Challenge Failed!';
            message.textContent = reason;
            overlay.style.display = 'flex';
        }
    }
    
    showCompletionScreen(result) {
        const overlay = document.getElementById('game-overlay');
        const title = document.getElementById('overlay-title');
        const message = document.getElementById('overlay-message');
        
        if (overlay && title && message) {
            title.textContent = 'Challenge Complete!';
            message.innerHTML = `
                <div style="text-align: center;">
                    <p>🎉 Congratulations! 🎉</p>
                    <p>XP Earned: <strong>${result.xpEarned}</strong></p>
                    <p>Current Streak: <strong>${result.streak} days</strong></p>
                    ${result.rank ? `<p>Daily Rank: <strong>#${result.rank}</strong></p>` : ''}
                    <p>Next challenge in: ${result.nextChallenge.hours}h ${result.nextChallenge.minutes}m</p>
                </div>
            `;
            overlay.style.display = 'flex';
        }
    }
    
    showAlreadyCompleted() {
        const overlay = document.getElementById('game-overlay');
        const title = document.getElementById('overlay-title');
        const message = document.getElementById('overlay-message');
        
        if (overlay && title && message) {
            title.textContent = 'Already Completed!';
            message.textContent = 'You have already completed today\'s challenge. Come back tomorrow!';
            overlay.style.display = 'flex';
        }
        
        // Return to menu after 3 seconds
        setTimeout(() => {
            this.game.modalManager.backToHome();
        }, 3000);
    }
    
    showChallengeInfo() {
        // Show challenge details in UI
        const modeUI = document.getElementById('mode-ui');
        if (modeUI) {
            modeUI.innerHTML = `
                <div class="daily-challenge-info">
                    <h3>${this.challenge.name}</h3>
                    <div class="challenge-objective">
                        <strong>Objective:</strong> ${this.getObjectiveText()}
                    </div>
                    <div class="challenge-modifiers">
                        <strong>Modifiers:</strong>
                        <ul>
                            ${this.modifiersActive.map(m => `<li>${m.name} - ${m.description}</li>`).join('')}
                        </ul>
                    </div>
                    ${this.challenge.timeLimit > 0 ? `
                        <div class="challenge-timer">
                            Time Limit: <span id="challenge-timer">${this.challenge.timeLimit}s</span>
                        </div>
                    ` : ''}
                    <div class="challenge-progress">
                        Progress: <span id="challenge-progress">0</span> / <span id="challenge-target">${this.getTargetText()}</span>
                    </div>
                </div>
            `;
            modeUI.style.display = 'block';
        }
    }
    
    getObjectiveText() {
        const obj = this.challenge.objective;
        switch(obj.type) {
            case 'lines': return `Clear ${obj.target} lines`;
            case 'score': return `Score ${obj.target} points`;
            case 'combo': return `Achieve a ${obj.target}x combo`;
            case 'tetris': return `Perform ${obj.target} Tetris${obj.target > 1 ? 'es' : ''}`;
            case 'tspin': return `Perform ${obj.target} T-Spin${obj.target > 1 ? 's' : ''}`;
            case 'perfect': return 'Achieve a perfect clear';
            case 'cascade': return `Create ${obj.target} cascade${obj.target > 1 ? 's' : ''}`;
            case 'speed': return `Clear 40 lines in ${obj.timeLimit} seconds`;
            case 'survival': return `Survive for ${obj.duration} seconds`;
            case 'pattern': return `Create a ${obj.pattern} pattern`;
            default: return 'Complete the objective';
        }
    }
    
    getTargetText() {
        const obj = this.challenge.objective;
        if (obj.type === 'perfect' || obj.type === 'pattern') return '1';
        if (obj.type === 'speed') return '40';
        if (obj.type === 'survival') return `${obj.duration}s`;
        return obj.target || '?';
    }
    
    getObjective() {
        return this.getObjectiveText();
    }
    
    getModeUI() {
        // Update progress display
        const progressEl = document.getElementById('challenge-progress');
        if (progressEl) {
            progressEl.textContent = Math.floor(this.objectiveProgress);
        }
        
        // Update timer if exists
        if (this.challenge.timeLimit > 0) {
            const timerEl = document.getElementById('challenge-timer');
            if (timerEl) {
                const remaining = Math.max(0, this.challenge.timeLimit - Math.floor(this.elapsedTime / 1000));
                timerEl.textContent = `${remaining}s`;
            }
        }
        
        return '';
    }
    
    // Called BEFORE rendering to apply filters
    render(ctx, canvas) {
        // Apply visual modifiers that affect rendering
        if (this.modifierStates.monochromeActive) {
            // Apply grayscale filter for monochrome mode
            ctx.filter = 'grayscale(100%)';
        }
        
        // Giant pieces are handled in renderPieceWithModifiers
        
        // Apply invisible grid effect
        if (this.modifierStates.invisible) {
            // Make placed pieces semi-transparent
            ctx.globalAlpha = 0.15;
        }
    }
    
    
    // Called AFTER rendering to apply overlays and reset filters
    postRender(ctx) {
        // Restore alpha if invisible was active
        if (this.modifierStates.invisible) {
            ctx.globalAlpha = 1.0;
        }
        
        // Apply overlays that go on top of the rendered game
        if (this.modifierStates.fogActive) {
            // Draw fog overlay on top rows (hide top 10 rows)
            const fogHeight = this.game.canvas.height * 0.5;
            
            // Create gradient for fog effect
            const gradient = ctx.createLinearGradient(0, 0, 0, fogHeight);
            gradient.addColorStop(0, 'rgba(0, 0, 0, 0.95)');
            gradient.addColorStop(0.8, 'rgba(0, 0, 0, 0.7)');
            gradient.addColorStop(1, 'rgba(0, 0, 0, 0)');
            
            ctx.fillStyle = gradient;
            ctx.fillRect(0, 0, this.game.canvas.width, fogHeight);
        }
        
        // Reset any filters that were applied
        ctx.filter = 'none';
    }
    
    handleInput(action) {
        // Apply mirror controls if active
        if (this.modifierStates.mirrorControls) {
            if (action === 'left') action = 'right';
            else if (action === 'right') action = 'left';
        }
        
        // Block counter-clockwise rotation if clockwise only is active
        if (this.modifierStates.clockwiseOnly && action === 'rotateCounterClockwise') {
            // Convert counter-clockwise to clockwise or block it
            return null; // Block the action
        }
        
        return action;
    }
    
    cleanup() {
        // Reset all game modifiers to default values
        this.game.canHold = true;
        this.game.canRotateCounterClockwise = true;
        this.game.dropInterval = 1000; // Reset to default speed
        
        
        // Restore hold display
        const holdContainer = document.querySelector('.hold-container');
        if (holdContainer) {
            holdContainer.style.opacity = '1';
            holdContainer.style.pointerEvents = 'auto';
        }
        
        // Restore original grid dimensions if they were modified
        if (this.modifierStates.tinyGrid && this.originalGridWidth) {
            this.game.grid.width = this.originalGridWidth;
            // Re-initialize grid cells with original width
            const restoredCells = [];
            for (let y = 0; y < this.game.grid.height; y++) {
                restoredCells[y] = [];
                for (let x = 0; x < this.originalGridWidth; x++) {
                    restoredCells[y][x] = null;
                }
            }
            this.game.grid.cells = restoredCells;
        }
        
        // Reset visual filters
        if (this.game.ctx) {
            this.game.ctx.filter = 'none';
            this.game.ctx.globalAlpha = 1.0;
            // Clear any saved states
            try {
                this.game.ctx.restore();
            } catch(e) {
                // Context might not have a saved state
            }
        }
        
        // Remove shake effect
        if (this.game.canvas) {
            this.game.canvas.style.transform = '';
        }
        
        // Hide Daily Challenge UI
        const modeUI = document.getElementById('mode-ui');
        if (modeUI) {
            modeUI.innerHTML = '';
            modeUI.style.display = 'none';
        }
    }
}