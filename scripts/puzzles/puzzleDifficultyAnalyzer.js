// Puzzle Difficulty Analyzer
import { PuzzleAutoSolver } from './puzzleAutoSolver.js';

export class PuzzleDifficultyAnalyzer {
    constructor() {
        this.solver = new PuzzleAutoSolver();
        this.metrics = {};
    }
    
    // Analyze puzzle difficulty
    analyze(puzzle) {
        this.metrics = {
            // Basic metrics
            gridComplexity: this.analyzeGridComplexity(puzzle.grid),
            pieceConstraints: this.analyzePieceConstraints(puzzle),
            objectiveDifficulty: this.analyzeObjectiveDifficulty(puzzle),
            
            // Advanced metrics
            solutionComplexity: 0,
            minimumMoves: 0,
            optimalPath: null,
            alternativeSolutions: 0,
            
            // Skill requirements
            skillsRequired: [],
            techniques: [],
            
            // Time estimates
            estimatedTime: {
                beginner: 0,
                intermediate: 0,
                expert: 0
            },
            
            // Overall rating
            difficulty: 1,
            difficultyLabel: 'Easy',
            confidence: 0
        };
        
        // Run solver to get solution metrics
        const solution = this.solver.solve(puzzle);
        if (solution) {
            this.metrics.solutionComplexity = this.calculateSolutionComplexity(solution);
            this.metrics.minimumMoves = solution.moves.length;
            this.metrics.optimalPath = solution.moves;
        }
        
        // Calculate overall difficulty
        this.calculateOverallDifficulty();
        
        // Identify required skills
        this.identifyRequiredSkills(puzzle);
        
        // Estimate completion times
        this.estimateCompletionTimes();
        
        return this.metrics;
    }
    
    // Analyze grid complexity
    analyzeGridComplexity(grid) {
        if (!grid) return { score: 0, factors: [] };
        
        let score = 0;
        const factors = [];
        
        // Height of existing blocks
        const maxHeight = this.getMaxHeight(grid);
        score += maxHeight * 2;
        if (maxHeight > 10) factors.push('High starting blocks');
        
        // Number of holes
        const holes = this.countHoles(grid);
        score += holes * 5;
        if (holes > 3) factors.push(`${holes} holes to clear`);
        
        // Overhangs (blocks with empty space below)
        const overhangs = this.countOverhangs(grid);
        score += overhangs * 4;
        if (overhangs > 2) factors.push('Complex overhangs');
        
        // Wells (deep gaps)
        const wells = this.countWells(grid);
        score += wells * 6;
        if (wells > 0) factors.push(`${wells} well${wells > 1 ? 's' : ''}`);
        
        // Pattern complexity
        const patternScore = this.analyzePattern(grid);
        score += patternScore;
        if (patternScore > 20) factors.push('Complex pattern');
        
        // Accessibility (how easy to clear lines)
        const accessibility = this.analyzeAccessibility(grid);
        score += (100 - accessibility);
        if (accessibility < 50) factors.push('Difficult line access');
        
        return { score, factors };
    }
    
    // Analyze piece constraints
    analyzePieceConstraints(puzzle) {
        let score = 0;
        const factors = [];
        
        if (puzzle.pieces && puzzle.pieces !== 'random') {
            // Limited piece set
            const uniquePieces = new Set(puzzle.pieces).size;
            if (uniquePieces < 4) {
                score += 20;
                factors.push('Limited piece variety');
            }
            
            // Specific piece requirements
            if (puzzle.objective === 'tspin' && !puzzle.pieces.includes('T')) {
                score += 50;
                factors.push('T-Spin without T pieces!');
            }
            
            if (puzzle.objective === 'tetris' && !puzzle.pieces.includes('I')) {
                score += 40;
                factors.push('Tetris without I pieces!');
            }
        }
        
        // Piece limit constraints
        if (puzzle.maxPieces) {
            const efficiency = puzzle.maxPieces / (puzzle.targetLines || 1);
            if (efficiency < 3) {
                score += 30;
                factors.push('Very tight piece limit');
            } else if (efficiency < 5) {
                score += 15;
                factors.push('Tight piece limit');
            }
        }
        
        // Time constraints
        if (puzzle.timeLimit) {
            if (puzzle.timeLimit < 30) {
                score += 25;
                factors.push('Very short time limit');
            } else if (puzzle.timeLimit < 60) {
                score += 10;
                factors.push('Short time limit');
            }
        }
        
        return { score, factors };
    }
    
    // Analyze objective difficulty
    analyzeObjectiveDifficulty(puzzle) {
        let score = 0;
        const factors = [];
        
        const objectiveScores = {
            'clear': 10,
            'lines': 15,
            'score': 20,
            'tspin': 40,
            'tetris': 35,
            'combo': 30,
            'perfect': 50,
            'cascade': 35,
            'speed': 25,
            'norotation': 45,
            'chain': 40,
            'pattern': 30,
            'survival': 20
        };
        
        score = objectiveScores[puzzle.objective] || 15;
        
        // Adjust for target values
        if (puzzle.targetLines > 5) {
            score += (puzzle.targetLines - 5) * 2;
            factors.push(`Clear ${puzzle.targetLines} lines`);
        }
        
        if (puzzle.targetCombo > 5) {
            score += (puzzle.targetCombo - 5) * 5;
            factors.push(`${puzzle.targetCombo}x combo required`);
        }
        
        if (puzzle.targetTSpins > 1) {
            score += puzzle.targetTSpins * 15;
            factors.push(`${puzzle.targetTSpins} T-Spins required`);
        }
        
        return { score, factors };
    }
    
    // Calculate solution complexity
    calculateSolutionComplexity(solution) {
        if (!solution || !solution.moves) return 0;
        
        let complexity = 0;
        
        // Number of moves
        complexity += solution.moves.length * 2;
        
        // Rotation complexity
        const rotations = solution.moves.reduce((sum, move) => sum + move.rotation, 0);
        complexity += rotations * 1.5;
        
        // Placement precision (edges and corners)
        solution.moves.forEach(move => {
            if (move.column === 0 || move.column === 9) complexity += 2;
            if (move.row > 15) complexity += 3; // High placement
        });
        
        return complexity;
    }
    
    // Calculate overall difficulty
    calculateOverallDifficulty() {
        const gridScore = this.metrics.gridComplexity.score;
        const pieceScore = this.metrics.pieceConstraints.score;
        const objectiveScore = this.metrics.objectiveDifficulty.score;
        const solutionScore = this.metrics.solutionComplexity;
        
        const totalScore = gridScore + pieceScore + objectiveScore + solutionScore;
        
        // Determine difficulty level (1-5)
        if (totalScore < 30) {
            this.metrics.difficulty = 1;
            this.metrics.difficultyLabel = 'Easy';
        } else if (totalScore < 60) {
            this.metrics.difficulty = 2;
            this.metrics.difficultyLabel = 'Medium';
        } else if (totalScore < 100) {
            this.metrics.difficulty = 3;
            this.metrics.difficultyLabel = 'Hard';
        } else if (totalScore < 150) {
            this.metrics.difficulty = 4;
            this.metrics.difficultyLabel = 'Expert';
        } else {
            this.metrics.difficulty = 5;
            this.metrics.difficultyLabel = 'Master';
        }
        
        // Calculate confidence (0-100%)
        this.metrics.confidence = Math.min(100, 
            (this.metrics.optimalPath ? 50 : 0) +
            (gridScore > 0 ? 25 : 0) +
            (objectiveScore > 0 ? 25 : 0)
        );
    }
    
    // Identify required skills
    identifyRequiredSkills(puzzle) {
        const skills = [];
        const techniques = [];
        
        // Check objective requirements
        switch (puzzle.objective) {
            case 'tspin':
                skills.push('T-Spin Mastery');
                techniques.push('T-Spin Setup', '3-Corner Rule');
                break;
            case 'tetris':
                skills.push('Tetris Setup');
                techniques.push('Well Building', 'I-Piece Management');
                break;
            case 'combo':
                skills.push('Combo Building');
                techniques.push('Skimming', '4-Wide Strategy');
                break;
            case 'perfect':
                skills.push('Perfect Clear Knowledge');
                techniques.push('PC Patterns', 'Residue Management');
                break;
            case 'norotation':
                skills.push('Placement Precision');
                techniques.push('Column Selection', 'Drop Timing');
                break;
            case 'cascade':
                skills.push('Gravity Manipulation');
                techniques.push('Cascade Setup', 'Chain Reactions');
                break;
            case 'pattern':
                skills.push('Pattern Recognition');
                techniques.push('Shape Building', 'Symmetry');
                break;
        }
        
        // Check grid requirements
        if (this.metrics.gridComplexity.factors.includes('Complex overhangs')) {
            skills.push('Overhang Navigation');
            techniques.push('Tuck Unders', 'Slide Moves');
        }
        
        if (this.metrics.gridComplexity.factors.includes('wells')) {
            skills.push('Well Clearing');
            techniques.push('I-Piece Placement', 'Well Stacking');
        }
        
        // Check constraints
        if (puzzle.maxPieces && puzzle.maxPieces < 10) {
            skills.push('Efficiency Optimization');
            techniques.push('Minimal Moves', 'Multi-Line Clears');
        }
        
        if (puzzle.timeLimit && puzzle.timeLimit < 60) {
            skills.push('Speed Execution');
            techniques.push('Finesse', 'Quick Decision Making');
        }
        
        this.metrics.skillsRequired = skills;
        this.metrics.techniques = techniques;
    }
    
    // Estimate completion times
    estimateCompletionTimes() {
        const baseTime = this.metrics.minimumMoves * 2; // 2 seconds per move base
        
        // Adjust for difficulty
        const difficultyMultipliers = {
            1: { beginner: 3, intermediate: 1.5, expert: 1 },
            2: { beginner: 4, intermediate: 2, expert: 1.2 },
            3: { beginner: 6, intermediate: 3, expert: 1.5 },
            4: { beginner: 10, intermediate: 5, expert: 2 },
            5: { beginner: 15, intermediate: 8, expert: 3 }
        };
        
        const multipliers = difficultyMultipliers[this.metrics.difficulty];
        
        this.metrics.estimatedTime = {
            beginner: Math.round(baseTime * multipliers.beginner),
            intermediate: Math.round(baseTime * multipliers.intermediate),
            expert: Math.round(baseTime * multipliers.expert)
        };
    }
    
    // Helper functions
    getMaxHeight(grid) {
        for (let row = 0; row < 20; row++) {
            if (grid[row].some(cell => cell !== 0)) {
                return 20 - row;
            }
        }
        return 0;
    }
    
    countHoles(grid) {
        let holes = 0;
        for (let col = 0; col < 10; col++) {
            let blockFound = false;
            for (let row = 0; row < 20; row++) {
                if (grid[row][col] !== 0) {
                    blockFound = true;
                } else if (blockFound) {
                    holes++;
                }
            }
        }
        return holes;
    }
    
    countOverhangs(grid) {
        let overhangs = 0;
        for (let row = 0; row < 19; row++) {
            for (let col = 0; col < 10; col++) {
                if (grid[row][col] !== 0 && grid[row + 1][col] === 0) {
                    // Check if there's any block below in the column
                    for (let checkRow = row + 2; checkRow < 20; checkRow++) {
                        if (grid[checkRow][col] !== 0) {
                            overhangs++;
                            break;
                        }
                    }
                }
            }
        }
        return overhangs;
    }
    
    countWells(grid) {
        let wells = 0;
        for (let col = 1; col < 9; col++) {
            let wellDepth = 0;
            for (let row = 0; row < 20; row++) {
                if (grid[row][col] === 0 && 
                    grid[row][col - 1] !== 0 && 
                    grid[row][col + 1] !== 0) {
                    wellDepth++;
                } else {
                    if (wellDepth >= 3) wells++;
                    wellDepth = 0;
                }
            }
            if (wellDepth >= 3) wells++;
        }
        return wells;
    }
    
    analyzePattern(grid) {
        let score = 0;
        let transitions = 0;
        
        // Count transitions (changes from empty to filled)
        for (let row = 0; row < 20; row++) {
            for (let col = 0; col < 9; col++) {
                if ((grid[row][col] === 0) !== (grid[row][col + 1] === 0)) {
                    transitions++;
                }
            }
        }
        
        score = transitions * 2;
        return score;
    }
    
    analyzeAccessibility(grid) {
        // Percentage of how easy it is to clear lines
        let accessible = 0;
        let total = 0;
        
        for (let row = 0; row < 20; row++) {
            const filled = grid[row].filter(cell => cell !== 0).length;
            if (filled > 0) {
                total++;
                if (filled >= 7) accessible++; // Nearly complete lines
            }
        }
        
        return total > 0 ? (accessible / total) * 100 : 100;
    }
    
    // Generate difficulty report
    generateReport() {
        return {
            overall: {
                difficulty: this.metrics.difficulty,
                label: this.metrics.difficultyLabel,
                confidence: `${this.metrics.confidence}%`
            },
            breakdown: {
                grid: this.metrics.gridComplexity,
                pieces: this.metrics.pieceConstraints,
                objective: this.metrics.objectiveDifficulty
            },
            solution: {
                minimumMoves: this.metrics.minimumMoves,
                complexity: this.metrics.solutionComplexity,
                hasOptimalPath: !!this.metrics.optimalPath
            },
            requirements: {
                skills: this.metrics.skillsRequired,
                techniques: this.metrics.techniques
            },
            timeEstimates: this.metrics.estimatedTime,
            factors: [
                ...this.metrics.gridComplexity.factors,
                ...this.metrics.pieceConstraints.factors,
                ...this.metrics.objectiveDifficulty.factors
            ]
        };
    }
}