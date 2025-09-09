#!/usr/bin/env python3
"""
Generate complete Tetris Theme (Korobeiniki) - Full 2-3 minute version
"""

import numpy as np
import struct
import wave
import os
import subprocess
from scipy import signal

class TetrisThemeGenerator:
    def __init__(self, sample_rate=44100):
        self.sample_rate = sample_rate
        self.output_dir = "/var/www/html/tetris/android-app/app/src/main/res/raw/"
        os.makedirs(self.output_dir, exist_ok=True)
        
    def note_to_freq(self, note):
        """Convert note name to frequency"""
        notes = {
            'C4': 261.63, 'D4': 293.66, 'E4': 329.63, 'F4': 349.23, 'G4': 392.00, 'A4': 440.00, 'B4': 493.88,
            'C5': 523.25, 'D5': 587.33, 'E5': 659.25, 'F5': 698.46, 'G5': 783.99, 'A5': 880.00, 'B5': 987.77,
            'C6': 1046.50, 'D6': 1174.66, 'E6': 1318.51
        }
        return notes.get(note, 440.0)
    
    def create_envelope(self, duration, attack=0.01, decay=0.1, sustain=0.7, release=0.2):
        """Create ADSR envelope"""
        samples = int(duration * self.sample_rate)
        envelope = np.zeros(samples)
        
        attack_samples = int(attack * samples)
        decay_samples = int(decay * samples)
        release_samples = int(release * samples)
        sustain_samples = samples - attack_samples - decay_samples - release_samples
        
        # Attack
        envelope[:attack_samples] = np.linspace(0, 1, attack_samples)
        
        # Decay
        start = attack_samples
        end = start + decay_samples
        envelope[start:end] = np.linspace(1, sustain, decay_samples)
        
        # Sustain
        start = end
        end = start + sustain_samples
        envelope[start:end] = sustain
        
        # Release
        start = end
        envelope[start:] = np.linspace(sustain, 0, release_samples)
        
        return envelope
    
    def synthesize_note(self, freq, duration, waveform='mixed'):
        """Synthesize a single note with rich harmonics"""
        t = np.linspace(0, duration, int(self.sample_rate * duration))
        
        # Main tone
        wave = np.sin(2 * np.pi * freq * t)
        
        if waveform == 'mixed':
            # Add harmonics for richer sound
            wave += 0.3 * np.sin(2 * np.pi * freq * 2 * t)  # Octave
            wave += 0.15 * np.sin(2 * np.pi * freq * 3 * t)  # Fifth
            wave += 0.1 * signal.square(2 * np.pi * freq * t)  # Square for bite
            wave += 0.05 * signal.sawtooth(2 * np.pi * freq * 0.5 * t)  # Sub bass
        
        envelope = self.create_envelope(duration, attack=0.02, sustain=0.6, release=0.1)
        return wave * envelope * 0.3
    
    def create_full_korobeiniki(self):
        """Create the complete Korobeiniki theme with all sections"""
        print("Generating complete Tetris theme (Korobeiniki)...")
        
        # Tempo in BPM (normal speed, not too fast)
        tempo = 120
        beat_duration = 60.0 / tempo
        
        # Complete Korobeiniki melody - all sections
        # Format: (note, duration_in_beats)
        melody_section_a = [
            # Measure 1-2 (Main theme)
            ('E5', 1), ('B4', 0.5), ('C5', 0.5), ('D5', 1), ('C5', 0.5), ('B4', 0.5),
            ('A4', 1), ('A4', 0.5), ('C5', 0.5), ('E5', 1), ('D5', 0.5), ('C5', 0.5),
            
            # Measure 3-4
            ('B4', 1.5), ('C5', 0.5), ('D5', 1), ('E5', 1),
            ('C5', 1), ('A4', 1), ('A4', 2),
            
            # Measure 5-6 (Development)
            ('D5', 1.5), ('F5', 0.5), ('A5', 1), ('G5', 0.5), ('F5', 0.5),
            ('E5', 1.5), ('C5', 0.5), ('E5', 1), ('D5', 0.5), ('C5', 0.5),
            
            # Measure 7-8
            ('B4', 1), ('B4', 0.5), ('C5', 0.5), ('D5', 1), ('E5', 1),
            ('C5', 1), ('A4', 1), ('A4', 2),
        ]
        
        melody_section_b = [
            # Measure 9-10 (B section - variation)
            ('E4', 2), ('C4', 2),
            ('D4', 2), ('B4', 2),
            ('C4', 2), ('A4', 2),
            ('G4', 4),
            
            # Measure 13-14
            ('E4', 2), ('C4', 2),
            ('D4', 2), ('B4', 2),
            ('C5', 1), ('E5', 1), ('A5', 2),
            ('G5', 4),
        ]
        
        # Bass line pattern
        bass_pattern = [
            ('A4', 2), ('A4', 2), ('E4', 2), ('E4', 2),
            ('A4', 2), ('A4', 2), ('E4', 2), ('E4', 2),
            ('D4', 2), ('D4', 2), ('C4', 2), ('C4', 2),
            ('B4', 2), ('B4', 2), ('E4', 2), ('E4', 2),
        ]
        
        # Generate complete song structure: A-A-B-A-B-A (about 2.5 minutes)
        full_melody = []
        full_melody.extend(melody_section_a * 2)  # AA
        full_melody.extend(melody_section_b)       # B
        full_melody.extend(melody_section_a)       # A
        full_melody.extend(melody_section_b)       # B
        full_melody.extend(melody_section_a * 2)   # AA (finale)
        
        # Generate melody track
        melody_track = np.array([])
        for note, beats in full_melody:
            duration = beats * beat_duration
            freq = self.note_to_freq(note)
            tone = self.synthesize_note(freq, duration)
            melody_track = np.concatenate([melody_track, tone])
        
        # Generate bass track
        bass_track = np.array([])
        bass_repeats = int(len(melody_track) / (len(bass_pattern) * beat_duration * self.sample_rate / 2)) + 1
        
        for _ in range(bass_repeats):
            for note, beats in bass_pattern:
                duration = beats * beat_duration
                freq = self.note_to_freq(note) * 0.5  # One octave lower
                tone = self.synthesize_note(freq, duration, 'mixed')
                bass_track = np.concatenate([bass_track, tone])
                if len(bass_track) >= len(melody_track):
                    break
            if len(bass_track) >= len(melody_track):
                break
        
        # Trim bass to match melody length
        bass_track = bass_track[:len(melody_track)]
        
        # Generate simple drum pattern
        drums = self.create_drum_pattern(len(melody_track) / self.sample_rate, tempo)
        
        # Mix all tracks
        final_mix = melody_track + bass_track * 0.6 + drums * 0.4
        
        # Apply some mastering
        final_mix = self.apply_compression(final_mix)
        final_mix = self.add_reverb(final_mix, 0.05)
        
        # Normalize
        final_mix = final_mix / np.max(np.abs(final_mix)) * 0.8
        
        print(f"Generated {len(final_mix) / self.sample_rate:.1f} seconds of music")
        return final_mix
    
    def create_drum_pattern(self, duration, tempo):
        """Create a simple drum pattern"""
        samples = int(duration * self.sample_rate)
        drums = np.zeros(samples)
        
        beat_samples = int((60 / tempo) * self.sample_rate)
        
        # Kick on 1 and 3
        for i in range(0, samples, beat_samples * 2):
            if i < samples:
                kick = self.create_kick()
                end = min(i + len(kick), samples)
                drums[i:end] += kick[:end-i] * 0.5
        
        # Snare on 2 and 4
        for i in range(beat_samples, samples, beat_samples * 2):
            if i < samples:
                snare = self.create_snare()
                end = min(i + len(snare), samples)
                drums[i:end] += snare[:end-i] * 0.3
        
        # Hi-hat on 8th notes
        for i in range(0, samples, beat_samples // 2):
            if i < samples:
                hihat = self.create_hihat()
                end = min(i + len(hihat), samples)
                drums[i:end] += hihat[:end-i] * 0.1
        
        return drums
    
    def create_kick(self):
        """Create kick drum sound"""
        duration = 0.15
        t = np.linspace(0, duration, int(self.sample_rate * duration))
        
        # Pitch envelope
        pitch_env = np.exp(-35 * t)
        frequency = 60 * pitch_env + 40
        
        # Generate tone
        kick = np.sin(2 * np.pi * frequency * t)
        
        # Amplitude envelope
        amp_env = np.exp(-10 * t)
        kick *= amp_env
        
        return kick
    
    def create_snare(self):
        """Create snare drum sound"""
        duration = 0.1
        samples = int(self.sample_rate * duration)
        
        # Tone component
        tone = np.sin(2 * np.pi * 200 * np.linspace(0, duration, samples))
        
        # Noise component
        noise = np.random.normal(0, 1, samples)
        noise = self.lowpass_filter(noise, 5000)
        
        # Mix and apply envelope
        snare = (tone * 0.3 + noise * 0.7) * np.exp(-30 * np.linspace(0, duration, samples))
        
        return snare
    
    def create_hihat(self):
        """Create hi-hat sound"""
        duration = 0.02
        samples = int(self.sample_rate * duration)
        
        # High-frequency noise
        hihat = np.random.normal(0, 1, samples)
        hihat = self.lowpass_filter(hihat, 10000)
        
        # Sharp envelope
        hihat *= np.exp(-200 * np.linspace(0, duration, samples))
        
        return hihat
    
    def lowpass_filter(self, audio, cutoff=4000):
        """Apply lowpass filter"""
        nyquist = self.sample_rate / 2
        normal_cutoff = cutoff / nyquist
        b, a = signal.butter(4, normal_cutoff, btype='low', analog=False)
        return signal.filtfilt(b, a, audio)
    
    def add_reverb(self, audio, room_size=0.1):
        """Add simple reverb"""
        delay_samples = int(room_size * self.sample_rate)
        reverb = np.zeros(len(audio) + delay_samples)
        reverb[:len(audio)] = audio
        
        # Create delay taps
        for i in range(3):
            delay = delay_samples * (i + 1)
            gain = 0.5 ** (i + 1)
            if delay < len(reverb):
                reverb[delay:] += reverb[:-delay] * gain * 0.3
        
        return reverb[:len(audio)]
    
    def apply_compression(self, audio, threshold=0.5, ratio=4):
        """Apply simple compression"""
        compressed = audio.copy()
        above_threshold = np.abs(compressed) > threshold
        compressed[above_threshold] = threshold + (compressed[above_threshold] - threshold) / ratio
        return compressed
    
    def save_audio(self, audio_data, filename):
        """Save audio to MP3 file"""
        # Ensure audio is in correct format
        audio_data = np.clip(audio_data, -1, 1)
        audio_data = (audio_data * 32767).astype(np.int16)
        
        # Save as WAV first
        wav_path = os.path.join(self.output_dir, filename + '.wav')
        with wave.open(wav_path, 'wb') as wav_file:
            wav_file.setnchannels(1)  # Mono
            wav_file.setsampwidth(2)  # 16-bit
            wav_file.setframerate(self.sample_rate)
            wav_file.writeframes(audio_data.tobytes())
        
        # Convert to MP3
        mp3_path = os.path.join(self.output_dir, filename + '.mp3')
        try:
            subprocess.run([
                'ffmpeg', '-i', wav_path, '-codec:a', 'libmp3lame',
                '-b:a', '128k', '-ar', '44100', mp3_path, '-y'
            ], check=True, capture_output=True)
            
            # Remove WAV file
            os.remove(wav_path)
            print(f"✓ Saved: {filename}.mp3")
            return mp3_path
        except subprocess.CalledProcessError as e:
            print(f"Error converting to MP3: {e}")
            return wav_path
    
    def generate_all_versions(self):
        """Generate all versions of the theme"""
        print("\n=== Generating Complete Tetris Theme Music ===\n")
        
        # Generate main theme
        theme = self.create_full_korobeiniki()
        
        # Save Theme A (normal speed)
        self.save_audio(theme, 'music_theme_a')
        
        # Theme B (slightly faster - 110%)
        theme_b = signal.resample(theme, int(len(theme) * 0.91))  # Faster = shorter duration
        self.save_audio(theme_b, 'music_theme_b')
        
        # Theme C (faster - 120%)
        theme_c = signal.resample(theme, int(len(theme) * 0.83))  # Even faster
        theme_c = self.apply_compression(theme_c, 0.4, 3)  # More aggressive
        self.save_audio(theme_c, 'music_theme_c')
        
        print(f"\n✓ Generated complete Tetris theme music")
        print(f"  Duration: {len(theme) / self.sample_rate:.1f} seconds")
        print(f"  Versions: A (normal), B (fast), C (very fast)")

if __name__ == "__main__":
    generator = TetrisThemeGenerator()
    generator.generate_all_versions()