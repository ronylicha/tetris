// Local 2-Player Battle Mode
import { GameMode } from './gameMode.js';
import { Grid } from '../grid.js';
import { Piece, PIECE_TYPES } from '../pieces.js';

export class Battle2PMode extends GameMode {
    constructor(game) {
        super(game);
        this.name = 'Battle 2P';
        this.description = 'Local 2-player competitive Tetris!';
        this.icon = '👥';
        this.themeColor = '#ff00ff';
        
        // Player states
        this.players = {
            p1: {
                grid: null,
                score: 0,
                lines: 0,
                level: 1,
                currentPiece: null,
                nextPieces: [],
                holdPiece: null,
                canHold: true,
                dropTimer: 0,
                dropInterval: 1000,
                combo: 0,
                garbage: [],
                attacks: [],
                defenses: [],
                ko: false,
                wins: 0,
                stats: {
                    piecesPlaced: 0,
                    linesCleared: 0,
                    attacks: 0,
                    defenses: 0,
                    tspins: 0,
                    tetris: 0
                }
            },
            p2: {
                grid: null,
                score: 0,
                lines: 0,
                level: 1,
                currentPiece: null,
                nextPieces: [],
                holdPiece: null,
                canHold: true,
                dropTimer: 0,
                dropInterval: 1000,
                combo: 0,
                garbage: [],
                attacks: [],
                defenses: [],
                ko: false,
                wins: 0,
                stats: {
                    piecesPlaced: 0,
                    linesCleared: 0,
                    attacks: 0,
                    defenses: 0,
                    tspins: 0,
                    tetris: 0
                }
            }
        };
        
        // Battle settings
        this.roundNumber = 1;
        this.maxRounds = 3; // First to 2 wins
        this.roundTimer = 120; // 2 minutes per round
        this.roundStartTime = null;
        this.battleOver = false;
        
        // Garbage settings
        this.garbageMultiplier = 1.0;
        this.attackDelay = 500; // ms delay before garbage appears
        
        // Controls mapping
        this.controls = {
            p1: {
                left: 'ArrowLeft',
                right: 'ArrowRight',
                down: 'ArrowDown',
                rotate: 'ArrowUp',
                rotateCC: 'z',
                hardDrop: ' ',
                hold: 'Shift'
            },
            p2: {
                left: 'a',
                right: 'd',
                down: 's',
                rotate: 'w',
                rotateCC: 'q',
                hardDrop: 'e',
                hold: 'r'
            }
        };
        
        // Visual separation
        this.splitScreen = true;
        this.centerDivider = true;
    }
    
    async initialize() {
        // Setup dual canvas display
        this.setupDualCanvas();
        
        // Create grids for both players
        this.players.p1.grid = new Grid();
        this.players.p2.grid = new Grid();
        
        // Reset wins for new battle
        this.players.p1.wins = 0;
        this.players.p2.wins = 0;
        
        // Initialize both players
        this.initializePlayer('p1');
        this.initializePlayer('p2');
        
        // Make sure the battle is not over
        this.battleOver = false;
        this.roundNumber = 1;
        
        // Start round
        this.startRound();
        
        // Show instructions
        if (this.game.uiManager) {
            this.game.uiManager.showMessage(
                'Battle Mode - First to 2 wins! P1: Arrows | P2: WASD',
                'info',
                3000
            );
        }
    }
    
    setupDualCanvas() {
        // Add class to body to activate Battle 2P styles
        document.body.classList.add('battle2p-active');
        
        // Show dual canvas container
        const battle2pContainer = document.getElementById('battle2p-container');
        if (battle2pContainer) {
            battle2pContainer.style.display = 'flex';
        }
        
        // Setup player 1 canvases
        this.players.p1.canvas = document.getElementById('p1-canvas');
        this.players.p1.holdCanvas = document.getElementById('p1-hold-canvas');
        this.players.p1.nextCanvas = document.getElementById('p1-next-canvas');
        
        if (this.players.p1.canvas) {
            this.players.p1.ctx = this.players.p1.canvas.getContext('2d');
            this.players.p1.canvas.width = 250;
            this.players.p1.canvas.height = 500;
        }
        if (this.players.p1.holdCanvas) {
            this.players.p1.holdCtx = this.players.p1.holdCanvas.getContext('2d');
        }
        if (this.players.p1.nextCanvas) {
            this.players.p1.nextCtx = this.players.p1.nextCanvas.getContext('2d');
        }
        
        // Setup player 2 canvases
        this.players.p2.canvas = document.getElementById('p2-canvas');
        this.players.p2.holdCanvas = document.getElementById('p2-hold-canvas');
        this.players.p2.nextCanvas = document.getElementById('p2-next-canvas');
        
        if (this.players.p2.canvas) {
            this.players.p2.ctx = this.players.p2.canvas.getContext('2d');
            this.players.p2.canvas.width = 250;
            this.players.p2.canvas.height = 500;
        }
        if (this.players.p2.holdCanvas) {
            this.players.p2.holdCtx = this.players.p2.holdCanvas.getContext('2d');
        }
        if (this.players.p2.nextCanvas) {
            this.players.p2.nextCtx = this.players.p2.nextCanvas.getContext('2d');
        }
    }
    
    initializePlayer(playerId) {
        const player = this.players[playerId];
        
        // Reset player state
        player.score = 0;
        player.lines = 0;
        player.level = 1;
        player.combo = 0;
        player.garbage = [];
        player.attacks = [];
        player.defenses = [];
        player.ko = false;
        player.dropTimer = 0;
        player.holdPiece = null;
        player.canHold = true;
        
        // Clear the grid properly
        if (player.grid) {
            player.grid.reset();
        }
        
        // Generate piece bag
        player.nextPieces = this.generatePieceBag();
        player.currentPiece = this.getNextPiece(playerId);
        
        // Position piece (start in hidden area)
        if (player.currentPiece) {
            const shape = player.currentPiece.getCurrentShape();
            player.currentPiece.x = Math.floor((10 - shape[0].length) / 2);
            player.currentPiece.y = 2; // Start in hidden rows (y=2 gives some buffer)
        }
    }
    
    startRound() {
        this.roundStartTime = Date.now();
        
        // Clear grids
        this.players.p1.grid.reset();
        this.players.p2.grid.reset();
        
        // Reset round-specific stats and pieces
        ['p1', 'p2'].forEach(playerId => {
            const player = this.players[playerId];
            player.stats.piecesPlaced = 0;
            player.stats.linesCleared = 0;
            player.stats.attacks = 0;
            player.stats.defenses = 0;
            player.ko = false;
            
            // Generate new pieces for the round
            player.nextPieces = this.generatePieceBag();
            player.currentPiece = this.getNextPiece(playerId);
            
            // Position piece (start in hidden area)
            if (player.currentPiece) {
                const shape = player.currentPiece.getCurrentShape();
                player.currentPiece.x = Math.floor((10 - shape[0].length) / 2);
                player.currentPiece.y = 2; // Start in hidden rows (y=2 gives some buffer)
            }
        });
        
        // Announce round
        if (this.game.uiManager) {
            this.game.uiManager.showMessage(
                `Round ${this.roundNumber} - FIGHT!`,
                'battle',
                2000
            );
        }
    }
    
    update(deltaTime) {
        if (this.battleOver) return false;
        
        // Update both players
        this.updatePlayer('p1', deltaTime);
        this.updatePlayer('p2', deltaTime);
        
        // Process garbage queue
        this.processGarbage('p1');
        this.processGarbage('p2');
        
        // Render both players
        this.renderPlayer('p1');
        this.renderPlayer('p2');
        
        // Update UI
        this.updateBattle2PUI();
        
        // Check round timer
        if (this.roundTimer > 0) {
            const elapsed = (Date.now() - this.roundStartTime) / 1000;
            if (elapsed >= this.roundTimer) {
                this.endRoundByTimeout();
            }
        }
        
        // Check for KO
        if (this.players.p1.ko || this.players.p2.ko) {
            this.endRound();
        }
        
        return true;
    }
    
    updatePlayer(playerId, deltaTime) {
        const player = this.players[playerId];
        
        if (player.ko) return;
        
        // Update drop timer
        player.dropTimer += deltaTime;
        
        // Auto drop
        if (player.dropTimer >= player.dropInterval) {
            player.dropTimer = 0;
            this.movePlayerPiece(playerId, 0, 1);
        }
        
        // Check for topped out only when piece is locked
        // Not during regular updates to avoid false positives
    }
    
    handleKeyPress(key) {
        // Handle P1 controls
        if (this.handlePlayerInput('p1', key)) {
            return true;
        }
        
        // Handle P2 controls
        if (this.handlePlayerInput('p2', key)) {
            return true;
        }
        
        return false;
    }
    
    handleInput(event) {
        return this.handleKeyPress(event.key);
    }
    
    handlePlayerInput(playerId, key) {
        const player = this.players[playerId];
        const controls = this.controls[playerId];
        
        if (player.ko) return false;
        
        switch(key) {
            case controls.left:
                this.movePlayerPiece(playerId, -1, 0);
                return true;
                
            case controls.right:
                this.movePlayerPiece(playerId, 1, 0);
                return true;
                
            case controls.down:
                this.movePlayerPiece(playerId, 0, 1);
                player.score += 1; // Soft drop points
                return true;
                
            case controls.rotate:
                this.rotatePlayerPiece(playerId, 1);
                return true;
                
            case controls.rotateCC:
                this.rotatePlayerPiece(playerId, -1);
                return true;
                
            case controls.hardDrop:
                this.hardDropPlayerPiece(playerId);
                return true;
                
            case controls.hold:
                this.holdPlayerPiece(playerId);
                return true;
        }
        
        return false;
    }
    
    movePlayerPiece(playerId, dx, dy) {
        const player = this.players[playerId];
        if (!player.currentPiece) return false;
        
        // Check if move is valid
        const newX = player.currentPiece.x + dx;
        const newY = player.currentPiece.y + dy;
        const shape = player.currentPiece.getCurrentShape();
        
        if (this.isValidPosition(playerId, shape, newX, newY)) {
            player.currentPiece.x = newX;
            player.currentPiece.y = newY;
            return true;
        } else if (dy > 0) {
            // Piece can't move down, lock it
            this.lockPlayerPiece(playerId);
        }
        
        return false;
    }
    
    rotatePlayerPiece(playerId, direction) {
        const player = this.players[playerId];
        if (!player.currentPiece) return false;
        
        // Rotate shape
        const currentShape = player.currentPiece.getCurrentShape();
        const rotated = this.rotateMatrix(currentShape, direction);
        
        // Try wall kicks
        const kicks = this.getWallKicks(player.currentPiece.type, direction);
        
        for (const [dx, dy] of kicks) {
            const newX = player.currentPiece.x + dx;
            const newY = player.currentPiece.y + dy;
            
            if (this.isValidPosition(playerId, rotated, newX, newY)) {
                // Update rotation state instead of shape directly
                player.currentPiece.rotation = (player.currentPiece.rotation + direction + 4) % 4;
                player.currentPiece.x = newX;
                player.currentPiece.y = newY;
                return true;
            }
        }
        
        return false;
    }
    
    hardDropPlayerPiece(playerId) {
        const player = this.players[playerId];
        if (!player.currentPiece) return;
        
        let dropDistance = 0;
        while (this.movePlayerPiece(playerId, 0, 1)) {
            dropDistance++;
        }
        
        // Award hard drop points
        player.score += dropDistance * 2;
    }
    
    holdPlayerPiece(playerId) {
        const player = this.players[playerId];
        if (!player.canHold || !player.currentPiece) return false;
        
        const held = player.currentPiece;
        
        if (player.holdPiece) {
            // Swap with hold
            player.currentPiece = player.holdPiece;
            player.holdPiece = held;
        } else {
            // Put in hold and get next
            player.holdPiece = held;
            player.currentPiece = this.getNextPiece(playerId);
        }
        
        // Reset position (start in hidden area)
        if (player.currentPiece) {
            const shape = player.currentPiece.getCurrentShape();
            player.currentPiece.x = Math.floor((10 - shape[0].length) / 2);
            player.currentPiece.y = 2; // Start in hidden rows (y=2 gives some buffer)
        }
        
        player.canHold = false;
        return true;
    }
    
    lockPlayerPiece(playerId) {
        const player = this.players[playerId];
        if (!player.currentPiece) return;
        
        // Place piece on grid
        const shape = player.currentPiece.getCurrentShape();
        this.placePieceOnGrid(
            player.grid,
            shape,
            player.currentPiece.x,
            player.currentPiece.y,
            player.currentPiece.type
        );
        
        // Update stats
        player.stats.piecesPlaced++;
        
        // Check for line clears
        const clearedLines = this.checkLineClears(playerId);
        
        // Get next piece
        player.currentPiece = this.getNextPiece(playerId);
        player.canHold = true;
        
        // Reset drop timer
        player.dropTimer = 0;
        
        // Check if the new piece can be placed (game over check)
        if (player.currentPiece) {
            const shape = player.currentPiece.getCurrentShape();
            const startX = Math.floor((10 - shape[0].length) / 2);
            const startY = 2; // Check in hidden area where pieces spawn
            
            if (!this.isValidPosition(playerId, shape, startX, startY)) {
                // Player is topped out
                player.ko = true;
            } else {
                // Position the new piece
                player.currentPiece.x = startX;
                player.currentPiece.y = startY;
            }
        }
    }
    
    checkLineClears(playerId) {
        const player = this.players[playerId];
        const clearedLines = [];
        
        // Check each row
        for (let y = player.grid.height - 1; y >= 0; y--) {
            let complete = true;
            for (let x = 0; x < player.grid.width; x++) {
                if (player.grid.cells[y][x] === null || player.grid.cells[y][x] === 0) {
                    complete = false;
                    break;
                }
            }
            
            if (complete) {
                clearedLines.push(y);
            }
        }
        
        if (clearedLines.length > 0) {
            // Clear the lines
            clearedLines.forEach(y => {
                player.grid.cells.splice(y, 1);
                player.grid.cells.unshift(new Array(player.grid.width).fill(null));
            });
            
            // Update stats
            player.lines += clearedLines.length;
            player.stats.linesCleared += clearedLines.length;
            
            // Calculate attack
            const attack = this.calculateAttack(playerId, clearedLines.length);
            
            // Send garbage to opponent
            const opponentId = playerId === 'p1' ? 'p2' : 'p1';
            this.sendGarbage(opponentId, attack);
            
            // Update combo
            player.combo++;
            
            // Score calculation
            const baseScore = [0, 100, 300, 500, 800][clearedLines.length];
            player.score += baseScore * player.level * (1 + player.combo * 0.1);
            
            // Check for special clears
            if (clearedLines.length === 4) {
                player.stats.tetris++;
                this.showPlayerMessage(playerId, 'TETRIS!');
            }
        } else {
            // Reset combo
            player.combo = 0;
        }
        
        return clearedLines;
    }
    
    calculateAttack(playerId, linesCleared) {
        const player = this.players[playerId];
        let attack = 0;
        
        // Base attack values
        const attackTable = [0, 0, 1, 2, 4]; // 0, 1, 2, 3, 4 lines
        attack = attackTable[Math.min(linesCleared, 4)];
        
        // Combo multiplier
        if (player.combo > 1) {
            attack += Math.floor(player.combo / 2);
        }
        
        // T-Spin bonus
        // Would need T-spin detection here
        
        // Perfect clear bonus
        if (this.isGridEmpty(player.grid)) {
            attack += 10;
            this.showPlayerMessage(playerId, 'PERFECT CLEAR!');
        }
        
        player.stats.attacks += attack;
        
        return attack;
    }
    
    sendGarbage(playerId, amount) {
        if (amount <= 0) return;
        
        const player = this.players[playerId];
        
        // Add to garbage queue with delay
        player.garbage.push({
            amount: amount,
            timestamp: Date.now() + this.attackDelay
        });
    }
    
    processGarbage(playerId) {
        const player = this.players[playerId];
        const now = Date.now();
        
        // Process ready garbage
        player.garbage = player.garbage.filter(g => {
            if (g.timestamp <= now) {
                this.addGarbageLines(playerId, g.amount);
                return false;
            }
            return true;
        });
    }
    
    addGarbageLines(playerId, amount) {
        const player = this.players[playerId];
        
        // Remove top lines
        for (let i = 0; i < amount; i++) {
            player.grid.cells.shift();
        }
        
        // Add garbage lines at bottom
        for (let i = 0; i < amount; i++) {
            const garbageLine = new Array(player.grid.width).fill(8); // Gray blocks
            
            // Add one random hole
            const holePosition = Math.floor(Math.random() * player.grid.width);
            garbageLine[holePosition] = null;
            
            player.grid.cells.push(garbageLine);
        }
        
        // Show warning
        this.showPlayerMessage(playerId, `+${amount} GARBAGE!`);
    }
    
    isValidPosition(playerId, shape, x, y) {
        const player = this.players[playerId];
        
        for (let row = 0; row < shape.length; row++) {
            for (let col = 0; col < shape[row].length; col++) {
                if (shape[row][col] !== 0) {
                    const gridX = x + col;
                    const gridY = y + row;
                    
                    // Check boundaries
                    if (gridX < 0 || gridX >= player.grid.width ||
                        gridY < 0 || gridY >= player.grid.height) {
                        return false;
                    }
                    
                    // Check collision
                    if (player.grid.cells[gridY][gridX] !== null && player.grid.cells[gridY][gridX] !== 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    
    isGridEmpty(grid) {
        for (let y = 0; y < grid.height; y++) {
            for (let x = 0; x < grid.width; x++) {
                if (grid.cells[y][x] !== null && grid.cells[y][x] !== 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    endRound() {
        // Determine winner
        let winnerId = null;
        if (this.players.p1.ko && !this.players.p2.ko) {
            winnerId = 'p2';
        } else if (this.players.p2.ko && !this.players.p1.ko) {
            winnerId = 'p1';
        } else {
            // Both KO or timeout - highest score wins
            winnerId = this.players.p1.score > this.players.p2.score ? 'p1' : 'p2';
        }
        
        // Award round win
        this.players[winnerId].wins++;
        
        // Show round result
        if (this.game.uiManager) {
            const winnerName = winnerId === 'p1' ? 'Player 1' : 'Player 2';
            this.game.uiManager.showMessage(
                `${winnerName} wins Round ${this.roundNumber}!`,
                'success',
                3000
            );
        }
        
        // Check for match winner
        if (this.players[winnerId].wins >= 2) {
            this.endBattle(winnerId);
        } else {
            // Start next round
            this.roundNumber++;
            setTimeout(() => {
                this.initializePlayer('p1');
                this.initializePlayer('p2');
                this.startRound();
            }, 3000);
        }
    }
    
    endRoundByTimeout() {
        // Highest score wins
        const winnerId = this.players.p1.score > this.players.p2.score ? 'p1' : 'p2';
        this.players[winnerId].wins++;
        this.endRound();
    }
    
    endBattle(winnerId) {
        this.battleOver = true;
        
        const winnerName = winnerId === 'p1' ? 'Player 1' : 'Player 2';
        const loserName = winnerId === 'p1' ? 'Player 2' : 'Player 1';
        const winnerScore = winnerId === 'p1' ? this.players.p1.score : this.players.p2.score;
        const loserScore = winnerId === 'p1' ? this.players.p2.score : this.players.p1.score;
        
        // Calculate final stats
        const results = {
            winner: winnerName,
            loser: loserName,
            rounds: this.roundNumber,
            p1Stats: this.players.p1.stats,
            p2Stats: this.players.p2.stats,
            p1Score: this.players.p1.score,
            p2Score: this.players.p2.score
        };
        
        // Show victory screen
        if (this.game.uiManager) {
            const message = `${winnerName} WINS!\n\nFinal Score:\n${winnerName}: ${winnerScore}\n${loserName}: ${loserScore}\n\nRounds played: ${this.roundNumber}`;
            this.game.uiManager.showOverlay('Battle Complete!', message, true);
        }
        
        // Set game state to game over
        if (this.game) {
            this.game.state = 'gameover';
            this.game.battleResults = results;
        }
    }
    
    showPlayerMessage(playerId, message) {
        // Show message above player's grid
        if (this.game.uiManager) {
            const playerName = playerId === 'p1' ? 'Player 1' : 'Player 2';
            this.game.uiManager.showMessage(`${playerName}: ${message}`, 'info', 1000);
        }
    }
    
    getNextPiece(playerId) {
        const player = this.players[playerId];
        
        if (player.nextPieces.length < 7) {
            player.nextPieces.push(...this.generatePieceBag());
        }
        
        const pieceType = player.nextPieces.shift();
        return new Piece(pieceType);
    }
    
    generatePieceBag() {
        const pieces = ['I', 'O', 'T', 'S', 'Z', 'J', 'L'];
        const shuffled = [...pieces];
        
        for (let i = shuffled.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
        }
        
        return shuffled;
    }
    
    rotateMatrix(matrix, direction) {
        const n = matrix.length;
        const rotated = Array(n).fill(null).map(() => Array(n).fill(0));
        
        if (direction > 0) {
            // Clockwise
            for (let i = 0; i < n; i++) {
                for (let j = 0; j < n; j++) {
                    rotated[j][n - 1 - i] = matrix[i][j];
                }
            }
        } else {
            // Counter-clockwise
            for (let i = 0; i < n; i++) {
                for (let j = 0; j < n; j++) {
                    rotated[n - 1 - j][i] = matrix[i][j];
                }
            }
        }
        
        return rotated;
    }
    
    getWallKicks(pieceType, direction) {
        // Basic wall kick data
        if (pieceType === 'I') {
            return [[0, 0], [-2, 0], [1, 0], [-2, -1], [1, 2]];
        } else if (pieceType === 'O') {
            return [[0, 0]]; // O piece doesn't kick
        } else {
            return [[0, 0], [-1, 0], [-1, 1], [0, -2], [-1, -2]];
        }
    }
    
    placePieceOnGrid(grid, shape, x, y, type) {
        for (let row = 0; row < shape.length; row++) {
            for (let col = 0; col < shape[row].length; col++) {
                if (shape[row][col] !== 0) {
                    const gridY = y + row;
                    const gridX = x + col;
                    
                    if (gridY >= 0 && gridY < grid.height &&
                        gridX >= 0 && gridX < grid.width) {
                        // Use the piece type instead of the shape value
                        grid.cells[gridY][gridX] = type;
                    }
                }
            }
        }
    }
    
    renderPlayer(playerId) {
        const player = this.players[playerId];
        const ctx = player.ctx;
        const canvas = player.canvas;
        
        if (!ctx || !canvas) return;
        
        // Clear canvas
        ctx.fillStyle = 'rgba(0, 0, 0, 0.9)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        
        const cellSize = canvas.width / 10;
        
        // Render grid (only visible rows, not hidden rows)
        const visibleHeight = 20; // Only render the visible 20 rows, not the 4 hidden rows
        for (let y = 4; y < player.grid.height; y++) { // Start from row 4 to skip hidden rows
            for (let x = 0; x < player.grid.width; x++) {
                if (player.grid.cells[y][x] !== null && player.grid.cells[y][x] !== 0) {
                    this.renderCell(ctx, x, y - 4, player.grid.cells[y][x], cellSize);
                }
            }
        }
        
        // Render ghost piece
        if (player.currentPiece && !player.ko) {
            const ghostY = this.getGhostPieceY(playerId);
            const piece = player.currentPiece;
            const shape = piece.getCurrentShape();
            
            ctx.globalAlpha = 0.3;
            for (let y = 0; y < shape.length; y++) {
                for (let x = 0; x < shape[y].length; x++) {
                    if (shape[y][x] !== 0) {
                        // Adjust for hidden rows
                        const renderY = ghostY + y - 4;
                        if (renderY >= 0) {
                            this.renderCell(ctx, piece.x + x, renderY, piece.type, cellSize);
                        }
                    }
                }
            }
            ctx.globalAlpha = 1.0;
        }
        
        // Render current piece
        if (player.currentPiece && !player.ko) {
            const piece = player.currentPiece;
            const shape = piece.getCurrentShape();
            for (let y = 0; y < shape.length; y++) {
                for (let x = 0; x < shape[y].length; x++) {
                    if (shape[y][x] !== 0) {
                        const pieceType = PIECE_TYPES[piece.type] ? piece.type : 1;
                        // Adjust for hidden rows
                        const renderY = piece.y + y - 4;
                        if (renderY >= 0) {
                            this.renderCell(ctx, piece.x + x, renderY, pieceType, cellSize);
                        }
                    }
                }
            }
        }
        
        // Render grid lines
        ctx.strokeStyle = 'rgba(255, 255, 255, 0.1)';
        ctx.lineWidth = 0.5;
        for (let i = 0; i <= 10; i++) {
            ctx.beginPath();
            ctx.moveTo(i * cellSize, 0);
            ctx.lineTo(i * cellSize, canvas.height);
            ctx.stroke();
        }
        for (let i = 0; i <= 20; i++) {
            ctx.beginPath();
            ctx.moveTo(0, i * cellSize);
            ctx.lineTo(canvas.width, i * cellSize);
            ctx.stroke();
        }
        
        // Show KO overlay if player is defeated
        if (player.ko) {
            ctx.fillStyle = 'rgba(255, 0, 0, 0.5)';
            ctx.fillRect(0, 0, canvas.width, canvas.height);
            ctx.fillStyle = '#fff';
            ctx.font = 'bold 30px Arial';
            ctx.textAlign = 'center';
            ctx.fillText('K.O.', canvas.width / 2, canvas.height / 2);
        }
        
        // Render hold piece
        this.renderHoldPiece(playerId);
        
        // Render next pieces
        this.renderNextPieces(playerId);
    }
    
    renderCell(ctx, x, y, type, cellSize) {
        const colors = {
            'I': '#00ffff', // I - Cyan
            'J': '#0000ff', // J - Blue
            'L': '#ff8800', // L - Orange
            'O': '#ffff00', // O - Yellow
            'S': '#00ff00', // S - Green
            'T': '#ff00ff', // T - Purple
            'Z': '#ff0000', // Z - Red
            // Legacy number support for grid cells
            1: '#00ffff',
            2: '#0000ff',
            3: '#ff8800',
            4: '#ffff00',
            5: '#00ff00',
            6: '#ff00ff',
            7: '#ff0000'
        };
        
        ctx.fillStyle = colors[type] || '#888';
        ctx.fillRect(x * cellSize + 1, y * cellSize + 1, cellSize - 2, cellSize - 2);
        
        // Add glow effect
        ctx.shadowColor = colors[type] || '#888';
        ctx.shadowBlur = 10;
        ctx.fillRect(x * cellSize + 1, y * cellSize + 1, cellSize - 2, cellSize - 2);
        ctx.shadowBlur = 0;
    }
    
    getGhostPieceY(playerId) {
        const player = this.players[playerId];
        if (!player.currentPiece) return 0;
        
        let testY = player.currentPiece.y;
        const shape = player.currentPiece.getCurrentShape();
        
        while (this.isValidPosition(playerId, shape, player.currentPiece.x, testY + 1)) {
            testY++;
        }
        
        return testY;
    }
    
    renderHoldPiece(playerId) {
        const player = this.players[playerId];
        const ctx = player.holdCtx;
        const canvas = player.holdCanvas;
        
        if (!ctx || !canvas) return;
        
        // Clear canvas
        ctx.fillStyle = 'rgba(0, 0, 0, 0.8)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        
        if (player.holdPiece) {
            const piece = player.holdPiece;
            const shape = piece.getCurrentShape();
            const cellSize = 16;
            const offsetX = (canvas.width - shape[0].length * cellSize) / 2;
            const offsetY = (canvas.height - shape.length * cellSize) / 2;
            
            for (let y = 0; y < shape.length; y++) {
                for (let x = 0; x < shape[y].length; x++) {
                    if (shape[y][x] !== 0) {
                        this.renderCell(ctx, offsetX / cellSize + x, offsetY / cellSize + y, piece.type, cellSize);
                    }
                }
            }
        }
    }
    
    renderNextPieces(playerId) {
        const player = this.players[playerId];
        const ctx = player.nextCtx;
        const canvas = player.nextCanvas;
        
        if (!ctx || !canvas) return;
        
        // Clear canvas
        ctx.fillStyle = 'rgba(0, 0, 0, 0.8)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        
        // Show next 3 pieces
        for (let i = 0; i < 3 && i < player.nextPieces.length; i++) {
            const pieceType = player.nextPieces[i];
            const piece = new Piece(pieceType);
            const shape = piece.getCurrentShape();
            const cellSize = 14;
            const offsetX = (canvas.width - shape[0].length * cellSize) / 2;
            const offsetY = 10 + i * 65;
            
            for (let y = 0; y < shape.length; y++) {
                for (let x = 0; x < shape[y].length; x++) {
                    if (shape[y][x] !== 0) {
                        this.renderCell(ctx, offsetX / cellSize + x, offsetY / cellSize + y, piece.type, cellSize);
                    }
                }
            }
        }
    }
    
    updateBattle2PUI() {
        // Update player 1 stats
        const p1Score = document.getElementById('p1-score');
        const p1Lines = document.getElementById('p1-lines');
        const p1Level = document.getElementById('p1-level');
        const p1Wins = document.getElementById('p1-wins');
        if (p1Score) p1Score.textContent = this.players.p1.score;
        if (p1Lines) p1Lines.textContent = this.players.p1.lines;
        if (p1Level) p1Level.textContent = this.players.p1.level;
        if (p1Wins) p1Wins.textContent = this.players.p1.wins;
        
        // Update player 2 stats
        const p2Score = document.getElementById('p2-score');
        const p2Lines = document.getElementById('p2-lines');
        const p2Level = document.getElementById('p2-level');
        const p2Wins = document.getElementById('p2-wins');
        if (p2Score) p2Score.textContent = this.players.p2.score;
        if (p2Lines) p2Lines.textContent = this.players.p2.lines;
        if (p2Level) p2Level.textContent = this.players.p2.level;
        if (p2Wins) p2Wins.textContent = this.players.p2.wins;
        
        // Update round number
        const roundNumber = document.getElementById('round-number');
        if (roundNumber) roundNumber.textContent = this.roundNumber;
    }
    
    render(ctx) {
        // Main canvas is hidden in Battle 2P mode
        // Rendering is done in renderPlayer for each canvas
        return null;
    }
    
    getObjective() {
        return `Round ${this.roundNumber} - First to 2 wins!`;
    }
    
    getModeUI() {
        return {
            showScore: false, // Custom score display
            showLines: false, // Custom lines display
            showLevel: false,
            showHold: false, // Custom hold display
            showNext: false, // Custom next display
            showTimer: true,
            showObjective: true,
            customDisplay: {
                mode: 'battle-2p',
                p1Wins: this.players.p1.wins,
                p2Wins: this.players.p2.wins,
                round: this.roundNumber
            }
        };
    }
    
    pause() {
        super.pause();
    }
    
    resume() {
        super.resume();
    }
    
    cleanup() {
        // Remove Battle 2P class from body
        document.body.classList.remove('battle2p-active');
        
        // Restore single canvas display
        const singleCanvas = document.getElementById('game-canvas');
        if (singleCanvas) {
            singleCanvas.style.display = 'block';
        }
        
        // Hide dual canvas container
        const battle2pContainer = document.getElementById('battle2p-container');
        if (battle2pContainer) {
            battle2pContainer.style.display = 'none';
        }
    }
    
    getLeaderboardCategory() {
        return 'battle2p';
    }
    
    supportsSaving() {
        return false; // Multiplayer can't be saved
    }
}