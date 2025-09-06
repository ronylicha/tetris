// Modern Tetris - Grid Management and Collision Detection

export const GRID_WIDTH = 10;
export const GRID_HEIGHT = 20;
export const GRID_HIDDEN_ROWS = 4; // Hidden rows above visible area

export class Grid {
    constructor() {
        this.width = GRID_WIDTH;
        this.height = GRID_HEIGHT + GRID_HIDDEN_ROWS;
        this.cells = this.createEmptyGrid();
        this.linesCleared = 0;
        this.totalLines = 0;
    }

    // Create empty grid
    createEmptyGrid() {
        return Array(this.height).fill(null).map(() => 
            Array(this.width).fill(null)
        );
    }

    // Check if coordinates are within grid bounds
    isInBounds(x, y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    // Check if cell is occupied
    isOccupied(x, y) {
        if (!this.isInBounds(x, y)) return true; // Out of bounds = occupied
        return this.cells[y][x] !== null;
    }

    // Get cell value
    getCell(x, y) {
        if (!this.isInBounds(x, y)) return null;
        return this.cells[y][x];
    }

    // Set cell value
    setCell(x, y, value) {
        if (this.isInBounds(x, y)) {
            this.cells[y][x] = value;
        }
    }

    // Check collision with piece
    checkCollision(piece) {
        const blocks = piece.getBlocks();
        
        for (const block of blocks) {
            if (this.isOccupied(block.x, block.y)) {
                return true;
            }
        }
        
        return false;
    }

    // Place piece on grid
    placePiece(piece) {
        const blocks = piece.getBlocks();
        
        for (const block of blocks) {
            if (this.isInBounds(block.x, block.y)) {
                this.cells[block.y][block.x] = {
                    color: block.color,
                    type: piece.type
                };
            }
        }
    }

    // Check for completed lines
    getCompletedLines() {
        const completedLines = [];
        
        for (let y = 0; y < this.height; y++) {
            if (this.isLineFull(y)) {
                completedLines.push(y);
            }
        }
        
        console.log(`Found ${completedLines.length} completed lines:`, completedLines);
        return completedLines;
    }

    // Check if line is full
    isLineFull(y) {
        if (!this.isInBounds(0, y)) return false;
        
        let filledCells = 0;
        for (let x = 0; x < this.width; x++) {
            if (this.cells[y][x] !== null) {
                filledCells++;
            }
        }
        
        const isFull = filledCells === this.width;
        if (isFull) {
            console.log(`Line ${y} is full (${filledCells}/${this.width} cells filled)`);
        }
        
        return isFull;
    }

    // Clear completed lines and return line count
    clearLines(linesToClear = null) {
        let completedLines = linesToClear || this.getCompletedLines();
        
        if (completedLines.length === 0) return 0;
        
        console.log(`Clearing ${completedLines.length} lines:`, completedLines);
        console.log(`Grid height before clearing: ${this.cells.length}`);
        
        // Create a new grid without the completed lines
        const newCells = [];
        const originalExpectedCount = completedLines.length;
        let actualClearedCount = 0;
        
        // Add empty lines at the top for each cleared line
        for (let i = 0; i < completedLines.length; i++) {
            newCells.push(Array(this.width).fill(null));
        }
        
        // Copy all non-completed lines to the new grid
        for (let y = 0; y < this.height; y++) {
            if (!completedLines.includes(y)) {
                newCells.push([...this.cells[y]]);
            } else {
                actualClearedCount++;
                console.log(`Line ${y} is being cleared`);
            }
        }
        
        console.log(`Built new grid with ${newCells.length} rows`);
        console.log(`Cleared ${actualClearedCount} lines (expected ${originalExpectedCount})`);
        
        // Replace the old grid with the new one
        this.cells = newCells;
        
        console.log(`Grid height after clearing: ${this.cells.length}`);
        
        this.linesCleared = actualClearedCount;
        this.totalLines += actualClearedCount;
        
        console.log(`Total lines cleared: ${actualClearedCount} (expected: ${originalExpectedCount})`);
        return actualClearedCount;
    }

    // Get line clearing info for animations
    getLineClearInfo() {
        const completedLines = this.getCompletedLines();
        return {
            lines: completedLines,
            count: completedLines.length,
            isSpecial: this.isSpecialClear(completedLines.length)
        };
    }

    // Check for special clears
    isSpecialClear(lineCount) {
        switch (lineCount) {
            case 1: return 'single';
            case 2: return 'double';
            case 3: return 'triple';
            case 4: return 'tetris';
            default: return null;
        }
    }

    // Check for perfect clear (empty grid)
    isPerfectClear() {
        for (let y = GRID_HIDDEN_ROWS; y < this.height; y++) {
            for (let x = 0; x < this.width; x++) {
                if (this.cells[y][x] !== null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Get visible grid (without hidden rows)
    getVisibleGrid() {
        return this.cells.slice(GRID_HIDDEN_ROWS);
    }

    // Check game over condition
    isGameOver() {
        // Check if any blocks are above the visible area
        for (let y = 0; y < GRID_HIDDEN_ROWS; y++) {
            for (let x = 0; x < this.width; x++) {
                if (this.cells[y][x] !== null) {
                    return true;
                }
            }
        }
        return false;
    }

    // Reset grid
    reset() {
        this.cells = this.createEmptyGrid();
        this.linesCleared = 0;
        this.totalLines = 0;
    }

    // Get grid state for rendering
    getRenderData() {
        const visibleGrid = this.getVisibleGrid();
        const renderData = [];
        
        for (let y = 0; y < visibleGrid.length; y++) {
            for (let x = 0; x < this.width; x++) {
                const cell = visibleGrid[y][x];
                if (cell !== null) {
                    renderData.push({
                        x,
                        y,
                        color: cell.color,
                        type: cell.type
                    });
                }
            }
        }
        
        return renderData;
    }

    // Test piece movement (returns valid position or null)
    testPieceMovement(piece, dx, dy) {
        const testPiece = piece.copy();
        testPiece.move(dx, dy);
        
        return this.checkCollision(testPiece) ? null : testPiece;
    }

    // Test piece rotation with wall kicks
    testPieceRotation(piece, direction = 1) {
        const newRotation = piece.getRotatedState(direction);
        const testPiece = piece.copy();
        testPiece.rotation = newRotation;
        
        // Get wall kick tests
        const wallKicks = this.getWallKicks(piece, direction);
        
        // Test original position first
        if (!this.checkCollision(testPiece)) {
            return testPiece;
        }
        
        // Try wall kicks
        for (const kick of wallKicks) {
            const kickTestPiece = testPiece.copy();
            kickTestPiece.x += kick[0];
            kickTestPiece.y += kick[1];
            
            if (!this.checkCollision(kickTestPiece)) {
                return kickTestPiece;
            }
        }
        
        return null; // Rotation not possible
    }

    // Get wall kick tests for rotation
    getWallKicks(piece, direction) {
        // Import WALL_KICKS synchronously from pieces module
        // This will be handled by the game engine which has already imported the module
        
        const from = piece.rotation;
        const to = piece.getRotatedState(direction);
        const kickKey = `${from}->${to}`;
        
        // Wall kick data defined here to avoid async import issues
        const WALL_KICKS = {
            JLSTZ: {
                '0->1': [[-1, 0], [-1, 1], [0, -2], [-1, -2]],
                '1->0': [[1, 0], [1, -1], [0, 2], [1, 2]],
                '1->2': [[1, 0], [1, -1], [0, 2], [1, 2]],
                '2->1': [[-1, 0], [-1, 1], [0, -2], [-1, -2]],
                '2->3': [[1, 0], [1, 1], [0, -2], [1, -2]],
                '3->2': [[-1, 0], [-1, -1], [0, 2], [-1, 2]],
                '3->0': [[-1, 0], [-1, -1], [0, 2], [-1, 2]],
                '0->3': [[1, 0], [1, 1], [0, -2], [1, -2]]
            },
            I: {
                '0->1': [[-2, 0], [1, 0], [-2, -1], [1, 2]],
                '1->0': [[2, 0], [-1, 0], [2, 1], [-1, -2]],
                '1->2': [[-1, 0], [2, 0], [-1, 2], [2, -1]],
                '2->1': [[1, 0], [-2, 0], [1, -2], [-2, 1]],
                '2->3': [[2, 0], [-1, 0], [2, 1], [-1, -2]],
                '3->2': [[-2, 0], [1, 0], [-2, -1], [1, 2]],
                '3->0': [[1, 0], [-2, 0], [1, -2], [-2, 1]],
                '0->3': [[-1, 0], [2, 0], [-1, 2], [2, -1]]
            }
        };
        
        if (piece.type === 'I') {
            return WALL_KICKS.I[kickKey] || [];
        } else if (piece.type === 'O') {
            return []; // O piece doesn't need wall kicks
        } else {
            return WALL_KICKS.JLSTZ[kickKey] || [];
        }
    }

    // Get ghost piece position (hard drop preview)
    getGhostPosition(piece) {
        const ghostPiece = piece.copy();
        
        // Move down until collision
        while (!this.checkCollision(ghostPiece)) {
            ghostPiece.y += 1;
        }
        
        // Move back one step
        ghostPiece.y -= 1;
        
        return ghostPiece;
    }

    // Calculate drop distance for scoring
    getDropDistance(fromY, toY) {
        return Math.max(0, toY - fromY);
    }

    // Get grid statistics
    getStats() {
        let occupiedCells = 0;
        let highestRow = this.height;
        
        for (let y = GRID_HIDDEN_ROWS; y < this.height; y++) {
            let rowHasCells = false;
            for (let x = 0; x < this.width; x++) {
                if (this.cells[y][x] !== null) {
                    occupiedCells++;
                    rowHasCells = true;
                }
            }
            if (rowHasCells && y < highestRow) {
                highestRow = y;
            }
        }
        
        return {
            occupiedCells,
            height: highestRow === this.height ? 0 : (this.height - highestRow),
            totalLines: this.totalLines,
            isEmpty: occupiedCells === 0
        };
    }

    // Debug: Print grid state to console
    debugPrintGrid() {
        console.log('=== GRID STATE ===');
        for (let y = GRID_HIDDEN_ROWS; y < this.height; y++) {
            let line = `${y.toString().padStart(2, '0')}: `;
            for (let x = 0; x < this.width; x++) {
                line += this.cells[y][x] !== null ? '■' : '·';
            }
            console.log(line);
        }
        console.log('==================');
    }

    // Deep copy grid state
    copy() {
        const newGrid = new Grid();
        newGrid.cells = this.cells.map(row => [...row]);
        newGrid.linesCleared = this.linesCleared;
        newGrid.totalLines = this.totalLines;
        return newGrid;
    }
}