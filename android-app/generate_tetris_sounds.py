#!/usr/bin/env python3
"""
Modern Tetris Sound Effects Generator
Creates high-quality, synthesized sound effects for Tetris game
Using advanced audio synthesis techniques for satisfying game feedback
"""

import numpy as np
import soundfile as sf
from scipy import signal
from scipy.signal import butter, lfilter, resample
import os

# Audio parameters
SAMPLE_RATE = 44100
BIT_DEPTH = 16

# Output directory
OUTPUT_DIR = "/var/www/html/tetris/android-app/app/src/main/res/raw/"

def apply_envelope(audio, attack=0.01, decay=0.1, sustain=0.7, release=0.2):
    """Apply ADSR envelope to audio signal"""
    length = len(audio)
    envelope = np.ones(length)
    
    attack_samples = int(attack * SAMPLE_RATE)
    decay_samples = int(decay * SAMPLE_RATE)
    release_samples = int(release * SAMPLE_RATE)
    sustain_samples = length - attack_samples - decay_samples - release_samples
    
    if sustain_samples < 0:
        # Adjust for short sounds
        total = attack_samples + decay_samples + release_samples
        attack_samples = int(attack_samples * length / total)
        decay_samples = int(decay_samples * length / total)
        release_samples = length - attack_samples - decay_samples
        sustain_samples = 0
    
    idx = 0
    # Attack
    if attack_samples > 0:
        envelope[idx:idx+attack_samples] = np.linspace(0, 1, attack_samples)
        idx += attack_samples
    
    # Decay
    if decay_samples > 0:
        envelope[idx:idx+decay_samples] = np.linspace(1, sustain, decay_samples)
        idx += decay_samples
    
    # Sustain
    if sustain_samples > 0:
        envelope[idx:idx+sustain_samples] = sustain
        idx += sustain_samples
    
    # Release
    if release_samples > 0 and idx < length:
        remaining = length - idx
        envelope[idx:] = np.linspace(sustain, 0, remaining)
    
    return audio * envelope

def add_reverb(audio, reverb_amount=0.1, delay_ms=20):
    """Add subtle reverb effect"""
    delay_samples = int(delay_ms * SAMPLE_RATE / 1000)
    reverb = np.zeros(len(audio) + delay_samples * 3)
    reverb[:len(audio)] = audio
    reverb[delay_samples:delay_samples+len(audio)] += audio * reverb_amount * 0.5
    if delay_samples*2 + len(audio) <= len(reverb):
        reverb[delay_samples*2:delay_samples*2+len(audio)] += audio * reverb_amount * 0.25
    return reverb[:len(audio)]

def lowpass_filter(audio, cutoff_freq):
    """Apply lowpass filter"""
    nyquist = SAMPLE_RATE / 2
    normal_cutoff = cutoff_freq / nyquist
    b, a = butter(4, normal_cutoff, btype='low', analog=False)
    return lfilter(b, a, audio)

def highpass_filter(audio, cutoff_freq):
    """Apply highpass filter"""
    nyquist = SAMPLE_RATE / 2
    normal_cutoff = cutoff_freq / nyquist
    b, a = butter(2, normal_cutoff, btype='high', analog=False)
    return lfilter(b, a, audio)

def normalize_audio(audio, target_db=-6):
    """Normalize audio to target dB level"""
    rms = np.sqrt(np.mean(audio**2))
    if rms > 0:
        target_rms = 10**(target_db/20)
        audio = audio * (target_rms / rms)
    return np.clip(audio, -0.99, 0.99)

def save_sound(audio, filename, normalize_db=-6):
    """Save audio to WAV file"""
    audio = normalize_audio(audio, normalize_db)
    filepath = os.path.join(OUTPUT_DIR, filename.replace('.mp3', '.wav'))
    sf.write(filepath, audio, SAMPLE_RATE, subtype='PCM_16')
    print(f"Created: {filename.replace('.mp3', '.wav')}")

# ============== MOVEMENT SOUNDS ==============

def create_move_sound():
    """Quick, subtle click for horizontal movement"""
    duration = 0.06  # 60ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Combine multiple frequency components for richness
    click = np.sin(2 * np.pi * 800 * t)  # Main tone
    click += 0.3 * np.sin(2 * np.pi * 1600 * t)  # Harmonic
    click += 0.2 * np.sin(2 * np.pi * 400 * t)  # Sub harmonic
    
    # Add a tiny bit of noise for texture
    noise = np.random.normal(0, 0.02, len(t))
    click += noise
    
    # Sharp envelope for click feel
    click = apply_envelope(click, attack=0.001, decay=0.01, sustain=0.3, release=0.049)
    
    # Slight filtering for smoothness
    click = lowpass_filter(click, 4000)
    
    return click

def create_rotate_sound():
    """Mechanical rotation click with pitch sweep"""
    duration = 0.08  # 80ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Pitch sweep from high to low for rotation feel
    freq_sweep = np.linspace(1200, 600, len(t))
    rotate = np.sin(2 * np.pi * freq_sweep * t)
    
    # Add mechanical click component
    click = np.sin(2 * np.pi * 2000 * t) * np.exp(-30 * t)
    rotate += 0.5 * click
    
    # Add some harmonics
    rotate += 0.2 * np.sin(2 * np.pi * freq_sweep * 2 * t)
    
    # Quick envelope
    rotate = apply_envelope(rotate, attack=0.001, decay=0.02, sustain=0.4, release=0.059)
    
    # Filter for clarity
    rotate = lowpass_filter(rotate, 5000)
    rotate = highpass_filter(rotate, 200)
    
    return rotate

# ============== DROP SOUNDS ==============

def create_drop_sound():
    """Soft drop - gentle thud"""
    duration = 0.12  # 120ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Low frequency thud
    drop = np.sin(2 * np.pi * 150 * t)
    drop += 0.5 * np.sin(2 * np.pi * 100 * t)
    
    # Add impact transient
    impact = np.sin(2 * np.pi * 500 * t) * np.exp(-20 * t)
    drop += 0.3 * impact
    
    # Subtle pitch bend downward
    pitch_bend = np.linspace(1, 0.9, len(t))
    drop *= pitch_bend
    
    # Smooth envelope
    drop = apply_envelope(drop, attack=0.002, decay=0.03, sustain=0.5, release=0.088)
    
    # Filter for warmth
    drop = lowpass_filter(drop, 2000)
    
    return drop

def create_hard_drop_sound():
    """Hard drop - impactful slam"""
    duration = 0.15  # 150ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Deep bass impact
    slam = np.sin(2 * np.pi * 80 * t)
    slam += 0.7 * np.sin(2 * np.pi * 60 * t)
    
    # Mid frequency punch
    punch = np.sin(2 * np.pi * 300 * t) * np.exp(-15 * t)
    slam += 0.5 * punch
    
    # High frequency crack
    crack = np.sin(2 * np.pi * 2000 * t) * np.exp(-50 * t)
    slam += 0.2 * crack
    
    # Add white noise burst for impact
    noise_burst = np.random.normal(0, 0.3, int(0.01 * SAMPLE_RATE))
    noise_envelope = np.exp(-100 * np.linspace(0, 0.01, len(noise_burst)))
    slam[:len(noise_burst)] += noise_burst * noise_envelope
    
    # Punchy envelope
    slam = apply_envelope(slam, attack=0.001, decay=0.02, sustain=0.6, release=0.129)
    
    # Filter and add subtle reverb
    slam = lowpass_filter(slam, 3000)
    slam = add_reverb(slam, 0.15, 10)
    
    return slam

# ============== LINE CLEAR SOUNDS ==============

def create_line_clear_sound():
    """Satisfying swoosh for 1-3 lines"""
    duration = 0.25  # 250ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Ascending sweep
    freq_sweep = np.linspace(200, 1500, len(t))
    swoosh = np.sin(2 * np.pi * freq_sweep * t)
    
    # Add harmonics for richness
    swoosh += 0.5 * np.sin(2 * np.pi * freq_sweep * 2 * t)
    swoosh += 0.3 * np.sin(2 * np.pi * freq_sweep * 3 * t)
    
    # White noise sweep
    noise = np.random.normal(0, 0.1, len(t))
    # Apply time-varying filter manually for sweep effect
    noise_filtered = np.zeros_like(noise)
    for i in range(len(noise)):
        if i > 0:
            alpha = 1.0 / (1.0 + SAMPLE_RATE / (2 * np.pi * freq_sweep[i]))
            noise_filtered[i] = alpha * noise[i] + (1 - alpha) * noise_filtered[i-1]
        else:
            noise_filtered[i] = noise[i]
    swoosh += noise_filtered * 0.3
    
    # Success bell tone
    bell = np.sin(2 * np.pi * 1000 * t) * np.exp(-3 * t)
    bell += 0.5 * np.sin(2 * np.pi * 2000 * t) * np.exp(-4 * t)
    swoosh += 0.4 * bell
    
    # Dynamic envelope
    swoosh = apply_envelope(swoosh, attack=0.01, decay=0.05, sustain=0.7, release=0.19)
    
    # Filter and reverb for space
    swoosh = lowpass_filter(swoosh, 8000)
    swoosh = highpass_filter(swoosh, 150)
    swoosh = add_reverb(swoosh, 0.2, 30)
    
    return swoosh

def create_tetris_sound():
    """Epic, rewarding sound for 4-line clear"""
    duration = 0.45  # 450ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Major chord arpeggio (C-E-G-C)
    notes = [261.63, 329.63, 392.00, 523.25]  # C4, E4, G4, C5
    arpeggio = np.zeros(len(t))
    
    for i, freq in enumerate(notes):
        start = int(i * len(t) / 4)
        end = min(start + int(len(t) / 2), len(t))
        note_t = t[start:end] - t[start]
        note = np.sin(2 * np.pi * freq * note_t)
        note *= np.exp(-2 * note_t)  # Decay
        arpeggio[start:end] += note
    
    # Add powerful bass
    bass = np.sin(2 * np.pi * 130.81 * t)  # C3
    bass += 0.5 * np.sin(2 * np.pi * 65.41 * t)  # C2
    tetris = arpeggio + 0.6 * bass
    
    # Shimmer effect
    shimmer = np.sin(2 * np.pi * 3000 * t) * np.sin(2 * np.pi * 7 * t)
    tetris += 0.1 * shimmer
    
    # Impact at beginning
    impact = np.sin(2 * np.pi * 100 * t) * np.exp(-30 * t)
    tetris += 0.5 * impact
    
    # Grand envelope
    tetris = apply_envelope(tetris, attack=0.005, decay=0.1, sustain=0.8, release=0.345)
    
    # Filter and reverb for epicness
    tetris = lowpass_filter(tetris, 10000)
    tetris = highpass_filter(tetris, 80)
    tetris = add_reverb(tetris, 0.3, 50)
    
    return tetris

# ============== SPECIAL MOVE SOUNDS ==============

def create_t_spin_sound():
    """Unique spinning effect for T-spin"""
    duration = 0.3  # 300ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Spinning modulation
    carrier = 800
    modulator = np.sin(2 * np.pi * 15 * t) * 200
    spin = np.sin(2 * np.pi * (carrier + modulator) * t)
    
    # Add spiral harmonics
    for i in range(2, 5):
        harm_mod = np.sin(2 * np.pi * 15 * i * t) * 100
        spin += (0.5 / i) * np.sin(2 * np.pi * (carrier * i + harm_mod) * t)
    
    # Success chime
    chime = np.sin(2 * np.pi * 1200 * t) * np.exp(-5 * t)
    chime += 0.5 * np.sin(2 * np.pi * 1600 * t) * np.exp(-6 * t)
    spin += 0.4 * chime
    
    # Whoosh effect
    whoosh = np.random.normal(0, 0.1, len(t))
    # Apply time-varying filter for descending whoosh
    freq_sweep_whoosh = np.linspace(5000, 500, len(t))
    whoosh_filtered = np.zeros_like(whoosh)
    for i in range(len(whoosh)):
        if i > 0:
            alpha = 1.0 / (1.0 + SAMPLE_RATE / (2 * np.pi * freq_sweep_whoosh[i]))
            whoosh_filtered[i] = alpha * whoosh[i] + (1 - alpha) * whoosh_filtered[i-1]
        else:
            whoosh_filtered[i] = whoosh[i]
    spin += whoosh_filtered * 0.2
    
    # Dynamic envelope
    spin = apply_envelope(spin, attack=0.01, decay=0.05, sustain=0.6, release=0.24)
    
    # Filter for clarity
    spin = lowpass_filter(spin, 6000)
    spin = highpass_filter(spin, 200)
    
    return spin

def create_combo_sound():
    """Ascending chime for combos"""
    duration = 0.35  # 350ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Three ascending notes
    notes = [440, 554.37, 659.25]  # A4, C#5, E5 (A major)
    combo = np.zeros(len(t))
    
    for i, freq in enumerate(notes):
        start = int(i * len(t) / 4)
        end = len(t)
        note_t = t[start:end] - t[start]
        note = np.sin(2 * np.pi * freq * note_t)
        note += 0.3 * np.sin(2 * np.pi * freq * 2 * note_t)  # Octave
        note *= np.exp(-3 * note_t)  # Decay
        combo[start:end] += note * (0.7 + i * 0.1)  # Increasing volume
    
    # Add sparkle
    sparkle = np.sin(2 * np.pi * 4000 * t) * np.sin(2 * np.pi * 12 * t)
    combo += 0.1 * sparkle * np.exp(-5 * t)
    
    # Smooth envelope
    combo = apply_envelope(combo, attack=0.005, decay=0.05, sustain=0.7, release=0.295)
    
    # Filter and reverb
    combo = lowpass_filter(combo, 8000)
    combo = add_reverb(combo, 0.25, 40)
    
    return combo

# ============== UI SOUNDS ==============

def create_hold_sound():
    """Clean interface sound for holding piece"""
    duration = 0.1  # 100ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Two-tone beep
    beep1 = np.sin(2 * np.pi * 600 * t)
    beep2 = np.sin(2 * np.pi * 900 * t)
    
    # Crossfade between tones
    crossfade = np.linspace(0, 1, len(t))
    hold = beep1 * (1 - crossfade) + beep2 * crossfade
    
    # Add click transient
    click = np.sin(2 * np.pi * 2000 * t) * np.exp(-50 * t)
    hold += 0.2 * click
    
    # Quick envelope
    hold = apply_envelope(hold, attack=0.002, decay=0.02, sustain=0.6, release=0.078)
    
    # Clean filtering
    hold = lowpass_filter(hold, 4000)
    
    return hold

def create_pause_sound():
    """Distinct pause/unpause toggle"""
    duration = 0.15  # 150ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Descending two-tone for pause feel
    freq1 = 800
    freq2 = 400
    
    # First half - high tone
    half = len(t) // 2
    pause = np.zeros(len(t))
    pause[:half] = np.sin(2 * np.pi * freq1 * t[:half])
    
    # Second half - low tone
    pause[half:] = np.sin(2 * np.pi * freq2 * t[half:])
    
    # Add subtle modulation
    pause *= (1 + 0.1 * np.sin(2 * np.pi * 25 * t))
    
    # Smooth envelope
    pause = apply_envelope(pause, attack=0.005, decay=0.03, sustain=0.7, release=0.115)
    
    # Filter for smoothness
    pause = lowpass_filter(pause, 3000)
    
    return pause

# ============== PROGRESSION SOUNDS ==============

def create_level_up_sound():
    """Triumphant level progression"""
    duration = 0.4  # 400ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Victory fanfare - ascending major scale
    notes = [523.25, 587.33, 659.25, 783.99]  # C5, D5, E5, G5
    fanfare = np.zeros(len(t))
    
    for i, freq in enumerate(notes):
        start = int(i * len(t) / 5)
        end = min(start + int(len(t) / 3), len(t))
        note_t = t[start:end] - t[start]
        note = np.sin(2 * np.pi * freq * note_t)
        note += 0.3 * np.sin(2 * np.pi * freq * 2 * note_t)
        note += 0.2 * np.sin(2 * np.pi * freq / 2 * note_t)
        note *= np.exp(-2 * note_t)
        fanfare[start:end] += note
    
    # Power chord at the end
    power_start = int(3 * len(t) / 5)
    power_t = t[power_start:] - t[power_start]
    power = np.sin(2 * np.pi * 261.63 * power_t)  # C4
    power += np.sin(2 * np.pi * 392.00 * power_t)  # G4
    power += np.sin(2 * np.pi * 523.25 * power_t)  # C5
    power *= np.exp(-3 * power_t)
    fanfare[power_start:] += 0.8 * power
    
    # Add shimmer
    shimmer = np.sin(2 * np.pi * 5000 * t) * np.sin(2 * np.pi * 8 * t)
    fanfare += 0.05 * shimmer
    
    # Grand envelope
    fanfare = apply_envelope(fanfare, attack=0.01, decay=0.05, sustain=0.8, release=0.34)
    
    # Filter and reverb
    fanfare = lowpass_filter(fanfare, 10000)
    fanfare = highpass_filter(fanfare, 100)
    fanfare = add_reverb(fanfare, 0.3, 40)
    
    return fanfare

def create_game_over_sound():
    """Descending, melancholic game over"""
    duration = 0.6  # 600ms
    t = np.linspace(0, duration, int(SAMPLE_RATE * duration))
    
    # Descending minor chord progression
    # Dm - Bb - F - C (ii - bVII - IV - I in C minor feel)
    chord_times = [0, 0.15, 0.3, 0.45]
    chords = [
        [293.66, 349.23, 440.00],  # D4, F4, A4 (Dm)
        [233.08, 293.66, 349.23],  # Bb3, D4, F4 (Bb)
        [174.61, 220.00, 261.63],  # F3, A3, C4 (F)
        [130.81, 164.81, 196.00],  # C3, E3, G3 (C)
    ]
    
    game_over = np.zeros(len(t))
    
    for i, (chord_time, chord) in enumerate(zip(chord_times, chords)):
        start = int(chord_time * SAMPLE_RATE)
        end = min(start + int(0.3 * SAMPLE_RATE), len(t))
        chord_t = t[start:end] - t[start]
        
        for freq in chord:
            note = np.sin(2 * np.pi * freq * chord_t)
            note *= np.exp(-2 * chord_t)  # Decay
            game_over[start:end] += note / len(chord)
    
    # Add low rumble
    rumble = np.sin(2 * np.pi * 60 * t) * 0.3
    game_over += rumble * np.exp(-1 * t)
    
    # Melancholic envelope
    game_over = apply_envelope(game_over, attack=0.02, decay=0.1, sustain=0.5, release=0.48)
    
    # Filter for somber tone
    game_over = lowpass_filter(game_over, 4000)
    game_over = highpass_filter(game_over, 80)
    game_over = add_reverb(game_over, 0.4, 60)
    
    return game_over

# ============== MAIN GENERATION ==============

def generate_all_sounds():
    """Generate all Tetris sound effects"""
    print("Generating Modern Tetris Sound Effects...")
    print("=" * 50)
    
    # Create output directory if it doesn't exist
    os.makedirs(OUTPUT_DIR, exist_ok=True)
    
    # Movement sounds
    print("\nGenerating movement sounds...")
    save_sound(create_move_sound(), "sfx_move.wav", -8)
    save_sound(create_rotate_sound(), "sfx_rotate.wav", -8)
    
    # Drop sounds
    print("\nGenerating drop sounds...")
    save_sound(create_drop_sound(), "sfx_drop.wav", -6)
    save_sound(create_hard_drop_sound(), "sfx_hard_drop.wav", -4)
    
    # Line clear sounds
    print("\nGenerating line clear sounds...")
    save_sound(create_line_clear_sound(), "sfx_line_clear.wav", -5)
    save_sound(create_tetris_sound(), "sfx_tetris.wav", -3)
    
    # Special move sounds
    print("\nGenerating special move sounds...")
    save_sound(create_t_spin_sound(), "sfx_t_spin.wav", -5)
    save_sound(create_combo_sound(), "sfx_combo.wav", -5)
    
    # UI sounds
    print("\nGenerating UI sounds...")
    save_sound(create_hold_sound(), "sfx_hold.wav", -8)
    save_sound(create_pause_sound(), "sfx_pause.wav", -7)
    
    # Progression sounds
    print("\nGenerating progression sounds...")
    save_sound(create_level_up_sound(), "sfx_level_up.wav", -4)
    save_sound(create_game_over_sound(), "sfx_game_over.wav", -4)
    
    # Additional menu sounds (using variations of existing functions)
    print("\nGenerating additional menu sounds...")
    
    # Menu select (variation of hold sound but higher pitched)
    menu_select = create_hold_sound()
    menu_select = resample(menu_select, int(len(menu_select) * 1.2))  # Pitch up
    save_sound(menu_select[:int(0.08 * SAMPLE_RATE)], "sfx_menu_select.wav", -8)
    
    # Menu back (reverse of menu select)
    menu_back = menu_select[::-1]
    save_sound(menu_back[:int(0.08 * SAMPLE_RATE)], "sfx_menu_back.wav", -8)
    
    # Menu navigation (lighter move sound)
    menu_nav = create_move_sound() * 0.7
    save_sound(menu_nav, "sfx_menu.wav", -10)
    
    # Countdown (modified pause sound)
    countdown = create_pause_sound()
    countdown = resample(countdown, int(len(countdown) * 0.8))  # Pitch down slightly
    save_sound(countdown, "sfx_countdown.wav", -6)
    
    # Power up (enhanced combo sound)
    power_up = create_combo_sound()
    power_up = resample(power_up, int(len(power_up) * 1.1))  # Pitch up slightly
    save_sound(power_up[:int(0.3 * SAMPLE_RATE)], "sfx_power_up.wav", -5)
    
    print("\n" + "=" * 50)
    print("All sound effects generated successfully!")
    print(f"Output directory: {OUTPUT_DIR}")
    print("\nSound characteristics:")
    print("- Movement sounds: Quick, subtle (50-80ms)")
    print("- Drop sounds: Impactful thuds (120-150ms)")
    print("- Line clears: Satisfying swooshes (250-450ms)")
    print("- Special moves: Unique, distinguishable")
    print("- UI sounds: Clean, interface-like")
    print("- All sounds: 44.1kHz, 16-bit WAV format")
    print("\nNext steps:")
    print("1. Convert WAV files to MP3 using ffmpeg or similar")
    print("2. Test sounds in the Android app")
    print("3. Adjust volume levels in-game as needed")

if __name__ == "__main__":
    generate_all_sounds()