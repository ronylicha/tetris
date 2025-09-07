// Power-Up Mode - Classic gameplay with power-ups
import { GameMode } from './gameMode.js';
import { PowerUpManager } from '../powerups/powerUpManager.js';

export class PowerUpMode extends GameMode {
    constructor(game) {
        super(game);
        this.name = 'Power-Up';
        this.description = 'Classic Tetris with exciting power-ups!';
        this.icon = '⚡';
        this.themeColor = '#ff00ff';
        
        // Power-up manager
        this.powerUpManager = new PowerUpManager(game);
        
        // Track accomplishments
        this.linesSinceLastPowerUp = 0;
        this.lastCombo = 0;
        this.consecutiveTSpins = 0;
    }
    
    initialize() {
        // Reset game state
        this.game.score = 0;
        this.game.lines = 0;
        this.game.level = 1;
        this.game.combo = 0;
        
        // Initialize power-up manager
        this.powerUpManager.initialize();
        
        // Reset tracking
        this.linesSinceLastPowerUp = 0;
        this.lastCombo = 0;
        this.consecutiveTSpins = 0;
        
        // Set initial drop speed
        this.updateDropSpeed();
        
        // Show power-up UI
        const powerupUI = document.getElementById('powerup-ui');
        if (powerupUI) {
            powerupUI.style.display = 'block';
        }
        
        // Initialize power-up slots display
        this.powerUpManager.updateSlotDisplay(0);
        this.powerUpManager.updateSlotDisplay(1);
        
        // Show instructions
        if (this.game.uiManager) {
            this.game.uiManager.showMessage(
                'Power-Up Mode! Press V/B to use power-ups',
                'powerup',
                3000
            );
        }
    }
    
    update(deltaTime) {
        // Update power-up manager
        this.powerUpManager.update(deltaTime);
        
        // Update active power-ups display
        this.powerUpManager.updateActivePowerUpsDisplay();
        
        // Check for level progression
        const newLevel = Math.floor(this.game.lines / 10) + 1;
        if (newLevel > this.game.level) {
            this.game.level = newLevel;
            this.updateDropSpeed();
            
            // Bonus power-up every 5 levels
            if (this.game.level % 5 === 0) {
                this.powerUpManager.generatePowerUp();
                this.powerUpManager.generatePowerUp();
            }
        }
        
        return true; // Continue game
    }
    
    handleLineClears(linesCleared, specialClear) {
        if (linesCleared === 0) return;
        
        // Track lines
        this.linesSinceLastPowerUp += linesCleared;
        
        // Check for Tetris
        if (linesCleared === 4) {
            this.powerUpManager.checkAccomplishments('lines_cleared', { count: 4, total: this.game.lines });
        }
        
        // Check for T-Spin
        if (specialClear && specialClear.type === 'tspin') {
            this.consecutiveTSpins++;
            this.powerUpManager.checkAccomplishments('tspin', this.consecutiveTSpins);
        } else {
            this.consecutiveTSpins = 0;
        }
        
        // Check for perfect clear
        if (this.game.grid && this.game.grid.isEmpty()) {
            this.powerUpManager.checkAccomplishments('perfect_clear', true);
        }
        
        // Check lines milestones
        this.powerUpManager.checkAccomplishments('lines_cleared', { 
            count: linesCleared, 
            total: this.game.lines 
        });
        
        // Calculate score with multiplier
        let baseScore = this.calculateLineScore(linesCleared, specialClear);
        const multiplier = this.game.scoreMultiplier || 1;
        const score = Math.floor(baseScore * multiplier);
        
        this.game.score += score;
        
        // Check score milestones
        this.powerUpManager.checkAccomplishments('score', this.game.score);
        
        // Update combo
        if (linesCleared > 0) {
            this.game.combo++;
            
            // Check combo milestones
            if (this.game.combo >= 5) {
                this.powerUpManager.checkAccomplishments('combo', this.game.combo);
            }
        } else {
            this.game.combo = 0;
        }
    }
    
    calculateLineScore(lines, specialClear) {
        const baseScores = [0, 100, 300, 500, 800];
        let score = baseScores[Math.min(lines, 4)] * this.game.level;
        
        // T-Spin bonus
        if (specialClear && specialClear.type === 'tspin') {
            score += specialClear.mini ? 200 : 400;
        }
        
        // Combo bonus
        if (this.game.combo > 1) {
            score += 50 * this.game.combo * this.game.level;
        }
        
        return score;
    }
    
    updateDropSpeed() {
        // Base speed increases with level
        const baseInterval = 1000;
        const speedIncrease = 50;
        this.game.dropInterval = Math.max(50, baseInterval - (this.game.level - 1) * speedIncrease);
    }
    
    getObjective() {
        return `Score as many points as possible with power-ups!`;
    }
    
    getModeUI() {
        return {
            showScore: true,
            showLines: true,
            showLevel: true,
            customDisplay: {
                powerups: true  // Flag to indicate power-up UI should be created
            }
        };
    }
    
    handleKeyPress(key) {
        // Handle power-up activation
        switch(key.toLowerCase()) {
            case 'v':
                this.powerUpManager.activatePowerUp(0);
                return true;
            case 'b':
                this.powerUpManager.activatePowerUp(1);
                return true;
        }
        return false;
    }
    
    handlePiecePlaced() {
        // Standard piece placement handling
        // Power-ups are checked in handleLineClears
    }
    
    checkGameOver() {
        // Check if any pieces are above row 0
        if (this.game.grid) {
            for (let x = 0; x < this.game.grid.width; x++) {
                if (this.game.grid.cells[0][x] !== 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    onGameOver() {
        // Save high score
        const stats = {
            mode: 'powerup',
            score: this.game.score,
            lines: this.game.lines,
            level: this.game.level,
            powerUpsUsed: this.powerUpManager.activePowerUps.length,
            maxCombo: this.game.combo
        };
        
        return {
            title: 'Game Over!',
            stats: stats,
            showLeaderboard: true
        };
    }
    
    cleanup() {
        // Hide power-up UI
        const powerupUI = document.getElementById('powerup-ui');
        if (powerupUI) {
            powerupUI.style.display = 'none';
        }
        
        // Clear any active power-ups
        this.powerUpManager.clearAll();
    }
    
    // Save/Load state for offline support
    getState() {
        return {
            score: this.game.score,
            lines: this.game.lines,
            level: this.game.level,
            combo: this.game.combo,
            powerUpState: this.powerUpManager.getState(),
            linesSinceLastPowerUp: this.linesSinceLastPowerUp,
            consecutiveTSpins: this.consecutiveTSpins
        };
    }
    
    loadState(state) {
        if (state) {
            this.game.score = state.score || 0;
            this.game.lines = state.lines || 0;
            this.game.level = state.level || 1;
            this.game.combo = state.combo || 0;
            this.linesSinceLastPowerUp = state.linesSinceLastPowerUp || 0;
            this.consecutiveTSpins = state.consecutiveTSpins || 0;
            
            if (state.powerUpState) {
                this.powerUpManager.loadState(state.powerUpState);
            }
            
            this.updateDropSpeed();
        }
    }
}