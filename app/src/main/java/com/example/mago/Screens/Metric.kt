@file:Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")

package com.example.mago.Screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import co.yml.charts.common.model.Point
import com.example.mago.Components.Style.BottomSheetWithOutContent
import com.example.mago.Components.Dashboard.FilterMetrics
import com.example.mago.Components.Dashboard.FilterTimeSelectorDiagram
import com.example.mago.Components.Dashboard.LineChartScreen
import com.example.mago.Components.Style.LoadingIndicator
import com.example.mago.Components.Utils.fetchDataIfRequired
import com.example.mago.Components.Style.getDrawableId
import com.example.mago.Components.Style.getMaterialInColorText
import com.example.mago.Components.Style.getTemperatureColorText
import com.example.mago.Data.DeviceMetrics
import com.example.mago.R
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.ui.theme.Grey


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenMetric(navController: NavController, deviceId: String, deviceViewModel: DeviceViewModel) {
    val aggLogs by deviceViewModel.aggLogs.observeAsState()
    var dataList = mutableListOf<Map<String, Any>>()

    // Declaring the variable `unit` with an initial value (empty string in this case)
    var unit = ""
    var metricValue: Double

    // Extract metric fieldId based on selectedFilterMetric
    val fieldId: Int = when (deviceViewModel.selectedFilterMetric.value) {
        "Temperature" -> 1
        "Material In" -> 17
        else -> 0
    }

    LaunchedEffect(key1 = true) {
        fetchDataIfRequired(deviceViewModel, fieldId, deviceId)
    }

    val isDataLoading by deviceViewModel.isLoading.observeAsState()

    if (aggLogs != null) {
        val newDataList = mutableListOf<Map<String, Any>>()
        for (aggLog in aggLogs!!) {
            val dataMap = mapOf(
                "referenceDate" to aggLog.referenceDate,
                "averageValue" to aggLog.averageValue,
                "maxValue" to aggLog.maxValue,
                "minValue" to aggLog.minValue
            )

            newDataList.add(dataMap)
        }
        dataList = newDataList
        // Sort the dataList by referenceDate, oldest first
        dataList.sortBy { it["referenceDate"] as String }

    }


    val context = LocalContext.current

    val selectedMetric = deviceViewModel.getMetricsForDevice(deviceId)
    var metricForDevice: DeviceMetrics?

    selectedMetric.observe((context as LifecycleOwner)) { deviceMetrics ->
        // Extract the specific value based on deviceId
        val metricValueForDevice =
            deviceMetrics?.find { it.field.name == selectedMetric.toString() }
        metricForDevice = metricValueForDevice
    }

    // Define the sheet state
    val sheetStateLog = rememberModalBottomSheetState()
    val scrollState = rememberScrollState()

    // Mutable state to control the visibility of the bottom sheet
    var showBottomSheetLog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp),
    ) {
        Row() {
            FilterMetrics(
                deviceId,
                deviceViewModel,
                deviceViewModel.selectedFilterMetric.value
            ) { newFilter ->
                deviceViewModel.selectedFilterMetric.value = newFilter
                deviceViewModel.selectedFilterMetric.value?.let {
                    deviceViewModel.updateTitle(
                        it,
                        true
                    )
                }
                deviceViewModel.selectedFilterMetricArrow.value = 19
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            deviceViewModel.selectedFilterMetric.value?.let { selectedFilter ->
                metricValue = selectedMetric.value?.find{it.field.name == selectedFilter}?.value!!

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = getDrawableId(selectedFilter)),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(0.dp)
                            .size(70.dp),
                        tint = when (selectedFilter) {
                            "Temperature" -> {
                                getTemperatureColorText(metricValue)
                            }
                            "Material In" -> {
                                getMaterialInColorText(metricValue)
                            }
                            else -> {
                                //MaterialTheme.colorScheme.tertiary
                                MaterialTheme.colorScheme.onError
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column() {
                    Text(
                        "currently",
                        style = MaterialTheme.typography.bodySmall,
                        color = when (selectedFilter) {
                            "Temperature" -> {
                                getTemperatureColorText(metricValue)
                            }
                            "Material In" -> {
                                getMaterialInColorText(metricValue)
                            }
                            else -> {
                                MaterialTheme.colorScheme.tertiary
                            }
                        }
                    )
                    // Assigning a value to the variable `unit`
                    unit = selectedMetric.value?.find { it.field.name == selectedFilter }?.field?.unit?.symbol ?: ""
                    // check if Null for selectedMetric.value
                    (when (selectedFilter) {
                        "Temperature" -> {
                            getTemperatureColorText(metricValue)
                        }

                        "Material In" -> {
                            getMaterialInColorText(metricValue)
                        }

                        else -> {
                            MaterialTheme.colorScheme.tertiary
                        }
                    }).let {
                        Text(
                            "%.2f".format(metricValue) + " $unit ",
                            style = MaterialTheme.typography.displayLarge,
                            color =it,
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(top= 20.dp, end = 0.dp)) {


            if (isDataLoading == true) {
                LoadingIndicator()
            } else if (dataList.isNotEmpty()) {
                val pointsData = dataList.mapIndexed { index, dataMap ->
                    val xValue = index.toFloat()
                    val yValue = (dataMap["averageValue"] as? Double)?.toFloat() ?: 0.0f
                    Point(xValue, yValue)
                }

                Text(unit, modifier = Modifier.padding(start= 40.dp, bottom= 0.dp, end = 0.dp))

                //insert Diagram
                LineChartScreen(pointsData = pointsData)

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end =0.dp, top=0.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                    val text = when (deviceViewModel.selectedFilterDiagramMetric.value) {
                        "Yearly" -> {
                            "years"
                        }
                        "Monthly" -> {
                            "months"
                        }
                        else -> {
                            "weeks"
                        }
                    }
                    //Text("This diagram shows the last ${dataList.size} $text.")
                    Text(text)
                }
                Spacer(modifier = Modifier.height(40.dp))

                //Diagram-filter: Week. Month & Year
                Row(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    FilterTimeSelectorDiagram(deviceViewModel, deviceId.toInt(), fieldId)
                }
                Spacer(modifier = Modifier.height(25.dp))
            }

        }

        //Download-Button & Logs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 0.dp)
                    .fillMaxWidth(0.3f)
                    .clickable {
                        Toast.makeText(context, "Not possible", Toast.LENGTH_LONG).show()
                    },
                horizontalAlignment = Alignment.Start,

                ) {
                Row() {
                    Spacer(modifier = Modifier.width(14.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.download_arrow_down),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(0.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Grey)) {
                            append("Download")
                        }
                    },
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(0.dp)
                        ,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                append("Show Logs")
                            }
                        },
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .clickable { showBottomSheetLog = true }
                            .padding(0.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }

    if (showBottomSheetLog) {
        // Display the bottom sheet using ModalBottomSheet
        BottomSheetWithOutContent(sheetStateLog,
            onDismiss = {
                showBottomSheetLog = false
            })
    }
}




