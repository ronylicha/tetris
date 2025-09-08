// Daily Challenge System
import { storage } from '../storage-adapter.js';
import { PUZZLES } from '../puzzles/puzzleData.js';

export class DailyChallenge {
    constructor() {
        this.currentChallenge = null;
        this.challengeHistory = [];
        this.streak = 0;
        this.lastPlayedDate = null;
        this.todaysSeed = null;
        this.loadProgress();
    }
    
    async loadProgress() {
        const saved = await storage.load('daily_challenge_progress');
        if (saved) {
            this.challengeHistory = saved.history || [];
            this.streak = saved.streak || 0;
            this.lastPlayedDate = saved.lastPlayedDate;
        }
    }
    
    async saveProgress() {
        await storage.save('daily_challenge_progress', {
            history: this.challengeHistory,
            streak: this.streak,
            lastPlayedDate: this.lastPlayedDate
        });
    }
    
    getDailySeed() {
        // Generate consistent seed for the day
        const today = new Date();
        const dateString = `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`;
        return this.hashCode(dateString);
    }
    
    hashCode(str) {
        let hash = 0;
        for (let i = 0; i < str.length; i++) {
            const char = str.charCodeAt(i);
            hash = ((hash << 5) - hash) + char;
            hash = hash & hash; // Convert to 32-bit integer
        }
        return Math.abs(hash);
    }
    
    getTodaysChallenge() {
        const seed = this.getDailySeed();
        
        // Check if we already have today's challenge
        if (this.todaysSeed === seed && this.currentChallenge) {
            return this.currentChallenge;
        }
        
        this.todaysSeed = seed;
        
        // Generate challenge based on seed
        const rng = this.seededRandom(seed);
        
        // Pick a random puzzle as base
        const basePuzzle = PUZZLES[Math.floor(rng() * PUZZLES.length)];
        
        // Generate modifiers
        const modifiers = this.generateModifiers(rng);
        
        // Create challenge configuration
        this.currentChallenge = {
            id: `daily_${seed}`,
            name: this.generateChallengeName(rng),
            basePuzzle: basePuzzle.id,
            date: new Date().toISOString().split('T')[0],
            seed: seed,
            
            // Challenge parameters
            objective: this.selectObjective(rng),
            difficulty: this.calculateDifficulty(rng),
            modifiers: modifiers,
            
            // Rewards
            baseXP: 500,
            bonusXP: modifiers.length * 100,
            streakBonus: this.streak * 50,
            
            // Constraints
            timeLimit: this.generateTimeLimit(rng),
            maxPieces: this.generatePieceLimit(rng),
            
            // Special rules
            specialRules: this.generateSpecialRules(rng),
            
            // Leaderboard
            leaderboardEnabled: true,
            globalStats: {
                attempts: 0,
                completions: 0,
                averageTime: 0,
                bestTime: null,
                topPlayers: []
            }
        };
        
        return this.currentChallenge;
    }
    
    seededRandom(seed) {
        // Seeded random number generator
        let s = seed;
        return function() {
            s = (s * 9301 + 49297) % 233280;
            return s / 233280;
        };
    }
    
    generateModifiers(rng) {
        const allModifiers = [
            {
                id: 'invisible',
                name: 'Invisible Pieces',
                description: 'Pieces become invisible after placement',
                effect: 'invisible_grid',
                difficulty: 3
            },
            {
                id: 'speed_increase',
                name: 'Accelerating Speed',
                description: 'Speed increases every 45 seconds',
                effect: 'speed_ramp',
                difficulty: 2
            },
            {
                id: 'limited_hold',
                name: 'No Hold',
                description: 'Hold feature is disabled',
                effect: 'no_hold',
                difficulty: 2
            },
            {
                id: 'mirror',
                name: 'Mirror Mode',
                description: 'Controls are reversed',
                effect: 'mirror_controls',
                difficulty: 3
            },
            {
                id: 'earthquake',
                name: 'Earthquake',
                description: 'Grid shakes periodically',
                effect: 'shake',
                difficulty: 2
            },
            {
                id: 'fog',
                name: 'Fog of War',
                description: 'Only see bottom 10 rows',
                effect: 'fog',
                difficulty: 2
            },
            {
                id: 'tiny',
                name: 'Tiny Grid',
                description: 'Grid is only 6 columns wide',
                effect: 'tiny_grid',
                difficulty: 2
            },
            {
                id: 'rotation_lock',
                name: 'Rotation Lock',
                description: 'Can only rotate clockwise',
                effect: 'clockwise_only',
                difficulty: 1
            },
            {
                id: 'color_blind',
                name: 'Monochrome',
                description: 'All pieces are the same color',
                effect: 'monochrome',
                difficulty: 2
            }
        ];
        
        // Select 1-3 modifiers based on day
        const numModifiers = Math.floor(rng() * 3) + 1;
        const selected = [];
        const available = [...allModifiers];
        
        for (let i = 0; i < numModifiers && available.length > 0; i++) {
            const index = Math.floor(rng() * available.length);
            selected.push(available[index]);
            available.splice(index, 1);
        }
        
        return selected;
    }
    
    selectObjective(rng) {
        const objectives = [
            { type: 'lines', target: 10 + Math.floor(rng() * 20) },
            { type: 'score', target: 5000 + Math.floor(rng() * 10000) },
            { type: 'combo', target: 5 + Math.floor(rng() * 5) },
            { type: 'tetris', target: 2 + Math.floor(rng() * 3) },
            { type: 'tspin', target: 1 + Math.floor(rng() * 2) },
            { type: 'perfect', target: 1 },
            { type: 'cascade', target: 3 + Math.floor(rng() * 2) },
            { type: 'speed', timeLimit: 60 + Math.floor(rng() * 60) },
            { type: 'survival', duration: 120 + Math.floor(rng() * 60) },
            { type: 'pattern', pattern: ['pyramid', 'checkerboard', 'stairs'][Math.floor(rng() * 3)] }
        ];
        
        return objectives[Math.floor(rng() * objectives.length)];
    }
    
    generateTimeLimit(rng) {
        // 0 means no limit, otherwise 1-5 minutes
        const hasLimit = rng() > 0.5;
        return hasLimit ? (60 + Math.floor(rng() * 240)) : 0;
    }
    
    generatePieceLimit(rng) {
        // 0 means no limit, otherwise 20-100 pieces
        const hasLimit = rng() > 0.6;
        return hasLimit ? (20 + Math.floor(rng() * 80)) : 0;
    }
    
    generateSpecialRules(rng) {
        const rules = [];
        
        if (rng() > 0.8) {
            rules.push({
                id: 'no_line_clear',
                name: 'Pacifist',
                description: 'Complete without clearing lines'
            });
        }
        
        if (rng() > 0.9) {
            rules.push({
                id: 'all_pieces_once',
                name: 'One of Each',
                description: 'Use each piece type exactly once'
            });
        }
        
        if (rng() > 0.7) {
            rules.push({
                id: 'increasing_speed',
                name: 'Escalation',
                description: 'Speed increases with each piece'
            });
        }
        
        return rules;
    }
    
    calculateDifficulty(rng) {
        // Calculate overall difficulty based on modifiers and objectives
        let difficulty = 1;
        
        // Add base randomness
        difficulty += Math.floor(rng() * 3);
        
        // Cap at 5
        return Math.min(5, Math.max(1, difficulty));
    }
    
    generateChallengeName(rng) {
        const adjectives = [
            'Extreme', 'Ultimate', 'Legendary', 'Epic', 'Mystical',
            'Quantum', 'Cosmic', 'Stellar', 'Infernal', 'Celestial',
            'Ancient', 'Forbidden', 'Sacred', 'Chaotic', 'Harmonious'
        ];
        
        const nouns = [
            'Challenge', 'Trial', 'Gauntlet', 'Ordeal', 'Quest',
            'Mission', 'Journey', 'Expedition', 'Crusade', 'Odyssey'
        ];
        
        const adj = adjectives[Math.floor(rng() * adjectives.length)];
        const noun = nouns[Math.floor(rng() * nouns.length)];
        
        return `${adj} ${noun}`;
    }
    
    async completeChallenge(stats) {
        const today = new Date().toISOString().split('T')[0];
        
        // Check if already completed today
        if (this.lastPlayedDate === today) {
            return {
                alreadyCompleted: true,
                message: 'Daily challenge already completed!'
            };
        }
        
        // Update streak
        const yesterday = new Date();
        yesterday.setDate(yesterday.getDate() - 1);
        const yesterdayString = yesterday.toISOString().split('T')[0];
        
        if (this.lastPlayedDate === yesterdayString) {
            this.streak++;
        } else {
            this.streak = 1;
        }
        
        // Calculate rewards
        const challenge = this.currentChallenge;
        let totalXP = challenge.baseXP;
        
        // Add bonus XP for modifiers
        totalXP += challenge.bonusXP;
        
        // Add streak bonus
        totalXP += challenge.streakBonus;
        
        // Add performance bonus
        if (stats.time < challenge.timeLimit / 2) {
            totalXP += 200; // Speed bonus
        }
        if (stats.perfectClear) {
            totalXP += 300; // Perfect clear bonus
        }
        
        // Record completion
        const completion = {
            date: today,
            challengeId: challenge.id,
            challengeName: challenge.name,
            stats: stats,
            xpEarned: totalXP,
            streak: this.streak,
            time: stats.time,
            score: stats.score
        };
        
        this.challengeHistory.push(completion);
        this.lastPlayedDate = today;
        
        // Save progress
        await this.saveProgress();
        
        // Submit to global leaderboard
        await this.submitToLeaderboard(completion);
        
        return {
            success: true,
            xpEarned: totalXP,
            streak: this.streak,
            rank: await this.getPlayerRank(stats.score),
            nextChallenge: this.getTimeUntilNextChallenge()
        };
    }
    
    async submitToLeaderboard(completion) {
        // Submit score to global daily leaderboard
        try {
            // Get player name from storage or use Guest
            const playerName = localStorage.getItem('tetris_player_name') || 'Guest';
            
            const response = await fetch('/api/daily-challenge.php', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'submit',
                    challengeId: completion.challengeId,
                    playerName: playerName,
                    score: completion.score,
                    time: completion.time,
                    stats: completion.stats
                })
            });
            
            if (response.ok) {
                const data = await response.json();
                return data.rank;
            }
        } catch (error) {
            console.error('Failed to submit to leaderboard:', error);
        }
        return null;
    }
    
    async getPlayerRank(score) {
        // Get player's rank in today's challenge
        try {
            const response = await fetch('/api/daily-challenge.php', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'getRank',
                    challengeId: this.currentChallenge.id,
                    score: score
                })
            });
            
            if (response.ok) {
                const data = await response.json();
                return data.rank;
            }
        } catch (error) {
            console.error('Failed to get rank:', error);
        }
        return null;
    }
    
    getTimeUntilNextChallenge() {
        const now = new Date();
        const tomorrow = new Date(now);
        tomorrow.setDate(tomorrow.getDate() + 1);
        tomorrow.setHours(0, 0, 0, 0);
        
        const msUntilTomorrow = tomorrow - now;
        const hours = Math.floor(msUntilTomorrow / (1000 * 60 * 60));
        const minutes = Math.floor((msUntilTomorrow % (1000 * 60 * 60)) / (1000 * 60));
        
        return { hours, minutes };
    }
    
    async getGlobalStats() {
        // Fetch global statistics for today's challenge
        try {
            const response = await fetch('/api/daily-challenge.php', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'getStats',
                    challengeId: this.currentChallenge.id
                })
            });
            
            if (response.ok) {
                return await response.json();
            }
        } catch (error) {
            console.error('Failed to get global stats:', error);
        }
        return null;
    }
    
    getStreakRewards() {
        // Define streak milestone rewards
        const milestones = [
            { streak: 3, reward: 'Bronze Badge', xp: 500 },
            { streak: 7, reward: 'Silver Badge', xp: 1000 },
            { streak: 14, reward: 'Gold Badge', xp: 2000 },
            { streak: 30, reward: 'Platinum Badge', xp: 5000 },
            { streak: 60, reward: 'Diamond Badge', xp: 10000 },
            { streak: 100, reward: 'Master Badge', xp: 20000 }
        ];
        
        return milestones.filter(m => m.streak === this.streak);
    }
    
    isAvailable() {
        const today = new Date().toISOString().split('T')[0];
        return this.lastPlayedDate !== today;
    }
}

export const dailyChallenge = new DailyChallenge();