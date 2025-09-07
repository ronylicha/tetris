// Piece Style Manager - Handles different visual styles for tetromino pieces

export class PieceStyleManager {
    constructor() {
        this.currentStyle = 'standard';
        this.styles = {
            standard: {
                name: 'Standard',
                render: this.renderStandard.bind(this),
                borderWidth: 1,
                borderStyle: 'solid',
                innerGradient: false,
                unlocked: true
            },
            glass: {
                name: 'Glass',
                render: this.renderGlass.bind(this),
                borderWidth: 2,
                borderStyle: 'solid',
                innerGradient: true,
                opacity: 0.8,
                unlocked: false
            },
            pixel: {
                name: 'Pixel',
                render: this.renderPixel.bind(this),
                borderWidth: 0,
                borderStyle: 'none',
                pixelSize: 4,
                unlocked: false
            },
            neon: {
                name: 'Neon',
                render: this.renderNeon.bind(this),
                borderWidth: 2,
                borderStyle: 'solid',
                glowRadius: 10,
                unlocked: false
            },
            hologram: {
                name: 'Hologram',
                render: this.renderHologram.bind(this),
                borderWidth: 1,
                borderStyle: 'dashed',
                scanlines: true,
                opacity: 0.7,
                unlocked: false
            },
            crystal: {
                name: 'Crystal',
                render: this.renderCrystal.bind(this),
                borderWidth: 1,
                borderStyle: 'solid',
                facets: true,
                unlocked: false
            },
            animated: {
                name: 'Animated',
                render: this.renderAnimated.bind(this),
                borderWidth: 1,
                borderStyle: 'solid',
                pulse: true,
                unlocked: false
            },
            minimal: {
                name: 'Minimal',
                render: this.renderMinimal.bind(this),
                borderWidth: 0,
                borderStyle: 'none',
                simple: true,
                unlocked: false
            }
        };
        
        this.loadSavedStyle();
    }
    
    loadSavedStyle() {
        const saved = localStorage.getItem('tetris_piece_style');
        if (saved && this.styles[saved]) {
            this.currentStyle = saved;
        }
    }
    
    setStyle(styleName) {
        if (this.styles[styleName] && this.styles[styleName].unlocked) {
            this.currentStyle = styleName;
            localStorage.setItem('tetris_piece_style', styleName);
            return true;
        }
        return false;
    }
    
    getCurrentStyle() {
        return this.styles[this.currentStyle];
    }
    
    renderPiece(ctx, x, y, cellSize, color, isGhost = false) {
        const style = this.getCurrentStyle();
        style.render(ctx, x, y, cellSize, color, isGhost);
    }
    
    // Standard rendering
    renderStandard(ctx, x, y, cellSize, color, isGhost) {
        if (isGhost) {
            ctx.fillStyle = 'rgba(255, 255, 255, 0.2)';
            ctx.strokeStyle = 'rgba(255, 255, 255, 0.3)';
        } else {
            ctx.fillStyle = color;
            ctx.strokeStyle = this.darkenColor(color, 0.3);
        }
        
        ctx.fillRect(x, y, cellSize, cellSize);
        ctx.strokeRect(x, y, cellSize, cellSize);
    }
    
    // Glass style with transparency
    renderGlass(ctx, x, y, cellSize, color, isGhost) {
        const gradient = ctx.createLinearGradient(x, y, x + cellSize, y + cellSize);
        
        if (isGhost) {
            gradient.addColorStop(0, 'rgba(255, 255, 255, 0.1)');
            gradient.addColorStop(1, 'rgba(255, 255, 255, 0.3)');
        } else {
            const rgb = this.hexToRgb(color);
            gradient.addColorStop(0, `rgba(${rgb.r}, ${rgb.g}, ${rgb.b}, 0.6)`);
            gradient.addColorStop(0.5, `rgba(${rgb.r}, ${rgb.g}, ${rgb.b}, 0.8)`);
            gradient.addColorStop(1, `rgba(${rgb.r}, ${rgb.g}, ${rgb.b}, 0.6)`);
        }
        
        ctx.fillStyle = gradient;
        ctx.fillRect(x, y, cellSize, cellSize);
        
        // Glass reflection
        ctx.fillStyle = 'rgba(255, 255, 255, 0.3)';
        ctx.fillRect(x + 2, y + 2, cellSize / 3, cellSize / 3);
        
        ctx.strokeStyle = isGhost ? 'rgba(255, 255, 255, 0.2)' : color;
        ctx.lineWidth = 2;
        ctx.strokeRect(x, y, cellSize, cellSize);
        ctx.lineWidth = 1;
    }
    
    // Pixel art style
    renderPixel(ctx, x, y, cellSize, color, isGhost) {
        const pixelSize = 4;
        const numPixels = Math.floor(cellSize / pixelSize);
        
        ctx.fillStyle = isGhost ? 'rgba(255, 255, 255, 0.2)' : color;
        
        for (let i = 0; i < numPixels; i++) {
            for (let j = 0; j < numPixels; j++) {
                if ((i + j) % 2 === 0 || i === 0 || j === 0 || i === numPixels - 1 || j === numPixels - 1) {
                    ctx.fillRect(
                        x + i * pixelSize,
                        y + j * pixelSize,
                        pixelSize - 1,
                        pixelSize - 1
                    );
                }
            }
        }
    }
    
    // Neon style with glow
    renderNeon(ctx, x, y, cellSize, color, isGhost) {
        // Glow effect
        if (!isGhost) {
            ctx.shadowColor = color;
            ctx.shadowBlur = 10;
            ctx.shadowOffsetX = 0;
            ctx.shadowOffsetY = 0;
        }
        
        // Outer border
        ctx.strokeStyle = isGhost ? 'rgba(255, 255, 255, 0.3)' : color;
        ctx.lineWidth = 2;
        ctx.strokeRect(x + 2, y + 2, cellSize - 4, cellSize - 4);
        
        // Inner fill
        ctx.fillStyle = isGhost ? 'rgba(255, 255, 255, 0.1)' : `${color}33`;
        ctx.fillRect(x + 4, y + 4, cellSize - 8, cellSize - 8);
        
        // Reset shadow
        ctx.shadowBlur = 0;
        ctx.lineWidth = 1;
    }
    
    // Hologram style
    renderHologram(ctx, x, y, cellSize, color, isGhost) {
        const rgb = this.hexToRgb(color);
        
        // Holographic gradient
        const gradient = ctx.createLinearGradient(x, y, x, y + cellSize);
        if (isGhost) {
            gradient.addColorStop(0, 'rgba(255, 255, 255, 0.1)');
            gradient.addColorStop(0.5, 'rgba(255, 255, 255, 0.2)');
            gradient.addColorStop(1, 'rgba(255, 255, 255, 0.1)');
        } else {
            gradient.addColorStop(0, `rgba(${rgb.r}, ${rgb.g}, ${rgb.b}, 0.3)`);
            gradient.addColorStop(0.5, `rgba(${rgb.r}, ${rgb.g}, ${rgb.b}, 0.7)`);
            gradient.addColorStop(1, `rgba(${rgb.r}, ${rgb.g}, ${rgb.b}, 0.3)`);
        }
        
        ctx.fillStyle = gradient;
        ctx.fillRect(x, y, cellSize, cellSize);
        
        // Scanlines
        ctx.strokeStyle = isGhost ? 'rgba(255, 255, 255, 0.1)' : `${color}66`;
        for (let i = 0; i < cellSize; i += 4) {
            ctx.beginPath();
            ctx.moveTo(x, y + i);
            ctx.lineTo(x + cellSize, y + i);
            ctx.stroke();
        }
        
        // Border
        ctx.setLineDash([5, 5]);
        ctx.strokeStyle = isGhost ? 'rgba(255, 255, 255, 0.3)' : color;
        ctx.strokeRect(x, y, cellSize, cellSize);
        ctx.setLineDash([]);
    }
    
    // Crystal style with facets
    renderCrystal(ctx, x, y, cellSize, color, isGhost) {
        const centerX = x + cellSize / 2;
        const centerY = y + cellSize / 2;
        
        // Draw facets
        const facets = [
            { x1: x, y1: y, x2: centerX, y2: centerY },
            { x1: x + cellSize, y1: y, x2: centerX, y2: centerY },
            { x1: x + cellSize, y1: y + cellSize, x2: centerX, y2: centerY },
            { x1: x, y1: y + cellSize, x2: centerX, y2: centerY }
        ];
        
        facets.forEach((facet, i) => {
            const gradient = ctx.createLinearGradient(facet.x1, facet.y1, facet.x2, facet.y2);
            if (isGhost) {
                gradient.addColorStop(0, 'rgba(255, 255, 255, 0.3)');
                gradient.addColorStop(1, 'rgba(255, 255, 255, 0.1)');
            } else {
                const brightness = 0.7 + (i * 0.1);
                gradient.addColorStop(0, this.adjustBrightness(color, brightness));
                gradient.addColorStop(1, this.adjustBrightness(color, brightness * 0.5));
            }
            
            ctx.fillStyle = gradient;
            ctx.beginPath();
            ctx.moveTo(facet.x1, facet.y1);
            ctx.lineTo(facet.x2, facet.y2);
            ctx.lineTo(
                facet.x1 === x ? x : x + cellSize,
                facet.y1 === y ? y + cellSize : y
            );
            ctx.closePath();
            ctx.fill();
            ctx.stroke();
        });
        
        ctx.strokeStyle = isGhost ? 'rgba(255, 255, 255, 0.3)' : this.darkenColor(color, 0.4);
        ctx.strokeRect(x, y, cellSize, cellSize);
    }
    
    // Animated pulsing style
    renderAnimated(ctx, x, y, cellSize, color, isGhost) {
        const time = Date.now() / 1000;
        const pulse = Math.sin(time * 3) * 0.3 + 0.7;
        
        if (!isGhost) {
            ctx.fillStyle = this.adjustBrightness(color, pulse);
        } else {
            ctx.fillStyle = `rgba(255, 255, 255, ${0.2 * pulse})`;
        }
        
        const offset = (1 - pulse) * cellSize * 0.1;
        ctx.fillRect(
            x + offset,
            y + offset,
            cellSize - offset * 2,
            cellSize - offset * 2
        );
        
        ctx.strokeStyle = isGhost ? 'rgba(255, 255, 255, 0.3)' : color;
        ctx.strokeRect(x, y, cellSize, cellSize);
    }
    
    // Minimal flat style
    renderMinimal(ctx, x, y, cellSize, color, isGhost) {
        ctx.fillStyle = isGhost ? 'rgba(255, 255, 255, 0.15)' : color;
        ctx.fillRect(x + 1, y + 1, cellSize - 2, cellSize - 2);
    }
    
    // Helper functions
    hexToRgb(hex) {
        const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
        return result ? {
            r: parseInt(result[1], 16),
            g: parseInt(result[2], 16),
            b: parseInt(result[3], 16)
        } : { r: 255, g: 255, b: 255 };
    }
    
    darkenColor(color, amount) {
        const rgb = this.hexToRgb(color);
        return `rgb(${Math.floor(rgb.r * (1 - amount))}, ${Math.floor(rgb.g * (1 - amount))}, ${Math.floor(rgb.b * (1 - amount))})`;
    }
    
    adjustBrightness(color, brightness) {
        const rgb = this.hexToRgb(color);
        return `rgb(${Math.min(255, Math.floor(rgb.r * brightness))}, ${Math.min(255, Math.floor(rgb.g * brightness))}, ${Math.min(255, Math.floor(rgb.b * brightness))})`;
    }
    
    unlockStyle(styleName) {
        if (this.styles[styleName]) {
            this.styles[styleName].unlocked = true;
            return true;
        }
        return false;
    }
}

// Export singleton instance
export const pieceStyleManager = new PieceStyleManager();