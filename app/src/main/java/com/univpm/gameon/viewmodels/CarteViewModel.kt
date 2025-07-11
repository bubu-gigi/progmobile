package com.univpm.gameon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.repositories.CartaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarteViewModel @Inject constructor(
    private val repository: CartaRepository
) : ViewModel() {

    private val _carte = MutableStateFlow<List<Carta>>(emptyList())
    val carte: StateFlow<List<Carta>> = _carte.asStateFlow()

    private val _errore = MutableStateFlow<String?>(null)
    val errore: StateFlow<String?> = _errore.asStateFlow()

    private val _cartaSelezionataId = MutableStateFlow<String?>(null)
    val cartaSelezionataId: StateFlow<String?> = _cartaSelezionataId

    fun caricaCartePerUtente(userId: String) {
        viewModelScope.launch {
            try {
                _carte.value = repository.getCarteByUserId(userId)
            } catch (e: Exception) {
                _errore.value = "Errore nel caricamento carte: ${e.message}"
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

    fun selezionaCarta(id: String) {
        viewModelScope.launch {
            try {
                val carteAttuali = _carte.value
                val cartaDaSelezionare = carteAttuali.find { it.id == id } ?: return@launch
                carteAttuali.forEach { carta ->
                    if (carta.default && carta.id != id) {
                        repository.updateCarta(carta.id, carta.copy(default = false))
                    }
                }
                repository.updateCarta(cartaDaSelezionare.id, cartaDaSelezionare.copy(default = true))
                _cartaSelezionataId.value = id
                caricaCartePerUtente(cartaDaSelezionare.userId)
            } catch (e: Exception) {
                _errore.value = "Errore durante la selezione della carta: ${e.message}"
            }
        }
    }
}
