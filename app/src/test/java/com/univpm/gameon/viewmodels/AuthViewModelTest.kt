package com.univpm.gameon.viewmodels

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.univpm.gameon.core.AdminHomeScreenRoute
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.junit.Before
import org.junit.After
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import junit.framework.TestCase.assertEquals
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    private lateinit var authViewModel: AuthViewModel

    @Mock
    private lateinit var mockUserRepository: UserRepository

    @Mock
    private lateinit var mockFirebaseAuth: FirebaseAuth

    private val fakeTask: Task<AuthResult> = Tasks.forResult(mock())

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(StandardTestDispatcher())
        authViewModel = AuthViewModel(mockUserRepository, mockFirebaseAuth)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loginTest() = runTest {
        val email = "test@example.com"
        val password = "123456"
        val testUser = User(id = "1", email = email, password = password, ruolo = "Admin")

        Mockito.`when`(
            mockFirebaseAuth.signInWithEmailAndPassword(email, password)
        ).thenReturn(fakeTask)

        Mockito.`when`(
            mockUserRepository.getUserByEmail(email)
        ).thenReturn(testUser)

        authViewModel.login(email, password)

        advanceUntilIdle()

        assertEquals("SUCCESS", authViewModel.authState.value)
        assertEquals(testUser, authViewModel.currentUser.value)
        assertEquals(AdminHomeScreenRoute, authViewModel.destination.value)
    }

    @Test
    fun loginFails_showsFailedState() = runTest {
        val email = "test@example.com"
        val password = "wrong-password"
        val errorMessage = "Login fallito"

        val failedTask: Task<AuthResult> = Tasks.forException(Exception(errorMessage))

        Mockito.`when`(
            mockFirebaseAuth.signInWithEmailAndPassword(email, password)
        ).thenReturn(failedTask)

        Mockito.verifyNoInteractions(mockUserRepository)

        authViewModel.login(email, password)

        advanceUntilIdle()

        val result = authViewModel.authState.value
        assert(result?.startsWith("FAILED") == true) {
            "Expected FAILED state but got: $result"
        }
    }
}
