package com.univpm.gameon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univpm.gameon.core.UserSessionManager
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

    private val _prenotazioni= MutableStateFlow<List<Prenotazione>>(emptyList())
    val prenotazioni: StateFlow<List<Prenotazione>> = _prenotazioni

    private val _prenotazione = MutableStateFlow<Prenotazione?>(null)
    val prenotazione: StateFlow<Prenotazione?> = _prenotazione

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

    fun caricaTuttePrenotazioni() {
        _error.value = null
        viewModelScope.launch {
            try {
                _prenotazioni.value = repository.getPrenotazioni()
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

    fun annullaPrenotazione(id: String) {
        _error.value = null
        viewModelScope.launch {
            val success = repository.deletePrenotazione(id)
            if (!success) {
                _error.value = "Impossibile annullare la prenotazione"
            } else {
                if(UserSessionManager.userRole == "Admin") {
                    caricaTuttePrenotazioni()
                } else {
                    UserSessionManager.userId?.let { caricaPrenotazioniUtente(it) }
                }
            }
        }
    }

    fun caricaPrenotazione(id: String) {
        _error.value = null
        viewModelScope.launch {
            try {
                _prenotazione.value = repository.getPrenotazione(id)
            } catch (e: Exception) {
                _error.value = "Errore durante il caricamento: ${e.message}"
            }
        }
    }
}
