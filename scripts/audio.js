// Modern Tetris - 80s Style Audio System

export class AudioManager {
    constructor() {
        this.audioContext = null;
        this.masterGain = null;
        this.musicGain = null;
        this.sfxGain = null;
        
        // Audio settings
        this.settings = {
            masterVolume: 0.7,
            musicVolume: 0.5,
            sfxVolume: 0.8,
            musicEnabled: true,
            sfxEnabled: true
        };
        
        // Load settings from localStorage
        this.loadSettings();
        
        // Background music state
        this.currentMusic = null;
        this.musicLoop = null;
        
        this.initializeAudioContext();
    }

    async initializeAudioContext() {
        try {
            this.audioContext = new (window.AudioContext || window.webkitAudioContext)();
            
            // Create gain nodes for volume control
            this.masterGain = this.audioContext.createGain();
            this.musicGain = this.audioContext.createGain();
            this.sfxGain = this.audioContext.createGain();
            
            // Connect gain nodes
            this.musicGain.connect(this.masterGain);
            this.sfxGain.connect(this.masterGain);
            this.masterGain.connect(this.audioContext.destination);
            
            // Set initial volumes
            this.updateVolumes();
            
        } catch (error) {
            console.warn('Audio context not supported:', error);
        }
    }

    // Ensure audio context is resumed (required for user interaction)
    async resumeAudioContext() {
        if (this.audioContext && this.audioContext.state === 'suspended') {
            await this.audioContext.resume();
        }
    }

    // Load audio settings from localStorage
    loadSettings() {
        const saved = localStorage.getItem('tetris_audio_settings');
        if (saved) {
            this.settings = { ...this.settings, ...JSON.parse(saved) };
        }
    }

    // Save audio settings to localStorage
    saveSettings() {
        localStorage.setItem('tetris_audio_settings', JSON.stringify(this.settings));
    }

    // Update all volume levels
    updateVolumes() {
        if (!this.audioContext) return;
        
        this.masterGain.gain.value = this.settings.masterVolume;
        this.musicGain.gain.value = this.settings.musicEnabled ? this.settings.musicVolume : 0;
        this.sfxGain.gain.value = this.settings.sfxEnabled ? this.settings.sfxVolume : 0;
    }

    // Generate 80s-style synthesizer tones
    createOscillator(frequency, type = 'square', duration = 0.1) {
        if (!this.audioContext) return null;
        
        const oscillator = this.audioContext.createOscillator();
        const gainNode = this.audioContext.createGain();
        
        oscillator.type = type;
        oscillator.frequency.setValueAtTime(frequency, this.audioContext.currentTime);
        
        // 80s-style envelope (attack, decay, sustain, release)
        const now = this.audioContext.currentTime;
        gainNode.gain.setValueAtTime(0, now);
        gainNode.gain.linearRampToValueAtTime(0.3, now + 0.01); // Attack
        gainNode.gain.exponentialRampToValueAtTime(0.1, now + duration * 0.3); // Decay
        gainNode.gain.setValueAtTime(0.1, now + duration * 0.7); // Sustain
        gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration); // Release
        
        oscillator.connect(gainNode);
        gainNode.connect(this.sfxGain);
        
        return { oscillator, gainNode, duration };
    }

    // Play a synthesized sound effect
    playSFX(type) {
        if (!this.settings.sfxEnabled || !this.audioContext) return;
        
        this.resumeAudioContext();
        
        switch (type) {
            case 'move':
                this.playMoveSound();
                break;
            case 'rotate':
                this.playRotateSound();
                break;
            case 'drop':
                this.playDropSound();
                break;
            case 'lock':
                this.playLockSound();
                break;
            case 'lineClear':
                this.playLineClearSound();
                break;
            case 'tetris':
                this.playTetrisSound();
                break;
            case 'levelUp':
                this.playLevelUpSound();
                break;
            case 'gameOver':
                this.playGameOverSound();
                break;
            case 'hold':
                this.playHoldSound();
                break;
            case 'tspin':
                this.playTSpinSound();
                break;
        }
    }

    playMoveSound() {
        const sound = this.createOscillator(220, 'square', 0.05);
        if (sound) {
            sound.oscillator.start();
            sound.oscillator.stop(this.audioContext.currentTime + sound.duration);
        }
    }

    playRotateSound() {
        const sound = this.createOscillator(330, 'sawtooth', 0.08);
        if (sound) {
            // Add frequency sweep for rotation effect
            sound.oscillator.frequency.exponentialRampToValueAtTime(
                440, this.audioContext.currentTime + sound.duration
            );
            sound.oscillator.start();
            sound.oscillator.stop(this.audioContext.currentTime + sound.duration);
        }
    }

    playDropSound() {
        const sound = this.createOscillator(110, 'square', 0.1);
        if (sound) {
            // Descending pitch for drop effect
            sound.oscillator.frequency.exponentialRampToValueAtTime(
                55, this.audioContext.currentTime + sound.duration
            );
            sound.oscillator.start();
            sound.oscillator.stop(this.audioContext.currentTime + sound.duration);
        }
    }

    playLockSound() {
        // Two-tone lock sound
        const sound1 = this.createOscillator(660, 'square', 0.06);
        const sound2 = this.createOscillator(440, 'square', 0.06);
        
        if (sound1 && sound2) {
            const now = this.audioContext.currentTime;
            sound1.oscillator.start(now);
            sound1.oscillator.stop(now + 0.03);
            
            sound2.oscillator.start(now + 0.03);
            sound2.oscillator.stop(now + 0.06);
        }
    }

    playLineClearSound() {
        // Ascending arpeggio for line clear
        const frequencies = [523, 659, 784, 1047]; // C5, E5, G5, C6
        frequencies.forEach((freq, index) => {
            const sound = this.createOscillator(freq, 'square', 0.15);
            if (sound) {
                const startTime = this.audioContext.currentTime + (index * 0.05);
                sound.oscillator.start(startTime);
                sound.oscillator.stop(startTime + 0.15);
            }
        });
    }

    playTetrisSound() {
        // Epic Tetris chord progression
        const chord1 = [523, 659, 784]; // C major
        const chord2 = [587, 740, 880]; // D major
        const chord3 = [659, 831, 988]; // E major
        
        [chord1, chord2, chord3].forEach((chord, chordIndex) => {
            chord.forEach((freq, noteIndex) => {
                const sound = this.createOscillator(freq, 'sawtooth', 0.3);
                if (sound) {
                    const startTime = this.audioContext.currentTime + (chordIndex * 0.1);
                    sound.oscillator.start(startTime);
                    sound.oscillator.stop(startTime + 0.3);
                }
            });
        });
    }

    playLevelUpSound() {
        // Victory fanfare
        const melody = [523, 659, 784, 1047, 1319]; // C5 to E6
        melody.forEach((freq, index) => {
            const sound = this.createOscillator(freq, 'triangle', 0.2);
            if (sound) {
                const startTime = this.audioContext.currentTime + (index * 0.1);
                sound.oscillator.start(startTime);
                sound.oscillator.stop(startTime + 0.2);
            }
        });
    }

    playGameOverSound() {
        // Descending minor scale
        const melody = [523, 494, 440, 415, 392, 370, 330]; // C5 down
        melody.forEach((freq, index) => {
            const sound = this.createOscillator(freq, 'sawtooth', 0.4);
            if (sound) {
                const startTime = this.audioContext.currentTime + (index * 0.15);
                sound.oscillator.start(startTime);
                sound.oscillator.stop(startTime + 0.4);
            }
        });
    }

    playHoldSound() {
        const sound = this.createOscillator(880, 'triangle', 0.12);
        if (sound) {
            sound.oscillator.frequency.linearRampToValueAtTime(
                660, this.audioContext.currentTime + sound.duration
            );
            sound.oscillator.start();
            sound.oscillator.stop(this.audioContext.currentTime + sound.duration);
        }
    }

    playTSpinSound() {
        // Special T-Spin sound with rapid frequency changes
        const sound = this.createOscillator(1000, 'square', 0.2);
        if (sound) {
            const now = this.audioContext.currentTime;
            sound.oscillator.frequency.setValueAtTime(1000, now);
            sound.oscillator.frequency.exponentialRampToValueAtTime(1500, now + 0.05);
            sound.oscillator.frequency.exponentialRampToValueAtTime(800, now + 0.1);
            sound.oscillator.frequency.exponentialRampToValueAtTime(1200, now + 0.15);
            sound.oscillator.frequency.exponentialRampToValueAtTime(1000, now + 0.2);
            
            sound.oscillator.start();
            sound.oscillator.stop(now + sound.duration);
        }
    }

    // Background Music System
    startBackgroundMusic() {
        if (!this.settings.musicEnabled || this.musicLoop) return;
        
        this.resumeAudioContext();
        this.playBackgroundLoop();
    }

    stopBackgroundMusic() {
        if (this.musicLoop) {
            clearInterval(this.musicLoop);
            this.musicLoop = null;
        }
    }

    playBackgroundLoop() {
        // Classic Tetris-inspired melody
        const melody = [
            // Main theme inspired by Korobeiniki
            { note: 659, duration: 0.5 }, // E
            { note: 494, duration: 0.25 }, // B
            { note: 523, duration: 0.25 }, // C
            { note: 587, duration: 0.5 }, // D
            { note: 523, duration: 0.25 }, // C
            { note: 494, duration: 0.25 }, // B
            { note: 440, duration: 0.5 }, // A
            { note: 440, duration: 0.25 }, // A
            { note: 523, duration: 0.25 }, // C
            { note: 659, duration: 0.5 }, // E
            { note: 587, duration: 0.25 }, // D
            { note: 523, duration: 0.25 }, // C
            { note: 494, duration: 1 }, // B
            { note: 523, duration: 0.25 }, // C
            { note: 587, duration: 0.5 }, // D
            { note: 659, duration: 0.5 }, // E
            { note: 523, duration: 0.5 }, // C
            { note: 440, duration: 0.5 }, // A
            { note: 440, duration: 1 } // A
        ];
        
        let noteIndex = 0;
        const playNextNote = () => {
            if (!this.settings.musicEnabled) return;
            
            const currentNote = melody[noteIndex];
            const sound = this.createMusicNote(currentNote.note, currentNote.duration);
            
            if (sound) {
                sound.oscillator.start();
                sound.oscillator.stop(this.audioContext.currentTime + currentNote.duration);
            }
            
            noteIndex = (noteIndex + 1) % melody.length;
        };
        
        // Start playing and set interval for continuous loop
        playNextNote();
        this.musicLoop = setInterval(playNextNote, 600); // 100 BPM roughly
    }

    createMusicNote(frequency, duration) {
        if (!this.audioContext) return null;
        
        const oscillator = this.audioContext.createOscillator();
        const gainNode = this.audioContext.createGain();
        
        oscillator.type = 'square';
        oscillator.frequency.setValueAtTime(frequency, this.audioContext.currentTime);
        
        // Softer envelope for background music
        const now = this.audioContext.currentTime;
        gainNode.gain.setValueAtTime(0, now);
        gainNode.gain.linearRampToValueAtTime(0.1, now + 0.05);
        gainNode.gain.setValueAtTime(0.1, now + duration * 0.8);
        gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration);
        
        oscillator.connect(gainNode);
        gainNode.connect(this.musicGain);
        
        return { oscillator, gainNode };
    }

    // Volume Controls
    setMasterVolume(volume) {
        this.settings.masterVolume = Math.max(0, Math.min(1, volume));
        this.updateVolumes();
        this.saveSettings();
    }

    setMusicVolume(volume) {
        this.settings.musicVolume = Math.max(0, Math.min(1, volume));
        this.updateVolumes();
        this.saveSettings();
    }

    setSFXVolume(volume) {
        this.settings.sfxVolume = Math.max(0, Math.min(1, volume));
        this.updateVolumes();
        this.saveSettings();
    }

    // Toggle Controls
    toggleMusic() {
        this.settings.musicEnabled = !this.settings.musicEnabled;
        this.updateVolumes();
        this.saveSettings();
        
        if (this.settings.musicEnabled) {
            this.startBackgroundMusic();
        } else {
            this.stopBackgroundMusic();
        }
    }

    toggleSFX() {
        this.settings.sfxEnabled = !this.settings.sfxEnabled;
        this.updateVolumes();
        this.saveSettings();
    }

    // Get current settings for UI
    getSettings() {
        return { ...this.settings };
    }

    // Cleanup
    destroy() {
        this.stopBackgroundMusic();
        if (this.audioContext) {
            this.audioContext.close();
        }
    }
}