#!/usr/bin/env node

/**
 * Build script to create a standalone single-file version of Tetris
 * Usage: node build-standalone.js
 * Output: tetris-standalone.html
 */

const fs = require('fs').promises;
const path = require('path');

// Configuration
const CONFIG = {
    baseDir: __dirname,
    outputFile: 'tetris-standalone.html',
    htmlFile: 'index.html',
    styles: [
        'styles/main.css',
        'styles/animations.css',
        'styles/responsive.css',
        'styles/modes.css',
        'styles/progression.css'
    ],
    scripts: [
        // Order matters for dependencies
        'scripts/pieces.js',
        'scripts/grid.js',
        'scripts/storage-adapter.js',
        'scripts/offline-storage.js',
        'scripts/audio.js',
        'scripts/input.js',
        'scripts/puzzles/puzzleData.js',
        'scripts/puzzles/puzzleObjectives.js',
        'scripts/puzzles/hintSystem.js',
        'scripts/puzzles/puzzleValidator.js',
        'scripts/puzzles/puzzleAutoSolver.js',
        'scripts/puzzles/puzzleDifficultyAnalyzer.js',
        'scripts/puzzles/puzzleVerificationSuite.js',
        'scripts/powerups/powerUpTypes.js',
        'scripts/powerups/powerUpManager.js',
        'scripts/progression/playerProgression.js',
        'scripts/achievements/achievementSystem.js',
        'scripts/challenges/dailyChallenge.js',
        'scripts/modes/gameMode.js',
        'scripts/modes/classicMode.js',
        'scripts/modes/sprintMode.js',
        'scripts/modes/marathonMode.js',
        'scripts/modes/zenMode.js',
        'scripts/modes/puzzleMode.js',
        'scripts/modes/battleMode.js',
        'scripts/modes/battle2PMode.js',
        'scripts/modes/dailyChallengeMode.js',
        'scripts/modes/powerUpMode.js',
        'scripts/ai/tetrisAI.js',
        'scripts/modeSelector.js',
        'scripts/leaderboard.js',
        'scripts/ui.js',
        'scripts/modals.js',
        'scripts/progression/progressionManager.js',
        'scripts/game.js'
    ],
    // Assets to embed as base64
    assets: {
        'favicon.svg': 'image/svg+xml',
        'logo.svg': 'image/svg+xml'
    }
};

async function readFile(filePath) {
    try {
        return await fs.readFile(path.join(CONFIG.baseDir, filePath), 'utf8');
    } catch (error) {
        console.warn(`Warning: Could not read ${filePath}: ${error.message}`);
        return '';
    }
}

async function readBinaryFile(filePath) {
    try {
        const buffer = await fs.readFile(path.join(CONFIG.baseDir, filePath));
        return buffer.toString('base64');
    } catch (error) {
        console.warn(`Warning: Could not read ${filePath}: ${error.message}`);
        return '';
    }
}

async function embedAssets(html) {
    for (const [assetPath, mimeType] of Object.entries(CONFIG.assets)) {
        const base64 = await readBinaryFile(assetPath);
        if (base64) {
            const dataUri = `data:${mimeType};base64,${base64}`;
            // Replace references to the asset with data URI
            const regex = new RegExp(`(src|href)=["']${assetPath}["']`, 'gi');
            html = html.replace(regex, `$1="${dataUri}"`);
        }
    }
    return html;
}

async function buildStandalone() {
    console.log('🔨 Building standalone Tetris...');
    
    try {
        // Read the base HTML
        let html = await readFile(CONFIG.htmlFile);
        
        // Remove external dependencies that won't work in file:// mode
        html = html.replace(/<link[^>]*href="https:\/\/fonts\.googleapis\.com[^>]*>/gi, '');
        html = html.replace(/<link[^>]*rel="manifest"[^>]*>/gi, '');
        
        // Collect all styles
        console.log('📦 Embedding styles...');
        let combinedStyles = '';
        for (const styleFile of CONFIG.styles) {
            const css = await readFile(styleFile);
            if (css) {
                combinedStyles += `\n/* ${styleFile} */\n${css}\n`;
            }
        }
        
        // Replace external style links with embedded styles
        const styleRegex = /<link[^>]*rel="stylesheet"[^>]*>/gi;
        html = html.replace(styleRegex, '');
        const styleTag = `<style>\n${combinedStyles}\n</style>`;
        html = html.replace('</head>', `${styleTag}\n</head>`);
        
        // Collect all scripts and convert from modules
        console.log('📦 Embedding scripts...');
        let combinedScripts = '';
        
        // Add a module system polyfill
        combinedScripts += `
// Simple module system for standalone version
window.TetrisModules = {};
window.TetrisExports = {};

function defineModule(name, factory) {
    const module = { exports: {} };
    const exports = module.exports;
    factory(exports, module);
    window.TetrisModules[name] = module.exports;
    // Make exports available globally
    if (typeof module.exports === 'object') {
        Object.assign(window.TetrisExports, module.exports);
    }
}

// Override import/export for standalone
`;
        
        for (const scriptFile of CONFIG.scripts) {
            let js = await readFile(scriptFile);
            if (js) {
                // Convert ES6 modules to standalone format
                js = convertModuleToStandalone(js, scriptFile);
                combinedScripts += `\n/* ${scriptFile} */\n${js}\n`;
            }
        }
        
        // Add initialization script
        combinedScripts += `
// Initialize the game when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    console.log('🎮 Initializing standalone Tetris...');
    
    // Set file mode context
    window.TetrisContext = {
        isFileProtocol: true,
        isLocalhost: false,
        hasServiceWorker: false,
        mode: 'standalone'
    };
    
    // Initialize game
    if (window.TetrisExports.Game) {
        window.game = new window.TetrisExports.Game();
    } else if (window.Game) {
        window.game = new window.Game();
    }
    
    console.log('✅ Standalone Tetris ready!');
});
`;
        
        // Remove module script tags and add combined script
        const scriptRegex = /<script[^>]*type="module"[^>]*><\/script>/gi;
        html = html.replace(scriptRegex, '');
        
        // Remove service worker registration script
        const swRegex = /<script>[\s\S]*?serviceWorker[\s\S]*?<\/script>/gi;
        html = html.replace(swRegex, '');
        
        // Add combined scripts before closing body
        const scriptTag = `<script>\n${combinedScripts}\n</script>`;
        html = html.replace('</body>', `${scriptTag}\n</body>`);
        
        // Embed assets as base64
        console.log('📦 Embedding assets...');
        html = await embedAssets(html);
        
        // Add standalone mode indicator
        html = html.replace('<body>', `<body data-standalone="true">`);
        
        // Write the output file
        const outputPath = path.join(CONFIG.baseDir, CONFIG.outputFile);
        await fs.writeFile(outputPath, html, 'utf8');
        
        // Get file size
        const stats = await fs.stat(outputPath);
        const sizeMB = (stats.size / (1024 * 1024)).toFixed(2);
        
        console.log(`✅ Standalone build complete!`);
        console.log(`📄 Output: ${CONFIG.outputFile} (${sizeMB} MB)`);
        console.log(`🎮 Open ${CONFIG.outputFile} directly in your browser to play offline!`);
        
    } catch (error) {
        console.error('❌ Build failed:', error);
        process.exit(1);
    }
}

function convertModuleToStandalone(js, filePath) {
    // Remove import statements
    js = js.replace(/^import\s+.*?from\s+['"].*?['"];?\s*$/gm, '');
    js = js.replace(/^import\s+\{[^}]*\}\s+from\s+['"].*?['"];?\s*$/gm, '');
    
    // Convert named exports to window assignments
    js = js.replace(/^export\s+class\s+(\w+)/gm, 'window.$1 = class $1');
    js = js.replace(/^export\s+function\s+(\w+)/gm, 'window.$1 = function $1');
    js = js.replace(/^export\s+const\s+(\w+)/gm, 'window.$1 = const $1');
    js = js.replace(/^export\s+\{([^}]+)\}/gm, (match, exports) => {
        const items = exports.split(',').map(item => item.trim());
        return items.map(item => `window.${item} = ${item};`).join('\n');
    });
    
    // Handle default exports
    js = js.replace(/^export\s+default\s+class\s+(\w+)/gm, 'window.$1 = class $1');
    js = js.replace(/^export\s+default\s+(\w+);?\s*$/gm, 'window.$1 = $1;');
    
    // Wrap in IIFE to avoid global scope pollution
    return `(function() {\n${js}\n})();`;
}

// Run the build
buildStandalone();