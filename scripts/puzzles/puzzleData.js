// Puzzle Data - 150 unique puzzle challenges
export const PUZZLES = [
    // Beginner Puzzles (1-30) - Teaching basics
    {
        id: 1,
        name: "First Clear",
        category: "beginner",
        difficulty: 1,
        objective: "clear",
        targetLines: 1,
        description: "Clear 1 line",
        initialGrid: [
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [1,1,1,1,1,1,0,1,1,1]
        ],
        pieces: ['I'],
        maxPieces: 3,
        timeLimit: 0
    },
    {
        id: 2,
        name: "Double Trouble",
        category: "beginner",
        difficulty: 1,
        objective: "clear",
        targetLines: 2,
        description: "Clear 2 lines at once",
        initialGrid: [
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [1,1,1,1,0,0,1,1,1,1],
            [1,1,1,1,0,0,1,1,1,1]
        ],
        pieces: ['O', 'O'],  // Changed to 2 O pieces for feasibility
        maxPieces: 5,  // Increased to allow more attempts
        timeLimit: 0
    },
    {
        id: 3,
        name: "T-Spin Tutorial",
        category: "beginner",
        difficulty: 2,
        objective: "tspin",
        targetTSpins: 1,
        description: "Perform your first T-Spin",
        initialGrid: [
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [1,0,0,0,0,0,0,0,0,1],
            [1,0,1,1,1,1,1,1,0,1],
            [1,1,1,1,1,1,1,1,0,1]
        ],
        pieces: ['T'],
        maxPieces: 5,
        timeLimit: 0
    },
    {
        id: 4,
        name: "Tetris Time",
        category: "beginner",
        difficulty: 2,
        objective: "tetris",
        targetTetris: 1,
        description: "Clear 4 lines at once (Tetris)",
        initialGrid: [
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,1,1,1,1,1,1,1,1,1],
            [0,1,1,1,1,1,1,1,1,1],
            [0,1,1,1,1,1,1,1,1,1],
            [0,1,1,1,1,1,1,1,1,1]
        ],
        pieces: ['I'],
        maxPieces: 2,
        timeLimit: 0
    },
    {
        id: 5,
        name: "Perfect Clear",
        category: "beginner",
        difficulty: 3,
        objective: "perfectclear",
        description: "Clear the entire board",
        initialGrid: [
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0,0],
            [1,1,1,1,0,0,1,1,1,1]
        ],
        pieces: 'random',  // Changed to random for perfect clear
        maxPieces: 10,      // More pieces for flexibility
        timeLimit: 0
    }
];

// Generate additional puzzles programmatically
function generatePuzzles() {
    const additionalPuzzles = [];
    let id = 6;
    
    // Intermediate Puzzles (31-60)
    for (let i = 0; i < 25; i++) {
        additionalPuzzles.push({
            id: id++,
            name: `Challenge ${id}`,
            category: "intermediate",
            difficulty: 3 + Math.floor(i / 8),
            objective: ["clear", "tspin", "tetris", "perfectclear"][i % 4],
            targetLines: 2 + Math.floor(i / 5),
            description: `Clear ${2 + Math.floor(i / 5)} lines with style`,
            initialGrid: generateRandomGrid(10 + i % 5),
            pieces: generatePieceSet(3 + Math.floor(i / 10)),
            maxPieces: 10 + Math.floor(i / 5),  // Increased for better playability
            timeLimit: i > 15 ? 120 : 0
        });
    }
    
    // Advanced Puzzles (61-90)
    for (let i = 0; i < 30; i++) {
        additionalPuzzles.push({
            id: id++,
            name: `Expert ${id}`,
            category: "advanced",
            difficulty: 6 + Math.floor(i / 10),
            objective: ["combo", "tspin", "tetris", "perfectclear"][i % 4],
            targetLines: 3 + Math.floor(i / 4),
            targetCombo: i % 4 === 0 ? 3 + Math.floor(i / 10) : undefined,
            targetTSpins: i % 4 === 1 ? 2 : undefined,
            targetTetris: i % 4 === 2 ? 1 : undefined,
            description: `Advanced technique required`,
            initialGrid: generateComplexGrid(12 + i % 6),
            pieces: generatePieceSet(4 + Math.floor(i / 8)),
            maxPieces: 12 + Math.floor(i / 8),  // Increased for better playability
            timeLimit: 90 - (i * 2)
        });
    }
    
    // Expert Puzzles (91-120)
    for (let i = 0; i < 30; i++) {
        additionalPuzzles.push({
            id: id++,
            name: `Master ${id}`,
            category: "expert",
            difficulty: 8 + Math.floor(i / 15),
            objective: "mixed",
            targetLines: 4 + Math.floor(i / 3),
            targetTSpins: 1 + Math.floor(i / 15),
            targetCombo: 4 + Math.floor(i / 10),
            description: `Master-level challenge`,
            initialGrid: generateExpertGrid(14 + i % 4),
            pieces: generateMixedPieceSet(5 + Math.floor(i / 6)),
            maxPieces: 15 + Math.floor(i / 6),  // Increased for better playability
            timeLimit: 60
        });
    }
    
    // Grandmaster Puzzles (121-150)
    for (let i = 0; i < 30; i++) {
        additionalPuzzles.push({
            id: id++,
            name: `Grandmaster ${id}`,
            category: "grandmaster",
            difficulty: 10,
            objective: "survival",
            targetLines: 5 + Math.floor(i / 2),
            minScore: 1000 * (i + 1),
            description: `Ultimate Tetris challenge`,
            initialGrid: generateGrandmasterGrid(16 + i % 3),
            pieces: "random",
            maxPieces: 20 + Math.floor(i / 3),  // Increased significantly for survival mode
            timeLimit: 45
        });
    }
    
    return additionalPuzzles;
}

// Helper functions to generate grids
function generateRandomGrid(filledRows) {
    const grid = Array(20).fill(null).map(() => Array(10).fill(0));
    for (let row = 20 - filledRows; row < 20; row++) {
        for (let col = 0; col < 10; col++) {
            if (Math.random() > 0.2) {
                grid[row][col] = Math.floor(Math.random() * 7) + 1;
            }
        }
    }
    return grid;
}

function generateComplexGrid(filledRows) {
    const grid = Array(20).fill(null).map(() => Array(10).fill(0));
    for (let row = 20 - filledRows; row < 20; row++) {
        const holes = Math.floor(Math.random() * 3) + 1;
        const holePositions = [];
        for (let i = 0; i < holes; i++) {
            holePositions.push(Math.floor(Math.random() * 10));
        }
        for (let col = 0; col < 10; col++) {
            if (!holePositions.includes(col)) {
                grid[row][col] = Math.floor(Math.random() * 7) + 1;
            }
        }
    }
    return grid;
}

function generateExpertGrid(filledRows) {
    const grid = Array(20).fill(null).map(() => Array(10).fill(0));
    // Create challenging patterns
    for (let row = 20 - filledRows; row < 20; row++) {
        const pattern = row % 3;
        for (let col = 0; col < 10; col++) {
            if (pattern === 0 && col % 2 === 0) {
                grid[row][col] = Math.floor(Math.random() * 7) + 1;
            } else if (pattern === 1 && col % 2 === 1) {
                grid[row][col] = Math.floor(Math.random() * 7) + 1;
            } else if (pattern === 2 && col !== 4 && col !== 5) {
                grid[row][col] = Math.floor(Math.random() * 7) + 1;
            }
        }
    }
    return grid;
}

function generateGrandmasterGrid(filledRows) {
    const grid = Array(20).fill(null).map(() => Array(10).fill(0));
    // Create very challenging patterns with minimal clearing opportunities
    for (let row = 20 - filledRows; row < 20; row++) {
        const clearable = row === 19 || Math.random() > 0.7;
        if (clearable) {
            // Leave strategic holes
            const hole = Math.floor(Math.random() * 10);
            for (let col = 0; col < 10; col++) {
                if (col !== hole) {
                    grid[row][col] = Math.floor(Math.random() * 7) + 1;
                }
            }
        } else {
            // Complex pattern
            for (let col = 0; col < 10; col++) {
                if (Math.random() > 0.15) {
                    grid[row][col] = Math.floor(Math.random() * 7) + 1;
                }
            }
        }
    }
    return grid;
}

function generatePieceSet(count) {
    const pieces = ['I', 'O', 'T', 'S', 'Z', 'J', 'L'];
    const set = [];
    for (let i = 0; i < count; i++) {
        set.push(pieces[Math.floor(Math.random() * pieces.length)]);
    }
    return set;
}

function generateMixedPieceSet(count) {
    const pieces = ['I', 'O', 'T', 'S', 'Z', 'J', 'L'];
    const set = [];
    // Ensure variety
    pieces.forEach(piece => {
        if (set.length < count) {
            set.push(piece);
        }
    });
    // Fill remaining with random
    while (set.length < count) {
        set.push(pieces[Math.floor(Math.random() * pieces.length)]);
    }
    return set;
}

// Add generated puzzles to the main array
PUZZLES.push(...generatePuzzles());

// Export puzzle utilities
export function getPuzzleById(id) {
    return PUZZLES.find(p => p.id === id);
}

export function getPuzzlesByCategory(category) {
    return PUZZLES.filter(p => p.category === category);
}

export function getPuzzlesByDifficulty(difficulty) {
    return PUZZLES.filter(p => p.difficulty === difficulty);
}

export function getUnlockedPuzzles(completedIds = []) {
    // First 5 puzzles are always unlocked
    // Each completed puzzle unlocks the next 2
    const baseUnlocked = 5;
    const unlockedCount = baseUnlocked + (completedIds.length * 2);
    return PUZZLES.slice(0, Math.min(unlockedCount, PUZZLES.length));
}

export function getNextPuzzle(currentId, completedIds = []) {
    const currentIndex = PUZZLES.findIndex(p => p.id === currentId);
    if (currentIndex === -1 || currentIndex === PUZZLES.length - 1) {
        return null;
    }
    
    const nextPuzzle = PUZZLES[currentIndex + 1];
    const unlockedPuzzles = getUnlockedPuzzles(completedIds);
    
    if (unlockedPuzzles.find(p => p.id === nextPuzzle.id)) {
        return nextPuzzle;
    }
    return null;
}

export const PUZZLE_CATEGORIES = [
    { id: 'beginner', name: 'Beginner', color: '#00ff00', icon: '🌱' },
    { id: 'intermediate', name: 'Intermediate', color: '#ffff00', icon: '⭐' },
    { id: 'advanced', name: 'Advanced', color: '#ff8800', icon: '🔥' },
    { id: 'expert', name: 'Expert', color: '#ff0000', icon: '💎' },
    { id: 'grandmaster', name: 'Grandmaster', color: '#ff00ff', icon: '👑' }
];

export const PUZZLE_OBJECTIVES = {
    clear: { name: 'Line Clear', description: 'Clear specified number of lines' },
    tspin: { name: 'T-Spin', description: 'Perform T-Spin clears' },
    tetris: { name: 'Tetris', description: 'Clear 4 lines at once' },
    perfectclear: { name: 'Perfect Clear', description: 'Clear the entire board' },
    combo: { name: 'Combo', description: 'Achieve combo chains' },
    mixed: { name: 'Mixed', description: 'Complete multiple objectives' },
    survival: { name: 'Survival', description: 'Survive and score high' }
};

// Make PUZZLES available globally for UI
if (typeof window !== 'undefined') {
    window.PUZZLES = PUZZLES;
}