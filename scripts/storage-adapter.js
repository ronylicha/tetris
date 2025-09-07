// Unified Storage Adapter - Works in both file:// and http:// contexts
export class StorageAdapter {
    constructor() {
        // Detect execution context
        this.isFileProtocol = window.location.protocol === 'file:';
        this.hasServiceWorker = 'serviceWorker' in navigator && !this.isFileProtocol;
        this.hasIndexedDB = 'indexedDB' in window && !this.isFileProtocol;
        
        // Choose storage method based on context
        this.storageMethod = this.isFileProtocol ? 'localStorage' : 'hybrid';
        
        // Storage limits
        this.localStorageLimit = 5 * 1024 * 1024; // 5MB typical limit
        this.compressionEnabled = true;
        
        // Initialize IndexedDB if available
        this.db = null;
        this.dbName = 'TetrisUnifiedDB';
        this.dbVersion = 1;
        
        if (this.hasIndexedDB) {
            this.initIndexedDB();
        }
        
        console.log(`Storage Adapter initialized - Mode: ${this.storageMethod}, File Protocol: ${this.isFileProtocol}`);
    }
    
    // Initialize IndexedDB for http:// context
    async initIndexedDB() {
        return new Promise((resolve, reject) => {
            const request = indexedDB.open(this.dbName, this.dbVersion);
            
            request.onerror = () => {
                console.warn('IndexedDB not available, falling back to localStorage');
                this.storageMethod = 'localStorage';
                resolve(null);
            };
            
            request.onsuccess = () => {
                this.db = request.result;
                console.log('IndexedDB initialized successfully');
                resolve(this.db);
            };
            
            request.onupgradeneeded = (event) => {
                const db = event.target.result;
                
                // Create stores for different data types
                if (!db.objectStoreNames.contains('gameStates')) {
                    db.createObjectStore('gameStates', { keyPath: 'key' });
                }
                if (!db.objectStoreNames.contains('scores')) {
                    db.createObjectStore('scores', { keyPath: 'id', autoIncrement: true });
                }
                if (!db.objectStoreNames.contains('settings')) {
                    db.createObjectStore('settings', { keyPath: 'key' });
                }
            };
        });
    }
    
    // Compress data if needed (for localStorage)
    compress(data) {
        if (!this.compressionEnabled) return data;
        
        try {
            // Simple compression using JSON stringify and base64
            const jsonStr = JSON.stringify(data);
            // For better compression, we could use LZ-string library if available
            if (typeof LZString !== 'undefined') {
                return LZString.compressToUTF16(jsonStr);
            }
            return jsonStr;
        } catch (e) {
            console.error('Compression failed:', e);
            return JSON.stringify(data);
        }
    }
    
    // Decompress data
    decompress(data) {
        if (!this.compressionEnabled || !data) return null;
        
        try {
            // Check if LZ-string is available
            if (typeof LZString !== 'undefined' && data.charAt(0) !== '{' && data.charAt(0) !== '[') {
                const decompressed = LZString.decompressFromUTF16(data);
                return JSON.parse(decompressed);
            }
            return JSON.parse(data);
        } catch (e) {
            console.error('Decompression failed:', e);
            return null;
        }
    }
    
    // Save data - unified interface
    async save(key, data, store = 'gameStates') {
        if (this.storageMethod === 'localStorage' || !this.db) {
            return this.saveToLocalStorage(key, data);
        } else {
            return this.saveToIndexedDB(key, data, store);
        }
    }
    
    // Load data - unified interface
    async load(key, store = 'gameStates') {
        if (this.storageMethod === 'localStorage' || !this.db) {
            return this.loadFromLocalStorage(key);
        } else {
            return this.loadFromIndexedDB(key, store);
        }
    }
    
    // Delete data - unified interface
    async delete(key, store = 'gameStates') {
        if (this.storageMethod === 'localStorage' || !this.db) {
            return this.deleteFromLocalStorage(key);
        } else {
            return this.deleteFromIndexedDB(key, store);
        }
    }
    
    // LocalStorage operations
    saveToLocalStorage(key, data) {
        try {
            const compressed = this.compress(data);
            const fullKey = `tetris_${key}`;
            
            // Check storage quota
            const dataSize = compressed.length * 2; // Rough estimate (UTF-16)
            if (dataSize > this.localStorageLimit) {
                console.warn(`Data too large for localStorage (${dataSize} bytes)`);
                // Try to clean old data
                this.cleanOldLocalStorageData();
            }
            
            localStorage.setItem(fullKey, compressed);
            return Promise.resolve(true);
        } catch (e) {
            console.error('localStorage save failed:', e);
            if (e.name === 'QuotaExceededError') {
                this.cleanOldLocalStorageData();
                // Retry once
                try {
                    const compressed = this.compress(data);
                    localStorage.setItem(`tetris_${key}`, compressed);
                    return Promise.resolve(true);
                } catch (retryError) {
                    return Promise.reject(retryError);
                }
            }
            return Promise.reject(e);
        }
    }
    
    loadFromLocalStorage(key) {
        try {
            const fullKey = `tetris_${key}`;
            const compressed = localStorage.getItem(fullKey);
            if (!compressed) return Promise.resolve(null);
            
            const data = this.decompress(compressed);
            return Promise.resolve(data);
        } catch (e) {
            console.error('localStorage load failed:', e);
            return Promise.resolve(null);
        }
    }
    
    deleteFromLocalStorage(key) {
        try {
            const fullKey = `tetris_${key}`;
            localStorage.removeItem(fullKey);
            return Promise.resolve(true);
        } catch (e) {
            console.error('localStorage delete failed:', e);
            return Promise.reject(e);
        }
    }
    
    // IndexedDB operations
    async saveToIndexedDB(key, data, storeName) {
        if (!this.db) {
            // Fallback to localStorage if IndexedDB not available
            return this.saveToLocalStorage(key, data);
        }
        
        return new Promise((resolve, reject) => {
            const transaction = this.db.transaction([storeName], 'readwrite');
            const store = transaction.objectStore(storeName);
            
            const record = {
                key: key,
                data: data,
                timestamp: Date.now()
            };
            
            const request = store.put(record);
            
            request.onsuccess = () => resolve(true);
            request.onerror = () => {
                console.error('IndexedDB save failed:', request.error);
                // Fallback to localStorage
                this.saveToLocalStorage(key, data).then(resolve).catch(reject);
            };
        });
    }
    
    async loadFromIndexedDB(key, storeName) {
        if (!this.db) {
            // Fallback to localStorage if IndexedDB not available
            return this.loadFromLocalStorage(key);
        }
        
        return new Promise((resolve, reject) => {
            const transaction = this.db.transaction([storeName], 'readonly');
            const store = transaction.objectStore(storeName);
            const request = store.get(key);
            
            request.onsuccess = () => {
                const result = request.result;
                resolve(result ? result.data : null);
            };
            
            request.onerror = () => {
                console.error('IndexedDB load failed:', request.error);
                // Fallback to localStorage
                this.loadFromLocalStorage(key).then(resolve).catch(reject);
            };
        });
    }
    
    async deleteFromIndexedDB(key, storeName) {
        if (!this.db) {
            // Fallback to localStorage if IndexedDB not available
            return this.deleteFromLocalStorage(key);
        }
        
        return new Promise((resolve, reject) => {
            const transaction = this.db.transaction([storeName], 'readwrite');
            const store = transaction.objectStore(storeName);
            const request = store.delete(key);
            
            request.onsuccess = () => resolve(true);
            request.onerror = () => {
                console.error('IndexedDB delete failed:', request.error);
                reject(request.error);
            };
        });
    }
    
    // Clean old localStorage data to free space
    cleanOldLocalStorageData() {
        const keysToCheck = [];
        for (let i = 0; i < localStorage.length; i++) {
            const key = localStorage.key(i);
            if (key && key.startsWith('tetris_')) {
                keysToCheck.push(key);
            }
        }
        
        // Sort by timestamp if available, otherwise delete oldest entries
        const itemsWithTime = keysToCheck.map(key => {
            try {
                const data = localStorage.getItem(key);
                const parsed = this.decompress(data);
                return {
                    key,
                    timestamp: parsed?.timestamp || 0,
                    size: data.length * 2
                };
            } catch {
                return { key, timestamp: 0, size: 0 };
            }
        });
        
        // Sort by timestamp (oldest first)
        itemsWithTime.sort((a, b) => a.timestamp - b.timestamp);
        
        // Remove oldest 25% of items
        const toRemove = Math.floor(itemsWithTime.length * 0.25);
        for (let i = 0; i < toRemove; i++) {
            localStorage.removeItem(itemsWithTime[i].key);
            console.log(`Cleaned old data: ${itemsWithTime[i].key}`);
        }
    }
    
    // Get storage info
    async getStorageInfo() {
        const info = {
            method: this.storageMethod,
            isFileProtocol: this.isFileProtocol,
            hasServiceWorker: this.hasServiceWorker,
            hasIndexedDB: this.hasIndexedDB
        };
        
        if (this.storageMethod === 'localStorage') {
            let totalSize = 0;
            let itemCount = 0;
            
            for (let i = 0; i < localStorage.length; i++) {
                const key = localStorage.key(i);
                if (key && key.startsWith('tetris_')) {
                    const value = localStorage.getItem(key);
                    totalSize += (key.length + value.length) * 2; // UTF-16
                    itemCount++;
                }
            }
            
            info.localStorage = {
                itemCount,
                estimatedSize: totalSize,
                estimatedSizeMB: (totalSize / (1024 * 1024)).toFixed(2)
            };
        }
        
        if (this.db && navigator.storage && navigator.storage.estimate) {
            const estimate = await navigator.storage.estimate();
            info.indexedDB = {
                usage: estimate.usage,
                quota: estimate.quota,
                usageMB: (estimate.usage / (1024 * 1024)).toFixed(2),
                quotaMB: (estimate.quota / (1024 * 1024)).toFixed(2)
            };
        }
        
        return info;
    }
    
    // Check if running in offline-capable context
    isOfflineCapable() {
        return true; // Always capable with this adapter
    }
    
    // Get appropriate asset path based on context
    getAssetPath(path) {
        if (this.isFileProtocol) {
            // For file:// protocol, use relative paths
            return path.startsWith('/') ? '.' + path : path;
        }
        return path;
    }
}

// Create singleton instance
export const storage = new StorageAdapter();

// Expose globally for debugging
if (typeof window !== 'undefined') {
    window.TetrisStorage = storage;
}