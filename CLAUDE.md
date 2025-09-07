# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Modern Tetris is a fully-featured, responsive web-based Tetris game built with vanilla JavaScript ES6 modules. It features neon graphics, dynamic audio, advanced touch controls, and Progressive Web App (PWA) capabilities with offline support.

**Latest Version: 3.1.1** - Fixed Battle 2P mode issues: grid rendering with hidden rows, proper null/0 value handling, dedicated UI without AI references, and improved piece spawning.

## Architecture

### Module Structure
- **game.js**: Main game engine and state management (TetrisGame class)
- **grid.js**: Game grid logic and line clearing (Grid class, GRID_WIDTH=10, GRID_HEIGHT=20)
- **pieces.js**: Tetromino definitions, rotation system (SRS), and T-spin detection
- **input.js**: Cross-platform input handling (keyboard + touch gestures)
- **audio.js**: Web Audio API integration with dynamic tempo system (120-168 BPM)
- **ui.js**: UI updates, animations, and visual effects (includes showPuzzleSelection)
- **modals.js**: Settings, help, game over dialogs, and mode selection
- **leaderboard.js**: Score management with offline support
- **offline-storage.js**: IndexedDB integration for PWA functionality
- **storage-adapter.js**: Unified storage that works in both file:// and http:// contexts
- **sw.js**: Service Worker for offline caching (v1.3.5 with dynamic path detection)
- **modeSelector.js**: Game mode management and selection system
- **build-standalone.js**: Node.js script to create single-file version
- **test-offline.html**: Diagnostic tool for testing offline compatibility

### Game Modes System
- **modes/gameMode.js**: Abstract base class for all game modes
- **modes/classicMode.js**: Traditional endless Tetris
- **modes/powerUpMode.js**: Classic gameplay with 8 unique power-ups (NEW)
- **modes/sprintMode.js**: 40-line time attack mode
- **modes/marathonMode.js**: 150-line survival with checkpoints
- **modes/zenMode.js**: Relaxing endless mode with statistics
- **modes/puzzleMode.js**: Challenge mode with 150 puzzles with enhanced star ratings and hint system
- **modes/battleMode.js**: VS AI battle mode with power-ups and rounds
- **modes/battle2PMode.js**: Local 2-player competitive mode with split-screen (NEW)

### Power-Up System
- **powerups/powerUpTypes.js**: Definitions for 8 power-ups (Slow Time, Line Bomb, Ghost Mode, Lightning, Precision, 2x Score, Shuffle, Magnet)
- **powerups/powerUpManager.js**: Power-up state management, activation, and UI updates

### Puzzle System
- **puzzles/puzzleData.js**: 150 puzzle configurations (all verified as solvable)
- **puzzles/puzzleValidator.js**: Automatic puzzle validation and feasibility checking
- **puzzles/puzzleObjectives.js**: Enhanced objectives (cascade, speed, no-rotation, chain, pattern)
- **puzzles/hintSystem.js**: 3-level AI-powered hint system with visual indicators
- **puzzles/puzzleAutoSolver.js**: AI pathfinding solver for puzzle verification (NEW)
- **puzzles/puzzleDifficultyAnalyzer.js**: Comprehensive difficulty analysis and skill requirements (NEW)
- **puzzles/puzzleVerificationSuite.js**: Automated testing for all 150 puzzles (NEW)
- **puzzleCreator.html**: Visual puzzle creation tool with 13 objective types
- **verifyPuzzles.html**: Puzzle verification dashboard with fix suggestions (NEW)

### AI System
- **ai/tetrisAI.js**: Advanced AI with T-spin detection, combo tracking, optimal pathfinding (5 difficulty levels)

### Progression & Rewards (NEW in v3.0.0)
- **progression/playerProgression.js**: 100-level XP system with unlockables (themes, music, effects)
- **achievements/achievementSystem.js**: 30+ achievements and 10+ trophies across multiple categories
- **challenges/dailyChallenge.js**: Daily procedurally-generated challenges with modifiers and streaks

### Backend Components
- **api/scores.php**: PHP API for score management
- **database/tetris_scores.db**: SQLite database for leaderboard
- **database/init.sql**: Database schema and initialization

## Development Commands

Since this is a vanilla JavaScript project:

```bash
# Start local development server (Python)
python3 -m http.server 3000

# Start local development server (Node.js)
npx serve .

# Build standalone version (single HTML file)
node build-standalone.js

# Initialize database (if needed)
sqlite3 database/tetris_scores.db < database/init.sql

# Test offline compatibility
# Open test-offline.html in browser

# Run linting (if ESLint configured)
npm run lint

# Run type checking (if TypeScript configured)
npm run typecheck
```

## New Features in v3.1.0

### Puzzle Verification & Quality Assurance (NEW)
- **Auto-Solver System**: AI pathfinding that finds optimal solutions
  - Depth-first search with pruning
  - Evaluates all possible placements
  - Supports all objective types
- **Difficulty Analyzer**: Comprehensive puzzle analysis
  - 5-level difficulty scale (Easy to Master)
  - Skill requirement identification
  - Time estimates for different player levels
  - Grid complexity scoring
- **Verification Suite**: Automated testing for all puzzles
  - Validates all 150 puzzles are solvable
  - Identifies structural issues
  - Suggests automatic fixes
  - Generates detailed reports
- **Verification Dashboard**: Visual testing interface
  - Real-time verification progress
  - Filter by status (solvable/impossible/warnings)
  - Export verification reports
  - Apply fixes with one click

### Mobile Compatibility
- **Battle 2P Mode**: Automatically hidden on mobile devices (width < 768px)
- **Responsive Mode Selection**: Desktop-only modes filtered on mobile

## New Features in v3.0.0

### Player Progression System
- **100 Levels**: Earn XP from gameplay, achievements, and challenges
- **Ranks**: Progress from Novice to Eternal (10 ranks total)
- **Unlockables**: 
  - 7 themes (cyberpunk, retro, nature, minimal, galaxy, matrix, rainbow)
  - 6 music tracks (chiptune, synthwave, orchestral, jazz, metal, lofi)
  - 5 piece styles (glass, pixel, hologram, crystal, animated)
  - 6 visual effects (particles, trails, explosions, lightning, shatter, quantum)

### Achievement System
- **30+ Achievements**: Across beginner, lines, score, combo, special, modes, daily, and secret categories
- **10+ Trophies**: Bronze, Silver, Gold, and Platinum tiers
- **XP Rewards**: Each achievement and trophy awards XP for progression

### Daily Challenge System
- **Procedural Generation**: Unique challenge every day with consistent seed
- **Modifiers**: 10 random modifiers (invisible pieces, mirror mode, earthquake, etc.)
- **Streak Rewards**: Milestone rewards at 3, 7, 14, 30, 60, and 100 days
- **Global Leaderboard**: Compete with players worldwide

### Enhanced Puzzle Mode
- **Star Rating System**: 
  - Efficiency-based (pieces used vs maximum)
  - Time bonuses for speed completion
  - Hint penalties (deduct stars when used)
- **Hint System (3 Levels)**:
  - Level 1: Column position highlighting
  - Level 2: Rotation suggestions
  - Level 3: Full solution ghost with movement arrows
  - AI-powered evaluation using heuristics
  - 5-second cooldown between hints
- **New Objectives**:
  - Cascade: Clear lines with gravity effects
  - Speed: Complete within time limits
  - No-Rotation: Master placement without rotating
  - Chain: Consecutive line clears
  - Pattern: Create specific shapes
  - Survival: Last for duration

### Local 2-Player Battle Mode
- **Split-Screen**: Side-by-side competitive play
- **Controls**: P1 uses arrows, P2 uses WASD
- **Garbage System**: Send lines to opponent based on clears
- **Best of 3**: First to 2 round wins takes the match
- **Attack Mechanics**: Combos, T-spins, and Tetrises send garbage

## Key Game Mechanics

### Touch Controls Implementation
- **Continuous soft drop**: Hold and drag down with velocity-based speed
- **Swipe detection**: Horizontal movement with gesture conflict prevention
- **Long press hold**: 800ms press duration for piece holding
- **Touch-optimized**: Minimum 44px touch targets throughout UI

### Scoring System
- Single: 100 × Level
- Double: 300 × Level
- Triple: 500 × Level
- Tetris: 800 × Level
- T-Spin bonuses and combo multipliers implemented

### Audio System
- Dynamic tempo increases 2% per level
- Web Audio API with real-time synthesis
- Master volume and separate music/SFX controls
- Auto-mute on focus loss

## Important Implementation Details

### Responsive Design
- Mobile-first approach with breakpoints at 480px, 768px, 1024px, 1440px
- 2-column layout on mobile (hold+stats | next pieces)
- 3-column layout on desktop and tablets
- Landscape mode optimization for mobile devices

### Canvas Rendering
- High-DPI/Retina display support with devicePixelRatio scaling
- Cell size calculated as canvas.width / GRID_WIDTH
- Ghost piece rendering for placement preview
- Efficient redraw only when game state changes

### Game State Management
- States: 'menu', 'playing', 'paused', 'gameover'
- Lock delay system (500ms) for piece placement
- Piece bag system (7-bag randomizer) for fair piece distribution
- Hold piece system with single-use-per-drop restriction

### PWA Features
- Service Worker with cache-first strategy (v1.3.0)
- IndexedDB for local score storage
- Background sync for score synchronization
- Manifest.json for app installation
- Audio file caching support

### Offline Support (v1.3.0)
- **Storage Adapter**: Unified storage system that automatically detects context
  - file:// protocol: Uses localStorage with compression
  - http:// protocol: Uses IndexedDB with fallback to localStorage
- **Three deployment modes**:
  1. **Server mode**: Full features with Service Worker, IndexedDB, sync
  2. **File mode**: Direct file:// access, localStorage only, no sync
  3. **Standalone mode**: Single HTML file (410KB) with everything embedded
- **Context detection**: `window.TetrisContext` object tracks execution environment
- **Visual indicators**: Shows Online/Offline/File Mode status
- **Data migration**: Automatic migration from legacy localStorage to new system
- **Puzzle progress**: All 150 puzzles playable offline with saved progress

## Testing Approach

No automated testing framework is currently implemented. Manual testing should focus on:
- Cross-browser compatibility (Chrome 60+, Firefox 55+, Safari 12+, Edge 79+)
- Touch controls on mobile devices
- Responsive layout at different screen sizes
- Offline functionality and PWA installation
- Audio synchronization with gameplay

## Common Tasks

### Adding New Game Modes
1. Create new class extending GameMode in scripts/modes/
2. Implement required methods: initialize(), update(), handleLineClears(), getObjective(), getModeUI()
3. Add mode to ModeSelector in modeSelector.js
4. Update Service Worker cache list
5. Add UI elements if needed
6. Test offline functionality

### Adding New Features
1. Follow ES6 module pattern - export classes/functions from modules
2. Import dependencies at top of file
3. Update relevant UI components if adding visual elements
4. Test on both desktop and mobile devices

### Modifying Game Mechanics
- Core mechanics in game.js (update(), moveDown(), rotate(), etc.)
- Piece definitions in pieces.js (PIECES constant)
- Grid operations in grid.js (placePiece(), clearLines())
- Always update scoring in calculateScore() method

### Updating Styles
- Main styles in styles/main.css (neon theme, layout)
- Animations in styles/animations.css (CSS animations)
- Responsive styles in styles/responsive.css (media queries)
- Use CSS custom properties for theming

### Working with Audio
- Audio files synthesized in real-time via Web Audio API
- Tempo control in audioManager.setGameSpeed()
- Sound effects triggered via audioManager.playSFX() (not playSound)
- Audio files cached by Service Worker when available

## Important Notes for Development

### File Protocol Compatibility
- Always use storage-adapter.js instead of direct localStorage/IndexedDB
- Test changes in both http:// and file:// contexts
- Run `node build-standalone.js` after major changes
- Check test-offline.html to verify offline functionality

### Puzzle Mode Development
- Puzzle definitions in puzzles/puzzleData.js
- Progress saved automatically via storage adapter
- Puzzle selection UI in ui.js showPuzzleSelection()
- All puzzles must be completable with maxPieces limit

### Performance Considerations
- localStorage limited to ~5MB in file:// mode
- Use compression for large data sets
- Avoid frequent DOM updates during gameplay
- Canvas operations optimized for 60fps

### AI Development Notes
- **Evaluation Metrics**: Height, holes, bumpiness, wells, deep holes, transitions
- **Advanced Strategies**: T-spin setup detection, combo tracking, Tetris well management
- **Difficulty Levels**: Each level has unique error rates, thinking delays, and evaluation weights
- **Pathfinding**: Higher difficulties use optimal move sequencing
- **Garbage Handling**: Smart placement and counter-attack strategies

### Puzzle Creation & Validation
- Use `/puzzleCreator.html` to create and validate puzzles
- Validator checks: grid validity, objective feasibility, piece requirements
- Auto-fix suggestions for common issues
- Difficulty analysis based on solution complexity
- Import/Export puzzle configurations as JSON

## Version 3.1.1 Changes (Latest)

### Battle 2P Mode Fixes
1. **Grid Rendering Issues**:
   - Fixed rendering of hidden rows (top 4 rows are now properly hidden)
   - Pieces now spawn in hidden area (y=2) instead of visible area
   - Adjusted render offsets for pieces and ghost pieces

2. **Data Type Compatibility**:
   - Fixed null vs 0 comparison issues between Grid class and Battle2P mode
   - Grid uses `null` for empty cells, Battle2P now handles both `null` and `0`
   - Proper initialization of grids using `new Grid()` without parameters

3. **UI Improvements**:
   - Removed "AI Normal" display from Battle 2P mode
   - Added dedicated Battle 2P UI showing P1 and P2 wins
   - Proper round tracking and display
   - Fixed premature game ending issues

4. **Gameplay Fixes**:
   - Fixed piece initialization using `getCurrentShape()` instead of direct shape access
   - Corrected rotation state management
   - Fixed game over detection logic
   - Improved piece placement and collision detection

## Version 2.0.0 Changes

### Major UI/UX Improvements
1. **New Home Screen**: 
   - Dedicated landing page with game mode selection grid
   - Large animated logo and title
   - Quick access to Settings, Leaderboard, and Help
   - Modern card-based layout for 6 game modes

2. **Improved Game Navigation**:
   - Clean game view without header clutter
   - Floating action buttons (Back to Menu, Help, Mute)
   - ESC key returns to home screen
   - Complete game reset when returning to menu

3. **Responsive Leaderboard**:
   - Mobile-optimized card layout for entries
   - Top 3 highlighted with gold/silver/bronze
   - Smooth scrolling with styled scrollbar
   - Mode-specific filtering with database support

4. **Enhanced Help Modal**:
   - Tabbed interface (Keyboard/Touch/Scoring)
   - Visual keyboard keys display
   - Touch gesture icons and explanations
   - Comprehensive scoring system documentation

5. **Database Updates**:
   - Added `game_mode` column for mode-specific leaderboards
   - Added `mode_data` column for storing mode-specific information
   - Fixed permissions issues with SQLite database
   - API now handles different game modes properly

### Technical Improvements
1. **Canvas Rendering**: Responsive sizing using em units
2. **Navigation Flow**: Home → Game Mode Selection → Game → Home
3. **State Management**: Proper cleanup when switching screens
4. **Error Handling**: Better API error messages and logging
5. **Mobile Optimization**: Touch-friendly UI elements throughout

### Recent Critical Fixes
1. **Game Reset Issue**: Fixed game continuing in background when returning to menu
2. **Score Saving 400 Error**: Fixed API path and database permissions
3. **Canvas Visibility**: Fixed canvas sizing and display issues
4. **Leaderboard Mobile**: Complete responsive redesign for all screen sizes
5. **Database Permissions**: Fixed write permissions for Apache/PHP

### Testing Checklist
- [ ] Test in server mode (http://)
- [ ] Test in file mode (file://)
- [ ] Test standalone version
- [ ] Verify offline functionality
- [ ] Check mobile responsiveness
- [ ] Validate puzzle progression
- [ ] Test all 6 game modes