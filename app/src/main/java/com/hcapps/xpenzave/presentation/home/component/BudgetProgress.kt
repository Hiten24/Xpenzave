package com.hcapps.xpenzave.presentation.home.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun BudgetProgress(
    archWidth: Float = 120f,
    progress: Int = 100,
    containerWidth: Dp = 200.dp,
    progressContainerColor: Color = Color.LightGray,
    textColor: Color = MaterialTheme.colorScheme.primary,
    progressGradient: List<Color>
) {

    val pathPortion = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true) {
        pathPortion.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .width(containerWidth)
                .height(containerWidth / 2)
        ) {
            val canvasSize = Offset(size.width, size.height)
            val reduceArchWidth = archWidth / 2f

            val path = Path().apply {
                moveTo(reduceArchWidth, canvasSize.y)
                cubicTo(
                    reduceArchWidth,
                    (-(canvasSize.y / 4) + (archWidth / 2)), // first control point
                    canvasSize.x - reduceArchWidth,
                    (-(canvasSize.y / 4) + (archWidth / 2)), // second control point
                    canvasSize.x - reduceArchWidth,
                    canvasSize.y // end point (connect point / close point)
                )
            }
            val outPath = Path()
            PathMeasure().apply {
                setPath(path, false)
                val progressValue = length.times(progress).div(100)
                Timber.d("length: $length, progress: $progressValue")
                getSegment(0f, progressValue, outPath)
            }
            // background of the budget progress indicator
            drawPath(path = path, color = progressContainerColor, style = Stroke(width = archWidth))
            // progress bar of the budget progress indicator
            drawPath(
                path = outPath,
                brush = Brush.horizontalGradient(progressGradient),
                style = Stroke(width = archWidth)
            )
        }

        Text(
            text = "$progress%",
            color = textColor,
            fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )

    }
}

@Preview
@Composable
fun PreviewBudgetProgress() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))
        BudgetProgress(progressGradient = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.primary,
        ))
    }
}