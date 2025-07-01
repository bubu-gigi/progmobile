package com.univpm.gameon.viewmodels

import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.repositories.PrenotazioneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull

@ExperimentalCoroutinesApi
class PrenotazioneViewModelTest {

    private lateinit var viewModel: PrenotazioneViewModel
    private val mockRepository: PrenotazioneRepository = mock()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = PrenotazioneViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun caricaPrenotazioniUtenteTest() = runTest {
        val userId = "user1"
        val prenotazioni = listOf(
            Prenotazione(id = "p1", userId = userId),
            Prenotazione(id = "p2", userId = userId)
        )

        whenever(mockRepository.getPrenotazioniByUser(userId)).thenReturn(prenotazioni)

        viewModel.caricaPrenotazioniUtente(userId)
        advanceUntilIdle()

        assertEquals(prenotazioni, viewModel.prenotazioniUtente.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun caricaTuttePrenotazioniTest() = runTest {
        val prenotazioni = listOf(
            Prenotazione(id = "p1", userId = "u1"),
            Prenotazione(id = "p2", userId = "u2")
        )

        whenever(mockRepository.getPrenotazioni()).thenReturn(prenotazioni)

        viewModel.caricaTuttePrenotazioni()
        advanceUntilIdle()

        assertEquals(prenotazioni, viewModel.prenotazioni.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun creaPrenotazioneTest() = runTest {
        val prenotazione = Prenotazione(id = "p1", userId = "userX")

        whenever(mockRepository.savePrenotazione(prenotazione)).thenReturn(true)
        whenever(mockRepository.getPrenotazioniByUser("userX")).thenReturn(listOf(prenotazione))

        viewModel.creaPrenotazione(prenotazione)
        advanceUntilIdle()

        assertEquals(listOf(prenotazione), viewModel.prenotazioniUtente.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun aggiornaPrenotazioneTest() = runTest {
        val prenotazione = Prenotazione(id = "p2", userId = "userY")

        whenever(mockRepository.updatePrenotazione("p2", prenotazione)).thenReturn(true)
        whenever(mockRepository.getPrenotazioniByUser("userY")).thenReturn(listOf(prenotazione))

        viewModel.aggiornaPrenotazione("p2", prenotazione)
        advanceUntilIdle()

        assertEquals(listOf(prenotazione), viewModel.prenotazioniUtente.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun annullaPrenotazioneDaAdminTest() = runTest {
        val prenotazioni = listOf(Prenotazione(id = "p3", userId = "u3"))

        UserSessionManager.userRole = "Admin"
        whenever(mockRepository.deletePrenotazione("p3")).thenReturn(true)
        whenever(mockRepository.getPrenotazioni()).thenReturn(prenotazioni)

        viewModel.annullaPrenotazione("p3")
        advanceUntilIdle()

        assertEquals(prenotazioni, viewModel.prenotazioni.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun annullaPrenotazioneDaGiocatoreTest() = runTest {
        val userId = "utente"
        val prenotazioni = listOf(Prenotazione(id = "p4", userId = userId))

        UserSessionManager.userRole = "Giocatore"
        UserSessionManager.userId = userId
        whenever(mockRepository.deletePrenotazione("p4")).thenReturn(true)
        whenever(mockRepository.getPrenotazioniByUser(userId)).thenReturn(prenotazioni)

        viewModel.annullaPrenotazione("p4")
        advanceUntilIdle()

        assertEquals(prenotazioni, viewModel.prenotazioniUtente.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun creaPrenotazioneErroreTest() = runTest {
        val prenotazione = Prenotazione(id = "pX", userId = "userZ")

        whenever(mockRepository.savePrenotazione(prenotazione)).thenReturn(false)

        viewModel.creaPrenotazione(prenotazione)
        advanceUntilIdle()

        assertEquals("Impossibile creare la prenotazione", viewModel.error.value)
    }

    @Test
    fun aggiornaPrenotazioneErroreTest() = runTest {
        val prenotazione = Prenotazione(id = "pX", userId = "userZ")

        whenever(mockRepository.updatePrenotazione("pX", prenotazione)).thenReturn(false)

        viewModel.aggiornaPrenotazione("pX", prenotazione)
        advanceUntilIdle()

        assertEquals("Aggiornamento non riuscito", viewModel.error.value)
    }

    @Test
    fun annullaPrenotazioneErroreTest() = runTest {
        whenever(mockRepository.deletePrenotazione("p5")).thenReturn(false)

        viewModel.annullaPrenotazione("p5")
        advanceUntilIdle()

        assertEquals("Impossibile annullare la prenotazione", viewModel.error.value)
    }
}
