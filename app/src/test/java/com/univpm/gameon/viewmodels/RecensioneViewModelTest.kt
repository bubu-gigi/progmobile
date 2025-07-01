package com.univpm.gameon.viewmodels

import com.univpm.gameon.data.collections.Recensione
import com.univpm.gameon.repositories.RecensioneRepository
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
class RecensioneViewModelTest {

    private lateinit var viewModel: RecensioneViewModel
    private val mockRepository: RecensioneRepository = mock()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = RecensioneViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun inviaRecensioneSuccessChiamaOnComplete() = runTest {
        val recensione = Recensione(
            id = "r1",
            strutturaId = "s1",
            utenteId = "u1",
            rating = 4,
            commento = "Ottima!"
        )
        whenever(mockRepository.saveRecensione(recensione)).thenReturn(true)

        var chiamato = false
        viewModel.inviaRecensione(recensione) {
            chiamato = true
        }

        advanceUntilIdle()

        assertEquals(true, chiamato)
    }

    @Test
    fun inviaRecensioneFallitaNonChiamaOnComplete() = runTest {
        val recensione = Recensione(
            id = "r2",
            strutturaId = "s2",
            utenteId = "u2",
            rating = 2,
            commento = "Così così"
        )
        whenever(mockRepository.saveRecensione(recensione)).thenReturn(false)

        var chiamato = false
        viewModel.inviaRecensione(recensione) {
            chiamato = true
        }

        advanceUntilIdle()

        assertEquals(false, chiamato)
    }

    @Test
    fun getRecensioneUtenteAssegnaRecensioneCorretta() = runTest {
        val strutturaId = "s1"
        val utenteId = "u1"

        val recensioni = listOf(
            Recensione(id = "r1", strutturaId = strutturaId, utenteId = "altri", rating = 5, commento = ""),
            Recensione(id = "r2", strutturaId = strutturaId, utenteId = utenteId, rating = 3, commento = "ok")
        )

        whenever(mockRepository.getRecensioniByStruttura(strutturaId)).thenReturn(recensioni)

        viewModel.getRecensioneUtente(strutturaId, utenteId)
        advanceUntilIdle()

        assertEquals(recensioni[1], viewModel.recensioneUtente.value)
    }

    @Test
    fun getRecensioneUtenteNonTrovataImpostaNull() = runTest {
        val strutturaId = "s1"
        val utenteId = "non_esistente"

        val recensioni = listOf(
            Recensione(id = "r1", strutturaId = strutturaId, utenteId = "altro", rating = 5, commento = "")
        )

        whenever(mockRepository.getRecensioniByStruttura(strutturaId)).thenReturn(recensioni)

        viewModel.getRecensioneUtente(strutturaId, utenteId)
        advanceUntilIdle()

        assertNull(viewModel.recensioneUtente.value)
    }
}
