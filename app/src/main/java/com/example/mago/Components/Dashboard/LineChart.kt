package com.example.mago.Components.Dashboard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import kotlin.math.roundToInt

@Composable
fun LineChartScreen(pointsData: List<Point>) {
    // Set minYValue to 0 to ensure the y-axis starts at 0
    val minYValue = 0f

    // Find the lowest and highest y values in the dataset
    val lowestYValue = pointsData.minByOrNull { it.y }?.y ?: 0f
    val maxYValue = pointsData.maxByOrNull { it.y }?.y ?: 0f

    // Calculate the y-axis range
    val yRange = maxYValue - lowestYValue

    // Define the number of steps on the y-axis
    val steps = 5

    // Build configuration for the x-axis
    val xAxisData = AxisData.Builder()
        .axisStepSize(40.dp) // Smaller steps for the x-axis
        .backgroundColor(MaterialTheme.colorScheme.background)
        .steps(pointsData.size - 1)
        .labelData { i ->
            if (i < pointsData.size) {
                val xValue = pointsData[i].x.roundToInt()
                return@labelData xValue.toString() // Return the X value of the point as the label
            } else {
                ""
            }
        }
        .labelAndAxisLinePadding(10.dp) // Increase the padding value
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    // Build configuration for the y-axis
    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(MaterialTheme.colorScheme.background)
        .labelAndAxisLinePadding(12.dp)
        .labelData { i ->
            // Calculate the y value for the current step
            val yValue = minYValue + i * (yRange / steps) + lowestYValue
            String.format("%.2f", yValue) // Display Y values with two decimal places
        }
        .axisLineColor(MaterialTheme.colorScheme.tertiary)
        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    // Build configuration for the line chart
    val lineChartData = LineChartData(
        paddingRight = 0.dp,
        containerPaddingEnd = 0.dp,
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.tertiary
                    ),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.tertiary),
                    ShadowUnderLine(
                        alpha = 0.7f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.tertiary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        backgroundColor = MaterialTheme.colorScheme.background,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = Color.Transparent)
    )

    // Display the LineChart
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(top = 0.dp, bottom = 0.dp, end = 0.dp),
        lineChartData = lineChartData
    )
}



