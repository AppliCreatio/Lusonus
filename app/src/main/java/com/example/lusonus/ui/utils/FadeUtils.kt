package com.example.lusonus.ui.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp

// The best way I found to getting a fade going was to make a custom util function.
// Go read the docs if you are confused on the Modifier junk.
fun Modifier.fadeOuterEdge(
    isToLeft: Boolean,
    isToRight: Boolean,
    fadeWidth: Dp,
    color: Color
): Modifier = this.then(
    Modifier.drawWithCache {
        val fadeWidthPixels = fadeWidth.toPx()

        onDrawWithContent {
            // This will draw all the normal stuff first.
            drawContent()

            // If there is something to the left to fade.
            if (isToLeft) {
                // Gradients are cool.
                val brush = Brush.horizontalGradient(
                    colors = listOf(
                        color.copy(alpha = 1f),
                        color.copy(alpha = 0f),
                    ),
                    startX = 0f,
                    endX = fadeWidthPixels
                )

                // We update the draw.
                drawRect(
                    brush = brush,
                    topLeft = androidx.compose.ui.geometry.Offset(0f, 0f),
                    size = androidx.compose.ui.geometry.Size(fadeWidthPixels, size.height),
                    blendMode = BlendMode.SrcOver
                )
            }

            // If there is something to the right to fade.
            if (isToRight) {
                // Gradients are cool.
                val brush = Brush.horizontalGradient(
                    colors = listOf(
                        color.copy(alpha = 0f),
                        color.copy(alpha = 1f),
                    ),
                    startX = size.width - fadeWidthPixels,
                    endX = size.width
                )

                // We update the draw.
                drawRect(
                    brush = brush,
                    topLeft = androidx.compose.ui.geometry.Offset(size.width - fadeWidthPixels, 0f),
                    size = androidx.compose.ui.geometry.Size(fadeWidthPixels, size.height),
                    blendMode = BlendMode.SrcOver
                )
            }
        }
    }
)

fun Modifier.fadeBottomEdge(
    fadeHeight: Dp,
    color: Color
): Modifier = this.then(
    Modifier.drawWithCache {
        val fadeHeightPx = fadeHeight.toPx()

        onDrawWithContent {
            drawContent()

            val brush = Brush.verticalGradient(
                colors = listOf(
                    color.copy(alpha = 0f),
                    color.copy(alpha = 1f),
                ),
                startY = size.height - fadeHeightPx,
                endY = size.height
            )

            drawRect(
                brush = brush,
                topLeft = androidx.compose.ui.geometry.Offset(0f, size.height - fadeHeightPx),
                size = androidx.compose.ui.geometry.Size(size.width, fadeHeightPx),
                blendMode = BlendMode.SrcOver
            )
        }
    }
)
