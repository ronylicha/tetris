// Power-Up Manager - Handles power-up logic and state
import { POWER_UP_TYPES, getRandomPowerUp, shouldGeneratePowerUp, getPowerUpCount } from './powerUpTypes.js';

export class PowerUpManager {
    constructor(game) {
        this.game = game;
        this.slots = [null, null]; // Max 2 power-ups
        this.activePowerUps = []; // Currently active power-ups with timers
        this.accomplishmentQueue = [];
        this.lastAccomplishment = null;
        this.particleEffects = [];
    }
    
    // Initialize the manager
    initialize() {
        this.slots = [null, null];
        this.activePowerUps = [];
        this.accomplishmentQueue = [];
        this.lastAccomplishment = null;
        this.particleEffects = [];
    }
    
    // Update active power-ups (called each frame)
    update(deltaTime) {
        // Update active power-up timers
        for (let i = this.activePowerUps.length - 1; i >= 0; i--) {
            const active = this.activePowerUps[i];
            if (active.duration > 0) {
                active.timeRemaining -= deltaTime;
                
                // Update UI timer
                this.updatePowerUpTimer(active);
                
                if (active.timeRemaining <= 0) {
                    // Power-up expired, cleanup
                    this.deactivatePowerUp(i);
                }
            }
        }
        
        // Process accomplishment queue
        this.processAccomplishments();
        
        // Update particle effects
        this.updateParticles(deltaTime);
    }
    
    // Register an accomplishment
    registerAccomplishment(type, value = 1) {
        this.accomplishmentQueue.push({ type, value, timestamp: Date.now() });
    }
    
    // Process accomplishments and generate power-ups
    processAccomplishments() {
        while (this.accomplishmentQueue.length > 0) {
            const accomplishment = this.accomplishmentQueue.shift();
            
            // Check if we should generate power-up
            if (shouldGeneratePowerUp(accomplishment.type)) {
                const count = getPowerUpCount(accomplishment.type) || 1;
                
                for (let i = 0; i < count; i++) {
                    this.generatePowerUp();
                }
            }
        }
    }
    
    // Generate a new power-up
    generatePowerUp() {
        const powerUp = getRandomPowerUp();
        
        // Try to add to an empty slot
        for (let i = 0; i < this.slots.length; i++) {
            if (!this.slots[i]) {
                this.slots[i] = {
                    ...powerUp,
                    slotIndex: i,
                    generated: Date.now()
                };
                
                // Show notification
                this.showPowerUpNotification(powerUp);
                
                // Update UI
                this.updateSlotDisplay(i);
                
                // Play sound
                if (this.game.audioManager) {
                    this.game.audioManager.playSFX('powerup_collect');
                }
                
                return true;
            }
        }
        
        // All slots full - replace oldest
        const oldestSlot = this.slots[0].generated < this.slots[1].generated ? 0 : 1;
        this.slots[oldestSlot] = {
            ...powerUp,
            slotIndex: oldestSlot,
            generated: Date.now()
        };
        
        this.showPowerUpNotification(powerUp);
        this.updateSlotDisplay(oldestSlot);
        
        return true;
    }
    
    // Activate a power-up from slot
    activatePowerUp(slotIndex) {
        if (slotIndex < 0 || slotIndex >= this.slots.length) return false;
        
        const powerUp = this.slots[slotIndex];
        if (!powerUp) return false;
        
        // Clear the slot
        this.slots[slotIndex] = null;
        this.updateSlotDisplay(slotIndex);
        
        // Apply effect
        if (powerUp.effect) {
            powerUp.effect(this.game);
        }
        
        // If it has duration, add to active list
        if (powerUp.duration > 0) {
            this.activePowerUps.push({
                ...powerUp,
                timeRemaining: powerUp.duration,
                startTime: Date.now()
            });
            
            // Update active display
            this.updateActivePowerUpsDisplay();
        }
        
        // Create visual effect
        this.createActivationEffect(powerUp);
        
        // Play activation sound
        if (this.game.audioManager) {
            this.game.audioManager.playSFX('powerup_activate');
        }
        
        return true;
    }
    
    // Deactivate expired power-up
    deactivatePowerUp(index) {
        const active = this.activePowerUps[index];
        
        // Run cleanup if exists
        if (active.cleanup) {
            active.cleanup(this.game);
        }
        
        // Remove from active list
        this.activePowerUps.splice(index, 1);
        
        // Update display
        this.updateActivePowerUpsDisplay();
        
        // Play deactivation sound
        if (this.game.audioManager) {
            this.game.audioManager.playSFX('powerup_expire');
        }
    }
    
    // UI Update Methods
    updateSlotDisplay(slotIndex) {
        const slotElement = document.getElementById(`powerup-slot-${slotIndex}`);
        if (!slotElement) return;
        
        const powerUp = this.slots[slotIndex];
        
        const keyLabel = slotIndex === 0 ? 'V' : 'B';
        
        if (powerUp) {
            slotElement.innerHTML = `
                <div class="powerup-icon">${powerUp.icon}</div>
                <div class="powerup-name">${powerUp.name}</div>
                <div class="slot-key">${keyLabel}</div>
            `;
            slotElement.style.background = `linear-gradient(135deg, ${powerUp.color}33, ${powerUp.color}11)`;
            slotElement.style.border = `2px solid ${powerUp.color}`;
            slotElement.classList.add('filled');
        } else {
            slotElement.innerHTML = `
                <div class="powerup-empty">Empty</div>
                <div class="slot-key">${keyLabel}</div>
            `;
            slotElement.style.background = 'rgba(255, 255, 255, 0.05)';
            slotElement.style.border = '2px solid rgba(255, 255, 255, 0.2)';
            slotElement.classList.remove('filled');
        }
    }
    
    updateActivePowerUpsDisplay() {
        const container = document.getElementById('active-powerups');
        if (!container) return;
        
        container.innerHTML = '';
        
        this.activePowerUps.forEach(active => {
            const element = document.createElement('div');
            element.className = 'active-powerup';
            element.style.borderColor = active.color;
            
            const progress = active.timeRemaining / active.duration;
            
            element.innerHTML = `
                <div class="active-powerup-icon">${active.icon}</div>
                <div class="active-powerup-timer">
                    <div class="timer-fill" style="width: ${progress * 100}%; background: ${active.color}"></div>
                </div>
            `;
            
            container.appendChild(element);
        });
    }
    
    updatePowerUpTimer(active) {
        // This is called frequently, so we just trigger display update
        // The actual timer display is handled in updateActivePowerUpsDisplay
    }
    
    showPowerUpNotification(powerUp) {
        const notification = document.createElement('div');
        notification.className = 'powerup-notification';
        notification.style.color = powerUp.color;
        notification.innerHTML = `
            <span class="notification-icon">${powerUp.icon}</span>
            <span class="notification-text">Power-Up Collected: ${powerUp.name}</span>
        `;
        
        document.body.appendChild(notification);
        
        // Animate and remove
        setTimeout(() => {
            notification.classList.add('fade-out');
            setTimeout(() => notification.remove(), 500);
        }, 2000);
    }
    
    createActivationEffect(powerUp) {
        // Create particle burst effect at game canvas center
        const canvas = this.game.canvas;
        if (!canvas) return;
        
        const rect = canvas.getBoundingClientRect();
        const centerX = rect.left + rect.width / 2;
        const centerY = rect.top + rect.height / 2;
        
        // Create particles
        for (let i = 0; i < 20; i++) {
            const particle = {
                x: centerX,
                y: centerY,
                vx: (Math.random() - 0.5) * 10,
                vy: (Math.random() - 0.5) * 10,
                color: powerUp.color,
                life: 1000,
                size: Math.random() * 4 + 2
            };
            this.particleEffects.push(particle);
        }
    }
    
    updateParticles(deltaTime) {
        // Update and render particles (would need canvas overlay for full effect)
        for (let i = this.particleEffects.length - 1; i >= 0; i--) {
            const particle = this.particleEffects[i];
            particle.life -= deltaTime;
            
            if (particle.life <= 0) {
                this.particleEffects.splice(i, 1);
            } else {
                particle.x += particle.vx * deltaTime / 100;
                particle.y += particle.vy * deltaTime / 100;
                particle.vy += 0.5; // Gravity
            }
        }
    }
    
    clearAll() {
        // Clear all slots
        this.slots = [null, null];
        this.updateSlotDisplay(0);
        this.updateSlotDisplay(1);
        
        // Deactivate all active power-ups
        while (this.activePowerUps.length > 0) {
            this.deactivatePowerUp(0);
        }
        
        // Clear particle effects
        this.particleEffects = [];
    }
    
    // Check for specific accomplishments during gameplay
    checkAccomplishments(event, data) {
        switch (event) {
            case 'lines_cleared':
                if (data.count === 4) {
                    this.registerAccomplishment('tetris');
                }
                if (data.total >= 10 && data.total % 10 === 0) {
                    this.registerAccomplishment('lines10');
                }
                if (data.total >= 20 && data.total % 20 === 0) {
                    this.registerAccomplishment('lines20');
                }
                break;
                
            case 'tspin':
                this.registerAccomplishment('tspin');
                break;
                
            case 'combo':
                if (data >= 5) {
                    this.registerAccomplishment('combo5');
                }
                if (data >= 10) {
                    this.registerAccomplishment('combo10');
                }
                break;
                
            case 'perfect_clear':
                this.registerAccomplishment('perfectClear');
                break;
                
            case 'score':
                if (data >= 1000 && data < 1100) {
                    this.registerAccomplishment('score1000');
                }
                if (data >= 5000 && data < 5100) {
                    this.registerAccomplishment('score5000');
                }
                break;
        }
    }
    
    // Get current state for saving
    getState() {
        return {
            slots: this.slots,
            activePowerUps: this.activePowerUps.map(p => ({
                ...p,
                timeRemaining: p.timeRemaining
            }))
        };
    }
    
    // Restore from saved state
    loadState(state) {
        if (state.slots) {
            this.slots = state.slots;
            this.slots.forEach((slot, i) => {
                if (slot) {
                    this.updateSlotDisplay(i);
                }
            });
        }
        
        if (state.activePowerUps) {
            this.activePowerUps = state.activePowerUps;
            this.updateActivePowerUpsDisplay();
        }
    }
}