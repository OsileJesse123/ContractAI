package com.jesse.contractai.ui.theme

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.jesse.contractai.util.customHexagon
import kotlin.math.min

class HexagonShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawCustomHexagonPath(size)
        )
    }

    companion object {
        fun drawCustomHexagonPath(size: Size): Path {
            return Path().apply {
                val radius = min(size.width / 2f, size.height / 2f)
                customHexagon(radius, size)
            }
        }
    }
}
