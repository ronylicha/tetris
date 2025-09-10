# Build Instructions for Modern Tetris Android App

## Prerequisites
1. Install Android SDK (minimum API 24)
2. Install Java JDK 17 or higher
3. Install Android Studio (recommended) or use command line tools

## Building the Debug APK

### Option 1: Using Android Studio
1. Open Android Studio
2. Select "Open an existing Android Studio project"
3. Navigate to `/var/www/html/tetris/android-app/`
4. Wait for Gradle sync to complete
5. Click "Build" > "Build Bundle(s) / APK(s)" > "Build APK(s)"
6. The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### Option 2: Using Command Line
```bash
cd /var/www/html/tetris/android-app/

# Download Gradle wrapper (first time only)
./gradlew wrapper

# Build debug APK
./gradlew assembleDebug

# The APK will be located at:
# app/build/outputs/apk/debug/app-debug.apk
```

## Installing the APK on Device

### Via ADB (Android Debug Bridge)
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Manual Installation
1. Copy the APK file to your Android device
2. Enable "Install from Unknown Sources" in Settings
3. Open the APK file on your device to install

## APK Location
After successful build, the debug APK will be available at:
```
/var/www/html/tetris/android-app/app/build/outputs/apk/debug/app-debug.apk
```

## Troubleshooting

### Missing Fonts
The Orbitron font files need to be downloaded from Google Fonts:
1. Download from: https://fonts.google.com/specimen/Orbitron
2. Place the .ttf files in: `app/src/main/res/font/`
   - orbitron_regular.ttf (weight 400)
   - orbitron_medium.ttf (weight 500)
   - orbitron_bold.ttf (weight 700)
   - orbitron_black.ttf (weight 900)

### Google Play Services
To enable Google Play Games integration:
1. Create a project in Google Play Console
2. Get your app ID
3. Update `app/src/main/res/values/strings.xml` with your app ID

### Build Errors
If you encounter build errors:
1. Clean the project: `./gradlew clean`
2. Rebuild: `./gradlew assembleDebug`
3. Check that all dependencies are available

## Features Included
- ✅ 9 Game Modes (Battle 2P disabled on mobile)
- ✅ 150 Puzzles with hint system
- ✅ AI with 5 difficulty levels
- ✅ Progression system (100 levels, 10 ranks)
- ✅ 30+ Achievements
- ✅ Full customization (themes, music, effects)
- ✅ Touch controls optimized for mobile
- ✅ Offline play support
- ✅ Material Design 3 UI

## Release Build
To create a release APK for distribution:
1. Create a keystore file for signing
2. Configure signing in `app/build.gradle.kts`
3. Run: `./gradlew assembleRelease`