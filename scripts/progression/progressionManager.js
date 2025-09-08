// Progression Manager - Handles all progression UI and API interactions
import { dailyChallenge } from '../challenges/dailyChallenge.js';

// Détection automatique du chemin API selon l'environnement
function getApiPath() {
    // Si on est dans un sous-dossier tetris, ajouter le préfixe
    const path = window.location.pathname;
    if (path.includes('/tetris/') && !path.includes('tetris.')) {
        return '/tetris/api';
    }
    // Production ou racine
    return '/api';
}
const API_BASE = getApiPath();

export class ProgressionManager {
    constructor() {
        this.playerId = null;
        this.playerData = null;
        this.achievements = [];
        this.unlockables = [];
        this.dailyChallenge = null;
        this.authToken = localStorage.getItem('auth_token') || null;
        this.isAuthenticated = false;
        this.isGuest = true; // Start as guest by default
        
        this.init();
    }
    
    async init() {
        console.log('[ProgressionManager] Initializing...');
        // Initialize in guest mode first
        this.initGuestMode();
        console.log('[ProgressionManager] Guest mode initialized');
        
        // Check authentication if token exists
        if (this.authToken) {
            await this.verifyAuth();
        }
        
        // Setup UI event listeners
        this.setupEventListeners();
        
        // Setup service worker message listener
        this.setupServiceWorkerListener();
        
        // Load initial data (local or API)
        await this.loadInitialData();
        
        // Update UI
        this.updateProgressionUI();
        this.updateAuthUI();
        
        // Start daily challenge timer
        this.startDailyChallengeTimer();
        
        // Start auto-sync if authenticated
        if (this.isAuthenticated) {
            this.startAutoSync();
        }
    }
    
    initGuestMode() {
        this.isGuest = true;
        this.playerId = 'guest_local';
        
        // Load or create guest data from localStorage
        const guestData = localStorage.getItem('tetris_guest_data');
        if (guestData) {
            try {
                const parsed = JSON.parse(guestData);
                this.playerData = parsed.playerData || this.getDefaultPlayerData();
                this.achievements = parsed.achievements || [];
                
                // Check if unlockables need update (if less than expected)
                if (!parsed.unlockables || parsed.unlockables.length < 31) {
                    console.log('Updating unlockables to new structure');
                    this.unlockables = this.getDefaultUnlockables();
                    this.saveGuestData(); // Save the updated structure
                } else {
                    this.unlockables = parsed.unlockables;
                }
            } catch (e) {
                console.error('Error parsing guest data:', e);
                this.createDefaultGuestData();
            }
        } else {
            this.createDefaultGuestData();
        }
    }
    
    createDefaultGuestData() {
        this.playerData = this.getDefaultPlayerData();
        this.achievements = [];
        this.unlockables = this.getDefaultUnlockables();
        this.saveGuestData();
    }
    
    getDefaultPlayerData() {
        return {
            id: 0,
            username: 'guest',
            display_name: 'Guest Player',
            level: 1,
            current_xp: 0,
            total_xp: 0,
            rank: 'Novice',
            games_played: 0,
            total_score: 0,
            total_lines: 0,
            total_time: 0,
            best_score: 0,
            best_combo: 0,
            total_tspins: 0,
            total_tetrises: 0
        };
    }
    
    getDefaultUnlockables() {
        // Return all unlockables with complete list
        return [
            // Themes (7 total)
            { code: 'theme_neon', name: 'Neon', type: 'theme', unlocked: true, equipped: true, unlock_value: 'default', description: 'Vibrant neon colors with glow effects' },
            { code: 'theme_classic', name: 'Classic', type: 'theme', unlocked: true, equipped: false, unlock_value: 'default', description: 'Traditional Tetris colors' },
            { code: 'theme_cyberpunk', name: 'Cyberpunk', type: 'theme', unlocked: false, equipped: false, unlock_value: 'level:5', description: 'Futuristic pink and cyan aesthetic' },
            { code: 'theme_retro', name: 'Retro', type: 'theme', unlocked: false, equipped: false, unlock_value: 'level:10', description: 'Nostalgic 80s color palette' },
            { code: 'theme_nature', name: 'Nature', type: 'theme', unlocked: false, equipped: false, unlock_value: 'level:15', description: 'Earthy tones and natural colors' },
            { code: 'theme_minimal', name: 'Minimal', type: 'theme', unlocked: false, equipped: false, unlock_value: 'level:20', description: 'Clean monochrome design' },
            { code: 'theme_galaxy', name: 'Galaxy', type: 'theme', unlocked: false, equipped: false, unlock_value: 'level:30', description: 'Cosmic colors and space theme' },
            
            // Music (8 total)
            { code: 'music_classic', name: 'Classic', type: 'music', unlocked: true, equipped: true, unlock_value: 'default', description: 'Original Tetris melody' },
            { code: 'music_modern', name: 'Modern', type: 'music', unlocked: true, equipped: false, unlock_value: 'default', description: 'Contemporary electronic beats' },
            { code: 'music_synthwave', name: 'Synthwave', type: 'music', unlocked: false, equipped: false, unlock_value: 'level:8', description: 'Retro 80s synth sounds' },
            { code: 'music_lofi', name: 'Lo-Fi', type: 'music', unlocked: false, equipped: false, unlock_value: 'level:12', description: 'Relaxing chill beats' },
            { code: 'music_orchestral', name: 'Orchestral', type: 'music', unlocked: false, equipped: false, unlock_value: 'level:18', description: 'Epic symphonic arrangement' },
            { code: 'music_jazz', name: 'Jazz', type: 'music', unlocked: false, equipped: false, unlock_value: 'level:22', description: 'Smooth jazz fusion' },
            { code: 'music_metal', name: 'Metal', type: 'music', unlocked: false, equipped: false, unlock_value: 'level:28', description: 'Heavy metal intensity' },
            { code: 'music_chiptune', name: 'Chiptune', type: 'music', unlocked: false, equipped: false, unlock_value: 'level:35', description: '8-bit retro game music' },
            
            // Piece styles (8 total)
            { code: 'piece_style_standard', name: 'Standard', type: 'piece_style', unlocked: true, equipped: true, unlock_value: 'default', description: 'Classic block style' },
            { code: 'piece_style_glass', name: 'Glass', type: 'piece_style', unlocked: false, equipped: false, unlock_value: 'level:7', description: 'Transparent glass blocks' },
            { code: 'piece_style_pixel', name: 'Pixel', type: 'piece_style', unlocked: false, equipped: false, unlock_value: 'level:11', description: 'Retro pixelated style' },
            { code: 'piece_style_neon', name: 'Neon', type: 'piece_style', unlocked: false, equipped: false, unlock_value: 'level:16', description: 'Glowing neon outlines' },
            { code: 'piece_style_hologram', name: 'Hologram', type: 'piece_style', unlocked: false, equipped: false, unlock_value: 'level:21', description: 'Futuristic holographic effect' },
            { code: 'piece_style_crystal', name: 'Crystal', type: 'piece_style', unlocked: false, equipped: false, unlock_value: 'level:26', description: 'Shimmering crystal facets' },
            { code: 'piece_style_animated', name: 'Animated', type: 'piece_style', unlocked: false, equipped: false, unlock_value: 'level:32', description: 'Pulsing animated blocks' },
            { code: 'piece_style_minimal', name: 'Minimal', type: 'piece_style', unlocked: false, equipped: false, unlock_value: 'level:40', description: 'Simple flat design' },
            
            // Effects (8 total)
            { code: 'effect_none', name: 'None', type: 'effect', unlocked: true, equipped: true, unlock_value: 'default', description: 'No visual effects' },
            { code: 'effect_particles', name: 'Particles', type: 'effect', unlocked: false, equipped: false, unlock_value: 'level:3', description: 'Particle explosions on placement' },
            { code: 'effect_trails', name: 'Trails', type: 'effect', unlocked: false, equipped: false, unlock_value: 'level:9', description: 'Motion trails when moving pieces' },
            { code: 'effect_explosions', name: 'Explosions', type: 'effect', unlocked: false, equipped: false, unlock_value: 'level:14', description: 'Explosive line clears' },
            { code: 'effect_lightning', name: 'Lightning', type: 'effect', unlocked: false, equipped: false, unlock_value: 'level:19', description: 'Electric bolts on line clears' },
            { code: 'effect_shatter', name: 'Shatter', type: 'effect', unlocked: false, equipped: false, unlock_value: 'level:24', description: 'Pieces shatter on placement' },
            { code: 'effect_quantum', name: 'Quantum', type: 'effect', unlocked: false, equipped: false, unlock_value: 'level:31', description: 'Phase shifting quantum effects' },
            { code: 'effect_matrix', name: 'Matrix', type: 'effect', unlocked: false, equipped: false, unlock_value: 'level:45', description: 'Digital rain effect' }
        ];
    }
    
    saveGuestData() {
        if (!this.isGuest) return;
        
        const data = {
            playerData: this.playerData,
            achievements: this.achievements,
            unlockables: this.unlockables
        };
        localStorage.setItem('tetris_guest_data', JSON.stringify(data));
    }
    
    async loadInitialData() {
        if (this.isGuest) {
            // Use local data for guest
            this.dailyChallenge = this.generateLocalDailyChallenge();
            this.updateDailyChallengeUI();
        } else {
            // Load from API if authenticated
            await this.loadDailyChallenge();
            await this.loadAchievements();
            await this.loadUnlockables();
        }
    }
    
    generateLocalDailyChallenge() {
        // Use the real daily challenge system
        const challenge = dailyChallenge.getTodaysChallenge();
        
        if (!challenge) {
            // Fallback if challenge generation fails
            return {
                id: Date.now(),
                date: new Date().toDateString(),
                goal: 'Score 50,000 points',
                modifiers: ['Speed Increase'],
                xp_reward: 500,
                completed: false
            };
        }
        
        // Format the challenge for display
        const objective = challenge.objective;
        let goalText = '';
        
        switch(objective.type) {
            case 'lines':
                goalText = `Clear ${objective.target} lines`;
                break;
            case 'score':
                goalText = `Score ${objective.target} points`;
                break;
            case 'combo':
                goalText = `Achieve ${objective.target}x combo`;
                break;
            case 'tetris':
                goalText = `Perform ${objective.target} Tetris${objective.target > 1 ? 'es' : ''}`;
                break;
            case 'tspin':
                goalText = `Perform ${objective.target} T-Spin${objective.target > 1 ? 's' : ''}`;
                break;
            case 'perfect':
                goalText = 'Achieve a perfect clear';
                break;
            case 'survival':
                goalText = `Survive for ${objective.duration} seconds`;
                break;
            case 'speed':
                goalText = `Clear 40 lines quickly`;
                break;
            default:
                goalText = challenge.name;
        }
        
        // Extract modifier names
        const modifierNames = challenge.modifiers ? 
            challenge.modifiers.map(mod => mod.name || mod) : [];
        
        return {
            id: challenge.seed,
            date: new Date().toDateString(),
            goal: goalText,
            modifiers: modifierNames,
            xp_reward: challenge.baseXP || 500,
            completed: !dailyChallenge.isAvailable()
        };
    }
    
    setupServiceWorkerListener() {
        if ('serviceWorker' in navigator) {
            navigator.serviceWorker.addEventListener('message', (event) => {
                if (event.data && event.data.type) {
                    switch (event.data.type) {
                        case 'REGISTRATION_SYNCED':
                            this.showNotification('Account created successfully!', 'success');
                            this.loadPlayer();
                            break;
                        case 'PROGRESSION_SYNCED':
                            this.showNotification(event.data.message, 'success');
                            break;
                        case 'SYNC_SCORES':
                            // Trigger score sync from storage
                            if (window.offlineStorage) {
                                window.offlineStorage.syncPendingScores();
                            }
                            break;
                    }
                }
            });
        }
    }
    
    setupEventListeners() {
        // Prevent multiple event listener registrations
        if (this.eventListenersSetup) {
            console.log('Event listeners already setup, skipping');
            return;
        }
        this.eventListenersSetup = true;
        
        // Profile button
        const profileBtn = document.getElementById('profile-btn');
        if (profileBtn) {
            profileBtn.addEventListener('click', () => this.showProfileModal());
        }
        
        // Achievements button
        const achievementsBtn = document.getElementById('home-achievements-button');
        if (achievementsBtn) {
            achievementsBtn.addEventListener('click', () => this.showAchievementsModal());
        }
        
        // Customize button
        const customizeBtn = document.getElementById('home-customize-button');
        if (customizeBtn) {
            customizeBtn.addEventListener('click', () => this.showCustomizeModal());
        }
        
        // Also add listener for the customize button in progression header
        const customizeBtnHeader = document.getElementById('customize-btn');
        if (customizeBtnHeader) {
            customizeBtnHeader.addEventListener('click', () => this.showCustomizeModal());
        }
        
        // Customization tabs - Use event delegation to avoid duplicates
        const customizationModal = document.getElementById('customizationModal');
        if (customizationModal && !customizationModal.hasAttribute('data-listeners-attached')) {
            customizationModal.setAttribute('data-listeners-attached', 'true');
            
            // Use event delegation on the modal itself
            customizationModal.addEventListener('click', (e) => {
                // Check if clicked element is a tab button
                if (e.target.classList.contains('tab-btn')) {
                    e.stopPropagation();
                    e.preventDefault();
                    
                    const clickedTab = e.target;
                    const type = clickedTab.dataset.type;
                    
                    if (!type) {
                        console.error('Tab type not found:', clickedTab);
                        return;
                    }
                    
                    // Remove active class from all tabs
                    document.querySelectorAll('#customizationModal .tab-btn').forEach(t => t.classList.remove('active'));
                    // Add active to clicked tab
                    clickedTab.classList.add('active');
                    
                    // Load the content for this type
                    this.loadCustomizationOptions(type);
                }
            });
        }
        
        // Daily challenge button (existing card button)
        const dailyPlayBtn = document.getElementById('daily-play-btn');
        if (dailyPlayBtn) {
            dailyPlayBtn.addEventListener('click', () => this.startDailyChallenge());
        }
        
        // Modal close buttons
        document.getElementById('achievements-close')?.addEventListener('click', () => {
            document.getElementById('achievements-overlay').style.display = 'none';
        });
        
        document.getElementById('profile-close')?.addEventListener('click', () => {
            document.getElementById('profile-overlay').style.display = 'none';
        });
        
        document.getElementById('customize-close')?.addEventListener('click', () => {
            document.getElementById('customize-overlay').style.display = 'none';
        });
        
        // Achievement tabs
        document.querySelectorAll('.achievement-tab').forEach(tab => {
            tab.addEventListener('click', (e) => {
                document.querySelectorAll('.achievement-tab').forEach(t => t.classList.remove('active'));
                e.target.classList.add('active');
                this.filterAchievements(e.target.dataset.category);
            });
        });
        
        // Customize tabs (in modal)
        document.querySelectorAll('#customizationModal .tab-btn').forEach(tab => {
            tab.addEventListener('click', (e) => {
                document.querySelectorAll('#customizationModal .tab-btn').forEach(t => t.classList.remove('active'));
                e.target.classList.add('active');
                const tabType = e.target.dataset.tab;
                // Map tab names to type names
                const typeMap = {
                    'themes': 'theme',
                    'music': 'music',
                    'pieces': 'piece_style',
                    'effects': 'effect'
                };
                this.loadCustomizationOptions(typeMap[tabType] || tabType);
            });
        });
    }
    
    async loadPlayer() {
        // This method is now only called when authenticated
        if (!this.isAuthenticated || !this.playerId) return;
        
        try {
            const response = await fetch(`${API_BASE}/progression.php?action=get_player&id=${this.playerId}`);
            if (response.ok) {
                const apiData = await response.json();
                this.playerData = apiData;
                this.isGuest = false;
                
                // Migrate any guest data if needed
                await this.migrateGuestData();
            }
        } catch (error) {
            console.error('Error loading player:', error);
            // Stay in guest mode
        }
    }
    
    async migrateGuestData() {
        // Check if there's guest data to migrate
        const guestData = localStorage.getItem('tetris_guest_data');
        if (!guestData || !this.isAuthenticated) return;
        
        try {
            const parsed = JSON.parse(guestData);
            
            // Send guest data to server for migration
            await fetch(`${API_BASE}/progression.php`, {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${this.authToken}`
                },
                body: JSON.stringify({
                    action: 'migrate_guest_data',
                    guest_data: parsed
                })
            });
            
            // Clear guest data after successful migration
            localStorage.removeItem('tetris_guest_data');
        } catch (error) {
            console.log('Could not migrate guest data:', error);
        }
    }
    
    updateProgressionUI() {
        if (!this.playerData) {
            console.warn('[ProgressionManager] No player data available for UI update');
            return;
        }
        console.log(`[ProgressionManager] Updating UI - Level: ${this.playerData.level}, XP: ${this.playerData.current_xp}, Rank: ${this.playerData.rank}`);
        
        // Update header
        document.getElementById('player-level').textContent = this.playerData.level;
        document.getElementById('player-rank').textContent = this.playerData.rank;
        document.getElementById('profile-name').textContent = this.playerData.display_name;
        
        // Update XP bar
        const xpPercent = (this.playerData.current_xp / 1000) * 100;
        const xpBar = document.getElementById('xp-bar');
        if (xpBar) {
            xpBar.style.width = xpPercent + '%';
        }
        document.getElementById('xp-text').textContent = `${this.playerData.current_xp} / 1000 XP`;
    }
    
    async loadDailyChallenge() {
        if (this.isGuest) {
            // Use local daily challenge for guests
            this.dailyChallenge = this.generateLocalDailyChallenge();
            this.updateDailyChallengeUI();
            return;
        }
        
        try {
            const response = await fetch(`${API_BASE}/progression.php?action=get_daily_challenge&player_id=${this.playerId}`);
            if (response.ok) {
                this.dailyChallenge = await response.json();
                this.updateDailyChallengeUI();
            }
        } catch (error) {
            console.log('Using local daily challenge (offline mode)');
            this.dailyChallenge = this.generateLocalDailyChallenge();
            this.updateDailyChallengeUI();
        }
    }
    
    updateDailyChallengeUI() {
        if (!this.dailyChallenge) return;
        
        // Update goal - handle both simple and complex format
        const goalElement = document.getElementById('daily-goal');
        if (goalElement) {
            if (this.dailyChallenge.goal) {
                // Simple format for guest
                goalElement.textContent = this.dailyChallenge.goal;
            } else if (this.dailyChallenge.goal_type) {
                // Complex format from API
                const goalText = `${this.dailyChallenge.goal_type === 'score' ? 'Score' : 
                                 this.dailyChallenge.goal_type === 'lines' ? 'Clear' :
                                 this.dailyChallenge.goal_type === 'time' ? 'Survive for' : 'Survive'} 
                                 ${this.dailyChallenge.goal_value} 
                                 ${this.dailyChallenge.goal_type === 'score' ? 'points' :
                                  this.dailyChallenge.goal_type === 'lines' ? 'lines' :
                                  this.dailyChallenge.goal_type === 'time' ? 'seconds' : 'seconds'}`;
                goalElement.textContent = goalText;
            }
        }
        
        // Update modifiers
        const modifiersContainer = document.getElementById('daily-modifiers');
        if (modifiersContainer) {
            modifiersContainer.innerHTML = '';
            
            const modifiers = Array.isArray(this.dailyChallenge.modifiers) ? 
                            this.dailyChallenge.modifiers : 
                            JSON.parse(this.dailyChallenge.modifiers || '[]');
            
            modifiers.forEach(mod => {
                const tag = document.createElement('span');
                tag.className = 'modifier-tag';
                tag.textContent = typeof mod === 'string' ? mod : mod.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
                modifiersContainer.appendChild(tag);
            });
        }
        
        // Check if completed
        const playBtn = document.getElementById('daily-play-btn');
        if (playBtn) {
            if (this.dailyChallenge.completed || (this.dailyChallenge.player_attempt && this.dailyChallenge.player_attempt.completed)) {
                playBtn.textContent = '✓ Completed';
                playBtn.disabled = true;
            }
        }
    }
    
    startDailyChallengeTimer() {
        const updateTimer = () => {
            const now = new Date();
            const tomorrow = new Date(now);
            tomorrow.setDate(tomorrow.getDate() + 1);
            tomorrow.setHours(0, 0, 0, 0);
            
            const timeLeft = tomorrow - now;
            const hours = Math.floor(timeLeft / (1000 * 60 * 60));
            const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);
            
            const timerElement = document.getElementById('daily-timer');
            if (timerElement) {
                timerElement.textContent = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            }
        };
        
        updateTimer();
        setInterval(updateTimer, 1000);
    }
    
    async loadAchievements() {
        if (this.isGuest) {
            // Achievements stored locally for guests
            this.updateAchievementBadge();
            return;
        }
        
        try {
            const response = await fetch(`${API_BASE}/progression.php?action=get_achievements&player_id=${this.playerId}`);
            if (response.ok) {
                this.achievements = await response.json();
                this.updateAchievementBadge();
            }
        } catch (error) {
            console.log('Using local achievements (offline mode)');
            // Keep existing local achievements
            this.updateAchievementBadge();
        }
    }
    
    updateAchievementBadge() {
        const newAchievements = this.achievements.filter(a => a.unlocked && a.is_new).length;
        const badge = document.getElementById('achievement-badge');
        if (badge) {
            if (newAchievements > 0) {
                badge.style.display = 'flex';
                badge.textContent = newAchievements;
            } else {
                badge.style.display = 'none';
            }
        }
    }
    
    showProfileModal() {
        if (!this.playerData) return;
        
        // Update profile modal content
        document.getElementById('profile-display-name').textContent = this.playerData.display_name;
        document.getElementById('profile-level').textContent = this.playerData.level;
        document.getElementById('profile-rank').textContent = this.playerData.rank;
        
        // XP is shown in the progression section of the modal
        
        // Update stats
        document.getElementById('stat-games').textContent = this.playerData.games_played || 0;
        document.getElementById('stat-score').textContent = (this.playerData.total_score || 0).toLocaleString();
        document.getElementById('stat-lines').textContent = this.playerData.total_lines || 0;
        
        const totalTime = this.playerData.total_time || 0;
        const hours = Math.floor(totalTime / 3600);
        const minutes = Math.floor((totalTime % 3600) / 60);
        document.getElementById('stat-time').textContent = `${hours}h ${minutes}m`;
        
        document.getElementById('stat-high-score').textContent = (this.playerData.best_score || this.playerData.highest_score || 0).toLocaleString();
        document.getElementById('stat-combo').textContent = this.playerData.best_combo || this.playerData.highest_combo || 0;
        document.getElementById('stat-tspins').textContent = this.playerData.total_tspins || 0;
        document.getElementById('stat-tetris').textContent = this.playerData.total_tetrises || this.playerData.total_tetris || 0;
        
        // Update progression info
        const totalXPElement = document.getElementById('profile-total-xp');
        if (totalXPElement) totalXPElement.textContent = this.playerData.total_xp || 0;
        
        const streakElement = document.getElementById('profile-streak');
        if (streakElement) streakElement.textContent = this.playerData.daily_streak || 0;
        
        const achievementsElement = document.getElementById('profile-achievements');
        if (achievementsElement) {
            const unlocked = this.achievements.filter(a => a.unlocked).length;
            achievementsElement.textContent = `${unlocked}/30`;
        }
        
        // Show modal
        document.getElementById('profileModal').style.display = 'flex';
    }
    
    showAchievementsModal() {
        // Update progress
        const unlocked = this.achievements.filter(a => a.unlocked).length;
        const total = this.achievements.length;
        document.getElementById('achievements-unlocked').textContent = unlocked;
        document.getElementById('achievements-total').textContent = total;
        
        const totalXP = this.achievements
            .filter(a => a.unlocked)
            .reduce((sum, a) => sum + (a.xp_reward || 0), 0);
        document.getElementById('achievements-points').textContent = totalXP.toLocaleString();
        
        // Show all achievements
        this.renderAchievements('all');
        
        // Show modal
        document.getElementById('achievementsModal').style.display = 'flex';
    }
    
    renderAchievements(category) {
        const container = document.getElementById('achievements-grid');
        container.innerHTML = '';
        
        const filtered = category === 'all' 
            ? this.achievements 
            : this.achievements.filter(a => a.category === category);
        
        filtered.forEach(achievement => {
            const item = document.createElement('div');
            item.className = `achievement-item ${achievement.unlocked ? 'unlocked' : 'locked'}`;
            
            const progress = achievement.progress || 0;
            const requirements = JSON.parse(achievement.requirements || '{}');
            
            item.innerHTML = `
                <div class="achievement-icon">${achievement.unlocked ? '🏆' : '🔒'}</div>
                <div class="achievement-details">
                    <h4>${achievement.name}</h4>
                    <p>${achievement.description}</p>
                    ${!achievement.unlocked && progress > 0 ? `
                        <div class="achievement-progress-bar">
                            <div class="achievement-progress-fill" style="width: ${(progress * 100)}%"></div>
                        </div>
                    ` : ''}
                    <span class="achievement-xp">+${achievement.xp_reward} XP</span>
                </div>
                ${achievement.unlocked ? `
                    <div class="achievement-date">
                        ${new Date(achievement.unlocked_at).toLocaleDateString()}
                    </div>
                ` : ''}
            `;
            
            container.appendChild(item);
        });
    }
    
    filterAchievements(category) {
        this.renderAchievements(category);
    }
    
    async showCustomizeModal() {
        // Load unlockables if not loaded
        if (!this.unlockables || this.unlockables.length === 0) {
            await this.loadUnlockables();
        }
        
        // Show themes by default
        this.loadCustomizationOptions('theme');
        
        // Make sure the first tab is active
        const tabs = document.querySelectorAll('#customizationModal .tab-btn');
        tabs.forEach((tab, index) => {
            tab.classList.toggle('active', index === 0);
        });
        
        // Show modal
        const modal = document.getElementById('customizationModal');
        if (modal) {
            modal.style.display = 'flex';
        } else {
            console.error('Customization modal not found');
        }
    }
    
    async loadUnlockables() {
        if (this.isGuest) {
            // Use default unlockables for guest
            if (this.unlockables.length === 0) {
                this.unlockables = this.getDefaultUnlockables();
            }
            return;
        }
        
        try {
            const response = await fetch(`${API_BASE}/progression.php?action=get_unlockables&player_id=${this.playerId}`);
            if (response.ok) {
                this.unlockables = await response.json();
            }
        } catch (error) {
            console.log('Using local unlockables (offline mode)');
            if (this.unlockables.length === 0) {
                this.unlockables = this.getDefaultUnlockables();
            }
        }
    }
    
    loadCustomizationOptions(type) {
        const container = document.getElementById('customizationContent');
        if (!container) {
            console.error('Customization content container not found');
            return;
        }
        container.innerHTML = '';
        
        // Debug: Check if unlockables exist
        if (!this.unlockables || this.unlockables.length === 0) {
            console.warn('No unlockables loaded! Loading defaults...');
            this.unlockables = this.getDefaultUnlockables();
            this.saveGuestData();
        }
        
        const items = this.unlockables.filter(u => u.type === type);
        
        if (items.length === 0) {
            container.innerHTML = `<p style="text-align: center; color: #999;">No items available for ${type}</p>`;
            return;
        }
        
        const grid = document.createElement('div');
        grid.className = 'customize-grid';
        
        items.forEach(item => {
            const card = document.createElement('div');
            card.className = `customize-item ${item.unlocked ? 'unlocked' : 'locked'} ${item.equipped ? 'equipped' : ''}`;
            
            // Get preview based on type and item code
            const preview = this.getCustomizationPreview(type, item.code);
            
            card.innerHTML = `
                <div class="customize-preview" ${preview.style ? `style="${preview.style}"` : ''}>
                    ${preview.content}
                </div>
                <h4>${item.name}</h4>
                ${item.description ? `<p class="item-description">${item.description}</p>` : ''}
                ${!item.unlocked ? `
                    <p class="unlock-requirement">${this.getUnlockText(item)}</p>
                    <button class="preview-btn" data-code="${item.code}">Preview</button>
                ` : item.equipped ? `
                    <button class="equipped-btn" disabled>✓ Equipped</button>
                ` : `
                    <button class="equip-btn" data-code="${item.code}">Equip</button>
                `}
            `;
            
            if (item.unlocked && !item.equipped) {
                card.querySelector('.equip-btn').addEventListener('click', () => {
                    this.equipItem(item.code);
                });
            }
            
            // Add preview button for locked items
            if (!item.unlocked) {
                const previewBtn = card.querySelector('.preview-btn');
                if (previewBtn) {
                    previewBtn.addEventListener('click', (e) => {
                        e.stopPropagation();
                        this.startPreview(type, item.code);
                    });
                }
            }
            
            // Add hover preview for all items (not just unlocked)
            if (type === 'theme') {
                card.addEventListener('mouseenter', () => this.previewTheme(item.code));
                card.addEventListener('mouseleave', () => this.resetThemePreview());
            }
            
            grid.appendChild(card);
        });
        
        container.appendChild(grid);
    }
    
    getCustomizationPreview(type, code) {
        const previews = {
            theme: {
                theme_neon: { content: '🎨', style: 'background: linear-gradient(45deg, #00ffff, #ff00ff); -webkit-background-clip: text; -webkit-text-fill-color: transparent;' },
                theme_classic: { content: '🎮', style: 'color: #4CAF50;' },
                theme_cyberpunk: { content: '🌆', style: 'color: #ff006e; text-shadow: 0 0 10px #ff006e;' },
                theme_retro: { content: '👾', style: 'color: #FFB900;' },
                theme_nature: { content: '🌿', style: 'color: #22c55e;' },
                theme_minimal: { content: '⬜', style: 'filter: grayscale(1);' },
                theme_galaxy: { content: '🌌', style: 'background: linear-gradient(45deg, #667eea, #764ba2); -webkit-background-clip: text; -webkit-text-fill-color: transparent;' }
            },
            music: {
                music_classic: { content: '🎵', style: 'color: #4CAF50;' },
                music_modern: { content: '🎶', style: 'color: #2196F3;' },
                music_synthwave: { content: '🎹', style: 'color: #E91E63;' },
                music_lofi: { content: '☕', style: 'color: #795548;' },
                music_orchestral: { content: '🎻', style: 'color: #9C27B0;' },
                music_jazz: { content: '🎷', style: 'color: #FF9800;' },
                music_metal: { content: '🎸', style: 'color: #F44336;' },
                music_chiptune: { content: '🕹️', style: 'color: #00BCD4;' }
            },
            piece_style: {
                piece_style_standard: { content: '◼', style: 'color: #fff;' },
                piece_style_glass: { content: '◻', style: 'color: rgba(255,255,255,0.5); backdrop-filter: blur(5px);' },
                piece_style_pixel: { content: '▪', style: 'font-size: 2em;' },
                piece_style_neon: { content: '◼', style: 'color: #00ffff; text-shadow: 0 0 10px #00ffff;' },
                piece_style_hologram: { content: '◻', style: 'color: #00ff88; opacity: 0.7;' },
                piece_style_crystal: { content: '💎', style: '' },
                piece_style_animated: { content: '◼', style: 'animation: pulse 1s infinite;' },
                piece_style_minimal: { content: '▫', style: 'color: #666;' }
            },
            effect: {
                effect_none: { content: '⭕', style: 'color: #666;' },
                effect_particles: { content: '✨', style: 'animation: sparkle 1s infinite;' },
                effect_trails: { content: '💫', style: '' },
                effect_explosions: { content: '💥', style: '' },
                effect_lightning: { content: '⚡', style: 'color: #ffeb3b;' },
                effect_shatter: { content: '💔', style: '' },
                effect_quantum: { content: '🌀', style: 'animation: spin 2s linear infinite;' },
                effect_matrix: { content: '📟', style: 'color: #00ff00;' }
            }
        };
        
        const typePreview = previews[type];
        if (typePreview && typePreview[code]) {
            return typePreview[code];
        }
        
        // Default previews
        const defaults = {
            theme: { content: '🎨', style: '' },
            music: { content: '🎵', style: '' },
            piece_style: { content: '◼', style: '' },
            effect: { content: '✨', style: '' }
        };
        
        return defaults[type] || { content: '?', style: '' };
    }
    
    previewTheme(themeCode) {
        // Temporarily apply theme for preview
        const themeName = themeCode.replace('theme_', '');
        if (window.customizationManager && window.customizationManager.managers.theme) {
            window.customizationManager.managers.theme.previewTheme(themeName);
        }
    }
    
    resetThemePreview() {
        // Reset to equipped theme
        if (window.customizationManager && window.customizationManager.managers.theme) {
            window.customizationManager.managers.theme.applyTheme();
        }
    }
    
    startPreview(type, code) {
        const itemName = code.replace(`${type}_`, '');
        
        // Create preview overlay
        const previewOverlay = document.createElement('div');
        previewOverlay.className = 'preview-overlay';
        previewOverlay.innerHTML = `
            <div class="preview-container">
                <div class="preview-header">
                    <h3>Preview: ${this.unlockables.find(u => u.code === code)?.name || itemName}</h3>
                    <button class="close-preview">✕</button>
                </div>
                <div class="preview-content">
                    ${this.getPreviewContent(type, code)}
                </div>
                <div class="preview-footer">
                    <p class="unlock-info">${this.getUnlockText(this.unlockables.find(u => u.code === code))}</p>
                    <button class="close-btn">Close Preview</button>
                </div>
            </div>
        `;
        
        document.body.appendChild(previewOverlay);
        
        // Apply preview based on type
        if (type === 'theme') {
            this.previewTheme(code);
        } else if (type === 'music' && window.customizationManager) {
            // Preview music track
            const musicManager = window.customizationManager.managers.music;
            if (musicManager) {
                musicManager.previewTrack(itemName);
            }
        } else if (type === 'piece_style') {
            // Show piece style preview
            this.showPieceStylePreview(itemName);
        } else if (type === 'effect') {
            // Show effect preview
            this.showEffectPreview(itemName);
        }
        
        // Add event listeners
        const closePreview = () => {
            previewOverlay.remove();
            this.resetThemePreview();
            
            // Stop music preview if playing
            if (type === 'music' && window.customizationManager) {
                const musicManager = window.customizationManager.managers.music;
                if (musicManager) {
                    musicManager.stopPreview();
                }
            }
        };
        
        previewOverlay.querySelector('.close-preview').addEventListener('click', closePreview);
        previewOverlay.querySelector('.close-btn').addEventListener('click', closePreview);
        previewOverlay.addEventListener('click', (e) => {
            if (e.target === previewOverlay) closePreview();
        });
    }
    
    getPreviewContent(type, code) {
        const itemName = code.replace(`${type}_`, '');
        
        if (type === 'theme') {
            const colors = this.getThemeColors(itemName);
            return `
                <div class="theme-preview">
                    <div class="color-palette">
                        ${Object.entries(colors).map(([name, color]) => `
                            <div class="color-swatch">
                                <div class="swatch" style="background: ${color};"></div>
                                <span>${name}</span>
                            </div>
                        `).join('')}
                    </div>
                    <div class="preview-game-board">
                        <canvas id="preview-canvas" width="200" height="400"></canvas>
                    </div>
                </div>
            `;
        } else if (type === 'music') {
            return `
                <div class="music-preview">
                    <div class="music-icon">🎵</div>
                    <p>Click play to preview this track</p>
                    <button class="play-music-preview" data-track="${itemName}">▶ Play Preview</button>
                </div>
            `;
        } else if (type === 'piece_style') {
            return `
                <div class="piece-style-preview">
                    <canvas id="style-preview-canvas" width="300" height="200"></canvas>
                    <p>Preview of piece rendering style</p>
                </div>
            `;
        } else if (type === 'effect') {
            return `
                <div class="effect-preview">
                    <canvas id="effect-preview-canvas" width="300" height="200"></canvas>
                    <p>Visual effect demonstration</p>
                </div>
            `;
        }
        
        return '<p>Preview not available</p>';
    }
    
    getThemeColors(themeName) {
        // Get colors from theme manager if available
        if (window.customizationManager && window.customizationManager.managers.theme) {
            const theme = window.customizationManager.managers.theme.themes[themeName];
            if (theme) {
                return {
                    'I Piece': theme.colors.I,
                    'O Piece': theme.colors.O,
                    'T Piece': theme.colors.T,
                    'S Piece': theme.colors.S,
                    'Z Piece': theme.colors.Z,
                    'L Piece': theme.colors.L,
                    'J Piece': theme.colors.J
                };
            }
        }
        
        // Default colors
        return {
            'I Piece': '#00FFFF',
            'O Piece': '#FFFF00',
            'T Piece': '#FF00FF',
            'S Piece': '#00FF00',
            'Z Piece': '#FF0000',
            'L Piece': '#FFA500',
            'J Piece': '#0000FF'
        };
    }
    
    showPieceStylePreview(styleName) {
        setTimeout(() => {
            const canvas = document.getElementById('style-preview-canvas');
            if (canvas && window.customizationManager) {
                const ctx = canvas.getContext('2d');
                const pieceStyle = window.customizationManager.managers.piece_style;
                
                // Draw sample pieces with the style
                const pieces = ['I', 'O', 'T', 'S', 'Z', 'L', 'J'];
                const cellSize = 25;
                
                pieces.forEach((piece, index) => {
                    const x = (index % 4) * cellSize * 3;
                    const y = Math.floor(index / 4) * cellSize * 2;
                    
                    // Temporarily set the style
                    const oldStyle = pieceStyle.currentStyle;
                    pieceStyle.currentStyle = styleName;
                    pieceStyle.renderPiece(ctx, x, y, cellSize, this.getThemeColors('neon')[`${piece} Piece`], false);
                    pieceStyle.currentStyle = oldStyle;
                });
            }
        }, 100);
    }
    
    showEffectPreview(effectName) {
        setTimeout(() => {
            const canvas = document.getElementById('effect-preview-canvas');
            if (canvas && window.customizationManager) {
                const ctx = canvas.getContext('2d');
                
                // Show effect description
                ctx.fillStyle = '#fff';
                ctx.font = '14px Arial';
                ctx.textAlign = 'center';
                ctx.fillText(`${effectName} Effect Active`, canvas.width / 2, canvas.height / 2);
            }
        }, 100);
    }
    
    getUnlockText(item) {
        if (!item.unlock_value || item.unlock_value === 'default') {
            return 'Unlocked';
        }
        
        const parts = item.unlock_value.split(':');
        if (parts.length < 2) return 'Locked';
        
        const [type, value] = parts;
        if (type === 'level') {
            return `Unlock at Level ${value}`;
        } else if (type === 'achievement') {
            return `Complete achievement`;
        } else if (type === 'daily_streak') {
            return `${value} day streak`;
        }
        return 'Locked';
    }
    
    startDailyChallenge() {
        // Check if challenge is already completed
        if (this.dailyChallenge && this.dailyChallenge.completed) {
            this.showNotification('Daily challenge already completed!', 'warning');
            return;
        }
        
        // Close any open modals
        document.querySelectorAll('.modal-overlay').forEach(modal => {
            modal.style.display = 'none';
        });
        
        // Start the game with daily challenge modifiers
        if (window.tetrisGame) {
            // Set up daily challenge mode
            window.tetrisGame.isDailyChallenge = true;
            window.tetrisGame.dailyChallengeData = this.dailyChallenge;
            
            // Start game in classic mode with modifiers
            window.tetrisGame.modalManager.selectModeAndStart('classic');
            
            // Apply modifiers (this would need to be implemented in the game)
            if (this.dailyChallenge.modifiers) {
                this.dailyChallenge.modifiers.forEach(modifier => {
                    console.log('Applying modifier:', modifier);
                    // TODO: Apply actual modifiers to the game
                });
            }
            
            this.showNotification('Daily Challenge Started!', 'info');
        } else {
            console.error('Game not initialized');
        }
    }
    
    async equipItem(itemCode) {
        // Find the item in unlockables
        const item = this.unlockables.find(u => u.code === itemCode);
        if (!item || !item.unlocked) {
            this.showNotification('Item not unlocked yet!', 'warning');
            return;
        }
        
        // Unequip other items of the same type
        this.unlockables.forEach(u => {
            if (u.type === item.type) {
                u.equipped = false;
            }
        });
        
        // Equip the selected item
        item.equipped = true;
        
        // Save to localStorage for guest
        if (this.isGuest) {
            this.saveGuestData();
        }
        
        // Apply the customization
        window.dispatchEvent(new CustomEvent('customizationChanged', {
            detail: { type: item.type, code: itemCode }
        }));
        
        // Refresh the UI
        this.loadCustomizationOptions(item.type);
        
        // Show notification
        this.showNotification(`${item.name} equipped!`, 'success');
        
        // Try to sync with server if not guest
        if (!this.isGuest) {
            try {
                const response = await fetch(`${API_BASE}/progression.php`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        action: 'equip_item',
                        player_id: this.playerId,
                        unlockable_code: itemCode
                    })
                });
                
                if (response.ok) {
                    // Reload unlockables and refresh UI
                    await this.loadUnlockables();
                    const type = this.unlockables.find(u => u.code === itemCode)?.type;
                    if (type) {
                        this.loadCustomizationOptions(type);
                    }
                    
                    // Apply the customization (theme, music, etc.)
                    this.applyCustomization(itemCode);
                }
            } catch (error) {
                console.error('Error equipping item:', error);
            }
        }
    }
    
    applyCustomization(itemCode) {
        // This would apply the actual theme/music/style changes
        // For now, just log it
        console.log('Applying customization:', itemCode);
        
        // TODO: Implement actual theme/music/style application
    }
    
    startDailyChallenge() {
        if (!this.dailyChallenge) return;
        
        // Store challenge info for the game
        sessionStorage.setItem('daily_challenge', JSON.stringify(this.dailyChallenge));
        
        // Start the game in daily challenge mode
        if (window.tetrisGame && window.tetrisGame.modalManager) {
            window.tetrisGame.modalManager.selectModeAndStart('daily');
        } else {
            console.error('Game or modal manager not initialized');
        }
    }
    
    // Notifications
    showNotification(message, type = 'info') {
        // Create notification element
        const notification = document.createElement('div');
        notification.className = `notification notification-${type}`;
        notification.textContent = message;
        notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 15px 20px;
            border-radius: 5px;
            background: ${type === 'success' ? '#00ff88' : 
                         type === 'warning' ? '#ffaa00' : 
                         type === 'error' ? '#ff4444' : '#00aaff'};
            color: ${type === 'success' || type === 'info' ? '#000' : '#fff'};
            box-shadow: 0 4px 12px rgba(0,0,0,0.3);
            z-index: 10000;
            animation: slideIn 0.3s ease-out;
            font-family: 'Orbitron', sans-serif;
        `;
        
        document.body.appendChild(notification);
        
        // Remove after 3 seconds
        setTimeout(() => {
            notification.style.animation = 'slideOut 0.3s ease-out';
            setTimeout(() => notification.remove(), 300);
        }, 3000);
    }
    
    // XP and Achievement notifications
    showXPNotification(amount, source = '') {
        console.log(`[ProgressionManager] Showing XP notification: +${amount} XP from ${source}`);
        const container = document.getElementById('xp-notifications');
        if (!container) {
            console.error('[ProgressionManager] XP notifications container not found!');
            return;
        }
        const notification = document.createElement('div');
        notification.className = 'xp-notification';
        notification.innerHTML = `+${amount} XP${source ? ` - ${source}` : ''}`;
        
        container.appendChild(notification);
        
        // Remove after animation
        setTimeout(() => {
            notification.remove();
        }, 3000);
    }
    
    showAchievementUnlock(achievement) {
        const container = document.getElementById('achievement-unlocks');
        const unlock = document.createElement('div');
        unlock.className = 'achievement-unlock';
        
        unlock.innerHTML = `
            <div class="achievement-icon">🏆</div>
            <div class="achievement-title">${achievement.name}</div>
            <div class="achievement-description">${achievement.description}</div>
            <div class="achievement-xp">+${achievement.xp_reward} XP</div>
        `;
        
        container.appendChild(unlock);
        
        // Remove after animation
        setTimeout(() => {
            unlock.remove();
        }, 5000);
    }
    
    showLevelUp(newLevel) {
        const container = document.getElementById('level-up-celebration');
        container.innerHTML = `
            <div class="level-up-celebration">
                <div class="level-up-text">LEVEL ${newLevel}!</div>
                <div class="level-up-particles"></div>
            </div>
        `;
        
        // Create particles
        const particlesContainer = container.querySelector('.level-up-particles');
        for (let i = 0; i < 50; i++) {
            const particle = document.createElement('div');
            particle.className = 'particle';
            particle.style.left = Math.random() * 100 + '%';
            particle.style.animationDelay = Math.random() * 2 + 's';
            particlesContainer.appendChild(particle);
        }
        
        // Remove after animation
        setTimeout(() => {
            container.innerHTML = '';
        }, 3000);
    }
    
    // Game integration methods
    async addXP(amount, source = 'gameplay') {
        console.log(`[ProgressionManager] Adding ${amount} XP from ${source}`);
        console.log(`[ProgressionManager] Current state - Guest: ${this.isGuest}, Level: ${this.playerData?.level}, XP: ${this.playerData?.current_xp}`);
        
        if (this.isGuest) {
            // Handle XP locally for guests
            const oldLevel = this.playerData.level || 1;
            this.playerData.current_xp = (this.playerData.current_xp || 0) + amount;
            this.playerData.total_xp = (this.playerData.total_xp || 0) + amount;
            console.log(`[ProgressionManager] New XP: ${this.playerData.current_xp}, Total: ${this.playerData.total_xp}`);
            
            // Calculate level locally
            this.playerData.level = this.calculateLevel(this.playerData.total_xp);
            
            // Update rank
            this.playerData.rank = this.getRankForLevel(this.playerData.level);
            
            // Save guest data
            this.saveGuestData();
            
            // Show XP notification
            this.showXPNotification(amount, source);
            
            if (this.playerData.level > oldLevel) {
                this.showLevelUp(this.playerData.level);
            }
            
            // Update UI
            this.updateProgressionUI();
            return;
        }
        
        try {
            const response = await fetch(`${API_BASE}/progression.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'add_xp',
                    player_id: this.playerId,
                    amount: amount,
                    source: source
                })
            });
            
            const data = await response.json();
            
            if (data.offline && data.queued) {
                // Offline - update locally
                const oldLevel = this.playerData.level || 1;
                this.playerData.current_xp = (this.playerData.current_xp || 0) + amount;
                this.playerData.total_xp = (this.playerData.total_xp || 0) + amount;
                
                // Calculate level locally
                this.playerData.level = this.calculateLevel(this.playerData.total_xp);
                
                // Show XP notification
                this.showXPNotification(amount, source);
                
                if (this.playerData.level > oldLevel) {
                    this.showLevelUp(this.playerData.level);
                    this.updateRank(this.playerData.level);
                }
                
                // Show offline indicator
                this.showNotification('Progress saved offline', 'info');
            } else if (response.ok && data.leveled_up) {
                // Online success with level up
                this.showXPNotification(amount, source);
                this.showLevelUp(data.new_level);
                
                // Update player data
                this.playerData.level = data.new_level;
                this.playerData.current_xp = data.new_xp;
                this.playerData.total_xp = data.total_xp;
                
                // Recalculate rank
                this.updateRank(data.new_level);
            } else if (response.ok) {
                // Online success without level up
                this.showXPNotification(amount, source);
                
                if (data.new_xp !== undefined) {
                    this.playerData.current_xp = data.new_xp;
                    this.playerData.total_xp = data.total_xp;
                }
            }
            
            // Update UI
            this.updateProgressionUI();
        } catch (error) {
            console.error('Error adding XP:', error);
            
            // Fallback to local update
            this.playerData.current_xp = (this.playerData.current_xp || 0) + amount;
            this.playerData.total_xp = (this.playerData.total_xp || 0) + amount;
            this.showXPNotification(amount, source);
            this.updateProgressionUI();
        }
    }
    
    calculateLevel(totalXP) {
        // XP required per level increases progressively
        console.log(`[ProgressionManager] Calculating level for ${totalXP} total XP`);
        let level = 1;
        let xpNeeded = 0;
        
        while (xpNeeded <= totalXP && level < 100) {
            level++;
            xpNeeded += 1000 + (level - 1) * 100;
        }
        
        return level - 1;
    }
    
    getRankForLevel(level) {
        const ranks = [
            { min: 90, name: 'Eternal' },
            { min: 80, name: 'Titan' },
            { min: 70, name: 'Myth' },
            { min: 60, name: 'Legend' },
            { min: 50, name: 'Grandmaster' },
            { min: 40, name: 'Master' },
            { min: 30, name: 'Expert' },
            { min: 20, name: 'Adept' },
            { min: 10, name: 'Apprentice' },
            { min: 1, name: 'Novice' }
        ];
        
        for (const rank of ranks) {
            if (level >= rank.min) {
                return rank.name;
            }
        }
        
        return 'Novice';
    }
    
    updateRank(level) {
        this.playerData.rank = this.getRankForLevel(level);
    }
    
    async checkAchievement(type, value) {
        // Check if any achievement should be unlocked
        const achievementsToCheck = this.achievements.filter(a => {
            if (a.unlocked) return false;
            
            const req = JSON.parse(a.requirements || '{}');
            return req.type === type;
        });
        
        for (const achievement of achievementsToCheck) {
            const req = JSON.parse(achievement.requirements || '{}');
            const progress = Math.min(1, value / req.value);
            
            if (progress >= 1) {
                // Unlock achievement
                await this.unlockAchievement(achievement.code);
            } else if (progress > achievement.progress) {
                // Update progress
                await this.updateAchievementProgress(achievement.code, progress);
            }
        }
    }
    
    async unlockAchievement(achievementCode) {
        try {
            const response = await fetch(`${API_BASE}/progression.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'unlock_achievement',
                    player_id: this.playerId,
                    achievement_code: achievementCode,
                    progress: 1.0
                })
            });
            
            if (response.ok) {
                const data = await response.json();
                
                if (data.is_new) {
                    const achievement = this.achievements.find(a => a.code === achievementCode);
                    if (achievement) {
                        this.showAchievementUnlock(achievement);
                        
                        // Add XP
                        if (data.xp_reward) {
                            await this.addXP(data.xp_reward, 'achievement');
                        }
                    }
                }
                
                // Reload achievements
                await this.loadAchievements();
            }
        } catch (error) {
            console.error('Error unlocking achievement:', error);
        }
    }
    
    async updateAchievementProgress(achievementCode, progress) {
        try {
            await fetch(`${API_BASE}/progression.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'unlock_achievement',
                    player_id: this.playerId,
                    achievement_code: achievementCode,
                    progress: progress
                })
            });
        } catch (error) {
            console.error('Error updating achievement progress:', error);
        }
    }
    
    async updatePlayerStats(stats) {
        try {
            await fetch(`${API_BASE}/progression.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'update_player_stats',
                    player_id: this.playerId,
                    ...stats
                })
            });
            
            // Update local data
            Object.assign(this.playerData, stats);
        } catch (error) {
            console.error('Error updating player stats:', error);
        }
    }
    
    // ========================================
    // AUTHENTICATION METHODS
    // ========================================
    
    async register() {
        const username = document.getElementById('registerUsername').value.trim();
        const email = document.getElementById('registerEmail').value.trim();
        const password = document.getElementById('registerPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        
        // Validation
        if (!username || !email || !password) {
            this.showNotification('Please fill in all fields', 'error');
            return;
        }
        
        if (password !== confirmPassword) {
            this.showNotification('Passwords do not match', 'error');
            return;
        }
        
        if (password.length < 6) {
            this.showNotification('Password must be at least 6 characters', 'error');
            return;
        }
        
        try {
            const response = await fetch(`${API_BASE}/auth.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'register',
                    username,
                    email,
                    password
                })
            });
            
            const data = await response.json();
            
            if (data.success) {
                // Store auth token
                this.authToken = data.auth_token;
                localStorage.setItem('auth_token', this.authToken);
                this.isAuthenticated = true;
                
                // Link guest account if exists
                if (this.playerId && !this.playerData.email) {
                    await this.linkGuestAccount();
                }
                
                // Update player data
                this.playerData = data.player;
                this.playerId = data.player.id;
                
                // Update UI
                this.updateAuthUI();
                this.showNotification('Account created successfully!', 'success');
                
                // Close modal
                document.getElementById('accountModal').style.display = 'none';
            } else {
                this.showNotification(data.error || 'Registration failed', 'error');
            }
        } catch (error) {
            console.error('Registration error:', error);
            this.showNotification('Network error. Please try again.', 'error');
        }
    }
    
    async login() {
        const email = document.getElementById('loginEmail').value.trim();
        const password = document.getElementById('loginPassword').value;
        
        if (!email || !password) {
            this.showNotification('Please enter email and password', 'error');
            return;
        }
        
        try {
            const response = await fetch(`${API_BASE}/auth.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'login',
                    email,
                    password
                })
            });
            
            const data = await response.json();
            
            if (data.success) {
                // Store auth token
                this.authToken = data.auth_token;
                localStorage.setItem('auth_token', this.authToken);
                this.isAuthenticated = true;
                
                // Link guest account if exists
                if (this.playerId && !this.playerData.email) {
                    await this.linkGuestAccount();
                }
                
                // Update player data
                this.playerData = data.player;
                this.playerId = data.player.id;
                
                // Reload progression data
                await this.loadPlayer();
                await this.loadAchievements();
                
                // Update UI
                this.updateAuthUI();
                this.updateProgressionUI();
                this.showNotification('Login successful!', 'success');
                
                // Close modal
                document.getElementById('accountModal').style.display = 'none';
                
                // Start syncing
                this.startAutoSync();
            } else {
                this.showNotification(data.error || 'Login failed', 'error');
            }
        } catch (error) {
            console.error('Login error:', error);
            this.showNotification('Network error. Please try again.', 'error');
        }
    }
    
    async logout() {
        try {
            await fetch(`${API_BASE}/auth.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'logout',
                    auth_token: this.authToken
                })
            });
            
            // Clear auth data
            this.authToken = null;
            this.isAuthenticated = false;
            localStorage.removeItem('auth_token');
            
            // Create guest account
            await this.createGuestPlayer();
            
            // Update UI
            this.updateAuthUI();
            this.updateProgressionUI();
            this.showNotification('Logged out successfully', 'success');
            
            // Stop syncing
            this.stopAutoSync();
            
            // Close modal
            document.getElementById('accountModal').style.display = 'none';
        } catch (error) {
            console.error('Logout error:', error);
        }
    }
    
    async linkGuestAccount() {
        if (!this.authToken || !this.playerId) return;
        
        try {
            await fetch(`${API_BASE}/auth.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'link_guest',
                    auth_token: this.authToken,
                    guest_id: this.playerId
                })
            });
        } catch (error) {
            console.error('Error linking guest account:', error);
        }
    }
    
    async verifyAuth() {
        if (!this.authToken) return false;
        
        try {
            const response = await fetch(`${API_BASE}/auth.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'verify',
                    auth_token: this.authToken
                })
            });
            
            const data = await response.json();
            
            if (data.success) {
                this.isAuthenticated = true;
                this.playerData = data.player;
                this.playerId = data.player.id;
                return true;
            } else {
                // Invalid token
                this.authToken = null;
                this.isAuthenticated = false;
                localStorage.removeItem('auth_token');
                return false;
            }
        } catch (error) {
            console.error('Auth verification error:', error);
            return false;
        }
    }
    
    showAccount() {
        const modal = document.getElementById('accountModal');
        if (!modal) return;
        
        if (this.isAuthenticated) {
            // Show account info
            document.getElementById('loginForm').style.display = 'none';
            document.getElementById('registerForm').style.display = 'none';
            document.getElementById('accountInfo').style.display = 'block';
            
            // Update account details
            document.getElementById('accountUsername').textContent = this.playerData.username;
            document.getElementById('accountEmail').textContent = this.playerData.email;
            document.getElementById('accountType').textContent = 
                this.playerData.account_type === 'registered' ? 'Registered' : 'Guest';
            document.getElementById('memberSince').textContent = 
                new Date(this.playerData.created_at).toLocaleDateString();
            document.getElementById('lastSync').textContent = 'Just now';
        } else {
            // Show login form
            document.getElementById('loginForm').style.display = 'block';
            document.getElementById('registerForm').style.display = 'none';
            document.getElementById('accountInfo').style.display = 'none';
        }
        
        modal.style.display = 'flex';
    }
    
    updateAuthUI() {
        const accountBtn = document.getElementById('account-status');
        const profileName = document.getElementById('profile-name');
        
        if (this.isAuthenticated) {
            if (accountBtn) {
                accountBtn.textContent = this.playerData.username;
                accountBtn.parentElement.querySelector('.profile-icon').textContent = '✅';
            }
            if (profileName) {
                profileName.textContent = this.playerData.display_name;
            }
        } else {
            if (accountBtn) {
                accountBtn.textContent = 'Login';
                accountBtn.parentElement.querySelector('.profile-icon').textContent = '🔐';
            }
            if (profileName) {
                profileName.textContent = 'Guest';
            }
        }
    }
    
    // ========================================
    // AUTO SYNC METHODS
    // ========================================
    
    startAutoSync() {
        if (!this.isAuthenticated) return;
        
        // Sync every 30 seconds
        this.syncInterval = setInterval(() => {
            this.syncData();
        }, 30000);
        
        // Sync on page unload
        window.addEventListener('beforeunload', () => {
            this.syncData();
        });
    }
    
    stopAutoSync() {
        if (this.syncInterval) {
            clearInterval(this.syncInterval);
            this.syncInterval = null;
        }
    }
    
    async syncData() {
        if (!this.isAuthenticated || !this.authToken) return;
        
        try {
            // Gather current data
            const syncData = {
                xp: {
                    current: this.playerData.current_xp,
                    total: this.playerData.total_xp,
                    level: this.playerData.level
                },
                achievements: this.achievements.filter(a => a.unlocked).map(a => ({
                    id: a.id,
                    progress: a.progress,
                    unlocked: a.unlocked,
                    unlocked_at: a.unlocked_at
                })),
                stats: {
                    games_played: this.playerData.games_played,
                    total_score: this.playerData.total_score,
                    total_lines: this.playerData.total_lines,
                    highest_score: this.playerData.highest_score
                }
            };
            
            await fetch(`${API_BASE}/auth.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'sync',
                    auth_token: this.authToken,
                    sync_data: syncData
                })
            });
            
            document.getElementById('lastSync').textContent = 'Just now';
        } catch (error) {
            console.error('Sync error:', error);
        }
    }
    
    async createGuestPlayer() {
        try {
            const response = await fetch(`${API_BASE}/progression.php`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    action: 'create_player',
                    username: 'guest_' + Date.now(),
                    display_name: 'Guest'
                })
            });
            
            const data = await response.json();
            if (data.success) {
                this.playerId = data.player_id;
                this.playerData = data.player;
                localStorage.setItem('player_id', this.playerId);
            }
        } catch (error) {
            console.error('Error creating guest player:', error);
        }
    }
}

// Create and export singleton instance
export const progressionManager = new ProgressionManager();