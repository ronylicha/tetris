// Mode Selector - Manages game mode selection and initialization
import { ClassicMode } from './modes/classicMode.js';
import { SprintMode } from './modes/sprintMode.js';
import { MarathonMode } from './modes/marathonMode.js';
import { ZenMode } from './modes/zenMode.js';
import { PuzzleMode } from './modes/puzzleMode.js';
import { BattleMode } from './modes/battleMode.js';
import { PowerUpMode } from './modes/powerUpMode.js';
import { Battle2PMode } from './modes/battle2PMode.js';
import { DailyChallengeMode } from './modes/dailyChallengeMode.js';

export class ModeSelector {
    constructor() {
        this.availableModes = {
            classic: {
                name: 'Classic',
                class: ClassicMode,
                icon: '🎮',
                color: '#00ffff',
                description: 'The original endless Tetris experience',
                unlocked: true
            },
            powerup: {
                name: 'Power-Up',
                class: PowerUpMode,
                icon: '⚡',
                color: '#ff00ff',
                description: 'Classic with exciting power-ups!',
                unlocked: true
            },
            sprint: {
                name: 'Sprint',
                class: SprintMode,
                icon: '⏱️',
                color: '#ff00ff',
                description: 'Clear 40 lines as fast as possible',
                unlocked: true
            },
            marathon: {
                name: 'Marathon',
                class: MarathonMode,
                icon: '🏃',
                color: '#ffff00',
                description: 'Survive 150 lines with increasing difficulty',
                unlocked: true
            },
            zen: {
                name: 'Zen',
                class: ZenMode,
                icon: '🧘',
                color: '#00ff00',
                description: 'Relaxing endless mode with no pressure',
                unlocked: true
            },
            puzzle: {
                name: 'Puzzle',
                class: PuzzleMode,
                icon: '🧩',
                color: '#ff8800',
                description: 'Solve 150 unique challenges',
                unlocked: true
            },
            battle: {
                name: 'Battle',
                class: BattleMode,
                icon: '⚔️',
                color: '#ff0000',
                description: 'Face off against intelligent AI opponents',
                unlocked: true
            },
            battle2p: {
                name: 'Battle 2P',
                class: Battle2PMode,
                icon: '👥',
                color: '#ff00ff',
                description: 'Local 2-player split-screen battle',
                unlocked: true,
                desktopOnly: true // Flag for desktop-only mode
            },
            daily: {
                name: 'Daily Challenge',
                class: DailyChallengeMode,
                icon: '📅',
                color: '#ffd700',
                description: 'New challenge every day with unique modifiers',
                unlocked: true
            }
        };
        
        this.currentMode = 'classic';
        this.modeInstance = null;
    }

    // Get all available modes
    getModes() {
        // Check if we're on mobile/tablet
        const isMobile = window.innerWidth < 768 || 'ontouchstart' in window;
        
        return Object.entries(this.availableModes)
            .filter(([key, mode]) => {
                // Filter out desktop-only modes on mobile
                if (isMobile && mode.desktopOnly) {
                    return false;
                }
                return true;
            })
            .map(([key, mode]) => ({
                id: key,
                ...mode
            }));
    }

    // Get unlocked modes only
    getUnlockedModes() {
        return this.getModes().filter(mode => mode.unlocked);
    }

    // Select a game mode
    selectMode(modeId) {
        if (!this.availableModes[modeId]) {
            console.error(`Mode ${modeId} not found`);
            return null;
        }
        
        if (!this.availableModes[modeId].unlocked) {
            console.warn(`Mode ${modeId} is locked`);
            return null;
        }
        
        this.currentMode = modeId;
        return this.availableModes[modeId];
    }

    // Create mode instance
    createModeInstance(modeId, game) {
        const mode = this.selectMode(modeId);
        if (!mode) {
            // Default to classic if mode not found
            modeId = 'classic';
        }
        
        const ModeClass = this.availableModes[modeId].class;
        this.modeInstance = new ModeClass(game);
        return this.modeInstance;
    }

    // Get current mode instance
    getCurrentMode() {
        return this.modeInstance;
    }

    // Get mode info
    getModeInfo(modeId) {
        return this.availableModes[modeId] || null;
    }

    // Check if mode is unlocked
    isModeUnlocked(modeId) {
        return this.availableModes[modeId] && this.availableModes[modeId].unlocked;
    }

    // Unlock a mode
    unlockMode(modeId) {
        if (this.availableModes[modeId]) {
            this.availableModes[modeId].unlocked = true;
            this.saveModeProgress();
        }
    }

    // Save mode progress to localStorage
    saveModeProgress() {
        const progress = {};
        Object.keys(this.availableModes).forEach(key => {
            progress[key] = this.availableModes[key].unlocked;
        });
        localStorage.setItem('tetris_mode_progress', JSON.stringify(progress));
    }

    // Load mode progress from localStorage
    loadModeProgress() {
        const saved = localStorage.getItem('tetris_mode_progress');
        if (saved) {
            try {
                const progress = JSON.parse(saved);
                Object.keys(progress).forEach(key => {
                    if (this.availableModes[key]) {
                        this.availableModes[key].unlocked = progress[key];
                    }
                });
            } catch (e) {
                console.error('Failed to load mode progress:', e);
            }
        }
    }

    // Get mode statistics
    getModeStatistics(modeId) {
        const key = `tetris_stats_${modeId}`;
        const saved = localStorage.getItem(key);
        if (saved) {
            try {
                return JSON.parse(saved);
            } catch (e) {
                console.error('Failed to load mode statistics:', e);
            }
        }
        return null;
    }

    // Save mode statistics
    saveModeStatistics(modeId, stats) {
        const key = `tetris_stats_${modeId}`;
        localStorage.setItem(key, JSON.stringify(stats));
    }

    // Get best scores for a mode
    getBestScores(modeId, limit = 10) {
        const key = `tetris_scores_${modeId}`;
        const saved = localStorage.getItem(key);
        if (saved) {
            try {
                const scores = JSON.parse(saved);
                return scores.slice(0, limit);
            } catch (e) {
                console.error('Failed to load mode scores:', e);
            }
        }
        return [];
    }

    // Add score to mode leaderboard
    addScore(modeId, scoreData) {
        const key = `tetris_scores_${modeId}`;
        let scores = [];
        
        const saved = localStorage.getItem(key);
        if (saved) {
            try {
                scores = JSON.parse(saved);
            } catch (e) {
                console.error('Failed to load existing scores:', e);
            }
        }
        
        // Add new score with timestamp
        scores.push({
            ...scoreData,
            timestamp: Date.now()
        });
        
        // Sort by score (or time for sprint mode)
        if (modeId === 'sprint') {
            scores.sort((a, b) => a.time - b.time);
        } else {
            scores.sort((a, b) => b.score - a.score);
        }
        
        // Keep only top 100 scores
        scores = scores.slice(0, 100);
        
        localStorage.setItem(key, JSON.stringify(scores));
        return scores;
    }

    // Reset mode progress
    resetModeProgress(modeId) {
        if (modeId) {
            // Reset specific mode
            localStorage.removeItem(`tetris_stats_${modeId}`);
            localStorage.removeItem(`tetris_scores_${modeId}`);
        } else {
            // Reset all modes
            Object.keys(this.availableModes).forEach(key => {
                localStorage.removeItem(`tetris_stats_${key}`);
                localStorage.removeItem(`tetris_scores_${key}`);
            });
            localStorage.removeItem('tetris_mode_progress');
        }
    }

    // Get mode thumbnail for UI
    getModeThumbnail(modeId) {
        const mode = this.availableModes[modeId];
        if (!mode) return null;
        
        return {
            icon: mode.icon,
            color: mode.color,
            name: mode.name,
            description: mode.description,
            unlocked: mode.unlocked
        };
    }
}