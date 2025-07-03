package com.univpm.gameon.ui.components

import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonComponentElimina(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .border(BorderStroke(2.dp, Color.Red), RoundedCornerShape(12.dp))
            .background(Color.Red, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues()
    ) {
        Text(text = text, color = Color.White, fontSize = 18.sp)
    }

}
