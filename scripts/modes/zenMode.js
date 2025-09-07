// Zen Mode - Relaxing endless mode with no pressure
import { GameMode } from './gameMode.js';

export class ZenMode extends GameMode {
    constructor(game) {
        super(game);
        this.name = 'Zen';
        this.description = 'Relax and play without pressure. No game over!';
        this.icon = '🧘';
        this.themeColor = '#00ff00';
        
        // Zen mode settings
        this.customSpeed = this.loadCustomSpeed();
        this.autoSaveInterval = 30000; // Auto-save every 30 seconds
        this.lastAutoSave = Date.now();
        this.sessionStats = null;
        this.savedGames = [];
    }

    initialize() {
        // Zen mode configuration
        this.game.level = 1;
        this.game.lines = 0;
        this.game.score = 0;
        this.game.combo = 0;
        
        // Use custom speed or default
        this.game.dropInterval = this.customSpeed || 1000;
        
        this.modeSpecificStats = {
            sessionTime: 0,
            totalPieces: 0,
            totalLines: 0,
            totalScore: 0,
            averageLinesPerMinute: 0,
            maxCombo: 0,
            tspins: 0,
            tetris: 0,
            perfectClears: 0,
            efficiency: 0,
            bestStreak: 0,
            currentStreak: 0
        };
        
        // Initialize session tracking
        this.sessionStats = {
            startTime: Date.now(),
            pieces: [],
            lineClears: [],
            scores: []
        };
        
        // Load any saved game
        this.checkForSavedGames();
    }

    update(deltaTime) {
        // Update session time
        this.modeSpecificStats.sessionTime = Date.now() - this.sessionStats.startTime;
        
        // Calculate statistics
        this.updateStatistics();
        
        // Auto-save periodically
        if (Date.now() - this.lastAutoSave > this.autoSaveInterval) {
            this.autoSave();
            this.lastAutoSave = Date.now();
        }
        
        // Zen mode never ends
        return true;
    }

    handleLineClears(linesCleared, specialClear) {
        if (!linesCleared || linesCleared === 0) {
            // Break streak
            if (this.modeSpecificStats.currentStreak > this.modeSpecificStats.bestStreak) {
                this.modeSpecificStats.bestStreak = this.modeSpecificStats.currentStreak;
            }
            this.modeSpecificStats.currentStreak = 0;
            return;
        }
        
        // Update lines
        this.game.lines += linesCleared;
        this.modeSpecificStats.totalLines = this.game.lines;
        
        // Track line clears
        this.sessionStats.lineClears.push({
            lines: linesCleared,
            time: Date.now() - this.sessionStats.startTime,
            special: specialClear
        });
        
        // Update streak
        this.modeSpecificStats.currentStreak++;
        
        // Calculate score (relaxed scoring)
        let score = linesCleared * 100;
        
        // Special clear bonuses
        if (specialClear) {
            if (specialClear.type === 'tspin') {
                score += 200;
                this.modeSpecificStats.tspins++;
            } else if (specialClear.type === 'perfectClear') {
                score += 500;
                this.modeSpecificStats.perfectClears++;
            }
        }
        
        // Tetris bonus
        if (linesCleared === 4) {
            score += 300;
            this.modeSpecificStats.tetris++;
        }
        
        // Combo bonus
        if (this.game.combo > 0) {
            score += 50 * this.game.combo;
        }
        this.game.combo++;
        
        if (this.game.combo > this.modeSpecificStats.maxCombo) {
            this.modeSpecificStats.maxCombo = this.game.combo;
        }
        
        this.game.score += score;
        this.modeSpecificStats.totalScore = this.game.score;
        
        // Track score
        this.sessionStats.scores.push({
            score: score,
            total: this.game.score,
            time: Date.now() - this.sessionStats.startTime
        });
        
        // Gentle level progression (optional)
        const newLevel = Math.floor(this.game.lines / 20) + 1; // Slower progression
        if (newLevel > this.game.level) {
            this.game.level = newLevel;
            // Don't change speed in Zen mode unless user wants
            if (!this.customSpeed) {
                this.updateGameSpeed();
            }
        }
    }

    handlePiecePlaced() {
        this.modeSpecificStats.totalPieces++;
        this.sessionStats.pieces.push(Date.now() - this.sessionStats.startTime);
    }

    updateGameSpeed() {
        // Very gentle speed increase in Zen mode
        const baseInterval = 1000;
        const speedMultiplier = Math.pow(0.98, this.game.level - 1);
        this.game.dropInterval = Math.max(200, baseInterval * speedMultiplier);
    }

    updateStatistics() {
        // Calculate lines per minute
        const minutesPlayed = this.modeSpecificStats.sessionTime / 60000;
        if (minutesPlayed > 0) {
            this.modeSpecificStats.averageLinesPerMinute = 
                (this.game.lines / minutesPlayed).toFixed(1);
        }
        
        // Calculate efficiency
        if (this.modeSpecificStats.totalPieces > 0) {
            this.modeSpecificStats.efficiency = 
                (this.game.lines / this.modeSpecificStats.totalPieces * 100).toFixed(1);
        }
    }

    // Special Zen mode features
    clearGrid() {
        // Allow clearing the grid in Zen mode
        if (this.game.grid) {
            this.game.grid.reset();
        }
    }

    setCustomSpeed(speed) {
        this.customSpeed = speed;
        this.game.dropInterval = speed;
        localStorage.setItem('tetris_zen_speed', speed.toString());
    }

    loadCustomSpeed() {
        const saved = localStorage.getItem('tetris_zen_speed');
        return saved ? parseInt(saved) : null;
    }

    exportStatistics() {
        // Export session statistics as JSON
        const exportData = {
            mode: 'zen',
            date: new Date().toISOString(),
            sessionTime: this.modeSpecificStats.sessionTime,
            stats: this.modeSpecificStats,
            sessionDetails: this.sessionStats
        };
        
        const blob = new Blob([JSON.stringify(exportData, null, 2)], 
            { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `tetris_zen_${Date.now()}.json`;
        a.click();
        URL.revokeObjectURL(url);
    }

    autoSave() {
        const saveData = this.saveState();
        const saveId = `zen_${Date.now()}`;
        
        // Keep only last 5 saves
        this.savedGames.push({ id: saveId, data: saveData, time: Date.now() });
        if (this.savedGames.length > 5) {
            this.savedGames.shift();
        }
        
        localStorage.setItem('tetris_zen_saves', JSON.stringify(this.savedGames));
        
        // Show save indicator
        if (this.game.uiManager) {
            this.game.uiManager.showMessage('Game saved', 'success', 1000);
        }
    }

    checkForSavedGames() {
        const saved = localStorage.getItem('tetris_zen_saves');
        if (saved) {
            try {
                this.savedGames = JSON.parse(saved);
                if (this.savedGames.length > 0 && this.game.uiManager) {
                    // Show load game prompt
                    this.game.uiManager.showLoadGamePrompt(this.savedGames, (saveId) => {
                        const save = this.savedGames.find(s => s.id === saveId);
                        if (save) {
                            this.loadState(save.data);
                        }
                    });
                }
            } catch (e) {
                console.error('Failed to load saved games:', e);
            }
        }
    }

    getObjective() {
        return 'Relax and enjoy! No pressure.';
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
            customDisplay: {
                time: this.formatTime(this.modeSpecificStats.sessionTime),
                lpm: `${this.modeSpecificStats.averageLinesPerMinute} LPM`,
                efficiency: `${this.modeSpecificStats.efficiency}%`,
                pieces: this.modeSpecificStats.totalPieces,
                streak: this.modeSpecificStats.currentStreak
            }
        };
    }

    formatTime(milliseconds) {
        const totalSeconds = Math.floor(milliseconds / 1000);
        const hours = Math.floor(totalSeconds / 3600);
        const minutes = Math.floor((totalSeconds % 3600) / 60);
        const seconds = totalSeconds % 60;
        
        if (hours > 0) {
            return `${hours}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
        }
        return `${minutes}:${seconds.toString().padStart(2, '0')}`;
    }

    getProgress() {
        // Zen mode has no completion
        return 0;
    }

    shouldEnd() {
        // Zen mode never ends
        return false;
    }

    getMusicTempo() {
        // Calm, consistent tempo for Zen mode
        return 0.8; // Slower than normal
    }

    handleGameOver() {
        // In Zen mode, there's no real game over
        // Just clear the grid and continue
        this.clearGrid();
        
        // Reset combo
        this.game.combo = 0;
        
        // Show message
        if (this.game.uiManager) {
            this.game.uiManager.showMessage('Grid cleared! Keep playing.', 'info', 2000);
        }
        
        // Don't actually end the game
        return null;
    }

    saveState() {
        const baseState = super.saveState();
        return {
            ...baseState,
            lines: this.game.lines,
            score: this.game.score,
            level: this.game.level,
            customSpeed: this.customSpeed,
            sessionStats: this.sessionStats,
            grid: this.game.grid ? this.game.grid.saveState() : null,
            currentPiece: this.game.currentPiece ? this.game.currentPiece.saveState() : null,
            heldPiece: this.game.heldPiece ? this.game.heldPiece.saveState() : null,
            nextPieces: this.game.nextPieces
        };
    }

    loadState(state) {
        super.loadState(state);
        this.game.lines = state.lines;
        this.game.score = state.score;
        this.game.level = state.level;
        this.customSpeed = state.customSpeed;
        this.sessionStats = state.sessionStats;
        
        // Restore grid and pieces
        if (state.grid && this.game.grid) {
            this.game.grid.loadState(state.grid);
        }
        if (state.currentPiece && this.game.currentPiece) {
            this.game.currentPiece.loadState(state.currentPiece);
        }
        if (state.heldPiece && this.game.heldPiece) {
            this.game.heldPiece.loadState(state.heldPiece);
        }
        if (state.nextPieces) {
            this.game.nextPieces = state.nextPieces;
        }
        
        // Restore custom speed
        if (this.customSpeed) {
            this.game.dropInterval = this.customSpeed;
        }
    }

    handleInput(action) {
        // Special Zen mode inputs
        if (action === 'clearGrid') {
            this.clearGrid();
            return true;
        } else if (action === 'exportStats') {
            this.exportStatistics();
            return true;
        } else if (action === 'quickSave') {
            this.autoSave();
            return true;
        }
        return false;
    }

    getLeaderboardCategory() {
        return 'zen';
    }

    supportsSaving() {
        return true; // Zen mode fully supports saving
    }

    getIcon() {
        return '🧘';
    }

    getThemeColor() {
        return '#00ff00';
    }
}