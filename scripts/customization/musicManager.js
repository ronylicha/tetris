// Music Manager - Handles background music tracks

export class MusicManager {
    constructor(audioManager) {
        this.audioManager = audioManager;
        this.currentTrack = 'classic';
        this.tracks = {
            classic: {
                name: 'Classic',
                tempo: 120,
                style: 'chiptune',
                notes: this.getClassicNotes(),
                unlocked: true
            },
            modern: {
                name: 'Modern',
                tempo: 128,
                style: 'electronic',
                notes: this.getModernNotes(),
                unlocked: true
            },
            synthwave: {
                name: 'Synthwave',
                tempo: 118,
                style: 'synthwave',
                notes: this.getSynthwaveNotes(),
                unlocked: false
            },
            lofi: {
                name: 'Lo-Fi',
                tempo: 85,
                style: 'lofi',
                notes: this.getLofiNotes(),
                unlocked: false
            },
            orchestral: {
                name: 'Orchestral',
                tempo: 100,
                style: 'orchestral',
                notes: this.getOrchestralNotes(),
                unlocked: false
            },
            jazz: {
                name: 'Jazz',
                tempo: 120,
                style: 'jazz',
                notes: this.getJazzNotes(),
                unlocked: false
            },
            metal: {
                name: 'Metal',
                tempo: 140,
                style: 'metal',
                notes: this.getMetalNotes(),
                unlocked: false
            },
            chiptune: {
                name: 'Chiptune',
                tempo: 125,
                style: 'chiptune',
                notes: this.getChiptuneNotes(),
                unlocked: false
            }
        };
        
        this.loadSavedTrack();
    }
    
    loadSavedTrack() {
        const saved = localStorage.getItem('tetris_music');
        if (saved && this.tracks[saved]) {
            this.currentTrack = saved;
        }
    }
    
    setTrack(trackName) {
        if (this.tracks[trackName] && this.tracks[trackName].unlocked) {
            this.currentTrack = trackName;
            localStorage.setItem('tetris_music', trackName);
            
            // Update audio manager if it exists
            if (this.audioManager) {
                this.audioManager.stopBackgroundMusic();
                // The audio manager will need to be updated to support different tracks
                this.applyMusicStyle();
                this.audioManager.playBackgroundMusic();
            }
            
            return true;
        }
        return false;
    }
    
    getCurrentTrack() {
        return this.tracks[this.currentTrack];
    }
    
    applyMusicStyle() {
        const track = this.getCurrentTrack();
        
        // Update audio manager settings based on track style
        if (this.audioManager) {
            // Set tempo
            this.audioManager.baseTempo = track.tempo;
            
            // Apply style-specific audio parameters
            switch (track.style) {
                case 'chiptune':
                    this.audioManager.waveType = 'square';
                    this.audioManager.filterFreq = 2000;
                    break;
                case 'synthwave':
                    this.audioManager.waveType = 'sawtooth';
                    this.audioManager.filterFreq = 1500;
                    break;
                case 'lofi':
                    this.audioManager.waveType = 'sine';
                    this.audioManager.filterFreq = 800;
                    break;
                case 'electronic':
                    this.audioManager.waveType = 'triangle';
                    this.audioManager.filterFreq = 3000;
                    break;
                case 'orchestral':
                    this.audioManager.waveType = 'sine';
                    this.audioManager.filterFreq = 5000;
                    break;
                case 'jazz':
                    this.audioManager.waveType = 'sine';
                    this.audioManager.filterFreq = 2500;
                    break;
                case 'metal':
                    this.audioManager.waveType = 'sawtooth';
                    this.audioManager.filterFreq = 4000;
                    break;
            }
        }
    }
    
    unlockTrack(trackName) {
        if (this.tracks[trackName]) {
            this.tracks[trackName].unlocked = true;
            return true;
        }
        return false;
    }
    
    // Note sequences for different tracks (simplified melodies)
    getClassicNotes() {
        return [
            { note: 'E4', duration: 0.25 },
            { note: 'B3', duration: 0.125 },
            { note: 'C4', duration: 0.125 },
            { note: 'D4', duration: 0.25 },
            { note: 'C4', duration: 0.125 },
            { note: 'B3', duration: 0.125 },
            { note: 'A3', duration: 0.25 }
        ];
    }
    
    getModernNotes() {
        return [
            { note: 'C4', duration: 0.5 },
            { note: 'E4', duration: 0.5 },
            { note: 'G4', duration: 0.5 },
            { note: 'C5', duration: 0.5 }
        ];
    }
    
    getSynthwaveNotes() {
        return [
            { note: 'A3', duration: 0.5 },
            { note: 'C4', duration: 0.25 },
            { note: 'E4', duration: 0.25 },
            { note: 'A4', duration: 0.5 }
        ];
    }
    
    getLofiNotes() {
        return [
            { note: 'F3', duration: 1 },
            { note: 'A3', duration: 1 },
            { note: 'C4', duration: 1 },
            { note: 'E4', duration: 1 }
        ];
    }
    
    getOrchestralNotes() {
        return [
            { note: 'D3', duration: 0.5 },
            { note: 'F3', duration: 0.5 },
            { note: 'A3', duration: 0.5 },
            { note: 'D4', duration: 0.5 }
        ];
    }
    
    getJazzNotes() {
        return [
            { note: 'Bb3', duration: 0.333 },
            { note: 'D4', duration: 0.333 },
            { note: 'F4', duration: 0.333 },
            { note: 'A4', duration: 0.5 }
        ];
    }
    
    getMetalNotes() {
        return [
            { note: 'E2', duration: 0.125 },
            { note: 'E2', duration: 0.125 },
            { note: 'F2', duration: 0.125 },
            { note: 'E2', duration: 0.125 }
        ];
    }
    
    getChiptuneNotes() {
        return [
            { note: 'C4', duration: 0.125 },
            { note: 'C4', duration: 0.125 },
            { note: 'G4', duration: 0.25 },
            { note: 'E4', duration: 0.25 }
        ];
    }
    
    previewTrack(trackName) {
        // Simple preview - could be expanded with actual audio playback
        console.log('Previewing track:', trackName);
        
        // Apply track settings temporarily
        if (this.tracks[trackName]) {
            const track = this.tracks[trackName];
            if (this.audioManager) {
                // Store current settings
                this.previewBackup = {
                    tempo: this.audioManager.baseTempo,
                    waveType: this.audioManager.waveType
                };
                
                // Apply preview settings
                this.audioManager.baseTempo = track.tempo;
                this.applyTrackStyle(track.style);
            }
        }
    }
    
    stopPreview() {
        // Restore original settings
        if (this.previewBackup && this.audioManager) {
            this.audioManager.baseTempo = this.previewBackup.tempo;
            this.audioManager.waveType = this.previewBackup.waveType;
            this.previewBackup = null;
        }
    }
    
    applyTrackStyle(style) {
        if (!this.audioManager) return;
        
        switch (style) {
            case 'chiptune':
                this.audioManager.waveType = 'square';
                break;
            case 'synthwave':
                this.audioManager.waveType = 'sawtooth';
                break;
            case 'lofi':
                this.audioManager.waveType = 'sine';
                break;
            case 'electronic':
                this.audioManager.waveType = 'triangle';
                break;
            case 'orchestral':
                this.audioManager.waveType = 'sine';
                break;
            case 'jazz':
                this.audioManager.waveType = 'sine';
                break;
            case 'metal':
                this.audioManager.waveType = 'sawtooth';
                break;
        }
    }
}

// Export singleton instance (will be initialized with audioManager)
export const musicManager = new MusicManager(null);