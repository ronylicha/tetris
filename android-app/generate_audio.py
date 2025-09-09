#!/usr/bin/env python3
"""
Professional Audio Generator for Android Tetris
Generates modern versions of classic Tetris sounds and music
"""

import numpy as np
import struct
import wave
import os
from scipy import signal
from typing import List, Tuple
import subprocess

class TetrisAudioGenerator:
    def __init__(self, sample_rate=44100):
        self.sample_rate = sample_rate
        self.output_dir = "/var/www/html/tetris/android-app/app/src/main/res/raw/"
        os.makedirs(self.output_dir, exist_ok=True)
        
    def create_envelope(self, duration, attack=0.01, decay=0.1, sustain=0.7, release=0.2):
        """Create ADSR envelope for natural sound"""
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
    
    def synthesize_tone(self, frequency, duration, waveform='sine', envelope=None):
        """Synthesize a tone with specified waveform"""
        t = np.linspace(0, duration, int(self.sample_rate * duration))
        
        if waveform == 'sine':
            wave = np.sin(2 * np.pi * frequency * t)
        elif waveform == 'square':
            wave = signal.square(2 * np.pi * frequency * t)
        elif waveform == 'sawtooth':
            wave = signal.sawtooth(2 * np.pi * frequency * t)
        elif waveform == 'triangle':
            wave = signal.sawtooth(2 * np.pi * frequency * t, width=0.5)
        else:
            wave = np.sin(2 * np.pi * frequency * t)
        
        if envelope is not None:
            wave *= envelope
        
        return wave
    
    def add_reverb(self, audio, room_size=0.1, damping=0.5):
        """Add simple reverb effect"""
        delay_samples = int(room_size * self.sample_rate)
        reverb = np.zeros(len(audio) + delay_samples)
        reverb[:len(audio)] = audio
        
        # Create delay taps
        for i in range(3):
            delay = delay_samples * (i + 1)
            gain = damping ** (i + 1)
            if delay < len(reverb):
                reverb[delay:] += reverb[:-delay] * gain * 0.3
        
        return reverb[:len(audio)]
    
    def lowpass_filter(self, audio, cutoff=4000):
        """Apply lowpass filter"""
        nyquist = self.sample_rate / 2
        normal_cutoff = cutoff / nyquist
        b, a = signal.butter(4, normal_cutoff, btype='low', analog=False)
        return signal.filtfilt(b, a, audio)
    
    def create_korobeiniki_theme(self):
        """Create modern version of Tetris theme (Korobeiniki)"""
        print("Generating Korobeiniki theme...")
        
        # Korobeiniki melody notes (simplified)
        # E5, B4, C5, D5, C5, B4, A4, A4, C5, E5, D5, C5, B4...
        melody_notes = [
            (659.25, 0.25), (493.88, 0.125), (523.25, 0.125), (587.33, 0.25),
            (523.25, 0.125), (493.88, 0.125), (440.00, 0.25), (440.00, 0.125),
            (523.25, 0.125), (659.25, 0.25), (587.33, 0.125), (523.25, 0.125),
            (493.88, 0.25), (493.88, 0.125), (523.25, 0.125), (587.33, 0.25),
            (659.25, 0.25), (523.25, 0.25), (440.00, 0.25), (440.00, 0.5),
        ]
        
        # Bass line (root notes)
        bass_notes = [
            (110.00, 0.5), (110.00, 0.5), (110.00, 0.5), (110.00, 0.5),
            (82.41, 0.5), (82.41, 0.5), (110.00, 0.5), (110.00, 0.5),
        ]
        
        tempo = 120  # BPM
        beat_duration = 60 / tempo
        
        # Generate melody
        melody = np.array([])
        for freq, duration in melody_notes * 4:  # Repeat 4 times
            note_duration = duration * beat_duration
            envelope = self.create_envelope(note_duration, attack=0.02, sustain=0.6)
            
            # Layer multiple waveforms for rich sound
            tone1 = self.synthesize_tone(freq, note_duration, 'sawtooth', envelope) * 0.3
            tone2 = self.synthesize_tone(freq * 2, note_duration, 'square', envelope) * 0.1
            tone3 = self.synthesize_tone(freq * 0.5, note_duration, 'sine', envelope) * 0.2
            
            note = tone1 + tone2 + tone3
            melody = np.concatenate([melody, note])
        
        # Generate bass
        bass = np.array([])
        bass_duration = len(melody) / self.sample_rate
        bass_beat = 0
        while len(bass) < len(melody):
            freq, duration = bass_notes[bass_beat % len(bass_notes)]
            note_duration = duration * beat_duration
            envelope = self.create_envelope(note_duration, attack=0.01, release=0.1)
            
            tone = self.synthesize_tone(freq, note_duration, 'sine', envelope) * 0.4
            sub_bass = self.synthesize_tone(freq * 0.5, note_duration, 'sine', envelope) * 0.3
            
            bass = np.concatenate([bass, tone + sub_bass])
            bass_beat += 1
        
        # Trim bass to match melody length
        bass = bass[:len(melody)]
        
        # Generate drums
        drums = self.create_drum_pattern(len(melody) / self.sample_rate, tempo)
        
        # Mix all elements
        mix = melody + bass + drums
        
        # Add effects
        mix = self.lowpass_filter(mix, 8000)
        mix = self.add_reverb(mix, 0.05, 0.7)
        
        # Normalize
        mix = mix / np.max(np.abs(mix)) * 0.8
        
        return mix
    
    def create_drum_pattern(self, duration, tempo):
        """Create drum pattern"""
        samples = int(duration * self.sample_rate)
        drums = np.zeros(samples)
        
        beat_samples = int((60 / tempo) * self.sample_rate)
        
        # Kick drum on beats 1 and 3
        for i in range(0, samples, beat_samples * 2):
            kick = self.create_kick_drum()
            end = min(i + len(kick), samples)
            drums[i:end] += kick[:end-i] * 0.5
        
        # Snare on beats 2 and 4
        for i in range(beat_samples, samples, beat_samples * 2):
            snare = self.create_snare_drum()
            end = min(i + len(snare), samples)
            drums[i:end] += snare[:end-i] * 0.3
        
        # Hi-hats on 16th notes
        for i in range(0, samples, beat_samples // 4):
            hihat = self.create_hihat()
            end = min(i + len(hihat), samples)
            drums[i:end] += hihat[:end-i] * 0.15
        
        return drums
    
    def create_kick_drum(self):
        """Synthesize kick drum"""
        duration = 0.15
        t = np.linspace(0, duration, int(self.sample_rate * duration))
        
        # Pitch envelope from 60Hz to 40Hz
        pitch_env = np.exp(-35 * t)
        frequency = 60 * pitch_env + 40
        
        # Generate tone
        kick = np.sin(2 * np.pi * frequency * t)
        
        # Amplitude envelope
        amp_env = np.exp(-10 * t)
        kick *= amp_env
        
        # Add click
        click = np.random.normal(0, 1, len(kick)) * np.exp(-100 * t)
        kick += click * 0.3
        
        return kick
    
    def create_snare_drum(self):
        """Synthesize snare drum"""
        duration = 0.1
        samples = int(self.sample_rate * duration)
        
        # Tone component (200Hz)
        tone = self.synthesize_tone(200, duration, 'sine')
        
        # Noise component
        noise = np.random.normal(0, 1, samples)
        noise = self.lowpass_filter(noise, 5000)
        
        # Envelope
        envelope = np.exp(-30 * np.linspace(0, duration, samples))
        
        snare = (tone * 0.3 + noise * 0.7) * envelope
        return snare
    
    def create_hihat(self):
        """Synthesize hi-hat"""
        duration = 0.02
        samples = int(self.sample_rate * duration)
        
        # High-frequency noise
        hihat = np.random.normal(0, 1, samples)
        hihat = self.lowpass_filter(hihat, 10000)
        
        # Sharp envelope
        envelope = np.exp(-200 * np.linspace(0, duration, samples))
        
        return hihat * envelope
    
    def create_sound_effects(self):
        """Create all game sound effects"""
        effects = {}
        
        # Movement sound - subtle mechanical click
        print("Generating movement sound...")
        duration = 0.03
        envelope = self.create_envelope(duration, attack=0.002, decay=0.005, sustain=0.3, release=0.023)
        move = self.synthesize_tone(200, duration, 'sine', envelope) * 0.3
        click = np.random.normal(0, 0.1, int(0.002 * self.sample_rate))
        move[:len(click)] += click
        effects['sfx_move'] = self.lowpass_filter(move, 4000)
        
        # Rotation sound - satisfying mechanical click
        print("Generating rotation sound...")
        duration = 0.02
        rot1 = self.synthesize_tone(800, duration, 'triangle') * 0.4
        rot2 = self.synthesize_tone(400, duration, 'sine') * 0.3
        noise = np.random.normal(0, 0.2, int(0.005 * self.sample_rate))
        rotation = np.zeros(int(duration * self.sample_rate))
        rotation[:len(rot1)] = rot1
        rotation[:len(rot2)] += rot2
        rotation[:len(noise)] += noise
        effects['sfx_rotate'] = rotation * np.exp(-50 * np.linspace(0, duration, len(rotation)))
        
        # Soft drop - whoosh
        print("Generating soft drop sound...")
        duration = 0.15
        t = np.linspace(0, duration, int(self.sample_rate * duration))
        freq_sweep = np.linspace(8000, 2000, len(t))
        whoosh = np.sin(2 * np.pi * freq_sweep * t / self.sample_rate)
        whoosh *= np.exp(-10 * t)
        effects['sfx_drop'] = self.add_reverb(whoosh * 0.3, 0.02)
        
        # Hard drop - impact
        print("Generating hard drop sound...")
        impact = self.create_kick_drum() * 1.5
        thud = self.synthesize_tone(60, 0.1, 'sine') * np.exp(-20 * np.linspace(0, 0.1, int(0.1 * self.sample_rate)))
        effects['sfx_hard_drop'] = np.concatenate([impact[:int(0.05 * self.sample_rate)], thud]) * 0.6
        
        # Line clear - ascending chime
        print("Generating line clear sound...")
        duration = 0.3
        frequencies = [523.25, 659.25, 783.99]  # C5, E5, G5
        line_clear = np.zeros(int(duration * self.sample_rate))
        for i, freq in enumerate(frequencies):
            start = int(i * 0.08 * self.sample_rate)
            note_duration = 0.2
            envelope = self.create_envelope(note_duration, attack=0.01, sustain=0.5)
            tone = self.synthesize_tone(freq, note_duration, 'sine', envelope)
            end = min(start + len(tone), len(line_clear))
            line_clear[start:end] += tone[:end-start] * 0.3
        effects['sfx_line_clear'] = self.add_reverb(line_clear, 0.1)
        
        # Tetris - epic fanfare
        print("Generating Tetris sound...")
        duration = 0.8
        fanfare_notes = [523.25, 659.25, 783.99, 1046.50]  # C5, E5, G5, C6
        tetris = np.zeros(int(duration * self.sample_rate))
        for i, freq in enumerate(fanfare_notes):
            envelope = self.create_envelope(duration - i*0.1, attack=0.05, sustain=0.7)
            tone1 = self.synthesize_tone(freq, duration - i*0.1, 'sawtooth', envelope) * 0.2
            tone2 = self.synthesize_tone(freq * 2, duration - i*0.1, 'square', envelope) * 0.1
            start = int(i * 0.1 * self.sample_rate)
            combined = tone1 + tone2
            end = min(start + len(combined), len(tetris))
            tetris[start:end] += combined[:end-start]
        effects['sfx_tetris'] = self.add_reverb(tetris, 0.2) * 0.7
        
        # T-Spin - spinning whoosh
        print("Generating T-Spin sound...")
        duration = 0.3
        t = np.linspace(0, duration, int(self.sample_rate * duration))
        freq_sweep = 440 * (1 + np.sin(2 * np.pi * 10 * t))  # Modulated frequency
        tspin = np.sin(2 * np.pi * freq_sweep * t / self.sample_rate)
        tspin *= self.create_envelope(duration, attack=0.05, sustain=0.5)
        effects['sfx_t_spin'] = tspin * 0.4
        
        # Level up - ascending fanfare
        print("Generating level up sound...")
        duration = 0.5
        level_notes = [440, 554.37, 659.25, 880]  # A4, C#5, E5, A5
        levelup = np.zeros(int(duration * self.sample_rate))
        for i, freq in enumerate(level_notes):
            start = int(i * 0.1 * self.sample_rate)
            note_duration = 0.2
            envelope = self.create_envelope(note_duration, attack=0.02)
            tone = self.synthesize_tone(freq, note_duration, 'square', envelope)
            end = min(start + len(tone), len(levelup))
            levelup[start:end] += tone[:end-start] * 0.3
        effects['sfx_level_up'] = self.add_reverb(levelup, 0.15)
        
        # Game over - descending tones
        print("Generating game over sound...")
        duration = 1.0
        gameover_notes = [440, 415.30, 392, 369.99, 349.23]  # Descending
        gameover = np.zeros(int(duration * self.sample_rate))
        for i, freq in enumerate(gameover_notes):
            start = int(i * 0.15 * self.sample_rate)
            note_duration = 0.3
            envelope = self.create_envelope(note_duration, attack=0.05, release=0.2)
            tone = self.synthesize_tone(freq, note_duration, 'sine', envelope)
            end = min(start + len(tone), len(gameover))
            gameover[start:end] += tone[:end-start] * 0.4
        effects['sfx_game_over'] = self.add_reverb(gameover, 0.3)
        
        # Hold - mechanical lock
        print("Generating hold sound...")
        duration = 0.05
        hold = self.synthesize_tone(220, duration, 'square') * 0.3
        click = self.create_snare_drum()[:int(0.02 * self.sample_rate)]
        effects['sfx_hold'] = np.concatenate([click * 0.5, hold * np.exp(-30 * np.linspace(0, duration, len(hold)))])
        
        # Combo - escalating pitch
        print("Generating combo sound...")
        duration = 0.2
        combo = self.synthesize_tone(523.25, duration, 'sine')  # C5
        combo += self.synthesize_tone(659.25, duration, 'sine') * 0.7  # E5
        combo += self.synthesize_tone(783.99, duration, 'sine') * 0.5  # G5
        combo *= self.create_envelope(duration, attack=0.01, sustain=0.6)
        effects['sfx_combo'] = combo * 0.4
        
        # Power up
        print("Generating power up sound...")
        duration = 0.4
        t = np.linspace(0, duration, int(self.sample_rate * duration))
        freq_sweep = np.linspace(440, 880, len(t))
        powerup = np.sin(2 * np.pi * freq_sweep * t / self.sample_rate)
        powerup *= self.create_envelope(duration, attack=0.1, sustain=0.7)
        effects['sfx_power_up'] = self.add_reverb(powerup * 0.5, 0.1)
        
        # Menu select - pleasant chime
        print("Generating menu select sound...")
        select = self.synthesize_tone(880, 0.1, 'sine')  # A5
        select += self.synthesize_tone(1108.73, 0.1, 'sine') * 0.5  # C#6
        select *= self.create_envelope(0.1, attack=0.01)
        effects['sfx_menu_select'] = select * 0.3
        
        # Menu back - lower tone
        print("Generating menu back sound...")
        back = self.synthesize_tone(440, 0.1, 'sine')  # A4
        back *= self.create_envelope(0.1, attack=0.01, release=0.05)
        effects['sfx_menu_back'] = back * 0.3
        
        # Pause - two-tone beep
        print("Generating pause sound...")
        pause1 = self.synthesize_tone(440, 0.1, 'square') * 0.2
        pause2 = self.synthesize_tone(349.23, 0.1, 'square') * 0.2
        effects['sfx_pause'] = np.concatenate([pause1, pause2])
        
        # Countdown beep
        print("Generating countdown sound...")
        countdown = self.synthesize_tone(880, 0.2, 'square')
        countdown *= self.create_envelope(0.2, attack=0.01, sustain=0.3)
        effects['sfx_countdown'] = countdown * 0.4
        
        # Additional sfx_menu (generic menu sound)
        effects['sfx_menu'] = effects['sfx_menu_select']
        
        return effects
    
    def create_menu_music(self):
        """Create calm menu music"""
        print("Generating menu music...")
        duration = 30  # 30 seconds loop
        
        # Chord progression: Am7 - Dm7 - Gmaj7 - Cmaj7
        chord_frequencies = [
            [220, 261.63, 329.63, 392],  # Am7
            [293.66, 349.23, 440, 523.25],  # Dm7
            [392, 493.88, 587.33, 739.99],  # Gmaj7
            [261.63, 329.63, 392, 493.88],  # Cmaj7
        ]
        
        music = np.zeros(int(duration * self.sample_rate))
        
        # Create pad progression
        chord_duration = 2.0  # Each chord lasts 2 seconds
        for i, chord in enumerate(chord_frequencies * int(duration / (len(chord_frequencies) * chord_duration))):
            start = int(i * chord_duration * self.sample_rate)
            for freq in chord:
                envelope = self.create_envelope(chord_duration * 1.1, attack=0.5, release=0.5)
                tone = self.synthesize_tone(freq, chord_duration * 1.1, 'sine', envelope) * 0.1
                end = min(start + len(tone), len(music))
                music[start:end] += tone[:end-start]
        
        # Add subtle melody hint of Korobeiniki
        melody_notes = [(659.25, 0.5), (493.88, 0.25), (523.25, 0.25), (587.33, 0.5)]
        melody_start = int(10 * self.sample_rate)  # Start melody at 10 seconds
        for freq, dur in melody_notes * 2:
            note_samples = int(dur * self.sample_rate)
            envelope = self.create_envelope(dur, attack=0.1, sustain=0.3)
            tone = self.synthesize_tone(freq * 2, dur, 'sine', envelope) * 0.05
            end = min(melody_start + len(tone), len(music))
            music[melody_start:end] += tone[:end-melody_start]
            melody_start += note_samples
        
        # Add reverb and normalize
        music = self.add_reverb(music, 0.2, 0.8)
        music = music / np.max(np.abs(music)) * 0.6
        
        return music
    
    def create_victory_music(self):
        """Create victory fanfare"""
        print("Generating victory music...")
        duration = 5
        
        # Triumphant progression
        victory_notes = [
            (523.25, 0.2), (523.25, 0.2), (523.25, 0.2),  # C5 x3
            (659.25, 0.3), (587.33, 0.1), (659.25, 0.5),  # E5-D5-E5
            (783.99, 0.3), (659.25, 0.2), (1046.50, 1.0),  # G5-E5-C6
        ]
        
        music = np.zeros(int(duration * self.sample_rate))
        position = 0
        
        for freq, dur in victory_notes:
            note_samples = int(dur * self.sample_rate)
            envelope = self.create_envelope(dur, attack=0.05, sustain=0.8)
            
            # Layer multiple harmonics
            tone1 = self.synthesize_tone(freq, dur, 'sawtooth', envelope) * 0.3
            tone2 = self.synthesize_tone(freq * 2, dur, 'square', envelope) * 0.1
            tone3 = self.synthesize_tone(freq * 0.5, dur, 'sine', envelope) * 0.2
            
            note = tone1 + tone2 + tone3
            end = min(position + len(note), len(music))
            music[position:end] += note[:end-position]
            position += int(note_samples * 0.9)  # Slight overlap
        
        # Add orchestral hit at the end
        hit_position = int(3.5 * self.sample_rate)
        hit = self.create_kick_drum() * 2
        music[hit_position:hit_position+len(hit)] += hit
        
        music = self.add_reverb(music, 0.3, 0.9)
        music = music / np.max(np.abs(music)) * 0.7
        
        return music
    
    def create_gameover_music(self):
        """Create melancholic game over music"""
        print("Generating game over music...")
        duration = 10
        
        # Minor key variation of theme
        sad_notes = [
            (440, 1.0), (415.30, 0.5), (392, 0.5),  # A4-G#4-G4
            (349.23, 1.0), (329.63, 0.5), (293.66, 1.5),  # F4-E4-D4
            (329.63, 0.5), (349.23, 0.5), (392, 0.5), (440, 2.0),  # Resolution
        ]
        
        music = np.zeros(int(duration * self.sample_rate))
        position = 0
        
        for freq, dur in sad_notes:
            envelope = self.create_envelope(dur, attack=0.2, release=0.3)
            tone = self.synthesize_tone(freq, dur, 'sine', envelope) * 0.4
            
            # Add lower octave
            bass = self.synthesize_tone(freq * 0.5, dur, 'sine', envelope) * 0.2
            
            note = tone + bass
            end = min(position + len(note), len(music))
            music[position:end] += note[:end-position]
            position += int(dur * self.sample_rate)
        
        music = self.add_reverb(music, 0.4, 0.9)
        music = self.lowpass_filter(music, 3000)  # Muffled sound
        music = music / np.max(np.abs(music)) * 0.5
        
        return music
    
    def save_audio(self, audio_data, filename, convert_to_mp3=True):
        """Save audio data to file and optionally convert to MP3"""
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
        
        if convert_to_mp3:
            # Convert to MP3 using ffmpeg
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
                print(f"✓ Saved: {filename}.wav")
                return wav_path
        else:
            print(f"✓ Saved: {filename}.wav")
            return wav_path
    
    def generate_all(self):
        """Generate all audio assets"""
        print("\n=== Generating Professional Tetris Audio ===\n")
        
        # Generate main theme variations
        theme = self.create_korobeiniki_theme()
        self.save_audio(theme, 'music_theme_a')
        
        # Create variations by adjusting tempo and effects
        theme_b = signal.resample(theme, int(len(theme) * 1.1))  # Slightly faster
        self.save_audio(theme_b, 'music_theme_b')
        
        theme_c = signal.resample(theme, int(len(theme) * 1.2))  # Even faster
        theme_c = self.lowpass_filter(theme_c, 6000)  # More intense
        self.save_audio(theme_c, 'music_theme_c')
        
        # Also save as generic theme
        self.save_audio(theme, 'music_theme')
        
        # Generate menu and special music
        menu_music = self.create_menu_music()
        self.save_audio(menu_music, 'music_menu_music')
        
        victory_music = self.create_victory_music()
        self.save_audio(victory_music, 'music_victory')
        
        gameover_music = self.create_gameover_music()
        self.save_audio(gameover_music, 'music_game_over_music')
        
        # Generate style variations
        print("\nGenerating style variations...")
        
        # Chiptune version (8-bit style)
        chiptune = signal.resample(theme, int(len(theme) / 2))  # Lower sample rate
        chiptune = np.round(chiptune * 8) / 8  # Quantize for 8-bit feel
        chiptune = signal.resample(chiptune, len(theme))
        self.save_audio(chiptune, 'music_chiptune')
        
        # Synthwave version (add more reverb and filters)
        synthwave = self.add_reverb(theme, 0.3, 0.8)
        synthwave = self.lowpass_filter(synthwave, 5000)
        self.save_audio(synthwave, 'music_synthwave')
        
        # Orchestral version (layer harmonics)
        orchestral = theme.copy()
        # Create octave up by resampling to half length, then back to original
        octave_up = signal.resample(theme, int(len(theme) / 2))
        octave_up = signal.resample(octave_up, len(theme))
        orchestral += octave_up * 0.3
        # Create octave down by resampling to double length, then back
        octave_down = signal.resample(theme, int(len(theme) * 2))
        octave_down = signal.resample(octave_down, len(theme))
        orchestral += octave_down * 0.4
        orchestral = self.add_reverb(orchestral, 0.4, 0.9)
        self.save_audio(orchestral / np.max(np.abs(orchestral)) * 0.7, 'music_orchestral')
        
        # Jazz version (swing rhythm)
        jazz = theme.copy()
        # Add swing by delaying every other beat slightly
        # Simplified jazz effect
        jazz = self.lowpass_filter(jazz, 4000)
        self.save_audio(jazz, 'music_jazz')
        
        # Metal version (distortion)
        metal = np.tanh(theme * 3) * 0.7  # Soft clipping distortion
        metal = self.lowpass_filter(metal, 10000)
        self.save_audio(metal, 'music_metal')
        
        # Generate all sound effects
        print("\nGenerating sound effects...")
        effects = self.create_sound_effects()
        for name, audio in effects.items():
            self.save_audio(audio, name)
        
        print("\n=== Audio Generation Complete! ===")
        print(f"Generated {len(effects) + 11} audio files")
        print(f"Output directory: {self.output_dir}")

if __name__ == "__main__":
    generator = TetrisAudioGenerator()
    generator.generate_all()