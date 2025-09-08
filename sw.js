// Tetris PWA Service Worker
const CACHE_NAME = 'tetris-v3.3.1';
const CACHE_VERSION = 'v3.3.1';
const API_CACHE = 'tetris-api-v3.3.1';
const AUDIO_CACHE = 'tetris-audio-v3.3.1';
const AUTH_CACHE = 'tetris-auth-v3.3.1';
const PROGRESSION_CACHE = 'tetris-progression-v3.3.1';
const DAILY_CACHE = 'tetris-daily-v3.3.1';

// Determine base path based on the service worker's location
const BASE_PATH = self.location.pathname.replace('/sw.js', '');
const ROOT_PATH = BASE_PATH ? BASE_PATH + '/' : '/';

// Resources to cache immediately on install
const STATIC_CACHE_URLS = [
    ROOT_PATH,
    ROOT_PATH + 'index.html',
    ROOT_PATH + 'manifest.json',
    ROOT_PATH + 'favicon.ico',
    ROOT_PATH + 'favicon.svg',
    ROOT_PATH + 'logo.svg',
    ROOT_PATH + 'favicon-192x192.png',
    ROOT_PATH + 'favicon-512x512.png',
    ROOT_PATH + 'apple-touch-icon.png',
    ROOT_PATH + 'styles/main.css',
    ROOT_PATH + 'styles/responsive.css',
    ROOT_PATH + 'styles/animations.css',
    ROOT_PATH + 'styles/modes.css',
    ROOT_PATH + 'styles/progression.css',
    ROOT_PATH + 'scripts/game.js',
    ROOT_PATH + 'scripts/pieces.js',
    ROOT_PATH + 'scripts/grid.js',
    ROOT_PATH + 'scripts/input.js',
    ROOT_PATH + 'scripts/ui.js',
    ROOT_PATH + 'scripts/audio.js',
    ROOT_PATH + 'scripts/modals.js',
    ROOT_PATH + 'scripts/leaderboard.js',
    ROOT_PATH + 'scripts/offline-storage.js',
    ROOT_PATH + 'scripts/storage-adapter.js',
    ROOT_PATH + 'scripts/modeSelector.js',
    ROOT_PATH + 'scripts/modes/gameMode.js',
    ROOT_PATH + 'scripts/modes/classicMode.js',
    ROOT_PATH + 'scripts/modes/sprintMode.js',
    ROOT_PATH + 'scripts/modes/marathonMode.js',
    ROOT_PATH + 'scripts/modes/zenMode.js',
    ROOT_PATH + 'scripts/modes/puzzleMode.js',
    ROOT_PATH + 'scripts/modes/battleMode.js',
    ROOT_PATH + 'scripts/modes/powerUpMode.js',
    ROOT_PATH + 'scripts/modes/battle2PMode.js',
    ROOT_PATH + 'scripts/modes/dailyChallengeMode.js',
    ROOT_PATH + 'scripts/puzzles/puzzleData.js',
    ROOT_PATH + 'scripts/puzzles/puzzleValidator.js',
    ROOT_PATH + 'scripts/puzzles/puzzleObjectives.js',
    ROOT_PATH + 'scripts/puzzles/hintSystem.js',
    ROOT_PATH + 'scripts/puzzles/puzzleAutoSolver.js',
    ROOT_PATH + 'scripts/puzzles/puzzleDifficultyAnalyzer.js',
    ROOT_PATH + 'scripts/puzzles/puzzleVerificationSuite.js',
    ROOT_PATH + 'scripts/ai/tetrisAI.js',
    ROOT_PATH + 'scripts/powerups/powerUpTypes.js',
    ROOT_PATH + 'scripts/powerups/powerUpManager.js',
    ROOT_PATH + 'scripts/challenges/dailyChallenge.js',
    ROOT_PATH + 'scripts/progression/playerProgression.js',
    ROOT_PATH + 'scripts/achievements/achievementSystem.js',
    ROOT_PATH + 'scripts/progression/progressionManager.js'
];

// Audio files to cache when available
const AUDIO_URLS = [
    // Add audio file paths here when they exist
    // '/tetris/audio/theme.mp3',
    // '/tetris/audio/move.ogg',
    // '/tetris/audio/rotate.ogg',
    // '/tetris/audio/drop.ogg',
    // '/tetris/audio/clear.ogg',
    // '/tetris/audio/tetris.ogg',
    // '/tetris/audio/gameover.ogg'
];

// Install event - cache all static resources
self.addEventListener('install', (event) => {
    console.log('[ServiceWorker] Installing...');
    
    event.waitUntil(
        caches.open(CACHE_NAME)
            .then(cache => {
                console.log('[ServiceWorker] Caching static assets');
                // Cache resources individually to handle failures gracefully
                return Promise.all(
                    STATIC_CACHE_URLS.map(url => {
                        return cache.add(url).catch(error => {
                            console.warn(`[ServiceWorker] Failed to cache ${url}:`, error);
                            // Continue even if individual resource fails
                            return Promise.resolve();
                        });
                    })
                );
            })
            .then(() => {
                console.log('[ServiceWorker] Install complete');
                // Skip waiting to activate immediately
                return self.skipWaiting();
            })
            .catch(error => {
                console.error('[ServiceWorker] Install failed:', error);
                // Still skip waiting even if some resources failed
                return self.skipWaiting();
            })
    );
});

// Activate event - clean up old caches
self.addEventListener('activate', (event) => {
    console.log('[ServiceWorker] Activating...');
    
    event.waitUntil(
        caches.keys().then(cacheNames => {
            return Promise.all(
                cacheNames
                    .filter(cacheName => {
                        // Delete old caches
                        return cacheName.startsWith('tetris-') && 
                               cacheName !== CACHE_NAME &&
                               cacheName !== API_CACHE &&
                               cacheName !== AUTH_CACHE &&
                               cacheName !== PROGRESSION_CACHE;
                    })
                    .map(cacheName => {
                        console.log('[ServiceWorker] Deleting old cache:', cacheName);
                        return caches.delete(cacheName);
                    })
            );
        }).then(() => {
            console.log('[ServiceWorker] Activate complete');
            // Take control of all pages immediately
            return self.clients.claim();
        })
    );
});

// Fetch event - serve from cache with network fallback
self.addEventListener('fetch', (event) => {
    const { request } = event;
    const url = new URL(request.url);
    
    // Skip non-HTTP(S) requests (like chrome-extension://)
    if (!request.url.startsWith('http://') && !request.url.startsWith('https://')) {
        return;
    }
    
    // Handle API requests differently
    if (url.pathname.includes('/api/')) {
        event.respondWith(handleApiRequest(request));
        return;
    }
    
    // For navigation requests, always serve the app shell
    if (request.mode === 'navigate') {
        event.respondWith(
            caches.match(ROOT_PATH + 'index.html')
                .then(response => response || fetch(ROOT_PATH + 'index.html'))
                .catch(() => caches.match(ROOT_PATH))
        );
        return;
    }
    
    // Handle audio files with cache-first strategy
    if (request.url.match(/\.(mp3|ogg|wav)$/)) {
        event.respondWith(
            caches.open(AUDIO_CACHE).then(cache => {
                return cache.match(request).then(response => {
                    if (response) {
                        return response;
                    }
                    // Cache audio files on first request
                    return fetch(request).then(networkResponse => {
                        if (networkResponse && networkResponse.status === 200) {
                            // Only cache http/https requests
                            if (request.url.startsWith('http://') || request.url.startsWith('https://')) {
                                cache.put(request, networkResponse.clone()).catch(err => {
                                    console.warn('[ServiceWorker] Audio cache put failed:', err);
                                });
                            }
                        }
                        return networkResponse;
                    }).catch(() => {
                        // Return empty audio if offline
                        return new Response('', {
                            headers: { 'Content-Type': 'audio/mpeg' }
                        });
                    });
                });
            })
        );
        return;
    }
    
    // For static assets, use cache-first strategy
    event.respondWith(
        caches.match(request)
            .then(cachedResponse => {
                if (cachedResponse) {
                    // Return cached version
                    return cachedResponse;
                }
                
                // Try with /tetris/ prefix if not found
                const tetrisUrl = `/tetris${url.pathname}`;
                return caches.match(tetrisUrl)
                    .then(tetrisResponse => {
                        if (tetrisResponse) {
                            return tetrisResponse;
                        }
                        
                        // Not in cache, fetch from network
                        return fetch(request)
                            .then(response => {
                                // Don't cache non-successful responses
                                if (!response || response.status !== 200 || response.type !== 'basic') {
                                    return response;
                                }
                                
                                // Clone the response before caching
                                const responseToCache = response.clone();
                                
                                // Only cache http/https requests
                                if (request.url.startsWith('http://') || request.url.startsWith('https://')) {
                                    caches.open(CACHE_NAME)
                                        .then(cache => {
                                            cache.put(request, responseToCache);
                                        })
                                        .catch(err => {
                                            console.warn('[ServiceWorker] Cache put failed:', err);
                                        });
                                }
                                
                                return response;
                            })
                            .catch(error => {
                                console.error('[ServiceWorker] Fetch failed:', error);
                                
                                // Return fallback responses for different file types
                                if (request.destination === 'document') {
                                    return caches.match('/tetris/index.html');
                                }
                                if (request.destination === 'script') {
                                    // Return empty module for missing scripts
                                    return new Response('', {
                                        headers: { 'Content-Type': 'application/javascript' }
                                    });
                                }
                                if (request.destination === 'style') {
                                    // Return empty stylesheet for missing styles
                                    return new Response('', {
                                        headers: { 'Content-Type': 'text/css' }
                                    });
                                }
                                if (request.destination === 'image') {
                                    // Return transparent 1x1 image for missing images
                                    return new Response('', {
                                        headers: { 'Content-Type': 'image/svg+xml' }
                                    });
                                }
                            });
                    });
            })
    );
});

// Handle API requests with network-first strategy
async function handleApiRequest(request) {
    const url = new URL(request.url);
    
    // Handle authentication requests specially
    if (url.pathname.includes('/api/auth.php')) {
        return handleAuthRequest(request);
    }
    
    // Handle progression requests specially
    if (url.pathname.includes('/api/progression.php')) {
        return handleProgressionRequest(request);
    }
    
    // Handle daily challenge requests specially
    if (url.pathname.includes('/api/daily-challenge.php')) {
        return handleDailyChallengeRequest(request);
    }
    
    try {
        // Try network first
        const networkResponse = await fetch(request);
        
        // Cache successful GET responses only (POST/PUT/DELETE should not be cached)
        if (networkResponse && networkResponse.status === 200 && request.method === 'GET') {
            const cache = await caches.open(API_CACHE);
            cache.put(request, networkResponse.clone()).catch(err => {
                console.warn('[ServiceWorker] API cache put failed:', err);
            });
        }
        
        return networkResponse;
    } catch (error) {
        console.log('[ServiceWorker] Network request failed, checking cache');
        
        // Network failed, try cache
        const cachedResponse = await caches.match(request);
        if (cachedResponse) {
            return cachedResponse;
        }
        
        // No cache available, return offline response
        return new Response(
            JSON.stringify({
                success: false,
                offline: true,
                error: 'You are offline. Scores will be saved locally and synced when online.'
            }),
            {
                status: 503,
                statusText: 'Service Unavailable',
                headers: new Headers({
                    'Content-Type': 'application/json'
                })
            }
        );
    }
}

// Handle authentication requests with offline support
async function handleAuthRequest(request) {
    const requestClone = request.clone();
    const body = await requestClone.json().catch(() => ({}));
    const action = body.action;
    
    try {
        // Try network first
        const networkResponse = await fetch(request);
        
        // Cache successful auth responses (except passwords)
        if (networkResponse && networkResponse.status === 200) {
            const responseClone = networkResponse.clone();
            const responseData = await responseClone.json();
            
            if (action === 'verify' || action === 'login') {
                // Cache user data without sensitive info
                const cache = await caches.open(AUTH_CACHE);
                const sanitizedData = { ...responseData };
                if (sanitizedData.player) {
                    delete sanitizedData.player.password_hash;
                }
                
                const cacheResponse = new Response(JSON.stringify(sanitizedData), {
                    headers: { 'Content-Type': 'application/json' }
                });
                
                cache.put(request.url + '_cached', cacheResponse).catch(err => {
                    console.warn('[ServiceWorker] Auth cache put failed:', err);
                });
            }
        }
        
        return networkResponse;
    } catch (error) {
        console.log('[ServiceWorker] Auth request failed, checking offline strategy');
        
        // Handle offline auth scenarios
        switch (action) {
            case 'verify':
                // Return cached verification if available
                const cache = await caches.open(AUTH_CACHE);
                const cachedAuth = await cache.match(request.url + '_cached');
                if (cachedAuth) {
                    return cachedAuth;
                }
                break;
                
            case 'register':
                // Queue registration for sync
                await queueForSync('auth-registration', body);
                return new Response(JSON.stringify({
                    success: false,
                    offline: true,
                    queued: true,
                    message: 'Registration queued. Will be processed when online.'
                }), {
                    headers: { 'Content-Type': 'application/json' }
                });
                
            case 'sync':
                // Queue sync data
                await queueForSync('progression-sync', body);
                return new Response(JSON.stringify({
                    success: true,
                    offline: true,
                    queued: true,
                    message: 'Data queued for sync.'
                }), {
                    headers: { 'Content-Type': 'application/json' }
                });
                
            case 'login':
                // Can't login offline
                return new Response(JSON.stringify({
                    success: false,
                    offline: true,
                    error: 'Login requires internet connection.'
                }), {
                    status: 503,
                    headers: { 'Content-Type': 'application/json' }
                });
        }
        
        // Default offline response
        return new Response(JSON.stringify({
            success: false,
            offline: true,
            error: 'Authentication service unavailable offline.'
        }), {
            status: 503,
            headers: { 'Content-Type': 'application/json' }
        });
    }
}

// Handle progression requests with offline support
async function handleProgressionRequest(request) {
    const requestClone = request.clone();
    const body = await requestClone.json().catch(() => ({}));
    const action = body.action;
    
    try {
        // Try network first
        const networkResponse = await fetch(request);
        
        // Cache successful progression responses
        if (networkResponse && networkResponse.status === 200) {
            const cache = await caches.open(PROGRESSION_CACHE);
            
            if (action === 'get_player' || action === 'get_achievements') {
                cache.put(request, networkResponse.clone()).catch(err => {
                    console.warn('[ServiceWorker] Progression cache put failed:', err);
                });
            }
        }
        
        return networkResponse;
    } catch (error) {
        console.log('[ServiceWorker] Progression request failed, checking offline strategy');
        
        // Check cache for GET requests
        if (action === 'get_player' || action === 'get_achievements') {
            const cache = await caches.open(PROGRESSION_CACHE);
            const cachedResponse = await cache.match(request);
            if (cachedResponse) {
                return cachedResponse;
            }
        }
        
        // Queue update requests for sync
        if (action === 'add_xp' || action === 'unlock_achievement' || action === 'update_player_stats') {
            await queueForSync('progression-update', body);
            return new Response(JSON.stringify({
                success: true,
                offline: true,
                queued: true,
                message: 'Progress saved offline. Will sync when online.'
            }), {
                headers: { 'Content-Type': 'application/json' }
            });
        }
        
        // Default offline response
        return new Response(JSON.stringify({
            success: false,
            offline: true,
            error: 'Progression data unavailable offline.'
        }), {
            status: 503,
            headers: { 'Content-Type': 'application/json' }
        });
    }
}

// Queue data for background sync
async function queueForSync(tag, data) {
    const cache = await caches.open('sync-queue');
    const timestamp = Date.now();
    const queueKey = `${tag}_${timestamp}`;
    
    const queueEntry = {
        tag,
        data,
        timestamp,
        attempts: 0
    };
    
    await cache.put(queueKey, new Response(JSON.stringify(queueEntry), {
        headers: { 'Content-Type': 'application/json' }
    }));
    
    // Register for background sync if available
    if ('sync' in self.registration) {
        await self.registration.sync.register(tag).catch(err => {
            console.warn('[ServiceWorker] Sync registration failed:', err);
        });
    }
}

// Background sync for score submission
self.addEventListener('sync', (event) => {
    console.log('[ServiceWorker] Background sync triggered:', event.tag);
    
    if (event.tag === 'sync-scores') {
        event.waitUntil(syncPendingScores());
    } else if (event.tag === 'auth-registration') {
        event.waitUntil(syncPendingRegistrations());
    } else if (event.tag === 'progression-sync') {
        event.waitUntil(syncPendingProgression());
    } else if (event.tag === 'progression-update') {
        event.waitUntil(syncProgressionUpdates());
    } else if (event.tag === 'daily-challenge-score') {
        event.waitUntil(syncDailyChallengeScores());
    }
});

// Sync pending scores when online
async function syncPendingScores() {
    console.log('[ServiceWorker] Syncing pending scores...');
    
    // Send message to all clients to trigger score sync
    const clients = await self.clients.matchAll();
    clients.forEach(client => {
        client.postMessage({
            type: 'SYNC_SCORES',
            message: 'Syncing pending scores...'
        });
    });
}

// Sync pending registrations
async function syncPendingRegistrations() {
    console.log('[ServiceWorker] Syncing pending registrations...');
    
    const cache = await caches.open('sync-queue');
    const requests = await cache.keys();
    
    for (const request of requests) {
        const url = new URL(request.url);
        if (url.pathname.includes('auth-registration')) {
            const response = await cache.match(request);
            const queueEntry = await response.json();
            
            try {
                // Attempt to register
                const result = await fetch('/api/auth.php', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(queueEntry.data)
                });
                
                if (result.ok) {
                    // Success - remove from queue
                    await cache.delete(request);
                    
                    // Notify clients
                    const clients = await self.clients.matchAll();
                    clients.forEach(client => {
                        client.postMessage({
                            type: 'REGISTRATION_SYNCED',
                            message: 'Registration completed successfully!'
                        });
                    });
                }
            } catch (error) {
                console.error('[ServiceWorker] Registration sync failed:', error);
                // Keep in queue for retry
            }
        }
    }
}

// Sync pending progression data
async function syncPendingProgression() {
    console.log('[ServiceWorker] Syncing pending progression data...');
    
    const cache = await caches.open('sync-queue');
    const requests = await cache.keys();
    
    for (const request of requests) {
        const url = new URL(request.url);
        if (url.pathname.includes('progression-sync')) {
            const response = await cache.match(request);
            const queueEntry = await response.json();
            
            try {
                // Attempt to sync
                const result = await fetch('/api/auth.php', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(queueEntry.data)
                });
                
                if (result.ok) {
                    // Success - remove from queue
                    await cache.delete(request);
                }
            } catch (error) {
                console.error('[ServiceWorker] Progression sync failed:', error);
                // Keep in queue for retry
            }
        }
    }
}

// Handle daily challenge requests with offline support
async function handleDailyChallengeRequest(request) {
    const requestClone = request.clone();
    const body = await requestClone.json().catch(() => ({}));
    const action = body.action;
    
    try {
        // Try network first
        const networkResponse = await fetch(request);
        
        // Cache successful responses for statistics
        if (networkResponse && networkResponse.status === 200) {
            if (action === 'getStats' || action === 'getLeaderboard') {
                const cache = await caches.open(DAILY_CACHE);
                cache.put(request, networkResponse.clone()).catch(err => {
                    console.warn('[ServiceWorker] Daily challenge cache put failed:', err);
                });
            }
        }
        
        return networkResponse;
    } catch (error) {
        console.log('[ServiceWorker] Daily challenge request failed, using offline mode');
        
        // Check cache for stats/leaderboard
        if (action === 'getStats' || action === 'getLeaderboard') {
            const cache = await caches.open(DAILY_CACHE);
            const cachedResponse = await cache.match(request);
            if (cachedResponse) {
                return cachedResponse;
            }
        }
        
        // Queue score submissions for sync
        if (action === 'submit') {
            await queueForSync('daily-challenge-score', body);
            return new Response(JSON.stringify({
                success: true,
                offline: true,
                queued: true,
                message: 'Score saved offline. Will submit when online.'
            }), {
                headers: { 'Content-Type': 'application/json' }
            });
        }
        
        // Default offline response
        return new Response(JSON.stringify({
            success: false,
            offline: true,
            error: 'Daily challenge data unavailable offline.'
        }), {
            status: 503,
            headers: { 'Content-Type': 'application/json' }
        });
    }
}

// Sync progression updates (XP, achievements, etc.)
async function syncProgressionUpdates() {
    console.log('[ServiceWorker] Syncing progression updates...');
    
    const cache = await caches.open('sync-queue');
    const requests = await cache.keys();
    let syncedCount = 0;
    
    for (const request of requests) {
        const url = new URL(request.url);
        if (url.pathname.includes('progression-update')) {
            const response = await cache.match(request);
            const queueEntry = await response.json();
            
            try {
                // Attempt to update
                const result = await fetch('/api/progression.php', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(queueEntry.data)
                });
                
                if (result.ok) {
                    // Success - remove from queue
                    await cache.delete(request);
                    syncedCount++;
                }
            } catch (error) {
                console.error('[ServiceWorker] Progression update sync failed:', error);
                // Keep in queue for retry
            }
        }
    }
    
    if (syncedCount > 0) {
        // Notify clients of successful sync
        const clients = await self.clients.matchAll();
        clients.forEach(client => {
            client.postMessage({
                type: 'PROGRESSION_SYNCED',
                message: `${syncedCount} progression updates synced!`
            });
        });
    }
}

// Sync daily challenge scores
async function syncDailyChallengeScores() {
    console.log('[ServiceWorker] Syncing daily challenge scores...');
    
    const cache = await caches.open('sync-queue');
    const requests = await cache.keys();
    let syncedCount = 0;
    
    for (const request of requests) {
        const url = new URL(request.url);
        if (url.pathname.includes('daily-challenge-score')) {
            const response = await cache.match(request);
            const queueEntry = await response.json();
            
            try {
                // Attempt to submit score
                const result = await fetch('/api/daily-challenge.php', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(queueEntry.data)
                });
                
                if (result.ok) {
                    // Success - remove from queue
                    await cache.delete(request);
                    syncedCount++;
                }
            } catch (error) {
                console.error('[ServiceWorker] Daily challenge sync failed:', error);
                // Keep in queue for retry
            }
        }
    }
    
    if (syncedCount > 0) {
        // Notify clients of successful sync
        const clients = await self.clients.matchAll();
        clients.forEach(client => {
            client.postMessage({
                type: 'DAILY_CHALLENGE_SYNCED',
                message: `${syncedCount} daily challenge score(s) synced!`
            });
        });
    }
}

// Handle messages from the main app
self.addEventListener('message', (event) => {
    if (event.data && event.data.type === 'SKIP_WAITING') {
        console.log('[ServiceWorker] Skip waiting requested');
        self.skipWaiting();
    }
    
    if (event.data && event.data.type === 'CACHE_UPDATE') {
        console.log('[ServiceWorker] Cache update requested');
        event.waitUntil(updateCache());
    }
});

// Update cache with latest resources
async function updateCache() {
    const cache = await caches.open(CACHE_NAME);
    
    try {
        // Re-fetch and cache all static resources
        const promises = STATIC_CACHE_URLS.map(async (url) => {
            try {
                const response = await fetch(url, { cache: 'no-cache' });
                if (response && response.status === 200) {
                    // Only cache http/https URLs
                    if (url.startsWith('http://') || url.startsWith('https://')) {
                        await cache.put(url, response).catch(err => {
                            console.warn(`[ServiceWorker] Failed to cache ${url}:`, err);
                        });
                        console.log(`[ServiceWorker] Updated ${url}`);
                    }
                }
            } catch (error) {
                console.warn(`[ServiceWorker] Failed to update ${url}:`, error);
            }
        });
        
        await Promise.all(promises);
        console.log('[ServiceWorker] Cache updated successfully');
        
        // Notify clients of update
        const clients = await self.clients.matchAll();
        clients.forEach(client => {
            client.postMessage({
                type: 'CACHE_UPDATED',
                message: 'App updated successfully'
            });
        });
    } catch (error) {
        console.error('[ServiceWorker] Cache update failed:', error);
    }
}

// Pre-cache on first visit
self.addEventListener('message', (event) => {
    if (event.data && event.data.type === 'INIT_CACHE') {
        event.waitUntil(
            caches.open(CACHE_NAME).then(cache => {
                return Promise.all(
                    STATIC_CACHE_URLS.map(url => {
                        return fetch(url)
                            .then(response => {
                                if (response.ok && (url.startsWith('http://') || url.startsWith('https://'))) {
                                    return cache.put(url, response).catch(err => {
                                        console.warn(`[ServiceWorker] Failed to cache audio ${url}:`, err);
                                    });
                                }
                            })
                            .catch(error => {
                                console.warn(`Failed to cache ${url}:`, error);
                            });
                    })
                );
            })
        );
    }
});

// Listen for push notifications (future feature)
self.addEventListener('push', (event) => {
    const options = {
        body: event.data ? event.data.text() : 'New high score achieved!',
        icon: '/favicon-192x192.png',
        badge: '/favicon-192x192.png',
        vibrate: [100, 50, 100],
        data: {
            dateOfArrival: Date.now(),
            primaryKey: 1
        }
    };
    
    event.waitUntil(
        self.registration.showNotification('Tetris PWA', options)
    );
});

// Handle notification clicks
self.addEventListener('notificationclick', (event) => {
    event.notification.close();
    
    event.waitUntil(
        clients.openWindow('/')
    );
});