# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Modern Tetris is a fully-featured, responsive web-based Tetris game built with vanilla JavaScript ES6 modules. It features neon graphics, dynamic audio, advanced touch controls, and Progressive Web App (PWA) capabilities with offline support.

**Latest Version: 2.5.0** - Major AI improvements, puzzle fixes, and offline enhancements.

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
- **modes/sprintMode.js**: 40-line time attack mode
- **modes/marathonMode.js**: 150-line survival with checkpoints
- **modes/zenMode.js**: Relaxing endless mode with statistics
- **modes/puzzleMode.js**: Challenge mode with 150 puzzles
- **modes/battleMode.js**: VS AI battle mode with power-ups and rounds
- **puzzles/puzzleData.js**: 150 puzzle configurations (fixed impossible puzzles)
- **ai/tetrisAI.js**: Advanced AI with T-spin detection, combo tracking, optimal pathfinding (5 difficulty levels)

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

### Recent Critical Fixes
1. **IndexedDB Error**: Fixed invalid `getAll(false)` by filtering results manually
2. **Service Worker 404**: Added dynamic path detection for production environments
3. **Overlay Issues**: Removed duplicate indicator systems, now only minimal offline dot
4. **Puzzle 2 Fix**: Changed from 1 to 2 O pieces for feasibility
5. **AI Improvements**: Complete overhaul of evaluation algorithm and strategies

### Testing Checklist
- [ ] Test in server mode (http://)
- [ ] Test in file mode (file://)
- [ ] Test standalone version
- [ ] Verify offline functionality
- [ ] Check mobile responsiveness
- [ ] Validate puzzle progression
- [ ] Test all 6 game modes