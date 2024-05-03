package com.jesse.contractai.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jesse.contractai.R

private val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular, weight = FontWeight.W400),
    Font(R.font.poppins_bold, weight = FontWeight.W600),
    Font(R.font.poppins_italic, weight = FontWeight.W400, style = FontStyle.Italic),
    Font(R.font.poppins_medium, weight = FontWeight.W500)
)
val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    titleSmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    ),
    titleLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        fontStyle = FontStyle.Italic
    )
)