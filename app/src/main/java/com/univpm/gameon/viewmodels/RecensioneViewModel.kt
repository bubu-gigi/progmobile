package com.univpm.gameon.viewmodels

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

    fun inviaRecensione(recensione: Recensione) {
        viewModelScope.launch {
            repository.saveRecensione(recensione)
        }
    }

    fun haGiaRecensito(
        strutturaId: String,
        utenteId: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val recensioni = repository.getRecensioniByStruttura(strutturaId)
            val esiste = recensioni.any { it.utenteId == utenteId }
            onResult(esiste)
        }
    }
}

