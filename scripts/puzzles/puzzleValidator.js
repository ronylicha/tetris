// Puzzle Validator - Automatic validation system for puzzle feasibility
import { PIECES } from '../pieces.js';
import { GRID_WIDTH, GRID_HEIGHT } from '../grid.js';

export class PuzzleValidator {
    constructor() {
        this.pieceShapes = this.initializePieceShapes();
    }
    
    initializePieceShapes() {
        const shapes = {};
        for (const [type, shape] of Object.entries(PIECES)) {
            shapes[type] = this.getAllRotations(shape);
        }
        return shapes;
    }
    
    getAllRotations(shape) {
        const rotations = [shape];
        let current = shape;
        
        // Get all 4 rotations
        for (let i = 0; i < 3; i++) {
            current = this.rotateMatrix(current);
            rotations.push(current);
        }
        
        return rotations;
    }
    
    rotateMatrix(matrix) {
        const n = matrix.length;
        const rotated = Array(n).fill(null).map(() => Array(n).fill(0));
        
        for (let i = 0; i < n; i++) {
            for (let j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }
        
        return rotated;
    }
    
    /**
     * Validates if a puzzle is solvable
     * @param {Object} puzzle - Puzzle configuration
     * @returns {Object} Validation result with details
     */
    validatePuzzle(puzzle) {
        const result = {
            valid: false,
            solvable: false,
            difficulty: 'unknown',
            issues: [],
            suggestions: [],
            minPiecesRequired: 0,
            theoreticalSolutions: 0
        };
        
        // Basic validation
        if (!puzzle.grid || !puzzle.objective || !puzzle.pieces) {
            result.issues.push('Missing required puzzle properties');
            return result;
        }
        
        // Check if grid is valid
        if (!this.isValidGrid(puzzle.grid)) {
            result.issues.push('Invalid grid configuration');
            return result;
        }
        
        // Check objective feasibility
        const objectiveAnalysis = this.analyzeObjective(puzzle);
        if (!objectiveAnalysis.feasible) {
            result.issues.push(objectiveAnalysis.issue);
            result.suggestions.push(objectiveAnalysis.suggestion);
            return result;
        }
        
        // Calculate minimum pieces needed
        const minPieces = this.calculateMinimumPieces(puzzle);
        result.minPiecesRequired = minPieces;
        
        // Check if enough pieces are provided
        if (puzzle.pieces.length < minPieces) {
            result.issues.push(`Not enough pieces: ${puzzle.pieces.length} provided, ${minPieces} required`);
            result.suggestions.push(`Add at least ${minPieces - puzzle.pieces.length} more pieces`);
            return result;
        }
        
        // Check if pieces can actually solve the puzzle
        const solutionAnalysis = this.findSolutions(puzzle);
        result.theoreticalSolutions = solutionAnalysis.count;
        result.solvable = solutionAnalysis.count > 0;
        
        if (!result.solvable) {
            result.issues.push('No valid solution found with given pieces');
            result.suggestions = result.suggestions.concat(solutionAnalysis.suggestions);
            return result;
        }
        
        // Determine difficulty based on solution count and constraints
        result.difficulty = this.calculateDifficulty(puzzle, solutionAnalysis);
        
        // Mark as valid if we made it here
        result.valid = true;
        
        return result;
    }
    
    isValidGrid(grid) {
        if (!Array.isArray(grid) || grid.length !== GRID_HEIGHT) {
            return false;
        }
        
        for (const row of grid) {
            if (!Array.isArray(row) || row.length !== GRID_WIDTH) {
                return false;
            }
            for (const cell of row) {
                if (typeof cell !== 'number' || cell < 0 || cell > 9) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    analyzeObjective(puzzle) {
        const result = {
            feasible: true,
            issue: '',
            suggestion: ''
        };
        
        switch (puzzle.objective.type) {
            case 'lines':
                const maxPossibleLines = this.calculateMaxPossibleLines(puzzle);
                if (puzzle.objective.count > maxPossibleLines) {
                    result.feasible = false;
                    result.issue = `Objective requires ${puzzle.objective.count} lines but only ${maxPossibleLines} are possible`;
                    result.suggestion = `Reduce objective to ${maxPossibleLines} lines or modify grid`;
                }
                break;
                
            case 'clear':
                const totalBlocks = this.countBlocks(puzzle.grid);
                const maxClearable = this.calculateMaxClearableBlocks(puzzle);
                if (totalBlocks > maxClearable) {
                    result.feasible = false;
                    result.issue = `Cannot clear all blocks with available pieces`;
                    result.suggestion = `Add more pieces or reduce initial blocks`;
                }
                break;
                
            case 'score':
                // Score objectives are generally feasible if lines can be cleared
                const canScore = this.calculateMaxPossibleLines(puzzle) > 0;
                if (!canScore) {
                    result.feasible = false;
                    result.issue = `No lines can be cleared to generate score`;
                    result.suggestion = `Modify grid to allow line clears`;
                }
                break;
        }
        
        return result;
    }
    
    calculateMinimumPieces(puzzle) {
        switch (puzzle.objective.type) {
            case 'lines':
                // Each piece can contribute to at most 1 line (4 blocks per line / 4 blocks per piece)
                return Math.ceil(puzzle.objective.count);
                
            case 'clear':
                // Need to fill gaps to clear the board
                const emptySpaces = this.countEmptySpaces(puzzle.grid);
                return Math.ceil(emptySpaces / 4); // Each piece has 4 blocks
                
            case 'score':
                // Depends on target score, but minimum 1 line clear
                return Math.ceil(puzzle.objective.target / 1000); // Rough estimate
                
            default:
                return 1;
        }
    }
    
    findSolutions(puzzle) {
        const result = {
            count: 0,
            suggestions: []
        };
        
        // Simplified solution finder - checks if pieces can theoretically achieve objective
        // This is a basic implementation - a full solver would use backtracking
        
        const totalBlocks = puzzle.pieces.length * 4;
        const requiredBlocks = this.calculateRequiredBlocks(puzzle);
        
        if (totalBlocks < requiredBlocks) {
            result.suggestions.push(`Need at least ${Math.ceil(requiredBlocks / 4)} pieces`);
            return result;
        }
        
        // Check if specific pieces are needed
        if (puzzle.objective.type === 'lines') {
            const analysis = this.analyzeLineRequirements(puzzle);
            if (analysis.needsSpecificPieces) {
                result.suggestions = result.suggestions.concat(analysis.suggestions);
                if (!analysis.hasSolution) {
                    return result;
                }
            }
        }
        
        // If we made it here, assume at least one solution exists
        // (Full implementation would actually find solutions)
        result.count = 1;
        
        return result;
    }
    
    calculateRequiredBlocks(puzzle) {
        switch (puzzle.objective.type) {
            case 'lines':
                // Need to fill complete lines
                const emptyInTargetLines = this.countEmptyInTargetLines(puzzle);
                return emptyInTargetLines;
                
            case 'clear':
                // Need to fill all empty spaces
                return this.countEmptySpaces(puzzle.grid);
                
            default:
                return 0;
        }
    }
    
    countEmptyInTargetLines(puzzle) {
        let count = 0;
        const targetLines = puzzle.objective.count;
        
        // Count from bottom up
        for (let row = GRID_HEIGHT - 1; row >= GRID_HEIGHT - targetLines && row >= 0; row--) {
            for (let col = 0; col < GRID_WIDTH; col++) {
                if (puzzle.grid[row][col] === 0) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    analyzeLineRequirements(puzzle) {
        const result = {
            needsSpecificPieces: false,
            hasSolution: true,
            suggestions: []
        };
        
        // Check each target line
        const targetLines = puzzle.objective.count;
        for (let i = 0; i < targetLines; i++) {
            const row = GRID_HEIGHT - 1 - i;
            if (row < 0) break;
            
            const emptyCount = puzzle.grid[row].filter(cell => cell === 0).length;
            
            // Special cases
            if (emptyCount === 1) {
                // Need exactly 1 block - only I piece in specific orientation
                result.needsSpecificPieces = true;
                if (!puzzle.pieces.includes('I')) {
                    result.hasSolution = false;
                    result.suggestions.push(`Line ${i + 1} has 1 gap - needs I piece`);
                }
            } else if (emptyCount === 2) {
                // Need 2 blocks - O piece is perfect
                if (!puzzle.pieces.includes('O') && puzzle.pieces.length < 2) {
                    result.suggestions.push(`Line ${i + 1} has 2 gaps - O piece recommended`);
                }
            }
        }
        
        return result;
    }
    
    calculateDifficulty(puzzle, solutionAnalysis) {
        const factors = {
            solutionCount: solutionAnalysis.count,
            pieceConstraint: puzzle.maxPieces / puzzle.pieces.length,
            gridComplexity: this.calculateGridComplexity(puzzle.grid),
            objectiveDifficulty: this.getObjectiveDifficulty(puzzle.objective)
        };
        
        // Weight the factors
        const score = 
            (1 / Math.max(1, factors.solutionCount)) * 40 +  // Fewer solutions = harder
            (1 / Math.max(1, factors.pieceConstraint)) * 20 + // Tighter constraint = harder
            factors.gridComplexity * 20 +                      // Complex grid = harder
            factors.objectiveDifficulty * 20;                  // Harder objective = harder
        
        if (score < 25) return 'easy';
        if (score < 50) return 'medium';
        if (score < 75) return 'hard';
        return 'expert';
    }
    
    calculateGridComplexity(grid) {
        let complexity = 0;
        
        // Check for holes and overhangs
        for (let col = 0; col < GRID_WIDTH; col++) {
            let foundBlock = false;
            for (let row = 0; row < GRID_HEIGHT; row++) {
                if (grid[row][col] !== 0) {
                    foundBlock = true;
                } else if (foundBlock) {
                    complexity += 0.1; // Hole or overhang
                }
            }
        }
        
        // Check for irregular patterns
        for (let row = 0; row < GRID_HEIGHT - 1; row++) {
            for (let col = 0; col < GRID_WIDTH - 1; col++) {
                const pattern = [
                    grid[row][col],
                    grid[row][col + 1],
                    grid[row + 1][col],
                    grid[row + 1][col + 1]
                ];
                
                const uniqueValues = new Set(pattern).size;
                if (uniqueValues === 4) {
                    complexity += 0.05; // Very irregular
                }
            }
        }
        
        return Math.min(1, complexity);
    }
    
    getObjectiveDifficulty(objective) {
        switch (objective.type) {
            case 'lines':
                return Math.min(1, objective.count / 4); // 4+ lines is max difficulty
            case 'clear':
                return 0.8; // Clearing all is generally hard
            case 'score':
                return Math.min(1, objective.target / 10000); // Higher score = harder
            default:
                return 0.5;
        }
    }
    
    calculateMaxPossibleLines(puzzle) {
        // Count how many complete lines could theoretically be made
        let possibleLines = 0;
        
        for (let row = GRID_HEIGHT - 1; row >= 0; row--) {
            const filledCount = puzzle.grid[row].filter(cell => cell !== 0).length;
            const emptyCount = GRID_WIDTH - filledCount;
            
            // Can we fill this line with available pieces?
            const totalBlocks = puzzle.pieces.length * 4;
            if (emptyCount <= totalBlocks) {
                possibleLines++;
                // Subtract blocks used for this line from total
                totalBlocks -= emptyCount;
            }
        }
        
        return possibleLines;
    }
    
    calculateMaxClearableBlocks(puzzle) {
        return puzzle.pieces.length * 4; // Each piece has 4 blocks
    }
    
    countBlocks(grid) {
        let count = 0;
        for (const row of grid) {
            for (const cell of row) {
                if (cell !== 0) count++;
            }
        }
        return count;
    }
    
    countEmptySpaces(grid) {
        let count = 0;
        for (const row of grid) {
            for (const cell of row) {
                if (cell === 0) count++;
            }
        }
        return count;
    }
    
    /**
     * Batch validate all puzzles
     * @param {Array} puzzles - Array of puzzle configurations
     * @returns {Object} Validation report
     */
    validateAllPuzzles(puzzles) {
        const report = {
            total: puzzles.length,
            valid: 0,
            invalid: 0,
            issues: [],
            byDifficulty: {
                easy: 0,
                medium: 0,
                hard: 0,
                expert: 0
            }
        };
        
        puzzles.forEach((puzzle, index) => {
            const result = this.validatePuzzle(puzzle);
            
            if (result.valid) {
                report.valid++;
                report.byDifficulty[result.difficulty]++;
            } else {
                report.invalid++;
                report.issues.push({
                    puzzleIndex: index,
                    puzzleName: puzzle.name || `Puzzle ${index + 1}`,
                    issues: result.issues,
                    suggestions: result.suggestions
                });
            }
        });
        
        return report;
    }
}

// Export singleton instance
export const puzzleValidator = new PuzzleValidator();