package com.univpm.gameon.ui.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.univpm.gameon.R

@Composable
fun BackgroundScaffold2(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.sfondogrigio),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        content()
    }
}
