// Modern Tetris - Main Game Engine

import { Grid, GRID_WIDTH, GRID_HEIGHT } from './grid.js';
import { Piece, PieceBag, TSpinDetector, PIECE_TYPES } from './pieces.js';
import { InputManager } from './input.js';
import { UIManager } from './ui.js';
import { AudioManager } from './audio.js';
import { ModalManager } from './modals.js';

export class TetrisGame {
    constructor() {
        this.canvas = document.getElementById('game-canvas');
        this.ctx = this.canvas.getContext('2d');
        
        // Game state
        this.state = 'menu'; // menu, playing, paused, gameover
        this.grid = new Grid();
        this.pieceBag = new PieceBag();
        
        // Current game pieces
        this.currentPiece = null;
        this.ghostPiece = null;
        this.heldPiece = null;
        this.heldPieceUsed = false;
        this.nextPieces = [];
        
        // Timing
        this.lastTime = 0;
        this.dropTimer = 0;
        this.dropInterval = 1000; // 1 second initially
        
        // Scoring
        this.score = 0;
        this.lines = 0;
        this.level = 1;
        this.combo = 0;
        
        // Special achievements tracking
        this.specialAchievements = {
            tspins: 0,
            tspinMinis: 0,
            tetris: 0,
            combos: 0,
            perfectClears: 0
        };
        
        // Game mechanics
        this.lockDelay = 0;
        this.maxLockDelay = 500;
        this.lastPiecePosition = null;
        
        // Systems
        this.audioManager = new AudioManager();
        this.inputManager = new InputManager(this);
        this.uiManager = new UIManager(this);
        this.modalManager = new ModalManager(this.audioManager);
        
        // Store piece module reference for UI
        this.pieceModule = { Piece, PIECE_TYPES };
        
        // Track game start time for duration calculation
        this.gameStartTime = Date.now();
        
        this.initializeGame();
        this.startGameLoop();
    }

    initializeGame() {
        // Setup canvas
        this.setupCanvas();
        
        // Generate initial next pieces
        for (let i = 0; i < 3; i++) {
            this.nextPieces.push(this.pieceBag.getNextPiece().type);
        }
        
        // Update UI
        this.updateUI();
    }

    setupCanvas() {
        const dpr = window.devicePixelRatio || 1;
        const rect = this.canvas.getBoundingClientRect();
        
        this.canvas.width = rect.width * dpr;
        this.canvas.height = rect.height * dpr;
        
        this.ctx.scale(dpr, dpr);
        this.canvas.style.width = rect.width + 'px';
        this.canvas.style.height = rect.height + 'px';
        
        // Setup rendering context
        this.ctx.imageSmoothingEnabled = false;
        this.cellSize = rect.width / GRID_WIDTH;
    }

    // Game loop
    startGameLoop() {
        let lastRenderTime = 0;
        const targetFPS = 60;
        const frameTime = 1000 / targetFPS;
        
        const gameLoop = (currentTime) => {
            const deltaTime = currentTime - this.lastTime;
            this.lastTime = currentTime;
            
            // Always update game logic
            this.update(deltaTime);
            
            // Limit rendering to 60 FPS for better performance
            if (currentTime - lastRenderTime >= frameTime) {
                this.render();
                lastRenderTime = currentTime;
            }
            
            requestAnimationFrame(gameLoop);
        };
        
        requestAnimationFrame(gameLoop);
    }

    update(deltaTime) {
        if (this.state !== 'playing') {
            this.inputManager.update(deltaTime);
            return;
        }
        
        this.inputManager.update(deltaTime);
        this.updateGameLogic(deltaTime);
    }

    updateGameLogic(deltaTime) {
        if (!this.currentPiece) {
            this.spawnNextPiece();
            return;
        }
        
        // Update drop timer
        this.dropTimer += deltaTime;
        
        // Natural drop
        if (this.dropTimer >= this.dropInterval) {
            this.dropTimer = 0;
            this.dropPiece();
        }
        
        // Update ghost piece
        this.updateGhostPiece();
        
        // Handle lock delay
        if (this.currentPiece.shouldLock(this.grid)) {
            if (this.currentPiece.updateLockDelay(deltaTime)) {
                this.lockPiece();
            }
        } else {
            this.currentPiece.resetLockDelay();
        }
        
        // Update UI
        this.updateUI();
    }

    // Piece spawning
    spawnNextPiece() {
        if (this.nextPieces.length === 0) return;
        
        // Get next piece type and create piece
        const pieceType = this.nextPieces.shift();
        this.currentPiece = new Piece(pieceType, 3, 0);
        
        // Add new piece to next queue
        this.nextPieces.push(this.pieceBag.getNextPiece().type);
        
        // Reset hold piece usage
        this.heldPieceUsed = false;
        
        // Check game over
        if (this.grid.checkCollision(this.currentPiece)) {
            this.gameOver();
            return;
        }
        
        // Update UI
        this.uiManager.updateNextPieces(this.nextPieces);
    }

    // Piece movement
    movePiece(dx, dy) {
        if (!this.currentPiece || this.state !== 'playing') return false;
        
        const newPiece = this.currentPiece.copy();
        newPiece.move(dx, dy);
        
        if (!this.grid.checkCollision(newPiece)) {
            this.lastPiecePosition = this.currentPiece.copy();
            this.currentPiece = newPiece;
            
            // Play move sound
            this.audioManager.playSFX('move');
            
            // Reset lock delay on successful movement
            if (this.currentPiece.shouldLock(this.grid)) {
                this.currentPiece.resetLockDelay();
            }
            
            return true;
        }
        
        return false;
    }

    // Piece rotation
    rotatePiece(direction = 1) {
        if (!this.currentPiece || this.state !== 'playing') return false;
        
        const rotatedPiece = this.grid.testPieceRotation(this.currentPiece, direction);
        
        if (rotatedPiece) {
            this.lastPiecePosition = this.currentPiece.copy();
            this.currentPiece = rotatedPiece;
            
            // Play rotate sound
            this.audioManager.playSFX('rotate');
            
            return true;
        }
        
        return false;
    }

    // Soft drop
    softDrop() {
        if (this.movePiece(0, 1)) {
            this.score += 1;
            this.dropTimer = 0;
            // Move sound is already played in movePiece
        }
    }

    // Hard drop
    hardDrop() {
        if (!this.currentPiece || this.state !== 'playing') return;
        
        const originalY = this.currentPiece.y;
        let dropDistance = 0;
        
        // Temporarily disable move sound for hard drop
        const originalPlaySFX = this.audioManager.playSFX;
        this.audioManager.playSFX = () => {};
        
        while (this.movePiece(0, 1)) {
            dropDistance++;
        }
        
        // Restore sound and play drop sound
        this.audioManager.playSFX = originalPlaySFX;
        this.audioManager.playSFX('drop');
        
        this.score += dropDistance * 2;
        this.lockPiece();
    }

    // Natural drop
    dropPiece() {
        if (!this.movePiece(0, 1)) {
            // Can't move down, start lock delay
            this.currentPiece.lockDelay = this.currentPiece.lockDelay || 0;
        }
    }

    // Hold piece
    holdPiece() {
        if (!this.currentPiece || this.heldPieceUsed || this.state !== 'playing') return;
        
        if (this.heldPiece) {
            // Swap current and held pieces
            const temp = this.heldPiece;
            this.heldPiece = this.currentPiece.type;
            this.currentPiece = new Piece(temp, 3, 0);
        } else {
            // Hold current piece and spawn next
            this.heldPiece = this.currentPiece.type;
            this.spawnNextPiece();
        }
        
        this.heldPieceUsed = true;
        this.audioManager.playSFX('hold');
        this.uiManager.updateHoldPiece(this.heldPiece ? new Piece(this.heldPiece) : null);
    }

    // Lock piece in place
    lockPiece() {
        if (!this.currentPiece) return;
        
        // Check for T-Spin
        const tspinResult = TSpinDetector.isTSpin(this.currentPiece, this.grid, this.lastPiecePosition);
        
        // Place piece on grid
        this.grid.placePiece(this.currentPiece);
        
        // Play lock sound
        this.audioManager.playSFX('lock');
        
        // Process line clearing immediately and completely
        this.processLineClear(tspinResult);
        
        // Check game over after everything is processed
        if (this.grid.isGameOver()) {
            this.gameOver();
        } else {
            this.currentPiece = null;
        }
    }

    // Process line clearing with proper sequencing
    processLineClear(tspinResult) {
        console.log('=== LINE CLEARING PROCESS START ===');
        
        // Debug: Print grid state before line detection
        this.grid.debugPrintGrid();
        
        // Get lines to clear BEFORE clearing them for animation
        const linesToClear = this.grid.getCompletedLines();
        
        console.log(`processLineClear: Found ${linesToClear.length} lines to clear:`, linesToClear);
        
        if (linesToClear.length === 0) {
            console.log('=== LINE CLEARING PROCESS END (no lines) ===');
            return; // No lines to clear
        }
        
        // Clear the lines from the grid immediately using the detected lines
        const actualCleared = this.grid.clearLines(linesToClear);
        
        console.log(`processLineClear: Actually cleared ${actualCleared} lines`);
        
        // Debug: Print grid state after clearing
        this.grid.debugPrintGrid();
        
        if (actualCleared !== linesToClear.length) {
            console.error(`Line clearing mismatch! Expected ${linesToClear.length}, but cleared ${actualCleared}`);
        }
        
        console.log('=== LINE CLEARING PROCESS END ===');
        
        // Track special achievements
        this.updateSpecialAchievements(tspinResult, actualCleared);
        
        // Show effects and play sounds using original line positions
        this.playLineClearEffects(actualCleared, linesToClear, tspinResult);
        
        // Calculate and update score
        this.calculateScore(actualCleared, tspinResult);
        
        // Check for perfect clear bonus
        if (this.grid.isPerfectClear() && actualCleared > 0) {
            this.specialAchievements.perfectClears++;
            this.uiManager.showPerfectClearEffect();
            this.score += 10000 * this.level; // Bonus for perfect clear
        }
        
        // Update level after processing everything
        this.updateLevel();
    }

    // Update special achievements tracking
    updateSpecialAchievements(tspinResult, clearedLines) {
        // Track T-Spins
        if (tspinResult.type === 'tspin') {
            if (tspinResult.mini) {
                this.specialAchievements.tspinMinis++;
            } else {
                this.specialAchievements.tspins++;
            }
        }
        
        if (clearedLines === 4) {
            this.specialAchievements.tetris++;
        }
    }

    // Play line clear effects and sounds
    playLineClearEffects(clearedLines, originalLinePositions, tspinResult) {
        if (clearedLines > 0) {
            // Show visual effects using original line positions
            this.uiManager.showLineClearEffect(originalLinePositions, clearedLines === 4);
            
            // Play appropriate sound
            if (clearedLines === 4) {
                this.audioManager.playSFX('tetris');
            } else {
                this.audioManager.playSFX('lineClear');
            }
        }
        
        if (tspinResult.type === 'tspin') {
            this.audioManager.playSFX('tspin');
            this.uiManager.showTSpinEffect(this.currentPiece, tspinResult.mini);
        }
    }

    // Calculate score based on lines cleared and special moves
    calculateScore(linesCleared, tspinResult) {
        if (linesCleared === 0) {
            this.combo = 0;
            return;
        }
        
        let baseScore = 0;
        let multiplier = this.level;
        
        // Base scoring
        switch (linesCleared) {
            case 1:
                baseScore = tspinResult.type === 'tspin' ? 
                    (tspinResult.mini ? 200 : 800) : 100;
                break;
            case 2:
                baseScore = tspinResult.type === 'tspin' ? 1200 : 300;
                break;
            case 3:
                baseScore = tspinResult.type === 'tspin' ? 1600 : 500;
                break;
            case 4:
                baseScore = 800; // Tetris
                break;
        }
        
        // Combo bonus
        this.combo++;
        if (this.combo > 1) {
            baseScore += 50 * this.combo * multiplier;
            this.uiManager.showComboEffect(this.combo);
            this.specialAchievements.combos++;
        }
        
        // Perfect clear bonus
        if (this.grid.isPerfectClear()) {
            baseScore *= 10;
        }
        
        this.score += baseScore * multiplier;
        this.lines += linesCleared;
    }

    // Update game level
    updateLevel() {
        const newLevel = Math.floor(this.lines / 10) + 1;
        if (newLevel > this.level) {
            this.level = newLevel;
            this.audioManager.playSFX('levelUp');
            this.updateDropSpeed();
        }
    }

    // Update drop speed based on level
    updateDropSpeed() {
        // Standard Tetris drop speed formula
        if (this.level <= 8) {
            this.dropInterval = (48 - 5 * this.level) * 16.67; // ~60 FPS
        } else if (this.level <= 10) {
            this.dropInterval = (8 - this.level) * 16.67;
        } else if (this.level <= 12) {
            this.dropInterval = 16.67; // 1 frame
        } else if (this.level <= 15) {
            this.dropInterval = 13.33; // ~3/4 frame
        } else if (this.level <= 18) {
            this.dropInterval = 8.33; // 1/2 frame
        } else if (this.level <= 28) {
            this.dropInterval = 6.67; // 1/3 frame
        } else {
            this.dropInterval = 3.33; // 1/5 frame
        }
    }

    // Update ghost piece
    updateGhostPiece() {
        if (this.currentPiece) {
            this.ghostPiece = this.grid.getGhostPosition(this.currentPiece);
        } else {
            this.ghostPiece = null;
        }
    }

    // Update UI elements
    updateUI() {
        this.uiManager.updateStats({
            score: this.score,
            lines: this.lines,
            level: this.level
        });
    }

    // Game state management
    async start() {
        this.state = 'playing';
        this.gameStartTime = Date.now(); // Reset game start time
        this.uiManager.hideOverlay();
        
        // Ensure audio context is ready
        await this.audioManager.resumeAudioContext();
        
        // Start background music
        this.audioManager.startBackgroundMusic();
        
        this.spawnNextPiece();
    }

    pause() {
        if (this.state === 'playing') {
            this.state = 'paused';
            this.uiManager.showPauseOverlay();
        }
    }

    resume() {
        if (this.state === 'paused') {
            this.state = 'playing';
            this.uiManager.hideOverlay();
        }
    }

    togglePause() {
        if (this.state === 'playing') {
            this.pause();
        } else if (this.state === 'paused') {
            this.resume();
        }
    }

    restart() {
        // Reset game state
        this.grid.reset();
        this.score = 0;
        this.lines = 0;
        this.level = 1;
        this.combo = 0;
        this.dropTimer = 0;
        
        // Reset special achievements
        this.specialAchievements = {
            tspins: 0,
            tspinMinis: 0,
            tetris: 0,
            combos: 0,
            perfectClears: 0
        };
        
        this.updateDropSpeed();
        
        this.currentPiece = null;
        this.ghostPiece = null;
        this.heldPiece = null;
        this.heldPieceUsed = false;
        
        // Reset piece bag
        this.pieceBag = new PieceBag();
        this.nextPieces = [];
        for (let i = 0; i < 3; i++) {
            this.nextPieces.push(this.pieceBag.getNextPiece().type);
        }
        
        // Update UI
        this.uiManager.updateNextPieces(this.nextPieces);
        this.uiManager.updateHoldPiece(null);
        
        this.start();
    }

    gameOver() {
        this.state = 'gameover';
        
        // Stop background music and play game over sound
        this.audioManager.stopBackgroundMusic();
        this.audioManager.playSFX('gameOver');
        
        // Prepare final stats
        const finalStats = {
            score: this.score,
            lines: this.lines,
            level: this.level
        };
        
        // Prepare special achievements for database
        const dbAchievements = {
            tspins: this.specialAchievements.tspins + this.specialAchievements.tspinMinis,
            combos: this.specialAchievements.combos,
            tetris: this.specialAchievements.tetris,
            perfectClears: this.specialAchievements.perfectClears
        };
        
        // Set game start time for score saver
        this.uiManager.scoreSaver.setGameStartTime(this.gameStartTime);
        
        this.uiManager.showGameOverOverlay(finalStats, dbAchievements);
    }

    handleConfirm() {
        if (this.state === 'menu' || this.state === 'gameover') {
            this.start();
        }
    }

    // Rendering
    render() {
        this.clearCanvas();
        
        if (this.state === 'playing' || this.state === 'paused') {
            this.renderGrid();
            this.renderGhostPiece();
            this.renderCurrentPiece();
            this.renderGridLines();
        }
    }

    clearCanvas() {
        this.ctx.fillStyle = 'rgba(0, 0, 0, 0.1)';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
    }

    renderGrid() {
        const gridData = this.grid.getRenderData();
        
        gridData.forEach(cell => {
            this.renderCell(cell.x, cell.y, cell.color, false);
        });
    }

    renderCurrentPiece() {
        if (!this.currentPiece) return;
        
        const blocks = this.currentPiece.getBlocks();
        blocks.forEach(block => {
            if (block.y >= 4) { // Only render visible blocks
                this.renderCell(block.x, block.y - 4, block.color, false);
            }
        });
    }

    renderGhostPiece() {
        if (!this.ghostPiece) return;
        
        const blocks = this.ghostPiece.getBlocks();
        blocks.forEach(block => {
            if (block.y >= 4) { // Only render visible blocks
                this.renderCell(block.x, block.y - 4, block.color, true);
            }
        });
    }

    renderCell(x, y, color, isGhost = false) {
        const cellX = x * this.cellSize;
        const cellY = y * this.cellSize;
        
        this.ctx.save();
        
        if (isGhost) {
            this.ctx.globalAlpha = 0.3;
            this.ctx.strokeStyle = color;
            this.ctx.lineWidth = 2;
            this.ctx.strokeRect(cellX + 1, cellY + 1, this.cellSize - 2, this.cellSize - 2);
        } else {
            // Filled block with glow effect
            this.ctx.fillStyle = color;
            this.ctx.fillRect(cellX, cellY, this.cellSize, this.cellSize);
            
            // Inner highlight
            this.ctx.fillStyle = 'rgba(255, 255, 255, 0.3)';
            this.ctx.fillRect(cellX + 1, cellY + 1, this.cellSize - 2, 2);
            this.ctx.fillRect(cellX + 1, cellY + 1, 2, this.cellSize - 2);
            
            // Border
            this.ctx.strokeStyle = 'rgba(255, 255, 255, 0.5)';
            this.ctx.lineWidth = 1;
            this.ctx.strokeRect(cellX, cellY, this.cellSize, this.cellSize);
        }
        
        this.ctx.restore();
    }

    renderGridLines() {
        this.ctx.save();
        this.ctx.strokeStyle = 'rgba(255, 255, 255, 0.1)';
        this.ctx.lineWidth = 0.5;
        
        // Vertical lines
        for (let x = 0; x <= GRID_WIDTH; x++) {
            const lineX = x * this.cellSize;
            this.ctx.beginPath();
            this.ctx.moveTo(lineX, 0);
            this.ctx.lineTo(lineX, GRID_HEIGHT * this.cellSize);
            this.ctx.stroke();
        }
        
        // Horizontal lines
        for (let y = 0; y <= GRID_HEIGHT; y++) {
            const lineY = y * this.cellSize;
            this.ctx.beginPath();
            this.ctx.moveTo(0, lineY);
            this.ctx.lineTo(GRID_WIDTH * this.cellSize, lineY);
            this.ctx.stroke();
        }
        
        this.ctx.restore();
    }
}

// Initialize game when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.tetrisGame = new TetrisGame();
});