package com.tetris.modern.rl.customization

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.tetris.modern.rl.progression.PlayerProgression
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomizationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val playerProgression: PlayerProgression
) {
    
    data class Theme(
        val id: String,
        val name: String,
        val description: String,
        val primaryColor: Color,
        val secondaryColor: Color,
        val backgroundColor: Color,
        val gridColor: Color,
        val pieceColors: Map<String, Color>,
        val effectStyle: String,
        val requiredLevel: Int = 1
    )
    
    data class MusicTrack(
        val id: String,
        val name: String,
        val description: String,
        val tempo: Int, // BPM
        val style: String,
        val composer: String,
        val requiredLevel: Int = 1
    )
    
    data class PieceStyle(
        val id: String,
        val name: String,
        val description: String,
        val renderStyle: RenderStyle,
        val hasAnimation: Boolean,
        val specialEffects: List<String>,
        val requiredLevel: Int = 1
    )
    
    enum class RenderStyle {
        STANDARD, GLASS, PIXEL, HOLOGRAM, CRYSTAL, ANIMATED, NEON, RETRO
    }
    
    data class VisualEffect(
        val id: String,
        val name: String,
        val description: String,
        val effectType: EffectType,
        val intensity: Float,
        val requiredLevel: Int = 1
    )
    
    enum class EffectType {
        PARTICLES, TRAILS, EXPLOSIONS, LIGHTNING, SHATTER, QUANTUM, RIPPLE, GLOW
    }
    
    data class CustomizationState(
        val currentTheme: String = "cyberpunk",
        val currentMusic: String = "theme_a",
        val currentPieceStyle: String = "standard",
        val currentEffects: Set<String> = setOf("basic"),
        val unlockedThemes: Set<String> = setOf("cyberpunk"),
        val unlockedMusic: Set<String> = setOf("theme_a"),
        val unlockedPieceStyles: Set<String> = setOf("standard"),
        val unlockedEffects: Set<String> = setOf("basic")
    )
    
    // All available themes (matching original game)
    private val themes = listOf(
        Theme(
            id = "cyberpunk",
            name = "Cyberpunk",
            description = "Neon lights and digital rain",
            primaryColor = Color(0xFF00FFFF),
            secondaryColor = Color(0xFFFF00FF),
            backgroundColor = Color(0xFF0A0A0A),
            gridColor = Color(0xFF1A1A2E),
            pieceColors = mapOf(
                "I" to Color(0xFF00FFFF),
                "O" to Color(0xFFFFFF00),
                "T" to Color(0xFFFF00FF),
                "S" to Color(0xFF00FF00),
                "Z" to Color(0xFFFF0000),
                "J" to Color(0xFF0000FF),
                "L" to Color(0xFFFF8800)
            ),
            effectStyle = "neon_glow",
            requiredLevel = 1
        ),
        Theme(
            id = "retro",
            name = "Retro 8-bit",
            description = "Classic arcade nostalgia",
            primaryColor = Color(0xFF4CAF50),
            secondaryColor = Color(0xFFFFC107),
            backgroundColor = Color(0xFF000000),
            gridColor = Color(0xFF333333),
            pieceColors = mapOf(
                "I" to Color(0xFF00BCD4),
                "O" to Color(0xFFFFEB3B),
                "T" to Color(0xFF9C27B0),
                "S" to Color(0xFF4CAF50),
                "Z" to Color(0xFFF44336),
                "J" to Color(0xFF2196F3),
                "L" to Color(0xFFFF9800)
            ),
            effectStyle = "pixel",
            requiredLevel = 5
        ),
        Theme(
            id = "nature",
            name = "Nature",
            description = "Calming natural colors",
            primaryColor = Color(0xFF4CAF50),
            secondaryColor = Color(0xFF8BC34A),
            backgroundColor = Color(0xFF1B5E20),
            gridColor = Color(0xFF2E7D32),
            pieceColors = mapOf(
                "I" to Color(0xFF81C784),
                "O" to Color(0xFFFFF176),
                "T" to Color(0xFFBA68C8),
                "S" to Color(0xFF4CAF50),
                "Z" to Color(0xFFE57373),
                "J" to Color(0xFF64B5F6),
                "L" to Color(0xFFFFB74D)
            ),
            effectStyle = "organic",
            requiredLevel = 10
        ),
        Theme(
            id = "minimal",
            name = "Minimal",
            description = "Clean and simple design",
            primaryColor = Color(0xFF212121),
            secondaryColor = Color(0xFF757575),
            backgroundColor = Color(0xFFFFFFFF),
            gridColor = Color(0xFFE0E0E0),
            pieceColors = mapOf(
                "I" to Color(0xFF424242),
                "O" to Color(0xFF616161),
                "T" to Color(0xFF757575),
                "S" to Color(0xFF9E9E9E),
                "Z" to Color(0xFFBDBDBD),
                "J" to Color(0xFF424242),
                "L" to Color(0xFF616161)
            ),
            effectStyle = "clean",
            requiredLevel = 15
        ),
        Theme(
            id = "galaxy",
            name = "Galaxy",
            description = "Cosmic space theme",
            primaryColor = Color(0xFF7B1FA2),
            secondaryColor = Color(0xFF303F9F),
            backgroundColor = Color(0xFF000428),
            gridColor = Color(0xFF1A237E),
            pieceColors = mapOf(
                "I" to Color(0xFF00BCD4),
                "O" to Color(0xFFFFD54F),
                "T" to Color(0xFFE91E63),
                "S" to Color(0xFF00E676),
                "Z" to Color(0xFFFF5252),
                "J" to Color(0xFF536DFE),
                "L" to Color(0xFFFF6E40)
            ),
            effectStyle = "cosmic",
            requiredLevel = 25
        ),
        Theme(
            id = "matrix",
            name = "Matrix",
            description = "Digital rain effect",
            primaryColor = Color(0xFF00FF00),
            secondaryColor = Color(0xFF00AA00),
            backgroundColor = Color(0xFF000000),
            gridColor = Color(0xFF003300),
            pieceColors = mapOf(
                "I" to Color(0xFF00FF00),
                "O" to Color(0xFF00DD00),
                "T" to Color(0xFF00BB00),
                "S" to Color(0xFF00FF00),
                "Z" to Color(0xFF009900),
                "J" to Color(0xFF00CC00),
                "L" to Color(0xFF00AA00)
            ),
            effectStyle = "digital_rain",
            requiredLevel = 35
        ),
        Theme(
            id = "rainbow",
            name = "Rainbow",
            description = "Vibrant rainbow colors",
            primaryColor = Color(0xFFFF0000),
            secondaryColor = Color(0xFF00FF00),
            backgroundColor = Color(0xFF000033),
            gridColor = Color(0xFF333366),
            pieceColors = mapOf(
                "I" to Color(0xFFFF0000),
                "O" to Color(0xFFFF7F00),
                "T" to Color(0xFFFFFF00),
                "S" to Color(0xFF00FF00),
                "Z" to Color(0xFF0000FF),
                "J" to Color(0xFF4B0082),
                "L" to Color(0xFF9400D3)
            ),
            effectStyle = "rainbow_trail",
            requiredLevel = 50
        )
    )
    
    // All available music tracks (matching original game)
    private val musicTracks = listOf(
        MusicTrack(
            id = "theme_a",
            name = "Classic Theme A",
            description = "The original Tetris theme",
            tempo = 120,
            style = "Classic",
            composer = "Traditional",
            requiredLevel = 1
        ),
        MusicTrack(
            id = "chiptune",
            name = "Chiptune",
            description = "8-bit retro music",
            tempo = 140,
            style = "Retro",
            composer = "Digital Artist",
            requiredLevel = 3
        ),
        MusicTrack(
            id = "synthwave",
            name = "Synthwave",
            description = "80s electronic vibes",
            tempo = 128,
            style = "Electronic",
            composer = "Synth Master",
            requiredLevel = 8
        ),
        MusicTrack(
            id = "orchestral",
            name = "Orchestral",
            description = "Epic classical score",
            tempo = 100,
            style = "Classical",
            composer = "Symphony Orchestra",
            requiredLevel = 20
        ),
        MusicTrack(
            id = "jazz",
            name = "Jazz",
            description = "Smooth jazz rhythms",
            tempo = 110,
            style = "Jazz",
            composer = "Jazz Ensemble",
            requiredLevel = 30
        ),
        MusicTrack(
            id = "metal",
            name = "Metal",
            description = "Heavy metal intensity",
            tempo = 180,
            style = "Metal",
            composer = "Metal Band",
            requiredLevel = 40
        ),
        MusicTrack(
            id = "lofi",
            name = "Lo-fi",
            description = "Relaxing lo-fi beats",
            tempo = 85,
            style = "Chill",
            composer = "Lo-fi Producer",
            requiredLevel = 60
        )
    )
    
    // All available piece styles (matching original game)
    private val pieceStyles = listOf(
        PieceStyle(
            id = "standard",
            name = "Standard",
            description = "Classic Tetris pieces",
            renderStyle = RenderStyle.STANDARD,
            hasAnimation = false,
            specialEffects = emptyList(),
            requiredLevel = 1
        ),
        PieceStyle(
            id = "glass",
            name = "Glass",
            description = "Transparent glass pieces",
            renderStyle = RenderStyle.GLASS,
            hasAnimation = false,
            specialEffects = listOf("transparency", "refraction"),
            requiredLevel = 7
        ),
        PieceStyle(
            id = "pixel",
            name = "Pixel",
            description = "Chunky pixel art",
            renderStyle = RenderStyle.PIXEL,
            hasAnimation = false,
            specialEffects = listOf("pixelation"),
            requiredLevel = 12
        ),
        PieceStyle(
            id = "hologram",
            name = "Hologram",
            description = "Futuristic holograms",
            renderStyle = RenderStyle.HOLOGRAM,
            hasAnimation = true,
            specialEffects = listOf("scan_lines", "glitch", "transparency"),
            requiredLevel = 22
        ),
        PieceStyle(
            id = "crystal",
            name = "Crystal",
            description = "Shimmering crystals",
            renderStyle = RenderStyle.CRYSTAL,
            hasAnimation = true,
            specialEffects = listOf("shimmer", "refraction", "sparkle"),
            requiredLevel = 32
        ),
        PieceStyle(
            id = "animated",
            name = "Animated",
            description = "Living pieces",
            renderStyle = RenderStyle.ANIMATED,
            hasAnimation = true,
            specialEffects = listOf("pulse", "breathe", "morph"),
            requiredLevel = 45
        ),
        PieceStyle(
            id = "neon",
            name = "Neon",
            description = "Glowing neon tubes",
            renderStyle = RenderStyle.NEON,
            hasAnimation = true,
            specialEffects = listOf("glow", "flicker", "electric"),
            requiredLevel = 55
        ),
        PieceStyle(
            id = "retro",
            name = "Retro CRT",
            description = "Old-school CRT monitor effect",
            renderStyle = RenderStyle.RETRO,
            hasAnimation = true,
            specialEffects = listOf("scan_lines", "chromatic_aberration", "curve"),
            requiredLevel = 65
        )
    )
    
    // All available visual effects (matching original game)
    private val visualEffects = listOf(
        VisualEffect(
            id = "basic",
            name = "Basic",
            description = "Standard visual effects",
            effectType = EffectType.PARTICLES,
            intensity = 0.5f,
            requiredLevel = 1
        ),
        VisualEffect(
            id = "particles",
            name = "Particles",
            description = "Particle explosions on line clear",
            effectType = EffectType.PARTICLES,
            intensity = 1.0f,
            requiredLevel = 5
        ),
        VisualEffect(
            id = "trails",
            name = "Trails",
            description = "Motion trails for falling pieces",
            effectType = EffectType.TRAILS,
            intensity = 0.8f,
            requiredLevel = 10
        ),
        VisualEffect(
            id = "explosions",
            name = "Explosions",
            description = "Explosive line clears",
            effectType = EffectType.EXPLOSIONS,
            intensity = 1.0f,
            requiredLevel = 18
        ),
        VisualEffect(
            id = "lightning",
            name = "Lightning",
            description = "Electric effects on Tetris",
            effectType = EffectType.LIGHTNING,
            intensity = 0.9f,
            requiredLevel = 28
        ),
        VisualEffect(
            id = "shatter",
            name = "Shatter",
            description = "Pieces shatter on line clear",
            effectType = EffectType.SHATTER,
            intensity = 1.0f,
            requiredLevel = 38
        ),
        VisualEffect(
            id = "quantum",
            name = "Quantum",
            description = "Quantum particle effects",
            effectType = EffectType.QUANTUM,
            intensity = 1.2f,
            requiredLevel = 55
        ),
        VisualEffect(
            id = "ripple",
            name = "Ripple",
            description = "Ripple effects on placement",
            effectType = EffectType.RIPPLE,
            intensity = 0.7f,
            requiredLevel = 70
        ),
        VisualEffect(
            id = "glow",
            name = "Glow",
            description = "Ambient glow effects",
            effectType = EffectType.GLOW,
            intensity = 0.6f,
            requiredLevel = 80
        )
    )
    
    private val _customizationState = MutableStateFlow(CustomizationState())
    val customizationState: StateFlow<CustomizationState> = _customizationState.asStateFlow()
    
    init {
        syncWithProgression()
    }
    
    private fun syncWithProgression() {
        // Sync unlocked items with player progression
        val progressionState = playerProgression.progressionState.value
        val unlockedThemes = themes.filter { it.requiredLevel <= progressionState.level }.map { it.id }.toSet()
        val unlockedMusic = musicTracks.filter { it.requiredLevel <= progressionState.level }.map { it.id }.toSet()
        val unlockedPieceStyles = pieceStyles.filter { it.requiredLevel <= progressionState.level }.map { it.id }.toSet()
        val unlockedEffects = visualEffects.filter { it.requiredLevel <= progressionState.level }.map { it.id }.toSet()
        
        _customizationState.value = _customizationState.value.copy(
            unlockedThemes = unlockedThemes,
            unlockedMusic = unlockedMusic,
            unlockedPieceStyles = unlockedPieceStyles,
            unlockedEffects = unlockedEffects
        )
    }
    
    fun setTheme(themeId: String) {
        if (themeId in _customizationState.value.unlockedThemes) {
            _customizationState.value = _customizationState.value.copy(currentTheme = themeId)
        }
    }
    
    fun setMusic(musicId: String) {
        if (musicId in _customizationState.value.unlockedMusic) {
            _customizationState.value = _customizationState.value.copy(currentMusic = musicId)
        }
    }
    
    fun setPieceStyle(styleId: String) {
        if (styleId in _customizationState.value.unlockedPieceStyles) {
            _customizationState.value = _customizationState.value.copy(currentPieceStyle = styleId)
        }
    }
    
    fun toggleEffect(effectId: String) {
        val currentEffects = _customizationState.value.currentEffects.toMutableSet()
        if (effectId in _customizationState.value.unlockedEffects) {
            if (effectId in currentEffects) {
                currentEffects.remove(effectId)
            } else {
                currentEffects.add(effectId)
            }
            _customizationState.value = _customizationState.value.copy(currentEffects = currentEffects)
        }
    }
    
    fun getCurrentTheme(): Theme? {
        return themes.find { it.id == _customizationState.value.currentTheme }
    }
    
    fun getCurrentMusic(): MusicTrack? {
        return musicTracks.find { it.id == _customizationState.value.currentMusic }
    }
    
    fun getCurrentPieceStyle(): PieceStyle? {
        return pieceStyles.find { it.id == _customizationState.value.currentPieceStyle }
    }
    
    fun getCurrentEffects(): List<VisualEffect> {
        return visualEffects.filter { it.id in _customizationState.value.currentEffects }
    }
    
    fun getUnlockedItems(): Map<String, List<Any>> {
        return mapOf(
            "themes" to themes.filter { it.id in _customizationState.value.unlockedThemes },
            "music" to musicTracks.filter { it.id in _customizationState.value.unlockedMusic },
            "pieceStyles" to pieceStyles.filter { it.id in _customizationState.value.unlockedPieceStyles },
            "effects" to visualEffects.filter { it.id in _customizationState.value.unlockedEffects }
        )
    }
    
    fun getLockedItems(): Map<String, List<Any>> {
        return mapOf(
            "themes" to themes.filter { it.id !in _customizationState.value.unlockedThemes },
            "music" to musicTracks.filter { it.id !in _customizationState.value.unlockedMusic },
            "pieceStyles" to pieceStyles.filter { it.id !in _customizationState.value.unlockedPieceStyles },
            "effects" to visualEffects.filter { it.id !in _customizationState.value.unlockedEffects }
        )
    }
    
    fun getNextUnlocks(currentLevel: Int): List<Any> {
        val nextItems = mutableListOf<Any>()
        
        themes.filter { it.requiredLevel > currentLevel }
            .sortedBy { it.requiredLevel }
            .firstOrNull()?.let { nextItems.add(it) }
            
        musicTracks.filter { it.requiredLevel > currentLevel }
            .sortedBy { it.requiredLevel }
            .firstOrNull()?.let { nextItems.add(it) }
            
        pieceStyles.filter { it.requiredLevel > currentLevel }
            .sortedBy { it.requiredLevel }
            .firstOrNull()?.let { nextItems.add(it) }
            
        visualEffects.filter { it.requiredLevel > currentLevel }
            .sortedBy { it.requiredLevel }
            .firstOrNull()?.let { nextItems.add(it) }
            
        return nextItems
    }
}