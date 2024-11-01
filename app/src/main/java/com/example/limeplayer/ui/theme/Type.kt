package com.example.limeplayer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.limeplayer.R

val CustomFont = FontFamily(
    Font(resId = R.font.nunito_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.nunito_bold, weight = FontWeight.Bold),
    Font(resId = R.font.nunito_light, weight = FontWeight.Light),
    Font(resId = R.font.nunito_medium, weight = FontWeight.Medium),
    Font(resId = R.font.nunito_regular, weight = FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleMedium = TextStyle(
        fontFamily = CustomFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
)