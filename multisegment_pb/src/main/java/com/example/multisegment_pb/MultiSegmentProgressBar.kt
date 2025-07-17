package com.example.multisegment_pb

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MultiSegmentProgressBar(
    acceptedValue: Float,
    inProgressValue: Float,
    rejectedValue: Float,
    totalValue: Float,
    acceptedColor: Color = Color(0xFF18CDE4),
    inProgressColor: Color = Color(0xFFA9E9F3),
    rejectedColor: Color = Color(0xFFB10505),
    backgroundColor: Color = Color(0xFFDBD9D9),
    orientation: ProgressOrientation = ProgressOrientation.Horizontal,
    modifier: Modifier = Modifier,
    cornerRadiusDp: Float? = null
) {

    val animatedAccepted by animateFloatAsState(
        targetValue = acceptedValue,
        animationSpec = tween(durationMillis = 500),
        label = "accepted"
    )

    val animatedInProgress by animateFloatAsState(
        targetValue = inProgressValue,
        animationSpec = tween(durationMillis = 500),
        label = "inProgress"
    )

    val animatedRejected by animateFloatAsState(
        targetValue = rejectedValue,
        animationSpec = tween(durationMillis = 500),
        label = "rejected"
    )

    val canvasModifier = when (orientation) {
        ProgressOrientation.Horizontal -> modifier
            .fillMaxWidth()
            .height(20.dp)

        ProgressOrientation.Vertical -> modifier
            .fillMaxHeight()
            .width(20.dp)
    }

    Canvas(modifier = canvasModifier) {
        val width = size.width
        val height = size.height
        val radius = when {
            cornerRadiusDp != null -> cornerRadiusDp.dp.toPx()
            orientation == ProgressOrientation.Horizontal -> size.height / 2f
            else -> size.width / 2f
        }


        when (orientation) {
            ProgressOrientation.Horizontal -> {

                // 1. Draw the container with rounded corners (empty space)
                drawRoundRect(
                    color = backgroundColor,
                    topLeft = Offset.Zero,
                    size = Size(width, height),
                    cornerRadius = CornerRadius(radius, radius)
                )

                val path = androidx.compose.ui.graphics.Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = androidx.compose.ui.geometry.Rect(0f, 0f, width, height),
                            cornerRadius = CornerRadius(radius, radius)
                        )
                    )
                }

                clipPath(path) {
                    var startX = 0f

                    // Accepted
                    val acceptedWidth = width * animatedAccepted / totalValue
                    if (acceptedWidth > 0f) {
                        drawRect(
                            color = acceptedColor,
                            topLeft = Offset(startX, 0f),
                            size = Size(acceptedWidth, height)
                        )
                        startX += acceptedWidth
                    }

                    // Rejected
                    val rejectedWidth = width * animatedRejected / totalValue
                    if (rejectedWidth > 0f) {
                        drawRect(
                            color = rejectedColor,
                            topLeft = Offset(startX, 0f),
                            size = Size(rejectedWidth, height)
                        )
                        startX += rejectedWidth
                    }

                    // In Progress
                    val inProgressWidth = width * animatedInProgress / totalValue
                    if (inProgressWidth > 0f) {
                        drawRect(
                            color = inProgressColor,
                            topLeft = Offset(startX, 0f),
                            size = Size(inProgressWidth, height)
                        )
                    }
                }
            }
            ProgressOrientation.Vertical -> {
                // Draw background rounded container
                drawRoundRect(
                    color = backgroundColor,
                    topLeft = Offset.Zero,
                    size = Size(width, height),
                    cornerRadius = CornerRadius(radius, radius)
                )

                val path = androidx.compose.ui.graphics.Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(0f, 0f, width, height),
                            cornerRadius = CornerRadius(radius, radius)
                        )
                    )
                }

                clipPath(path) {
                    var currentHeight = 0f

                    val inProgressHeight = height * animatedInProgress / totalValue
                    val rejectedHeight = height * animatedRejected / totalValue
                    val acceptedHeight = height * animatedAccepted / totalValue

                    // Start from the bottom
                    if (acceptedHeight > 0) {
                        drawRect(
                            color = acceptedColor,
                            topLeft = Offset(0f, height - acceptedHeight - currentHeight),
                            size = Size(width, acceptedHeight)
                        )
                        currentHeight += acceptedHeight
                    }

                    if (rejectedHeight > 0) {
                        drawRect(
                            color = rejectedColor,
                            topLeft = Offset(0f, height - rejectedHeight - currentHeight),
                            size = Size(width, rejectedHeight)
                        )
                        currentHeight += rejectedHeight
                    }

                    if (inProgressHeight > 0) {
                        drawRect(
                            color = inProgressColor,
                            topLeft = Offset(0f, height - inProgressHeight - currentHeight),
                            size = Size(width, inProgressHeight)
                        )
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultiSegmentProgressBarPreview() {
    MultiSegmentProgressBar(
        acceptedValue =0f,
        rejectedValue =0f,
        inProgressValue = 0f,
        totalValue = 100f
    )
}