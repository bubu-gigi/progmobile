package com.univpm.gameon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.repositories.CartaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarteViewModel @Inject constructor(
    private val repository: CartaRepository
) : ViewModel() {

    private val _carte = MutableStateFlow<List<Carta>>(emptyList())
    val carte: StateFlow<List<Carta>> = _carte.asStateFlow()

    private val _cartaSelezionata = MutableStateFlow<Carta?>(null)
    val cartaSelezionata: StateFlow<Carta?> = _cartaSelezionata.asStateFlow()

    private val _errore = MutableStateFlow<String?>(null)
    val errore: StateFlow<String?> = _errore.asStateFlow()

    fun caricaCartePerUtente(userId: String) {
        viewModelScope.launch {
            try {
                _carte.value = repository.getCarteByUserId(userId)
            } catch (e: Exception) {
                _errore.value = "Errore nel caricamento carte: ${e.message}"
            }
        }
    }

    fun caricaCarta(id: String) {
        viewModelScope.launch {
            try {
                _cartaSelezionata.value = repository.getCarta(id)
            } catch (e: Exception) {
                _errore.value = "Errore nel caricamento della carta: ${e.message}"
            }
        }
    }

    fun salvaCarta(carta: Carta) {
        viewModelScope.launch {
            try {
                val success = repository.saveCarta(carta)
                if (success) {
                    caricaCartePerUtente(carta.userId)
                } else {
                    _errore.value = "Impossibile salvare la carta"
                }
            } catch (e: Exception) {
                _errore.value = "Errore nel salvataggio: ${e.message}"
            }
        }
    }

    fun aggiornaCarta(id: String, carta: Carta) {
        viewModelScope.launch {
            try {
                val success = repository.updateCarta(id, carta)
                if (success) {
                    caricaCartePerUtente(carta.userId)
                } else {
                    _errore.value = "Impossibile aggiornare la carta"
                }
            } catch (e: Exception) {
                _errore.value = "Errore nell’aggiornamento: ${e.message}"
            }
        }
    }

    fun eliminaCarta(id: String, userId: String) {
        viewModelScope.launch {
            try {
                val success = repository.deleteCarta(id)
                if (success) {
                    caricaCartePerUtente(userId)
                } else {
                    _errore.value = "Impossibile eliminare la carta"
                }
            } catch (e: Exception) {
                _errore.value = "Errore nell’eliminazione: ${e.message}"
            }
        }
    }
}
