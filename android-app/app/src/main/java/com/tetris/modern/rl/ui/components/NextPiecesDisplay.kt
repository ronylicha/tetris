package com.tetris.modern.rl.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tetris.modern.rl.game.Piece
import com.tetris.modern.rl.ui.theme.*

@Composable
fun NextPiecesDisplay(
    nextPieces: List<Piece>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NEXT",
                fontSize = 10.sp,
                color = TextSecondary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            nextPieces.take(4).forEach { piece ->
                PiecePreview(
                    piece = piece,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun PiecePreview(
    piece: Piece,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val cellSize = size.width / 4
        val shape = piece.getCurrentShape()
        
        shape.forEachIndexed { row, rowArray ->
            rowArray.forEachIndexed { col, cell ->
                if (cell != 0) {
                    drawPieceCell(
                        x = col * cellSize,
                        y = row * cellSize,
                        size = cellSize,
                        color = getPieceColor(piece.type)
                    )
                }
            }
        }
    }
}

fun DrawScope.drawPieceCell(
    x: Float,
    y: Float,
    size: Float,
    color: Color
) {
    drawRect(
        color = color,
        topLeft = Offset(x + 1, y + 1),
        size = Size(size - 2, size - 2)
    )
}

fun getPieceColor(type: Char): Color {
    return when (type) {
        'I' -> AccentCyan
        'O' -> AccentYellow
        'T' -> AccentMagenta
        'S' -> NeonGreen
        'Z' -> Error
        'J' -> AccentOrange
        'L' -> Secondary
        else -> TextSecondary
    }
}