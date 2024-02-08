package com.example.mago.Components.Dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.Components.Style.LoadingIndicator
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel
import com.example.mago.ui.theme.ComplementaryGrey

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun adminDashboardPager(
    deviceViewModel: DeviceViewModel,
    currentPage: Int,
    navController: NavController,
    userViewModel: UserViewModel
) {
    val devicesAdmin by deviceViewModel.devicesAdmin.observeAsState(emptyList())

    // Use the converted currentPage to set up the pager state for admin users
    val pagerState = rememberPagerState(pageCount = { devicesAdmin.size }, initialPage = currentPage)
    val isDataLoading by deviceViewModel.isLoading.observeAsState()

    if(isDataLoading == true){
        LoadingIndicator()
    }else {

        LazyColumn {
            item {
                // Row for pager indicators
                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Pager indicators for each device page
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.tertiary else ComplementaryGrey
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(5.dp)
                        )
                    }
                }

                // HorizontalPager to display each device's dashboard for admin users
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) { page ->
                    DeviceCard(navController, devicesAdmin[page], deviceViewModel, userViewModel)
                }
            }
        }
    }
}
