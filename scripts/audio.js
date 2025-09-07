// Modern Tetris - 80s Style Audio System

export class AudioManager {
    constructor() {
        this.audioContext = null;
        this.masterGain = null;
        this.musicGain = null;
        this.sfxGain = null;
        
        // Audio settings - Balanced for comfortable extended play
        this.settings = {
            masterVolume: 0.7,    // Reduced master for better headroom
            musicVolume: 0.5,     // Background music level - present but not overwhelming
            sfxVolume: 0.7,       // Clear sound effects that cut through
            musicEnabled: true,
            sfxEnabled: true
        };
        
        // Master mute state
        this.masterMuted = false;
        
        // Load settings from localStorage
        this.loadSettings();
        
        // Background music state
        this.currentMusic = null;
        this.musicLoop = null;
        this.musicTimeout = null;
        this.currentMusicNotes = [];
        this.currentGameLevel = 1; // Track game level for adaptive music
        
        this.initializeAudioContext();
    }

    async initializeAudioContext() {
        try {
            // Check if Web Audio API is supported
            if (!(window.AudioContext || window.webkitAudioContext)) {
                throw new Error('Web Audio API not supported');
            }
            
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
            this.audioContext = null;
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
            return false;
        }
        
        if (this.audioContext.state === 'suspended') {
            try {
                await this.audioContext.resume();
                return true;
            } catch (error) {
                this.showAudioError('Failed to resume audio: ' + error.message);
                return false;
            }
        }
        
        return this.audioContext.state === 'running';
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
        
        // Only update master volume if not muted
        if (!this.masterMuted) {
            this.masterGain.gain.value = this.settings.masterVolume;
        }
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
        if (!this.settings.sfxEnabled || !this.audioContext) {
            return;
        }
        
        // Ensure audio context is resumed before playing
        const resumed = await this.resumeAudioContext();
        if (!resumed) {
            return;
        }
        
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
            }
        } catch (error) {
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

    // Background Music System - Classic Tetris Style
    // Features authentic chiptune melodies inspired by Korobeiniki,
    // proper square wave instrumentation, and energetic rhythms
    startBackgroundMusic() {
        console.log('startBackgroundMusic called - musicEnabled:', this.settings.musicEnabled, 'musicLoop exists:', !!this.musicLoop);
        if (!this.settings.musicEnabled || this.musicLoop) return;
        
        this.resumeAudioContext();
        this.playBackgroundLoop();
    }

    stopBackgroundMusic() {
        if (this.musicLoop) {
            clearTimeout(this.musicLoop);
            this.musicLoop = null;
        }
        
        // Clear any scheduled timeouts
        if (this.musicTimeout) {
            clearTimeout(this.musicTimeout);
            this.musicTimeout = null;
        }
        
        // Gradually fade out currently playing notes instead of abrupt stop
        if (this.currentMusicNotes) {
            const now = this.audioContext ? this.audioContext.currentTime : 0;
            this.currentMusicNotes.forEach(note => {
                if (note.gainNode && this.audioContext) {
                    try {
                        // Fade out over 2 seconds for smooth transition
                        note.gainNode.gain.exponentialRampToValueAtTime(0.001, now + 2.0);
                        if (note.oscillator) {
                            setTimeout(() => {
                                try {
                                    note.oscillator.stop();
                                } catch (e) {
                                    // Ignore if already stopped
                                }
                            }, 2100);
                        }
                    } catch (e) {
                        // Fallback to immediate stop if smooth fade fails
                        if (note.oscillator) {
                            try {
                                note.oscillator.stop();
                            } catch (e2) {
                                // Ignore if already stopped
                            }
                        }
                    }
                }
            });
            // Clear the array after fade completes
            setTimeout(() => {
                this.currentMusicNotes = [];
            }, 2200);
        }
    }

    playBackgroundLoop() {
        console.log('playBackgroundLoop called');
        // ENHANCED TETRIS THEME - Korobeiniki-inspired but unique composition
        // Optimized for extended gameplay with perfect looping and non-fatiguing mix
        const composition = [
            // === PART A: THE ICONIC OPENING (8 bars) ===
            // More accurate to the original Tetris theme rhythm and intervals
            { note: 659.25, duration: 1.0, harmony: [329.63], section: 'main' }, // E5 - Iconic opening
            { note: 493.88, duration: 0.5, harmony: [246.94], section: 'main' }, // B4
            { note: 523.25, duration: 0.5, harmony: [261.63], section: 'main' }, // C5
            { note: 587.33, duration: 1.0, harmony: [293.66], section: 'main' }, // D5
            { note: 523.25, duration: 0.5, harmony: [261.63], section: 'main' }, // C5
            { note: 493.88, duration: 0.5, harmony: [246.94], section: 'main' }, // B4
            
            { note: 440.00, duration: 1.0, harmony: [220.00], section: 'main' }, // A4
            { note: 440.00, duration: 0.5, harmony: [220.00], section: 'main' }, // A4
            { note: 523.25, duration: 0.5, harmony: [261.63], section: 'main' }, // C5
            { note: 659.25, duration: 1.0, harmony: [329.63], section: 'main' }, // E5
            { note: 587.33, duration: 0.5, harmony: [293.66], section: 'main' }, // D5
            { note: 523.25, duration: 0.5, harmony: [261.63], section: 'main' }, // C5
            
            { note: 493.88, duration: 1.5, harmony: [246.94], section: 'main' }, // B4 - extended
            { note: 523.25, duration: 0.5, harmony: [261.63], section: 'main' }, // C5
            { note: 587.33, duration: 1.0, harmony: [293.66], section: 'main' }, // D5
            { note: 659.25, duration: 1.0, harmony: [329.63], section: 'main' }, // E5
            
            { note: 523.25, duration: 1.0, harmony: [261.63], section: 'main' }, // C5
            { note: 440.00, duration: 1.0, harmony: [220.00], section: 'main' }, // A4
            { note: 440.00, duration: 2.0, harmony: [220.00], section: 'main' }, // A4 - hold
            
            // === PART B: THE ASCENDING SECTION (8 bars) ===
            // Creates variety and maintains interest
            { note: 0, duration: 0.5, harmony: [], section: 'bridge' }, // Rest
            { note: 587.33, duration: 0.5, harmony: [293.66], section: 'bridge' }, // D5
            { note: 698.46, duration: 1.0, harmony: [349.23], section: 'bridge' }, // F5
            { note: 880.00, duration: 1.0, harmony: [440.00], section: 'bridge' }, // A5
            
            { note: 784.00, duration: 0.5, harmony: [392.00], section: 'bridge' }, // G5
            { note: 698.46, duration: 0.5, harmony: [349.23], section: 'bridge' }, // F5
            { note: 659.25, duration: 1.5, harmony: [329.63], section: 'bridge' }, // E5
            { note: 523.25, duration: 0.5, harmony: [261.63], section: 'bridge' }, // C5
            
            { note: 659.25, duration: 1.0, harmony: [329.63], section: 'bridge' }, // E5
            { note: 587.33, duration: 0.5, harmony: [293.66], section: 'bridge' }, // D5
            { note: 523.25, duration: 0.5, harmony: [261.63], section: 'bridge' }, // C5
            { note: 493.88, duration: 2.0, harmony: [246.94], section: 'bridge' }, // B4 - hold
            
            { note: 493.88, duration: 0.5, harmony: [246.94], section: 'bridge' }, // B4
            { note: 523.25, duration: 0.5, harmony: [261.63], section: 'bridge' }, // C5
            { note: 587.33, duration: 1.0, harmony: [293.66], section: 'bridge' }, // D5
            { note: 659.25, duration: 1.0, harmony: [329.63], section: 'bridge' }, // E5
            
            { note: 523.25, duration: 1.0, harmony: [261.63], section: 'bridge' }, // C5
            { note: 440.00, duration: 1.0, harmony: [220.00], section: 'bridge' }, // A4
            { note: 440.00, duration: 2.0, harmony: [220.00], section: 'bridge' }, // A4 - hold
        ];
        
        // OPTIMIZED BASS LINE - More subtle and supportive, less fatiguing
        // Uses walking bass patterns that complement without overwhelming
        const bassPatterns = {
            main: [
                164.81, 0, 164.81, 0,      // E3 with rests - less constant
                110.00, 0, 110.00, 0,      // A2 with rests
                146.83, 0, 130.81, 0,      // D3, C3 walking pattern
                123.47, 0, 110.00, 0       // B2, A2 resolution
            ],
            bridge: [
                146.83, 0, 174.61, 0,      // D3, F3 with space
                220.00, 0, 196.00, 0,      // A3, G3 higher register
                164.81, 0, 130.81, 0,      // E3, C3 return
                123.47, 0, 146.83, 0       // B2, D3 turnaround
            ]
        };
        
        let noteIndex = 0;
        let sectionBassIndex = 0;
        this.currentMusicNotes = [];
        
        const playNextNote = async () => {
            if (!this.settings.musicEnabled) return;
            
            console.log('Playing note', noteIndex, 'of', composition.length);
            
            // Clean up old notes to prevent memory leaks
            const now = this.audioContext ? this.audioContext.currentTime : 0;
            this.currentMusicNotes = this.currentMusicNotes.filter(note => {
                if (note.startTime && (now - note.startTime) > 5) { // Reduced from 10 to 5 seconds
                    try {
                        if (note.oscillator) {
                            note.oscillator.stop();
                            note.oscillator.disconnect();
                        }
                        if (note.gainNode) note.gainNode.disconnect();
                        if (note.filterNode) note.filterNode.disconnect();
                    } catch (e) {
                        // Already stopped or disconnected
                    }
                    return false;
                }
                return true;
            });
            
            const currentNote = composition[noteIndex];
            const currentSection = currentNote.section;
            const bassPattern = bassPatterns[currentSection];
            const bassNote = bassPattern[sectionBassIndex % bassPattern.length];
            
            // Play main melody note (skip if it's a rest - note frequency 0)
            if (currentNote.note > 0) {
                const mainSound = this.createMusicNote(currentNote.note, currentNote.duration, 'lead');
                if (mainSound) {
                    mainSound.startTime = now;
                    mainSound.oscillator.start(now);
                    this.currentMusicNotes.push(mainSound);
                }
                
                // Play harmony notes with tight, precise chiptune timing
                currentNote.harmony.forEach((harmonyFreq, index) => {
                    const harmonySound = this.createMusicNote(harmonyFreq, currentNote.duration, 'harmony');
                    if (harmonySound) {
                        const delay = index * 0.01; // Very tight timing for authentic chiptune feel
                        harmonySound.startTime = now + delay;
                        harmonySound.oscillator.start(now + delay);
                        this.currentMusicNotes.push(harmonySound);
                    }
                });
            }
            
            // Play bass line (only if melody isn't resting - bass follows melody rhythm)
            if (currentNote.note > 0 && bassNote > 0) {
                const bassSound = this.createMusicNote(bassNote, currentNote.duration, 'bass');
                if (bassSound) {
                    bassSound.startTime = now;
                    bassSound.oscillator.start(now);
                    this.currentMusicNotes.push(bassSound);
                }
            }
            
            noteIndex = (noteIndex + 1) % composition.length;
            sectionBassIndex++;
            
            // Reset bass index when changing sections
            const nextSection = composition[noteIndex].section;
            if (nextSection !== currentSection) {
                sectionBassIndex = 0;
            }
        };
        
        // Start playing with authentic Tetris tempo - fast, energetic, and addictive
        const scheduleNextNote = () => {
            if (!this.settings.musicEnabled) return;
            
            playNextNote();
            const currentNote = composition[noteIndex];
            // ADAPTIVE TEMPO - Starts moderate and can speed up with game level
            // Base tempo around 120 BPM for comfortable extended listening
            const tempoMultiplier = this.getMusicSpeedMultiplier ? this.getMusicSpeedMultiplier() : 1.0;
            const baseDelay = 250; // More relaxed base tempo (120 BPM)
            const nextDelay = (currentNote.duration * baseDelay) / tempoMultiplier;
            this.musicTimeout = setTimeout(scheduleNextNote, nextDelay);
        };
        
        // Set musicLoop to indicate music is playing (use timeout ID)
        this.musicLoop = setTimeout(scheduleNextNote, 50); // Start quickly
    }

    createMusicNote(frequency, duration, instrument = 'lead') {
        if (!this.audioContext || frequency === 0) return null; // Support rests
        
        const oscillator = this.audioContext.createOscillator();
        const gainNode = this.audioContext.createGain();
        const filterNode = this.audioContext.createBiquadFilter();
        
        // Get current time first for consistent timing
        const now = this.audioContext.currentTime;
        
        // ENHANCED CHIPTUNE SYNTHESIS with better mixing
        switch (instrument) {
            case 'lead':
                oscillator.type = 'square';
                oscillator.frequency.setValueAtTime(frequency, now);
                // Subtle vibrato for character without being overwhelming
                const vibratoDepth = 0.002; // Much subtler vibrato
                const vibratoRate = 5; // Hz
                const lfo = this.audioContext.createOscillator();
                const lfoGain = this.audioContext.createGain();
                lfo.frequency.value = vibratoRate;
                lfoGain.gain.value = frequency * vibratoDepth;
                lfo.connect(lfoGain);
                lfoGain.connect(oscillator.frequency);
                lfo.start(now);
                lfo.stop(now + duration + 0.1);
                
                // Add gentle low-pass filter to soften harsh harmonics
                filterNode.type = 'lowpass';
                filterNode.frequency.value = 2000;
                filterNode.Q.value = 0.5;
                break;
                
            case 'harmony':
                oscillator.type = 'square';
                oscillator.frequency.setValueAtTime(frequency, now);
                // Softer filter for harmony to sit behind lead
                filterNode.type = 'lowpass';
                filterNode.frequency.value = 1500;
                filterNode.Q.value = 0.3;
                break;
                
            case 'bass':
                oscillator.type = 'triangle';
                oscillator.frequency.setValueAtTime(frequency, now);
                // Gentle pitch envelope for character
                oscillator.frequency.setValueAtTime(frequency * 0.98, now);
                oscillator.frequency.exponentialRampToValueAtTime(frequency, now + 0.01);
                
                // Low-pass filter to keep bass clean
                filterNode.type = 'lowpass';
                filterNode.frequency.value = 800;
                filterNode.Q.value = 1.0;
                break;
                
            default:
                oscillator.type = 'square';
                oscillator.frequency.setValueAtTime(frequency, now);
                filterNode.type = 'lowpass';
                filterNode.frequency.value = 2000;
        }
        
        // BALANCED MIXING - Pleasant for extended listening
        let volume;
        
        switch (instrument) {
            case 'lead':
                volume = 0.35; // Reduced but still prominent
                gainNode.gain.setValueAtTime(0, now);
                gainNode.gain.linearRampToValueAtTime(volume, now + 0.002); // Quick attack
                gainNode.gain.linearRampToValueAtTime(volume * 0.85, now + 0.01); // Slight decay
                gainNode.gain.setValueAtTime(volume * 0.85, now + duration * 0.8); // Sustain
                gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration + 0.02); // Release
                break;
                
            case 'harmony':
                volume = 0.20; // Supportive, not overwhelming
                gainNode.gain.setValueAtTime(0, now);
                gainNode.gain.linearRampToValueAtTime(volume, now + 0.003);
                gainNode.gain.linearRampToValueAtTime(volume * 0.8, now + 0.015);
                gainNode.gain.setValueAtTime(volume * 0.8, now + duration * 0.7);
                gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration + 0.03);
                break;
                
            case 'bass':
                volume = 0.25; // Present but not boomy
                gainNode.gain.setValueAtTime(0, now);
                gainNode.gain.linearRampToValueAtTime(volume, now + 0.001); // Tight attack
                gainNode.gain.linearRampToValueAtTime(volume * 0.9, now + 0.005);
                gainNode.gain.setValueAtTime(volume * 0.9, now + duration * 0.9);
                gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration + 0.01);
                break;
                
            default:
                volume = 0.2;
                gainNode.gain.setValueAtTime(0, now);
                gainNode.gain.linearRampToValueAtTime(volume, now + 0.01);
                gainNode.gain.exponentialRampToValueAtTime(0.001, now + duration + 0.1);
        }
        
        // Connect with filter for better sound quality
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

    // Master mute control (for window focus/blur)
    setMasterMute(muted) {
        if (!this.masterGain) return;
        
        this.masterMuted = muted;
        
        if (muted) {
            // Store current volume before muting
            this.previousMasterVolume = this.settings.masterVolume;
            this.masterGain.gain.value = 0;
        } else {
            // Restore previous volume or use current setting
            const volumeToRestore = this.previousMasterVolume ?? this.settings.masterVolume;
            this.masterGain.gain.value = volumeToRestore;
        }
    }

    // Get current settings for UI
    getSettings() {
        return { ...this.settings };
    }

    // Set game level for adaptive music tempo
    setGameLevel(level) {
        this.currentGameLevel = Math.max(1, Math.min(level, 20)); // Cap at level 20
    }
    
    // Get music speed multiplier based on game level
    getMusicSpeedMultiplier() {
        // Start at 1.0x speed, increase by 2% per level, max 1.4x at level 20
        return Math.min(1.4, 1.0 + (this.currentGameLevel - 1) * 0.02);
    }
    
    // Cleanup
    destroy() {
        this.stopBackgroundMusic();
        // Clean up all audio nodes
        this.currentMusicNotes.forEach(note => {
            try {
                if (note.oscillator) {
                    note.oscillator.stop();
                    note.oscillator.disconnect();
                }
                if (note.gainNode) note.gainNode.disconnect();
                if (note.filterNode) note.filterNode.disconnect();
            } catch (e) {
                // Ignore cleanup errors
            }
        });
        this.currentMusicNotes = [];
        
        if (this.audioContext) {
            this.audioContext.close();
        }
    }
}