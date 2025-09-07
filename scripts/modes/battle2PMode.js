// Local 2-Player Battle Mode
import { GameMode } from './gameMode.js';
import { Grid } from '../grid.js';
import { Piece } from '../pieces.js';

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
        // Create grids for both players
        this.players.p1.grid = new Grid(10, 20);
        this.players.p2.grid = new Grid(10, 20);
        
        // Initialize pieces for both players
        this.initializePlayer('p1');
        this.initializePlayer('p2');
        
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
        
        // Generate piece bag
        player.nextPieces = this.generatePieceBag();
        player.currentPiece = this.getNextPiece(playerId);
        
        // Position piece
        if (player.currentPiece) {
            player.currentPiece.x = Math.floor((10 - player.currentPiece.shape[0].length) / 2);
            player.currentPiece.y = 0;
        }
    }
    
    startRound() {
        this.roundStartTime = Date.now();
        
        // Clear grids
        this.players.p1.grid.clear();
        this.players.p2.grid.clear();
        
        // Reset round-specific stats
        ['p1', 'p2'].forEach(playerId => {
            const player = this.players[playerId];
            player.stats.piecesPlaced = 0;
            player.stats.linesCleared = 0;
            player.stats.attacks = 0;
            player.stats.defenses = 0;
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
        
        // Check for topped out
        if (this.isPlayerToppedOut(playerId)) {
            player.ko = true;
        }
    }
    
    handleInput(event) {
        // Handle P1 controls
        if (this.handlePlayerInput('p1', event.key)) {
            event.preventDefault();
            return true;
        }
        
        // Handle P2 controls
        if (this.handlePlayerInput('p2', event.key)) {
            event.preventDefault();
            return true;
        }
        
        return false;
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
        
        if (this.isValidPosition(playerId, player.currentPiece.shape, newX, newY)) {
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
        const rotated = this.rotateMatrix(player.currentPiece.shape, direction);
        
        // Try wall kicks
        const kicks = this.getWallKicks(player.currentPiece.type, direction);
        
        for (const [dx, dy] of kicks) {
            const newX = player.currentPiece.x + dx;
            const newY = player.currentPiece.y + dy;
            
            if (this.isValidPosition(playerId, rotated, newX, newY)) {
                player.currentPiece.shape = rotated;
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
        
        // Reset position
        if (player.currentPiece) {
            player.currentPiece.x = Math.floor((10 - player.currentPiece.shape[0].length) / 2);
            player.currentPiece.y = 0;
        }
        
        player.canHold = false;
        return true;
    }
    
    lockPlayerPiece(playerId) {
        const player = this.players[playerId];
        if (!player.currentPiece) return;
        
        // Place piece on grid
        this.placePieceOnGrid(
            player.grid,
            player.currentPiece.shape,
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
    }
    
    checkLineClears(playerId) {
        const player = this.players[playerId];
        const clearedLines = [];
        
        // Check each row
        for (let y = player.grid.height - 1; y >= 0; y--) {
            let complete = true;
            for (let x = 0; x < player.grid.width; x++) {
                if (player.grid.cells[y][x] === 0) {
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
                player.grid.cells.unshift(new Array(player.grid.width).fill(0));
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
            garbageLine[holePosition] = 0;
            
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
                    if (player.grid.cells[gridY][gridX] !== 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    isPlayerToppedOut(playerId) {
        const player = this.players[playerId];
        
        // Check if any blocks in top 2 rows
        for (let y = 0; y < 2; y++) {
            for (let x = 0; x < player.grid.width; x++) {
                if (player.grid.cells[y][x] !== 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    isGridEmpty(grid) {
        for (let y = 0; y < grid.height; y++) {
            for (let x = 0; x < grid.width; x++) {
                if (grid.cells[y][x] !== 0) {
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
            this.game.uiManager.showBattleVictory(results);
        }
        
        // Trigger game over
        if (this.game) {
            this.game.battleResults = results;
            this.game.gameOver();
        }
    }
    
    showPlayerMessage(playerId, message) {
        // Show message above player's grid
        if (this.game.uiManager) {
            const side = playerId === 'p1' ? 'left' : 'right';
            this.game.uiManager.showSideMessage(message, side, 1000);
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
                        grid.cells[gridY][gridX] = shape[row][col];
                    }
                }
            }
        }
    }
    
    render(ctx) {
        // This would need custom rendering for split-screen
        // The main game.js render would need to be modified to support this
        
        // For now, return render instructions
        return {
            mode: 'split-screen',
            players: {
                p1: {
                    grid: this.players.p1.grid,
                    currentPiece: this.players.p1.currentPiece,
                    score: this.players.p1.score,
                    lines: this.players.p1.lines,
                    wins: this.players.p1.wins
                },
                p2: {
                    grid: this.players.p2.grid,
                    currentPiece: this.players.p2.currentPiece,
                    score: this.players.p2.score,
                    lines: this.players.p2.lines,
                    wins: this.players.p2.wins
                }
            }
        };
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
        // Clean up any mode-specific resources
    }
    
    getLeaderboardCategory() {
        return 'battle2p';
    }
    
    supportsSaving() {
        return false; // Multiplayer can't be saved
    }
}