package com.univpm.gameon.viewmodels

import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.repositories.StrutturaRepository
import com.univpm.gameon.repositories.UserRepository
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
class StruttureViewModelTest {

    private lateinit var viewModel: StruttureViewModel
    private val mockRepository: StrutturaRepository = mock()
    private val mockUserRepository: UserRepository = mock()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = StruttureViewModel(mockRepository, mockUserRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun caricaStruttureTest() = runTest {
        val strutture = listOf(
            Struttura(id = "s1", nome = "A"),
            Struttura(id = "s2", nome = "B")
        )

        whenever(mockRepository.getStrutture()).thenReturn(strutture)

        viewModel.caricaStrutture()
        advanceUntilIdle()

        assertEquals(strutture, viewModel.strutture.value)
        assertNull(viewModel.errore.value)
    }

    @Test
    fun caricaStrutturaTest() = runTest {
        val struttura = Struttura(id = "s1", nome = "Centro Sportivo")
        val campi = listOf(
            Campo(id = "c1", nomeCampo = "Campo 1"),
            Campo(id = "c2", nomeCampo = "Campo 2")
        )

        whenever(mockRepository.getStruttura("s1")).thenReturn(struttura to campi)

        viewModel.caricaStruttura("s1")
        advanceUntilIdle()

        assertEquals(struttura, viewModel.strutturaSelezionata.value)
        assertEquals(campi, viewModel.campiStruttura.value)
        assertNull(viewModel.errore.value)
    }

    @Test
    fun salvaStrutturaTest() = runTest {
        val struttura = Struttura(id = "s3", nome = "New")
        val campi = listOf(Campo(id = "c3", nomeCampo = "Campo New"))

        whenever(mockRepository.saveStruttura(struttura, campi)).thenReturn(true)
        whenever(mockRepository.getStrutture()).thenReturn(listOf(struttura))

        viewModel.salvaStruttura(struttura, campi)
        advanceUntilIdle()

        assertEquals(listOf(struttura), viewModel.strutture.value)
        assertNull(viewModel.errore.value)
    }

    @Test
    fun salvaStrutturaFallimentoTest() = runTest {
        val struttura = Struttura(id = "fail", nome = "Fail")
        val campi = emptyList<Campo>()

        whenever(mockRepository.saveStruttura(struttura, campi)).thenReturn(false)

        viewModel.salvaStruttura(struttura, campi)
        advanceUntilIdle()

        assertEquals("Impossibile salvare la struttura", viewModel.errore.value)
    }

    @Test
    fun aggiornaStrutturaTest() = runTest {
        val struttura = Struttura(id = "s4", nome = "Aggiornata")
        val campi = listOf(Campo(id = "c4", nomeCampo = "Campo Aggiornato"))

        whenever(mockRepository.updateStruttura("s4", struttura, campi)).thenReturn(true)
        whenever(mockRepository.getStrutture()).thenReturn(listOf(struttura))

        viewModel.aggiornaStruttura("s4", struttura, campi)
        advanceUntilIdle()

        assertEquals(listOf(struttura), viewModel.strutture.value)
        assertNull(viewModel.errore.value)
    }

    @Test
    fun aggiornaStrutturaFallimentoTest() = runTest {
        val struttura = Struttura(id = "fail", nome = "Nope")
        val campi = emptyList<Campo>()

        whenever(mockRepository.updateStruttura("fail", struttura, campi)).thenReturn(false)

        viewModel.aggiornaStruttura("fail", struttura, campi)
        advanceUntilIdle()

        assertEquals("Impossibile aggiornare la struttura", viewModel.errore.value)
    }

    @Test
    fun eliminaStrutturaTest() = runTest {
        val struttura = Struttura(id = "del", nome = "Da Eliminare")

        whenever(mockRepository.deleteStruttura("del")).thenReturn(true)
        whenever(mockRepository.getStrutture()).thenReturn(emptyList())

        viewModel.eliminaStruttura("del")
        advanceUntilIdle()

        assertEquals(emptyList<Struttura>(), viewModel.strutture.value)
        assertNull(viewModel.errore.value)
    }

    @Test
    fun eliminaStrutturaFallimentoTest() = runTest {
        whenever(mockRepository.deleteStruttura("fail")).thenReturn(false)

        viewModel.eliminaStruttura("fail")
        advanceUntilIdle()

        assertEquals("Impossibile eliminare la struttura", viewModel.errore.value)
    }
}
