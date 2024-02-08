package com.example.mago.Components.Style

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.mago.Data.BottomNavigationItem
import com.example.mago.R
import com.example.mago.Viewmodel.UserViewModel

object BottomNavigationItemsState {
    @Composable
    fun createBottomNavigationItems(userViewModel: UserViewModel): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                title = stringResource(id = R.string.list),
                selectedIcon = (R.drawable.list_clicked),
                unselectedIcon = (R.drawable.list_unclicked),
                hasNews = false,
                isVisible = true
            ),
            BottomNavigationItem(
                title = stringResource(id = R.string.dashboard),
                selectedIcon = (R.drawable.dashboard_clicked),
                unselectedIcon = (R.drawable.dashboard_unclicked),
                hasNews = false,
                isVisible = true
            ),
            BottomNavigationItem(
                title = stringResource(id = R.string.admin),
                selectedIcon = (R.drawable.admin_clicked),
                unselectedIcon = (R.drawable.admin_unclicked),
                hasNews = false,
                isVisible = userViewModel.isAdmin.value
            ),
            BottomNavigationItem(
                title = stringResource(id = R.string.account),
                selectedIcon = (R.drawable.account_clicked),
                unselectedIcon = (R.drawable.account_unclicked),
                hasNews = false,
                isVisible = true
            )
        )
    }
}