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
            'Escape': 'menu',
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
            threshold: 25, // Reduced threshold for more responsive swipe detection
            isActive: false,
            startTime: 0,
            longPressThreshold: 800, // 800ms for long press (increased to avoid conflicts with continuous drop)
            longPressTriggered: false,
            isDownSwipe: false,
            downSwipeSpeed: 0,
            continuousDropActive: false
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
            if (this.isNameInputActive() || this.isLeaderboardActive() || 
                this.isSettingsActive() || this.isHelpActive()) {
                return;
            }
            
            if (this.keyBindings[e.code]) {
                e.preventDefault();
                // Also stop propagation to prevent multiple handlers
                e.stopImmediatePropagation();
            }
        });
        
        // Touch and mouse events for canvas interactions
        const canvas = document.getElementById('game-canvas');
        if (canvas) {
            // Touch events for mobile
            canvas.addEventListener('touchstart', (e) => this.handleTouchStart(e));
            canvas.addEventListener('touchmove', (e) => this.handleTouchMove(e));
            canvas.addEventListener('touchend', (e) => this.handleTouchEnd(e));
            
            // Mouse events
            canvas.addEventListener('click', (e) => this.handleCanvasClick(e));
        }
        
        // Also add touch events to game overlay for pause/resume functionality
        const gameOverlay = document.getElementById('game-overlay');
        if (gameOverlay) {
            gameOverlay.addEventListener('touchstart', (e) => this.handleTouchStart(e));
            gameOverlay.addEventListener('touchmove', (e) => this.handleTouchMove(e));
            gameOverlay.addEventListener('touchend', (e) => this.handleTouchEnd(e));
            gameOverlay.addEventListener('click', (e) => this.handleCanvasClick(e));
        }
        
        // Add touch support for UI elements
        this.initializeTouchUI();
        
        // Show mobile controls hint on touch devices
        this.showMobileControlsHint();
        
        // Focus management
        window.addEventListener('blur', () => this.handleWindowBlur());
        window.addEventListener('focus', () => this.handleWindowFocus());
        
        // Page visibility API for better mobile support
        document.addEventListener('visibilitychange', () => this.handleVisibilityChange());
    }

    handleKeyDown(e) {
        // Check if any modal is active - if so, ignore game controls
        if (this.isNameInputActive() || this.isLeaderboardActive() || 
            this.isSettingsActive() || this.isHelpActive()) {
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
            
            // For settings, only allow Escape to close
            if (this.isSettingsActive() && e.code === 'Escape') {
                const closeButton = document.getElementById('close-settings');
                if (closeButton) closeButton.click();
                return;
            }
            
            // For help, only allow Escape to close
            if (this.isHelpActive() && e.code === 'Escape') {
                const closeButton = document.getElementById('close-help');
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
        // Check if any modal is active - if so, ignore game key releases
        if (this.isNameInputActive() || this.isLeaderboardActive() || 
            this.isSettingsActive() || this.isHelpActive()) {
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
        // Only prevent default for game canvas touches
        e.preventDefault();
        const touch = e.touches[0];
        
        // Get touch coordinates relative to canvas
        const rect = e.target.getBoundingClientRect();
        this.touchControls.startX = touch.clientX - rect.left;
        this.touchControls.startY = touch.clientY - rect.top;
        this.touchControls.startTime = Date.now();
        this.touchControls.isActive = true;
        this.touchControls.longPressTriggered = false;
        
        // Set timeout for long press detection
        this.longPressTimeout = setTimeout(() => {
            if (this.touchControls.isActive && 
                !this.touchControls.longPressTriggered && 
                !this.touchControls.isDownSwipe && 
                !this.touchControls.continuousDropActive) {
                this.touchControls.longPressTriggered = true;
                this.executeAction('hold');
            }
        }, this.touchControls.longPressThreshold);
    }

    handleTouchMove(e) {
        if (!this.touchControls.isActive) return;
        e.preventDefault();
        
        const touch = e.touches[0];
        const rect = e.target.getBoundingClientRect();
        const currentY = touch.clientY - rect.top;
        const deltaY = currentY - this.touchControls.startY;
        
        // Detect continuous down swipe
        if (deltaY > this.touchControls.threshold && !this.touchControls.longPressTriggered) {
            if (!this.touchControls.isDownSwipe) {
                this.touchControls.isDownSwipe = true;
                this.touchControls.continuousDropActive = true;
                
                // Calculate swipe speed (distance per time)
                const timeElapsed = Date.now() - this.touchControls.startTime;
                this.touchControls.downSwipeSpeed = Math.min(deltaY / Math.max(timeElapsed, 1), 2); // Cap at 2x speed
                
                // Start continuous soft drop
                this.startContinuousDrop();
            }
        }
    }

    handleTouchEnd(e) {
        if (!this.touchControls.isActive) return;
        e.preventDefault();
        
        // Clear long press timeout
        if (this.longPressTimeout) {
            clearTimeout(this.longPressTimeout);
            this.longPressTimeout = null;
        }
        
        // Stop continuous drop if active
        if (this.touchControls.continuousDropActive) {
            this.stopContinuousDrop();
        }
        
        // Check if game is paused - allow tap to unpause
        if (this.game && this.game.state === 'paused') {
            const touchDuration = Date.now() - this.touchControls.startTime;
            // Only unpause on quick tap (not long press or swipe)
            if (touchDuration < 300 && !this.touchControls.longPressTriggered && !this.touchControls.isDownSwipe) {
                this.game.togglePause();
                this.resetTouchControls();
                return;
            }
        }
        
        // If long press was triggered, don't process other gestures
        if (this.touchControls.longPressTriggered) {
            this.resetTouchControls();
            return;
        }
        
        // If continuous drop was active, don't process other gestures
        if (this.touchControls.isDownSwipe) {
            this.resetTouchControls();
            return;
        }
        
        const touch = e.changedTouches[0];
        const rect = e.target.getBoundingClientRect();
        const endX = touch.clientX - rect.left;
        const endY = touch.clientY - rect.top;
        const deltaX = endX - this.touchControls.startX;
        const deltaY = endY - this.touchControls.startY;
        const threshold = this.touchControls.threshold;
        const touchDuration = Date.now() - this.touchControls.startTime;
        
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
                if (deltaY < 0) {
                    // Swipe up - hard drop
                    this.executeAction('hardDrop');
                }
                // Note: Down swipe is now handled by continuous drop in touchMove
            } else if (touchDuration < this.touchControls.longPressThreshold) {
                // Quick tap - rotate
                this.executeAction('rotateClockwise');
            }
        }
        
        this.resetTouchControls();
    }

    handleCanvasClick(e) {
        // Handle canvas clicks for piece placement or rotation
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
        
        // Stop any continuous touch actions
        this.stopContinuousDrop();
        this.resetTouchControls();
        
        // Auto-pause if game is active and mute audio
        if (this.game && this.game.state === 'playing') {
            this.game.pause();
            this.wasAutoPaused = true; // Flag to remember this was auto-paused
        }
        
        // Mute audio when window loses focus
        if (this.game && this.game.audioManager) {
            this.game.audioManager.setMasterMute(true);
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
        
        // Restore audio when window regains focus
        if (this.game && this.game.audioManager) {
            this.game.audioManager.setMasterMute(false);
        }
        
        // Note: We don't auto-resume the game to let the user decide when to continue
        // The game will remain paused and can be resumed with P key or touch
        this.wasAutoPaused = false;
    }

    handleVisibilityChange() {
        if (document.hidden) {
            // Page is now hidden (mobile browser switching, etc.)
            this.handleWindowBlur();
        } else {
            // Page is now visible
            this.handleWindowFocus();
        }
    }

    // Update input state (called every frame)
    update(deltaTime) {
        if (!this.game || this.game.state !== 'playing') return;
        
        // Check if any modal is active - if so, disable game controls
        if (this.isNameInputActive() || this.isLeaderboardActive() || 
            this.isSettingsActive() || this.isHelpActive()) return;
        
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
            case 'menu':
                // Return to home screen
                if (this.game.modalManager) {
                    this.game.modalManager.backToHome();
                }
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
    
    // Check if settings modal is active
    isSettingsActive() {
        const overlay = document.getElementById('settings-overlay');
        return overlay && overlay.style.display !== 'none' && 
               window.getComputedStyle(overlay).display !== 'none';
    }
    
    // Check if help modal is active
    isHelpActive() {
        const overlay = document.getElementById('help-overlay');
        return overlay && overlay.style.display !== 'none' && 
               window.getComputedStyle(overlay).display !== 'none';
    }

    // Start continuous drop for touch down swipe
    startContinuousDrop() {
        if (!this.game || this.game.state !== 'playing') return;
        
        // Calculate drop interval based on swipe speed (faster swipe = faster drop)
        const baseInterval = 50; // Base 50ms interval
        const speedMultiplier = Math.max(0.3, 1 - this.touchControls.downSwipeSpeed * 0.3);
        const dropInterval = baseInterval * speedMultiplier; // Faster swipe = shorter interval
        
        // Clear any existing interval
        if (this.continuousDropInterval) {
            clearInterval(this.continuousDropInterval);
        }
        
        // Start continuous dropping
        this.continuousDropInterval = setInterval(() => {
            if (!this.touchControls.continuousDropActive || !this.game || this.game.state !== 'playing') {
                this.stopContinuousDrop();
                return;
            }
            this.executeAction('softDrop');
        }, dropInterval);
    }
    
    // Stop continuous drop
    stopContinuousDrop() {
        if (this.continuousDropInterval) {
            clearInterval(this.continuousDropInterval);
            this.continuousDropInterval = null;
        }
        this.touchControls.continuousDropActive = false;
    }
    
    // Reset touch controls state
    resetTouchControls() {
        this.touchControls.isActive = false;
        this.touchControls.isDownSwipe = false;
        this.touchControls.longPressTriggered = false;
        this.touchControls.continuousDropActive = false;
        this.touchControls.downSwipeSpeed = 0;
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
        
        // Reset touch controls
        this.stopContinuousDrop();
        this.resetTouchControls();
        
        // Clear any pending long press timeout
        if (this.longPressTimeout) {
            clearTimeout(this.longPressTimeout);
            this.longPressTimeout = null;
        }
    }

    // Show mobile controls hint on touch devices
    showMobileControlsHint() {
        // Check if device supports touch
        const isTouchDevice = 'ontouchstart' in window || navigator.maxTouchPoints > 0;
        const mobileControlsHint = document.getElementById('mobile-controls-hint');
        
        if (isTouchDevice && mobileControlsHint) {
            mobileControlsHint.style.display = 'flex';
            
            // Auto-hide after 5 seconds during gameplay
            const autoHide = () => {
                if (this.game && this.game.state === 'playing') {
                    mobileControlsHint.style.opacity = '0.7';
                    setTimeout(() => {
                        if (this.game && this.game.state === 'playing') {
                            mobileControlsHint.style.display = 'none';
                        }
                    }, 5000);
                }
            };
            
            // Hide hint after 10 seconds or when game starts
            setTimeout(autoHide, 10000);
            
            // Also hide when game starts
            if (this.game) {
                const originalStart = this.game.start?.bind(this.game);
                if (originalStart) {
                    this.game.start = (...args) => {
                        originalStart(...args);
                        setTimeout(autoHide, 3000);
                    };
                }
            }
        }
    }
    
    // Initialize touch support for UI elements
    initializeTouchUI() {
        // Add better touch support for all buttons
        const buttons = document.querySelectorAll('button, .btn-primary, .btn-secondary, .btn-icon, .btn-close');
        buttons.forEach(button => {
            // Ensure buttons are clickable on mobile
            button.style.touchAction = 'manipulation';
            
            // Add active state for better touch feedback
            button.addEventListener('touchstart', (e) => {
                button.classList.add('touch-active');
                e.stopPropagation(); // Prevent game touch handling
            }, { passive: false });
            
            button.addEventListener('touchend', (e) => {
                button.classList.remove('touch-active');
                e.stopPropagation();
            }, { passive: false });
            
            button.addEventListener('touchcancel', () => {
                button.classList.remove('touch-active');
            });
        });
        
        // Add touch support for modal overlays (to prevent closing on touch)
        const modals = document.querySelectorAll('.name-input-content, .leaderboard-content, .settings-content, .help-content');
        modals.forEach(modal => {
            modal.addEventListener('touchstart', (e) => {
                e.stopPropagation();
            });
            modal.addEventListener('touchend', (e) => {
                e.stopPropagation();
            });
        });
        
        // Ensure input fields work properly on touch devices
        const inputs = document.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            input.style.touchAction = 'manipulation';
            input.addEventListener('touchstart', (e) => {
                e.stopPropagation();
            });
        });
    }
    
    // Cleanup event listeners
    destroy() {
        document.removeEventListener('keydown', this.handleKeyDown);
        document.removeEventListener('keyup', this.handleKeyUp);
        
        const canvas = document.getElementById('game-canvas');
        if (canvas) {
            canvas.removeEventListener('touchstart', this.handleTouchStart);
            canvas.removeEventListener('touchmove', this.handleTouchMove);
            canvas.removeEventListener('touchend', this.handleTouchEnd);
        }
        
        // Clean up continuous drop
        this.stopContinuousDrop();
        
        // Clear long press timeout
        if (this.longPressTimeout) {
            clearTimeout(this.longPressTimeout);
            this.longPressTimeout = null;
        }
        
        window.removeEventListener('blur', this.handleWindowBlur);
        window.removeEventListener('focus', this.handleWindowFocus);
        document.removeEventListener('visibilitychange', this.handleVisibilityChange);
    }
}