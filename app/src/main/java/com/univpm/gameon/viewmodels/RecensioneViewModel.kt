package com.univpm.gameon.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univpm.gameon.data.collections.Recensione
import com.univpm.gameon.repositories.RecensioneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecensioneViewModel @Inject constructor(
    private val repository: RecensioneRepository
) : ViewModel() {

    val recensioneUtente = mutableStateOf<Recensione?>(null)

    fun inviaRecensione(recensione: Recensione, onComplete: () -> Unit) {
        viewModelScope.launch {
            val success = repository.saveRecensione(recensione)
            if (success) {
                onComplete()
            }
        }
    }

    fun getRecensioneUtente(strutturaId: String, utenteId: String) {
        viewModelScope.launch {
            val recensioni = repository.getRecensioniByStruttura(strutturaId)
            recensioneUtente.value = recensioni.find { it.utenteId == utenteId }
        }
    }
}

