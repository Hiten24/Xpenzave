package com.hcapps.xpenzave.presentation.general_stats

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hcapps.xpenzave.R
import com.hcapps.xpenzave.ui.theme.DefaultCardElevation
import kotlin.math.abs
import kotlin.math.roundToInt

/*val data = listOf(
//    Pair(1, 111.45),
    Pair(1, 0.0),
    Pair(2, 10.0),
    Pair(3, 50.45),
    Pair(4, 500.25),
    Pair(5, 2000.45),
    Pair(6, 150.35),
    Pair(6, 700.0),
    Pair(7, 10000.65),
    Pair(8, 85.15),
    Pair(9, 20.05),
    Pair(10, 0.0),
)*/

/*val data = listOf(
//    Pair(1, 111.45),
    Pair(1, 1153.0),
    Pair(2, 111.0),
    Pair(3, 113.45),
    Pair(4, 112.25),
    Pair(5, 116.45),
    Pair(6, 113.35),
    Pair(6, 1500.0),
    Pair(7, 118.65),
    Pair(8, 110.15),
    Pair(9, 113.05),
    Pair(10, 114.25),
    *//*Pair(11, 116.35),
    Pair(12, 117.45),
    Pair(13, 112.65),
    Pair(14, 115.45),
    Pair(15, 111.85),
    Pair(16, 112.25),
    Pair(17, 111.0),
    Pair(18, 111.45),
    Pair(19, 116.45),
    Pair(20, 113.45),
    Pair(21, 110.15),
    Pair(22, 113.35),
    Pair(23, 118.65),
    Pair(24, 114.25),
    Pair(25, 113.05),
    Pair(26, 115.45),
    Pair(27, 117.45),
    Pair(28, 116.35),
    Pair(29, 111.85),
    Pair(30, 112.65),

    Pair(31, 107.0),
    Pair(32, 112.65),
    Pair(33, 123.0),*//*
)*/

val data = listOf(
    Pair(1, 100.0),
    Pair(2, 200.0),
    Pair(3, 1102.25),
    Pair(4, 400.25),
    Pair(5, 500.45),
    Pair(6, 300.45),
    Pair(7, 800.65),
    Pair(8, 700.0),
    Pair(9, 1000.05),
    Pair(10, 600.35),
    Pair(11, 900.15),
    Pair(12, 100.0),
    Pair(13, 200.0),
    Pair(14, 1102.25),
    Pair(15, 400.25),
    Pair(16, 500.45),
    Pair(17, 300.45),
    Pair(18, 800.65),
    Pair(19, 700.0),
    Pair(20, 1000.05),
    Pair(21, 600.35),
    Pair(22, 900.15),
    Pair(23, 100.0),
    Pair(24, 200.0),
    Pair(25, 1102.25),
    Pair(26, 400.25),
    Pair(27, 500.45),
    Pair(28, 300.45),
    Pair(29, 800.65),
    Pair(30, 700.0)
)

@Composable
fun DayExpenseGraph(
    data: List<Pair<Int, Double>>,
    elevation: Dp = DefaultCardElevation,
    cardColor: Color = MaterialTheme.colorScheme.surface
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(elevation),
        colors = CardDefaults.cardColors(cardColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 22.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            Text(
                text = stringResource(R.string.expenses_category),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            var xStep by remember { mutableStateOf(1) }

            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                data = data,
                xStep = xStep
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                IconButton(
                    onClick = { xStep -= 1 },
                    enabled = xStep > 1
                ) {
                    Icon(imageVector = Icons.Outlined.Remove, contentDescription = "Remove")
                }

                OutlinedButton(
                    onClick = {
                        if (xStep < 3) xStep += 1
                    },
                    shape = MaterialTheme.shapes.small,
                    enabled = false,
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(0.5f))
                ) {
                    Text(text = "Zoom", color = MaterialTheme.colorScheme.primary.copy(0.5f))
                }

                IconButton(
                    onClick = { xStep += xStep },
                    enabled = xStep < 3
                ) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
                }

            }
        }
    }
}

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    xSpace: Float = 80f,
    ySpace: Float = 80f,
    yCount: Int = 5,
    xStep: Int = 1,
    graphColor: Color = MaterialTheme.colorScheme.primary,
    gridColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
    data: List<Pair<Int, Double>> = emptyList()
) {
    
    val upperValue = remember { (data.maxOfOrNull { it.second }?.plus(2))?.roundToInt() ?: 0 }
    val lowerValue = remember { (data.minOfOrNull { it.second }?.minus(2)?.toInt() ?: 0) }
    val density = LocalDensity.current

    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLUE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    var graphStartPosition by remember { mutableStateOf(0f) }

    Canvas(
        modifier = modifier.pointerInput(true) {
            detectHorizontalDragGestures { _, dragAmount ->
                val visibleGrid = size.width / 80f
                graphStartPosition.plus(dragAmount).apply {
                    graphStartPosition = this.coerceIn(
                        minimumValue = 0f - ((data.size * xSpace) - visibleGrid * xSpace),
                        maximumValue = 0f
                    )
                }
            }
        }
    ) {

        // X cord label
        (data.indices step xStep).forEach { i ->
            val hour = data[i].first
            val x = (graphStartPosition + i * xSpace) / xStep
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    x,
                    size.height,
                    textPaint
                )
            }
            drawLine(gridColor, Offset(x, 0f), Offset(x, size.height - ySpace))
        }

        (upperValue - lowerValue) / (yCount - 1).toFloat() // unused
//        val priceStep = (upperValue - lowerValue) / (yCount - 1).toFloat()
        // Y Cord label
        /*(0 until yCount).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + priceStep * i).toString(),
                    30f,
                    size.height - ySpacing - i * size.height / yCount,
                    textPaint
                )
            }
        }*/

        val strokePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val info = data[i]
                val ratio = (info.second - lowerValue) / (upperValue - lowerValue)

                val x1 = (i * xSpace + graphStartPosition) / xStep
                val y1 = abs(height - ySpace - (ratio * height).toFloat())

                if (i == 0) {
                    moveTo(x1, y1)
                }
                drawCircle(color = graphColor, radius = 12f, center = Offset(x1, y1))
                lineTo(x1, y1)
            }
        }

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

    }
}

@Preview
@Composable
fun PreviewDayExpenseGraph() {
    DayExpenseGraph(data)
}