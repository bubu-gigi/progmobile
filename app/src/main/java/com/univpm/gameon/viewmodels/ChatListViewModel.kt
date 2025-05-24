package com.univpm.gameon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univpm.gameon.data.collections.Conversazione
import com.univpm.gameon.repositories.ConversazioneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val conversazioneRepository: ConversazioneRepository
) : ViewModel() {

    private val _conversazioni = MutableStateFlow<List<Conversazione>>(emptyList())
    val conversazioni: StateFlow<List<Conversazione>> = _conversazioni

    fun loadConversazioniForUser(userId: String) {
        viewModelScope.launch {
            _conversazioni.value = conversazioneRepository.getConversazioniByGiocatoreId(userId)
        }
    }

    fun caricaTutteLeConversazioni() {
        viewModelScope.launch {
            _conversazioni.value = conversazioneRepository.getConversazioni()
        }
    }

}
