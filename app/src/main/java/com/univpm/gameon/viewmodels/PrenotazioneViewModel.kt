package com.univpm.gameon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.repositories.PrenotazioneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrenotazioneViewModel @Inject constructor(
    private val repository: PrenotazioneRepository
) : ViewModel() {

    private val _prenotazioniUtente = MutableStateFlow<List<Prenotazione>>(emptyList())
    val prenotazioniUtente: StateFlow<List<Prenotazione>> = _prenotazioniUtente

    private val _prenotazioniStruttura = MutableStateFlow<List<Prenotazione>>(emptyList())
    val prenotazioniStruttura: StateFlow<List<Prenotazione>> = _prenotazioniStruttura

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun caricaPrenotazioniUtente(userId: String) {
        _error.value = null
        viewModelScope.launch {
            try {
                _prenotazioniUtente.value = repository.getPrenotazioniByUser(userId)
            } catch (e: Exception) {
                _error.value = "Errore durante il caricamento: ${e.message}"
            }
        }
    }

    fun caricaPrenotazioniStruttura(strutturaId: String) {
        _error.value = null
        viewModelScope.launch {
            try {
                _prenotazioniStruttura.value = repository.getPrenotazioniByStruttura(strutturaId)
            } catch (e: Exception) {
                _error.value = "Errore durante il caricamento: ${e.message}"
            }
        }
    }

    fun creaPrenotazione(prenotazione: Prenotazione) {
        _error.value = null
        viewModelScope.launch {
            try {
                val success = repository.savePrenotazione(prenotazione)
                if (success) {
                    caricaPrenotazioniUtente(prenotazione.userId)
                } else {
                    _error.value = "Impossibile creare la prenotazione"
                }
            } catch (e: Exception) {
                _error.value = "Errore durante la creazione: ${e.message}"
            }
        }
    }

    fun aggiornaPrenotazione(id: String, prenotazione: Prenotazione) {
        _error.value = null
        viewModelScope.launch {
            try {
                val success = repository.updatePrenotazione(id, prenotazione)
                if (success) {
                    caricaPrenotazioniUtente(prenotazione.userId)
                } else {
                    _error.value = "Aggiornamento non riuscito"
                }
            } catch (e: Exception) {
                _error.value = "Errore durante l'aggiornamento: ${e.message}"
            }
        }
    }

    fun annullaPrenotazione(id: String, onSuccess: () -> Unit) {
        _error.value = null
        viewModelScope.launch {
            val success = repository.deletePrenotazione(id)
            if (success) {
                _prenotazioniUtente.value = _prenotazioniUtente.value.filterNot { it.id == id }
                onSuccess()
            } else {
                _error.value = "Impossibile annullare la prenotazione"
            }
        }
    }
}
