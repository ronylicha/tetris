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
                        wells: 0
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
                        wells: 0.5
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
                        tspinSetup: 5
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
                        tetrisWell: 3
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
                        perfectClear: 50
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
        // Simulate placing the piece
        const testGrid = this.cloneGrid();
        this.placePieceOnGrid(testGrid, move.piece, move.x, move.y);
        
        // Clear lines
        const linesCleared = this.clearLines(testGrid);
        
        // Calculate evaluation metrics
        const height = this.calculateHeight(testGrid);
        const holes = this.calculateHoles(testGrid);
        const bumpiness = this.calculateBumpiness(testGrid);
        const wells = this.calculateWells(testGrid);
        
        // Calculate score
        let score = 0;
        score += this.params.evaluationWeights.height * height;
        score += this.params.evaluationWeights.holes * holes;
        score += this.params.evaluationWeights.bumpiness * bumpiness;
        score += this.params.evaluationWeights.lines * linesCleared;
        score += this.params.evaluationWeights.wells * wells;
        
        // Advanced scoring for higher difficulties
        if (this.difficulty >= 3) {
            if (this.params.evaluationWeights.tspinSetup) {
                score += this.params.evaluationWeights.tspinSetup * 
                         this.detectTSpinSetup(testGrid, move);
            }
            
            if (this.params.evaluationWeights.tetrisWell) {
                score += this.params.evaluationWeights.tetrisWell * 
                         this.detectTetrisWell(testGrid);
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
    
    detectTSpinSetup(grid, move) {
        // Simplified T-spin detection
        if (move.piece.type === 'T') {
            // Check for T-spin slot
            return 0; // Simplified for now
        }
        return 0;
    }
    
    detectTetrisWell(grid) {
        // Check if rightmost column is empty (common Tetris setup)
        let rightColumnEmpty = true;
        for (let row = 0; row < GRID_HEIGHT; row++) {
            if (grid[row][GRID_WIDTH - 1] !== 0) {
                rightColumnEmpty = false;
                break;
            }
        }
        return rightColumnEmpty ? 1 : 0;
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
        
        // Add rotation moves
        for (let i = 0; i < move.rotation; i++) {
            this.moveQueue.push('rotate');
        }
        
        // Add horizontal moves
        const currentX = Math.floor(GRID_WIDTH / 2) - 1; // Starting position
        const targetX = move.x;
        const direction = targetX > currentX ? 'right' : 'left';
        const distance = Math.abs(targetX - currentX);
        
        for (let i = 0; i < distance; i++) {
            this.moveQueue.push(direction);
        }
        
        // Add drop
        this.moveQueue.push('drop');
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
        
        // Add garbage lines to grid
        for (let i = 0; i < lines; i++) {
            this.grid.shift(); // Remove top row
            const garbageLine = Array(GRID_WIDTH).fill(1);
            // Add one hole
            const hole = Math.floor(Math.random() * GRID_WIDTH);
            garbageLine[hole] = 0;
            this.grid.push(garbageLine);
        }
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
    }
}