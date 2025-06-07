package com.jefisu.trackizer.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import trackizer.core.designsystem.generated.resources.Res
import trackizer.core.designsystem.generated.resources.inter
import trackizer.core.designsystem.generated.resources.inter_bold
import trackizer.core.designsystem.generated.resources.inter_medium
import trackizer.core.designsystem.generated.resources.inter_semibold

data class Typography(
    val headline7: TextStyle,
    val headline6: TextStyle,
    val headline5: TextStyle,
    val headline4: TextStyle,
    val headline3: TextStyle,
    val headline2: TextStyle,
    val headline1: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
)

@Composable
internal fun interTypography() = Typography(
    headline7 = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp,
        fontFamily = interFontFamily(),
    ),
    headline6 = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 48.sp,
        fontFamily = interFontFamily(),
    ),
    headline5 = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 36.sp,
        fontFamily = interFontFamily(),
    ),
    headline4 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 32.sp,
        fontFamily = interFontFamily(),
    ),
    headline3 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
        fontFamily = interFontFamily(),
    ),
    headline2 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        fontFamily = interFontFamily(),
    ),
    headline1 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 16.sp,
        fontFamily = interFontFamily(),
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        fontFamily = interFontFamily(),
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        fontFamily = interFontFamily(),
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        fontFamily = interFontFamily(),
    ),
)

@Composable
private fun interFontFamily() = FontFamily(
    Font(Res.font.inter, FontWeight.Normal),
    Font(Res.font.inter_medium, FontWeight.Medium),
    Font(Res.font.inter_semibold, FontWeight.SemiBold),
    Font(Res.font.inter_bold, FontWeight.Bold),
)
