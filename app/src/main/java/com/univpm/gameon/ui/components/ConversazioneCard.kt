package com.univpm.gameon.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConversazioneCard(
    strutturaNome: String,
    giocatoreNome: String? = null,
    ultimoMessaggio: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .border(BorderStroke(3.dp, Color(0xFFE36BE0)), shape = RoundedCornerShape(12.dp))
            .clickable{ onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD3D3D3))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                CustomText(
                    text = "Struttura: $strutturaNome",
                    color = Color.Black,
                    fontSize = 16.sp
                )
                if (giocatoreNome != null) {
                    CustomText(
                        text = "Giocatore: $giocatoreNome",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
                ultimoMessaggio.let { it ->
                    CustomText(
                        text = "Ultimo messaggio: $ultimoMessaggio",
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
