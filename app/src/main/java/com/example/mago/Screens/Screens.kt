package com.example.mago.Screens

sealed class Screens(val route: String) {
    object List : Screens("List")
    object Dashboard : Screens("Dashboard")

    // New object class for the dashboard with a deviceID
    data class DashboardWithDeviceID(val deviceID: Int) : Screens("Dashboard/${deviceID}")
    fun createDashboardRoute(deviceID: Int?): String {
        return if (deviceID != null) {
            DashboardWithDeviceID(deviceID).route
        } else {
            Dashboard.route
        }
    }

    object Admin : Screens("Admin")
    object Account : Screens("Account")
    object Profile : Screens("Profile")
    object UserManual : Screens("UserManual")
    object Contact : Screens("Contact")
    object Privacy : Screens("Privacy")
    object Password : Screens("Password")
    object Logout : Screens("Logout")
    object CreateDevice : Screens("CreateDevice")
    object CreateUser : Screens("CreateUser")
    object AssignUser : Screens("AssignUser")
    object Metric : Screens("Metric")
    object Login: Screens("Login")
    object FirmwareUpdate: Screens("FirmwareUpdate")

}




