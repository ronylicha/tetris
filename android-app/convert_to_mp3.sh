#!/bin/bash

# Convert WAV files to MP3 for Android compatibility
RAW_DIR="/var/www/html/tetris/android-app/app/src/main/res/raw"

echo "Converting WAV files to MP3..."
echo "==============================="

# Back up original MP3 files
echo "Creating backup of original files..."
mkdir -p "$RAW_DIR/backup_original"
cp "$RAW_DIR"/*.mp3 "$RAW_DIR/backup_original/" 2>/dev/null

# Convert each WAV to MP3
for wav_file in "$RAW_DIR"/*.wav; do
    if [ -f "$wav_file" ]; then
        base_name=$(basename "$wav_file" .wav)
        mp3_file="$RAW_DIR/${base_name}.mp3"
        
        echo "Converting: ${base_name}.wav -> ${base_name}.mp3"
        
        # Convert WAV to MP3 with good quality settings for game audio
        # -ab 128k: 128 kbps bitrate (good balance of quality and size)
        # -ar 44100: 44.1kHz sample rate
        ffmpeg -i "$wav_file" -codec:a libmp3lame -ab 128k -ar 44100 "$mp3_file" -y 2>/dev/null
        
        if [ $? -eq 0 ]; then
            echo "  ✓ Successfully converted ${base_name}.mp3"
            # Remove the WAV file after successful conversion
            rm "$wav_file"
        else
            echo "  ✗ Failed to convert ${base_name}.wav"
        fi
    fi
done

echo ""
echo "==============================="
echo "Conversion complete!"
echo ""
echo "File sizes comparison:"
ls -lh "$RAW_DIR"/*.mp3 | grep sfx_ | awk '{print $9, $5}'
echo ""
echo "Original files backed up to: $RAW_DIR/backup_original/"
echo ""
echo "Sound descriptions:"
echo "- sfx_move.mp3: Quick, subtle click for horizontal movement"
echo "- sfx_rotate.mp3: Mechanical rotation with pitch sweep"
echo "- sfx_drop.mp3: Gentle thud for soft drop"
echo "- sfx_hard_drop.mp3: Impactful slam for instant drop"
echo "- sfx_line_clear.mp3: Satisfying swoosh for 1-3 lines"
echo "- sfx_tetris.mp3: Epic arpeggio for 4-line Tetris"
echo "- sfx_t_spin.mp3: Unique spinning modulation effect"
echo "- sfx_combo.mp3: Ascending chime for combos"
echo "- sfx_hold.mp3: Clean interface beep for holding"
echo "- sfx_pause.mp3: Two-tone toggle for pause"
echo "- sfx_level_up.mp3: Triumphant fanfare for progression"
echo "- sfx_game_over.mp3: Melancholic descending progression"