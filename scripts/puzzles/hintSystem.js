// Hint System for Puzzle Mode
export class HintSystem {
    constructor(game) {
        this.game = game;
        this.hintsAvailable = 3; // Maximum hints per puzzle
        this.hintsUsed = 0;
        this.hintCooldown = 0;
        this.cooldownDuration = 5000; // 5 seconds between hints
        this.lastHintTime = 0;
        this.currentHintAnimation = null;
    }
    
    initialize(puzzle) {
        this.hintsAvailable = 3;
        this.hintsUsed = 0;
        this.hintCooldown = 0;
        this.lastHintTime = 0;
        this.puzzle = puzzle;
        
        // Add hint button to UI
        this.createHintButton();
    }
    
    createHintButton() {
        // Check if button already exists
        let hintButton = document.getElementById('hint-button');
        if (!hintButton) {
            hintButton = document.createElement('button');
            hintButton.id = 'hint-button';
            hintButton.className = 'hint-button';
            hintButton.innerHTML = `
                <span class="hint-icon">💡</span>
                <span class="hint-count">${this.hintsAvailable - this.hintsUsed}/3</span>
            `;
            
            // Add to game UI
            const gameHeader = document.querySelector('.game-header');
            if (gameHeader) {
                gameHeader.appendChild(hintButton);
            }
            
            // Add click handler
            hintButton.addEventListener('click', () => {
                this.useHint();
            });
        }
        
        this.updateHintButton();
    }
    
    updateHintButton() {
        const hintButton = document.getElementById('hint-button');
        if (!hintButton) return;
        
        const countSpan = hintButton.querySelector('.hint-count');
        if (countSpan) {
            countSpan.textContent = `${this.hintsAvailable - this.hintsUsed}/3`;
        }
        
        // Update button state
        const isOnCooldown = Date.now() - this.lastHintTime < this.cooldownDuration;
        const hasHintsLeft = this.hintsUsed < this.hintsAvailable;
        
        hintButton.disabled = !hasHintsLeft || isOnCooldown;
        
        if (isOnCooldown) {
            const remainingTime = Math.ceil((this.cooldownDuration - (Date.now() - this.lastHintTime)) / 1000);
            hintButton.title = `Cooldown: ${remainingTime}s`;
        } else if (!hasHintsLeft) {
            hintButton.title = 'No hints remaining';
        } else {
            hintButton.title = `Use hint (costs 1 star)`;
        }
    }
    
    useHint() {
        // Check if hint can be used
        if (this.hintsUsed >= this.hintsAvailable) {
            this.showMessage('No hints remaining!', 'warning');
            return false;
        }
        
        // Check cooldown
        if (Date.now() - this.lastHintTime < this.cooldownDuration) {
            const remaining = Math.ceil((this.cooldownDuration - (Date.now() - this.lastHintTime)) / 1000);
            this.showMessage(`Hint cooldown: ${remaining}s`, 'info');
            return false;
        }
        
        // Determine hint level based on hints used
        const hintLevel = this.hintsUsed + 1;
        
        switch(hintLevel) {
            case 1:
                this.showHintLevel1(); // Position suggestion
                break;
            case 2:
                this.showHintLevel2(); // Rotation suggestion
                break;
            case 3:
                this.showHintLevel3(); // Full solution
                break;
        }
        
        // Update hint usage
        this.hintsUsed++;
        this.lastHintTime = Date.now();
        
        // Update button
        this.updateHintButton();
        
        // Notify game mode
        if (this.game.gameMode && this.game.gameMode.hintsUsed !== undefined) {
            this.game.gameMode.hintsUsed = this.hintsUsed;
        }
        
        // Start cooldown timer
        this.startCooldownTimer();
        
        return true;
    }
    
    showHintLevel1() {
        // Level 1: Show optimal column position
        const optimalPosition = this.calculateOptimalPosition();
        
        if (optimalPosition !== null) {
            // Create visual indicator
            this.createColumnHighlight(optimalPosition);
            
            this.showMessage('Hint 1: Best column highlighted (−1⭐)', 'hint');
            
            // Play hint sound
            if (this.game.audioManager) {
                this.game.audioManager.playSFX('hint');
            }
        }
    }
    
    showHintLevel2() {
        // Level 2: Show optimal rotation
        const optimalRotation = this.calculateOptimalRotation();
        
        if (optimalRotation !== null) {
            // Create rotation indicator
            this.createRotationIndicator(optimalRotation);
            
            this.showMessage(`Hint 2: Rotate ${optimalRotation} times (−1⭐)`, 'hint');
            
            // Play hint sound
            if (this.game.audioManager) {
                this.game.audioManager.playSFX('hint');
            }
        }
    }
    
    showHintLevel3() {
        // Level 3: Show full ghost solution
        const solution = this.calculateFullSolution();
        
        if (solution) {
            // Create enhanced ghost piece
            this.createSolutionGhost(solution);
            
            this.showMessage('Hint 3: Full solution shown (−2⭐)', 'hint');
            
            // Play hint sound
            if (this.game.audioManager) {
                this.game.audioManager.playSFX('hint_major');
            }
        }
    }
    
    calculateOptimalPosition() {
        if (!this.game.currentPiece) return null;
        
        // Simple heuristic: find position that creates least holes
        let bestColumn = Math.floor(this.game.grid.width / 2);
        let minHoles = Infinity;
        
        for (let col = 0; col < this.game.grid.width - this.game.currentPiece.shape[0].length + 1; col++) {
            // Test placing piece at this column
            const testPiece = Object.assign({}, this.game.currentPiece);
            testPiece.x = col;
            
            // Find drop position
            while (testPiece.canMove(0, 1, this.game.grid)) {
                testPiece.y++;
            }
            
            // Count holes that would be created
            const holes = this.countHolesAfterPlacement(testPiece);
            
            if (holes < minHoles) {
                minHoles = holes;
                bestColumn = col;
            }
        }
        
        return bestColumn;
    }
    
    calculateOptimalRotation() {
        if (!this.game.currentPiece) return null;
        
        // Test all 4 rotations
        let bestRotation = 0;
        let bestScore = -Infinity;
        
        for (let rot = 0; rot < 4; rot++) {
            const testPiece = Object.assign({}, this.game.currentPiece);
            
            // Apply rotations
            for (let i = 0; i < rot; i++) {
                testPiece.rotate(this.game.grid);
            }
            
            // Evaluate position
            const score = this.evaluatePosition(testPiece);
            
            if (score > bestScore) {
                bestScore = score;
                bestRotation = rot;
            }
        }
        
        return bestRotation;
    }
    
    calculateFullSolution() {
        if (!this.game.currentPiece) return null;
        
        // Find best position and rotation
        let bestSolution = null;
        let bestScore = -Infinity;
        
        for (let rot = 0; rot < 4; rot++) {
            for (let col = 0; col < this.game.grid.width; col++) {
                const testPiece = Object.assign({}, this.game.currentPiece);
                
                // Apply rotation
                for (let i = 0; i < rot; i++) {
                    testPiece.rotate(this.game.grid);
                }
                
                // Set position
                testPiece.x = col;
                
                // Check if valid
                if (!testPiece.isValidPosition(this.game.grid)) continue;
                
                // Find drop position
                while (testPiece.canMove(0, 1, this.game.grid)) {
                    testPiece.y++;
                }
                
                // Evaluate
                const score = this.evaluatePosition(testPiece);
                
                if (score > bestScore) {
                    bestScore = score;
                    bestSolution = {
                        x: testPiece.x,
                        y: testPiece.y,
                        rotation: rot,
                        shape: testPiece.shape
                    };
                }
            }
        }
        
        return bestSolution;
    }
    
    evaluatePosition(piece) {
        // Scoring heuristic for position quality
        let score = 0;
        
        // Penalize height
        score -= piece.y * 10;
        
        // Penalize holes
        score -= this.countHolesAfterPlacement(piece) * 30;
        
        // Reward line clears
        score += this.countPotentialLineClears(piece) * 100;
        
        // Penalize column differences (bumpiness)
        score -= this.calculateBumpiness(piece) * 5;
        
        return score;
    }
    
    countHolesAfterPlacement(piece) {
        // Simulate placement and count holes
        let holes = 0;
        
        // This is a simplified version - in reality would need full simulation
        for (let y = piece.y + piece.shape.length; y < this.game.grid.height; y++) {
            for (let x = 0; x < this.game.grid.width; x++) {
                if (this.game.grid.cells[y][x] === 0) {
                    // Check if there's a block above
                    for (let checkY = y - 1; checkY >= 0; checkY--) {
                        if (this.game.grid.cells[checkY][x] !== 0) {
                            holes++;
                            break;
                        }
                    }
                }
            }
        }
        
        return holes;
    }
    
    countPotentialLineClears(piece) {
        // Count how many lines would be cleared
        let clears = 0;
        
        for (let y = 0; y < piece.shape.length; y++) {
            let lineComplete = true;
            for (let x = 0; x < this.game.grid.width; x++) {
                // Check if this position would be filled
                const pieceHasBlock = (x >= piece.x && 
                                       x < piece.x + piece.shape[0].length &&
                                       piece.shape[y][x - piece.x] !== 0);
                
                if (!pieceHasBlock && this.game.grid.cells[piece.y + y][x] === 0) {
                    lineComplete = false;
                    break;
                }
            }
            
            if (lineComplete) clears++;
        }
        
        return clears;
    }
    
    calculateBumpiness(piece) {
        // Calculate height differences between columns
        const heights = [];
        
        for (let x = 0; x < this.game.grid.width; x++) {
            let height = 0;
            for (let y = 0; y < this.game.grid.height; y++) {
                if (this.game.grid.cells[y][x] !== 0) {
                    height = this.game.grid.height - y;
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
    
    createColumnHighlight(column) {
        // Remove existing highlight
        this.clearHintAnimations();
        
        // Create highlight element
        const highlight = document.createElement('div');
        highlight.className = 'hint-column-highlight';
        highlight.style.cssText = `
            position: absolute;
            left: ${column * this.game.cellSize}px;
            top: 0;
            width: ${this.game.cellSize}px;
            height: 100%;
            background: linear-gradient(180deg, 
                rgba(255, 255, 0, 0.3) 0%, 
                rgba(255, 255, 0, 0.1) 100%);
            border: 2px solid #ffff00;
            pointer-events: none;
            animation: pulse 1s infinite;
            z-index: 10;
        `;
        
        // Add to canvas container
        const canvasContainer = this.game.canvas.parentElement;
        if (canvasContainer) {
            canvasContainer.style.position = 'relative';
            canvasContainer.appendChild(highlight);
            
            this.currentHintAnimation = highlight;
            
            // Remove after 5 seconds
            setTimeout(() => {
                if (highlight.parentNode) {
                    highlight.remove();
                }
            }, 5000);
        }
    }
    
    createRotationIndicator(rotations) {
        // Create rotation arrow indicator
        const indicator = document.createElement('div');
        indicator.className = 'hint-rotation-indicator';
        indicator.innerHTML = `🔄 × ${rotations}`;
        indicator.style.cssText = `
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            font-size: 3rem;
            color: #ffff00;
            text-shadow: 0 0 20px rgba(255, 255, 0, 0.5);
            animation: rotateHint 2s ease-out;
            pointer-events: none;
            z-index: 1000;
        `;
        
        document.body.appendChild(indicator);
        
        this.currentHintAnimation = indicator;
        
        // Remove after animation
        setTimeout(() => {
            if (indicator.parentNode) {
                indicator.remove();
            }
        }, 2000);
    }
    
    createSolutionGhost(solution) {
        // Clear existing
        this.clearHintAnimations();
        
        // Create enhanced ghost piece at solution position
        if (this.game.ghostPiece) {
            this.game.ghostPiece.x = solution.x;
            this.game.ghostPiece.y = solution.y;
            this.game.ghostPiece.shape = solution.shape;
            
            // Add glowing effect to ghost
            this.game.ghostPiece.hintMode = true;
        }
        
        // Show arrows indicating movement
        this.createMovementArrows(solution);
    }
    
    createMovementArrows(solution) {
        // Create visual arrows showing how to move piece
        const currentX = this.game.currentPiece.x;
        const targetX = solution.x;
        
        if (currentX !== targetX) {
            const arrow = document.createElement('div');
            arrow.className = 'hint-movement-arrow';
            arrow.innerHTML = targetX > currentX ? '→' : '←';
            arrow.style.cssText = `
                position: absolute;
                top: 100px;
                left: 50%;
                transform: translateX(-50%);
                font-size: 4rem;
                color: #ffff00;
                animation: slideHint 1s infinite;
                pointer-events: none;
                z-index: 100;
            `;
            
            const canvasContainer = this.game.canvas.parentElement;
            if (canvasContainer) {
                canvasContainer.appendChild(arrow);
                
                setTimeout(() => {
                    if (arrow.parentNode) {
                        arrow.remove();
                    }
                }, 5000);
            }
        }
    }
    
    clearHintAnimations() {
        // Remove any existing hint animations
        if (this.currentHintAnimation && this.currentHintAnimation.parentNode) {
            this.currentHintAnimation.remove();
        }
        
        // Clear hint mode on ghost piece
        if (this.game.ghostPiece) {
            this.game.ghostPiece.hintMode = false;
        }
    }
    
    startCooldownTimer() {
        // Update button every second during cooldown
        const interval = setInterval(() => {
            this.updateHintButton();
            
            if (Date.now() - this.lastHintTime >= this.cooldownDuration) {
                clearInterval(interval);
            }
        }, 1000);
    }
    
    showMessage(text, type) {
        if (this.game.uiManager) {
            this.game.uiManager.showMessage(text, type, 2000);
        }
    }
    
    cleanup() {
        // Remove hint button
        const hintButton = document.getElementById('hint-button');
        if (hintButton) {
            hintButton.remove();
        }
        
        // Clear animations
        this.clearHintAnimations();
    }
    
    getHintsUsed() {
        return this.hintsUsed;
    }
}