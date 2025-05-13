package com.univpm.gameon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BookingDetailScreen(bookingId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Dettaglio Prenotazione", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text("ID: $bookingId", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true, name = "Detail Screen Preview")
@Composable
fun PreviewBookingDetailScreen() {
    BookingDetailScreen(bookingId = "booking_123")
}