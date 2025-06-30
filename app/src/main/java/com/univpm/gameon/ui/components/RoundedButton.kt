package com.univpm.gameon.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RoundedButtonComponent(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFF232323),
    contentColor: Color = Color.White,
    borderColor: Color = Color(0xFFE36BE0)
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .border(BorderStroke(2.dp, borderColor), shape = RoundedCornerShape(120.dp))
            .size(width = 230.dp, height = 50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        CustomText(text = text)
    }
}
