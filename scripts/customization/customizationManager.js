// Customization Manager - Central manager for all customization systems

import { themeManager } from './themeManager.js';
import { musicManager } from './musicManager.js';
import { pieceStyleManager } from './pieceStyleManager.js';
import { effectsManager } from './effectsManager.js';

export class CustomizationManager {
    constructor() {
        this.managers = {
            theme: themeManager,
            music: musicManager,
            piece_style: pieceStyleManager,
            effect: effectsManager
        };
        
        this.initializeEventListeners();
        this.applyAllCustomizations();
    }
    
    initializeEventListeners() {
        // Listen for customization changes from progression manager
        window.addEventListener('customizationChanged', (event) => {
            const { type, code } = event.detail;
            this.applyCustomization(type, code);
        });
    }
    
    applyCustomization(type, code) {
        const manager = this.managers[type];
        if (!manager) return false;
        
        // Extract the specific item name from the code
        // e.g., 'theme_neon' -> 'neon'
        const itemName = code.replace(`${type}_`, '');
        
        switch (type) {
            case 'theme':
                return manager.setTheme(itemName);
            case 'music':
                return manager.setTrack(itemName);
            case 'piece_style':
                return manager.setStyle(itemName);
            case 'effect':
                return manager.setEffect(itemName);
        }
        
        return false;
    }
    
    applyAllCustomizations() {
        // Apply saved customizations on load
        themeManager.applyTheme();
        
        // Initialize piece style and effects
        pieceStyleManager.setStyle(pieceStyleManager.currentStyle);
        effectsManager.setEffect(effectsManager.currentEffect);
        
        // Music will be applied when audio manager is ready
        if (window.tetrisGame && window.tetrisGame.audioManager) {
            musicManager.audioManager = window.tetrisGame.audioManager;
            musicManager.applyMusicStyle();
        }
    }
    
    unlockItem(type, code) {
        const manager = this.managers[type];
        if (!manager) return false;
        
        const itemName = code.replace(`${type}_`, '');
        
        switch (type) {
            case 'theme':
                // Themes are unlocked through the progression system
                return true;
            case 'music':
                return manager.unlockTrack(itemName);
            case 'piece_style':
                return manager.unlockStyle(itemName);
            case 'effect':
                return manager.unlockEffect(itemName);
        }
        
        return false;
    }
    
    getCurrentCustomizations() {
        return {
            theme: themeManager.currentTheme,
            music: musicManager.currentTrack,
            piece_style: pieceStyleManager.currentStyle,
            effect: effectsManager.currentEffect
        };
    }
    
    // Integration with game rendering
    renderPiece(ctx, x, y, cellSize, pieceType, isGhost = false) {
        if (!pieceType || pieceType === 'undefined') {
            pieceType = 'J'; // Default fallback
        }
        const color = themeManager.getPieceColor(pieceType);
        pieceStyleManager.renderPiece(ctx, x, y, cellSize, color, isGhost);
        
        // Add glow effect if theme supports it
        if (themeManager.shouldUseGlow() && !isGhost) {
            ctx.shadowColor = color;
            ctx.shadowBlur = 5;
        }
    }
    
    renderGhostPiece(ctx, x, y, cellSize) {
        const color = themeManager.getGhostColor();
        pieceStyleManager.renderPiece(ctx, x, y, cellSize, color, true);
    }
    
    // Effects integration
    onPiecePlaced(x, y, width, height, pieceType) {
        const color = themeManager.getPieceColor(pieceType);
        effectsManager.onPiecePlaced(x, y, width, height, color);
    }
    
    onLinesCleared(lines) {
        effectsManager.onLinesCleared(lines, 0, '#FFD700');
    }
    
    onPieceMoved(x, y, pieceType) {
        if (effectsManager.currentEffect === 'trails') {
            const color = themeManager.getPieceColor(pieceType);
            effectsManager.onPieceMoved(x, y, color);
        }
    }
    
    updateEffects(deltaTime) {
        effectsManager.update(deltaTime);
    }
    
    renderEffects(ctx) {
        effectsManager.render(ctx);
    }
    
    // Check if animations should be used
    shouldUseAnimations() {
        return themeManager.shouldUseAnimations();
    }
    
    shouldUseParticles() {
        return themeManager.shouldUseParticles();
    }
}

// Export singleton instance
export const customizationManager = new CustomizationManager();