package com.univpm.gameon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.univpm.gameon.core.AppNavHost
import com.univpm.gameon.ui.BookingDetailScreen
import com.univpm.gameon.ui.BookingHomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavHost()
        }
    }
}


@Preview (showBackground = true, name = "Detail Screen Preview")
@Composable
fun PreviewBookingDetailScreen() {
    BookingDetailScreen(bookingId = "booking_123")
}

@Preview(showBackground = true, name = "Home Screen Preview")
@Composable
fun PreviewBookingHomeScreen() {
    BookingHomeScreen (onBookingClick = {})
}