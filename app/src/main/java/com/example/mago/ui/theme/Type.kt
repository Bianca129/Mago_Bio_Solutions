package com.example.mago.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mago.R


private val Raleway = FontFamily(
    Font(R.font.raleway_bold, FontWeight.W700),
    Font(R.font.raleway_medium, FontWeight.W500),
    Font(R.font.raleway_regular, FontWeight.W400),
    Font(R.font.raleway_semi_bold, FontWeight.W600)
)

val RalewayTypography = androidx.compose.material3.Typography(
    displayLarge = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp
    ),

    displayMedium = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.SemiBold,
        fontSize = 45.sp
    ),

    displaySmall = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    titleLarge = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),

    titleMedium = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),

    titleSmall = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp),

    bodyLarge = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),

    bodySmall = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    labelMedium = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.Light,
        fontSize = 11.sp
    ),


)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

