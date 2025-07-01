package com.univpm.gameon.viewmodels

import com.google.firebase.Timestamp
import com.univpm.gameon.data.collections.Conversazione
import com.univpm.gameon.data.collections.Messaggio
import com.univpm.gameon.repositories.ConversazioneRepository
import com.univpm.gameon.repositories.MessaggioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class ChatViewModelTest {

    private lateinit var viewModel: ChatViewModel

    private val mockMessaggioRepo: MessaggioRepository = mock()
    private val mockConversazioneRepo: ConversazioneRepository = mock()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = ChatViewModel(mockMessaggioRepo, mockConversazioneRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun caricaConversazioneTest() = runTest {
        val giocatoreId = "gioc1"
        val strutturaId = "strutt1"
        val conv = Conversazione(giocatoreId = giocatoreId, strutturaId = strutturaId)
        val messaggi = listOf(
            Messaggio(mittente = "gioc1", testo = "ciao", timestamp = Timestamp.now()),
            Messaggio(mittente = "strutt1", testo = "ciao a te", timestamp = Timestamp.now())
        )

        whenever(mockConversazioneRepo.getConversazione(giocatoreId, strutturaId)).thenReturn(conv)
        whenever(mockMessaggioRepo.getMessaggi(giocatoreId, strutturaId)).thenReturn(messaggi)

        viewModel.caricaConversazione(giocatoreId, strutturaId)
        advanceUntilIdle()

        assertEquals(conv, viewModel.conversazione.value)
        assertEquals(messaggi, viewModel.messaggi.value)
    }

    @Test
    fun inviaMessaggioTest() = runTest {
        val giocatoreId = "gioc1"
        val strutturaId = "strutt1"
        val testo = "testo di prova"
        val mittente = "gioc1"

        val messaggiAggiornati = listOf(
            Messaggio(mittente = "gioc1", testo = testo, timestamp = Timestamp.now())
        )

        whenever(mockMessaggioRepo.sendMessaggio(any(), any(), any())).thenReturn(true)
        whenever(mockConversazioneRepo.getConversazione(giocatoreId, strutturaId)).thenReturn(null)
        whenever(mockMessaggioRepo.getMessaggi(giocatoreId, strutturaId)).thenReturn(messaggiAggiornati)

        viewModel.inviaMessaggio(giocatoreId, strutturaId, testo, mittente)
        advanceUntilIdle()

        assertEquals(messaggiAggiornati, viewModel.messaggi.value)
    }

    @Test
    fun loadConversazioniForUserTest() = runTest {
        val userId = "giocatoreX"
        val conversazioni = listOf(
            Conversazione(giocatoreId = userId, strutturaId = "strutt1"),
            Conversazione(giocatoreId = userId, strutturaId = "strutt2")
        )

        whenever(mockConversazioneRepo.getConversazioniByPlayerId(userId)).thenReturn(conversazioni)

        viewModel.loadConversazioniForUser(userId)
        advanceUntilIdle()

        assertEquals(conversazioni, viewModel.conversazioni.value)
    }

    @Test
    fun caricaTutteLeConversazioniTest() = runTest {
        val conversazioni = listOf(
            Conversazione(giocatoreId = "g1", strutturaId = "s1"),
            Conversazione(giocatoreId = "g2", strutturaId = "s2")
        )

        whenever(mockConversazioneRepo.getConversazioni()).thenReturn(conversazioni)

        viewModel.caricaTutteLeConversazioni()
        advanceUntilIdle()

        assertEquals(conversazioni, viewModel.conversazioni.value)
    }
}
