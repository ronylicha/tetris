// Theme Manager - Handles visual themes for the game

export class ThemeManager {
    constructor() {
        this.currentTheme = 'classic';
        this.themes = {
            neon: {
                name: 'Neon',
                colors: {
                    I: '#00d4ff',  // Neon Cyan
                    O: '#ffff00',  // Neon Yellow
                    T: '#9d4edd',  // Neon Purple
                    S: '#39ff14',  // Neon Green
                    Z: '#ff0040',  // Neon Red
                    J: '#0066ff',  // Neon Blue
                    L: '#ff8500',  // Neon Orange
                    ghost: 'rgba(255, 255, 255, 0.2)',
                    grid: '#1a1a2e',
                    gridLine: 'rgba(255, 255, 255, 0.05)',
                    background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'
                },
                glow: true,
                particles: true,
                animations: true
            },
            classic: {
                name: 'Classic',
                colors: {
                    I: '#00d4ff',  // Cyan
                    O: '#ffff00',  // Yellow
                    T: '#9d4edd',  // Purple
                    S: '#39ff14',  // Green
                    Z: '#ff0040',  // Red
                    J: '#0066ff',  // Blue
                    L: '#ff8500',  // Orange
                    ghost: 'rgba(128, 128, 128, 0.3)',
                    grid: '#000000',
                    gridLine: '#333333',
                    background: '#222222'
                },
                glow: false,
                particles: false,
                animations: false
            },
            cyberpunk: {
                name: 'Cyberpunk',
                colors: {
                    I: '#ff00ff',
                    O: '#ffff00',
                    T: '#00ffff',
                    S: '#ff0080',
                    Z: '#8000ff',
                    J: '#00ff80',
                    L: '#ff8000',
                    ghost: 'rgba(255, 0, 255, 0.15)',
                    grid: '#0a0a0a',
                    gridLine: 'rgba(255, 0, 255, 0.1)',
                    background: 'linear-gradient(135deg, #ff00ff 0%, #00ffff 100%)'
                },
                glow: true,
                particles: true,
                animations: true,
                scanlines: true
            },
            retro: {
                name: 'Retro',
                colors: {
                    I: '#40e0d0',
                    O: '#ffd700',
                    T: '#da70d6',
                    S: '#32cd32',
                    Z: '#dc143c',
                    J: '#4169e1',
                    L: '#ff8c00',
                    ghost: 'rgba(255, 255, 255, 0.25)',
                    grid: '#2f4f4f',
                    gridLine: '#556b2f',
                    background: 'linear-gradient(180deg, #8b4513 0%, #daa520 100%)'
                },
                glow: false,
                particles: false,
                animations: true,
                pixelated: true
            },
            nature: {
                name: 'Nature',
                colors: {
                    I: '#87ceeb', // Sky blue
                    O: '#ffeb3b', // Sunflower
                    T: '#9c27b0', // Orchid
                    S: '#4caf50', // Grass
                    Z: '#f44336', // Rose
                    J: '#2196f3', // Water
                    L: '#ff9800', // Autumn
                    ghost: 'rgba(139, 195, 74, 0.2)',
                    grid: '#263238',
                    gridLine: 'rgba(76, 175, 80, 0.1)',
                    background: 'linear-gradient(180deg, #4caf50 0%, #8bc34a 100%)'
                },
                glow: false,
                particles: true,
                animations: true,
                leaves: true
            },
            minimal: {
                name: 'Minimal',
                colors: {
                    I: '#ffffff',
                    O: '#e0e0e0',
                    T: '#bdbdbd',
                    S: '#9e9e9e',
                    Z: '#757575',
                    J: '#616161',
                    L: '#424242',
                    ghost: 'rgba(255, 255, 255, 0.1)',
                    grid: '#000000',
                    gridLine: 'rgba(255, 255, 255, 0.02)',
                    background: '#121212'
                },
                glow: false,
                particles: false,
                animations: false
            },
            galaxy: {
                name: 'Galaxy',
                colors: {
                    I: '#e91e63',
                    O: '#ffc107',
                    T: '#9c27b0',
                    S: '#00bcd4',
                    Z: '#ff5722',
                    J: '#3f51b5',
                    L: '#ff9800',
                    ghost: 'rgba(255, 255, 255, 0.15)',
                    grid: '#0a0a2e',
                    gridLine: 'rgba(147, 112, 219, 0.1)',
                    background: 'radial-gradient(ellipse at center, #1b2735 0%, #090a0f 100%)'
                },
                glow: true,
                particles: true,
                animations: true,
                stars: true
            }
        };
        
        this.loadSavedTheme();
    }
    
    loadSavedTheme() {
        const saved = localStorage.getItem('tetris_theme');
        if (saved && this.themes[saved]) {
            this.currentTheme = saved;
        }
        // Apply the theme to ensure colors are set
        this.applyTheme();
    }
    
    setTheme(themeName) {
        if (this.themes[themeName]) {
            this.currentTheme = themeName;
            localStorage.setItem('tetris_theme', themeName);
            this.applyTheme();
            return true;
        }
        return false;
    }
    
    getCurrentTheme() {
        return this.themes[this.currentTheme];
    }
    
    applyTheme() {
        const theme = this.getCurrentTheme();
        this.applyThemeData(theme);
    }
    
    previewTheme(themeName) {
        if (this.themes[themeName]) {
            const theme = this.themes[themeName];
            this.applyThemeData(theme);
        }
    }
    
    applyThemeData(theme) {
        const root = document.documentElement;
        
        // Apply CSS variables
        root.style.setProperty('--piece-color-I', theme.colors.I);
        root.style.setProperty('--piece-color-O', theme.colors.O);
        root.style.setProperty('--piece-color-T', theme.colors.T);
        root.style.setProperty('--piece-color-S', theme.colors.S);
        root.style.setProperty('--piece-color-Z', theme.colors.Z);
        root.style.setProperty('--piece-color-J', theme.colors.J);
        root.style.setProperty('--piece-color-L', theme.colors.L);
        root.style.setProperty('--ghost-color', theme.colors.ghost);
        root.style.setProperty('--grid-bg', theme.colors.grid);
        root.style.setProperty('--grid-line', theme.colors.gridLine);
        
        // Apply background
        if (typeof theme.colors.background === 'string' && theme.colors.background.includes('gradient')) {
            document.body.style.background = theme.colors.background;
        } else {
            document.body.style.backgroundColor = theme.colors.background;
        }
        
        // Apply special effects
        this.applySpecialEffects(theme);
        
        // Trigger theme change event
        window.dispatchEvent(new CustomEvent('themeChanged', { detail: theme }));
    }
    
    applySpecialEffects(theme) {
        // Remove all special effect classes
        document.body.classList.remove('theme-glow', 'theme-scanlines', 'theme-pixelated', 'theme-stars', 'theme-leaves');
        
        // Add theme-specific effects
        if (theme.glow) document.body.classList.add('theme-glow');
        if (theme.scanlines) document.body.classList.add('theme-scanlines');
        if (theme.pixelated) document.body.classList.add('theme-pixelated');
        if (theme.stars) document.body.classList.add('theme-stars');
        if (theme.leaves) document.body.classList.add('theme-leaves');
    }
    
    getPieceColor(pieceType) {
        const theme = this.getCurrentTheme();
        return theme.colors[pieceType] || '#ffffff';
    }
    
    getGhostColor() {
        const theme = this.getCurrentTheme();
        return theme.colors.ghost;
    }
    
    shouldUseGlow() {
        return this.getCurrentTheme().glow;
    }
    
    shouldUseParticles() {
        return this.getCurrentTheme().particles;
    }
    
    shouldUseAnimations() {
        return this.getCurrentTheme().animations;
    }
}

// Export singleton instance
export const themeManager = new ThemeManager();