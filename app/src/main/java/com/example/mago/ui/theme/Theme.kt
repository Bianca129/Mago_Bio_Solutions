package com.example.mago.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val DarkColorPalette = darkColorScheme(
    primary = MagoBlue,
    onPrimary = ComplementaryGrey,
    primaryContainer = Black,

    secondary = Grey,
    onSecondary = White,
    secondaryContainer = ComplementaryBlue,

    //Buttons zb
    tertiary = ComplementaryBlue,
    onTertiary = ComplementaryGrey,
    tertiaryContainer = ComplementaryGrey,
    onTertiaryContainer = Grey,

    background = Black,
    onBackground = White,

    outline = StatusGreen,
    outlineVariant = ComplementaryGrey,

    error = StatusRed,
    onError = StatusYellow
)

private val LightColorPalette = lightColorScheme(
    primary = MagoGreen,
    onPrimary = ComplementaryGreen,
    primaryContainer = White,


    secondary = Grey,
    onSecondary = White,
    secondaryContainer = MagoGreen,

    //Buttons zb
    tertiary = ComplementaryGreen, //darkgreen --> blue
    onTertiary = White, //white --> lightgrey
    tertiaryContainer = ComplementaryGrey, //Lightgrey -->lightgreyy
    onTertiaryContainer = Grey, //darkgrey

    //status
    //errorCard = StatusRed,

    background = White,
    onBackground = Black,


    outline = StatusGreen,
    outlineVariant = ComplementaryGrey,
    error = StatusRed,
    onError = StatusYellow

)

@Composable
fun MagoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (!darkTheme) {
            LightColorPalette
        } else {
            DarkColorPalette
        }
    /*
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

     */

    MaterialTheme(
        colorScheme = colorScheme,
        typography = RalewayTypography,
        content = content
    )
}

