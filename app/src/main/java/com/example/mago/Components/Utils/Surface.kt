package com.example.mago.Components.Utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mago.Components.Style.BottomNavigationItemsState
import com.example.mago.R
import com.example.mago.Screens.Account
import com.example.mago.Screens.Admin
import com.example.mago.Screens.AssignUser
import com.example.mago.Screens.Contact
import com.example.mago.Screens.CreateDevice
import com.example.mago.Screens.CreateUser
import com.example.mago.Screens.Dashboard
import com.example.mago.Screens.FirmwareUpdate
import com.example.mago.Screens.ListComposter
import com.example.mago.Screens.Login
import com.example.mago.Screens.Logout
import com.example.mago.Screens.OpenMetric
import com.example.mago.Screens.Password
import com.example.mago.Screens.Privacy
import com.example.mago.Screens.Profile
import com.example.mago.Screens.Screens
import com.example.mago.Screens.UserManual
import com.example.mago.Viewmodel.DeviceViewModel
import com.example.mago.Viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurfaceMago(
    deviceViewModel: DeviceViewModel,
    userViewModel: UserViewModel,
    token: String?
) {
    val showAll = deviceViewModel.showBottomBar
    val items = BottomNavigationItemsState.createBottomNavigationItems(userViewModel)
    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    var selectedItemTitle by rememberSaveable { mutableStateOf(0) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.padding(start = 0.dp),
            topBar = {
                if (showAll.value) {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        title = {
                            val titleData = deviceViewModel.title.value

                            Text(
                                text = titleData.text,
                                style = if (titleData.useDifferentFont) {
                                    // Use a different font size
                                    MaterialTheme.typography.headlineSmall
                                } else {
                                    // Use the default font size
                                    MaterialTheme.typography.headlineLarge
                                }
                            )
                        },

                        navigationIcon = {

                            when (selectedItemTitle) {
                                4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 -> {
                                    IconButton(
                                        onClick = {
                                            navController.popBackStack()
                                        }) {
                                        Icon(
                                            painter = painterResource(R.drawable.back),
                                            contentDescription = null,
                                        )
                                    }
                                }
                            }
                        }

                    )
                }
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if(token != ""){
                            Screens.List.route}else{
                            Screens.Login.route},
                    ) {

                        composable(Screens.Login.route){
                            Login(navController = navController, userViewModel = userViewModel, deviceViewModel = deviceViewModel)
                        }

                        composable(Screens.List.route) {
                            selectedItemIndex = 0
                            selectedItemTitle = 0
                            deviceViewModel.updateTitle(stringResource(id = R.string.myDevices), false)
                            ListComposter(
                                navController = navController,
                                deviceViewModel = deviceViewModel,
                                userViewModel = userViewModel
                            )
                        }

                        composable(
                            route = "Dashboard/{deviceID}",
                            arguments = listOf(navArgument("deviceID") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            // Retrieve the device ID from the arguments
                            val deviceID =
                                backStackEntry.arguments?.getString("deviceID")
                            selectedItemIndex = 1
                            selectedItemTitle = 1
                            deviceViewModel.updateTitle(stringResource(id = R.string.dashboard), false)

                            // Pass the device ID to the Dashboard composable
                            Dashboard(
                                navController = navController,
                                deviceViewModel = deviceViewModel,
                                deviceID = deviceID,
                                userViewModel = userViewModel
                            )
                        }

                        composable(Screens.Admin.route) {
                            selectedItemIndex = 2
                            selectedItemTitle = 2
                            deviceViewModel.updateTitle(stringResource(id = R.string.admin), false)
                            Admin(
                                navController = navController
                            )
                        }

                        composable(Screens.CreateUser.route) {
                            selectedItemIndex = 2
                            selectedItemTitle = 12
                            deviceViewModel.updateTitle(stringResource(id = R.string.admin_createUser), true)
                            CreateUser(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }

                        composable(Screens.AssignUser.route) {
                            selectedItemIndex = 2
                            selectedItemTitle = 13
                            deviceViewModel.updateTitle(stringResource(id = R.string.admin_assignUser), true)
                            AssignUser(
                                navController = navController,
                                userViewModel = userViewModel,
                                deviceViewModel = deviceViewModel)
                        }

                        composable(Screens.CreateDevice.route) {
                            selectedItemIndex = 2
                            selectedItemTitle = 11
                            deviceViewModel.updateTitle(stringResource(id = R.string.admin_createDevice), true)
                            CreateDevice(
                                navController = navController,
                                deviceViewModel = deviceViewModel
                            )
                        }



                        composable(Screens.Account.route) {
                            selectedItemIndex = 3
                            selectedItemTitle = 3
                            deviceViewModel.updateTitle(stringResource(id = R.string.account), false)
                            Account(navController = navController)
                        }

                        composable(Screens.Profile.route) {
                            selectedItemIndex = 3
                            selectedItemTitle = 4
                            deviceViewModel.updateTitle(stringResource(id = R.string.profile), true)
                            Profile(navController = navController)
                        }


                        composable(Screens.UserManual.route) {
                            selectedItemIndex = 3
                            selectedItemTitle = 6
                            deviceViewModel.updateTitle(stringResource(id = R.string.userManual), true)
                            UserManual(navController = navController)
                        }

                        composable(Screens.Contact.route) {
                            selectedItemIndex = 3
                            selectedItemTitle = 7
                            deviceViewModel.updateTitle(stringResource(id = R.string.contact), true)
                            Contact(navController = navController, userViewModel = userViewModel)
                        }

                        composable(Screens.Privacy.route) {
                            selectedItemIndex = 3
                            selectedItemTitle = 8
                            deviceViewModel.updateTitle(stringResource(id = R.string.privacy), true)
                            Privacy(navController = navController)
                        }

                        composable(Screens.Password.route) {
                            selectedItemIndex = 3
                            selectedItemTitle = 9
                            deviceViewModel.updateTitle(stringResource(id = R.string.password), true)
                            Password(navController = navController, userViewModel = userViewModel)
                        }

                        composable(Screens.Logout.route) {
                            selectedItemIndex = 3
                            selectedItemTitle = 10
                            deviceViewModel.updateTitle(stringResource(id = R.string.logout), true)
                            Logout(navController = navController, deviceViewModel = deviceViewModel)
                        }

                        composable(
                            route = Screens.FirmwareUpdate.route + "/{deviceId}",
                            arguments = listOf(
                                navArgument("deviceId") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val deviceId = backStackEntry.arguments?.getString("deviceId") ?: ""
                            deviceViewModel.updateTitle(stringResource(id = R.string.firmwareUpdate), true)
                            // Use the deviceId for firmware update logic
                            FirmwareUpdate(navController = navController, deviceViewModel = deviceViewModel, deviceId = deviceId.toInt())
                            selectedItemIndex = 1
                            selectedItemTitle = 5
                        }


                        composable(
                            Screens.Metric.route + "/{clickedTileName}/{deviceId}",
                            arguments = listOf(
                                navArgument("clickedTileName") {
                                    type = NavType.StringType
                                },
                                navArgument("deviceId") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val clickedTileName =
                                backStackEntry.arguments?.getString("clickedTileName")
                                    ?: ""
                            val deviceId =
                                backStackEntry.arguments?.getString("deviceId")
                                    ?: ""

                            when (clickedTileName) {
                                "Temperature" -> {
                                    selectedItemTitle = 14
                                    deviceViewModel.updateTitle(stringResource(id = R.string.temperature), true)

                                }

                                "Material In" -> {
                                    selectedItemTitle = 17
                                    deviceViewModel.updateTitle("Material In", true)
                                }
                            }
                            selectedItemIndex = 1
                            selectedItemTitle = deviceViewModel.selectedFilterMetricArrow.value
                            deviceViewModel.selectedFilterMetric.value =
                                clickedTileName
                            deviceViewModel.updateTitle(clickedTileName, true)
                            OpenMetric(
                                navController = navController,
                                deviceId = deviceId,
                                deviceViewModel = deviceViewModel
                            )
                        }
                    }
                }

            },
            bottomBar = {
                if (showAll.value) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                    ) {
                        items.filter { it.isVisible }.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index

                                    // Navigation
                                    when (index) {
                                        0 -> navController.navigate(Screens.List.route)
                                        1 -> navController.navigate(
                                            Screens.Dashboard.createDashboardRoute(
                                                deviceViewModel.currentPage
                                            )
                                        )

                                        2 -> {
                                            if (items[2].isVisible) {
                                                navController.navigate(Screens.Admin.route)
                                            } else {
                                                navController.navigate(Screens.Account.route)
                                            }
                                        }
                                        3 -> navController.navigate(Screens.Account.route)
                                    }
                                },
                                label = {
                                    val textStyle = if (index == selectedItemIndex) {
                                        MaterialTheme.typography.bodyMedium
                                    } else {
                                        MaterialTheme.typography.labelMedium
                                    }

                                    Text(
                                        text = item.title,
                                        style = textStyle
                                    )
                                },
                                alwaysShowLabel = true,
                                icon = {
                                    Box(
                                        modifier = Modifier.size(24.dp) // Adjust the size as needed
                                    ) {
                                        Icon(
                                            painter = painterResource(id = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon),
                                            contentDescription = null,
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        )
    }

}