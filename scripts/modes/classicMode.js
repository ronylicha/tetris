// Classic Tetris Mode - The original endless gameplay
import { GameMode } from './gameMode.js';

export class ClassicMode extends GameMode {
    constructor(game) {
        super(game);
        this.name = 'Classic';
        this.description = 'The original endless Tetris experience';
        this.icon = '🎮';
        this.themeColor = 'var(--neon-blue)';
    }

    initialize() {
        // Classic mode starts at level 1
        this.game.level = 1;
        this.game.lines = 0;
        this.game.score = 0;
        this.game.combo = 0;
        
        // Reset special achievements
        this.game.specialAchievements = {
            tspins: 0,
            tspinMinis: 0,
            tetris: 0,
            combos: 0,
            perfectClears: 0
        };
        
        this.modeSpecificStats = {
            highestLevel: 1,
            maxCombo: 0,
            totalTSpins: 0,
            totalTetris: 0
        };
    }

    update(deltaTime) {
        // Classic mode has no special update logic
        // Game continues until game over
        return true;
    }

    handlePiecePlaced() {
        // Classic mode doesn't need special piece tracking
    }

    handleLineClears(linesCleared, specialClear) {
        if (!linesCleared || linesCleared === 0) return;
        
        // Update lines cleared
        this.game.lines += linesCleared;
        
        // Calculate score based on classic scoring
        let baseScore = 0;
        switch (linesCleared) {
            case 1:
                baseScore = 100;
                break;
            case 2:
                baseScore = 300;
                break;
            case 3:
                baseScore = 500;
                break;
            case 4:
                baseScore = 800;
                this.game.specialAchievements.tetris++;
                this.modeSpecificStats.totalTetris++;
                break;
        }
        
        // Apply level multiplier
        let score = baseScore * this.game.level;
        
        // Add combo bonus
        if (this.game.combo > 0) {
            score += 50 * this.game.combo * this.game.level;
            this.game.specialAchievements.combos++;
        }
        
        // Add T-spin bonus
        if (specialClear && specialClear.type === 'tspin') {
            score += 400 * this.game.level;
            this.game.specialAchievements.tspins++;
            this.modeSpecificStats.totalTSpins++;
        }
        
        // Add perfect clear bonus
        if (specialClear && specialClear.type === 'perfectClear') {
            score += 1000 * this.game.level;
            this.game.specialAchievements.perfectClears++;
        }
        
        this.game.score += score;
        
        // Update combo
        this.game.combo++;
        if (this.game.combo > this.modeSpecificStats.maxCombo) {
            this.modeSpecificStats.maxCombo = this.game.combo;
        }
        
        // Level progression - every 10 lines
        const newLevel = Math.floor(this.game.lines / 10) + 1;
        if (newLevel > this.game.level) {
            this.game.level = newLevel;
            if (newLevel > this.modeSpecificStats.highestLevel) {
                this.modeSpecificStats.highestLevel = newLevel;
            }
            
            // Increase game speed
            this.updateGameSpeed();
            
            // Update music tempo
            if (this.game.audioManager) {
                this.game.audioManager.setGameLevel(this.game.level);
            }
        }
    }

    updateGameSpeed() {
        // Classic speed curve
        const baseInterval = 1000;
        const speedMultiplier = Math.pow(0.9, this.game.level - 1);
        this.game.dropInterval = Math.max(50, baseInterval * speedMultiplier);
    }

    getObjective() {
        return 'Survive as long as possible!';
    }

    getModeUI() {
        return {
            showScore: true,
            showLines: true,
            showLevel: true,
            showHold: true,
            showNext: true,
            showTimer: false,
            showObjective: false,
            customDisplay: null
        };
    }

    getProgress() {
        // Classic mode has no completion, return level progress
        return ((this.game.lines % 10) / 10) * 100;
    }

    shouldEnd() {
        // Classic mode only ends on game over
        return false;
    }

    getScoreMultiplier() {
        // Classic mode uses standard scoring
        return 1;
    }

    getSpeedLevel() {
        return this.game.level;
    }

    isHoldAllowed() {
        return true;
    }

    showGhostPiece() {
        return true;
    }

    getMusicTempo() {
        // Tempo increases with level
        return 1 + (this.game.level - 1) * 0.02;
    }

    handleGameOver() {
        const result = super.handleGameOver();
        
        // Add final stats
        result.finalScore = this.game.score;
        result.finalLines = this.game.lines;
        result.finalLevel = this.game.level;
        result.achievements = this.game.specialAchievements;
        
        return result;
    }

    saveState() {
        const baseState = super.saveState();
        return {
            ...baseState,
            score: this.game.score,
            lines: this.game.lines,
            level: this.game.level,
            combo: this.game.combo,
            specialAchievements: this.game.specialAchievements,
            dropInterval: this.game.dropInterval
        };
    }

    loadState(state) {
        super.loadState(state);
        this.game.score = state.score;
        this.game.lines = state.lines;
        this.game.level = state.level;
        this.game.combo = state.combo;
        this.game.specialAchievements = state.specialAchievements;
        this.game.dropInterval = state.dropInterval;
    }

    getLeaderboardCategory() {
        return 'classic';
    }

    supportsSaving() {
        return false; // Classic mode doesn't support mid-game saving
    }

    getIcon() {
        return '🎮';
    }

    getThemeColor() {
        return 'var(--neon-blue)';
    }
}