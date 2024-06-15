package ui.services

import androidx.compose.ui.graphics.painter.Painter

expect object LoadImage {
    fun load(url: String): Painter
}