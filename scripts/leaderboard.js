// Modern Tetris - Leaderboard Management System

export class LeaderboardManager {
    constructor() {
        this.apiBaseUrl = 'api/scores.php';
        this.currentTab = 'top-scores';
        this.initializeEventListeners();
    }

    initializeEventListeners() {
        // Tab switching
        document.querySelectorAll('.tab-button').forEach(button => {
            button.addEventListener('click', (e) => {
                this.switchTab(e.target.dataset.tab);
            });
        });

        // Close leaderboard
        const closeButton = document.getElementById('close-leaderboard');
        if (closeButton) {
            closeButton.addEventListener('click', () => {
                this.hideLeaderboard();
            });
        }

        // Close on overlay click
        const overlay = document.getElementById('leaderboard-overlay');
        if (overlay) {
            overlay.addEventListener('click', (e) => {
                if (e.target === overlay) {
                    this.hideLeaderboard();
                }
            });
        }
    }

    // Show leaderboard modal
    async showLeaderboard() {
        const overlay = document.getElementById('leaderboard-overlay');
        if (overlay) {
            overlay.style.display = 'flex';
            
            // Clear any active game inputs to prevent interference
            if (window.tetrisGame && window.tetrisGame.inputManager) {
                window.tetrisGame.inputManager.reset();
            }
            
            this.currentTab = 'top-scores';
            this.updateTabButtons();
            await this.loadLeaderboardData();
        }
    }

    // Hide leaderboard modal
    hideLeaderboard() {
        const overlay = document.getElementById('leaderboard-overlay');
        if (overlay) {
            overlay.style.display = 'none';
        }
    }

    // Switch between tabs
    async switchTab(tab) {
        if (tab === this.currentTab) return;
        
        this.currentTab = tab;
        this.updateTabButtons();
        await this.loadLeaderboardData();
    }

    // Update tab button states
    updateTabButtons() {
        document.querySelectorAll('.tab-button').forEach(button => {
            button.classList.toggle('active', button.dataset.tab === this.currentTab);
        });
    }

    // Load leaderboard data based on current tab
    async loadLeaderboardData() {
        const listContainer = document.getElementById('leaderboard-list');
        if (!listContainer) return;

        listContainer.innerHTML = '<div class="loading">Loading scores...</div>';

        try {
            let data;
            if (this.currentTab === 'top-scores') {
                data = await this.fetchTopScores();
            } else if (this.currentTab === 'recent') {
                data = await this.fetchRecentScores();
            }

            this.renderLeaderboard(data);
        } catch (error) {
            this.showError('Failed to load scores. Please try again later.');
        }
    }

    // Fetch top scores from API
    async fetchTopScores(limit = 50) {
        const response = await fetch(`${this.apiBaseUrl}?action=leaderboard&limit=${limit}`);
        const result = await response.json();
        
        if (!result.success) {
            throw new Error(result.error || 'Failed to fetch scores');
        }
        
        return result.data;
    }

    // Fetch recent scores from API
    async fetchRecentScores(limit = 20) {
        const response = await fetch(`${this.apiBaseUrl}?action=recent&limit=${limit}`);
        const result = await response.json();
        
        if (!result.success) {
            throw new Error(result.error || 'Failed to fetch scores');
        }
        
        return result.data;
    }

    // Render leaderboard entries
    renderLeaderboard(scores) {
        const listContainer = document.getElementById('leaderboard-list');
        if (!listContainer || !scores || scores.length === 0) {
            listContainer.innerHTML = '<div class="loading">No scores available yet.</div>';
            return;
        }

        const entriesHTML = scores.map((score, index) => {
            const rank = score.rank || (index + 1);
            const rankClass = this.getRankClass(rank);
            const date = new Date(score.date_achieved);
            const achievements = this.formatAchievements(score.special_achievements);
            
            return `
                <div class="leaderboard-entry">
                    <div class="entry-rank ${rankClass}">${this.formatRank(rank)}</div>
                    <div class="entry-info">
                        <div class="entry-name">${this.escapeHtml(score.player_name)}</div>
                        <div class="entry-date">${date.toLocaleDateString()}</div>
                        ${achievements ? `<div class="entry-achievements">${achievements}</div>` : ''}
                    </div>
                    <div class="entry-score">${score.score.toLocaleString()}</div>
                    <div class="entry-lines">${score.lines}</div>
                    <div class="entry-level">${score.level}</div>
                </div>
            `;
        }).join('');

        listContainer.innerHTML = entriesHTML;
    }

    // Get CSS class for rank styling
    getRankClass(rank) {
        if (rank === 1) return 'gold';
        if (rank === 2) return 'silver';
        if (rank === 3) return 'bronze';
        return '';
    }

    // Format rank number with trophy for top 3
    formatRank(rank) {
        if (rank === 1) return '🥇';
        if (rank === 2) return '🥈';
        if (rank === 3) return '🥉';
        return rank;
    }

    // Format special achievements
    formatAchievements(achievements) {
        if (!achievements || typeof achievements !== 'object') return '';
        
        const parts = [];
        if (achievements.tspins > 0) parts.push(`${achievements.tspins} T-Spins`);
        if (achievements.tetris > 0) parts.push(`${achievements.tetris} Tetris`);
        if (achievements.combos > 0) parts.push(`${achievements.combos} Combos`);
        
        return parts.length > 0 ? parts.join(' • ') : '';
    }

    // Show error message
    showError(message) {
        const listContainer = document.getElementById('leaderboard-list');
        if (listContainer) {
            listContainer.innerHTML = `<div class="error-message">${this.escapeHtml(message)}</div>`;
        }
    }

    // Escape HTML to prevent XSS
    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }
}

// Score Saving System
export class ScoreSaver {
    constructor() {
        this.apiBaseUrl = 'api/scores.php';
        this.gameStartTime = Date.now();
        this.initializeEventListeners();
    }

    initializeEventListeners() {
        // Save score button
        const saveButton = document.getElementById('save-score-button');
        if (saveButton) {
            saveButton.addEventListener('click', () => {
                this.saveCurrentScore();
            });
        }

        // Skip save button
        const skipButton = document.getElementById('skip-save-button');
        if (skipButton) {
            skipButton.addEventListener('click', () => {
                this.hideNameInput();
            });
        }

        // Enter key in name input
        const nameInput = document.getElementById('player-name-input');
        if (nameInput) {
            nameInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter') {
                    this.saveCurrentScore();
                }
            });
        }

        // Close on overlay click
        const overlay = document.getElementById('name-input-overlay');
        if (overlay) {
            overlay.addEventListener('click', (e) => {
                if (e.target === overlay) {
                    this.hideNameInput();
                }
            });
        }
    }

    // Show name input dialog with score data
    showNameInput(gameStats, specialAchievements = {}) {
        this.currentGameStats = gameStats;
        this.currentAchievements = specialAchievements;
        
        // Update score display
        document.getElementById('final-score').textContent = gameStats.score.toLocaleString();
        document.getElementById('final-lines').textContent = gameStats.lines;
        document.getElementById('final-level').textContent = gameStats.level;
        
        // Load saved player name if available
        this.loadSavedPlayerName();
        
        // Show modal
        const overlay = document.getElementById('name-input-overlay');
        if (overlay) {
            overlay.style.display = 'flex';
            
            // Clear any active game inputs to prevent interference
            if (window.tetrisGame && window.tetrisGame.inputManager) {
                window.tetrisGame.inputManager.reset();
            }
            
            // Focus name input and select existing text if any
            setTimeout(() => {
                const nameInput = document.getElementById('player-name-input');
                if (nameInput) {
                    nameInput.focus();
                    if (nameInput.value) {
                        nameInput.select();
                    }
                }
            }, 300);
        }
    }

    // Hide name input dialog
    hideNameInput() {
        const overlay = document.getElementById('name-input-overlay');
        if (overlay) {
            overlay.style.display = 'none';
        }
        
        // Clear input
        const nameInput = document.getElementById('player-name-input');
        if (nameInput) {
            nameInput.value = '';
        }
    }

    // Save current score to database
    async saveCurrentScore() {
        const nameInput = document.getElementById('player-name-input');
        const playerName = nameInput ? nameInput.value.trim() : '';
        
        if (!playerName) {
            nameInput.focus();
            nameInput.style.borderColor = 'var(--neon-red)';
            setTimeout(() => {
                nameInput.style.borderColor = '';
            }, 2000);
            return;
        }

        const saveButton = document.getElementById('save-score-button');
        if (saveButton) {
            saveButton.textContent = 'Saving...';
            saveButton.disabled = true;
        }

        try {
            const gameDuration = Math.floor((Date.now() - this.gameStartTime) / 1000);
            
            const scoreData = {
                playerName: playerName,
                score: this.currentGameStats.score,
                lines: this.currentGameStats.lines,
                level: this.currentGameStats.level,
                gameDuration: gameDuration,
                specialAchievements: this.currentAchievements
            };

            const response = await fetch(this.apiBaseUrl + '?action=save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(scoreData)
            });

            const result = await response.json();

            if (result.success) {
                // Show success message
                this.showSaveSuccess(result.data.rank);
                
                // Store player name for next time
                localStorage.setItem('tetris_player_name', playerName);
                
                // Hide modal after delay
                setTimeout(() => {
                    this.hideNameInput();
                }, 2000);
            } else {
                throw new Error(result.error || 'Failed to save score');
            }
        } catch (error) {
            this.showSaveError(error.message);
        } finally {
            if (saveButton) {
                saveButton.textContent = 'Save Score';
                saveButton.disabled = false;
            }
        }
    }

    // Show save success message
    showSaveSuccess(rank) {
        const saveButton = document.getElementById('save-score-button');
        if (saveButton) {
            saveButton.textContent = `Saved! Rank #${rank}`;
            saveButton.style.background = 'var(--neon-green)';
        }
    }

    // Show save error message
    showSaveError(message) {
        const saveButton = document.getElementById('save-score-button');
        if (saveButton) {
            saveButton.textContent = 'Error - Try Again';
            saveButton.style.background = 'var(--neon-red)';
            setTimeout(() => {
                saveButton.textContent = 'Save Score';
                saveButton.style.background = '';
            }, 3000);
        }
    }

    // Check if score qualifies for high score
    async isHighScore(score) {
        try {
            const scores = await this.fetchTopScores(50);
            return scores.length < 50 || score > scores[scores.length - 1].score;
        } catch (error) {
            // If we can't check, assume it's worth saving
            return true;
        }
    }

    async fetchTopScores(limit) {
        const response = await fetch(`${this.apiBaseUrl}?action=leaderboard&limit=${limit}`);
        const result = await response.json();
        return result.success ? result.data : [];
    }

    // Set game start time
    setGameStartTime(time = Date.now()) {
        this.gameStartTime = time;
    }

    // Load saved player name
    loadSavedPlayerName() {
        const savedName = localStorage.getItem('tetris_player_name');
        const nameInput = document.getElementById('player-name-input');
        if (savedName && nameInput) {
            nameInput.value = savedName;
        }
        return savedName;
    }
}