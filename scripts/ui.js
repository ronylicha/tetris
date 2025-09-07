// Modern Tetris - UI Management and Effects

import { LeaderboardManager, ScoreSaver } from './leaderboard.js';

export class UIManager {
    constructor(game) {
        this.game = game;
        this.elements = this.getUIElements();
        this.particleSystem = new ParticleSystem();
        this.animations = new Map();
        
        // Initialize score and leaderboard systems
        this.scoreSaver = new ScoreSaver();
        this.leaderboardManager = new LeaderboardManager();
        
        // Mode-specific UI elements
        this.modeUI = null;
        this.currentModeDisplay = null;
        
        this.initializeUI();
    }

    getUIElements() {
        return {
            score: document.getElementById('score'),
            lines: document.getElementById('lines'),
            level: document.getElementById('level'),
            overlay: document.getElementById('game-overlay'),
            overlayTitle: document.getElementById('overlay-title'),
            overlayMessage: document.getElementById('overlay-message'),
            startButton: document.getElementById('start-button'),
            restartButton: document.getElementById('restart-button'),
            leaderboardButton: document.getElementById('leaderboard-button'),
            holdPiece: document.querySelector('.hold-piece'),
            nextPieces: [
                document.getElementById('next-0'),
                document.getElementById('next-1'),
                document.getElementById('next-2')
            ],
            particlesContainer: document.getElementById('particles'),
            
            // Mobile elements for synchronization
            mobileHold: document.getElementById('mobile-hold-piece'),
            mobileScore: document.querySelectorAll('[data-sync="score"]'),
            mobileLines: document.querySelectorAll('[data-sync="lines"]'),
            mobileLevel: document.querySelectorAll('[data-sync="level"]'),
            mobileNext: [
                document.getElementById('mobile-next-0'),
                document.getElementById('mobile-next-1'),
                document.getElementById('mobile-next-2')
            ]
        };
    }

    initializeUI() {
        // Button event listeners
        if (this.elements.startButton) {
            this.elements.startButton.addEventListener('click', async () => {
                // Trigger audio context on first user interaction
                if (this.game.audioManager) {
                    await this.game.audioManager.resumeAudioContext();
                }
                this.game.start();
            });
        }
        
        if (this.elements.restartButton) {
            this.elements.restartButton.addEventListener('click', () => {
                this.game.restart();
            });
        }
        
        if (this.elements.leaderboardButton) {
            this.elements.leaderboardButton.addEventListener('click', () => {
                this.leaderboardManager.showLeaderboard();
            });
        }
        
        // Initialize particle system
        this.particleSystem.init(this.elements.particlesContainer);
    }

    // Update game stats display
    updateStats(stats) {
        this.updateScore(stats.score);
        this.updateLines(stats.lines);
        this.updateLevel(stats.level);
    }

    updateScore(score) {
        if (this.elements.score) {
            const oldScore = parseInt(this.elements.score.textContent) || 0;
            this.elements.score.textContent = score.toLocaleString();
            
            // Update mobile elements
            this.elements.mobileScore.forEach(el => {
                if (el) el.textContent = score.toLocaleString();
            });
            
            // Animate score increase
            if (score > oldScore) {
                this.elements.score.classList.add('animate-score-pop');
                setTimeout(() => {
                    this.elements.score.classList.remove('animate-score-pop');
                }, 400);
            }
        }
    }

    updateLines(lines) {
        if (this.elements.lines) {
            this.elements.lines.textContent = lines;
        }
        
        // Update mobile elements
        this.elements.mobileLines.forEach(el => {
            if (el) el.textContent = lines;
        });
    }

    updateLevel(level) {
        if (this.elements.level) {
            const oldLevel = parseInt(this.elements.level.textContent) || 1;
            this.elements.level.textContent = level;
            
            // Update mobile elements
            this.elements.mobileLevel.forEach(el => {
                if (el) el.textContent = level;
            });
            
            // Animate level up
            if (level > oldLevel) {
                this.elements.level.classList.add('animate-level-up');
                setTimeout(() => {
                    this.elements.level.classList.remove('animate-level-up');
                }, 1000);
                
                // Create level up particles
                this.particleSystem.createLevelUpEffect();
            }
        }
    }

    // Update hold piece display
    updateHoldPiece(piece) {
        // Update desktop hold piece
        if (this.elements.holdPiece) {
            this.elements.holdPiece.innerHTML = '';
            
            if (piece) {
                const miniGrid = this.createMiniGrid(piece);
                this.elements.holdPiece.appendChild(miniGrid);
                
                // Animate swap
                this.elements.holdPiece.classList.add('animate-swap');
                setTimeout(() => {
                    this.elements.holdPiece.classList.remove('animate-swap');
                }, 600);
            }
        }
        
        // Update mobile hold piece
        if (this.elements.mobileHold) {
            this.elements.mobileHold.innerHTML = '';
            
            if (piece) {
                const mobileGrid = this.createMiniGrid(piece, true); // true for mobile size
                this.elements.mobileHold.appendChild(mobileGrid);
            }
        }
    }

    // Update next pieces display
    updateNextPieces(pieces) {
        // Update desktop next pieces
        pieces.forEach((pieceType, index) => {
            if (this.elements.nextPieces[index]) {
                this.elements.nextPieces[index].innerHTML = '';
                
                if (pieceType) {
                    const { Piece, PIECE_COLORS } = this.game.pieceModule;
                    const piece = new Piece(pieceType);
                    const miniGrid = this.createMiniGrid(piece);
                    this.elements.nextPieces[index].appendChild(miniGrid);
                    
                    // Animate new piece
                    if (index === 0) {
                        this.elements.nextPieces[index].classList.add('slide-in-right');
                        setTimeout(() => {
                            this.elements.nextPieces[index].classList.remove('slide-in-right');
                        }, 400);
                    }
                }
            }
        });
        
        // Update mobile next pieces
        pieces.forEach((pieceType, index) => {
            if (this.elements.mobileNext[index]) {
                this.elements.mobileNext[index].innerHTML = '';
                
                if (pieceType) {
                    const { Piece, PIECE_COLORS } = this.game.pieceModule;
                    const piece = new Piece(pieceType);
                    const mobileGrid = this.createMiniGrid(piece, true); // true for mobile size
                    this.elements.mobileNext[index].appendChild(mobileGrid);
                }
            }
        });
    }

    // Create mini grid for piece preview
    createMiniGrid(piece, isMobile = false) {
        const container = document.createElement('div');
        container.className = 'mini-grid';
        
        const gap = isMobile ? '0.5px' : '1px';
        const borderRadius = isMobile ? '1px' : '2px';
        const glowSize = isMobile ? '6px' : '10px';
        
        container.style.cssText = `
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: ${gap};
            width: 100%;
            height: 100%;
        `;
        
        const shape = piece.getCurrentShape();
        
        for (let y = 0; y < 4; y++) {
            for (let x = 0; x < 4; x++) {
                const cell = document.createElement('div');
                cell.style.cssText = `
                    aspect-ratio: 1;
                    border-radius: ${borderRadius};
                    transition: all 0.2s ease;
                `;
                
                if (shape[y] && shape[y][x]) {
                    cell.style.backgroundColor = piece.color;
                    cell.style.boxShadow = `0 0 ${glowSize} ${piece.color}40`;
                    cell.style.border = `1px solid ${piece.color}`;
                } else {
                    cell.style.backgroundColor = 'transparent';
                }
                
                container.appendChild(cell);
            }
        }
        
        return container;
    }

    // Show game overlay
    showOverlay(title, message, showRestart = false) {
        if (!this.elements.overlay) return;
        
        this.elements.overlay.classList.remove('hidden');
        
        if (this.elements.overlayTitle) {
            this.elements.overlayTitle.textContent = title;
        }
        
        if (this.elements.overlayMessage) {
            this.elements.overlayMessage.textContent = message;
        }
        
        if (this.elements.startButton) {
            this.elements.startButton.style.display = showRestart ? 'none' : 'inline-block';
        }
        
        if (this.elements.restartButton) {
            this.elements.restartButton.style.display = showRestart ? 'inline-block' : 'none';
        }
        
        // Animate overlay appearance
        this.elements.overlay.classList.add('animate-game-over');
        setTimeout(() => {
            this.elements.overlay.classList.remove('animate-game-over');
        }, 800);
    }

    // Hide game overlay
    hideOverlay() {
        if (this.elements.overlay) {
            this.elements.overlay.classList.add('hidden');
        }
    }
    
    // Update mode-specific display
    updateModeDisplay(modeConfig) {
        if (!modeConfig) return;
        
        this.modeUI = modeConfig;
        
        // Show/hide UI elements based on mode
        if (this.elements.score) {
            this.elements.score.parentElement.style.display = modeConfig.showScore ? 'block' : 'none';
        }
        if (this.elements.lines) {
            this.elements.lines.parentElement.style.display = modeConfig.showLines ? 'block' : 'none';
        }
        if (this.elements.level) {
            this.elements.level.parentElement.style.display = modeConfig.showLevel ? 'block' : 'none';
        }
        
        // Add mode-specific elements
        if (modeConfig.customDisplay) {
            this.createModeSpecificUI(modeConfig.customDisplay);
        }
    }
    
    // Create mode-specific UI elements
    createModeSpecificUI(customDisplay) {
        // Remove existing mode UI if any
        if (this.currentModeDisplay) {
            this.currentModeDisplay.remove();
        }
        
        const modeUIContainer = document.createElement('div');
        modeUIContainer.className = 'mode-specific-ui';
        
        // Add timer for Sprint mode
        if (customDisplay.timer) {
            const timerElement = document.createElement('div');
            timerElement.className = 'sprint-timer';
            timerElement.id = 'mode-timer';
            timerElement.textContent = customDisplay.timer;
            modeUIContainer.appendChild(timerElement);
        }
        
        // Add progress bar for Marathon mode
        if (customDisplay.progress) {
            const progressContainer = document.createElement('div');
            progressContainer.className = 'marathon-progress';
            const progressBar = document.createElement('div');
            progressBar.className = 'marathon-progress-bar';
            progressBar.style.width = customDisplay.progress;
            progressContainer.appendChild(progressBar);
            modeUIContainer.appendChild(progressContainer);
        }
        
        // Add objective display for Puzzle mode
        if (customDisplay.objective) {
            const objectiveElement = document.createElement('div');
            objectiveElement.className = 'puzzle-objective';
            objectiveElement.innerHTML = `
                <div class="puzzle-objective-title">Objective</div>
                <div class="puzzle-objective-desc" id="puzzle-objective-text">${customDisplay.objective}</div>
            `;
            modeUIContainer.appendChild(objectiveElement);
        }
        
        // Add pieces counter for Puzzle mode
        if (customDisplay.pieces) {
            const piecesElement = document.createElement('div');
            piecesElement.className = 'puzzle-pieces';
            piecesElement.innerHTML = `
                <div class="puzzle-pieces-title">Pieces Used</div>
                <div class="puzzle-pieces-count" id="puzzle-pieces-count">${customDisplay.pieces}</div>
            `;
            modeUIContainer.appendChild(piecesElement);
        }
        
        // Add puzzle number display
        if (customDisplay.puzzle) {
            const puzzleNumElement = document.createElement('div');
            puzzleNumElement.className = 'puzzle-number';
            puzzleNumElement.innerHTML = `
                <div class="puzzle-number-text">Puzzle ${customDisplay.puzzle}</div>
            `;
            modeUIContainer.appendChild(puzzleNumElement);
        }
        
        // Insert into game area
        const gameBoard = document.querySelector('.game-board');
        if (gameBoard) {
            gameBoard.appendChild(modeUIContainer);
            this.currentModeDisplay = modeUIContainer;
        }
    }
    
    // Update mode-specific UI continuously
    updateModeUI(modeConfig) {
        if (!modeConfig || !modeConfig.customDisplay) return;
        
        // Update timer for Sprint mode
        if (modeConfig.customDisplay.timer) {
            const timerElement = document.getElementById('mode-timer');
            if (timerElement) {
                timerElement.textContent = modeConfig.customDisplay.timer;
            }
        }
        
        // Update progress for Marathon mode
        if (modeConfig.customDisplay.progress) {
            const progressBar = document.querySelector('.marathon-progress-bar');
            if (progressBar) {
                progressBar.style.width = modeConfig.customDisplay.progress;
            }
        }
        
        // Update objective for Puzzle mode
        if (modeConfig.customDisplay.objective) {
            const objectiveElement = document.getElementById('puzzle-objective-text');
            if (objectiveElement) {
                objectiveElement.textContent = modeConfig.customDisplay.objective;
            }
        }
        
        // Update pieces counter for Puzzle mode
        if (modeConfig.customDisplay.pieces) {
            const piecesElement = document.getElementById('puzzle-pieces-count');
            if (piecesElement) {
                piecesElement.textContent = modeConfig.customDisplay.pieces;
            }
        }
    }
    
    // Show mode-specific messages
    showMessage(text, type = 'info', duration = 2000) {
        const messageElement = document.createElement('div');
        messageElement.className = `game-message message-${type}`;
        messageElement.textContent = text;
        messageElement.style.cssText = `
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: rgba(0, 0, 0, 0.9);
            color: white;
            padding: 20px 30px;
            border-radius: 10px;
            font-size: 1.2rem;
            z-index: 10000;
            border: 2px solid var(--neon-blue);
            box-shadow: 0 0 20px rgba(0, 255, 255, 0.5);
        `;
        
        document.body.appendChild(messageElement);
        
        setTimeout(() => {
            messageElement.style.opacity = '0';
            messageElement.style.transition = 'opacity 0.5s';
            setTimeout(() => messageElement.remove(), 500);
        }, duration);
    }
    
    // Show puzzle completion
    showPuzzleComplete(puzzle, stars, stats) {
        // Create a more elaborate completion screen
        const overlay = document.createElement('div');
        overlay.className = 'puzzle-complete-overlay';
        overlay.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.9);
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            z-index: 10000;
            animation: fadeIn 0.5s;
        `;
        
        const content = document.createElement('div');
        content.className = 'puzzle-complete-content';
        content.style.cssText = `
            background: linear-gradient(135deg, #1a1a2e 0%, #0f0f23 100%);
            border: 3px solid #ff8800;
            border-radius: 20px;
            padding: 40px;
            text-align: center;
            box-shadow: 0 0 40px rgba(255, 136, 0, 0.5);
            max-width: 500px;
        `;
        
        content.innerHTML = `
            <h2 style="color: #ff8800; font-size: 2.5rem; margin-bottom: 20px;">
                🎉 PUZZLE COMPLETE! 🎉
            </h2>
            <h3 style="color: #fff; font-size: 1.5rem; margin-bottom: 15px;">
                Puzzle #${puzzle.id}: ${puzzle.name}
            </h3>
            <div style="font-size: 3rem; margin: 20px 0;">
                ${'⭐'.repeat(stars)}${'☆'.repeat(Math.max(0, 3 - stars))}
            </div>
            <div style="color: #ccc; margin: 20px 0;">
                <p>Lines Cleared: ${stats.linesCleared}</p>
                <p>Pieces Used: ${stats.piecesUsed}</p>
                <p>Time: ${stats.timeElapsed}s</p>
            </div>
            <div style="margin-top: 30px; display: flex; gap: 15px; justify-content: center;">
                <button id="next-puzzle-btn" style="
                    background: #ff8800;
                    color: white;
                    border: none;
                    padding: 15px 30px;
                    font-size: 1.2rem;
                    border-radius: 10px;
                    cursor: pointer;
                    font-weight: bold;
                    box-shadow: 0 4px 10px rgba(0,0,0,0.3);
                ">Next Puzzle →</button>
                <button id="retry-puzzle-btn" style="
                    background: #666;
                    color: white;
                    border: none;
                    padding: 15px 30px;
                    font-size: 1.2rem;
                    border-radius: 10px;
                    cursor: pointer;
                    box-shadow: 0 4px 10px rgba(0,0,0,0.3);
                ">Retry</button>
            </div>
        `;
        
        overlay.appendChild(content);
        document.body.appendChild(overlay);
        
        // Add event listeners
        const nextBtn = document.getElementById('next-puzzle-btn');
        const retryBtn = document.getElementById('retry-puzzle-btn');
        
        if (nextBtn) {
            nextBtn.addEventListener('click', () => {
                overlay.remove();
                // Load next puzzle
                if (this.game && this.game.gameMode && this.game.gameMode.loadPuzzle) {
                    const nextPuzzleId = puzzle.id + 1;
                    console.log(`Loading next puzzle: ${nextPuzzleId}`);
                    
                    // Reset game state first
                    this.game.state = 'playing';
                    this.game.gameMode.isComplete = false;
                    this.game.gameMode.pendingCompletion = false;
                    
                    // Load the next puzzle
                    this.game.gameMode.loadPuzzle(nextPuzzleId);
                    
                    // Clear the grid and reset the game
                    this.game.grid.reset();
                    this.game.gameMode.loadPuzzle(nextPuzzleId); // Load again to apply grid
                    
                    // Continue playing without full restart
                    this.game.currentPiece = null;
                }
            });
        }
        
        if (retryBtn) {
            retryBtn.addEventListener('click', () => {
                overlay.remove();
                // Retry current puzzle
                if (this.game && this.game.gameMode) {
                    // Reset game state
                    this.game.state = 'playing';
                    this.game.gameMode.isComplete = false;
                    this.game.gameMode.pendingCompletion = false;
                    
                    // Reload current puzzle
                    const currentPuzzleId = puzzle.id;
                    this.game.grid.reset();
                    this.game.gameMode.loadPuzzle(currentPuzzleId);
                    this.game.currentPiece = null;
                }
            });
        }
        
        // Auto-remove after 10 seconds if no action
        setTimeout(() => {
            if (overlay.parentNode) {
                overlay.remove();
            }
        }, 10000);
    }
    
    // Show puzzle failed
    showPuzzleFailed(puzzle, reason, stats) {
        const message = `Puzzle #${puzzle.id} Failed\n${reason}`;
        this.showMessage(message, 'error', 3000);
    }
    
    // Show puzzle selection menu
    async showPuzzleSelection() {
        // Use storage adapter for better compatibility
        const storage = window.TetrisStorage || { 
            load: (key) => Promise.resolve(JSON.parse(localStorage.getItem('tetris_' + key) || 'null'))
        };
        
        const progress = await storage.load('puzzle_progress') || {};
        const completedPuzzles = await storage.load('puzzle_completed') || [];
        const highestUnlocked = progress.highestUnlocked || 1;
        
        const overlay = document.createElement('div');
        overlay.className = 'puzzle-selection-overlay';
        overlay.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.95);
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            z-index: 10000;
            overflow-y: auto;
            padding: 20px;
        `;
        
        const content = document.createElement('div');
        content.style.cssText = `
            background: linear-gradient(135deg, #1a1a2e 0%, #0f0f23 100%);
            border: 3px solid #ff8800;
            border-radius: 20px;
            padding: 30px;
            max-width: 900px;
            width: 100%;
            max-height: 80vh;
            overflow-y: auto;
        `;
        
        let html = `
            <h2 style="color: #ff8800; font-size: 2rem; margin-bottom: 20px; text-align: center;">
                🧩 Select Puzzle
            </h2>
            <div style="text-align: center; margin-bottom: 20px; color: #ccc;">
                Progress: ${completedPuzzles.length}/150 puzzles completed
            </div>
        `;
        
        // Create category tabs
        const categories = ['tutorial', 'beginner', 'intermediate', 'advanced', 'expert', 'master', 'grandmaster'];
        html += '<div style="display: flex; gap: 10px; margin-bottom: 20px; flex-wrap: wrap; justify-content: center;">';
        categories.forEach(cat => {
            html += `
                <button class="category-tab" data-category="${cat}" style="
                    background: #333;
                    color: #fff;
                    border: 1px solid #555;
                    padding: 8px 15px;
                    border-radius: 5px;
                    cursor: pointer;
                    text-transform: capitalize;
                ">${cat}</button>
            `;
        });
        html += '</div>';
        
        // Puzzle grid container
        html += '<div id="puzzle-grid" style="display: grid; grid-template-columns: repeat(auto-fill, minmax(80px, 1fr)); gap: 10px; margin-top: 20px;"></div>';
        
        // Buttons
        html += `
            <div style="margin-top: 30px; display: flex; gap: 15px; justify-content: center;">
                ${progress.currentPuzzleId ? `
                    <button id="resume-puzzle-btn" style="
                        background: #00ff00;
                        color: #000;
                        border: none;
                        padding: 15px 30px;
                        font-size: 1.2rem;
                        border-radius: 10px;
                        cursor: pointer;
                        font-weight: bold;
                    ">Resume Puzzle #${progress.currentPuzzleId}</button>
                ` : ''}
                <button id="close-selection-btn" style="
                    background: #666;
                    color: white;
                    border: none;
                    padding: 15px 30px;
                    font-size: 1.2rem;
                    border-radius: 10px;
                    cursor: pointer;
                ">Close</button>
            </div>
        `;
        
        content.innerHTML = html;
        overlay.appendChild(content);
        document.body.appendChild(overlay);
        
        // Function to show puzzles for a category
        const showCategory = (category) => {
            const gridElement = document.getElementById('puzzle-grid');
            gridElement.innerHTML = '';
            
            // Get puzzles from puzzleData
            let puzzles = [];
            if (window.PUZZLES) {
                puzzles = window.PUZZLES.filter(p => p.category === category);
            }
            
            // Create puzzle buttons
            puzzles.forEach(puzzle => {
                const isCompleted = completedPuzzles.some(p => p.puzzleId === puzzle.id);
                const isUnlocked = puzzle.id <= highestUnlocked;
                
                const puzzleBtn = document.createElement('button');
                puzzleBtn.style.cssText = `
                    background: ${isCompleted ? '#00ff00' : (isUnlocked ? '#ff8800' : '#444')};
                    color: ${isCompleted ? '#000' : '#fff'};
                    border: 2px solid ${isCompleted ? '#00ff00' : (isUnlocked ? '#ff8800' : '#666')};
                    padding: 15px;
                    border-radius: 10px;
                    cursor: ${isUnlocked ? 'pointer' : 'not-allowed'};
                    font-size: 1rem;
                    font-weight: bold;
                    opacity: ${isUnlocked ? 1 : 0.5};
                    position: relative;
                `;
                
                puzzleBtn.innerHTML = `
                    #${puzzle.id}
                    ${isCompleted ? '<span style="position: absolute; top: 2px; right: 2px; font-size: 0.8rem;">✓</span>' : ''}
                `;
                
                puzzleBtn.disabled = !isUnlocked;
                
                if (isUnlocked) {
                    puzzleBtn.addEventListener('click', () => {
                        overlay.remove();
                        // Load selected puzzle
                        if (this.game && this.game.gameMode && this.game.gameMode.loadPuzzle) {
                            this.game.state = 'playing';
                            this.game.gameMode.isComplete = false;
                            this.game.gameMode.pendingCompletion = false;
                            this.game.gameMode.puzzleId = puzzle.id;
                            this.game.gameMode.loadPuzzle(puzzle.id);
                            this.game.gameMode.saveCurrentProgress();
                        }
                    });
                }
                
                gridElement.appendChild(puzzleBtn);
            });
        };
        
        // Add category tab listeners
        setTimeout(() => {
            const tabs = overlay.querySelectorAll('.category-tab');
            tabs.forEach(tab => {
                tab.addEventListener('click', () => {
                    // Update active tab styling
                    tabs.forEach(t => t.style.background = '#333');
                    tab.style.background = '#ff8800';
                    showCategory(tab.dataset.category);
                });
            });
            
            // Show first category by default
            if (tabs.length > 0) {
                tabs[0].click();
            }
            
            // Resume button
            const resumeBtn = document.getElementById('resume-puzzle-btn');
            if (resumeBtn) {
                resumeBtn.addEventListener('click', () => {
                    overlay.remove();
                    if (this.game && this.game.gameMode && this.game.gameMode.loadPuzzle) {
                        this.game.state = 'playing';
                        this.game.gameMode.isComplete = false;
                        this.game.gameMode.pendingCompletion = false;
                        this.game.gameMode.loadPuzzle(progress.currentPuzzleId);
                    }
                });
            }
            
            // Close button
            const closeBtn = document.getElementById('close-selection-btn');
            if (closeBtn) {
                closeBtn.addEventListener('click', () => {
                    overlay.remove();
                });
            }
        }, 0);
    }
    
    // Show continue prompt for Marathon mode
    showContinuePrompt(state, callback) {
        const promptDiv = document.createElement('div');
        promptDiv.style.cssText = `
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: linear-gradient(135deg, #1a1a2e 0%, #0f0f23 100%);
            padding: 30px;
            border-radius: 15px;
            border: 2px solid var(--neon-blue);
            z-index: 10000;
            text-align: center;
            color: white;
        `;
        promptDiv.innerHTML = `
            <h3>Continue Previous Game?</h3>
            <p>Lines: ${state.lines} | Level: ${state.level}</p>
            <button id="continue-yes" style="margin: 10px; padding: 10px 20px;">Continue</button>
            <button id="continue-no" style="margin: 10px; padding: 10px 20px;">New Game</button>
        `;
        
        document.body.appendChild(promptDiv);
        
        document.getElementById('continue-yes').onclick = () => {
            promptDiv.remove();
            callback(true);
        };
        document.getElementById('continue-no').onclick = () => {
            promptDiv.remove();
            callback(false);
        };
    }
    
    // Show load game prompt for Zen mode
    showLoadGamePrompt(saves, callback) {
        // Similar to continue prompt
        this.showMessage('Saved games available', 'info', 2000);
    }
    
    // Show power-up effect for Battle mode
    showPowerUp(type, target) {
        this.showMessage(`${type.toUpperCase()} activated!`, 'powerup', 1500);
    }
    
    // Show damage for Battle mode
    showDamage(target, lines) {
        const side = target === 'player' ? 'You' : 'AI';
        this.showMessage(`${side}: ${lines} damage!`, 'damage', 1000);
    }
    
    // Show round result for Battle mode
    showRoundResult(playerWon, round, playerWins, aiWins) {
        const message = playerWon ? 'Round Won!' : 'Round Lost!';
        this.showMessage(`${message}\nScore: ${playerWins}-${aiWins}`, playerWon ? 'success' : 'error', 2000);
    }
    
    // Show match result for Battle mode
    showMatchResult(result) {
        const message = result.won ? 'Victory!' : 'Defeat!';
        this.showMessage(message, result.won ? 'success' : 'error', 3000);
    }
    
    // Update AI grid for Battle mode
    updateAIGrid(grid) {
        // This would update a visual representation of the AI's grid
        // For now, just a placeholder
    }

    // Show pause overlay
    showPauseOverlay() {
        // Check if device supports touch for appropriate message
        const isTouchDevice = 'ontouchstart' in window || navigator.maxTouchPoints > 0;
        const message = isTouchDevice ? 
            'Press P or tap screen to resume' : 
            'Press P to resume';
        this.showOverlay('Game Paused', message);
    }

    // Show game over overlay
    async showGameOverOverlay(stats, specialAchievements = {}, mode = null, modeData = {}) {
        // Get current game mode if not provided
        if (!mode && this.game && this.game.gameMode) {
            mode = this.game.gameMode.name.toLowerCase();
        }
        
        // Create mode-specific message
        let message = `Final Score: ${stats.score.toLocaleString()}\nLines: ${stats.lines}\nLevel: ${stats.level}`;
        
        // Add mode-specific info
        if (mode === 'sprint' && modeData.time) {
            const minutes = Math.floor(modeData.time / 60000);
            const seconds = Math.floor((modeData.time % 60000) / 1000);
            const ms = Math.floor((modeData.time % 1000) / 10);
            message += `\nTime: ${minutes}:${seconds.toString().padStart(2, '0')}.${ms.toString().padStart(2, '0')}`;
            if (modeData.isNewRecord) {
                message += '\n🏆 NEW RECORD!';
            }
        } else if (mode === 'puzzle' && modeData.puzzleId) {
            message = `Puzzle #${modeData.puzzleId} ${modeData.isVictory ? 'Complete!' : 'Failed'}\n`;
            message += `${'\u2b50'.repeat(modeData.stars || 0)}\n`;
            message += `Score: ${stats.score.toLocaleString()}`;
        } else if (mode === 'marathon') {
            message += `\nProgress: ${stats.lines}/150 lines`;
            if (stats.lines >= 150) {
                message += '\n🎆 MARATHON COMPLETE!';
            }
        } else if (mode === 'zen') {
            const duration = modeData.duration || 0;
            message += `\nDuration: ${Math.floor(duration / 60)}m ${duration % 60}s`;
            message += `\nEfficiency: ${modeData.efficiency || '0'}%`;
        } else if (mode === 'battle') {
            message = modeData.victory ? 'VICTORY!' : 'DEFEAT';
            message += `\nScore: ${stats.score.toLocaleString()}`;
            if (modeData.wins !== undefined) {
                message += `\nWins: ${modeData.wins}`;
            }
        }
        
        const title = mode === 'puzzle' && modeData.isVictory ? 'Victory!' : 'Game Over';
        this.showOverlay(title, message, true);
        
        // Create game over particles
        this.particleSystem.createGameOverEffect();
        
        // Shake animation
        document.querySelector('.game-board').classList.add('animate-shake');
        setTimeout(() => {
            document.querySelector('.game-board').classList.remove('animate-shake');
        }, 500);
        
        // Check if this is a high score and show name input
        try {
            const isHighScore = await this.scoreSaver.isHighScore(stats.score);
            if (isHighScore || mode === 'sprint' || mode === 'puzzle') {
                setTimeout(() => {
                    this.scoreSaver.showNameInput(stats, specialAchievements, mode, modeData);
                }, 1500); // Delay to let game over animation play
            }
        } catch (error) {
            console.warn('Could not check high score status:', error);
            // Show name input anyway if we can't check
            setTimeout(() => {
                this.scoreSaver.showNameInput(stats, specialAchievements, mode, modeData);
            }, 1500);
        }
        
        // Show leaderboard button
        if (this.elements.leaderboardButton) {
            this.elements.leaderboardButton.style.display = 'inline-block';
        }
    }

    // Line clear effects
    showLineClearEffect(clearedLines, isSpecial = false) {
        clearedLines.forEach((lineY, index) => {
            setTimeout(() => {
                this.particleSystem.createLineClearEffect(lineY, isSpecial);
            }, index * 50);
        });
        
        // Special effects for Tetris
        if (clearedLines.length === 4) {
            this.particleSystem.createTetrisEffect();
        }
    }

    // T-Spin effect
    showTSpinEffect(position, isMini = false) {
        this.particleSystem.createTSpinEffect(position, isMini);
        
        // Flash effect
        const canvas = document.getElementById('game-canvas');
        if (canvas) {
            canvas.classList.add('animate-tspin');
            setTimeout(() => {
                canvas.classList.remove('animate-tspin');
            }, 300);
        }
    }

    // Combo effect
    showComboEffect(comboCount) {
        if (comboCount > 1) {
            this.particleSystem.createComboEffect(comboCount);
            
            // Update score with combo animation
            if (this.elements.score) {
                this.elements.score.classList.add('animate-combo');
                setTimeout(() => {
                    this.elements.score.classList.remove('animate-combo');
                }, 600);
            }
        }
    }

    // Perfect clear effect
    showPerfectClearEffect() {
        this.particleSystem.createPerfectClearEffect();
    }

    // Update UI theme
    updateTheme(theme) {
        document.documentElement.className = `theme-${theme}`;
    }

    // Cleanup
    destroy() {
        this.particleSystem.destroy();
        this.animations.clear();
    }
}

// Particle System for Visual Effects
class ParticleSystem {
    constructor() {
        this.particles = [];
        this.container = null;
        this.animationId = null;
    }

    init(container) {
        this.container = container;
        this.startAnimation();
    }

    createParticle(x, y, options = {}) {
        const particle = document.createElement('div');
        particle.className = `particle ${options.color || ''}`;
        
        const size = options.size || 4;
        particle.style.cssText = `
            position: absolute;
            left: ${x}px;
            top: ${y}px;
            width: ${size}px;
            height: ${size}px;
            background: ${options.background || 'var(--neon-blue)'};
            border-radius: 50%;
            pointer-events: none;
            z-index: 1000;
        `;
        
        if (this.container) {
            this.container.appendChild(particle);
            
            // Remove particle after animation
            setTimeout(() => {
                if (particle.parentNode) {
                    particle.parentNode.removeChild(particle);
                }
            }, options.duration || 2000);
        }
        
        return particle;
    }

    createLineClearEffect(lineY, isSpecial) {
        const canvas = document.getElementById('game-canvas');
        if (!canvas) return;
        
        const rect = canvas.getBoundingClientRect();
        const y = rect.top + (lineY * (rect.height / 20));
        
        for (let i = 0; i < 20; i++) {
            const x = rect.left + (i * (rect.width / 20));
            const particle = this.createParticle(x, y, {
                color: isSpecial ? 'yellow' : 'blue',
                size: Math.random() * 6 + 2,
                duration: 1000 + Math.random() * 1000
            });
            
            // Animate particle
            particle.style.animation = `particle-float ${1 + Math.random()}s ease-out forwards`;
        }
    }

    createTetrisEffect() {
        const canvas = document.getElementById('game-canvas');
        if (!canvas) return;
        
        const rect = canvas.getBoundingClientRect();
        const centerX = rect.left + rect.width / 2;
        const centerY = rect.top + rect.height / 2;
        
        for (let i = 0; i < 50; i++) {
            const angle = (i / 50) * Math.PI * 2;
            const radius = Math.random() * 100 + 50;
            const x = centerX + Math.cos(angle) * radius;
            const y = centerY + Math.sin(angle) * radius;
            
            this.createParticle(x, y, {
                color: ['pink', 'yellow', 'green', 'purple'][Math.floor(Math.random() * 4)],
                size: Math.random() * 8 + 4,
                duration: 2000
            });
        }
    }

    createTSpinEffect(position, isMini) {
        const canvas = document.getElementById('game-canvas');
        if (!canvas) return;
        
        const rect = canvas.getBoundingClientRect();
        const x = rect.left + (position.x * (rect.width / 10));
        const y = rect.top + (position.y * (rect.height / 20));
        
        const particleCount = isMini ? 15 : 30;
        const colors = isMini ? ['purple'] : ['purple', 'pink'];
        
        for (let i = 0; i < particleCount; i++) {
            this.createParticle(
                x + Math.random() * 40 - 20,
                y + Math.random() * 40 - 20,
                {
                    color: colors[Math.floor(Math.random() * colors.length)],
                    size: Math.random() * 6 + 3,
                    duration: 1500
                }
            );
        }
    }

    createComboEffect(comboCount) {
        const canvas = document.getElementById('game-canvas');
        if (!canvas) return;
        
        const rect = canvas.getBoundingClientRect();
        const centerX = rect.left + rect.width / 2;
        const centerY = rect.top + rect.height / 2;
        
        for (let i = 0; i < comboCount * 10; i++) {
            const x = centerX + (Math.random() - 0.5) * 200;
            const y = centerY + (Math.random() - 0.5) * 200;
            
            this.createParticle(x, y, {
                color: 'yellow',
                size: Math.random() * 8 + 4,
                duration: 1000 + comboCount * 200
            });
        }
    }

    createLevelUpEffect() {
        const centerX = window.innerWidth / 2;
        const centerY = window.innerHeight / 2;
        
        for (let i = 0; i < 100; i++) {
            const angle = (i / 100) * Math.PI * 2;
            const radius = Math.random() * 200 + 100;
            const x = centerX + Math.cos(angle) * radius;
            const y = centerY + Math.sin(angle) * radius;
            
            this.createParticle(x, y, {
                color: 'green',
                size: Math.random() * 6 + 2,
                duration: 3000
            });
        }
    }

    createGameOverEffect() {
        for (let i = 0; i < 200; i++) {
            const x = Math.random() * window.innerWidth;
            const y = Math.random() * window.innerHeight;
            
            this.createParticle(x, y, {
                color: 'red',
                size: Math.random() * 8 + 2,
                duration: 5000
            });
        }
    }

    createPerfectClearEffect() {
        for (let i = 0; i < 300; i++) {
            const x = Math.random() * window.innerWidth;
            const y = Math.random() * window.innerHeight;
            
            this.createParticle(x, y, {
                color: ['pink', 'yellow', 'green', 'blue', 'purple'][Math.floor(Math.random() * 5)],
                size: Math.random() * 10 + 4,
                duration: 4000
            });
        }
    }

    startAnimation() {
        // Animation loop for particle updates if needed
        const animate = () => {
            this.animationId = requestAnimationFrame(animate);
        };
        animate();
    }

    destroy() {
        if (this.animationId) {
            cancelAnimationFrame(this.animationId);
        }
        this.particles = [];
    }
}