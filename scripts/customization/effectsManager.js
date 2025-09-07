// Effects Manager - Handles visual effects for the game

export class EffectsManager {
    constructor() {
        this.currentEffect = 'none';
        this.effects = {
            none: {
                name: 'None',
                enabled: false,
                unlocked: true
            },
            particles: {
                name: 'Particles',
                enabled: true,
                particleCount: 3,
                particleLifetime: 1000,
                particleSpeed: 2,
                unlocked: false
            },
            trails: {
                name: 'Trails',
                enabled: true,
                trailLength: 5,
                trailOpacity: 0.3,
                unlocked: false
            },
            explosions: {
                name: 'Explosions',
                enabled: true,
                explosionSize: 30,
                explosionDuration: 500,
                unlocked: false
            },
            lightning: {
                name: 'Lightning',
                enabled: true,
                lightningFrequency: 0.3,
                lightningDuration: 200,
                unlocked: false
            },
            shatter: {
                name: 'Shatter',
                enabled: true,
                shatterPieces: 8,
                shatterSpeed: 5,
                unlocked: false
            },
            quantum: {
                name: 'Quantum',
                enabled: true,
                quantumPhase: true,
                quantumGlitch: true,
                unlocked: false
            },
            matrix: {
                name: 'Matrix',
                enabled: true,
                matrixRain: true,
                matrixSpeed: 1,
                unlocked: false
            }
        };
        
        this.particles = [];
        this.trails = [];
        this.explosions = [];
        this.lightningBolts = [];
        this.shatterPieces = [];
        
        this.loadSavedEffect();
    }
    
    loadSavedEffect() {
        const saved = localStorage.getItem('tetris_effect');
        if (saved && this.effects[saved]) {
            this.currentEffect = saved;
        }
    }
    
    setEffect(effectName) {
        if (this.effects[effectName] && this.effects[effectName].unlocked) {
            this.currentEffect = effectName;
            localStorage.setItem('tetris_effect', effectName);
            this.clearAllEffects();
            return true;
        }
        return false;
    }
    
    getCurrentEffect() {
        return this.effects[this.currentEffect];
    }
    
    clearAllEffects() {
        this.particles = [];
        this.trails = [];
        this.explosions = [];
        this.lightningBolts = [];
        this.shatterPieces = [];
    }
    
    // Called when a piece is placed
    onPiecePlaced(x, y, width, height, color) {
        const effect = this.getCurrentEffect();
        if (!effect.enabled) return;
        
        switch (this.currentEffect) {
            case 'particles':
                this.createParticles(x, y, width, height, color);
                break;
            case 'explosions':
                this.createExplosion(x + width/2, y + height/2, color);
                break;
            case 'shatter':
                this.createShatter(x, y, width, height, color);
                break;
            case 'quantum':
                this.createQuantumEffect(x, y, width, height, color);
                break;
        }
    }
    
    // Called when lines are cleared
    onLinesCleared(lines, y, color) {
        const effect = this.getCurrentEffect();
        if (!effect.enabled) return;
        
        switch (this.currentEffect) {
            case 'particles':
                lines.forEach(lineY => {
                    for (let i = 0; i < 10; i++) {
                        this.createParticles(i * 30, lineY * 30, 30, 30, color);
                    }
                });
                break;
            case 'lightning':
                this.createLightning(lines);
                break;
            case 'explosions':
                lines.forEach(lineY => {
                    this.createExplosion(150, lineY * 30 + 15, '#FFD700');
                });
                break;
            case 'shatter':
                lines.forEach(lineY => {
                    for (let i = 0; i < 10; i++) {
                        this.createShatter(i * 30, lineY * 30, 30, 30, '#FFD700');
                    }
                });
                break;
        }
    }
    
    // Called when a piece moves
    onPieceMoved(x, y, color) {
        const effect = this.getCurrentEffect();
        if (!effect.enabled) return;
        
        if (this.currentEffect === 'trails') {
            this.addTrail(x, y, color);
        }
    }
    
    // Particle system
    createParticles(x, y, width, height, color) {
        const effect = this.effects.particles;
        for (let i = 0; i < effect.particleCount; i++) {
            this.particles.push({
                x: x + width / 2,
                y: y + height / 2,
                vx: (Math.random() - 0.5) * effect.particleSpeed,
                vy: (Math.random() - 0.5) * effect.particleSpeed,
                life: effect.particleLifetime,
                maxLife: effect.particleLifetime,
                color: color,
                size: Math.random() * 3 + 2
            });
        }
    }
    
    // Trail effect
    addTrail(x, y, color) {
        const effect = this.effects.trails;
        this.trails.push({
            x: x,
            y: y,
            color: color,
            opacity: effect.trailOpacity,
            age: 0
        });
        
        // Keep trail length limited
        if (this.trails.length > effect.trailLength * 10) {
            this.trails.shift();
        }
    }
    
    // Explosion effect
    createExplosion(x, y, color) {
        const effect = this.effects.explosions;
        this.explosions.push({
            x: x,
            y: y,
            radius: 0,
            maxRadius: effect.explosionSize,
            duration: effect.explosionDuration,
            time: 0,
            color: color
        });
    }
    
    // Lightning effect
    createLightning(lines) {
        const effect = this.effects.lightning;
        lines.forEach(lineY => {
            this.lightningBolts.push({
                startX: 0,
                endX: 300,
                y: lineY * 30 + 15,
                segments: this.generateLightningPath(0, 300),
                duration: effect.lightningDuration,
                time: 0
            });
        });
    }
    
    generateLightningPath(startX, endX) {
        const segments = [];
        const steps = 10;
        const stepX = (endX - startX) / steps;
        
        for (let i = 0; i <= steps; i++) {
            segments.push({
                x: startX + i * stepX,
                y: (Math.random() - 0.5) * 20
            });
        }
        
        return segments;
    }
    
    // Shatter effect
    createShatter(x, y, width, height, color) {
        const effect = this.effects.shatter;
        const pieceSize = width / effect.shatterPieces;
        
        for (let i = 0; i < effect.shatterPieces; i++) {
            this.shatterPieces.push({
                x: x + (i % 2) * pieceSize,
                y: y + Math.floor(i / 2) * pieceSize,
                vx: (Math.random() - 0.5) * effect.shatterSpeed,
                vy: Math.random() * effect.shatterSpeed,
                rotation: 0,
                rotationSpeed: (Math.random() - 0.5) * 0.2,
                size: pieceSize,
                color: color,
                life: 1000
            });
        }
    }
    
    // Quantum effect
    createQuantumEffect(x, y, width, height, color) {
        // Create phase shifting particles
        for (let i = 0; i < 5; i++) {
            this.particles.push({
                x: x + Math.random() * width,
                y: y + Math.random() * height,
                vx: 0,
                vy: 0,
                life: 2000,
                maxLife: 2000,
                color: color,
                size: Math.random() * 10 + 5,
                phase: Math.random() * Math.PI * 2,
                phaseSpeed: 0.1,
                quantum: true
            });
        }
    }
    
    // Update all effects
    update(deltaTime) {
        // Update particles
        this.particles = this.particles.filter(particle => {
            particle.x += particle.vx;
            particle.y += particle.vy;
            particle.life -= deltaTime;
            
            if (particle.quantum) {
                particle.phase += particle.phaseSpeed;
            }
            
            return particle.life > 0;
        });
        
        // Update trails
        this.trails = this.trails.filter(trail => {
            trail.age += deltaTime;
            trail.opacity *= 0.95;
            return trail.opacity > 0.01;
        });
        
        // Update explosions
        this.explosions = this.explosions.filter(explosion => {
            explosion.time += deltaTime;
            explosion.radius = (explosion.time / explosion.duration) * explosion.maxRadius;
            return explosion.time < explosion.duration;
        });
        
        // Update lightning
        this.lightningBolts = this.lightningBolts.filter(bolt => {
            bolt.time += deltaTime;
            return bolt.time < bolt.duration;
        });
        
        // Update shatter pieces
        this.shatterPieces = this.shatterPieces.filter(piece => {
            piece.x += piece.vx;
            piece.y += piece.vy;
            piece.vy += 0.2; // Gravity
            piece.rotation += piece.rotationSpeed;
            piece.life -= deltaTime;
            return piece.life > 0 && piece.y < 600; // Canvas height
        });
    }
    
    // Render all effects
    render(ctx) {
        // Render trails
        this.trails.forEach(trail => {
            ctx.fillStyle = `${trail.color}${Math.floor(trail.opacity * 255).toString(16).padStart(2, '0')}`;
            ctx.fillRect(trail.x, trail.y, 30, 30);
        });
        
        // Render particles
        this.particles.forEach(particle => {
            const opacity = particle.life / particle.maxLife;
            
            if (particle.quantum) {
                // Quantum phase effect
                const phase = Math.sin(particle.phase);
                ctx.fillStyle = `${particle.color}${Math.floor(opacity * phase * 255).toString(16).padStart(2, '0')}`;
                ctx.fillRect(
                    particle.x - particle.size * phase / 2,
                    particle.y - particle.size * phase / 2,
                    particle.size * phase,
                    particle.size * phase
                );
            } else {
                ctx.fillStyle = `${particle.color}${Math.floor(opacity * 255).toString(16).padStart(2, '0')}`;
                ctx.beginPath();
                ctx.arc(particle.x, particle.y, particle.size, 0, Math.PI * 2);
                ctx.fill();
            }
        });
        
        // Render explosions
        this.explosions.forEach(explosion => {
            const opacity = 1 - (explosion.time / explosion.duration);
            ctx.strokeStyle = `${explosion.color}${Math.floor(opacity * 255).toString(16).padStart(2, '0')}`;
            ctx.lineWidth = 3 * opacity;
            ctx.beginPath();
            ctx.arc(explosion.x, explosion.y, explosion.radius, 0, Math.PI * 2);
            ctx.stroke();
            ctx.lineWidth = 1;
        });
        
        // Render lightning
        this.lightningBolts.forEach(bolt => {
            const opacity = 1 - (bolt.time / bolt.duration);
            ctx.strokeStyle = `rgba(255, 255, 255, ${opacity})`;
            ctx.lineWidth = 2;
            ctx.shadowBlur = 10;
            ctx.shadowColor = '#00ffff';
            
            ctx.beginPath();
            bolt.segments.forEach((segment, i) => {
                if (i === 0) {
                    ctx.moveTo(segment.x, bolt.y + segment.y);
                } else {
                    ctx.lineTo(segment.x, bolt.y + segment.y);
                }
            });
            ctx.stroke();
            
            ctx.shadowBlur = 0;
            ctx.lineWidth = 1;
        });
        
        // Render shatter pieces
        this.shatterPieces.forEach(piece => {
            ctx.save();
            ctx.translate(piece.x + piece.size / 2, piece.y + piece.size / 2);
            ctx.rotate(piece.rotation);
            ctx.fillStyle = `${piece.color}${Math.floor((piece.life / 1000) * 255).toString(16).padStart(2, '0')}`;
            ctx.fillRect(-piece.size / 2, -piece.size / 2, piece.size, piece.size);
            ctx.restore();
        });
        
        // Matrix rain effect
        if (this.currentEffect === 'matrix' && this.effects.matrix.enabled) {
            this.renderMatrixRain(ctx);
        }
    }
    
    renderMatrixRain(ctx) {
        // This would need to be integrated with the game canvas
        // For now, just a placeholder
        ctx.fillStyle = 'rgba(0, 255, 0, 0.05)';
        ctx.fillRect(0, 0, ctx.canvas.width, ctx.canvas.height);
    }
    
    unlockEffect(effectName) {
        if (this.effects[effectName]) {
            this.effects[effectName].unlocked = true;
            return true;
        }
        return false;
    }
}

// Export singleton instance
export const effectsManager = new EffectsManager();