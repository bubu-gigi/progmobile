package com.univpm.gameon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.repositories.StrutturaRepository
import com.univpm.gameon.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StruttureViewModel @Inject constructor(
    private val repository: StrutturaRepository,
    private val userRepository: UserRepository,
) : ViewModel() {


    private val _strutture = MutableStateFlow<List<Struttura>>(emptyList())
    val strutture: StateFlow<List<Struttura>> = _strutture.asStateFlow()

    private val _strutturaSelezionata = MutableStateFlow<Struttura?>(null)
    val strutturaSelezionata: StateFlow<Struttura?> = _strutturaSelezionata.asStateFlow()

    private val _campiStruttura = MutableStateFlow<List<Campo>>(emptyList())
    val campiStruttura: StateFlow<List<Campo>> = _campiStruttura.asStateFlow()

    private val _errore = MutableStateFlow<String?>(null)
    val errore: StateFlow<String?> = _errore.asStateFlow()

    fun caricaStrutture() {
        _errore.value = null
        viewModelScope.launch {
            try {
                _strutture.value = repository.getStrutture()
            } catch (e: Exception) {
                _errore.value = "Errore nel caricamento strutture: ${e.message}"
            }
        }
    }

    fun caricaStruttura(id: String) {
        _errore.value = null
        viewModelScope.launch {
            try {
                val (struttura, campi) = repository.getStruttura(id)
                _strutturaSelezionata.value = struttura
                _campiStruttura.value = campi
            } catch (e: Exception) {
                _errore.value = "Errore nel caricamento della struttura: ${e.message}"
            }
        }
    }

    fun salvaStruttura(struttura: Struttura, campi: List<Campo>) {
        _errore.value = null
        viewModelScope.launch {
            try {
                val success = repository.saveStruttura(struttura, campi)
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

    fun aggiornaStruttura(id: String, struttura: Struttura, campi: List<Campo>) {
        _errore.value = null
        viewModelScope.launch {
            try {
                val success = repository.updateStruttura(id, struttura, campi)
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

    fun eliminaStruttura(id: String) {
        _errore.value = null
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

    fun modificaPreferiti(strutturaId: String) {
        viewModelScope.launch {
            val user = UserSessionManager.getCurrentUser() ?: return@launch
            val nuoviPreferiti = if (strutturaId in user.preferiti) {
                user.preferiti - strutturaId
            } else {
                user.preferiti + strutturaId
            }

            val utenteAggiornato = user.copy(preferiti = nuoviPreferiti)
            val successo = userRepository.updateUser(user.id, utenteAggiornato)

            if (successo) {
                UserSessionManager.setCurrentUser(utenteAggiornato)
            } else {
                _errore.value = "Errore aggiornamento preferiti"
            }
        }
    }

}
