// Comprehensive Puzzle Verification Suite
import { PUZZLES } from './puzzleData.js';
import { PuzzleAutoSolver } from './puzzleAutoSolver.js';
import { PuzzleDifficultyAnalyzer } from './puzzleDifficultyAnalyzer.js';
import { storage } from '../storage-adapter.js';

export class PuzzleVerificationSuite {
    constructor() {
        this.solver = new PuzzleAutoSolver();
        this.analyzer = new PuzzleDifficultyAnalyzer();
        this.results = [];
        this.impossiblePuzzles = [];
        this.fixes = {};
    }
    
    // Verify all puzzles
    async verifyAllPuzzles() {
        console.log('🔍 Starting comprehensive puzzle verification...');
        
        this.results = [];
        this.impossiblePuzzles = [];
        this.fixes = {};
        
        for (let i = 0; i < PUZZLES.length; i++) {
            const puzzle = PUZZLES[i];
            const result = await this.verifyPuzzle(puzzle);
            
            this.results.push(result);
            
            if (!result.solvable) {
                this.impossiblePuzzles.push(puzzle.id);
                
                // Attempt to fix
                const fix = this.generateFix(puzzle, result);
                if (fix) {
                    this.fixes[puzzle.id] = fix;
                }
            }
            
            // Progress update
            if ((i + 1) % 10 === 0) {
                console.log(`Verified ${i + 1}/${PUZZLES.length} puzzles...`);
            }
        }
        
        // Generate report
        const report = this.generateReport();
        
        // Save verification results
        await this.saveResults(report);
        
        return report;
    }
    
    // Verify single puzzle
    async verifyPuzzle(puzzle) {
        const startTime = Date.now();
        
        const result = {
            id: puzzle.id,
            name: puzzle.name,
            solvable: false,
            solution: null,
            difficulty: null,
            issues: [],
            warnings: [],
            time: 0
        };
        
        try {
            // Basic validation
            const validationIssues = this.validatePuzzleStructure(puzzle);
            result.issues.push(...validationIssues);
            
            // Convert puzzle to proper format
            const puzzleConfig = this.normalizePuzzle(puzzle);
            
            // Attempt to solve
            const solution = this.solver.solve(puzzleConfig);
            
            if (solution && solution.success) {
                result.solvable = true;
                result.solution = solution;
                
                // Analyze difficulty
                const analysis = this.analyzer.analyze(puzzleConfig);
                result.difficulty = analysis;
                
                // Check for warnings
                result.warnings = this.checkForWarnings(puzzle, solution, analysis);
            } else {
                result.solvable = false;
                result.issues.push('No solution found');
                
                // Detailed failure analysis
                const failureReasons = this.analyzeFailure(puzzle);
                result.issues.push(...failureReasons);
            }
            
        } catch (error) {
            result.issues.push(`Error during verification: ${error.message}`);
        }
        
        result.time = Date.now() - startTime;
        
        return result;
    }
    
    // Validate puzzle structure
    validatePuzzleStructure(puzzle) {
        const issues = [];
        
        // Check required fields
        if (!puzzle.id) issues.push('Missing puzzle ID');
        if (!puzzle.name) issues.push('Missing puzzle name');
        if (!puzzle.objective) issues.push('Missing objective');
        
        // Validate grid
        if (puzzle.initialGrid) {
            if (puzzle.initialGrid.length !== 20) {
                issues.push(`Invalid grid height: ${puzzle.initialGrid.length} (should be 20)`);
            } else if (puzzle.initialGrid[0].length !== 10) {
                issues.push(`Invalid grid width: ${puzzle.initialGrid[0].length} (should be 10)`);
            }
            
            // Check for invalid values
            for (let row = 0; row < puzzle.initialGrid.length; row++) {
                for (let col = 0; col < puzzle.initialGrid[row].length; col++) {
                    const value = puzzle.initialGrid[row][col];
                    if (value < 0 || value > 7) {
                        issues.push(`Invalid grid value at [${row},${col}]: ${value}`);
                    }
                }
            }
        }
        
        // Validate pieces
        if (puzzle.pieces && puzzle.pieces !== 'random') {
            const validPieces = ['I', 'O', 'T', 'S', 'Z', 'J', 'L'];
            for (const piece of puzzle.pieces) {
                if (!validPieces.includes(piece)) {
                    issues.push(`Invalid piece type: ${piece}`);
                }
            }
            
            // Check piece count vs requirements
            if (puzzle.maxPieces && puzzle.pieces.length < puzzle.maxPieces) {
                issues.push(`Not enough pieces: ${puzzle.pieces.length} < ${puzzle.maxPieces}`);
            }
        }
        
        // Validate objective requirements
        const objectiveIssues = this.validateObjective(puzzle);
        issues.push(...objectiveIssues);
        
        return issues;
    }
    
    // Validate objective feasibility
    validateObjective(puzzle) {
        const issues = [];
        
        switch (puzzle.objective) {
            case 'clear':
            case 'lines':
                if (puzzle.targetLines) {
                    const maxPossibleLines = this.calculateMaxPossibleLines(puzzle);
                    if (puzzle.targetLines > maxPossibleLines) {
                        issues.push(`Target lines (${puzzle.targetLines}) exceeds maximum possible (${maxPossibleLines})`);
                    }
                }
                break;
                
            case 'tspin':
                if (puzzle.targetTSpins && puzzle.pieces !== 'random') {
                    const tPieces = puzzle.pieces.filter(p => p === 'T').length;
                    if (tPieces < puzzle.targetTSpins) {
                        issues.push(`Not enough T pieces (${tPieces}) for target T-spins (${puzzle.targetTSpins})`);
                    }
                }
                break;
                
            case 'tetris':
                if (puzzle.targetTetris && puzzle.pieces !== 'random') {
                    const iPieces = puzzle.pieces.filter(p => p === 'I').length;
                    if (iPieces < puzzle.targetTetris) {
                        issues.push(`Not enough I pieces (${iPieces}) for target Tetris (${puzzle.targetTetris})`);
                    }
                }
                break;
                
            case 'perfect':
            case 'perfectclear':
                // Check if grid has correct number of blocks for perfect clear
                const blockCount = this.countBlocks(puzzle.initialGrid);
                if (blockCount % 4 !== 0) {
                    issues.push(`Grid has ${blockCount} blocks - not divisible by 4 for perfect clear`);
                }
                break;
        }
        
        return issues;
    }
    
    // Normalize puzzle to standard format
    normalizePuzzle(puzzle) {
        return {
            id: puzzle.id,
            name: puzzle.name,
            grid: puzzle.initialGrid || this.createEmptyGrid(),
            pieces: puzzle.pieces === 'random' ? null : puzzle.pieces,
            maxPieces: puzzle.maxPieces || 999,
            objective: {
                type: puzzle.objective,
                targetLines: puzzle.targetLines,
                targetScore: puzzle.minScore,
                targetTSpins: puzzle.targetTSpins,
                targetTetris: puzzle.targetTetris,
                targetCombo: puzzle.targetCombo
            },
            timeLimit: puzzle.timeLimit
        };
    }
    
    // Analyze why puzzle failed
    analyzeFailure(puzzle) {
        const reasons = [];
        
        // Check piece-objective mismatch
        if (puzzle.objective === 'tspin' && puzzle.pieces) {
            if (!puzzle.pieces.includes('T')) {
                reasons.push('T-spin objective without T pieces');
            }
        }
        
        if (puzzle.objective === 'tetris' && puzzle.pieces) {
            if (!puzzle.pieces.includes('I')) {
                reasons.push('Tetris objective without I pieces');
            }
        }
        
        // Check if pieces are insufficient
        if (puzzle.maxPieces) {
            const minRequired = this.calculateMinimumPieces(puzzle);
            if (puzzle.maxPieces < minRequired) {
                reasons.push(`Piece limit (${puzzle.maxPieces}) below minimum required (${minRequired})`);
            }
        }
        
        // Check for impossible grid configurations
        const gridIssues = this.checkGridFeasibility(puzzle.initialGrid);
        reasons.push(...gridIssues);
        
        return reasons;
    }
    
    // Check for warnings (puzzle works but has issues)
    checkForWarnings(puzzle, solution, analysis) {
        const warnings = [];
        
        // Difficulty warnings
        if (analysis.difficulty >= 4) {
            warnings.push(`Very high difficulty (${analysis.difficultyLabel})`);
        }
        
        // Efficiency warnings
        if (solution.moves.length > puzzle.maxPieces * 0.9) {
            warnings.push('Solution uses nearly all available pieces');
        }
        
        // Objective warnings
        if (puzzle.objective === 'perfect' && solution.linesCleared < 4) {
            warnings.push('Perfect clear with very few lines');
        }
        
        // Time warnings
        if (puzzle.timeLimit && analysis.estimatedTime) {
            if (analysis.estimatedTime.beginner > puzzle.timeLimit * 1000) {
                warnings.push('Time limit may be too strict for beginners');
            }
        }
        
        return warnings;
    }
    
    // Generate fix for impossible puzzle
    generateFix(puzzle, verificationResult) {
        const fix = {
            original: { ...puzzle },
            changes: [],
            fixed: { ...puzzle }
        };
        
        // Fix based on issues
        for (const issue of verificationResult.issues) {
            if (issue.includes('Not enough T pieces')) {
                // Add more T pieces
                const needed = puzzle.targetTSpins || 1;
                const current = puzzle.pieces.filter(p => p === 'T').length;
                for (let i = current; i < needed + 2; i++) {
                    fix.fixed.pieces.push('T');
                }
                fix.changes.push(`Added ${needed + 2 - current} T pieces`);
            }
            
            if (issue.includes('Not enough I pieces')) {
                // Add more I pieces
                const needed = puzzle.targetTetris || 1;
                const current = puzzle.pieces.filter(p => p === 'I').length;
                for (let i = current; i < needed + 1; i++) {
                    fix.fixed.pieces.push('I');
                }
                fix.changes.push(`Added ${needed + 1 - current} I pieces`);
            }
            
            if (issue.includes('Piece limit') && issue.includes('below minimum')) {
                // Increase piece limit
                const minRequired = this.calculateMinimumPieces(puzzle);
                fix.fixed.maxPieces = minRequired + 5;
                fix.changes.push(`Increased piece limit from ${puzzle.maxPieces} to ${fix.fixed.maxPieces}`);
            }
            
            if (issue.includes('Target lines') && issue.includes('exceeds maximum')) {
                // Reduce target lines
                const maxPossible = this.calculateMaxPossibleLines(puzzle);
                fix.fixed.targetLines = Math.max(1, maxPossible - 1);
                fix.changes.push(`Reduced target lines from ${puzzle.targetLines} to ${fix.fixed.targetLines}`);
            }
            
            if (issue.includes('not divisible by 4')) {
                // Adjust grid for perfect clear
                const blockCount = this.countBlocks(puzzle.initialGrid);
                const toRemove = blockCount % 4;
                fix.fixed.initialGrid = this.removeBlocks(puzzle.initialGrid, toRemove);
                fix.changes.push(`Removed ${toRemove} blocks for perfect clear compatibility`);
            }
        }
        
        // If no specific fixes, try general improvements
        if (fix.changes.length === 0) {
            if (puzzle.maxPieces && puzzle.maxPieces < 10) {
                fix.fixed.maxPieces = 15;
                fix.changes.push('Increased piece limit for better solvability');
            }
            
            if (puzzle.pieces && puzzle.pieces.length < 7) {
                fix.fixed.pieces = [...puzzle.pieces, 'I', 'T', 'O'];
                fix.changes.push('Added variety pieces for flexibility');
            }
        }
        
        return fix.changes.length > 0 ? fix : null;
    }
    
    // Helper functions
    calculateMaxPossibleLines(puzzle) {
        if (!puzzle.initialGrid) return 20;
        
        let maxLines = 0;
        for (let row = 0; row < 20; row++) {
            const filled = puzzle.initialGrid[row].filter(cell => cell !== 0).length;
            if (filled >= 7) { // Could potentially complete this line
                maxLines++;
            }
        }
        
        // Add potential from pieces
        if (puzzle.maxPieces) {
            maxLines += Math.floor(puzzle.maxPieces * 4 / 10); // Average 4 blocks per piece
        }
        
        return Math.min(20, maxLines);
    }
    
    calculateMinimumPieces(puzzle) {
        let minPieces = 0;
        
        switch (puzzle.objective) {
            case 'clear':
            case 'lines':
                // Minimum 2.5 pieces per line average
                minPieces = Math.ceil((puzzle.targetLines || 1) * 2.5);
                break;
                
            case 'tetris':
                // Need I pieces plus setup
                minPieces = (puzzle.targetTetris || 1) + 6;
                break;
                
            case 'tspin':
                // Need T pieces plus setup
                minPieces = (puzzle.targetTSpins || 1) * 3 + 4;
                break;
                
            case 'perfect':
                // Need exact pieces for clearing
                const blocks = this.countBlocks(puzzle.initialGrid);
                minPieces = Math.ceil(blocks / 4) + 2;
                break;
                
            default:
                minPieces = 5;
        }
        
        return minPieces;
    }
    
    checkGridFeasibility(grid) {
        const issues = [];
        
        if (!grid) return issues;
        
        // Check for completely blocked rows at top
        for (let row = 0; row < 4; row++) {
            if (grid[row].every(cell => cell !== 0)) {
                issues.push(`Row ${row} is completely filled at top - likely impossible`);
            }
        }
        
        // Check for isolated single blocks
        let isolatedBlocks = 0;
        for (let row = 1; row < 19; row++) {
            for (let col = 1; col < 9; col++) {
                if (grid[row][col] !== 0 &&
                    grid[row-1][col] === 0 && grid[row+1][col] === 0 &&
                    grid[row][col-1] === 0 && grid[row][col+1] === 0) {
                    isolatedBlocks++;
                }
            }
        }
        
        if (isolatedBlocks > 3) {
            issues.push(`${isolatedBlocks} isolated blocks make clearing very difficult`);
        }
        
        // Check for unreachable areas
        const unreachable = this.findUnreachableAreas(grid);
        if (unreachable > 0) {
            issues.push(`${unreachable} unreachable cells detected`);
        }
        
        return issues;
    }
    
    findUnreachableAreas(grid) {
        // Simplified check for cells that can't be reached by pieces
        let unreachable = 0;
        
        for (let row = 0; row < 20; row++) {
            for (let col = 0; col < 10; col++) {
                if (grid[row][col] === 0) {
                    // Check if surrounded by blocks on 3+ sides
                    let surroundedSides = 0;
                    
                    if (row > 0 && grid[row-1][col] !== 0) surroundedSides++;
                    if (row < 19 && grid[row+1][col] !== 0) surroundedSides++;
                    if (col > 0 && grid[row][col-1] !== 0) surroundedSides++;
                    if (col < 9 && grid[row][col+1] !== 0) surroundedSides++;
                    
                    if (surroundedSides >= 3) {
                        unreachable++;
                    }
                }
            }
        }
        
        return unreachable;
    }
    
    countBlocks(grid) {
        if (!grid) return 0;
        
        let count = 0;
        for (let row = 0; row < grid.length; row++) {
            for (let col = 0; col < grid[row].length; col++) {
                if (grid[row][col] !== 0) count++;
            }
        }
        return count;
    }
    
    removeBlocks(grid, count) {
        const newGrid = grid.map(row => [...row]);
        let removed = 0;
        
        // Remove from top rows first
        for (let row = 0; row < 20 && removed < count; row++) {
            for (let col = 0; col < 10 && removed < count; col++) {
                if (newGrid[row][col] !== 0) {
                    newGrid[row][col] = 0;
                    removed++;
                }
            }
        }
        
        return newGrid;
    }
    
    createEmptyGrid() {
        return Array(20).fill(null).map(() => Array(10).fill(0));
    }
    
    // Generate comprehensive report
    generateReport() {
        const totalPuzzles = this.results.length;
        const solvablePuzzles = this.results.filter(r => r.solvable).length;
        const impossiblePuzzles = this.results.filter(r => !r.solvable).length;
        
        // Difficulty distribution
        const difficultyDist = {
            1: 0, 2: 0, 3: 0, 4: 0, 5: 0
        };
        
        this.results.forEach(r => {
            if (r.difficulty) {
                difficultyDist[r.difficulty.difficulty]++;
            }
        });
        
        // Issues summary
        const allIssues = {};
        this.results.forEach(r => {
            r.issues.forEach(issue => {
                allIssues[issue] = (allIssues[issue] || 0) + 1;
            });
        });
        
        return {
            summary: {
                total: totalPuzzles,
                solvable: solvablePuzzles,
                impossible: impossiblePuzzles,
                successRate: `${((solvablePuzzles / totalPuzzles) * 100).toFixed(1)}%`
            },
            difficulty: difficultyDist,
            impossiblePuzzles: this.impossiblePuzzles,
            commonIssues: Object.entries(allIssues)
                .sort((a, b) => b[1] - a[1])
                .slice(0, 10),
            fixes: this.fixes,
            verificationTime: `${this.results.reduce((sum, r) => sum + r.time, 0)}ms`,
            timestamp: new Date().toISOString()
        };
    }
    
    // Save verification results
    async saveResults(report) {
        await storage.save('puzzle_verification_results', {
            report: report,
            results: this.results,
            fixes: this.fixes,
            timestamp: Date.now()
        });
        
        console.log('✅ Verification results saved');
    }
    
    // Apply fixes to puzzle data
    async applyFixes() {
        const fixedPuzzles = [];
        
        for (const [puzzleId, fix] of Object.entries(this.fixes)) {
            const puzzleIndex = PUZZLES.findIndex(p => p.id === parseInt(puzzleId));
            if (puzzleIndex >= 0) {
                // Apply fix
                Object.assign(PUZZLES[puzzleIndex], fix.fixed);
                fixedPuzzles.push(puzzleId);
                
                console.log(`✅ Fixed puzzle ${puzzleId}: ${fix.changes.join(', ')}`);
            }
        }
        
        console.log(`Applied ${fixedPuzzles.length} fixes`);
        
        return fixedPuzzles;
    }
}

// Export singleton instance
export const puzzleVerifier = new PuzzleVerificationSuite();