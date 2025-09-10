#!/bin/bash

# Build script for Modern Tetris Android App
# This script can be used if gradlew is not working

echo "========================================="
echo "Modern Tetris Android App Build Script"
echo "========================================="

# Check if ANDROID_HOME is set
if [ -z "$ANDROID_HOME" ]; then
    echo "Error: ANDROID_HOME is not set"
    echo "Please set ANDROID_HOME to your Android SDK location"
    echo "Example: export ANDROID_HOME=/path/to/android-sdk"
    exit 1
fi

# Check if gradle is installed
if ! command -v gradle &> /dev/null; then
    echo "Gradle is not installed. Attempting to use wrapper..."
    
    # Try to download gradle if not present
    if [ ! -f "gradle/wrapper/gradle-wrapper.jar" ]; then
        echo "Downloading Gradle wrapper..."
        mkdir -p gradle/wrapper
        curl -o gradle/wrapper/gradle-wrapper.jar https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar
    fi
    
    # Use gradlew
    GRADLE_CMD="./gradlew"
else
    echo "Using system Gradle installation"
    GRADLE_CMD="gradle"
fi

# Clean previous builds
echo ""
echo "Cleaning previous builds..."
$GRADLE_CMD clean

# Build debug APK
echo ""
echo "Building debug APK..."
$GRADLE_CMD assembleDebug

# Check if build was successful
if [ $? -eq 0 ]; then
    echo ""
    echo "========================================="
    echo "Build Successful!"
    echo "========================================="
    echo ""
    echo "APK Location:"
    echo "app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    echo "To install on device:"
    echo "adb install app/build/outputs/apk/debug/app-debug.apk"
    echo ""
    
    # Show APK info if exists
    if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
        echo "APK Details:"
        ls -lh app/build/outputs/apk/debug/app-debug.apk
    fi
else
    echo ""
    echo "========================================="
    echo "Build Failed!"
    echo "========================================="
    echo ""
    echo "Please check the error messages above."
    echo ""
    echo "Common issues:"
    echo "1. Missing Android SDK - Install Android SDK and set ANDROID_HOME"
    echo "2. Missing dependencies - Run: gradle build --refresh-dependencies"
    echo "3. Wrong Java version - Use Java 17 or higher"
    exit 1
fi