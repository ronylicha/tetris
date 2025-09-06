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
        this.currentMusicNotes = [];
        
        this.initializeAudioContext();
    }

    async initializeAudioContext() {
        try {
            console.log('Attempting to initialize audio context...');
            
            // Check if Web Audio API is supported
            if (!(window.AudioContext || window.webkitAudioContext)) {
                throw new Error('Web Audio API not supported');
            }
            
            this.audioContext = new (window.AudioContext || window.webkitAudioContext)();
            console.log('Audio context created, state:', this.audioContext.state);
            
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
            
            console.log('Audio context initialized successfully');
            console.log('Sample rate:', this.audioContext.sampleRate);
            console.log('Destination channels:', this.audioContext.destination.channelCount);
            
            // Try to resume context immediately if possible
            if (this.audioContext.state === 'suspended') {
                console.log('Audio context suspended, will resume on user interaction');
            } else if (this.audioContext.state === 'running') {
                console.log('Audio context is already running');
            }
            
        } catch (error) {
            console.error('Audio context initialization failed:', error);
            this.audioContext = null;
            
            // Add fallback notification
            this.showAudioError('Audio not available: ' + error.message);
        }
    }

    // Show audio error to user
    showAudioError(message) {
        console.warn('Audio Error:', message);
        
        // Create a temporary notification element
        const notification = document.createElement('div');
        notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            background: rgba(255, 0, 0, 0.8);
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            z-index: 10000;
            font-family: Arial, sans-serif;
            font-size: 14px;
        `;
        notification.textContent = message;
        document.body.appendChild(notification);
        
        // Remove notification after 5 seconds
        setTimeout(() => {
            if (notification.parentNode) {
                notification.parentNode.removeChild(notification);
            }
        }, 5000);
    }

    // Ensure audio context is resumed (required for user interaction)
    async resumeAudioContext() {
        if (!this.audioContext) {
            console.warn('No audio context available');
            return false;
        }
        
        if (this.audioContext.state === 'suspended') {
            try {
                await this.audioContext.resume();
                console.log('Audio context resumed successfully, state:', this.audioContext.state);
                return true;
            } catch (error) {
                console.error('Failed to resume audio context:', error);
                this.showAudioError('Failed to resume audio: ' + error.message);
                return false;
            }
        } else if (this.audioContext.state === 'running') {
            console.log('Audio context already running');
            return true;
        } else {
            console.warn('Audio context in unexpected state:', this.audioContext.state);
            return false;
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
    async playSFX(type) {
        console.log(`SFX Request: ${type}`);
        
        if (!this.settings.sfxEnabled) {
            console.log(`SFX ${type} skipped: SFX disabled in settings`);
            return;
        }
        
        if (!this.audioContext) {
            console.log(`SFX ${type} skipped: No audio context available`);
            return;
        }
        
        // Ensure audio context is resumed before playing
        const resumed = await this.resumeAudioContext();
        if (!resumed) {
            console.warn(`SFX ${type} skipped: Failed to resume audio context`);
            return;
        }
        
        console.log(`Playing SFX: ${type} (context state: ${this.audioContext.state})`);
        
        try {
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
                default:
                    console.warn(`Unknown SFX type: ${type}`);
            }
        } catch (error) {
            console.error(`Error playing SFX ${type}:`, error);
            this.showAudioError(`Sound effect error: ${error.message}`);
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

    // Background Music System - 90s Style
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
        
        // Stop any currently playing notes
        if (this.currentMusicNotes) {
            this.currentMusicNotes.forEach(note => {
                if (note.oscillator) {
                    try {
                        note.oscillator.stop();
                    } catch (e) {
                        // Ignore if already stopped
                    }
                }
            });
            this.currentMusicNotes = [];
        }
    }

    playBackgroundLoop() {
        // Enhanced 90s-style Tetris melody with harmony and bass
        const mainMelody = [
            // A section - Classic Tetris theme (Korobeiniki) enhanced
            { note: 659, duration: 0.5, harmony: [523, 392] }, // E + C + G
            { note: 494, duration: 0.25, harmony: [392] }, // B + G
            { note: 523, duration: 0.25, harmony: [415] }, // C + G#
            { note: 587, duration: 0.5, harmony: [440, 349] }, // D + A + F
            { note: 523, duration: 0.25, harmony: [415] }, // C + G#
            { note: 494, duration: 0.25, harmony: [392] }, // B + G
            { note: 440, duration: 0.5, harmony: [330, 277] }, // A + E + C#
            { note: 440, duration: 0.25, harmony: [330] }, // A + E
            { note: 523, duration: 0.25, harmony: [415] }, // C + G#
            { note: 659, duration: 0.5, harmony: [523, 392] }, // E + C + G
            { note: 587, duration: 0.25, harmony: [440] }, // D + A
            { note: 523, duration: 0.25, harmony: [415] }, // C + G#
            { note: 494, duration: 0.75, harmony: [392, 311] }, // B + G + D#
            { note: 0, duration: 0.25, harmony: [] }, // Rest
            
            // B section - Variation with 90s flair
            { note: 587, duration: 0.5, harmony: [440, 349] }, // D + A + F
            { note: 523, duration: 0.25, harmony: [415] }, // C + G#
            { note: 587, duration: 0.25, harmony: [440] }, // D + A
            { note: 659, duration: 0.5, harmony: [523, 392] }, // E + C + G
            { note: 523, duration: 0.5, harmony: [415, 311] }, // C + G# + D#
            { note: 440, duration: 0.5, harmony: [330, 277] }, // A + E + C#
            { note: 440, duration: 1, harmony: [330, 262] }, // A + E + C
            
            // Bridge with bass line
            { note: 392, duration: 0.25, harmony: [196, 262] }, // G + G(low) + C
            { note: 440, duration: 0.25, harmony: [220, 294] }, // A + A(low) + D
            { note: 494, duration: 0.25, harmony: [247, 330] }, // B + B(low) + E
            { note: 523, duration: 0.25, harmony: [262, 349] }, // C + C(low) + F
        ];
        
        const bassLine = [
            // Accompanying bass pattern
            196, 196, 220, 220, 196, 196, 175, 175, // G G A A G G F F
            196, 196, 220, 220, 196, 196, 175, 175,
            220, 220, 247, 247, 220, 220, 196, 196, // A A B B A A G G
            175, 185, 196, 208, 220, 233, 247, 262  // F F# G G# A A# B C
        ];
        
        let noteIndex = 0;
        let bassIndex = 0;
        this.currentMusicNotes = [];
        
        const playNextNote = async () => {
            if (!this.settings.musicEnabled) return;
            
            // Clear previous notes
            this.currentMusicNotes.forEach(note => {
                if (note.oscillator) {
                    try {
                        note.oscillator.stop();
                    } catch (e) {
                        // Already stopped
                    }
                }
            });
            this.currentMusicNotes = [];
            
            const currentNote = mainMelody[noteIndex];
            const bassNote = bassLine[bassIndex];
            const now = this.audioContext.currentTime;
            
            // Play main melody note
            if (currentNote.note > 0) {
                const mainSound = this.createMusicNote(currentNote.note, currentNote.duration, 'lead');
                if (mainSound) {
                    mainSound.oscillator.start(now);
                    mainSound.oscillator.stop(now + currentNote.duration);
                    this.currentMusicNotes.push(mainSound);
                }
                
                // Play harmony notes (90s style)
                currentNote.harmony.forEach((harmonyFreq, index) => {
                    const harmonySound = this.createMusicNote(harmonyFreq, currentNote.duration, 'harmony');
                    if (harmonySound) {
                        harmonySound.oscillator.start(now + (index * 0.02)); // Slight delay for richness
                        harmonySound.oscillator.stop(now + currentNote.duration);
                        this.currentMusicNotes.push(harmonySound);
                    }
                });
            }
            
            // Play bass line
            const bassSound = this.createMusicNote(bassNote, currentNote.duration, 'bass');
            if (bassSound) {
                bassSound.oscillator.start(now);
                bassSound.oscillator.stop(now + currentNote.duration);
                this.currentMusicNotes.push(bassSound);
            }
            
            noteIndex = (noteIndex + 1) % mainMelody.length;
            bassIndex = (bassIndex + 1) % bassLine.length;
        };
        
        // Start playing with smoother timing (90s feel)
        playNextNote();
        this.musicLoop = setInterval(playNextNote, 400); // 150 BPM for more energy
    }

    createMusicNote(frequency, duration, instrument = 'lead') {
        if (!this.audioContext) return null;
        
        const oscillator = this.audioContext.createOscillator();
        const gainNode = this.audioContext.createGain();
        const filterNode = this.audioContext.createBiquadFilter();
        
        // 90s-style instrument configuration
        switch (instrument) {
            case 'lead':
                oscillator.type = 'sawtooth'; // Richer sound than square
                filterNode.type = 'lowpass';
                filterNode.frequency.value = 2000;
                filterNode.Q.value = 1.5;
                break;
                
            case 'harmony':
                oscillator.type = 'triangle'; // Softer harmony
                filterNode.type = 'bandpass';
                filterNode.frequency.value = 1000;
                filterNode.Q.value = 0.7;
                break;
                
            case 'bass':
                oscillator.type = 'square'; // Classic bass sound
                filterNode.type = 'lowpass';
                filterNode.frequency.value = 400;
                filterNode.Q.value = 2;
                break;
                
            default:
                oscillator.type = 'square';
                filterNode.type = 'lowpass';
                filterNode.frequency.value = 1500;
        }
        
        oscillator.frequency.setValueAtTime(frequency, this.audioContext.currentTime);
        
        // 90s-style envelope with more character
        const now = this.audioContext.currentTime;
        let volume;
        
        switch (instrument) {
            case 'lead':
                volume = 0.08; // Main melody prominence
                gainNode.gain.setValueAtTime(0, now);
                gainNode.gain.linearRampToValueAtTime(volume, now + 0.02);
                gainNode.gain.setValueAtTime(volume * 0.9, now + duration * 0.3);
                gainNode.gain.setValueAtTime(volume * 0.7, now + duration * 0.7);
                gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration);
                break;
                
            case 'harmony':
                volume = 0.04; // Subtle harmony
                gainNode.gain.setValueAtTime(0, now);
                gainNode.gain.linearRampToValueAtTime(volume, now + 0.05);
                gainNode.gain.setValueAtTime(volume * 0.8, now + duration * 0.5);
                gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration);
                break;
                
            case 'bass':
                volume = 0.06; // Solid bass foundation
                gainNode.gain.setValueAtTime(0, now);
                gainNode.gain.linearRampToValueAtTime(volume, now + 0.01);
                gainNode.gain.setValueAtTime(volume, now + duration * 0.8);
                gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration);
                break;
                
            default:
                volume = 0.05;
                gainNode.gain.setValueAtTime(0, now);
                gainNode.gain.linearRampToValueAtTime(volume, now + 0.05);
                gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration);
        }
        
        // Connect with filter for 90s character
        oscillator.connect(filterNode);
        filterNode.connect(gainNode);
        gainNode.connect(this.musicGain);
        
        return { oscillator, gainNode, filterNode };
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