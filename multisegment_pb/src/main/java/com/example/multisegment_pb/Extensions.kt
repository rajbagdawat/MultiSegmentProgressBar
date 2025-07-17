package com.example.multisegment_pb

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope


fun DrawScope.drawCustomRoundRect(
    color: Color,
    topLeft: Offset,
    size: Size,
    radiusTopStart: Float = 0f,
    radiusTopEnd: Float = 0f,
    radiusBottomStart: Float = 0f,
    radiusBottomEnd: Float = 0f
) {
    val path = Path().apply {
        val rect = Rect(topLeft, size)

        addRoundRect(
            androidx.compose.ui.geometry.RoundRect(
                rect = rect,
                topLeft = CornerRadius(radiusTopStart, radiusTopStart),
                topRight = CornerRadius(radiusTopEnd, radiusTopEnd),
                bottomLeft = CornerRadius(radiusBottomStart, radiusBottomStart),
                bottomRight = CornerRadius(radiusBottomEnd, radiusBottomEnd)
            )
        )
    }

    drawPath(path = path, color = color)
}
