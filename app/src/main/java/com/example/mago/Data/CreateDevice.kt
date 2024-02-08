data class CreateDevice(
    val name: String,
    val deviceTypeId : Int,
    val sendSettingsAtConn: Boolean,
    val sendSettingsNow: Boolean,
    val authId: String,
    val password: String
)

