// Battle Mode - Face off against intelligent AI opponents
import { GameMode } from './gameMode.js';
import { TetrisAI } from '../ai/tetrisAI.js';
import { Grid } from '../grid.js';

export class BattleMode extends GameMode {
    constructor(game) {
        super(game);
        this.name = 'Battle';
        this.description = 'Face off against intelligent AI opponents!';
        this.icon = '⚔️';
        this.themeColor = '#ff0000';
        
        // Battle settings
        this.aiDifficulty = 3; // Default to Hard
        this.roundsToWin = 2; // Best of 3
        this.currentRound = 1;
        this.playerWins = 0;
        this.aiWins = 0;
        
        // AI opponent
        this.ai = null;
        this.aiGrid = null;
        
        // Garbage lines system
        this.playerGarbageQueue = [];
        this.aiGarbageQueue = [];
        
        // Power-ups
        this.powerUps = {
            freeze: { duration: 3000, cooldown: 20000 },
            bomb: { lines: 3, cooldown: 30000 },
            shield: { duration: 5000, cooldown: 25000 },
            speed: { multiplier: 2, duration: 5000, cooldown: 15000 }
        };
        this.activePowerUps = [];
        this.powerUpCooldowns = {};
        
        // Battle stats
        this.battleStats = {
            roundsPlayed: 0,
            totalDamageDealt: 0,
            totalDamageReceived: 0,
            powerUpsUsed: 0,
            maxCombo: 0
        };
    }

    initialize() {
        // Initialize AI opponent
        this.ai = new TetrisAI(this.aiDifficulty);
        this.aiGrid = new Grid();
        
        // Reset battle state
        this.currentRound = 1;
        this.playerWins = 0;
        this.aiWins = 0;
        
        // Reset game state
        this.game.level = 1;
        this.game.lines = 0;
        this.game.score = 0;
        this.game.combo = 0;
        
        // Clear queues
        this.playerGarbageQueue = [];
        this.aiGarbageQueue = [];
        this.activePowerUps = [];
        
        // Initialize power-up cooldowns
        Object.keys(this.powerUps).forEach(powerUp => {
            this.powerUpCooldowns[powerUp] = 0;
        });
        
        // Start round
        this.startRound();
    }

    startRound() {
        // Reset grids
        if (this.game.grid) {
            this.game.grid.reset();
        }
        this.aiGrid.reset();
        this.ai.reset();
        
        // Clear garbage
        this.playerGarbageQueue = [];
        this.aiGarbageQueue = [];
        
        // Show round message
        if (this.game.uiManager) {
            this.game.uiManager.showMessage(
                `Round ${this.currentRound} - FIGHT!`,
                'battle',
                2000
            );
        }
        
        // Play battle music
        if (this.game.audioManager) {
            this.game.audioManager.startBackgroundMusic();
        }
    }

    update(deltaTime) {
        // Update AI
        this.ai.update(deltaTime);
        
        // Process AI moves
        const aiMove = this.ai.executeNextMove();
        if (aiMove) {
            this.processAIMove(aiMove);
        }
        
        // Update power-ups
        this.updatePowerUps(deltaTime);
        
        // Update cooldowns
        this.updateCooldowns(deltaTime);
        
        // Process garbage lines
        this.processGarbageLines();
        
        // Check round end conditions
        if (this.checkRoundEnd()) {
            return false;
        }
        
        return true;
    }

    handlePiecePlaced() {
        // Battle mode tracks pieces for AI simulation
        this.battleStats.piecesPlaced = (this.battleStats.piecesPlaced || 0) + 1;
    }

    handleLineClears(linesCleared, specialClear) {
        if (!linesCleared || linesCleared === 0) {
            this.game.combo = 0;
            return;
        }
        
        // Update stats
        this.game.lines += linesCleared;
        this.game.combo++;
        
        if (this.game.combo > this.battleStats.maxCombo) {
            this.battleStats.maxCombo = this.game.combo;
        }
        
        // Calculate garbage to send
        let garbageLines = 0;
        switch (linesCleared) {
            case 1:
                garbageLines = 0;
                break;
            case 2:
                garbageLines = 1;
                break;
            case 3:
                garbageLines = 2;
                break;
            case 4:
                garbageLines = 4; // Tetris sends 4 lines
                break;
        }
        
        // Combo bonus
        if (this.game.combo > 2) {
            garbageLines += Math.floor(this.game.combo / 2);
        }
        
        // T-spin bonus
        if (specialClear && specialClear.type === 'tspin') {
            garbageLines += 2;
        }
        
        // Perfect clear bonus
        if (specialClear && specialClear.type === 'perfectClear') {
            garbageLines += 6;
        }
        
        // Send garbage to AI
        if (garbageLines > 0) {
            this.sendGarbageToAI(garbageLines);
        }
        
        // Calculate score
        let score = linesCleared * 100 * this.game.level;
        if (specialClear) {
            score *= 2;
        }
        this.game.score += score;
    }

    sendGarbageToAI(lines) {
        // Check if shield is active
        const hasShield = this.activePowerUps.find(p => p.type === 'shield' && p.target === 'ai');
        if (hasShield) {
            lines = Math.floor(lines / 2);
        }
        
        if (lines > 0) {
            this.aiGarbageQueue.push(lines);
            this.ai.addGarbageLines(lines);
            this.battleStats.totalDamageDealt += lines;
            
            // Visual feedback
            if (this.game.uiManager) {
                this.game.uiManager.showDamage('ai', lines);
            }
        }
    }

    receiveGarbageFromAI(lines) {
        // Check if player has shield
        const hasShield = this.activePowerUps.find(p => p.type === 'shield' && p.target === 'player');
        if (hasShield) {
            lines = Math.floor(lines / 2);
        }
        
        if (lines > 0) {
            this.playerGarbageQueue.push(lines);
            this.battleStats.totalDamageReceived += lines;
            
            // Visual feedback
            if (this.game.uiManager) {
                this.game.uiManager.showDamage('player', lines);
            }
        }
    }

    processGarbageLines() {
        // Process player garbage
        if (this.playerGarbageQueue.length > 0 && this.game.grid) {
            const lines = this.playerGarbageQueue.shift();
            this.addGarbageLinesToGrid(this.game.grid, lines);
        }
        
        // AI processes its own garbage
        // Handled in AI update
    }

    addGarbageLinesToGrid(grid, count) {
        for (let i = 0; i < count; i++) {
            // Remove top row
            grid.cells.shift();
            
            // Create garbage line with one hole
            const garbageLine = Array(grid.width).fill(8); // Gray blocks
            const hole = Math.floor(Math.random() * grid.width);
            garbageLine[hole] = 0;
            
            // Add to bottom
            grid.cells.push(garbageLine);
        }
    }

    processAIMove(move) {
        // Simulate AI playing
        switch (move) {
            case 'left':
            case 'right':
            case 'rotate':
            case 'drop':
                // Update AI grid visualization
                if (this.game.uiManager) {
                    this.game.uiManager.updateAIGrid(this.aiGrid);
                }
                break;
        }
        
        // Check if AI cleared lines
        const aiLines = this.checkAILineClears();
        if (aiLines > 0) {
            this.receiveGarbageFromAI(this.calculateGarbageFromLines(aiLines));
        }
    }

    checkAILineClears() {
        // Simplified AI line clear detection
        return Math.random() < 0.1 ? Math.floor(Math.random() * 4) + 1 : 0;
    }

    calculateGarbageFromLines(lines) {
        switch (lines) {
            case 1: return 0;
            case 2: return 1;
            case 3: return 2;
            case 4: return 4;
            default: return 0;
        }
    }

    usePowerUp(type) {
        // Check cooldown
        if (this.powerUpCooldowns[type] > 0) {
            return false;
        }
        
        const powerUp = this.powerUps[type];
        if (!powerUp) return false;
        
        // Apply power-up effect
        switch (type) {
            case 'freeze':
                this.activateFreeze(powerUp.duration);
                break;
            case 'bomb':
                this.activateBomb(powerUp.lines);
                break;
            case 'shield':
                this.activateShield(powerUp.duration);
                break;
            case 'speed':
                this.activateSpeed(powerUp.multiplier, powerUp.duration);
                break;
        }
        
        // Set cooldown
        this.powerUpCooldowns[type] = powerUp.cooldown;
        this.battleStats.powerUpsUsed++;
        
        return true;
    }

    activateFreeze(duration) {
        this.activePowerUps.push({
            type: 'freeze',
            target: 'ai',
            duration: duration,
            remaining: duration
        });
        
        // Freeze AI
        this.ai.params.thinkingDelay *= 3;
        
        if (this.game.uiManager) {
            this.game.uiManager.showPowerUp('freeze', 'ai');
        }
    }

    activateBomb(lines) {
        // Clear random lines from AI grid
        for (let i = 0; i < lines && i < this.aiGrid.cells.length; i++) {
            const row = Math.floor(Math.random() * this.aiGrid.cells.length);
            this.aiGrid.cells[row] = Array(this.aiGrid.width).fill(0);
        }
        
        if (this.game.uiManager) {
            this.game.uiManager.showPowerUp('bomb', 'ai');
        }
    }

    activateShield(duration) {
        this.activePowerUps.push({
            type: 'shield',
            target: 'player',
            duration: duration,
            remaining: duration
        });
        
        if (this.game.uiManager) {
            this.game.uiManager.showPowerUp('shield', 'player');
        }
    }

    activateSpeed(multiplier, duration) {
        this.activePowerUps.push({
            type: 'speed',
            target: 'player',
            duration: duration,
            remaining: duration,
            multiplier: multiplier
        });
        
        // Speed up player drop rate
        this.game.dropInterval /= multiplier;
        
        if (this.game.uiManager) {
            this.game.uiManager.showPowerUp('speed', 'player');
        }
    }

    updatePowerUps(deltaTime) {
        this.activePowerUps = this.activePowerUps.filter(powerUp => {
            powerUp.remaining -= deltaTime;
            
            if (powerUp.remaining <= 0) {
                // Remove power-up effect
                this.removePowerUpEffect(powerUp);
                return false;
            }
            return true;
        });
    }

    removePowerUpEffect(powerUp) {
        switch (powerUp.type) {
            case 'freeze':
                // Restore AI speed
                if (this.ai) {
                    this.ai.setupDifficultyParams();
                }
                break;
            case 'speed':
                // Restore player speed
                this.game.dropInterval *= powerUp.multiplier;
                break;
        }
    }

    updateCooldowns(deltaTime) {
        Object.keys(this.powerUpCooldowns).forEach(key => {
            if (this.powerUpCooldowns[key] > 0) {
                this.powerUpCooldowns[key] = Math.max(0, this.powerUpCooldowns[key] - deltaTime);
            }
        });
    }

    checkRoundEnd() {
        // Check if player lost
        if (this.game.grid && this.game.grid.checkGameOver()) {
            this.aiWins++;
            this.endRound(false);
            return true;
        }
        
        // Check if AI lost (simplified)
        if (this.aiGrid && this.aiGrid.checkGameOver()) {
            this.playerWins++;
            this.endRound(true);
            return true;
        }
        
        return false;
    }

    endRound(playerWon) {
        this.battleStats.roundsPlayed++;
        
        // Show round result
        if (this.game.uiManager) {
            this.game.uiManager.showRoundResult(
                playerWon,
                this.currentRound,
                this.playerWins,
                this.aiWins
            );
        }
        
        // Check if match is over
        if (this.playerWins >= this.roundsToWin) {
            this.endMatch(true);
        } else if (this.aiWins >= this.roundsToWin) {
            this.endMatch(false);
        } else {
            // Start next round
            this.currentRound++;
            setTimeout(() => this.startRound(), 3000);
        }
    }

    endMatch(playerWon) {
        this.isComplete = true;
        
        const result = {
            won: playerWon,
            rounds: this.battleStats.roundsPlayed,
            playerWins: this.playerWins,
            aiWins: this.aiWins,
            totalDamageDealt: this.battleStats.totalDamageDealt,
            totalDamageReceived: this.battleStats.totalDamageReceived,
            powerUpsUsed: this.battleStats.powerUpsUsed,
            aiDifficulty: this.aiDifficulty
        };
        
        // Save battle stats
        this.saveBattleStats(result);
        
        // Show match result
        if (this.game.uiManager) {
            this.game.uiManager.showMatchResult(result);
        }
        
        return result;
    }

    saveBattleStats(stats) {
        const key = 'tetris_battle_stats';
        let allStats = [];
        
        const saved = localStorage.getItem(key);
        if (saved) {
            try {
                allStats = JSON.parse(saved);
            } catch (e) {
                console.error('Failed to load battle stats:', e);
            }
        }
        
        allStats.push({
            ...stats,
            timestamp: Date.now()
        });
        
        // Keep only last 50 battles
        if (allStats.length > 50) {
            allStats = allStats.slice(-50);
        }
        
        localStorage.setItem(key, JSON.stringify(allStats));
    }

    setDifficulty(difficulty) {
        this.aiDifficulty = Math.max(1, Math.min(5, difficulty));
        if (this.ai) {
            this.ai.difficulty = this.aiDifficulty;
            this.ai.setupDifficultyParams();
        }
    }

    getObjective() {
        return `Win ${this.roundsToWin} rounds! (${this.playerWins}-${this.aiWins})`;
    }

    getModeUI() {
        return {
            showScore: true,
            showLines: true,
            showLevel: false,
            showHold: true,
            showNext: true,
            showTimer: false,
            showObjective: true,
            showBattleGrid: true, // Special flag for battle mode
            customDisplay: {
                round: `Round ${this.currentRound}`,
                playerWins: this.playerWins,
                aiWins: this.aiWins,
                aiDifficulty: ['Easy', 'Normal', 'Hard', 'Expert', 'Grandmaster'][this.aiDifficulty - 1],
                powerUps: this.getAvailablePowerUps()
            }
        };
    }

    getAvailablePowerUps() {
        const available = [];
        Object.keys(this.powerUps).forEach(key => {
            available.push({
                type: key,
                ready: this.powerUpCooldowns[key] === 0,
                cooldown: Math.ceil(this.powerUpCooldowns[key] / 1000)
            });
        });
        return available;
    }

    handleInput(action) {
        // Handle power-up inputs
        switch (action) {
            case 'powerUp1':
                return this.usePowerUp('freeze');
            case 'powerUp2':
                return this.usePowerUp('bomb');
            case 'powerUp3':
                return this.usePowerUp('shield');
            case 'powerUp4':
                return this.usePowerUp('speed');
        }
        return false;
    }

    getLeaderboardCategory() {
        return `battle_${this.aiDifficulty}`;
    }

    supportsSaving() {
        return false; // Battle mode is match-based
    }

    getIcon() {
        return '⚔️';
    }

    getThemeColor() {
        return '#ff0000';
    }
}