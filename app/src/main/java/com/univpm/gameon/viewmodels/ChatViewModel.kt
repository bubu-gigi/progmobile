package com.univpm.gameon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.univpm.gameon.data.collections.Conversazione
import com.univpm.gameon.data.collections.Messaggio
import com.univpm.gameon.repositories.ConversazioneRepository
import com.univpm.gameon.repositories.MessaggioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messaggioRepository: MessaggioRepository,
    private val conversazioneRepository: ConversazioneRepository
) : ViewModel() {

    private val _messaggi = MutableStateFlow<List<Messaggio>>(emptyList())
    val messaggi: StateFlow<List<Messaggio>> = _messaggi

    private val _conversazione = MutableStateFlow<Conversazione?>(null)
    val conversazione: StateFlow<Conversazione?> = _conversazione

    fun caricaConversazione(giocatoreId: String, strutturaId: String) {
        viewModelScope.launch {
            _conversazione.value = conversazioneRepository.getConversazione(giocatoreId, strutturaId)
            _messaggi.value = messaggioRepository.getMessaggi(giocatoreId, strutturaId)
        }
    }

    fun inviaMessaggio(giocatoreId: String, strutturaId: String, testo: String, mittente: String) {
        viewModelScope.launch {
            val messaggio = Messaggio(mittente = mittente, testo = testo, timestamp = Timestamp.now())
            if (messaggioRepository.sendMessaggio(giocatoreId, strutturaId, messaggio)) {
                caricaConversazione(giocatoreId, strutturaId) // aggiorna messaggi
            }
        }
    }
}
