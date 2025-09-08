// Modern Tetris - Main Game Engine

import { Grid, GRID_WIDTH, GRID_HEIGHT } from './grid.js';
import { Piece, PieceBag, TSpinDetector, PIECE_TYPES } from './pieces.js';
import { InputManager } from './input.js';
import { UIManager } from './ui.js';
import { AudioManager } from './audio.js';
import { ModalManager } from './modals.js';
import { ModeSelector } from './modeSelector.js';
import { ClassicMode } from './modes/classicMode.js';
import { playerProgression } from './progression/playerProgression.js';
import { achievementSystem } from './achievements/achievementSystem.js';
import { dailyChallenge } from './challenges/dailyChallenge.js';
import { customizationManager } from './customization/customizationManager.js';

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
        this.animationFrameId = null; // Track animation frame for proper cleanup
        
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
        
        // Daily Challenge tracking
        this.tetrisCount = 0;
        this.tspinCount = 0;
        this.maxCombo = 0;
        this.cascadeCount = 0;
        
        // Game modifiers
        this.canHold = true;
        this.canRotateCounterClockwise = true;
        
        // Game mechanics
        this.lockDelay = 0;
        this.maxLockDelay = 500;
        this.lastPiecePosition = null;
        
        // Systems
        this.audioManager = new AudioManager();
        this.inputManager = new InputManager(this);
        this.uiManager = new UIManager(this);
        this.modalManager = new ModalManager(this.audioManager);
        this.modalManager.setGame(this); // Connect modal manager to game
        
        // Connect music manager to audio manager
        if (customizationManager && customizationManager.managers.music) {
            customizationManager.managers.music.audioManager = this.audioManager;
        }
        
        // Mode system
        this.modeSelector = new ModeSelector();
        this.gameMode = null;
        this.currentModeName = 'classic';
        
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
        
        // Add resize listener
        window.addEventListener('resize', () => {
            this.setupCanvas();
        });
        
        // Generate initial next pieces
        for (let i = 0; i < 3; i++) {
            this.nextPieces.push(this.pieceBag.getNextPiece().type);
        }
        
        // Update UI
        this.updateUI();
        
        // Initialize PWA install handler
        this.initializePWAInstall();
        
        // Apply saved customizations
        if (customizationManager) {
            customizationManager.applyAllCustomizations();
            // Make game available globally for customization
            window.tetrisGame = this;
        }
    }

    setupCanvas() {
        // Fixed internal resolution
        const internalWidth = 400;
        const internalHeight = 800;
        
        // Set internal dimensions
        this.canvas.width = internalWidth;
        this.canvas.height = internalHeight;
        
        // CSS will handle the display size
        // The canvas will scale to fit its container
        
        // Setup rendering context
        this.ctx.imageSmoothingEnabled = false;
        this.cellSize = internalWidth / GRID_WIDTH;
        
    }

    // Game loop
    startGameLoop() {
        let lastRenderTime = 0;
        const targetFPS = 60;
        const frameTime = 1000 / targetFPS;
        const maxDeltaTime = 100; // Cap deltaTime to prevent huge jumps
        
        const gameLoop = (currentTime) => {
            let deltaTime = currentTime - this.lastTime;
            
            // Cap deltaTime to prevent issues when returning to tab
            if (deltaTime > maxDeltaTime) {
                deltaTime = maxDeltaTime;
            }
            
            this.lastTime = currentTime;
            
            // Always update game logic
            this.update(deltaTime);
            
            // Limit rendering to 60 FPS for better performance
            if (currentTime - lastRenderTime >= frameTime) {
                this.render();
                lastRenderTime = currentTime;
            }
            
            this.animationFrameId = requestAnimationFrame(gameLoop);
        };
        
        // Start the game loop
        this.lastTime = performance.now();
        this.animationFrameId = requestAnimationFrame(gameLoop);
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
        // Update customization effects
        if (customizationManager) {
            customizationManager.updateEffects(deltaTime);
        }
        
        // Update game mode first
        if (this.gameMode) {
            const continueGame = this.gameMode.update(deltaTime);
            if (!continueGame) {
                // Mode has ended
                return;
            }
        }
        
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
            
            // Trigger movement effects
            if (customizationManager && this.currentPiece) {
                const bounds = this.currentPiece.getBounds();
                customizationManager.onPieceMoved(
                    bounds.minX * this.cellSize + (bounds.maxX - bounds.minX) * this.cellSize / 2,
                    bounds.minY * this.cellSize + (bounds.maxY - bounds.minY) * this.cellSize / 2,
                    this.currentPiece.type
                );
            }
            
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
        
        // Check if counter-clockwise rotation is disabled
        if (direction === -1 && !this.canRotateCounterClockwise) return false;
        
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
        if (!this.currentPiece || this.heldPieceUsed || this.state !== 'playing' || !this.canHold) return;
        
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
        
        // Trigger customization effects for piece placement
        if (customizationManager && this.currentPiece) {
            const bounds = this.currentPiece.getBounds();
            const pieceType = this.currentPiece.type;
            customizationManager.onPiecePlaced(
                bounds.minX * this.cellSize,
                bounds.minY * this.cellSize,
                (bounds.maxX - bounds.minX + 1) * this.cellSize,
                (bounds.maxY - bounds.minY + 1) * this.cellSize,
                pieceType
            );
        }
        
        // Notify game mode that a piece was placed
        if (this.gameMode) {
            if (this.gameMode.onPiecePlaced) {
                this.gameMode.onPiecePlaced();
            } else if (this.gameMode.handlePiecePlaced) {
                this.gameMode.handlePiecePlaced();
            }
        }
        
        // Play lock sound
        this.audioManager.playSFX('lock');
        
        // Process line clearing immediately and completely
        this.processLineClear(tspinResult);
        
        // Check if the game mode is complete or will be complete
        const isModeComplete = this.gameMode && 
            (this.gameMode.isComplete || this.gameMode.pendingCompletion);
        
        // For puzzle mode, don't check game over if objective is met or pending
        if (isModeComplete) {
            this.currentPiece = null;
            return; // Exit early if mode is complete
        }
        
        // Check game over only if mode is not complete
        if (this.grid.isGameOver()) {
            // For puzzle mode, give it one last chance to check objective
            if (this.gameMode && this.gameMode.name === 'Puzzle') {
                // Final check for objective completion
                if (this.gameMode.checkObjectiveComplete && this.gameMode.checkObjectiveComplete()) {
                    this.gameMode.isComplete = true;
                    this.gameMode.pendingCompletion = true;
                    this.currentPiece = null;
                    return;
                }
            }
            this.gameOver();
        } else {
            this.currentPiece = null;
        }
    }

    // Process line clearing with proper sequencing
    processLineClear(tspinResult) {
        // Get lines to clear BEFORE clearing them for animation
        const linesToClear = this.grid.getCompletedLines();
        
        if (linesToClear.length === 0) {
            return; // No lines to clear
        }
        
        // Clear the lines from the grid immediately using the detected lines
        const actualCleared = this.grid.clearLines(linesToClear);
        
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
                this.tspinCount++; // Track for Daily Challenge
                
                // Check T-Spin achievement immediately
                achievementSystem.checkAchievement('FIRST_TSPIN', true);
            }
        }
        
        if (clearedLines === 4) {
            this.specialAchievements.tetris++;
            
            // Check Tetris achievement immediately
            achievementSystem.checkAchievement('FIRST_TETRIS', true);
            console.log('[Game] Tetris achieved! Total Tetris count:', this.specialAchievements.tetris);
        }
        
        // Check for first line achievement
        if (clearedLines > 0 && this.lines <= clearedLines) {
            achievementSystem.checkAchievement('FIRST_LINE', true);
        }
        
        // Update combo achievements in real-time
        if (this.combo >= 5) {
            achievementSystem.checkAchievement('COMBO_5', true);
        }
        if (this.combo >= 10) {
            achievementSystem.checkAchievement('COMBO_10', true);
        }
        if (this.combo >= 20) {
            achievementSystem.checkAchievement('COMBO_20', true);
        }
    }

    // Play line clear effects and sounds
    playLineClearEffects(clearedLines, originalLinePositions, tspinResult) {
        if (clearedLines > 0) {
            // Show visual effects using original line positions
            this.uiManager.showLineClearEffect(originalLinePositions, clearedLines === 4);
            
            // Trigger customization effects
            if (customizationManager) {
                customizationManager.onLinesCleared(originalLinePositions);
            }
            
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
        // Delegate to game mode if available
        if (this.gameMode) {
            const specialClear = {
                type: tspinResult.type,
                mini: tspinResult.mini,
                perfectClear: this.grid.isPerfectClear()
            };
            this.gameMode.handleLineClears(linesCleared, specialClear);
            return;
        }
        
        // Fallback to default scoring (for backward compatibility)
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
                this.tetrisCount++; // Track for Daily Challenge
                break;
        }
        
        // Combo bonus
        this.combo++;
        // Track max combo for Daily Challenge
        if (this.combo > this.maxCombo) {
            this.maxCombo = this.combo;
        }
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
            // Update music tempo based on new level
            this.audioManager.setGameLevel(this.level);
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

    // Select and initialize game mode
    selectMode(modeName) {
        // Clean up previous mode if it exists
        if (this.gameMode && this.gameMode.cleanup) {
            this.gameMode.cleanup();
        }
        
        this.currentModeName = modeName || 'classic';
        this.gameMode = this.modeSelector.createModeInstance(this.currentModeName, this);
        
        if (this.gameMode) {
            this.gameMode.initialize();
            this.uiManager.updateModeDisplay(this.gameMode.getModeUI());
            return true;
        }
        return false;
    }
    
    // Game state management
    async start() {
        // Initialize game mode if not already done
        if (!this.gameMode) {
            this.selectMode('classic');
        }
        
        this.state = 'playing';
        this.gameStartTime = Date.now(); // Reset game start time
        this.uiManager.hideOverlay();
        
        // Start the game mode
        if (this.gameMode) {
            this.gameMode.start();
        }
        
        // Ensure audio context is ready
        await this.audioManager.resumeAudioContext();
        
        // Set initial game level for music tempo
        this.audioManager.setGameLevel(this.level);
        
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
            this.lastTime = performance.now(); // Reset timing to prevent huge deltaTime
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
        // Reset music tempo to level 1
        this.audioManager.setGameLevel(1);
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

    gameOver(isVictory = false, customStats = null) {
        this.state = 'gameover';
        
        // Stop background music and play appropriate sound
        this.audioManager.stopBackgroundMusic();
        
        if (isVictory) {
            this.audioManager.playSFX('levelUp');
        } else {
            this.audioManager.playSFX('gameOver');
        }
        
        // Use custom stats if provided (for modes like Sprint)
        const finalStats = customStats || {
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
        
        // Update player progression and achievements
        const gameResults = {
            mode: this.gameMode ? this.gameMode.name.toLowerCase() : 'classic',
            score: finalStats.score,
            lines: finalStats.lines,
            level: finalStats.level,
            time: Date.now() - this.gameStartTime,
            combo: this.combo,
            tspins: dbAchievements.tspins,
            tetris: dbAchievements.tetris,
            perfectClears: dbAchievements.perfectClears,
            isVictory: isVictory
        };
        
        // Calculate and award XP through progressionManager for proper notifications
        if (window.progressionManager) {
            const xpEarned = playerProgression.calculateGameXP(gameResults);
            // Use progressionManager for XP to show notifications
            window.progressionManager.addXP(xpEarned, 'gameplay').then(() => {
                console.log(`Awarded ${xpEarned} XP for gameplay`);
            }).catch(err => {
                console.error('Error awarding XP:', err);
            });
            playerProgression.updateStats(gameResults);
        } else {
            // Fallback to direct playerProgression if manager not available
            const xpEarned = playerProgression.calculateGameXP(gameResults);
            const xpResult = playerProgression.addXP(xpEarned, 'gameplay');
            playerProgression.updateStats(gameResults);
            console.log(`Fallback: Awarded ${xpEarned} XP (no notifications)`);
        }
        
        // Check achievements at game over
        console.log('[Game] Game Over - Checking achievements');
        console.log('[Game] Stats:', { 
            lines: this.lines, 
            score: this.score, 
            combo: this.combo,
            tspins: dbAchievements.tspins,
            tetris: dbAchievements.tetris
        });
        
        // Update cumulative progress
        achievementSystem.updateProgress('totalLines', this.lines, true);
        achievementSystem.updateProgress('highScore', this.score);
        achievementSystem.updateProgress('maxCombo', this.combo);
        achievementSystem.updateProgress('tspins', dbAchievements.tspins, true);
        achievementSystem.updateProgress('perfectClears', dbAchievements.perfectClears, true);
        
        // Check score achievements
        achievementSystem.checkAchievement('SCORE_10K', this.score >= 10000);
        achievementSystem.checkAchievement('SCORE_100K', this.score >= 100000);
        achievementSystem.checkAchievement('SCORE_1M', this.score >= 1000000);
        
        // Set game start time for score saver
        this.uiManager.scoreSaver.setGameStartTime(this.gameStartTime);
        
        // Show appropriate overlay based on victory status
        if (!isVictory) {
            // Regular game over
            this.uiManager.showGameOverOverlay(finalStats, dbAchievements);
        }
        // For victory cases, the mode handles showing its own victory overlay
    }
    
    // Add helper method for modes to trigger game over
    triggerGameOver(isVictory = false, customStats = null) {
        this.gameOver(isVictory, customStats);
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
            // Apply mode-specific pre-render effects (like monochrome filter)
            if (this.gameMode && this.gameMode.render) {
                this.gameMode.render(this.ctx, this.canvas);
            }
            
            this.renderGrid();
            this.renderGhostPiece();
            this.renderCurrentPiece();
            this.renderGridLines();
            
            // Render customization effects
            if (customizationManager) {
                customizationManager.renderEffects(this.ctx);
            }
            
            // Apply mode-specific post-render effects
            if (this.gameMode && this.gameMode.postRender) {
                this.gameMode.postRender(this.ctx);
            }
            
            // Update mode-specific UI
            if (this.gameMode && this.uiManager) {
                this.uiManager.updateModeUI(this.gameMode.getModeUI());
            }
        }
    }

    clearCanvas() {
        this.ctx.fillStyle = 'rgba(0, 0, 0, 0.1)';
        this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
    }

    renderGrid() {
        const gridData = this.grid.getRenderData();
        
        gridData.forEach(cell => {
            // Pass the type directly if available, otherwise use color
            this.renderCell(cell.x, cell.y, cell.color, false, cell.type);
        });
    }

    renderCurrentPiece() {
        if (!this.currentPiece) return;
        
        const blocks = this.currentPiece.getBlocks();
        blocks.forEach(block => {
            if (block.y >= 4) { // Only render visible blocks
                this.renderCell(block.x, block.y - 4, block.color, false, this.currentPiece.type);
            }
        });
    }

    renderGhostPiece() {
        if (!this.ghostPiece) return;
        
        const blocks = this.ghostPiece.getBlocks();
        blocks.forEach(block => {
            if (block.y >= 4) { // Only render visible blocks
                this.renderCell(block.x, block.y - 4, block.color, true, this.ghostPiece.type);
            }
        });
    }

    renderCell(x, y, color, isGhost = false, pieceType = null) {
        const cellX = x * this.cellSize;
        const cellY = y * this.cellSize;
        
        // Use customization manager for piece rendering
        if (customizationManager) {
            // Use the provided pieceType or try to get it from color
            if (!pieceType) {
                pieceType = this.getPieceTypeFromColor(color);
            }
            customizationManager.renderPiece(this.ctx, cellX, cellY, this.cellSize, pieceType, isGhost);
        } else {
            // Fallback to original rendering
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
    }
    
    getPieceTypeFromColor(color) {
        // Map colors back to piece types for customization
        // Using the actual PIECE_COLORS from pieces.js
        const colorMap = {
            '#00d4ff': 'I',  // Cyan
            '#ffff00': 'O',  // Yellow
            '#9d4edd': 'T',  // Purple
            '#39ff14': 'S',  // Green
            '#ff0040': 'Z',  // Red
            '#0066ff': 'J',  // Blue
            '#ff8500': 'L'   // Orange
        };
        
        // Try lowercase version of color
        const lowerColor = color.toLowerCase();
        return colorMap[lowerColor] || 'J';  // Default to J if not found
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
    
    initializePWAInstall() {
        let deferredPrompt;
        const installButton = document.getElementById('install-button');
        
        // Listen for the beforeinstallprompt event
        window.addEventListener('beforeinstallprompt', (e) => {
            // Prevent the mini-infobar from appearing on mobile
            e.preventDefault();
            // Stash the event so it can be triggered later
            deferredPrompt = e;
            
            // Show install button
            if (installButton) {
                installButton.style.display = 'inline-block';
                
                installButton.addEventListener('click', async () => {
                    if (deferredPrompt) {
                        // Show the install prompt
                        deferredPrompt.prompt();
                        
                        // Wait for the user to respond to the prompt
                        const { outcome } = await deferredPrompt.userChoice;
                        
                        if (outcome === 'accepted') {
                            console.log('User accepted the install prompt');
                            installButton.style.display = 'none';
                        } else {
                            console.log('User dismissed the install prompt');
                        }
                        
                        // Clear the deferredPrompt for use later
                        deferredPrompt = null;
                    }
                });
            }
        });
        
        // Hide button if already installed
        window.addEventListener('appinstalled', () => {
            if (installButton) {
                installButton.style.display = 'none';
            }
            console.log('PWA was installed');
        });
        
        // Check if already installed
        if (window.matchMedia('(display-mode: standalone)').matches || 
            window.navigator.standalone === true) {
            if (installButton) {
                installButton.style.display = 'none';
            }
        }
    }
}

// Initialize game when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.tetrisGame = new TetrisGame();
    
    // Initialize hamburger menu
    const hamburgerMenu = document.getElementById('hamburger-menu');
    const mobileMenu = document.getElementById('mobile-menu');
    
    if (hamburgerMenu && mobileMenu) {
        hamburgerMenu.addEventListener('click', () => {
            hamburgerMenu.classList.toggle('active');
            mobileMenu.classList.toggle('active');
        });
        
        // Close menu when clicking outside
        document.addEventListener('click', (e) => {
            if (!hamburgerMenu.contains(e.target) && !mobileMenu.contains(e.target)) {
                hamburgerMenu.classList.remove('active');
                mobileMenu.classList.remove('active');
            }
        });
        
        // Sync mobile menu buttons with desktop buttons
        const syncButtons = [
            { desktop: 'back-to-menu', mobile: 'mobile-back-to-menu' },
            { desktop: 'game-help-button', mobile: 'mobile-game-help' },
            { desktop: 'mute-button', mobile: 'mobile-mute' }
        ];
        
        syncButtons.forEach(pair => {
            const desktopBtn = document.getElementById(pair.desktop);
            const mobileBtn = document.getElementById(pair.mobile);
            
            if (desktopBtn && mobileBtn) {
                // Clone event listeners from desktop to mobile
                mobileBtn.addEventListener('click', () => {
                    desktopBtn.click();
                    // Close menu after action
                    hamburgerMenu.classList.remove('active');
                    mobileMenu.classList.remove('active');
                });
            }
        });
        
        // Sync profile name updates
        const profileNameElements = ['profile-name', 'mobile-profile-name'];
        const accountStatusElements = ['account-status', 'mobile-account-status'];
        
        // Create observer for profile updates
        const observer = new MutationObserver(() => {
            const desktopProfile = document.getElementById('profile-name');
            const mobileProfile = document.getElementById('mobile-profile-name');
            if (desktopProfile && mobileProfile) {
                mobileProfile.textContent = desktopProfile.textContent;
            }
            
            const desktopAccount = document.getElementById('account-status');
            const mobileAccount = document.getElementById('mobile-account-status');
            if (desktopAccount && mobileAccount) {
                mobileAccount.textContent = desktopAccount.textContent;
            }
        });
        
        // Observe changes to desktop profile elements
        const desktopProfile = document.getElementById('profile-name');
        const desktopAccount = document.getElementById('account-status');
        
        if (desktopProfile) {
            observer.observe(desktopProfile, { childList: true, characterData: true, subtree: true });
        }
        if (desktopAccount) {
            observer.observe(desktopAccount, { childList: true, characterData: true, subtree: true });
        }
    }
});