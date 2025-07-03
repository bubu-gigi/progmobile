package com.univpm.gameon.ui.components

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.univpm.gameon.core.futuraBookFontFamily

@Composable
fun CustomText(
    text: String,
    color: Color = Color.White,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    fontFamily: FontFamily = futuraBookFontFamily,
    modifier: Modifier = Modifier,
    style: TextStyle? = null,
) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = style ?: TextStyle(
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = 2.sp,
        )
    )
}

