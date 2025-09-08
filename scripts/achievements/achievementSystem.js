// Achievement and Trophy System
import { storage } from '../storage-adapter.js';
import { playerProgression } from '../progression/playerProgression.js';

export const ACHIEVEMENTS = {
    // Beginner Achievements
    FIRST_LINE: {
        id: 'first_line',
        name: 'First Step',
        description: 'Clear your first line',
        icon: '🎯',
        xp: 10,
        category: 'beginner'
    },
    FIRST_TETRIS: {
        id: 'first_tetris',
        name: 'Tetris!',
        description: 'Clear 4 lines at once',
        icon: '⚡',
        xp: 50,
        category: 'beginner'
    },
    FIRST_TSPIN: {
        id: 'first_tspin',
        name: 'Spin Master',
        description: 'Perform your first T-Spin',
        icon: '🌀',
        xp: 100,
        category: 'beginner'
    },
    
    // Line Clearing Achievements
    LINES_100: {
        id: 'lines_100',
        name: 'Century',
        description: 'Clear 100 lines total',
        icon: '💯',
        xp: 100,
        category: 'lines'
    },
    LINES_1000: {
        id: 'lines_1000',
        name: 'Millennium',
        description: 'Clear 1000 lines total',
        icon: '🏆',
        xp: 500,
        category: 'lines'
    },
    LINES_10000: {
        id: 'lines_10000',
        name: 'Line Legend',
        description: 'Clear 10000 lines total',
        icon: '👑',
        xp: 2000,
        category: 'lines'
    },
    
    // Score Achievements
    SCORE_10K: {
        id: 'score_10k',
        name: 'Five Figures',
        description: 'Score 10,000 points in a single game',
        icon: '📈',
        xp: 50,
        category: 'score'
    },
    SCORE_100K: {
        id: 'score_100k',
        name: 'High Scorer',
        description: 'Score 100,000 points in a single game',
        icon: '🎰',
        xp: 200,
        category: 'score'
    },
    SCORE_1M: {
        id: 'score_1m',
        name: 'Millionaire',
        description: 'Score 1,000,000 points in a single game',
        icon: '💰',
        xp: 1000,
        category: 'score'
    },
    
    // Combo Achievements
    COMBO_5: {
        id: 'combo_5',
        name: 'Combo Starter',
        description: 'Achieve a 5x combo',
        icon: '🔥',
        xp: 30,
        category: 'combo'
    },
    COMBO_10: {
        id: 'combo_10',
        name: 'Combo Expert',
        description: 'Achieve a 10x combo',
        icon: '💥',
        xp: 100,
        category: 'combo'
    },
    COMBO_20: {
        id: 'combo_20',
        name: 'Combo God',
        description: 'Achieve a 20x combo',
        icon: '🌟',
        xp: 500,
        category: 'combo'
    },
    
    // Perfect Clear Achievements
    PERFECT_CLEAR: {
        id: 'perfect_clear',
        name: 'Perfectionist',
        description: 'Clear the entire board',
        icon: '✨',
        xp: 200,
        category: 'special'
    },
    PERFECT_CLEAR_5: {
        id: 'perfect_clear_5',
        name: 'Perfect Practice',
        description: 'Achieve 5 perfect clears total',
        icon: '🌈',
        xp: 500,
        category: 'special'
    },
    
    // T-Spin Achievements
    TSPIN_DOUBLE: {
        id: 'tspin_double',
        name: 'Double Spin',
        description: 'Perform a T-Spin Double',
        icon: '🔄',
        xp: 150,
        category: 'special'
    },
    TSPIN_TRIPLE: {
        id: 'tspin_triple',
        name: 'Triple Threat',
        description: 'Perform a T-Spin Triple',
        icon: '🌪️',
        xp: 300,
        category: 'special'
    },
    TSPIN_MASTER: {
        id: 'tspin_master',
        name: 'Spin Doctor',
        description: 'Perform 100 T-Spins total',
        icon: '🎭',
        xp: 1000,
        category: 'special'
    },
    
    // Mode-Specific Achievements
    SPRINT_SUB60: {
        id: 'sprint_sub60',
        name: 'Speed Demon',
        description: 'Complete Sprint mode in under 60 seconds',
        icon: '⏱️',
        xp: 300,
        category: 'modes'
    },
    MARATHON_COMPLETE: {
        id: 'marathon_complete',
        name: 'Marathon Runner',
        description: 'Complete Marathon mode (150 lines)',
        icon: '🏃',
        xp: 500,
        category: 'modes'
    },
    PUZZLE_50: {
        id: 'puzzle_50',
        name: 'Puzzle Solver',
        description: 'Complete 50 puzzles',
        icon: '🧩',
        xp: 400,
        category: 'modes'
    },
    PUZZLE_ALL: {
        id: 'puzzle_all',
        name: 'Puzzle Master',
        description: 'Complete all 150 puzzles',
        icon: '🎖️',
        xp: 2000,
        category: 'modes'
    },
    BATTLE_WIN_10: {
        id: 'battle_win_10',
        name: 'Battle Veteran',
        description: 'Win 10 Battle mode games',
        icon: '⚔️',
        xp: 300,
        category: 'modes'
    },
    
    // Daily Challenge Achievements
    DAILY_STREAK_3: {
        id: 'daily_streak_3',
        name: 'Consistent',
        description: '3-day daily challenge streak',
        icon: '📅',
        xp: 100,
        category: 'daily'
    },
    DAILY_STREAK_7: {
        id: 'daily_streak_7',
        name: 'Dedicated',
        description: '7-day daily challenge streak',
        icon: '📆',
        xp: 300,
        category: 'daily'
    },
    DAILY_STREAK_30: {
        id: 'daily_streak_30',
        name: 'Devotee',
        description: '30-day daily challenge streak',
        icon: '🗓️',
        xp: 1000,
        category: 'daily'
    },
    
    // Secret Achievements
    ONLY_I_PIECES: {
        id: 'only_i_pieces',
        name: 'Straight Shooter',
        description: 'Score 10,000 points using only I-pieces',
        icon: '📏',
        xp: 500,
        category: 'secret',
        hidden: true
    },
    NO_ROTATION: {
        id: 'no_rotation',
        name: 'No Spin Zone',
        description: 'Clear 20 lines without rotating any piece',
        icon: '🚫',
        xp: 400,
        category: 'secret',
        hidden: true
    },
    SPEED_DEMON: {
        id: 'speed_demon',
        name: 'Lightning Fast',
        description: 'Place 100 pieces in 60 seconds',
        icon: '⚡',
        xp: 300,
        category: 'secret',
        hidden: true
    }
};

export const TROPHIES = {
    // Bronze Trophies
    BEGINNER: {
        id: 'beginner',
        name: 'Tetris Beginner',
        description: 'Unlock 5 achievements',
        tier: 'bronze',
        icon: '🥉',
        xp: 200,
        requirement: { type: 'achievements', count: 5 }
    },
    LEVEL_10: {
        id: 'level_10',
        name: 'Rising Star',
        description: 'Reach level 10',
        tier: 'bronze',
        icon: '⭐',
        xp: 300,
        requirement: { type: 'level', value: 10 }
    },
    
    // Silver Trophies
    INTERMEDIATE: {
        id: 'intermediate',
        name: 'Tetris Intermediate',
        description: 'Unlock 20 achievements',
        tier: 'silver',
        icon: '🥈',
        xp: 500,
        requirement: { type: 'achievements', count: 20 }
    },
    LEVEL_25: {
        id: 'level_25',
        name: 'Experienced Player',
        description: 'Reach level 25',
        tier: 'silver',
        icon: '🌟',
        xp: 750,
        requirement: { type: 'level', value: 25 }
    },
    ALL_MODES: {
        id: 'all_modes',
        name: 'Mode Master',
        description: 'Play all game modes at least once',
        tier: 'silver',
        icon: '🎮',
        xp: 600,
        requirement: { type: 'modes', all: true }
    },
    
    // Gold Trophies
    EXPERT: {
        id: 'expert',
        name: 'Tetris Expert',
        description: 'Unlock 50 achievements',
        tier: 'gold',
        icon: '🥇',
        xp: 1000,
        requirement: { type: 'achievements', count: 50 }
    },
    LEVEL_50: {
        id: 'level_50',
        name: 'Tetris Champion',
        description: 'Reach level 50',
        tier: 'gold',
        icon: '👑',
        xp: 1500,
        requirement: { type: 'level', value: 50 }
    },
    PERFECT_PUZZLES: {
        id: 'perfect_puzzles',
        name: 'Puzzle Perfectionist',
        description: '3-star all puzzles',
        tier: 'gold',
        icon: '💎',
        xp: 2000,
        requirement: { type: 'puzzles', perfect: true }
    },
    
    // Platinum Trophies
    COMPLETIONIST: {
        id: 'completionist',
        name: 'Completionist',
        description: 'Unlock all achievements',
        tier: 'platinum',
        icon: '🏆',
        xp: 5000,
        requirement: { type: 'achievements', all: true }
    },
    LEVEL_100: {
        id: 'level_100',
        name: 'Tetris Legend',
        description: 'Reach level 100',
        tier: 'platinum',
        icon: '🌌',
        xp: 10000,
        requirement: { type: 'level', value: 100 }
    }
};

export class AchievementSystem {
    constructor() {
        this.unlockedAchievements = [];
        this.unlockedTrophies = [];
        this.progress = {};
        this.notifications = [];
        this.loadProgress();
    }
    
    async loadProgress() {
        const saved = await storage.load('achievements');
        if (saved) {
            this.unlockedAchievements = saved.achievements || [];
            this.unlockedTrophies = saved.trophies || [];
            this.progress = saved.progress || {};
            console.log(`[AchievementSystem] Loaded ${this.unlockedAchievements.length} achievements, ${this.unlockedTrophies.length} trophies`);
        } else {
            console.log('[AchievementSystem] No saved achievements found, starting fresh');
        }
    }
    
    async saveProgress() {
        await storage.save('achievements', {
            achievements: this.unlockedAchievements,
            trophies: this.unlockedTrophies,
            progress: this.progress
        });
    }
    
    checkAchievement(achievementId, condition) {
        const achievement = ACHIEVEMENTS[achievementId];
        if (!achievement) return false;
        
        // Check if already unlocked
        if (this.unlockedAchievements.includes(achievementId)) {
            return false;
        }
        
        // Check condition
        if (condition) {
            this.unlockAchievement(achievementId);
            return true;
        }
        
        return false;
    }
    
    unlockAchievement(achievementId) {
        const achievement = ACHIEVEMENTS[achievementId];
        if (!achievement || this.unlockedAchievements.includes(achievementId)) {
            return false;
        }
        
        console.log(`[AchievementSystem] Unlocking achievement: ${achievement.name}`);
        
        // Add to unlocked list
        this.unlockedAchievements.push(achievementId);
        
        // Award XP through progressionManager for notifications
        if (window.progressionManager) {
            window.progressionManager.addXP(achievement.xp, `Achievement: ${achievement.name}`).catch(err => {
                console.error('Error awarding achievement XP:', err);
            });
        } else if (playerProgression) {
            // Fallback to direct playerProgression
            playerProgression.addXP(achievement.xp, 'achievement');
        }
        
        // Create notification
        this.createNotification({
            type: 'achievement',
            title: 'Achievement Unlocked!',
            name: achievement.name,
            description: achievement.description,
            icon: achievement.icon,
            xp: achievement.xp
        });
        
        // Check for trophy unlocks
        this.checkTrophies();
        
        // Save progress
        this.saveProgress();
        
        return true;
    }
    
    checkTrophies() {
        for (const [trophyId, trophy] of Object.entries(TROPHIES)) {
            if (this.unlockedTrophies.includes(trophyId)) continue;
            
            let unlocked = false;
            
            switch (trophy.requirement.type) {
                case 'achievements':
                    if (trophy.requirement.all) {
                        unlocked = this.unlockedAchievements.length === Object.keys(ACHIEVEMENTS).length;
                    } else {
                        unlocked = this.unlockedAchievements.length >= trophy.requirement.count;
                    }
                    break;
                    
                case 'level':
                    unlocked = playerProgression.level >= trophy.requirement.value;
                    break;
                    
                case 'modes':
                    if (trophy.requirement.all) {
                        const modes = ['classic', 'sprint', 'marathon', 'puzzle', 'battle', 'zen', 'powerup'];
                        unlocked = modes.every(mode => 
                            playerProgression.stats.modeCount && 
                            playerProgression.stats.modeCount[mode] > 0
                        );
                    }
                    break;
                    
                case 'puzzles':
                    if (trophy.requirement.perfect) {
                        // Check if all puzzles completed with 3 stars
                        // This would need puzzle completion data
                        unlocked = false; // Implement based on puzzle data
                    }
                    break;
            }
            
            if (unlocked) {
                this.unlockTrophy(trophyId);
            }
        }
    }
    
    unlockTrophy(trophyId) {
        const trophy = TROPHIES[trophyId];
        if (!trophy || this.unlockedTrophies.includes(trophyId)) {
            return false;
        }
        
        // Add to unlocked list
        this.unlockedTrophies.push(trophyId);
        
        // Award XP
        if (playerProgression) {
            playerProgression.addXP(trophy.xp, 'trophy');
        }
        
        // Create notification
        this.createNotification({
            type: 'trophy',
            title: 'Trophy Earned!',
            name: trophy.name,
            description: trophy.description,
            icon: trophy.icon,
            tier: trophy.tier,
            xp: trophy.xp
        });
        
        // Save progress
        this.saveProgress();
        
        return true;
    }
    
    updateProgress(key, value, increment = false) {
        const oldValue = this.progress[key] || 0;
        
        if (increment) {
            this.progress[key] = (this.progress[key] || 0) + value;
        } else {
            this.progress[key] = value;
        }
        
        console.log(`[AchievementSystem] Progress update - ${key}: ${oldValue} -> ${this.progress[key]}`);
        
        // Check related achievements
        this.checkProgressAchievements(key);
        
        // Save progress after update
        this.saveProgress().catch(err => console.error('Error saving achievement progress:', err));
    }
    
    checkProgressAchievements(key) {
        const value = this.progress[key];
        console.log(`[AchievementSystem] Checking progress for ${key}: ${value}`);
        
        switch (key) {
            case 'totalLines':
                // Check for first line achievement
                if (value >= 1) {
                    this.checkAchievement('FIRST_LINE', true);
                }
                this.checkAchievement('LINES_100', value >= 100);
                this.checkAchievement('LINES_1000', value >= 1000);
                this.checkAchievement('LINES_10000', value >= 10000);
                break;
                
            case 'highScore':
                this.checkAchievement('SCORE_10K', value >= 10000);
                this.checkAchievement('SCORE_100K', value >= 100000);
                this.checkAchievement('SCORE_1M', value >= 1000000);
                break;
                
            case 'maxCombo':
                this.checkAchievement('COMBO_5', value >= 5);
                this.checkAchievement('COMBO_10', value >= 10);
                this.checkAchievement('COMBO_20', value >= 20);
                break;
                
            case 'tspins':
                this.checkAchievement('FIRST_TSPIN', value >= 1);
                this.checkAchievement('TSPIN_MASTER', value >= 100);
                break;
                
            case 'perfectClears':
                this.checkAchievement('PERFECT_CLEAR', value >= 1);
                this.checkAchievement('PERFECT_CLEAR_5', value >= 5);
                break;
                
            case 'puzzlesCompleted':
                this.checkAchievement('PUZZLE_50', value >= 50);
                this.checkAchievement('PUZZLE_ALL', value >= 150);
                break;
                
            case 'dailyStreak':
                this.checkAchievement('DAILY_STREAK_3', value >= 3);
                this.checkAchievement('DAILY_STREAK_7', value >= 7);
                this.checkAchievement('DAILY_STREAK_30', value >= 30);
                break;
        }
    }
    
    createNotification(data) {
        const notification = {
            ...data,
            id: Date.now(),
            timestamp: Date.now()
        };
        
        console.log(`[AchievementSystem] Creating notification for: ${data.name}`);
        
        this.notifications.push(notification);
        
        // Dispatch event for UI
        window.dispatchEvent(new CustomEvent('achievementUnlocked', { 
            detail: notification 
        }));
        
        // Show achievement unlock notification
        this.showAchievementNotification(notification);
        
        // Show UI notification if available
        if (window.game && window.game.uiManager) {
            window.game.uiManager.showAchievement(notification);
        }
    }
    
    showAchievementNotification(notification) {
        console.log(`[AchievementSystem] Showing achievement notification: ${notification.name}`);
        
        const container = document.getElementById('achievement-unlocks');
        if (!container) {
            console.error('[AchievementSystem] Achievement notification container not found!');
            return;
        }
        
        const element = document.createElement('div');
        element.className = 'achievement-unlock';
        element.innerHTML = `
            <div class="achievement-icon">${notification.icon}</div>
            <div class="achievement-title">${notification.title}</div>
            <div class="achievement-name">${notification.name}</div>
            <div class="achievement-description">${notification.description}</div>
            <div class="achievement-xp">+${notification.xp} XP</div>
        `;
        
        container.appendChild(element);
        
        // Remove after animation
        setTimeout(() => {
            element.style.animation = 'fade-out 0.5s ease forwards';
            setTimeout(() => element.remove(), 500);
        }, 4000);
    }
    
    getProgress() {
        const totalAchievements = Object.keys(ACHIEVEMENTS).length;
        const totalTrophies = Object.keys(TROPHIES).length;
        
        return {
            achievements: {
                unlocked: this.unlockedAchievements.length,
                total: totalAchievements,
                percentage: Math.floor((this.unlockedAchievements.length / totalAchievements) * 100)
            },
            trophies: {
                unlocked: this.unlockedTrophies.length,
                total: totalTrophies,
                percentage: Math.floor((this.unlockedTrophies.length / totalTrophies) * 100)
            },
            recentUnlocks: this.notifications.slice(-5)
        };
    }
    
    getUnlockedDetails() {
        return {
            achievements: this.unlockedAchievements.map(id => ({
                ...ACHIEVEMENTS[id],
                unlockedAt: this.progress[`${id}_time`] || null
            })),
            trophies: this.unlockedTrophies.map(id => ({
                ...TROPHIES[id],
                unlockedAt: this.progress[`${id}_time`] || null
            }))
        };
    }
    
    reset() {
        this.unlockedAchievements = [];
        this.unlockedTrophies = [];
        this.progress = {};
        this.notifications = [];
        this.saveProgress();
    }
}

export const achievementSystem = new AchievementSystem();