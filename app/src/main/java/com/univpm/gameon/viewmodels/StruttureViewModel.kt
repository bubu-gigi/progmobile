package com.univpm.gameon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.repositories.StrutturaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StruttureViewModel @Inject constructor(
    private val repository: StrutturaRepository
) : ViewModel() {

    private val _strutture = MutableStateFlow<List<Struttura>>(emptyList())
    val strutture: StateFlow<List<Struttura>> = _strutture.asStateFlow()

    private val _strutturaSelezionata = MutableStateFlow<Struttura?>(null)
    val strutturaSelezionata: StateFlow<Struttura?> = _strutturaSelezionata.asStateFlow()

    private val _errore = MutableStateFlow<String?>(null)
    val errore: StateFlow<String?> = _errore.asStateFlow()

    fun caricaStrutture() {
        viewModelScope.launch {
            try {
                _strutture.value = repository.getStrutture()
            } catch (e: Exception) {
                _errore.value = "Errore nel caricamento strutture: ${e.message}"
            }
        }
    }

    fun caricaStruttura(id: String) {
        viewModelScope.launch {
            try {
                _strutturaSelezionata.value = repository.getStruttura(id)
            } catch (e: Exception) {
                _errore.value = "Errore nel caricamento della struttura: ${e.message}"
            }
        }
    }

    fun salvaStruttura(struttura: Struttura) {
        viewModelScope.launch {
            try {
                val success = repository.saveStruttura(struttura)
                if (success) {
                    caricaStrutture()
                } else {
                    _errore.value = "Impossibile salvare la struttura"
                }
            } catch (e: Exception) {
                _errore.value = "Errore nel salvataggio: ${e.message}"
            }
        }
    }

    fun aggiornaStruttura(id: String, struttura: Struttura) {
        viewModelScope.launch {
            try {
                val success = repository.updateStruttura(id, struttura)
                if (success) {
                    caricaStrutture()
                } else {
                    _errore.value = "Impossibile aggiornare la struttura"
                }
            } catch (e: Exception) {
                _errore.value = "Errore nell’aggiornamento: ${e.message}"
            }
        }
    }

    fun eliminaStruttura(id: String, userId: String) {
        viewModelScope.launch {
            try {
                val success = repository.deleteStruttura(id)
                if (success) {
                    caricaStrutture()
                } else {
                    _errore.value = "Impossibile eliminare la struttura"
                }
            } catch (e: Exception) {
                _errore.value = "Errore nell’eliminazione: ${e.message}"
            }
        }
    }
}
