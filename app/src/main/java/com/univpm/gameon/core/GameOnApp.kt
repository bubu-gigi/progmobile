package com.univpm.gameon.core

import android.app.Application
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GameOnApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyBG5dhb3WhErCUEPCNloC4QLQP9x3vw6Mg")
        }
    }
}
