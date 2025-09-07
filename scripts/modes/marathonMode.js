// Marathon Mode - Survive 150 lines with increasing difficulty
import { GameMode } from './gameMode.js';

export class MarathonMode extends GameMode {
    constructor(game) {
        super(game);
        this.name = 'Marathon';
        this.description = 'Survive 150 lines with increasing difficulty!';
        this.icon = '🏃';
        this.themeColor = '#ffff00';
        
        this.targetLines = 150;
        this.checkpoints = [50, 100, 150]; // Checkpoint levels
        this.lastCheckpoint = 0;
        this.savedState = null;
    }

    initialize() {
        // Marathon mode configuration
        this.game.level = 1;
        this.game.lines = 0;
        this.game.score = 0;
        this.game.combo = 0;
        
        // Start with moderate speed
        this.game.dropInterval = 800;
        
        this.modeSpecificStats = {
            targetLines: this.targetLines,
            linesRemaining: this.targetLines,
            checkpointsReached: 0,
            nextCheckpoint: this.checkpoints[0],
            maxLevel: 1,
            totalScore: 0,
            maxCombo: 0,
            tspins: 0,
            tetris: 0
        };
        
        // Check for saved game
        this.loadSavedGame();
    }

    update(deltaTime) {
        // Check if target reached
        if (this.game.lines >= this.targetLines && !this.isComplete) {
            this.handleVictory();
            return false;
        }
        
        // Update remaining lines
        this.modeSpecificStats.linesRemaining = Math.max(0, this.targetLines - this.game.lines);
        
        // Check for checkpoint
        this.checkCheckpoint();
        
        // Auto-save at checkpoints
        if (this.shouldAutoSave()) {
            this.autoSave();
        }
        
        return true;
    }

    handlePiecePlaced() {
        // Marathon mode doesn't need special piece tracking
    }

    handleLineClears(linesCleared, specialClear) {
        if (!linesCleared || linesCleared === 0) return;
        
        // Update lines
        this.game.lines += linesCleared;
        
        // Calculate score with level multiplier
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
                this.modeSpecificStats.tetris++;
                break;
        }
        
        let score = baseScore * this.game.level;
        
        // Combo bonus
        if (this.game.combo > 0) {
            score += 50 * this.game.combo * this.game.level;
        }
        this.game.combo++;
        
        if (this.game.combo > this.modeSpecificStats.maxCombo) {
            this.modeSpecificStats.maxCombo = this.game.combo;
        }
        
        // T-spin bonus
        if (specialClear && specialClear.type === 'tspin') {
            score += 400 * this.game.level;
            this.modeSpecificStats.tspins++;
        }
        
        // Perfect clear bonus
        if (specialClear && specialClear.type === 'perfectClear') {
            score += 1000 * this.game.level;
        }
        
        this.game.score += score;
        this.modeSpecificStats.totalScore = this.game.score;
        
        // Level progression - every 10 lines
        const newLevel = Math.floor(this.game.lines / 10) + 1;
        if (newLevel > this.game.level) {
            this.game.level = newLevel;
            if (newLevel > this.modeSpecificStats.maxLevel) {
                this.modeSpecificStats.maxLevel = newLevel;
            }
            this.updateGameSpeed();
            
            // Update music tempo
            if (this.game.audioManager) {
                this.game.audioManager.setGameLevel(this.game.level);
            }
        }
    }

    updateGameSpeed() {
        // Progressive speed increase
        const baseInterval = 800;
        const speedMultiplier = Math.pow(0.92, this.game.level - 1);
        this.game.dropInterval = Math.max(50, baseInterval * speedMultiplier);
    }

    checkCheckpoint() {
        for (let i = 0; i < this.checkpoints.length; i++) {
            const checkpoint = this.checkpoints[i];
            if (this.game.lines >= checkpoint && this.lastCheckpoint < checkpoint) {
                this.lastCheckpoint = checkpoint;
                this.modeSpecificStats.checkpointsReached++;
                this.modeSpecificStats.nextCheckpoint = 
                    i < this.checkpoints.length - 1 ? this.checkpoints[i + 1] : this.targetLines;
                
                // Show checkpoint message
                if (this.game.uiManager) {
                    this.game.uiManager.showMessage(`Checkpoint ${checkpoint} lines reached!`);
                }
                
                // Play checkpoint sound
                if (this.game.audioManager) {
                    this.game.audioManager.playSFX('levelUp');
                }
                
                return true;
            }
        }
        return false;
    }

    shouldAutoSave() {
        // Auto-save every 10 lines
        return this.game.lines > 0 && this.game.lines % 10 === 0;
    }

    autoSave() {
        this.savedState = this.saveState();
        localStorage.setItem('tetris_marathon_saved', JSON.stringify(this.savedState));
    }

    loadSavedGame() {
        const saved = localStorage.getItem('tetris_marathon_saved');
        if (saved) {
            try {
                const state = JSON.parse(saved);
                // Ask user if they want to continue
                if (this.game.uiManager) {
                    this.game.uiManager.showContinuePrompt(state, (continueGame) => {
                        if (continueGame) {
                            this.loadState(state);
                        } else {
                            localStorage.removeItem('tetris_marathon_saved');
                        }
                    });
                }
            } catch (e) {
                console.error('Failed to load saved game:', e);
            }
        }
    }

    getObjective() {
        const checkpoint = this.modeSpecificStats.nextCheckpoint;
        const linesToCheckpoint = checkpoint - this.game.lines;
        
        if (this.game.lines >= this.targetLines) {
            return 'Marathon Complete!';
        } else if (linesToCheckpoint > 0) {
            return `${linesToCheckpoint} lines to checkpoint ${checkpoint}`;
        }
        return `Clear ${this.modeSpecificStats.linesRemaining} more lines!`;
    }

    getModeUI() {
        return {
            showScore: true,
            showLines: true,
            showLevel: true,
            showHold: true,
            showNext: true,
            showTimer: false,
            showObjective: true,
            customDisplay: {
                checkpoint: `${this.lastCheckpoint}/${this.targetLines}`,
                progress: `${Math.floor(this.getProgress())}%`
            }
        };
    }

    getProgress() {
        return (this.game.lines / this.targetLines) * 100;
    }

    shouldEnd() {
        return this.game.lines >= this.targetLines;
    }

    getMusicTempo() {
        // Tempo increases with level
        return 1 + (this.game.level - 1) * 0.03;
    }

    handleVictory() {
        const result = super.handleVictory();
        
        result.finalScore = this.game.score;
        result.finalLevel = this.game.level;
        result.checkpointsReached = this.modeSpecificStats.checkpointsReached;
        result.maxCombo = this.modeSpecificStats.maxCombo;
        result.tspins = this.modeSpecificStats.tspins;
        result.tetris = this.modeSpecificStats.tetris;
        
        // Clear saved game
        localStorage.removeItem('tetris_marathon_saved');
        
        // Play victory sound
        if (this.game.audioManager) {
            this.game.audioManager.playSFX('victory');
        }
        
        return result;
    }

    handleGameOver() {
        const result = super.handleGameOver();
        
        result.linesCleared = this.game.lines;
        result.linesRemaining = this.targetLines - this.game.lines;
        result.checkpointsReached = this.modeSpecificStats.checkpointsReached;
        result.finalScore = this.game.score;
        result.finalLevel = this.game.level;
        
        // Clear saved game
        localStorage.removeItem('tetris_marathon_saved');
        
        return result;
    }

    saveState() {
        const baseState = super.saveState();
        return {
            ...baseState,
            lines: this.game.lines,
            score: this.game.score,
            level: this.game.level,
            combo: this.game.combo,
            lastCheckpoint: this.lastCheckpoint,
            dropInterval: this.game.dropInterval,
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
        this.game.combo = state.combo;
        this.lastCheckpoint = state.lastCheckpoint;
        this.game.dropInterval = state.dropInterval;
        
        // Restore grid state
        if (state.grid && this.game.grid) {
            this.game.grid.loadState(state.grid);
        }
        
        // Restore pieces
        if (state.currentPiece && this.game.currentPiece) {
            this.game.currentPiece.loadState(state.currentPiece);
        }
        if (state.heldPiece && this.game.heldPiece) {
            this.game.heldPiece.loadState(state.heldPiece);
        }
        if (state.nextPieces) {
            this.game.nextPieces = state.nextPieces;
        }
        
        // Update stats
        this.modeSpecificStats.linesRemaining = this.targetLines - this.game.lines;
        for (let i = 0; i < this.checkpoints.length; i++) {
            if (this.checkpoints[i] > this.game.lines) {
                this.modeSpecificStats.nextCheckpoint = this.checkpoints[i];
                break;
            }
        }
    }

    pause() {
        super.pause();
        // Auto-save when pausing
        this.autoSave();
    }

    getLeaderboardCategory() {
        return 'marathon';
    }

    supportsSaving() {
        return true; // Marathon supports mid-game saving
    }

    getIcon() {
        return '🏃';
    }

    getThemeColor() {
        return '#ffff00';
    }
}