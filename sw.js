// Tetris PWA Service Worker
const CACHE_NAME = 'tetris-v1.3.0';
const API_CACHE = 'tetris-api-v1';
const AUDIO_CACHE = 'tetris-audio-v1';

// Resources to cache immediately on install
const STATIC_CACHE_URLS = [
    '/tetris/',
    '/tetris/index.html',
    '/tetris/manifest.json',
    '/tetris/favicon.ico',
    '/tetris/favicon.svg',
    '/tetris/logo.svg',
    '/tetris/favicon-192x192.png',
    '/tetris/favicon-512x512.png',
    '/tetris/apple-touch-icon.png',
    '/tetris/styles/main.css',
    '/tetris/styles/responsive.css',
    '/tetris/styles/animations.css',
    '/tetris/styles/modes.css',
    '/tetris/scripts/game.js',
    '/tetris/scripts/pieces.js',
    '/tetris/scripts/grid.js',
    '/tetris/scripts/input.js',
    '/tetris/scripts/ui.js',
    '/tetris/scripts/audio.js',
    '/tetris/scripts/modals.js',
    '/tetris/scripts/leaderboard.js',
    '/tetris/scripts/offline-storage.js',
    '/tetris/scripts/storage-adapter.js',
    '/tetris/scripts/modeSelector.js',
    '/tetris/scripts/modes/gameMode.js',
    '/tetris/scripts/modes/classicMode.js',
    '/tetris/scripts/modes/sprintMode.js',
    '/tetris/scripts/modes/marathonMode.js',
    '/tetris/scripts/modes/zenMode.js',
    '/tetris/scripts/modes/puzzleMode.js',
    '/tetris/scripts/modes/battleMode.js',
    '/tetris/scripts/puzzles/puzzleData.js',
    '/tetris/scripts/ai/tetrisAI.js'
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
    
    // Handle API requests differently
    if (url.pathname.includes('/api/')) {
        event.respondWith(handleApiRequest(request));
        return;
    }
    
    // For navigation requests, always serve the app shell
    if (request.mode === 'navigate') {
        event.respondWith(
            caches.match('/tetris/index.html')
                .then(response => response || fetch('/tetris/index.html'))
                .catch(() => caches.match('/tetris/'))
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
                            cache.put(request, networkResponse.clone());
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
                                
                                caches.open(CACHE_NAME)
                                    .then(cache => {
                                        cache.put(request, responseToCache);
                                    });
                                
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
        
        // Cache successful responses
        if (networkResponse && networkResponse.status === 200) {
            const cache = await caches.open(API_CACHE);
            cache.put(request, networkResponse.clone());
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
                    await cache.put(url, response);
                    console.log(`[ServiceWorker] Updated ${url}`);
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
                                if (response.ok) {
                                    return cache.put(url, response);
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