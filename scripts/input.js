// Modern Tetris - Input Management System

export class InputManager {
    constructor(game) {
        this.game = game;
        this.keys = {};
        this.keyBindings = {
            // Movement
            'ArrowLeft': 'moveLeft',
            'ArrowRight': 'moveRight',
            'ArrowDown': 'softDrop',
            'ArrowUp': 'rotateClockwise',
            
            // Alternative controls
            'KeyA': 'moveLeft',
            'KeyD': 'moveRight',
            'KeyS': 'softDrop',
            'KeyW': 'rotateClockwise',
            'KeyQ': 'rotateCounterclockwise',
            'KeyE': 'rotateClockwise',
            
            // Special actions
            'Space': 'hardDrop',
            'KeyC': 'hold',
            'KeyP': 'pause',
            'Escape': 'pause',
            'KeyR': 'restart',
            'Enter': 'confirm'
        };
        
        // DAS (Delayed Auto Shift) settings
        this.das = {
            delay: 100,     // 100ms initial delay (more responsive)
            repeat: 20,     // 20ms repeat rate (50Hz, much faster)
            leftTime: 0,
            rightTime: 0,
            downTime: 0
        };
        
        // Track if first press was handled
        this.firstPress = {
            left: false,
            right: false,
            down: false
        };
        
        this.touchControls = {
            startX: 0,
            startY: 0,
            threshold: 30,
            isActive: false
        };
        
        this.initializeEventListeners();
    }

    initializeEventListeners() {
        // Keyboard events
        document.addEventListener('keydown', (e) => this.handleKeyDown(e));
        document.addEventListener('keyup', (e) => this.handleKeyUp(e));
        
        // Prevent default for game controls and handle key repeat issues
        document.addEventListener('keydown', (e) => {
            // Don't prevent default when modals are active to allow typing
            if (this.isNameInputActive() || this.isLeaderboardActive()) {
                return;
            }
            
            if (this.keyBindings[e.code]) {
                e.preventDefault();
                // Also stop propagation to prevent multiple handlers
                e.stopImmediatePropagation();
            }
        });
        
        // Touch events for mobile
        document.addEventListener('touchstart', (e) => this.handleTouchStart(e));
        document.addEventListener('touchmove', (e) => this.handleTouchMove(e));
        document.addEventListener('touchend', (e) => this.handleTouchEnd(e));
        
        // Mouse events for canvas interactions
        const canvas = document.getElementById('game-canvas');
        if (canvas) {
            canvas.addEventListener('click', (e) => this.handleCanvasClick(e));
        }
        
        // Focus management
        window.addEventListener('blur', () => this.handleWindowBlur());
        window.addEventListener('focus', () => this.handleWindowFocus());
    }

    handleKeyDown(e) {
        // Check if name input or leaderboard is active - if so, ignore game controls
        if (this.isNameInputActive() || this.isLeaderboardActive()) {
            // Only allow specific keys during name input
            if (this.isNameInputActive()) {
                // Allow normal typing, Enter for save, Escape to close
                if (e.code === 'Enter') {
                    const saveButton = document.getElementById('save-score-button');
                    if (saveButton) saveButton.click();
                    return;
                } else if (e.code === 'Escape') {
                    const skipButton = document.getElementById('skip-save-button');
                    if (skipButton) skipButton.click();
                    return;
                }
                // Let all other keys pass through for typing
                return;
            }
            
            // For leaderboard, only allow Escape to close
            if (this.isLeaderboardActive() && e.code === 'Escape') {
                const closeButton = document.getElementById('close-leaderboard');
                if (closeButton) closeButton.click();
                return;
            }
            
            // Block all other game controls when modals are open
            e.preventDefault();
            return;
        }
        
        const action = this.keyBindings[e.code];
        if (!action) return;
        
        // Prevent key repeat for certain actions
        if (this.keys[e.code] && this.isInstantAction(action)) {
            return;
        }
        
        const wasPressed = this.keys[e.code];
        this.keys[e.code] = true;
        
        // Handle instant actions immediately
        if (this.isInstantAction(action)) {
            this.executeAction(action);
        } else if (!wasPressed) {
            // For movement actions, execute immediately on first press
            if (action === 'moveLeft') {
                this.executeAction(action);
                this.firstPress.left = true;
                this.das.leftTime = 0;
            } else if (action === 'moveRight') {
                this.executeAction(action);
                this.firstPress.right = true;
                this.das.rightTime = 0;
            } else if (action === 'softDrop') {
                this.executeAction(action);
                this.firstPress.down = true;
                this.das.downTime = 0;
            }
        }
    }

    handleKeyUp(e) {
        // Check if name input or leaderboard is active - if so, ignore game key releases
        if (this.isNameInputActive() || this.isLeaderboardActive()) {
            return;
        }
        
        this.keys[e.code] = false;
        
        // Reset DAS timers and first press flags
        const action = this.keyBindings[e.code];
        if (action === 'moveLeft') {
            this.das.leftTime = 0;
            this.firstPress.left = false;
        }
        if (action === 'moveRight') {
            this.das.rightTime = 0;
            this.firstPress.right = false;
        }
        if (action === 'softDrop') {
            this.das.downTime = 0;
            this.firstPress.down = false;
        }
    }

    handleTouchStart(e) {
        e.preventDefault();
        const touch = e.touches[0];
        this.touchControls.startX = touch.clientX;
        this.touchControls.startY = touch.clientY;
        this.touchControls.isActive = true;
    }

    handleTouchMove(e) {
        if (!this.touchControls.isActive) return;
        e.preventDefault();
    }

    handleTouchEnd(e) {
        if (!this.touchControls.isActive) return;
        e.preventDefault();
        
        const touch = e.changedTouches[0];
        const deltaX = touch.clientX - this.touchControls.startX;
        const deltaY = touch.clientY - this.touchControls.startY;
        const threshold = this.touchControls.threshold;
        
        // Determine gesture
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            // Horizontal swipe
            if (Math.abs(deltaX) > threshold) {
                if (deltaX > 0) {
                    this.executeAction('moveRight');
                } else {
                    this.executeAction('moveLeft');
                }
            }
        } else {
            // Vertical swipe
            if (Math.abs(deltaY) > threshold) {
                if (deltaY > 0) {
                    this.executeAction('softDrop');
                } else {
                    this.executeAction('hardDrop');
                }
            } else {
                // Tap - rotate
                this.executeAction('rotateClockwise');
            }
        }
        
        this.touchControls.isActive = false;
    }

    handleCanvasClick(e) {
        // Handle canvas clicks for piece placement or rotation
        const rect = e.target.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        
        // Convert to grid coordinates
        const cellSize = rect.width / 10; // Assuming 10 columns
        const gridX = Math.floor(x / cellSize);
        const gridY = Math.floor(y / cellSize);
        
        // For now, just rotate on click
        this.executeAction('rotateClockwise');
    }

    handleWindowBlur() {
        // Clear all keys when window loses focus
        this.keys = {};
        // Reset DAS and first press flags
        this.das.leftTime = 0;
        this.das.rightTime = 0;
        this.das.downTime = 0;
        this.firstPress.left = false;
        this.firstPress.right = false;
        this.firstPress.down = false;
        
        // Auto-pause if game is active
        if (this.game && this.game.state === 'playing') {
            this.game.pause();
        }
    }

    handleWindowFocus() {
        // Reset DAS timers and first press flags
        this.das.leftTime = 0;
        this.das.rightTime = 0;
        this.das.downTime = 0;
        this.firstPress.left = false;
        this.firstPress.right = false;
        this.firstPress.down = false;
    }

    // Update input state (called every frame)
    update(deltaTime) {
        if (!this.game || this.game.state !== 'playing') return;
        
        // Check if name input modal is active - if so, disable game controls
        if (this.isNameInputActive() || this.isLeaderboardActive()) return;
        
        // Handle DAS (Delayed Auto Shift)
        this.updateDAS(deltaTime);
    }

    updateDAS(deltaTime) {
        // Left movement
        if (this.keys['ArrowLeft'] || this.keys['KeyA']) {
            if (this.firstPress.left) {
                this.das.leftTime += deltaTime;
                if (this.das.leftTime >= this.das.delay) {
                    // After delay, start repeating
                    const repeatTime = this.das.leftTime - this.das.delay;
                    if (repeatTime >= this.das.repeat) {
                        this.executeAction('moveLeft');
                        this.das.leftTime = this.das.delay; // Reset repeat timer
                    }
                }
            }
        }
        
        // Right movement
        if (this.keys['ArrowRight'] || this.keys['KeyD']) {
            if (this.firstPress.right) {
                this.das.rightTime += deltaTime;
                if (this.das.rightTime >= this.das.delay) {
                    // After delay, start repeating
                    const repeatTime = this.das.rightTime - this.das.delay;
                    if (repeatTime >= this.das.repeat) {
                        this.executeAction('moveRight');
                        this.das.rightTime = this.das.delay; // Reset repeat timer
                    }
                }
            }
        }
        
        // Soft drop
        if (this.keys['ArrowDown'] || this.keys['KeyS']) {
            if (this.firstPress.down) {
                this.das.downTime += deltaTime;
                if (this.das.downTime >= this.das.repeat) {
                    this.executeAction('softDrop');
                    this.das.downTime = 0;
                }
            }
        }
    }

    // Execute game action
    executeAction(action) {
        if (!this.game) return;
        
        switch (action) {
            case 'moveLeft':
                this.game.movePiece(-1, 0);
                break;
            case 'moveRight':
                this.game.movePiece(1, 0);
                break;
            case 'softDrop':
                this.game.softDrop();
                break;
            case 'hardDrop':
                this.game.hardDrop();
                break;
            case 'rotateClockwise':
                this.game.rotatePiece(1);
                break;
            case 'rotateCounterclockwise':
                this.game.rotatePiece(-1);
                break;
            case 'hold':
                this.game.holdPiece();
                break;
            case 'pause':
                this.game.togglePause();
                break;
            case 'restart':
                this.game.restart();
                break;
            case 'confirm':
                this.game.handleConfirm();
                break;
        }
    }

    // Check if action should only happen once per keypress
    isInstantAction(action) {
        return [
            'rotateClockwise',
            'rotateCounterclockwise',
            'hardDrop',
            'hold',
            'pause',
            'restart',
            'confirm'
        ].includes(action);
    }

    // Get current input state
    getInputState() {
        return {
            left: this.keys['ArrowLeft'] || this.keys['KeyA'],
            right: this.keys['ArrowRight'] || this.keys['KeyD'],
            down: this.keys['ArrowDown'] || this.keys['KeyS'],
            rotateClockwise: this.keys['ArrowUp'] || this.keys['KeyW'] || this.keys['KeyE'],
            rotateCounterclockwise: this.keys['KeyQ'],
            hardDrop: this.keys['Space'],
            hold: this.keys['KeyC'],
            pause: this.keys['KeyP'] || this.keys['Escape'],
            restart: this.keys['KeyR'],
            confirm: this.keys['Enter']
        };
    }

    // Check if name input modal is active
    isNameInputActive() {
        const overlay = document.getElementById('name-input-overlay');
        return overlay && overlay.style.display !== 'none' && 
               window.getComputedStyle(overlay).display !== 'none';
    }
    
    // Check if leaderboard modal is active
    isLeaderboardActive() {
        const overlay = document.getElementById('leaderboard-overlay');
        return overlay && overlay.style.display !== 'none' && 
               window.getComputedStyle(overlay).display !== 'none';
    }

    // Update DAS settings
    updateDASSettings(settings) {
        if (settings.delay !== undefined) this.das.delay = settings.delay;
        if (settings.repeat !== undefined) this.das.repeat = settings.repeat;
    }

    // Reset input state
    reset() {
        this.keys = {};
        this.das.leftTime = 0;
        this.das.rightTime = 0;
        this.das.downTime = 0;
        this.firstPress.left = false;
        this.firstPress.right = false;
        this.firstPress.down = false;
        this.touchControls.isActive = false;
    }

    // Cleanup event listeners
    destroy() {
        document.removeEventListener('keydown', this.handleKeyDown);
        document.removeEventListener('keyup', this.handleKeyUp);
        document.removeEventListener('touchstart', this.handleTouchStart);
        document.removeEventListener('touchmove', this.handleTouchMove);
        document.removeEventListener('touchend', this.handleTouchEnd);
        window.removeEventListener('blur', this.handleWindowBlur);
        window.removeEventListener('focus', this.handleWindowFocus);
    }
}