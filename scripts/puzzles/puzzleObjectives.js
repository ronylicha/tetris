// Enhanced Puzzle Objectives System
export const PUZZLE_OBJECTIVE_TYPES = {
    // Classic objectives
    CLEAR: 'clear',
    PERFECT: 'perfect',
    TSPIN: 'tspin',
    TETRIS: 'tetris',
    COMBO: 'combo',
    
    // New objectives
    CASCADE: 'cascade',
    SPEED: 'speed',
    NO_ROTATION: 'norotation',
    CHAIN: 'chain',
    PATTERN: 'pattern',
    SURVIVAL: 'survival'
};

export class PuzzleObjective {
    constructor(type, config = {}) {
        this.type = type;
        this.config = config;
        this.progress = 0;
        this.completed = false;
        this.failed = false;
        this.startTime = null;
    }
    
    initialize() {
        this.progress = 0;
        this.completed = false;
        this.failed = false;
        this.startTime = Date.now();
    }
    
    checkCompletion(gameState, event) {
        switch (this.type) {
            case PUZZLE_OBJECTIVE_TYPES.CASCADE:
                return this.checkCascade(gameState, event);
            
            case PUZZLE_OBJECTIVE_TYPES.SPEED:
                return this.checkSpeed(gameState, event);
            
            case PUZZLE_OBJECTIVE_TYPES.NO_ROTATION:
                return this.checkNoRotation(gameState, event);
                
            case PUZZLE_OBJECTIVE_TYPES.CHAIN:
                return this.checkChain(gameState, event);
                
            case PUZZLE_OBJECTIVE_TYPES.PATTERN:
                return this.checkPattern(gameState, event);
                
            case PUZZLE_OBJECTIVE_TYPES.SURVIVAL:
                return this.checkSurvival(gameState, event);
                
            default:
                return this.checkClassicObjective(gameState, event);
        }
    }
    
    checkCascade(gameState, event) {
        // Cascade: Clear multiple lines with gravity effect
        if (event.type === 'lines_cleared') {
            if (event.cascade) {
                this.progress += event.linesCleared;
                
                if (this.progress >= (this.config.targetCascade || 3)) {
                    this.completed = true;
                    return {
                        completed: true,
                        message: `Cascade complete! ${this.progress} lines cleared with gravity!`
                    };
                }
            }
        }
        
        return { completed: false, progress: this.progress };
    }
    
    checkSpeed(gameState, event) {
        // Speed: Complete objective within time limit
        const elapsed = Date.now() - this.startTime;
        const timeLimit = (this.config.timeLimit || 30) * 1000;
        
        if (event.type === 'lines_cleared') {
            this.progress += event.linesCleared;
            
            if (this.progress >= (this.config.targetLines || 5)) {
                if (elapsed <= timeLimit) {
                    this.completed = true;
                    const timeBonus = Math.floor((timeLimit - elapsed) / 1000);
                    return {
                        completed: true,
                        message: `Speed clear! Completed with ${timeBonus}s to spare!`,
                        bonus: timeBonus * 100
                    };
                } else {
                    this.failed = true;
                    return {
                        failed: true,
                        message: 'Too slow! Time limit exceeded.'
                    };
                }
            }
        }
        
        if (elapsed > timeLimit && !this.completed) {
            this.failed = true;
            return {
                failed: true,
                message: 'Time limit exceeded!'
            };
        }
        
        return { 
            completed: false, 
            progress: this.progress,
            timeRemaining: Math.max(0, Math.floor((timeLimit - elapsed) / 1000))
        };
    }
    
    checkNoRotation(gameState, event) {
        // No Rotation: Complete without rotating pieces
        if (event.type === 'piece_rotated') {
            this.failed = true;
            return {
                failed: true,
                message: 'Failed! You rotated a piece.'
            };
        }
        
        if (event.type === 'lines_cleared') {
            this.progress += event.linesCleared;
            
            if (this.progress >= (this.config.targetLines || 3)) {
                this.completed = true;
                return {
                    completed: true,
                    message: 'No-rotation mastery! Completed without rotating!'
                };
            }
        }
        
        return { completed: false, progress: this.progress };
    }
    
    checkChain(gameState, event) {
        // Chain: Clear lines in consecutive drops
        if (event.type === 'lines_cleared') {
            if (event.linesCleared > 0) {
                this.progress++;
                
                if (this.progress >= (this.config.targetChain || 5)) {
                    this.completed = true;
                    return {
                        completed: true,
                        message: `Chain complete! ${this.progress} consecutive clears!`
                    };
                }
            }
        } else if (event.type === 'piece_placed' && !event.clearedLines) {
            // Chain broken
            if (this.progress > 0) {
                const broken = this.progress;
                this.progress = 0;
                return {
                    chainBroken: true,
                    message: `Chain broken at ${broken}! Start over.`
                };
            }
        }
        
        return { completed: false, progress: this.progress };
    }
    
    checkPattern(gameState, event) {
        // Pattern: Create specific shape or pattern
        if (event.type === 'piece_placed' || event.type === 'lines_cleared') {
            const pattern = this.config.pattern || 'checkerboard';
            const matched = this.checkGridPattern(gameState.grid, pattern);
            
            if (matched) {
                this.completed = true;
                return {
                    completed: true,
                    message: `Pattern complete! Created ${pattern} successfully!`
                };
            }
            
            // Calculate pattern progress
            this.progress = this.calculatePatternProgress(gameState.grid, pattern);
        }
        
        return { 
            completed: false, 
            progress: Math.floor(this.progress * 100),
            hint: this.getPatternHint(this.config.pattern)
        };
    }
    
    checkSurvival(gameState, event) {
        // Survival: Survive for duration without topping out
        const elapsed = Date.now() - this.startTime;
        const survivalTime = (this.config.survivalTime || 60) * 1000;
        
        if (gameState.gameOver) {
            this.failed = true;
            return {
                failed: true,
                message: `Failed! Survived ${Math.floor(elapsed / 1000)}s`
            };
        }
        
        if (elapsed >= survivalTime) {
            this.completed = true;
            return {
                completed: true,
                message: `Survival complete! Lasted ${Math.floor(survivalTime / 1000)}s!`
            };
        }
        
        this.progress = (elapsed / survivalTime) * 100;
        
        return {
            completed: false,
            progress: Math.floor(this.progress),
            timeRemaining: Math.floor((survivalTime - elapsed) / 1000)
        };
    }
    
    checkClassicObjective(gameState, event) {
        // Handle classic objectives
        switch (this.type) {
            case PUZZLE_OBJECTIVE_TYPES.CLEAR:
                if (event.type === 'lines_cleared') {
                    this.progress += event.linesCleared;
                    if (this.progress >= (this.config.targetLines || 1)) {
                        this.completed = true;
                    }
                }
                break;
                
            case PUZZLE_OBJECTIVE_TYPES.PERFECT:
                if (gameState.grid && gameState.grid.isEmpty()) {
                    this.completed = true;
                }
                break;
                
            case PUZZLE_OBJECTIVE_TYPES.TSPIN:
                if (event.type === 'special_clear' && event.clearType === 'tspin') {
                    this.progress++;
                    if (this.progress >= (this.config.targetTSpins || 1)) {
                        this.completed = true;
                    }
                }
                break;
                
            case PUZZLE_OBJECTIVE_TYPES.TETRIS:
                if (event.type === 'lines_cleared' && event.linesCleared === 4) {
                    this.progress++;
                    if (this.progress >= (this.config.targetTetris || 1)) {
                        this.completed = true;
                    }
                }
                break;
                
            case PUZZLE_OBJECTIVE_TYPES.COMBO:
                if (event.type === 'combo_update') {
                    this.progress = Math.max(this.progress, event.combo);
                    if (this.progress >= (this.config.targetCombo || 3)) {
                        this.completed = true;
                    }
                }
                break;
        }
        
        return { 
            completed: this.completed, 
            progress: this.progress 
        };
    }
    
    checkGridPattern(grid, patternType) {
        switch (patternType) {
            case 'checkerboard':
                return this.isCheckerboard(grid);
            case 'pyramid':
                return this.isPyramid(grid);
            case 'stairs':
                return this.isStairs(grid);
            case 'columns':
                return this.isColumns(grid);
            case 'zigzag':
                return this.isZigzag(grid);
            default:
                return false;
        }
    }
    
    isCheckerboard(grid) {
        // Check if bottom rows form a checkerboard pattern
        const checkRows = 4;
        const startRow = grid.length - checkRows;
        
        for (let row = startRow; row < grid.length; row++) {
            for (let col = 0; col < grid[row].length; col++) {
                const expected = ((row + col) % 2 === 0) ? 1 : 0;
                if ((grid[row][col] > 0 ? 1 : 0) !== expected) {
                    return false;
                }
            }
        }
        return true;
    }
    
    isPyramid(grid) {
        // Check if blocks form a pyramid shape
        const height = 5;
        const startRow = grid.length - height;
        const centerCol = Math.floor(grid[0].length / 2);
        
        for (let i = 0; i < height; i++) {
            const row = startRow + i;
            const width = height - i;
            const startCol = centerCol - width + 1;
            const endCol = centerCol + width - 1;
            
            for (let col = 0; col < grid[row].length; col++) {
                if (col >= startCol && col <= endCol) {
                    if (grid[row][col] === 0) return false;
                } else {
                    if (grid[row][col] !== 0) return false;
                }
            }
        }
        return true;
    }
    
    isStairs(grid) {
        // Check if blocks form stairs pattern
        const height = 4;
        const startRow = grid.length - height;
        
        for (let i = 0; i < height; i++) {
            const row = startRow + i;
            const expectedHeight = height - i;
            
            for (let col = 0; col < expectedHeight; col++) {
                if (grid[row][col] === 0) return false;
            }
            for (let col = expectedHeight; col < grid[row].length; col++) {
                if (grid[row][col] !== 0) return false;
            }
        }
        return true;
    }
    
    isColumns(grid) {
        // Check if blocks form alternating columns
        const height = 6;
        const startRow = grid.length - height;
        
        for (let row = startRow; row < grid.length; row++) {
            for (let col = 0; col < grid[row].length; col++) {
                const expected = (col % 2 === 0) ? 1 : 0;
                if ((grid[row][col] > 0 ? 1 : 0) !== expected) {
                    return false;
                }
            }
        }
        return true;
    }
    
    isZigzag(grid) {
        // Check if blocks form zigzag pattern
        const height = 4;
        const startRow = grid.length - height;
        
        for (let i = 0; i < height; i++) {
            const row = startRow + i;
            const offset = i % 2;
            
            for (let col = 0; col < grid[row].length - 1; col++) {
                const expected = ((col + offset) % 2 === 0) ? 1 : 0;
                if ((grid[row][col] > 0 ? 1 : 0) !== expected) {
                    return false;
                }
            }
        }
        return true;
    }
    
    calculatePatternProgress(grid, patternType) {
        // Calculate how close the grid is to matching the pattern
        let totalCells = 0;
        let matchingCells = 0;
        
        switch (patternType) {
            case 'checkerboard':
                const checkRows = 4;
                const startRow = grid.length - checkRows;
                totalCells = checkRows * grid[0].length;
                
                for (let row = startRow; row < grid.length; row++) {
                    for (let col = 0; col < grid[row].length; col++) {
                        const expected = ((row + col) % 2 === 0);
                        const actual = grid[row][col] > 0;
                        if (expected === actual) matchingCells++;
                    }
                }
                break;
                
            // Add other pattern progress calculations...
        }
        
        return totalCells > 0 ? matchingCells / totalCells : 0;
    }
    
    getPatternHint(patternType) {
        const hints = {
            checkerboard: 'Create alternating filled and empty cells',
            pyramid: 'Build a pyramid shape in the center',
            stairs: 'Create descending stairs from left to right',
            columns: 'Form alternating vertical columns',
            zigzag: 'Create a zigzag pattern'
        };
        return hints[patternType] || 'Create the required pattern';
    }
    
    getDescription() {
        switch (this.type) {
            case PUZZLE_OBJECTIVE_TYPES.CASCADE:
                return `Clear ${this.config.targetCascade || 3} lines with cascade effect`;
                
            case PUZZLE_OBJECTIVE_TYPES.SPEED:
                return `Clear ${this.config.targetLines || 5} lines in ${this.config.timeLimit || 30} seconds`;
                
            case PUZZLE_OBJECTIVE_TYPES.NO_ROTATION:
                return `Clear ${this.config.targetLines || 3} lines without rotating`;
                
            case PUZZLE_OBJECTIVE_TYPES.CHAIN:
                return `Clear lines in ${this.config.targetChain || 5} consecutive drops`;
                
            case PUZZLE_OBJECTIVE_TYPES.PATTERN:
                return `Create a ${this.config.pattern || 'checkerboard'} pattern`;
                
            case PUZZLE_OBJECTIVE_TYPES.SURVIVAL:
                return `Survive for ${this.config.survivalTime || 60} seconds`;
                
            default:
                return 'Complete the objective';
        }
    }
    
    getProgressText() {
        switch (this.type) {
            case PUZZLE_OBJECTIVE_TYPES.CASCADE:
                return `Cascade: ${this.progress}/${this.config.targetCascade || 3}`;
                
            case PUZZLE_OBJECTIVE_TYPES.SPEED:
                return `Lines: ${this.progress}/${this.config.targetLines || 5}`;
                
            case PUZZLE_OBJECTIVE_TYPES.NO_ROTATION:
                return `No-Rotation: ${this.progress}/${this.config.targetLines || 3}`;
                
            case PUZZLE_OBJECTIVE_TYPES.CHAIN:
                return `Chain: ${this.progress}/${this.config.targetChain || 5}`;
                
            case PUZZLE_OBJECTIVE_TYPES.PATTERN:
                return `Pattern: ${Math.floor(this.progress * 100)}%`;
                
            case PUZZLE_OBJECTIVE_TYPES.SURVIVAL:
                return `Survival: ${Math.floor(this.progress)}%`;
                
            default:
                return `Progress: ${this.progress}`;
        }
    }
}

// Factory function to create objectives from puzzle data
export function createObjective(puzzleData) {
    const type = puzzleData.objective || PUZZLE_OBJECTIVE_TYPES.CLEAR;
    const config = {
        targetLines: puzzleData.targetLines,
        targetCascade: puzzleData.targetCascade,
        targetTSpins: puzzleData.targetTSpins,
        targetTetris: puzzleData.targetTetris,
        targetCombo: puzzleData.targetCombo,
        targetChain: puzzleData.targetChain,
        timeLimit: puzzleData.timeLimit,
        survivalTime: puzzleData.survivalTime,
        pattern: puzzleData.pattern,
        minScore: puzzleData.minScore
    };
    
    return new PuzzleObjective(type, config);
}