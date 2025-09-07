// Abstract base class for all game modes
export class GameMode {
    constructor(game) {
        this.game = game;
        this.name = 'Base Mode';
        this.description = 'Base game mode';
        this.objectives = [];
        this.modeSpecificStats = {};
        this.isComplete = false;
        this.isPaused = false;
        this.startTime = null;
        this.endTime = null;
    }

    // Abstract methods that must be implemented by subclasses
    initialize() {
        throw new Error('initialize() must be implemented by subclass');
    }

    update(deltaTime) {
        throw new Error('update() must be implemented by subclass');
    }

    handleLineClears(linesCleared, specialClear) {
        throw new Error('handleLineClears() must be implemented by subclass');
    }

    getObjective() {
        throw new Error('getObjective() must be implemented by subclass');
    }

    getModeUI() {
        throw new Error('getModeUI() must be implemented by subclass');
    }

    // Common methods that can be overridden if needed
    start() {
        this.startTime = Date.now();
        this.isComplete = false;
        this.isPaused = false;
        this.initialize();
    }

    pause() {
        this.isPaused = true;
    }

    resume() {
        this.isPaused = false;
    }

    end(won = false) {
        this.endTime = Date.now();
        this.isComplete = true;
        const duration = this.endTime - this.startTime;
        return {
            mode: this.name,
            won: won,
            duration: duration,
            stats: this.getModeStats()
        };
    }

    getModeStats() {
        return {
            ...this.modeSpecificStats,
            duration: this.endTime ? this.endTime - this.startTime : Date.now() - this.startTime
        };
    }

    // Save and load state for offline support
    saveState() {
        return {
            name: this.name,
            startTime: this.startTime,
            modeSpecificStats: this.modeSpecificStats,
            isComplete: this.isComplete,
            isPaused: this.isPaused
        };
    }

    loadState(state) {
        this.startTime = state.startTime;
        this.modeSpecificStats = state.modeSpecificStats;
        this.isComplete = state.isComplete;
        this.isPaused = state.isPaused;
    }

    // Check if mode has special rules
    hasSpecialRules() {
        return false;
    }

    getSpecialRules() {
        return {};
    }

    // Handle mode-specific input
    handleInput(action) {
        // Override in subclasses if needed
        return false;
    }

    // Get current progress percentage
    getProgress() {
        return 0;
    }

    // Check if the mode should end
    shouldEnd() {
        return false;
    }

    // Get mode-specific scoring multiplier
    getScoreMultiplier() {
        return 1;
    }

    // Get mode-specific speed level
    getSpeedLevel() {
        return this.game.level;
    }

    // Check if hold piece is allowed
    isHoldAllowed() {
        return true;
    }

    // Check if ghost piece should be shown
    showGhostPiece() {
        return true;
    }

    // Get mode-specific background music tempo
    getMusicTempo() {
        return 1;
    }

    // Handle game over condition
    handleGameOver() {
        return this.end(false);
    }

    // Handle victory condition
    handleVictory() {
        return this.end(true);
    }

    // Render mode-specific UI elements
    renderModeSpecificUI(ctx, canvas) {
        // Override in subclasses to render custom UI
    }

    // Get leaderboard category for this mode
    getLeaderboardCategory() {
        return this.name.toLowerCase().replace(/\s+/g, '_');
    }

    // Check if mode supports saving mid-game
    supportsSaving() {
        return false;
    }

    // Get mode icon for UI
    getIcon() {
        return '🎮';
    }

    // Get mode color theme
    getThemeColor() {
        return 'var(--neon-blue)';
    }
}