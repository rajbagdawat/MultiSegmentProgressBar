package com.example.multisegment_pb

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MultiSegmentProgressBar(
    acceptedRatio: Float,
    inProgressRatio: Float,
    rejectedRatio: Float,
    acceptedColor: Color = Color(0xFF0FDEC3),
    inProgressColor: Color = Color(0xFF95ECE3),
    rejectedColor: Color = Color(0xFFD32F2F),
    orientation: ProgressOrientation = ProgressOrientation.Horizontal,
    modifier: Modifier = Modifier
) {


    val totalRatio = acceptedRatio + inProgressRatio + rejectedRatio

    val normalizedAccepted = acceptedRatio / totalRatio
    val normalizedInProgress = inProgressRatio / totalRatio
    val normalizedRejected = rejectedRatio / totalRatio

    val canvasModifier = when (orientation) {
        ProgressOrientation.Horizontal -> modifier
            .fillMaxWidth()
            .height(20.dp)
        ProgressOrientation.Vertical -> modifier
            .fillMaxHeight()
            .width(20.dp)
    }

    RoundedCornerShape(
        topStart = 10.dp,
        bottomStart = 10.dp,
        topEnd = 0.dp,
        bottomEnd = 0.dp
    )

    Canvas(modifier = canvasModifier) {
        val width = size.width
        val height = size.height

        when (orientation) {
            ProgressOrientation.Horizontal -> {
                var startX = 0f
                val radius = 10.dp.toPx()

                val acceptedWidth = width * normalizedAccepted
                drawCustomRoundRect(
                    color = acceptedColor,
                    topLeft = Offset(startX, 0f),
                    size = Size(acceptedWidth, height),
                    radiusTopStart = radius,
                    radiusBottomStart = radius
                )
                startX += acceptedWidth

                val inProgressWidth = width * normalizedInProgress
                drawRect(
                    color = inProgressColor,
                    topLeft = Offset(startX, 0f),
                    size = Size(inProgressWidth, height)
                )
                startX += inProgressWidth

                val rejectedWidth = width * normalizedRejected
                drawCustomRoundRect(
                    color = rejectedColor,
                    topLeft = Offset(startX, 0f),
                    size = Size(rejectedWidth, height),
                    radiusTopEnd = radius,
                    radiusBottomEnd = radius
                )
            }

            ProgressOrientation.Vertical -> {
                var startY = 0f
                val radius = 10.dp.toPx()
                val rejectedHeight = height * normalizedRejected
                drawCustomRoundRect(
                    color = rejectedColor,
                    topLeft = Offset(0f, startY),
                    size = Size(width, rejectedHeight),
                    radiusTopStart = radius,
                    radiusTopEnd = radius
                )
                startY += rejectedHeight

                val inProgressHeight = height * normalizedInProgress
                drawRect(
                    color = inProgressColor,
                    topLeft = Offset(0f, startY),
                    size = Size(width, inProgressHeight)
                )
                startY += inProgressHeight

                val acceptedHeight = height * normalizedAccepted
                drawCustomRoundRect(
                    color = acceptedColor,
                    topLeft = Offset(0f, startY),
                    size = Size(width, acceptedHeight),
                    radiusBottomStart = radius,
                    radiusBottomEnd = radius
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultiSegmentProgressBarPreview() {
    MultiSegmentProgressBar(
        acceptedRatio = 0.4f,
        inProgressRatio = 0.3f,
        rejectedRatio = 0.3f
    )
}