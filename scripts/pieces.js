// Modern Tetris - Piece Definitions and Logic

export const PIECE_TYPES = {
    I: 'I', O: 'O', T: 'T', S: 'S', Z: 'Z', J: 'J', L: 'L'
};

export const PIECE_COLORS = {
    I: '#00d4ff',  // Neon Blue
    O: '#ffff00',  // Neon Yellow
    T: '#9d4edd',  // Neon Purple
    S: '#39ff14',  // Neon Green
    Z: '#ff0040',  // Neon Red
    J: '#0066ff',  // Blue
    L: '#ff8500'   // Neon Orange
};

// SRS (Super Rotation System) piece definitions
// Each piece has 4 rotation states (0, 1, 2, 3)
export const PIECE_SHAPES = {
    I: [
        [[0,0,0,0], [1,1,1,1], [0,0,0,0], [0,0,0,0]], // 0°
        [[0,0,1,0], [0,0,1,0], [0,0,1,0], [0,0,1,0]], // 90°
        [[0,0,0,0], [0,0,0,0], [1,1,1,1], [0,0,0,0]], // 180°
        [[0,1,0,0], [0,1,0,0], [0,1,0,0], [0,1,0,0]]  // 270°
    ],
    O: [
        [[0,1,1,0], [0,1,1,0], [0,0,0,0], [0,0,0,0]], // All rotations same
        [[0,1,1,0], [0,1,1,0], [0,0,0,0], [0,0,0,0]],
        [[0,1,1,0], [0,1,1,0], [0,0,0,0], [0,0,0,0]],
        [[0,1,1,0], [0,1,1,0], [0,0,0,0], [0,0,0,0]]
    ],
    T: [
        [[0,1,0,0], [1,1,1,0], [0,0,0,0], [0,0,0,0]], // 0°
        [[0,1,0,0], [0,1,1,0], [0,1,0,0], [0,0,0,0]], // 90°
        [[0,0,0,0], [1,1,1,0], [0,1,0,0], [0,0,0,0]], // 180°
        [[0,1,0,0], [1,1,0,0], [0,1,0,0], [0,0,0,0]]  // 270°
    ],
    S: [
        [[0,1,1,0], [1,1,0,0], [0,0,0,0], [0,0,0,0]], // 0°
        [[0,1,0,0], [0,1,1,0], [0,0,1,0], [0,0,0,0]], // 90°
        [[0,0,0,0], [0,1,1,0], [1,1,0,0], [0,0,0,0]], // 180°
        [[1,0,0,0], [1,1,0,0], [0,1,0,0], [0,0,0,0]]  // 270°
    ],
    Z: [
        [[1,1,0,0], [0,1,1,0], [0,0,0,0], [0,0,0,0]], // 0°
        [[0,0,1,0], [0,1,1,0], [0,1,0,0], [0,0,0,0]], // 90°
        [[0,0,0,0], [1,1,0,0], [0,1,1,0], [0,0,0,0]], // 180°
        [[0,1,0,0], [1,1,0,0], [1,0,0,0], [0,0,0,0]]  // 270°
    ],
    J: [
        [[1,0,0,0], [1,1,1,0], [0,0,0,0], [0,0,0,0]], // 0°
        [[0,1,1,0], [0,1,0,0], [0,1,0,0], [0,0,0,0]], // 90°
        [[0,0,0,0], [1,1,1,0], [0,0,1,0], [0,0,0,0]], // 180°
        [[0,1,0,0], [0,1,0,0], [1,1,0,0], [0,0,0,0]]  // 270°
    ],
    L: [
        [[0,0,1,0], [1,1,1,0], [0,0,0,0], [0,0,0,0]], // 0°
        [[0,1,0,0], [0,1,0,0], [0,1,1,0], [0,0,0,0]], // 90°
        [[0,0,0,0], [1,1,1,0], [1,0,0,0], [0,0,0,0]], // 180°
        [[1,1,0,0], [0,1,0,0], [0,1,0,0], [0,0,0,0]]  // 270°
    ]
};

// SRS Wall Kick Data
export const WALL_KICKS = {
    // Standard pieces (J, L, S, T, Z)
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
    // I piece has different wall kicks
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

export class Piece {
    constructor(type, x = 3, y = 0) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.rotation = 0;
        this.color = PIECE_COLORS[type];
        this.shape = PIECE_SHAPES[type];
        this.lockDelay = 0;
        this.maxLockDelay = 500; // 500ms lock delay
        this.moved = false;
    }

    // Get current shape based on rotation
    getCurrentShape() {
        return this.shape[this.rotation];
    }

    // Get piece blocks with absolute positions
    getBlocks() {
        const blocks = [];
        const shape = this.getCurrentShape();
        
        for (let y = 0; y < shape.length; y++) {
            for (let x = 0; x < shape[y].length; x++) {
                if (shape[y][x]) {
                    blocks.push({
                        x: this.x + x,
                        y: this.y + y,
                        color: this.color
                    });
                }
            }
        }
        
        return blocks;
    }

    // Create a copy of the piece
    copy() {
        const newPiece = new Piece(this.type, this.x, this.y);
        newPiece.rotation = this.rotation;
        newPiece.lockDelay = this.lockDelay;
        newPiece.moved = this.moved;
        return newPiece;
    }

    // Move piece
    move(dx, dy) {
        this.x += dx;
        this.y += dy;
        this.moved = true;
        this.resetLockDelay();
    }

    // Rotate piece (returns new rotation state)
    getRotatedState(direction = 1) {
        const newRotation = (this.rotation + direction + 4) % 4;
        return newRotation;
    }

    // Apply rotation
    rotate(direction = 1) {
        this.rotation = this.getRotatedState(direction);
        this.moved = true;
        this.resetLockDelay();
    }

    // Reset lock delay when piece moves or rotates
    resetLockDelay() {
        this.lockDelay = 0;
    }

    // Update lock delay
    updateLockDelay(deltaTime) {
        this.lockDelay += deltaTime;
        return this.lockDelay >= this.maxLockDelay;
    }

    // Check if piece should lock
    shouldLock(grid) {
        // Check if piece can move down
        const testPiece = this.copy();
        testPiece.y += 1;
        return grid.checkCollision(testPiece);
    }
    
    // Save piece state
    saveState() {
        return {
            type: this.type,
            x: this.x,
            y: this.y,
            rotation: this.rotation,
            color: this.color
        };
    }
    
    // Load piece state
    loadState(state) {
        if (state) {
            this.type = state.type;
            this.x = state.x;
            this.y = state.y;
            this.rotation = state.rotation;
            this.color = state.color;
        }
    }
}

// 7-Bag Random Generator for fair piece distribution
export class PieceBag {
    constructor() {
        this.bag = [];
        this.refillBag();
    }

    refillBag() {
        const pieces = Object.values(PIECE_TYPES);
        // Shuffle using Fisher-Yates algorithm
        for (let i = pieces.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [pieces[i], pieces[j]] = [pieces[j], pieces[i]];
        }
        this.bag = pieces;
    }

    getNextPiece() {
        if (this.bag.length === 0) {
            this.refillBag();
        }
        const pieceType = this.bag.pop();
        return new Piece(pieceType);
    }

    // Preview next N pieces without removing them
    previewPieces(count = 3) {
        const preview = [];
        let tempBag = [...this.bag];
        let tempPieces = Object.values(PIECE_TYPES);
        
        for (let i = 0; i < count; i++) {
            if (tempBag.length === 0) {
                // Shuffle new bag
                for (let j = tempPieces.length - 1; j > 0; j--) {
                    const k = Math.floor(Math.random() * (j + 1));
                    [tempPieces[j], tempPieces[k]] = [tempPieces[k], tempPieces[j]];
                }
                tempBag = [...tempPieces];
            }
            preview.push(tempBag.pop());
        }
        
        return preview;
    }
}

// T-Spin Detection
export class TSpinDetector {
    static isTSpin(piece, grid, previousPosition) {
        if (piece.type !== 'T') return { type: 'none', mini: false };
        
        const corners = this.getCorners(piece);
        let filledCorners = 0;
        let frontCorners = 0;
        
        // Check which corners are filled
        corners.forEach((corner, index) => {
            if (grid.isOccupied(corner.x, corner.y)) {
                filledCorners++;
                if (index < 2) frontCorners++; // Front corners (relative to rotation)
            }
        });
        
        // T-Spin requires at least 3 filled corners
        if (filledCorners < 3) return { type: 'none', mini: false };
        
        // Check if the piece was rotated into position
        const wasRotated = previousPosition && 
            (previousPosition.rotation !== piece.rotation);
        
        if (!wasRotated) return { type: 'none', mini: false };
        
        // T-Spin Mini: Only 3 corners filled, or back corners filled
        const isMini = filledCorners === 3 || frontCorners < 2;
        
        return {
            type: 'tspin',
            mini: isMini
        };
    }
    
    static getCorners(piece) {
        const rotation = piece.rotation;
        const baseCorners = [
            { x: piece.x, y: piece.y },         // Top-left
            { x: piece.x + 2, y: piece.y },     // Top-right
            { x: piece.x, y: piece.y + 2 },     // Bottom-left
            { x: piece.x + 2, y: piece.y + 2 }  // Bottom-right
        ];
        
        // Adjust corners based on T-piece rotation
        switch (rotation) {
            case 0: return [baseCorners[0], baseCorners[1], baseCorners[2], baseCorners[3]];
            case 1: return [baseCorners[1], baseCorners[3], baseCorners[0], baseCorners[2]];
            case 2: return [baseCorners[3], baseCorners[2], baseCorners[1], baseCorners[0]];
            case 3: return [baseCorners[2], baseCorners[0], baseCorners[3], baseCorners[1]];
        }
    }
}