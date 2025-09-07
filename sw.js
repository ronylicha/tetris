// Tetris PWA Service Worker
const CACHE_NAME = 'tetris-v1.3.7';
const CACHE_VERSION = 'v1.3.7';
const API_CACHE = 'tetris-api-v1';
const AUDIO_CACHE = 'tetris-audio-v1';

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
    ROOT_PATH + 'scripts/puzzles/puzzleData.js',
    ROOT_PATH + 'scripts/puzzles/puzzleValidator.js',
    ROOT_PATH + 'scripts/ai/tetrisAI.js'
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
                               cacheName !== API_CACHE;
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

// Background sync for score submission
self.addEventListener('sync', (event) => {
    console.log('[ServiceWorker] Background sync triggered');
    
    if (event.tag === 'sync-scores') {
        event.waitUntil(syncPendingScores());
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