package com.univpm.gameon.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class PlacePickerActivity : AppCompatActivity() {

    private val AUTOCOMPLETE_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        avviaAutocomplete()
    }

    private fun avviaAutocomplete() {
        val fields = listOf(
            Place.Field.ID,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )

        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    val address = place.address ?: ""
                    val latLng = place.latLng

                    val result = Intent().apply {
                        putExtra("address", address)
                        putExtra("lat", latLng?.latitude)
                        putExtra("lng", latLng?.longitude)
                    }

                    setResult(Activity.RESULT_OK, result)
                    finish()
                }

                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Log.e("PlacePicker", "Errore: ${status.statusMessage}")
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }

                else -> {
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
            }
        }
    }
}
