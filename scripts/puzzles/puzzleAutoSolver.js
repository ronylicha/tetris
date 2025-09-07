// Puzzle Auto-Solver with AI pathfinding
export class PuzzleAutoSolver {
    constructor() {
        this.maxDepth = 10; // Maximum search depth
        this.maxStates = 10000; // Maximum states to explore
        this.statesExplored = 0;
        this.solution = null;
    }
    
    // Main solve function
    solve(puzzle) {
        this.statesExplored = 0;
        this.solution = null;
        
        // Convert puzzle to initial state
        const initialState = {
            grid: this.copyGrid(puzzle.grid || this.createEmptyGrid()),
            pieces: puzzle.pieces === 'random' ? this.generateRandomPieces(7) : [...puzzle.pieces],
            pieceIndex: 0,
            moves: [],
            linesCleared: 0,
            score: 0,
            objective: puzzle.objective,
            targetValue: this.getTargetValue(puzzle)
        };
        
        // Use iterative deepening DFS with alpha-beta pruning
        for (let depth = 1; depth <= this.maxDepth && !this.solution; depth++) {
            this.depthFirstSearch(initialState, depth, 0);
            
            if (this.statesExplored > this.maxStates) {
                break;
            }
        }
        
        return this.solution;
    }
    
    // Depth-first search with pruning
    depthFirstSearch(state, maxDepth, currentDepth) {
        if (this.solution || this.statesExplored > this.maxStates) {
            return;
        }
        
        this.statesExplored++;
        
        // Check if objective is met
        if (this.checkObjective(state)) {
            this.solution = {
                moves: state.moves,
                finalGrid: state.grid,
                linesCleared: state.linesCleared,
                piecesUsed: state.pieceIndex,
                score: state.score,
                success: true
            };
            return;
        }
        
        // Check if we've reached max depth or run out of pieces
        if (currentDepth >= maxDepth || state.pieceIndex >= state.pieces.length) {
            return;
        }
        
        // Get current piece
        const pieceType = state.pieces[state.pieceIndex];
        const piece = this.createPiece(pieceType);
        
        // Generate all possible placements
        const placements = this.generatePlacements(state.grid, piece);
        
        // Sort placements by heuristic score
        placements.sort((a, b) => this.evaluatePlacement(b, state) - this.evaluatePlacement(a, state));
        
        // Try top placements
        const topPlacements = placements.slice(0, Math.min(5, placements.length));
        
        for (const placement of topPlacements) {
            // Create new state
            const newState = this.applyPlacement(state, placement);
            
            // Recursively search
            this.depthFirstSearch(newState, maxDepth, currentDepth + 1);
            
            if (this.solution) {
                return;
            }
        }
    }
    
    // Generate all possible placements for a piece
    generatePlacements(grid, piece) {
        const placements = [];
        
        // Try all rotations
        for (let rotation = 0; rotation < 4; rotation++) {
            const rotatedPiece = this.rotatePiece(piece, rotation);
            
            // Try all columns
            for (let col = 0; col <= 10 - rotatedPiece[0].length; col++) {
                // Find drop position
                let row = 0;
                while (row < 20 && this.canPlace(grid, rotatedPiece, row + 1, col)) {
                    row++;
                }
                
                if (this.canPlace(grid, rotatedPiece, row, col)) {
                    placements.push({
                        piece: rotatedPiece,
                        row: row,
                        col: col,
                        rotation: rotation
                    });
                }
            }
        }
        
        return placements;
    }
    
    // Check if piece can be placed at position
    canPlace(grid, piece, row, col) {
        for (let r = 0; r < piece.length; r++) {
            for (let c = 0; c < piece[r].length; c++) {
                if (piece[r][c] !== 0) {
                    const gridRow = row + r;
                    const gridCol = col + c;
                    
                    if (gridRow < 0 || gridRow >= 20 || 
                        gridCol < 0 || gridCol >= 10 ||
                        grid[gridRow][gridCol] !== 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    // Apply placement and return new state
    applyPlacement(state, placement) {
        const newGrid = this.copyGrid(state.grid);
        
        // Place piece
        for (let r = 0; r < placement.piece.length; r++) {
            for (let c = 0; c < placement.piece[r].length; c++) {
                if (placement.piece[r][c] !== 0) {
                    newGrid[placement.row + r][placement.col + c] = placement.piece[r][c];
                }
            }
        }
        
        // Clear lines
        const clearedLines = this.clearLines(newGrid);
        
        // Create new state
        return {
            grid: newGrid,
            pieces: state.pieces,
            pieceIndex: state.pieceIndex + 1,
            moves: [...state.moves, {
                piece: state.pieces[state.pieceIndex],
                rotation: placement.rotation,
                column: placement.col,
                row: placement.row
            }],
            linesCleared: state.linesCleared + clearedLines.length,
            score: state.score + this.calculateScore(clearedLines.length),
            objective: state.objective,
            targetValue: state.targetValue
        };
    }
    
    // Evaluate placement quality
    evaluatePlacement(placement, state) {
        let score = 0;
        
        // Prefer lower placements
        score -= placement.row * 10;
        
        // Check for line clears
        const testGrid = this.copyGrid(state.grid);
        for (let r = 0; r < placement.piece.length; r++) {
            for (let c = 0; c < placement.piece[r].length; c++) {
                if (placement.piece[r][c] !== 0) {
                    testGrid[placement.row + r][placement.col + c] = placement.piece[r][c];
                }
            }
        }
        
        const clearedLines = this.clearLines(testGrid);
        score += clearedLines.length * 100;
        
        // Objective-specific scoring
        switch (state.objective.type) {
            case 'lines':
            case 'clear':
                score += clearedLines.length * 200;
                break;
                
            case 'tspin':
                if (this.isTSpin(placement, state.grid)) {
                    score += 500;
                }
                break;
                
            case 'tetris':
                if (clearedLines.length === 4) {
                    score += 1000;
                }
                break;
                
            case 'perfect':
                if (this.isPerfectClear(testGrid)) {
                    score += 2000;
                }
                break;
        }
        
        // Penalize holes
        score -= this.countHoles(testGrid) * 30;
        
        // Penalize height
        score -= this.getMaxHeight(testGrid) * 5;
        
        return score;
    }
    
    // Check if objective is met
    checkObjective(state) {
        switch (state.objective.type) {
            case 'lines':
                return state.linesCleared >= state.targetValue;
                
            case 'clear':
                return this.isGridEmpty(state.grid);
                
            case 'score':
                return state.score >= state.targetValue;
                
            case 'perfect':
                return this.isPerfectClear(state.grid);
                
            default:
                return false;
        }
    }
    
    // Detect T-Spin
    isTSpin(placement, grid) {
        // Simplified T-Spin detection
        const piece = placement.piece;
        const row = placement.row;
        const col = placement.col;
        
        // Check if it's a T piece
        if (!this.isTShape(piece)) return false;
        
        // Check corners for T-Spin pattern
        let filledCorners = 0;
        const corners = [
            [row, col],
            [row, col + 2],
            [row + 2, col],
            [row + 2, col + 2]
        ];
        
        for (const [r, c] of corners) {
            if (r >= 0 && r < 20 && c >= 0 && c < 10 && grid[r][c] !== 0) {
                filledCorners++;
            }
        }
        
        return filledCorners >= 3;
    }
    
    // Check if piece is T-shaped
    isTShape(piece) {
        // Simple check for T-piece pattern
        const flatPiece = piece.flat().filter(cell => cell !== 0);
        return flatPiece.length === 4; // T-piece has 4 blocks
    }
    
    // Clear complete lines
    clearLines(grid) {
        const clearedLines = [];
        
        for (let row = 19; row >= 0; row--) {
            if (grid[row].every(cell => cell !== 0)) {
                clearedLines.push(row);
                grid.splice(row, 1);
                grid.unshift(new Array(10).fill(0));
                row++; // Check same row again
            }
        }
        
        return clearedLines;
    }
    
    // Count holes in grid
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
    
    // Get maximum height
    getMaxHeight(grid) {
        for (let row = 0; row < 20; row++) {
            if (grid[row].some(cell => cell !== 0)) {
                return 20 - row;
            }
        }
        return 0;
    }
    
    // Check if grid is empty
    isGridEmpty(grid) {
        return grid.every(row => row.every(cell => cell === 0));
    }
    
    // Check for perfect clear
    isPerfectClear(grid) {
        return this.isGridEmpty(grid);
    }
    
    // Calculate score for lines cleared
    calculateScore(lines) {
        const scores = [0, 100, 300, 500, 800];
        return scores[Math.min(lines, 4)];
    }
    
    // Utility functions
    copyGrid(grid) {
        return grid.map(row => [...row]);
    }
    
    createEmptyGrid() {
        return Array(20).fill(null).map(() => Array(10).fill(0));
    }
    
    createPiece(type) {
        const pieces = {
            'I': [[1,1,1,1]],
            'O': [[1,1],[1,1]],
            'T': [[0,1,0],[1,1,1]],
            'S': [[0,1,1],[1,1,0]],
            'Z': [[1,1,0],[0,1,1]],
            'J': [[1,0,0],[1,1,1]],
            'L': [[0,0,1],[1,1,1]]
        };
        return pieces[type] || pieces['T'];
    }
    
    rotatePiece(piece, times) {
        let rotated = piece;
        for (let i = 0; i < times; i++) {
            rotated = this.rotate90(rotated);
        }
        return rotated;
    }
    
    rotate90(matrix) {
        const n = matrix.length;
        const m = matrix[0].length;
        const rotated = Array(m).fill(null).map(() => Array(n).fill(0));
        
        for (let i = 0; i < n; i++) {
            for (let j = 0; j < m; j++) {
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }
        
        return rotated;
    }
    
    generateRandomPieces(count) {
        const types = ['I', 'O', 'T', 'S', 'Z', 'J', 'L'];
        const pieces = [];
        for (let i = 0; i < count; i++) {
            pieces.push(types[Math.floor(Math.random() * types.length)]);
        }
        return pieces;
    }
    
    getTargetValue(puzzle) {
        return puzzle.targetLines || puzzle.targetScore || 
               puzzle.targetTSpins || puzzle.targetTetris || 
               puzzle.targetCombo || 1;
    }
}