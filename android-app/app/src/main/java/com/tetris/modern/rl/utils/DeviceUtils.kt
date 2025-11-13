package com.tetris.modern.rl.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * Device type detection and responsive utilities
 */
object DeviceUtils {
    
    enum class DeviceType {
        PHONE,
        TABLET_7,
        TABLET_10
    }
    
    enum class Orientation {
        PORTRAIT,
        LANDSCAPE
    }
    
    data class DeviceInfo(
        val type: DeviceType,
        val orientation: Orientation,
        val screenWidthDp: Int,
        val screenHeightDp: Int,
        val isTablet: Boolean,
        val isLandscape: Boolean
    )
    
    @Composable
    fun getDeviceInfo(): DeviceInfo {
        val configuration = LocalConfiguration.current
        val screenWidthDp = configuration.screenWidthDp
        val screenHeightDp = configuration.screenHeightDp
        
        // Determine device type based on smallest width
        val smallestWidth = minOf(screenWidthDp, screenHeightDp)
        val deviceType = when {
            smallestWidth >= 720 -> DeviceType.TABLET_10
            smallestWidth >= 600 -> DeviceType.TABLET_7
            else -> DeviceType.PHONE
        }
        
        // Determine orientation
        val isLandscape = screenWidthDp > screenHeightDp
        val orientation = if (isLandscape) Orientation.LANDSCAPE else Orientation.PORTRAIT
        
        return DeviceInfo(
            type = deviceType,
            orientation = orientation,
            screenWidthDp = screenWidthDp,
            screenHeightDp = screenHeightDp,
            isTablet = deviceType != DeviceType.PHONE,
            isLandscape = isLandscape
        )
    }
    
    /**
     * Get responsive game layout configuration
     */
    @Composable
    fun getGameLayoutConfig(deviceInfo: DeviceInfo): GameLayoutConfig {
        return when {
            // Tablet landscape - side by side with extra panels
            deviceInfo.isTablet && deviceInfo.isLandscape -> GameLayoutConfig(
                showSidePanels = true,
                showBottomControls = false,
                showFloatingControls = true,
                gridScale = 1.2f,
                panelArrangement = PanelArrangement.HORIZONTAL_EXTENDED,
                maxGridWidth = 500.dp,
                maxGridHeight = 500.dp
            )
            
            // Tablet portrait - vertical with larger grid
            deviceInfo.isTablet && !deviceInfo.isLandscape -> GameLayoutConfig(
                showSidePanels = true,
                showBottomControls = true,
                showFloatingControls = false,
                gridScale = 1.3f,
                panelArrangement = PanelArrangement.VERTICAL,
                maxGridWidth = 450.dp,
                maxGridHeight = 900.dp
            )
            
            // Phone landscape - compact horizontal
            !deviceInfo.isTablet && deviceInfo.isLandscape -> GameLayoutConfig(
                showSidePanels = true,
                showBottomControls = false,
                showFloatingControls = true,
                gridScale = 0.9f,
                panelArrangement = PanelArrangement.HORIZONTAL_COMPACT,
                maxGridWidth = 320.dp,
                maxGridHeight = 320.dp
            )
            
            // Phone portrait - standard vertical
            else -> GameLayoutConfig(
                showSidePanels = true,
                showBottomControls = true,
                showFloatingControls = false,
                gridScale = 1.0f,
                panelArrangement = PanelArrangement.VERTICAL,
                maxGridWidth = 360.dp,
                maxGridHeight = 720.dp
            )
        }
    }
    
    data class GameLayoutConfig(
        val showSidePanels: Boolean,
        val showBottomControls: Boolean,
        val showFloatingControls: Boolean,
        val gridScale: Float,
        val panelArrangement: PanelArrangement,
        val maxGridWidth: androidx.compose.ui.unit.Dp,
        val maxGridHeight: androidx.compose.ui.unit.Dp
    )
    
    enum class PanelArrangement {
        VERTICAL,               // Standard phone portrait
        HORIZONTAL_COMPACT,     // Phone landscape
        HORIZONTAL_EXTENDED     // Tablet landscape with extra panels
    }
    
    /**
     * Get number of columns for grid layouts
     */
    @Composable
    fun getGridColumns(deviceInfo: DeviceInfo, forModeSelection: Boolean = false): Int {
        return if (forModeSelection) {
            when {
                deviceInfo.isTablet && deviceInfo.isLandscape -> 4
                deviceInfo.isTablet -> 3
                deviceInfo.isLandscape -> 3
                else -> 2
            }
        } else {
            when {
                deviceInfo.isTablet -> 3
                deviceInfo.isLandscape -> 2
                else -> 1
            }
        }
    }
}