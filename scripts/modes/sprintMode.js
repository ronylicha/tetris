// Sprint Mode - Clear 40 lines as fast as possible
import { GameMode } from './gameMode.js';

export class SprintMode extends GameMode {
    constructor(game) {
        super(game);
        this.name = 'Sprint';
        this.description = 'Clear 40 lines as fast as possible!';
        this.icon = '⏱️';
        this.themeColor = '#ff00ff';
        
        this.targetLines = 40;
        this.timerInterval = null;
        this.elapsedTime = 0;
        this.bestTime = this.loadBestTime();
    }

    initialize() {
        // Sprint mode configuration
        this.game.level = 1; // Fixed level for fair competition
        this.game.lines = 0;
        this.game.score = 0;
        this.game.combo = 0;
        
        // Fixed speed for sprint mode
        this.game.dropInterval = 500; // Medium speed
        
        this.modeSpecificStats = {
            targetLines: this.targetLines,
            linesRemaining: this.targetLines,
            elapsedTime: 0,
            piecesUsed: 0,
            efficiency: 0,
            maxCombo: 0
        };
        
        // Start the timer
        this.startTimer();
    }

    startTimer() {
        this.elapsedTime = 0;
        this.timerInterval = setInterval(() => {
            if (!this.isPaused && !this.isComplete) {
                this.elapsedTime += 10; // Update every 10ms for precision
                this.modeSpecificStats.elapsedTime = this.elapsedTime;
            }
        }, 10);
    }

    stopTimer() {
        if (this.timerInterval) {
            clearInterval(this.timerInterval);
            this.timerInterval = null;
        }
    }

    update(deltaTime) {
        // Check if target reached
        if (this.game.lines >= this.targetLines && !this.isComplete) {
            this.handleVictory();
            return false; // End the game
        }
        
        // Update remaining lines
        this.modeSpecificStats.linesRemaining = Math.max(0, this.targetLines - this.game.lines);
        
        // Update efficiency (lines per piece)
        if (this.modeSpecificStats.piecesUsed > 0) {
            this.modeSpecificStats.efficiency = 
                (this.game.lines / this.modeSpecificStats.piecesUsed * 100).toFixed(1);
        }
        
        return true;
    }

    handleLineClears(linesCleared, specialClear) {
        if (!linesCleared || linesCleared === 0) return;
        
        // Update lines
        this.game.lines += linesCleared;
        
        // Update combo
        this.game.combo++;
        if (this.game.combo > this.modeSpecificStats.maxCombo) {
            this.modeSpecificStats.maxCombo = this.game.combo;
        }
        
        // Calculate score (minimal in sprint, focus is on time)
        let score = linesCleared * 100;
        if (specialClear && specialClear.type === 'tspin') {
            score += 200;
        }
        this.game.score += score;
        
        // Play sound effect
        if (this.game.audioManager) {
            this.game.audioManager.playSFX('lineClear');
            
            // Special sound for getting close to goal
            if (this.game.lines >= this.targetLines - 5) {
                this.game.audioManager.playSFX('combo');
            }
        }
    }

    handlePiecePlaced() {
        this.modeSpecificStats.piecesUsed++;
    }

    getObjective() {
        const remaining = this.targetLines - this.game.lines;
        if (remaining > 0) {
            return `Clear ${remaining} more lines!`;
        }
        return 'Sprint Complete!';
    }

    getModeUI() {
        return {
            showScore: false, // Score not important in sprint
            showLines: true,
            showLevel: false, // Fixed level
            showHold: true,
            showNext: true,
            showTimer: true,
            showObjective: true,
            customDisplay: {
                timer: this.formatTime(this.elapsedTime),
                bestTime: this.bestTime ? this.formatTime(this.bestTime) : '--:--:--',
                linesRemaining: this.modeSpecificStats.linesRemaining,
                efficiency: `${this.modeSpecificStats.efficiency}%`
            }
        };
    }

    formatTime(milliseconds) {
        const totalSeconds = Math.floor(milliseconds / 1000);
        const minutes = Math.floor(totalSeconds / 60);
        const seconds = totalSeconds % 60;
        const ms = Math.floor((milliseconds % 1000) / 10);
        
        return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}.${ms.toString().padStart(2, '0')}`;
    }

    pause() {
        super.pause();
        // Timer is paused through isPaused flag
    }

    resume() {
        super.resume();
        // Timer resumes automatically
    }

    getProgress() {
        return (this.game.lines / this.targetLines) * 100;
    }

    shouldEnd() {
        return this.game.lines >= this.targetLines;
    }

    getMusicTempo() {
        // Faster tempo as approaching goal
        const progress = this.game.lines / this.targetLines;
        return 1 + progress * 0.3; // Up to 30% faster near the end
    }

    handleVictory() {
        this.stopTimer();
        const result = super.handleVictory();
        
        // Check if new record
        const isNewRecord = !this.bestTime || this.elapsedTime < this.bestTime;
        if (isNewRecord) {
            this.saveBestTime(this.elapsedTime);
        }
        
        result.time = this.elapsedTime;
        result.formattedTime = this.formatTime(this.elapsedTime);
        result.isNewRecord = isNewRecord;
        result.piecesUsed = this.modeSpecificStats.piecesUsed;
        result.efficiency = this.modeSpecificStats.efficiency;
        result.maxCombo = this.modeSpecificStats.maxCombo;
        
        // Play victory sound
        if (this.game.audioManager) {
            this.game.audioManager.playSFX('levelUp');
        }
        
        return result;
    }

    handleGameOver() {
        this.stopTimer();
        const result = super.handleGameOver();
        
        result.time = this.elapsedTime;
        result.formattedTime = this.formatTime(this.elapsedTime);
        result.linesCleared = this.game.lines;
        result.linesRemaining = this.targetLines - this.game.lines;
        
        return result;
    }

    loadBestTime() {
        const saved = localStorage.getItem('tetris_sprint_best_time');
        return saved ? parseInt(saved) : null;
    }

    saveBestTime(time) {
        this.bestTime = time;
        localStorage.setItem('tetris_sprint_best_time', time.toString());
    }

    saveState() {
        const baseState = super.saveState();
        return {
            ...baseState,
            lines: this.game.lines,
            elapsedTime: this.elapsedTime,
            piecesUsed: this.modeSpecificStats.piecesUsed
        };
    }

    loadState(state) {
        super.loadState(state);
        this.game.lines = state.lines;
        this.elapsedTime = state.elapsedTime;
        this.modeSpecificStats.piecesUsed = state.piecesUsed;
        this.modeSpecificStats.linesRemaining = this.targetLines - this.game.lines;
        
        // Restart timer from saved time
        this.startTimer();
    }

    cleanup() {
        this.stopTimer();
    }

    getLeaderboardCategory() {
        return 'sprint';
    }

    supportsSaving() {
        return false; // Sprint is a time attack, no mid-game saving
    }

    getIcon() {
        return '⏱️';
    }

    getThemeColor() {
        return '#ff00ff';
    }
}