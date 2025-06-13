package com.univpm.gameon.ui.components

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.univpm.gameon.ui.PlacePickerActivity
import com.univpm.gameon.ui.futuraBookFontFamily


@Composable
fun GooglePlacesAutocomplete(onPlaceSelected: (String, LatLng) -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                val address = data.getStringExtra("address") ?: ""
                val lat = data.getDoubleExtra("lat", 0.0)
                val lng = data.getDoubleExtra("lng", 0.0)
                onPlaceSelected(address, LatLng(lat, lng))
            }
        }
    }

    OutlinedButton(
        onClick = {
            val intent = Intent(context, PlacePickerActivity::class.java)
            launcher.launch(intent)
        },
        modifier = modifier
            .border(BorderStroke(2.dp, Color(0xFFCFFF5E)), shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFFCFFF5E),
            containerColor = Color(0xFF6136FF)
        )
    ) {
        Text(
            text = "Cerca indirizzo",
            fontSize = 18.sp,
            fontFamily = futuraBookFontFamily,
            fontWeight = FontWeight.Bold
        )
    }
}