// Global Player Progression System
import { storage } from '../storage-adapter.js';

export class PlayerProgression {
    constructor() {
        this.level = 1;
        this.xp = 0;
        this.totalXP = 0;
        this.rank = 'Novice';
        this.stats = {
            gamesPlayed: 0,
            totalScore: 0,
            totalLines: 0,
            totalTime: 0,
            favoriteMode: null,
            achievements: [],
            trophies: [],
            puzzlesCompleted: 0,
            dailyChallengesCompleted: 0,
            highestCombo: 0,
            totalTSpins: 0,
            totalTetris: 0,
            perfectClears: 0
        };
        this.unlocks = {
            themes: ['default'],
            music: ['classic'],
            backgrounds: ['space'],
            pieceStyles: ['neon'],
            effects: ['basic']
        };
        this.badges = [];
        this.loadProgress();
    }
    
    async loadProgress() {
        const saved = await storage.load('player_progression');
        if (saved) {
            Object.assign(this, saved);
        }
    }
    
    async saveProgress() {
        await storage.save('player_progression', {
            level: this.level,
            xp: this.xp,
            totalXP: this.totalXP,
            rank: this.rank,
            stats: this.stats,
            unlocks: this.unlocks,
            badges: this.badges
        });
    }
    
    addXP(amount, source = 'gameplay') {
        this.xp += amount;
        this.totalXP += amount;
        
        // Check for level up
        const requiredXP = this.getRequiredXP();
        while (this.xp >= requiredXP) {
            this.levelUp();
        }
        
        // Update rank
        this.updateRank();
        
        // Save progress
        this.saveProgress();
        
        return {
            xpGained: amount,
            currentXP: this.xp,
            level: this.level,
            rank: this.rank,
            source: source
        };
    }
    
    getRequiredXP() {
        // XP required for next level (exponential curve)
        return Math.floor(100 * Math.pow(1.5, this.level - 1));
    }
    
    levelUp() {
        const requiredXP = this.getRequiredXP();
        this.xp -= requiredXP;
        this.level++;
        
        // Check for unlocks
        const newUnlocks = this.checkUnlocks();
        
        // Trigger level up event
        this.onLevelUp(newUnlocks);
        
        return {
            newLevel: this.level,
            unlocks: newUnlocks
        };
    }
    
    updateRank() {
        const ranks = [
            { level: 1, name: 'Novice' },
            { level: 5, name: 'Apprentice' },
            { level: 10, name: 'Adept' },
            { level: 20, name: 'Expert' },
            { level: 30, name: 'Master' },
            { level: 40, name: 'Grandmaster' },
            { level: 50, name: 'Champion' },
            { level: 60, name: 'Legend' },
            { level: 75, name: 'Mythic' },
            { level: 100, name: 'Eternal' }
        ];
        
        for (let i = ranks.length - 1; i >= 0; i--) {
            if (this.level >= ranks[i].level) {
                this.rank = ranks[i].name;
                break;
            }
        }
    }
    
    checkUnlocks() {
        const unlocks = [];
        
        // Theme unlocks
        const themeUnlocks = [
            { level: 5, theme: 'cyberpunk', name: 'Cyberpunk Theme' },
            { level: 10, theme: 'retro', name: 'Retro Theme' },
            { level: 15, theme: 'nature', name: 'Nature Theme' },
            { level: 20, theme: 'minimal', name: 'Minimal Theme' },
            { level: 30, theme: 'galaxy', name: 'Galaxy Theme' },
            { level: 40, theme: 'matrix', name: 'Matrix Theme' },
            { level: 50, theme: 'rainbow', name: 'Rainbow Theme' }
        ];
        
        themeUnlocks.forEach(unlock => {
            if (this.level === unlock.level && !this.unlocks.themes.includes(unlock.theme)) {
                this.unlocks.themes.push(unlock.theme);
                unlocks.push({ type: 'theme', id: unlock.theme, name: unlock.name });
            }
        });
        
        // Music unlocks
        const musicUnlocks = [
            { level: 3, track: 'chiptune', name: 'Chiptune Music' },
            { level: 8, track: 'synthwave', name: 'Synthwave Music' },
            { level: 12, track: 'orchestral', name: 'Orchestral Music' },
            { level: 18, track: 'jazz', name: 'Jazz Remix' },
            { level: 25, track: 'metal', name: 'Metal Version' },
            { level: 35, track: 'lofi', name: 'Lo-Fi Beats' }
        ];
        
        musicUnlocks.forEach(unlock => {
            if (this.level === unlock.level && !this.unlocks.music.includes(unlock.track)) {
                this.unlocks.music.push(unlock.track);
                unlocks.push({ type: 'music', id: unlock.track, name: unlock.name });
            }
        });
        
        // Piece style unlocks
        const pieceUnlocks = [
            { level: 7, style: 'glass', name: 'Glass Pieces' },
            { level: 14, style: 'pixel', name: 'Pixel Art Pieces' },
            { level: 22, style: 'hologram', name: 'Hologram Pieces' },
            { level: 28, style: 'crystal', name: 'Crystal Pieces' },
            { level: 45, style: 'animated', name: 'Animated Pieces' }
        ];
        
        pieceUnlocks.forEach(unlock => {
            if (this.level === unlock.level && !this.unlocks.pieceStyles.includes(unlock.style)) {
                this.unlocks.pieceStyles.push(unlock.style);
                unlocks.push({ type: 'pieceStyle', id: unlock.style, name: unlock.name });
            }
        });
        
        // Effect unlocks
        const effectUnlocks = [
            { level: 6, effect: 'particles', name: 'Particle Effects' },
            { level: 11, effect: 'trails', name: 'Piece Trails' },
            { level: 16, effect: 'explosions', name: 'Line Clear Explosions' },
            { level: 24, effect: 'lightning', name: 'Lightning Effects' },
            { level: 32, effect: 'shatter', name: 'Shatter Effects' },
            { level: 55, effect: 'quantum', name: 'Quantum Effects' }
        ];
        
        effectUnlocks.forEach(unlock => {
            if (this.level === unlock.level && !this.unlocks.effects.includes(unlock.effect)) {
                this.unlocks.effects.push(unlock.effect);
                unlocks.push({ type: 'effect', id: unlock.effect, name: unlock.name });
            }
        });
        
        return unlocks;
    }
    
    onLevelUp(unlocks) {
        // Create level up notification
        const notification = {
            type: 'levelUp',
            level: this.level,
            rank: this.rank,
            unlocks: unlocks,
            timestamp: Date.now()
        };
        
        // Dispatch event
        window.dispatchEvent(new CustomEvent('playerLevelUp', { detail: notification }));
        
        // Show UI notification
        if (window.game && window.game.uiManager) {
            window.game.uiManager.showLevelUp(this.level, this.rank, unlocks);
        }
    }
    
    updateStats(gameResults) {
        this.stats.gamesPlayed++;
        this.stats.totalScore += gameResults.score || 0;
        this.stats.totalLines += gameResults.lines || 0;
        this.stats.totalTime += gameResults.time || 0;
        
        if (gameResults.combo > this.stats.highestCombo) {
            this.stats.highestCombo = gameResults.combo;
        }
        
        this.stats.totalTSpins += gameResults.tspins || 0;
        this.stats.totalTetris += gameResults.tetris || 0;
        
        if (gameResults.perfectClear) {
            this.stats.perfectClears++;
        }
        
        // Update favorite mode
        if (gameResults.mode) {
            if (!this.stats.modeCount) {
                this.stats.modeCount = {};
            }
            this.stats.modeCount[gameResults.mode] = (this.stats.modeCount[gameResults.mode] || 0) + 1;
            
            // Find most played mode
            let maxCount = 0;
            let favoriteMode = null;
            for (const [mode, count] of Object.entries(this.stats.modeCount)) {
                if (count > maxCount) {
                    maxCount = count;
                    favoriteMode = mode;
                }
            }
            this.stats.favoriteMode = favoriteMode;
        }
        
        this.saveProgress();
    }
    
    calculateGameXP(gameResults) {
        let xp = 0;
        
        // Base XP from score
        xp += Math.floor(gameResults.score / 100);
        
        // Lines cleared
        xp += gameResults.lines * 10;
        
        // Special moves
        xp += gameResults.tspins * 50;
        xp += gameResults.tetris * 30;
        xp += gameResults.perfectClears * 100;
        
        // Combo bonus
        if (gameResults.combo >= 10) xp += 100;
        if (gameResults.combo >= 20) xp += 200;
        
        // Mode multipliers
        const modeMultipliers = {
            classic: 1.0,
            sprint: 1.2,
            marathon: 1.5,
            puzzle: 1.3,
            battle: 1.4,
            powerup: 1.1,
            zen: 0.8
        };
        
        const multiplier = modeMultipliers[gameResults.mode] || 1.0;
        xp = Math.floor(xp * multiplier);
        
        // First win of the day bonus
        if (this.isFirstWinOfDay()) {
            xp *= 2;
        }
        
        return xp;
    }
    
    isFirstWinOfDay() {
        const today = new Date().toISOString().split('T')[0];
        const lastWin = this.stats.lastWinDate;
        
        if (lastWin !== today) {
            this.stats.lastWinDate = today;
            return true;
        }
        return false;
    }
    
    addBadge(badge) {
        if (!this.badges.find(b => b.id === badge.id)) {
            this.badges.push({
                ...badge,
                unlockedAt: Date.now()
            });
            this.saveProgress();
            return true;
        }
        return false;
    }
    
    getProgressToNextLevel() {
        const required = this.getRequiredXP();
        return {
            current: this.xp,
            required: required,
            percentage: Math.floor((this.xp / required) * 100)
        };
    }
    
    getPlayerCard() {
        return {
            name: this.getPlayerName(),
            level: this.level,
            rank: this.rank,
            totalXP: this.totalXP,
            badges: this.badges.length,
            stats: {
                games: this.stats.gamesPlayed,
                avgScore: Math.floor(this.stats.totalScore / Math.max(1, this.stats.gamesPlayed)),
                favoriteMode: this.stats.favoriteMode,
                achievements: this.stats.achievements.length
            },
            unlocks: {
                themes: this.unlocks.themes.length,
                music: this.unlocks.music.length,
                effects: this.unlocks.effects.length
            }
        };
    }
    
    getPlayerName() {
        // Get from settings or use default
        return localStorage.getItem('tetris_player_name') || 'Player';
    }
    
    reset() {
        this.level = 1;
        this.xp = 0;
        this.totalXP = 0;
        this.rank = 'Novice';
        this.stats = {
            gamesPlayed: 0,
            totalScore: 0,
            totalLines: 0,
            totalTime: 0,
            favoriteMode: null,
            achievements: [],
            trophies: [],
            puzzlesCompleted: 0,
            dailyChallengesCompleted: 0,
            highestCombo: 0,
            totalTSpins: 0,
            totalTetris: 0,
            perfectClears: 0
        };
        this.unlocks = {
            themes: ['default'],
            music: ['classic'],
            backgrounds: ['space'],
            pieceStyles: ['neon'],
            effects: ['basic']
        };
        this.badges = [];
        this.saveProgress();
    }
}

export const playerProgression = new PlayerProgression();