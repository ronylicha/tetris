// Modern Tetris - Modal Management System
import { LeaderboardManager } from './leaderboard.js';

export class ModalManager {
    constructor(audioManager) {
        this.audioManager = audioManager;
        this.currentModal = null;
        this.leaderboardManager = new LeaderboardManager();
        this.game = null; // Will be set by game instance
        this.initializeEventListeners();
    }

    initializeEventListeners() {
        // Mode selector button
        const modeSelectorButton = document.getElementById('mode-selector-button');
        if (modeSelectorButton) {
            modeSelectorButton.addEventListener('click', () => {
                this.showModeSelector();
            });
        }
        
        // Mode cards
        document.querySelectorAll('.mode-card').forEach(card => {
            card.addEventListener('click', (e) => {
                const mode = e.currentTarget.dataset.mode;
                this.selectMode(mode);
            });
        });
        
        // Close mode selector
        const closeModeSelector = document.getElementById('close-mode-selector');
        if (closeModeSelector) {
            closeModeSelector.addEventListener('click', () => {
                this.hideModeSelector();
            });
        }
        
        // Header leaderboard button
        const headerLeaderboardButton = document.getElementById('header-leaderboard-button');
        if (headerLeaderboardButton) {
            headerLeaderboardButton.addEventListener('click', () => {
                this.leaderboardManager.showLeaderboard();
            });
        }

        // Settings button
        const settingsButton = document.getElementById('settings-button');
        if (settingsButton) {
            settingsButton.addEventListener('click', () => {
                this.showSettings();
            });
        }

        // Help button
        const helpButton = document.getElementById('help-button');
        if (helpButton) {
            helpButton.addEventListener('click', () => {
                this.showHelp();
            });
        }

        // Mute button
        const muteButton = document.getElementById('mute-button');
        if (muteButton) {
            muteButton.addEventListener('click', () => {
                this.toggleMute();
            });
        }

        // Close buttons
        document.getElementById('close-settings')?.addEventListener('click', () => {
            this.hideSettings();
        });

        document.getElementById('close-help')?.addEventListener('click', () => {
            this.hideHelp();
        });

        // Settings tabs
        document.querySelectorAll('.settings-tab-button').forEach(button => {
            button.addEventListener('click', (e) => {
                this.switchSettingsTab(e.target.dataset.tab);
            });
        });

        // Audio controls
        this.initializeAudioControls();

        // Close modals on overlay click
        this.initializeOverlayClicks();

        // Keyboard shortcuts for modals
        document.addEventListener('keydown', (e) => {
            if (e.code === 'Escape') {
                this.closeCurrentModal();
            }
        });
    }

    initializeAudioControls() {
        // Volume sliders
        const masterVolume = document.getElementById('master-volume');
        const musicVolume = document.getElementById('music-volume');
        const sfxVolume = document.getElementById('sfx-volume');

        // Volume value displays
        const masterVolumeValue = document.getElementById('master-volume-value');
        const musicVolumeValue = document.getElementById('music-volume-value');
        const sfxVolumeValue = document.getElementById('sfx-volume-value');

        // Toggle checkboxes
        const musicEnabled = document.getElementById('music-enabled');
        const sfxEnabled = document.getElementById('sfx-enabled');

        if (masterVolume && this.audioManager) {
            const settings = this.audioManager.getSettings();
            
            // Set initial values
            masterVolume.value = settings.masterVolume * 100;
            musicVolume.value = settings.musicVolume * 100;
            sfxVolume.value = settings.sfxVolume * 100;
            musicEnabled.checked = settings.musicEnabled;
            sfxEnabled.checked = settings.sfxEnabled;
            
            // Update display values
            masterVolumeValue.textContent = `${Math.round(settings.masterVolume * 100)}%`;
            musicVolumeValue.textContent = `${Math.round(settings.musicVolume * 100)}%`;
            sfxVolumeValue.textContent = `${Math.round(settings.sfxVolume * 100)}%`;

            // Master volume control
            masterVolume.addEventListener('input', (e) => {
                const volume = e.target.value / 100;
                this.audioManager.setMasterVolume(volume);
                masterVolumeValue.textContent = `${e.target.value}%`;
            });

            // Music volume control
            musicVolume.addEventListener('input', (e) => {
                const volume = e.target.value / 100;
                this.audioManager.setMusicVolume(volume);
                musicVolumeValue.textContent = `${e.target.value}%`;
            });

            // SFX volume control
            sfxVolume.addEventListener('input', (e) => {
                const volume = e.target.value / 100;
                this.audioManager.setSFXVolume(volume);
                sfxVolumeValue.textContent = `${e.target.value}%`;
            });

            // Music toggle
            musicEnabled.addEventListener('change', (e) => {
                if (e.target.checked !== settings.musicEnabled) {
                    this.audioManager.toggleMusic();
                }
            });

            // SFX toggle
            sfxEnabled.addEventListener('change', (e) => {
                if (e.target.checked !== settings.sfxEnabled) {
                    this.audioManager.toggleSFX();
                }
            });
        }
    }

    initializeOverlayClicks() {
        // Settings overlay
        const settingsOverlay = document.getElementById('settings-overlay');
        if (settingsOverlay) {
            settingsOverlay.addEventListener('click', (e) => {
                if (e.target === settingsOverlay) {
                    this.hideSettings();
                }
            });
        }

        // Help overlay
        const helpOverlay = document.getElementById('help-overlay');
        if (helpOverlay) {
            helpOverlay.addEventListener('click', (e) => {
                if (e.target === helpOverlay) {
                    this.hideHelp();
                }
            });
        }
    }

    showSettings() {
        const overlay = document.getElementById('settings-overlay');
        if (overlay) {
            overlay.style.display = 'flex';
            this.currentModal = 'settings';
            
            // Clear game inputs
            if (window.tetrisGame?.inputManager) {
                window.tetrisGame.inputManager.reset();
            }

            // Update audio settings display
            this.updateAudioSettingsDisplay();
        }
    }

    hideSettings() {
        const overlay = document.getElementById('settings-overlay');
        if (overlay) {
            overlay.style.display = 'none';
            this.currentModal = null;
        }
    }

    showHelp() {
        const overlay = document.getElementById('help-overlay');
        if (overlay) {
            overlay.style.display = 'flex';
            this.currentModal = 'help';
            
            // Clear game inputs
            if (window.tetrisGame?.inputManager) {
                window.tetrisGame.inputManager.reset();
            }

            // Populate quick controls based on device
            this.populateQuickControls();
        }
    }

    hideHelp() {
        const overlay = document.getElementById('help-overlay');
        if (overlay) {
            overlay.style.display = 'none';
            this.currentModal = null;
        }
    }

    closeCurrentModal() {
        if (this.currentModal === 'settings') {
            this.hideSettings();
        } else if (this.currentModal === 'help') {
            this.hideHelp();
        } else if (this.currentModal === 'mode-selector') {
            this.hideModeSelector();
        }
    }
    
    // Mode selector methods
    showModeSelector() {
        const modal = document.getElementById('mode-selector');
        if (modal) {
            modal.style.display = 'flex';
            this.currentModal = 'mode-selector';
        }
    }
    
    hideModeSelector() {
        const modal = document.getElementById('mode-selector');
        if (modal) {
            modal.style.display = 'none';
            this.currentModal = null;
        }
    }
    
    selectMode(modeName) {
        // Set the game mode
        if (this.game) {
            this.game.selectMode(modeName);
            this.hideModeSelector();
            
            // Show puzzle selection menu for puzzle mode
            if (modeName === 'puzzle' && this.game.ui) {
                setTimeout(() => {
                    this.game.ui.showPuzzleSelection();
                }, 100);
            } 
            // Show AI difficulty selection for battle mode
            else if (modeName === 'battle' && this.game.ui) {
                setTimeout(() => {
                    this.game.ui.showAIDifficultySelection((difficulty) => {
                        // Restart game with selected difficulty
                        this.game.restart();
                    });
                }, 100);
            } else {
                // Restart game with new mode
                this.game.restart();
            }
        } else {
            // Store for when game is ready
            localStorage.setItem('selected_mode', modeName);
            this.hideModeSelector();
        }
    }
    
    setGame(game) {
        this.game = game;
    }

    switchSettingsTab(tab) {
        // Update tab buttons
        document.querySelectorAll('.settings-tab-button').forEach(button => {
            button.classList.toggle('active', button.dataset.tab === tab);
        });

        // Show/hide panels
        const audioPanel = document.getElementById('audio-panel');
        const controlsPanel = document.getElementById('controls-panel');

        if (audioPanel && controlsPanel) {
            audioPanel.style.display = tab === 'audio' ? 'block' : 'none';
            controlsPanel.style.display = tab === 'controls' ? 'block' : 'none';
        }
    }

    toggleMute() {
        if (!this.audioManager) return;

        const settings = this.audioManager.getSettings();
        const muteButton = document.getElementById('mute-button');
        
        if (settings.masterVolume > 0) {
            // Mute
            this.audioManager.setMasterVolume(0);
            muteButton.textContent = '🔇';
            muteButton.classList.add('muted');
            muteButton.title = 'Unmute';
        } else {
            // Unmute
            this.audioManager.setMasterVolume(0.7);
            muteButton.textContent = '🔊';
            muteButton.classList.remove('muted');
            muteButton.title = 'Mute';
        }

        // Update settings display if visible
        this.updateAudioSettingsDisplay();
    }

    updateAudioSettingsDisplay() {
        if (!this.audioManager || this.currentModal !== 'settings') return;

        const settings = this.audioManager.getSettings();
        
        // Update sliders
        const masterVolume = document.getElementById('master-volume');
        const musicVolume = document.getElementById('music-volume');
        const sfxVolume = document.getElementById('sfx-volume');
        
        if (masterVolume) masterVolume.value = settings.masterVolume * 100;
        if (musicVolume) musicVolume.value = settings.musicVolume * 100;
        if (sfxVolume) sfxVolume.value = settings.sfxVolume * 100;

        // Update value displays
        const masterVolumeValue = document.getElementById('master-volume-value');
        const musicVolumeValue = document.getElementById('music-volume-value');
        const sfxVolumeValue = document.getElementById('sfx-volume-value');
        
        if (masterVolumeValue) masterVolumeValue.textContent = `${Math.round(settings.masterVolume * 100)}%`;
        if (musicVolumeValue) musicVolumeValue.textContent = `${Math.round(settings.musicVolume * 100)}%`;
        if (sfxVolumeValue) sfxVolumeValue.textContent = `${Math.round(settings.sfxVolume * 100)}%`;

        // Update toggles
        const musicEnabled = document.getElementById('music-enabled');
        const sfxEnabled = document.getElementById('sfx-enabled');
        
        if (musicEnabled) musicEnabled.checked = settings.musicEnabled;
        if (sfxEnabled) sfxEnabled.checked = settings.sfxEnabled;
    }

    populateQuickControls() {
        const quickControls = document.getElementById('quick-controls');
        if (!quickControls) return;

        const isMobile = /Android|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
        const isTouchDevice = 'ontouchstart' in window || navigator.maxTouchPoints > 0;

        let controlsHTML = '';

        if (isMobile || isTouchDevice) {
            // Show touch controls for mobile
            controlsHTML = `
                <div class="quick-control-item">
                    <span class="quick-control-key">👆 Tap</span>
                    <span class="quick-control-action">Rotate</span>
                </div>
                <div class="quick-control-item">
                    <span class="quick-control-key">👈👉 Swipe</span>
                    <span class="quick-control-action">Move</span>
                </div>
                <div class="quick-control-item">
                    <span class="quick-control-key">👇 Swipe Down</span>
                    <span class="quick-control-action">Drop</span>
                </div>
                <div class="quick-control-item">
                    <span class="quick-control-key">🤏 Long Press</span>
                    <span class="quick-control-action">Hold</span>
                </div>
            `;
        } else {
            // Show keyboard controls for desktop
            controlsHTML = `
                <div class="quick-control-item">
                    <span class="quick-control-key">←→</span>
                    <span class="quick-control-action">Move</span>
                </div>
                <div class="quick-control-item">
                    <span class="quick-control-key">↑</span>
                    <span class="quick-control-action">Rotate</span>
                </div>
                <div class="quick-control-item">
                    <span class="quick-control-key">Space</span>
                    <span class="quick-control-action">Drop</span>
                </div>
                <div class="quick-control-item">
                    <span class="quick-control-key">C</span>
                    <span class="quick-control-action">Hold</span>
                </div>
            `;
        }

        quickControls.innerHTML = controlsHTML;
    }

    // Check if any modal is active
    isAnyModalActive() {
        return this.currentModal !== null;
    }

    // Update mute button state
    updateMuteButton() {
        if (!this.audioManager) return;

        const settings = this.audioManager.getSettings();
        const muteButton = document.getElementById('mute-button');
        
        if (muteButton) {
            if (settings.masterVolume === 0) {
                muteButton.textContent = '🔇';
                muteButton.classList.add('muted');
                muteButton.title = 'Unmute';
            } else {
                muteButton.textContent = '🔊';
                muteButton.classList.remove('muted');
                muteButton.title = 'Mute';
            }
        }
    }
}

// Add styles for quick controls
const quickControlStyles = `
.quick-control-item {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 8px 12px;
    background: rgba(255, 255, 255, 0.05);
    border-radius: 6px;
    border: 1px solid rgba(255, 255, 255, 0.1);
}

.quick-control-key {
    background: var(--neon-green);
    color: white;
    padding: 4px 8px;
    border-radius: 4px;
    font-family: var(--font-primary);
    font-weight: 700;
    font-size: 0.8rem;
    min-width: 40px;
    text-align: center;
    box-shadow: 0 0 6px rgba(57, 255, 20, 0.3);
}

.quick-control-action {
    color: var(--text-primary);
    font-weight: 500;
    font-size: 0.9rem;
}
`;

// Inject styles
if (!document.getElementById('quick-control-styles')) {
    const style = document.createElement('style');
    style.id = 'quick-control-styles';
    style.textContent = quickControlStyles;
    document.head.appendChild(style);
}