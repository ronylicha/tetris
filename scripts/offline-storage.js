// Offline Storage Manager using IndexedDB
export class OfflineStorage {
    constructor() {
        this.dbName = 'TetrisOfflineDB';
        this.dbVersion = 1;
        this.db = null;
        this.isOnline = navigator.onLine;
        this.pendingSyncCount = 0;
        
        this.initializeDB();
        this.setupEventListeners();
        this.checkPendingScores();
    }
    
    // Initialize IndexedDB
    async initializeDB() {
        // Check if IndexedDB is available and not in private mode
        if (!window.indexedDB) {
            console.warn('IndexedDB not available');
            return null;
        }
        
        return new Promise((resolve, reject) => {
            try {
                const request = indexedDB.open(this.dbName, this.dbVersion);
                
                request.onerror = () => {
                    console.warn('Failed to open IndexedDB:', request.error);
                    // Don't reject, just resolve with null to fallback to localStorage
                    resolve(null);
                };
                
                request.onsuccess = () => {
                    this.db = request.result;
                    
                    // Handle database closing errors
                    this.db.onerror = (event) => {
                        console.error('Database error:', event.target.error);
                    };
                    
                    this.db.onabort = (event) => {
                        console.error('Database transaction aborted:', event.target.error);
                    };
                    
                    console.log('IndexedDB initialized successfully');
                    resolve(this.db);
                };
                
                request.onupgradeneeded = (event) => {
                    const db = event.target.result;
                    
                    // Create object stores if they don't exist
                    if (!db.objectStoreNames.contains('scores')) {
                        const scoresStore = db.createObjectStore('scores', { 
                            keyPath: 'id', 
                            autoIncrement: true 
                        });
                        // Only create index if it doesn't exist
                        if (!scoresStore.indexNames.contains('synced')) {
                            scoresStore.createIndex('synced', 'synced', { unique: false });
                        }
                        if (!scoresStore.indexNames.contains('timestamp')) {
                            scoresStore.createIndex('timestamp', 'timestamp', { unique: false });
                        }
                    }
                    
                    if (!db.objectStoreNames.contains('leaderboard_cache')) {
                        const cacheStore = db.createObjectStore('leaderboard_cache', { 
                            keyPath: 'type' 
                        });
                        if (!cacheStore.indexNames.contains('timestamp')) {
                            cacheStore.createIndex('timestamp', 'timestamp', { unique: false });
                        }
                    }
                    
                    console.log('IndexedDB schema created/updated');
                };
                
                request.onblocked = () => {
                    console.warn('IndexedDB blocked - close other tabs using this site');
                    resolve(null);
                };
            } catch (error) {
                console.warn('IndexedDB initialization error:', error);
                resolve(null);
            }
        });
    }
    
    // Setup online/offline event listeners
    setupEventListeners() {
        window.addEventListener('online', () => {
            console.log('Connection restored');
            this.isOnline = true;
            this.syncPendingScores();
            this.showNotification('Back online! Syncing scores...', 'success');
        });
        
        window.addEventListener('offline', () => {
            console.log('Connection lost');
            this.isOnline = false;
            this.showNotification('You are offline. Scores will be saved locally.', 'warning');
        });
        
        // Listen for messages from Service Worker
        if ('serviceWorker' in navigator) {
            navigator.serviceWorker.addEventListener('message', (event) => {
                if (event.data && event.data.type === 'SYNC_SCORES') {
                    this.syncPendingScores();
                }
            });
        }
    }
    
    // Save score locally
    async saveScoreLocally(scoreData) {
        if (!this.db) {
            await this.initializeDB();
        }
        
        const transaction = this.db.transaction(['scores'], 'readwrite');
        const store = transaction.objectStore('scores');
        
        const score = {
            ...scoreData,
            timestamp: Date.now(),
            synced: false
        };
        
        return new Promise((resolve, reject) => {
            const request = store.add(score);
            
            request.onsuccess = () => {
                console.log('Score saved locally:', request.result);
                this.pendingSyncCount++;
                this.updateSyncIndicator();
                resolve(request.result);
            };
            
            request.onerror = () => {
                console.error('Failed to save score locally:', request.error);
                reject(request.error);
            };
        });
    }
    
    // Get all unsynced scores
    async getUnsyncedScores() {
        if (!this.db) {
            await this.initializeDB();
        }
        
        // If still no DB (IndexedDB not available), return empty array
        if (!this.db) {
            console.warn('IndexedDB not available, cannot get unsynced scores');
            return [];
        }
        
        try {
            const transaction = this.db.transaction(['scores'], 'readonly');
            const store = transaction.objectStore('scores');
            
            return new Promise((resolve, reject) => {
                const request = store.getAll();
                
                request.onsuccess = () => {
                    // Filter for unsynced scores manually
                    const allScores = request.result || [];
                    const unsyncedScores = allScores.filter(score => score.synced === false);
                    resolve(unsyncedScores);
                };
                
                request.onerror = () => {
                    console.warn('Error getting unsynced scores:', request.error);
                    resolve([]); // Return empty array instead of rejecting
                };
            });
        } catch (error) {
            console.warn('Transaction error:', error);
            return [];
        }
    }
    
    // Mark score as synced
    async markScoreAsSynced(scoreId) {
        if (!this.db) {
            await this.initializeDB();
        }
        
        const transaction = this.db.transaction(['scores'], 'readwrite');
        const store = transaction.objectStore('scores');
        
        return new Promise((resolve, reject) => {
            const getRequest = store.get(scoreId);
            
            getRequest.onsuccess = () => {
                const score = getRequest.result;
                if (score) {
                    score.synced = true;
                    const updateRequest = store.put(score);
                    
                    updateRequest.onsuccess = () => {
                        this.pendingSyncCount = Math.max(0, this.pendingSyncCount - 1);
                        this.updateSyncIndicator();
                        resolve();
                    };
                    
                    updateRequest.onerror = () => reject(updateRequest.error);
                } else {
                    resolve();
                }
            };
            
            getRequest.onerror = () => reject(getRequest.error);
        });
    }
    
    // Sync pending scores with server
    async syncPendingScores() {
        if (!this.isOnline) {
            console.log('Cannot sync: offline');
            return;
        }
        
        const unsyncedScores = await this.getUnsyncedScores();
        
        if (unsyncedScores.length === 0) {
            console.log('No scores to sync');
            return;
        }
        
        console.log(`Syncing ${unsyncedScores.length} pending scores...`);
        this.showNotification(`Syncing ${unsyncedScores.length} pending scores...`, 'info');
        
        let syncedCount = 0;
        
        for (const score of unsyncedScores) {
            try {
                const response = await fetch('/tetris/api/scores.php?action=save', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        playerName: score.playerName,
                        score: score.score,
                        lines: score.lines,
                        level: score.level,
                        gameDuration: score.gameDuration,
                        specialAchievements: score.specialAchievements || {}
                    })
                });
                
                if (response.ok) {
                    await this.markScoreAsSynced(score.id);
                    syncedCount++;
                }
            } catch (error) {
                console.error('Failed to sync score:', error);
            }
        }
        
        if (syncedCount > 0) {
            this.showNotification(`${syncedCount} scores synced successfully!`, 'success');
        }
        
        return syncedCount;
    }
    
    // Cache leaderboard data
    async cacheLeaderboardData(type, data) {
        if (!this.db) {
            await this.initializeDB();
        }
        
        const transaction = this.db.transaction(['leaderboard_cache'], 'readwrite');
        const store = transaction.objectStore('leaderboard_cache');
        
        const cacheEntry = {
            type: type, // 'top-scores' or 'recent'
            data: data,
            timestamp: Date.now()
        };
        
        return new Promise((resolve, reject) => {
            const request = store.put(cacheEntry);
            
            request.onsuccess = () => {
                console.log(`Cached ${type} leaderboard data`);
                resolve();
            };
            
            request.onerror = () => {
                console.error('Failed to cache leaderboard data:', request.error);
                reject(request.error);
            };
        });
    }
    
    // Get cached leaderboard data
    async getCachedLeaderboardData(type) {
        if (!this.db) {
            await this.initializeDB();
        }
        
        const transaction = this.db.transaction(['leaderboard_cache'], 'readonly');
        const store = transaction.objectStore('leaderboard_cache');
        
        return new Promise((resolve, reject) => {
            const request = store.get(type);
            
            request.onsuccess = () => {
                const cached = request.result;
                if (cached) {
                    // Check if cache is less than 1 hour old
                    const isRecent = (Date.now() - cached.timestamp) < 3600000;
                    resolve({
                        data: cached.data,
                        timestamp: cached.timestamp,
                        isRecent: isRecent
                    });
                } else {
                    resolve(null);
                }
            };
            
            request.onerror = () => {
                reject(request.error);
            };
        });
    }
    
    // Get all local scores (synced and unsynced)
    async getAllLocalScores() {
        if (!this.db) {
            await this.initializeDB();
        }
        
        const transaction = this.db.transaction(['scores'], 'readonly');
        const store = transaction.objectStore('scores');
        
        return new Promise((resolve, reject) => {
            const request = store.getAll();
            
            request.onsuccess = () => {
                const scores = request.result || [];
                // Sort by score descending
                scores.sort((a, b) => b.score - a.score);
                resolve(scores);
            };
            
            request.onerror = () => {
                reject(request.error);
            };
        });
    }
    
    // Check for pending scores on startup
    async checkPendingScores() {
        try {
            const unsyncedScores = await this.getUnsyncedScores();
            this.pendingSyncCount = unsyncedScores.length;
            this.updateSyncIndicator();
            
            // Auto-sync if online
            if (this.isOnline && this.pendingSyncCount > 0) {
                setTimeout(() => this.syncPendingScores(), 2000);
            }
        } catch (error) {
            console.warn('Could not check pending scores:', error);
            this.pendingSyncCount = 0;
            this.updateSyncIndicator();
        }
    }
    
    // Update UI sync indicator
    updateSyncIndicator() {
        // Disabled - using mode-indicator in index.html instead
        // to avoid duplicate indicators
        return;
        
        /* Original code commented out to prevent conflicts
        const indicator = document.getElementById('offline-indicator');
        if (!indicator) {
            // Create indicator if it doesn't exist
            const div = document.createElement('div');
            div.id = 'offline-indicator';
            div.className = 'offline-indicator';
            document.body.appendChild(div);
        }
        
        const indicatorEl = document.getElementById('offline-indicator');
        
        if (!this.isOnline) {
            indicatorEl.innerHTML = `
                <span class="offline-icon">📴</span>
                <span>Offline Mode</span>
                ${this.pendingSyncCount > 0 ? `<span class="pending-count">${this.pendingSyncCount} pending</span>` : ''}
            `;
            indicatorEl.classList.add('visible', 'offline');
            indicatorEl.classList.remove('online');
        } else if (this.pendingSyncCount > 0) {
            indicatorEl.innerHTML = `
                <span class="sync-icon">🔄</span>
                <span>Syncing ${this.pendingSyncCount} scores...</span>
            `;
            indicatorEl.classList.add('visible', 'syncing');
            indicatorEl.classList.remove('offline', 'online');
        } else {
            indicatorEl.classList.remove('visible', 'offline', 'syncing');
            indicatorEl.classList.add('online');
        }
        */
    }
    
    // Show notification
    showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `offline-notification ${type}`;
        notification.textContent = message;
        
        document.body.appendChild(notification);
        
        // Animate in
        setTimeout(() => notification.classList.add('visible'), 10);
        
        // Remove after 3 seconds
        setTimeout(() => {
            notification.classList.remove('visible');
            setTimeout(() => notification.remove(), 300);
        }, 3000);
    }
    
    // Register background sync
    async registerBackgroundSync() {
        if ('serviceWorker' in navigator && 'SyncManager' in window) {
            try {
                const registration = await navigator.serviceWorker.ready;
                await registration.sync.register('sync-scores');
                console.log('Background sync registered');
            } catch (error) {
                console.error('Failed to register background sync:', error);
            }
        }
    }
}

// Export singleton instance
export const offlineStorage = new OfflineStorage();