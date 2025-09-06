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
        this.musicTimeout = null;
        this.currentMusicNotes = [];
        
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
        
        // Clear any scheduled timeouts
        if (this.musicTimeout) {
            clearTimeout(this.musicTimeout);
            this.musicTimeout = null;
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
        // Extended modern electronic composition - 4+ minutes of unique music
        const composition = [
            // === INTRO SECTION === (16 beats)
            { note: 329.63, duration: 1.0, harmony: [261.63, 196.00], section: 'intro' }, // E3 + C3 + G2
            { note: 0, duration: 0.5, harmony: [], section: 'intro' },
            { note: 440.00, duration: 0.5, harmony: [329.63, 220.00], section: 'intro' }, // A4 + E3 + A2
            { note: 523.25, duration: 1.0, harmony: [415.30, 261.63], section: 'intro' }, // C5 + G#4 + C3
            
            // === MAIN THEME A === (32 beats) - Enhanced Korobeiniki
            { note: 659.25, duration: 0.5, harmony: [523.25, 392.00, 196.00], section: 'themeA' }, // E5 + C5 + G4 + G2
            { note: 493.88, duration: 0.25, harmony: [392.00, 220.00], section: 'themeA' }, // B4 + G4 + A2
            { note: 523.25, duration: 0.25, harmony: [415.30, 233.08], section: 'themeA' }, // C5 + G#4 + A#2
            { note: 587.33, duration: 0.5, harmony: [440.00, 349.23, 246.94], section: 'themeA' }, // D5 + A4 + F4 + B2
            { note: 523.25, duration: 0.25, harmony: [415.30, 261.63], section: 'themeA' }, // C5 + G#4 + C3
            { note: 493.88, duration: 0.25, harmony: [392.00, 277.18], section: 'themeA' }, // B4 + G4 + C#3
            { note: 440.00, duration: 0.5, harmony: [329.63, 220.00], section: 'themeA' }, // A4 + E4 + A2
            { note: 440.00, duration: 0.25, harmony: [329.63, 196.00], section: 'themeA' }, // A4 + E4 + G2
            { note: 523.25, duration: 0.25, harmony: [415.30, 220.00], section: 'themeA' }, // C5 + G#4 + A2
            { note: 659.25, duration: 0.5, harmony: [523.25, 392.00, 246.94], section: 'themeA' }, // E5 + C5 + G4 + B2
            { note: 587.33, duration: 0.25, harmony: [440.00, 261.63], section: 'themeA' }, // D5 + A4 + C3
            { note: 523.25, duration: 0.25, harmony: [415.30, 277.18], section: 'themeA' }, // C5 + G#4 + C#3
            { note: 493.88, duration: 1.0, harmony: [392.00, 293.66, 196.00], section: 'themeA' }, // B4 + G4 + D4 + G2
            { note: 0, duration: 0.25, harmony: [], section: 'themeA' },
            
            // === BREAKDOWN SECTION === (16 beats) - Minimal electronic
            { note: 880.00, duration: 0.125, harmony: [659.25, 440.00], section: 'breakdown' }, // A5 + E5 + A4
            { note: 0, duration: 0.125, harmony: [], section: 'breakdown' },
            { note: 783.99, duration: 0.125, harmony: [587.33, 392.00], section: 'breakdown' }, // G5 + D5 + G4
            { note: 0, duration: 0.125, harmony: [], section: 'breakdown' },
            { note: 659.25, duration: 0.125, harmony: [493.88, 329.63], section: 'breakdown' }, // E5 + B4 + E4
            { note: 0, duration: 0.375, harmony: [], section: 'breakdown' },
            { note: 1046.50, duration: 0.25, harmony: [783.99, 523.25, 261.63], section: 'breakdown' }, // C6 + G5 + C5 + C3
            { note: 0, duration: 0.5, harmony: [], section: 'breakdown' },
            
            // === THEME B === (32 beats) - New melodic variation
            { note: 587.33, duration: 0.75, harmony: [440.00, 349.23, 220.00], section: 'themeB' }, // D5 + A4 + F4 + A2
            { note: 659.25, duration: 0.25, harmony: [523.25, 392.00], section: 'themeB' }, // E5 + C5 + G4
            { note: 739.99, duration: 0.5, harmony: [554.37, 415.30, 246.94], section: 'themeB' }, // F#5 + C#5 + G#4 + B2
            { note: 659.25, duration: 0.25, harmony: [523.25, 329.63], section: 'themeB' }, // E5 + C5 + E4
            { note: 587.33, duration: 0.25, harmony: [440.00, 293.66], section: 'themeB' }, // D5 + A4 + D4
            { note: 523.25, duration: 0.5, harmony: [392.00, 261.63], section: 'themeB' }, // C5 + G4 + C3
            { note: 493.88, duration: 0.5, harmony: [369.99, 277.18], section: 'themeB' }, // B4 + F#4 + C#3
            { note: 554.37, duration: 0.75, harmony: [415.30, 311.13, 185.00], section: 'themeB' }, // C#5 + G#4 + D#4 + F#2
            { note: 523.25, duration: 0.25, harmony: [392.00, 196.00], section: 'themeB' }, // C5 + G4 + G2
            
            // === BUILD UP === (16 beats) - Energy increase
            { note: 880.00, duration: 0.25, harmony: [659.25, 440.00, 220.00], section: 'buildup' }, // A5 + E5 + A4 + A2
            { note: 932.33, duration: 0.25, harmony: [698.46, 466.16, 233.08], section: 'buildup' }, // A#5 + F5 + A#4 + A#2
            { note: 987.77, duration: 0.25, harmony: [739.99, 493.88, 246.94], section: 'buildup' }, // B5 + F#5 + B4 + B2
            { note: 1046.50, duration: 0.25, harmony: [783.99, 523.25, 261.63], section: 'buildup' }, // C6 + G5 + C5 + C3
            { note: 1108.73, duration: 0.5, harmony: [830.61, 554.37, 277.18], section: 'buildup' }, // C#6 + G#5 + C#5 + C#3
            { note: 1174.66, duration: 0.5, harmony: [880.00, 587.33, 293.66], section: 'buildup' }, // D6 + A5 + D5 + D3
            { note: 1244.51, duration: 1.0, harmony: [932.33, 622.25, 311.13], section: 'buildup' }, // D#6 + A#5 + D#5 + D#3
            
            // === CLIMAX === (32 beats) - Full orchestration
            { note: 1318.51, duration: 0.5, harmony: [987.77, 659.25, 329.63, 164.81], section: 'climax' }, // E6 + B5 + E5 + E4 + E2
            { note: 1174.66, duration: 0.25, harmony: [880.00, 587.33, 293.66], section: 'climax' }, // D6 + A5 + D5 + D3
            { note: 1046.50, duration: 0.25, harmony: [783.99, 523.25, 261.63], section: 'climax' }, // C6 + G5 + C5 + C3
            { note: 987.77, duration: 0.5, harmony: [739.99, 493.88, 246.94], section: 'climax' }, // B5 + F#5 + B4 + B2
            { note: 880.00, duration: 0.25, harmony: [659.25, 440.00, 220.00], section: 'climax' }, // A5 + E5 + A4 + A2
            { note: 783.99, duration: 0.25, harmony: [587.33, 392.00, 196.00], section: 'climax' }, // G5 + D5 + G4 + G2
            { note: 698.46, duration: 0.5, harmony: [523.25, 349.23, 174.61], section: 'climax' }, // F5 + C5 + F4 + F2
            { note: 659.25, duration: 1.0, harmony: [493.88, 329.63, 164.81], section: 'climax' }, // E5 + B4 + E4 + E2
            
            // === OUTRO === (16 beats) - Fade to ambient
            { note: 523.25, duration: 1.5, harmony: [392.00, 261.63, 130.81], section: 'outro' }, // C5 + G4 + C3 + C2
            { note: 440.00, duration: 1.0, harmony: [329.63, 220.00, 110.00], section: 'outro' }, // A4 + E4 + A2 + A1
            { note: 392.00, duration: 1.5, harmony: [293.66, 196.00, 98.00], section: 'outro' }, // G4 + D4 + G2 + G1
            { note: 329.63, duration: 2.0, harmony: [246.94, 164.81, 82.41], section: 'outro' }, // E4 + B3 + E2 + E1
        ];
        
        // Advanced bass patterns for each section
        const bassPatterns = {
            intro: [130.81, 164.81, 110.00, 146.83], // C2 E2 A1 D2
            themeA: [196.00, 196.00, 220.00, 220.00, 196.00, 196.00, 175.00, 175.00, 220.00, 233.08, 246.94, 261.63], // Extended bass
            breakdown: [82.41, 0, 98.00, 0, 110.00, 0, 123.47, 130.81], // Minimal bass
            themeB: [185.00, 207.65, 220.00, 246.94, 261.63, 293.66, 311.13, 329.63], // Progressive bass
            buildup: [220.00, 233.08, 246.94, 261.63, 277.18, 293.66, 311.13, 329.63], // Rising bass
            climax: [164.81, 174.61, 185.00, 196.00, 207.65, 220.00, 233.08, 246.94, 261.63, 277.18, 293.66, 311.13], // Full bass
            outro: [130.81, 123.47, 110.00, 98.00] // Descending bass
        };
        
        let noteIndex = 0;
        let sectionBassIndex = 0;
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
            
            const currentNote = composition[noteIndex];
            const currentSection = currentNote.section;
            const bassPattern = bassPatterns[currentSection];
            const bassNote = bassPattern[sectionBassIndex % bassPattern.length];
            
            const now = this.audioContext.currentTime;
            
            // Play main melody note
            if (currentNote.note > 0) {
                const mainSound = this.createMusicNote(currentNote.note, currentNote.duration, 'lead');
                if (mainSound) {
                    mainSound.oscillator.start(now);
                    mainSound.oscillator.stop(now + currentNote.duration);
                    this.currentMusicNotes.push(mainSound);
                }
                
                // Play harmony notes with advanced spacing
                currentNote.harmony.forEach((harmonyFreq, index) => {
                    const harmonySound = this.createMusicNote(harmonyFreq, currentNote.duration, 'harmony');
                    if (harmonySound) {
                        const delay = index * 0.015 + (Math.random() * 0.01); // Slight randomization for organic feel
                        harmonySound.oscillator.start(now + delay);
                        harmonySound.oscillator.stop(now + currentNote.duration);
                        this.currentMusicNotes.push(harmonySound);
                    }
                });
            }
            
            // Play bass line
            if (bassNote > 0) {
                const bassSound = this.createMusicNote(bassNote, currentNote.duration, 'bass');
                if (bassSound) {
                    bassSound.oscillator.start(now);
                    bassSound.oscillator.stop(now + currentNote.duration);
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
        
        // Start playing with dynamic timing based on note duration
        const scheduleNextNote = () => {
            if (!this.settings.musicEnabled) return;
            
            playNextNote();
            const currentNote = composition[noteIndex];
            const nextDelay = (currentNote.duration * 300) + 50; // Convert to ms with slight overlap
            setTimeout(scheduleNextNote, nextDelay);
        };
        
        scheduleNextNote();
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