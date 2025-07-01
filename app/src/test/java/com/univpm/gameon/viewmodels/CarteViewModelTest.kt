package com.univpm.gameon.viewmodels

import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.repositories.CartaRepository
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
class CarteViewModelTest {

    private lateinit var viewModel: CarteViewModel
    private val mockRepository: CartaRepository = mock()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = CarteViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun caricaCarteTest() = runTest {
        val userId = "user123"
        val carteFittizie = listOf(
            Carta(id = "1", userId = userId),
            Carta(id = "2", userId = userId)
        )
        whenever(mockRepository.getCarteByUserId(userId)).thenReturn(carteFittizie)

        viewModel.caricaCartePerUtente(userId)
        advanceUntilIdle()

        assertEquals(carteFittizie, viewModel.carte.value)
        assertNull(viewModel.errore.value)
    }

    @Test
    fun salvaCartaTest() = runTest {
        val carta = Carta(id = "3", userId = "user456")
        whenever(mockRepository.saveCarta(carta)).thenReturn(true)
        whenever(mockRepository.getCarteByUserId("user456")).thenReturn(listOf(carta))

        viewModel.salvaCarta(carta)
        advanceUntilIdle()

        assertEquals(listOf(carta), viewModel.carte.value)
        assertNull(viewModel.errore.value)
    }

    @Test
    fun salvaCartaErrorTest() = runTest {
        val carta = Carta(id = "3", userId = "user456")
        whenever(mockRepository.saveCarta(carta)).thenReturn(false)

        viewModel.salvaCarta(carta)
        advanceUntilIdle()

        assertEquals("Impossibile salvare la carta", viewModel.errore.value)
    }

    @Test
    fun eliminaCartaTest() = runTest {
        whenever(mockRepository.deleteCarta("4")).thenReturn(true)
        whenever(mockRepository.getCarteByUserId("user789")).thenReturn(emptyList())

        viewModel.eliminaCarta("4", "user789")
        advanceUntilIdle()

        assertEquals(emptyList<Carta>(), viewModel.carte.value)
        assertNull(viewModel.errore.value)
    }

    @Test
    fun selezionaCartaTest() = runTest {
        val userId = "user000"
        val carte = listOf(
            Carta(id = "a", userId = userId, default = true),
            Carta(id = "b", userId = userId, default = false)
        )
        whenever(mockRepository.getCarteByUserId(userId)).thenReturn(carte)

        viewModel.caricaCartePerUtente(userId)
        advanceUntilIdle()

        whenever(mockRepository.updateCarta("a", carte[0].copy(default = false))).thenReturn(true)
        whenever(mockRepository.updateCarta("b", carte[1].copy(default = true))).thenReturn(true)

        whenever(mockRepository.getCarteByUserId(userId)).thenReturn(
            listOf(
                carte[0].copy(default = false),
                carte[1].copy(default = true)
            )
        )

        viewModel.selezionaCarta("b")
        advanceUntilIdle()

        assertEquals("b", viewModel.cartaSelezionataId.value)
        assertEquals(
            listOf(
                carte[0].copy(default = false),
                carte[1].copy(default = true)
            ),
            viewModel.carte.value
        )
    }
}
