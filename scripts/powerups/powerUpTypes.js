// Power-Up Types and Definitions
export const POWER_UP_TYPES = {
    SLOW_TIME: {
        id: 'slow_time',
        name: 'Slow Time',
        icon: '🕐',
        color: '#00ffff',
        duration: 10000, // 10 seconds
        description: 'Slows down piece falling speed by 50%',
        rarity: 'common',
        effect: (game) => {
            game.dropInterval *= 2; // Double the drop interval (slower)
        },
        cleanup: (game) => {
            game.dropInterval /= 2; // Restore original speed
        }
    },
    
    LINE_BOMB: {
        id: 'line_bomb',
        name: 'Line Bomb',
        icon: '💣',
        color: '#ff4444',
        duration: 0, // Instant use
        description: 'Instantly clears the bottom line',
        rarity: 'uncommon',
        effect: (game) => {
            if (game.grid) {
                // Clear the bottom line
                for (let x = 0; x < game.grid.width; x++) {
                    game.grid.cells[game.grid.height - 1][x] = 0;
                }
                // Collapse grid
                game.grid.collapseEmptySpaces();
                game.score += 100;
                
                // Play explosion sound
                if (game.audioManager) {
                    game.audioManager.playSFX('explosion');
                }
            }
        },
        cleanup: null
    },
    
    GHOST_MODE: {
        id: 'ghost_mode',
        name: 'Ghost Mode',
        icon: '👻',
        color: '#9966ff',
        duration: 5000, // 5 seconds
        description: 'Pieces can pass through blocks',
        rarity: 'rare',
        effect: (game) => {
            game.ghostModeActive = true;
        },
        cleanup: (game) => {
            game.ghostModeActive = false;
        }
    },
    
    LIGHTNING: {
        id: 'lightning',
        name: 'Lightning Clear',
        icon: '⚡',
        color: '#ffff00',
        duration: 0, // Instant
        description: 'Clears all isolated single blocks',
        rarity: 'uncommon',
        effect: (game) => {
            if (game.grid) {
                let blocksCleared = 0;
                for (let y = 0; y < game.grid.height; y++) {
                    for (let x = 0; x < game.grid.width; x++) {
                        if (game.grid.cells[y][x] !== 0) {
                            // Check if block is isolated (no neighbors)
                            const neighbors = [
                                [y-1, x], [y+1, x], [y, x-1], [y, x+1]
                            ];
                            let isolated = true;
                            for (const [ny, nx] of neighbors) {
                                if (ny >= 0 && ny < game.grid.height && 
                                    nx >= 0 && nx < game.grid.width &&
                                    game.grid.cells[ny][nx] !== 0) {
                                    isolated = false;
                                    break;
                                }
                            }
                            if (isolated) {
                                game.grid.cells[y][x] = 0;
                                blocksCleared++;
                            }
                        }
                    }
                }
                game.score += blocksCleared * 20;
            }
        },
        cleanup: null
    },
    
    PRECISION: {
        id: 'precision',
        name: 'Precision View',
        icon: '🎯',
        color: '#00ff00',
        duration: 15000, // 15 seconds
        description: 'Shows 5 next pieces instead of 3',
        rarity: 'common',
        effect: (game) => {
            game.extendedPreview = true;
            // Add 2 more pieces to preview
            if (game.nextPieces && game.pieceBag) {
                while (game.nextPieces.length < 5) {
                    game.nextPieces.push(game.pieceBag.getNextPiece().type);
                }
            }
        },
        cleanup: (game) => {
            game.extendedPreview = false;
            // Restore to 3 pieces
            if (game.nextPieces) {
                game.nextPieces = game.nextPieces.slice(0, 3);
            }
        }
    },
    
    DOUBLE_SCORE: {
        id: 'double_score',
        name: '2x Score',
        icon: '💎',
        color: '#ffd700',
        duration: 10000, // 10 seconds
        description: 'Doubles all points earned',
        rarity: 'common',
        effect: (game) => {
            game.scoreMultiplier = (game.scoreMultiplier || 1) * 2;
        },
        cleanup: (game) => {
            game.scoreMultiplier = (game.scoreMultiplier || 2) / 2;
        }
    },
    
    SHUFFLE: {
        id: 'shuffle',
        name: 'Grid Shuffle',
        icon: '🔄',
        color: '#ff9900',
        duration: 0, // Instant
        description: 'Randomly reorganizes existing blocks',
        rarity: 'rare',
        effect: (game) => {
            if (game.grid) {
                // Collect all non-empty blocks
                const blocks = [];
                for (let y = 0; y < game.grid.height; y++) {
                    for (let x = 0; x < game.grid.width; x++) {
                        if (game.grid.cells[y][x] !== 0) {
                            blocks.push(game.grid.cells[y][x]);
                            game.grid.cells[y][x] = 0;
                        }
                    }
                }
                
                // Randomly redistribute blocks from bottom
                let blockIndex = 0;
                for (let y = game.grid.height - 1; y >= 0 && blockIndex < blocks.length; y--) {
                    for (let x = 0; x < game.grid.width && blockIndex < blocks.length; x++) {
                        if (Math.random() > 0.3) { // 70% chance to place
                            game.grid.cells[y][x] = blocks[blockIndex++];
                        }
                    }
                }
            }
        },
        cleanup: null
    },
    
    MAGNET: {
        id: 'magnet',
        name: 'Magnet Mode',
        icon: '🧲',
        color: '#ff00ff',
        duration: 8000, // 8 seconds
        description: 'Auto-attracts pieces to fill gaps',
        rarity: 'uncommon',
        effect: (game) => {
            game.magnetMode = true;
        },
        cleanup: (game) => {
            game.magnetMode = false;
        }
    }
};

// Power-up generation chances based on accomplishments
export const POWER_UP_CHANCES = {
    tetris: 1.0,        // 100% chance
    tspin: 0.5,         // 50% chance
    combo5: 0.75,       // 75% chance
    combo10: 1.0,       // 100% chance
    perfectClear: 2.0,  // 200% chance (2 power-ups)
    lines10: 0.3,       // 30% chance
    lines20: 0.6,       // 60% chance
    score1000: 0.25,    // 25% chance
    score5000: 0.5      // 50% chance
};

// Get random power-up based on rarity
export function getRandomPowerUp() {
    const powerUps = Object.values(POWER_UP_TYPES);
    const weights = {
        common: 50,
        uncommon: 30,
        rare: 20
    };
    
    // Calculate total weight
    let totalWeight = 0;
    for (const powerUp of powerUps) {
        totalWeight += weights[powerUp.rarity];
    }
    
    // Random selection
    let random = Math.random() * totalWeight;
    for (const powerUp of powerUps) {
        random -= weights[powerUp.rarity];
        if (random <= 0) {
            return powerUp;
        }
    }
    
    // Fallback
    return powerUps[0];
}

// Check if accomplishment should generate power-up
export function shouldGeneratePowerUp(accomplishment) {
    const chance = POWER_UP_CHANCES[accomplishment] || 0;
    return Math.random() < chance;
}

// Get multiple power-ups for special accomplishments
export function getPowerUpCount(accomplishment) {
    const chance = POWER_UP_CHANCES[accomplishment] || 0;
    return Math.floor(chance);
}