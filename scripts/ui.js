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
            particlesContainer: document.getElementById('particles')
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
    }

    updateLevel(level) {
        if (this.elements.level) {
            const oldLevel = parseInt(this.elements.level.textContent) || 1;
            this.elements.level.textContent = level;
            
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
        if (!this.elements.holdPiece) return;
        
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

    // Update next pieces display
    updateNextPieces(pieces) {
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
    }

    // Create mini grid for piece preview
    createMiniGrid(piece) {
        const container = document.createElement('div');
        container.className = 'mini-grid';
        container.style.cssText = `
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 1px;
            width: 100%;
            height: 100%;
        `;
        
        const shape = piece.getCurrentShape();
        
        for (let y = 0; y < 4; y++) {
            for (let x = 0; x < 4; x++) {
                const cell = document.createElement('div');
                cell.style.cssText = `
                    aspect-ratio: 1;
                    border-radius: 2px;
                    transition: all 0.2s ease;
                `;
                
                if (shape[y] && shape[y][x]) {
                    cell.style.backgroundColor = piece.color;
                    cell.style.boxShadow = `0 0 10px ${piece.color}40`;
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

    // Show pause overlay
    showPauseOverlay() {
        this.showOverlay('Game Paused', 'Press P to resume');
    }

    // Show game over overlay
    async showGameOverOverlay(stats, specialAchievements = {}) {
        const message = `Final Score: ${stats.score.toLocaleString()}\nLines: ${stats.lines}\nLevel: ${stats.level}`;
        this.showOverlay('Game Over', message, true);
        
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
            if (isHighScore) {
                setTimeout(() => {
                    this.scoreSaver.showNameInput(stats, specialAchievements);
                }, 1500); // Delay to let game over animation play
            }
        } catch (error) {
            console.warn('Could not check high score status:', error);
            // Show name input anyway if we can't check
            setTimeout(() => {
                this.scoreSaver.showNameInput(stats, specialAchievements);
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