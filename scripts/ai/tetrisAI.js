// Tetris AI - Intelligent opponent for Battle Mode
import { GRID_WIDTH, GRID_HEIGHT } from '../grid.js';

export class TetrisAI {
    constructor(difficulty = 3) {
        this.difficulty = difficulty; // 1-5 (Easy to Grandmaster)
        this.grid = this.createEmptyGrid();
        this.currentPiece = null;
        this.nextPieces = [];
        this.score = 0;
        this.lines = 0;
        this.level = 1;
        this.garbageQueue = [];
        this.combo = 0;
        this.lastClearWasLine = false;
        this.recentMoves = [];
        
        // AI parameters based on difficulty
        this.setupDifficultyParams();
        
        // Decision making
        this.thinkingTime = 0;
        this.currentMove = null;
        this.moveQueue = [];
    }
    
    setupDifficultyParams() {
        switch (this.difficulty) {
            case 1: // Easy
                this.params = {
                    thinkingDelay: 800,
                    mistakeRate: 0.3,
                    lookAhead: 0,
                    evaluationWeights: {
                        height: -0.5,
                        holes: -3,
                        bumpiness: -0.2,
                        lines: 10,
                        wells: 0,
                        tspinSetup: 0,
                        tetrisWell: 0,
                        perfectClear: 0,
                        combo: 2
                    },
                    dropSpeed: 1000,
                    garbageDefenseSkill: 0.3
                };
                break;
                
            case 2: // Normal
                this.params = {
                    thinkingDelay: 500,
                    mistakeRate: 0.15,
                    lookAhead: 1,
                    evaluationWeights: {
                        height: -0.8,
                        holes: -5,
                        bumpiness: -0.5,
                        lines: 15,
                        wells: 0.5,
                        tspinSetup: 2,
                        tetrisWell: 1,
                        perfectClear: 10,
                        combo: 5
                    },
                    dropSpeed: 700,
                    garbageDefenseSkill: 0.5
                };
                break;
                
            case 3: // Hard
                this.params = {
                    thinkingDelay: 300,
                    mistakeRate: 0.08,
                    lookAhead: 1,
                    evaluationWeights: {
                        height: -1,
                        holes: -8,
                        bumpiness: -0.8,
                        lines: 20,
                        wells: 1,
                        tspinSetup: 5,
                        tetrisWell: 2,
                        perfectClear: 25,
                        combo: 8
                    },
                    dropSpeed: 400,
                    garbageDefenseSkill: 0.7
                };
                break;
                
            case 4: // Expert
                this.params = {
                    thinkingDelay: 150,
                    mistakeRate: 0.03,
                    lookAhead: 2,
                    evaluationWeights: {
                        height: -1.2,
                        holes: -10,
                        bumpiness: -1,
                        lines: 25,
                        wells: 2,
                        tspinSetup: 8,
                        tetrisWell: 3,
                        perfectClear: 40,
                        combo: 12
                    },
                    dropSpeed: 200,
                    garbageDefenseSkill: 0.85
                };
                break;
                
            case 5: // Grandmaster
                this.params = {
                    thinkingDelay: 50,
                    mistakeRate: 0.01,
                    lookAhead: 3,
                    evaluationWeights: {
                        height: -1.5,
                        holes: -15,
                        bumpiness: -1.2,
                        lines: 30,
                        wells: 3,
                        tspinSetup: 10,
                        tetrisWell: 5,
                        perfectClear: 50,
                        combo: 15
                    },
                    dropSpeed: 100,
                    garbageDefenseSkill: 0.95
                };
                break;
        }
    }
    
    createEmptyGrid() {
        return Array(GRID_HEIGHT).fill(null).map(() => Array(GRID_WIDTH).fill(0));
    }
    
    update(deltaTime) {
        this.thinkingTime += deltaTime;
        
        // Process garbage lines
        if (this.garbageQueue.length > 0 && Math.random() < this.params.garbageDefenseSkill) {
            this.processGarbage();
        }
        
        // Make decision if enough thinking time has passed
        if (this.thinkingTime >= this.params.thinkingDelay) {
            this.thinkingTime = 0;
            
            if (!this.currentMove && this.currentPiece) {
                this.decideBestMove();
            }
            
            if (this.currentMove) {
                this.executeNextMove();
            }
        }
    }
    
    setPiece(piece) {
        this.currentPiece = piece;
        this.currentMove = null;
        this.moveQueue = [];
    }
    
    decideBestMove() {
        if (!this.currentPiece) return;
        
        const moves = this.getAllPossibleMoves(this.currentPiece);
        let bestMove = null;
        let bestScore = -Infinity;
        
        for (let move of moves) {
            const score = this.evaluateMove(move);
            
            // Add randomness based on difficulty
            const randomFactor = (Math.random() - 0.5) * (5 - this.difficulty) * 10;
            const finalScore = score + randomFactor;
            
            if (finalScore > bestScore) {
                bestScore = finalScore;
                bestMove = move;
            }
        }
        
        // Chance to make a mistake
        if (Math.random() < this.params.mistakeRate) {
            bestMove = moves[Math.floor(Math.random() * moves.length)];
        }
        
        this.currentMove = bestMove;
        if (bestMove) {
            this.generateMoveSequence(bestMove);
        }
    }
    
    getAllPossibleMoves(piece) {
        const moves = [];
        const rotations = 4; // All possible rotations
        
        for (let rotation = 0; rotation < rotations; rotation++) {
            const rotatedPiece = this.rotatePiece(piece, rotation);
            
            for (let x = -2; x < GRID_WIDTH + 2; x++) {
                const move = {
                    x: x,
                    rotation: rotation,
                    piece: rotatedPiece
                };
                
                if (this.isValidPosition(rotatedPiece, x)) {
                    // Find drop position
                    move.y = this.getDropPosition(rotatedPiece, x);
                    moves.push(move);
                }
            }
        }
        
        return moves;
    }
    
    rotatePiece(piece, times) {
        let rotated = { ...piece };
        for (let i = 0; i < times; i++) {
            rotated = this.rotateMatrix(rotated);
        }
        return rotated;
    }
    
    rotateMatrix(piece) {
        // Simple rotation logic
        const matrix = piece.shape;
        const n = matrix.length;
        const rotated = Array(n).fill(null).map(() => Array(n).fill(0));
        
        for (let i = 0; i < n; i++) {
            for (let j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }
        
        return { ...piece, shape: rotated };
    }
    
    isValidPosition(piece, x, y = 0) {
        // Check if piece fits at position
        for (let row = 0; row < piece.shape.length; row++) {
            for (let col = 0; col < piece.shape[row].length; col++) {
                if (piece.shape[row][col]) {
                    const gridX = x + col;
                    const gridY = y + row;
                    
                    if (gridX < 0 || gridX >= GRID_WIDTH || 
                        gridY >= GRID_HEIGHT ||
                        (gridY >= 0 && this.grid[gridY][gridX])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    getDropPosition(piece, x) {
        let y = 0;
        while (this.isValidPosition(piece, x, y + 1)) {
            y++;
        }
        return y;
    }
    
    evaluateMove(move) {
        // Add y position if not set
        if (!move.y) {
            move.y = this.getDropPosition(move.piece, move.x);
        }
        
        // Simulate placing the piece
        const testGrid = this.cloneGrid();
        this.placePieceOnGrid(testGrid, move.piece, move.x, move.y);
        
        // Clear lines and track combos
        const linesCleared = this.clearLines(testGrid);
        
        // Calculate evaluation metrics - IMPROVED
        const height = this.calculateHeight(testGrid);
        const holes = this.calculateHoles(testGrid);
        const bumpiness = this.calculateBumpiness(testGrid);
        const wells = this.calculateWells(testGrid);
        
        // NEW: Calculate additional advanced metrics
        const deepHoles = this.calculateDeepHoles(testGrid);  // Holes covered by 3+ blocks
        const columnTransitions = this.calculateColumnTransitions(testGrid);  // Roughness
        const rowTransitions = this.calculateRowTransitions(testGrid);  // Horizontal gaps
        const landingHeight = move.y;  // Penalize high placements
        
        // Calculate base score with improved weights
        let score = 0;
        score += this.params.evaluationWeights.height * height;
        score += this.params.evaluationWeights.holes * holes * 1.5;  // Increased hole penalty
        score += this.params.evaluationWeights.bumpiness * bumpiness;
        score += this.params.evaluationWeights.lines * linesCleared * linesCleared;  // Quadratic bonus for multi-lines
        score += this.params.evaluationWeights.wells * wells;
        
        // Add advanced metrics
        score -= deepHoles * 20;  // Heavy penalty for deep holes
        score -= columnTransitions * 2;  // Penalty for roughness
        score -= rowTransitions * 2;  // Penalty for gaps
        score -= landingHeight * 0.5;  // Slight penalty for high placement
        
        // Combo tracking and bonus
        if (linesCleared > 0) {
            if (this.lastClearWasLine) {
                this.combo++;
                score += this.params.evaluationWeights.combo * this.combo * 10;
            } else {
                this.combo = 1;
            }
        } else {
            this.combo = 0;
        }
        
        // Advanced scoring for higher difficulties
        if (this.difficulty >= 3) {
            // T-Spin detection
            if (this.params.evaluationWeights.tspinSetup && move.piece.type === 'T') {
                const tSpinScore = this.detectTSpinSetup(testGrid, move);
                score += this.params.evaluationWeights.tspinSetup * tSpinScore;
            }
            
            // Tetris setup detection
            if (this.params.evaluationWeights.tetrisWell) {
                const tetrisScore = this.detectTetrisWell(testGrid);
                if (move.piece.type === 'I' && linesCleared === 4) {
                    score += this.params.evaluationWeights.tetrisWell * 100; // Big bonus for Tetris
                } else if (move.piece.type === 'I' && tetrisScore > 0) {
                    score -= 20; // Penalty for wasting I piece
                } else {
                    score += this.params.evaluationWeights.tetrisWell * tetrisScore * 10;
                }
            }
            
            // Perfect clear detection
            if (this.params.evaluationWeights.perfectClear && this.isPerfectClear(testGrid)) {
                score += this.params.evaluationWeights.perfectClear * 100;
            }
        }
        
        if (this.difficulty >= 5 && this.params.evaluationWeights.perfectClear) {
            if (this.isPerfectClear(testGrid)) {
                score += this.params.evaluationWeights.perfectClear;
            }
        }
        
        // Look ahead evaluation
        if (this.params.lookAhead > 0 && this.nextPieces.length > 0) {
            score += this.lookAheadEvaluation(testGrid, 0) * 0.5;
        }
        
        return score;
    }
    
    cloneGrid() {
        return this.grid.map(row => [...row]);
    }
    
    placePieceOnGrid(grid, piece, x, y) {
        for (let row = 0; row < piece.shape.length; row++) {
            for (let col = 0; col < piece.shape[row].length; col++) {
                if (piece.shape[row][col]) {
                    const gridY = y + row;
                    const gridX = x + col;
                    if (gridY >= 0 && gridY < GRID_HEIGHT && 
                        gridX >= 0 && gridX < GRID_WIDTH) {
                        grid[gridY][gridX] = piece.shape[row][col];
                    }
                }
            }
        }
    }
    
    clearLines(grid) {
        let linesCleared = 0;
        for (let row = GRID_HEIGHT - 1; row >= 0; row--) {
            if (grid[row].every(cell => cell !== 0)) {
                grid.splice(row, 1);
                grid.unshift(Array(GRID_WIDTH).fill(0));
                linesCleared++;
                row++; // Check same row again
            }
        }
        return linesCleared;
    }
    
    calculateHeight(grid) {
        for (let row = 0; row < GRID_HEIGHT; row++) {
            if (grid[row].some(cell => cell !== 0)) {
                return GRID_HEIGHT - row;
            }
        }
        return 0;
    }
    
    calculateHoles(grid) {
        let holes = 0;
        for (let col = 0; col < GRID_WIDTH; col++) {
            let blockFound = false;
            for (let row = 0; row < GRID_HEIGHT; row++) {
                if (grid[row][col] !== 0) {
                    blockFound = true;
                } else if (blockFound) {
                    holes++;
                }
            }
        }
        return holes;
    }
    
    calculateBumpiness(grid) {
        const heights = [];
        for (let col = 0; col < GRID_WIDTH; col++) {
            let height = 0;
            for (let row = 0; row < GRID_HEIGHT; row++) {
                if (grid[row][col] !== 0) {
                    height = GRID_HEIGHT - row;
                    break;
                }
            }
            heights.push(height);
        }
        
        let bumpiness = 0;
        for (let i = 0; i < heights.length - 1; i++) {
            bumpiness += Math.abs(heights[i] - heights[i + 1]);
        }
        return bumpiness;
    }
    
    calculateWells(grid) {
        let wells = 0;
        for (let col = 0; col < GRID_WIDTH; col++) {
            for (let row = 0; row < GRID_HEIGHT; row++) {
                if (grid[row][col] === 0) {
                    const leftFilled = col === 0 || grid[row][col - 1] !== 0;
                    const rightFilled = col === GRID_WIDTH - 1 || grid[row][col + 1] !== 0;
                    if (leftFilled && rightFilled) {
                        wells++;
                    }
                }
            }
        }
        return wells;
    }
    
    // NEW: Calculate deep holes (holes covered by 3+ blocks)
    calculateDeepHoles(grid) {
        let deepHoles = 0;
        for (let col = 0; col < GRID_WIDTH; col++) {
            let blocksAbove = 0;
            for (let row = 0; row < GRID_HEIGHT; row++) {
                if (grid[row][col] !== 0) {
                    blocksAbove++;
                } else if (blocksAbove > 0) {
                    // Found a hole
                    if (blocksAbove >= 3) {
                        deepHoles++;
                    }
                }
            }
        }
        return deepHoles;
    }
    
    // NEW: Calculate column transitions (changes from filled to empty)
    calculateColumnTransitions(grid) {
        let transitions = 0;
        for (let col = 0; col < GRID_WIDTH; col++) {
            for (let row = 1; row < GRID_HEIGHT; row++) {
                if ((grid[row][col] === 0) !== (grid[row - 1][col] === 0)) {
                    transitions++;
                }
            }
        }
        return transitions;
    }
    
    // NEW: Calculate row transitions (horizontal roughness)
    calculateRowTransitions(grid) {
        let transitions = 0;
        for (let row = 0; row < GRID_HEIGHT; row++) {
            for (let col = 1; col < GRID_WIDTH; col++) {
                if ((grid[row][col] === 0) !== (grid[row][col - 1] === 0)) {
                    transitions++;
                }
            }
        }
        return transitions;
    }
    
    detectTSpinSetup(grid, move) {
        if (!move.piece || move.piece.type !== 'T') return 0;
        
        const x = move.x;
        const y = move.y;
        
        // Check for T-spin setup patterns
        let tSpinScore = 0;
        
        // Check the four corners around the T piece center
        const corners = [
            { dx: -1, dy: -1 }, { dx: 1, dy: -1 },
            { dx: -1, dy: 1 }, { dx: 1, dy: 1 }
        ];
        
        let filledCorners = 0;
        const centerX = x + 1; // T piece center
        const centerY = y + 1;
        
        for (const corner of corners) {
            const cx = centerX + corner.dx;
            const cy = centerY + corner.dy;
            
            // Check bounds and filled cells
            if (cx < 0 || cx >= GRID_WIDTH || cy >= GRID_HEIGHT) {
                filledCorners++;
            } else if (cy >= 0 && grid[cy] && grid[cy][cx] !== 0) {
                filledCorners++;
            }
        }
        
        // T-spin requires at least 3 corners filled
        if (filledCorners >= 3) {
            tSpinScore = 20;
            
            // Check if this would clear lines (T-spin clear bonus)
            const testGrid = this.copyGrid(grid);
            this.placePieceOnGrid(testGrid, move.piece, move.x, move.y);
            const clearedLines = this.clearLines(testGrid);
            
            if (clearedLines > 0) {
                // T-spin single/double/triple bonus
                tSpinScore += clearedLines * 50;
            }
        }
        
        return tSpinScore;
    }
    
    copyGrid(grid) {
        return grid.map(row => [...row]);
    }
    
    detectTetrisWell(grid) {
        // Check for Tetris well setup (one deep column for I piece)
        let bestWellScore = 0;
        
        for (let col = 0; col < GRID_WIDTH; col++) {
            let emptyDepth = 0;
            
            // Count consecutive empty cells from top
            for (let row = GRID_HEIGHT - 1; row >= 0; row--) {
                if (grid[row][col] === 0) {
                    emptyDepth++;
                } else {
                    break;
                }
            }
            
            // Check if this forms a well (adjacent columns are higher)
            if (emptyDepth >= 4) {
                let isWell = true;
                const checkHeight = GRID_HEIGHT - emptyDepth;
                
                // Check left side
                if (col > 0) {
                    for (let row = checkHeight; row < checkHeight + 3 && row < GRID_HEIGHT; row++) {
                        if (grid[row][col - 1] === 0) {
                            isWell = false;
                            break;
                        }
                    }
                }
                
                // Check right side
                if (col < GRID_WIDTH - 1 && isWell) {
                    for (let row = checkHeight; row < checkHeight + 3 && row < GRID_HEIGHT; row++) {
                        if (grid[row][col + 1] === 0) {
                            isWell = false;
                            break;
                        }
                    }
                }
                
                if (isWell) {
                    // Score based on well depth (deeper = better for Tetris)
                    bestWellScore = Math.max(bestWellScore, Math.floor(emptyDepth / 4));
                }
            }
        }
        
        return bestWellScore;
    }
    
    isPerfectClear(grid) {
        return grid.every(row => row.every(cell => cell === 0));
    }
    
    lookAheadEvaluation(grid, depth) {
        if (depth >= this.params.lookAhead || depth >= this.nextPieces.length) {
            return 0;
        }
        // Simplified lookahead
        return 0;
    }
    
    generateMoveSequence(move) {
        this.moveQueue = [];
        
        // Optimize move sequence for faster execution
        const currentX = Math.floor(GRID_WIDTH / 2) - 1; // Starting position
        const targetX = move.x;
        const rotation = move.rotation;
        
        // For higher difficulties, use optimal pathfinding
        if (this.difficulty >= 4) {
            // Try different move orders to find the most efficient path
            const sequences = [];
            
            // Sequence 1: Rotate first, then move
            const seq1 = [];
            for (let i = 0; i < rotation; i++) seq1.push('rotate');
            const dir1 = targetX > currentX ? 'right' : 'left';
            for (let i = 0; i < Math.abs(targetX - currentX); i++) seq1.push(dir1);
            seq1.push('drop');
            sequences.push(seq1);
            
            // Sequence 2: Move partially, rotate, move rest (for wall kicks)
            if (rotation > 0) {
                const seq2 = [];
                const halfMove = Math.floor(Math.abs(targetX - currentX) / 2);
                const dir2 = targetX > currentX ? 'right' : 'left';
                for (let i = 0; i < halfMove; i++) seq2.push(dir2);
                for (let i = 0; i < rotation; i++) seq2.push('rotate');
                for (let i = halfMove; i < Math.abs(targetX - currentX); i++) seq2.push(dir2);
                seq2.push('drop');
                sequences.push(seq2);
            }
            
            // Choose shortest valid sequence
            this.moveQueue = sequences.reduce((best, seq) => 
                seq.length < best.length ? seq : best
            );
        } else {
            // Simple sequence for lower difficulties
            for (let i = 0; i < rotation; i++) {
                this.moveQueue.push('rotate');
            }
            
            const direction = targetX > currentX ? 'right' : 'left';
            const distance = Math.abs(targetX - currentX);
            
            for (let i = 0; i < distance; i++) {
                this.moveQueue.push(direction);
            }
            
            this.moveQueue.push('drop');
        }
        
        // Add finesse optimization for Grandmaster level
        if (this.difficulty === 5) {
            this.optimizeMoveFinesse();
        }
    }
    
    optimizeMoveFinesse() {
        // Remove redundant moves and optimize sequence
        const optimized = [];
        let consecutiveSame = 0;
        let lastMove = null;
        
        for (const move of this.moveQueue) {
            if (move === lastMove && move !== 'drop') {
                consecutiveSame++;
                if (consecutiveSame < 3) { // Limit consecutive same moves
                    optimized.push(move);
                }
            } else {
                consecutiveSame = 0;
                optimized.push(move);
                lastMove = move;
            }
        }
        
        this.moveQueue = optimized;
    }
    
    executeNextMove() {
        if (this.moveQueue.length === 0) {
            this.currentMove = null;
            return null;
        }
        
        return this.moveQueue.shift();
    }
    
    addGarbageLines(count) {
        this.garbageQueue.push(count);
    }
    
    processGarbage() {
        if (this.garbageQueue.length === 0) return;
        
        const lines = this.garbageQueue.shift();
        
        // Smart garbage handling based on difficulty
        if (this.difficulty >= 3) {
            // Try to clear garbage efficiently
            const garbageRows = [];
            for (let row = GRID_HEIGHT - 1; row >= GRID_HEIGHT - lines && row >= 0; row--) {
                if (this.grid[row].some(cell => cell !== 0)) {
                    garbageRows.push(row);
                }
            }
            
            // If we can clear some garbage with current piece, prioritize it
            if (garbageRows.length > 0 && this.currentPiece) {
                // Re-evaluate move with garbage consideration
                this.currentMove = null;
                this.moveQueue = [];
            }
        }
        
        // Add garbage lines to grid
        for (let i = 0; i < lines; i++) {
            this.grid.shift(); // Remove top row
            const garbageLine = Array(GRID_WIDTH).fill(8); // Gray blocks
            
            // Smart hole placement for counter-attack potential
            let hole;
            if (this.difficulty >= 4) {
                // Place hole strategically for easier clearing
                const columnHeights = this.getColumnHeights();
                const lowestCol = columnHeights.indexOf(Math.min(...columnHeights));
                hole = lowestCol;
            } else {
                hole = Math.floor(Math.random() * GRID_WIDTH);
            }
            
            garbageLine[hole] = 0;
            this.grid.push(garbageLine);
        }
    }
    
    getColumnHeights() {
        const heights = [];
        for (let col = 0; col < GRID_WIDTH; col++) {
            let height = 0;
            for (let row = 0; row < GRID_HEIGHT; row++) {
                if (this.grid[row][col] !== 0) {
                    height = GRID_HEIGHT - row;
                    break;
                }
            }
            heights.push(height);
        }
        return heights;
    }
    
    getStats() {
        return {
            score: this.score,
            lines: this.lines,
            level: this.level,
            difficulty: this.difficulty
        };
    }
    
    reset() {
        this.grid = this.createEmptyGrid();
        this.score = 0;
        this.lines = 0;
        this.level = 1;
        this.currentPiece = null;
        this.currentMove = null;
        this.moveQueue = [];
        this.garbageQueue = [];
        this.thinkingTime = 0;
        this.combo = 0;
        this.lastClearWasLine = false;
        this.recentMoves = [];
    }
}