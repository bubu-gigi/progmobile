package com.univpm.gameon.ui.auth

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.univpm.gameon.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun loginScreen_displaysEmailPasswordAndLoginButton() {
        composeRule.onNodeWithText("Email").assertIsDisplayed()
        composeRule.onNodeWithText("Password").assertIsDisplayed()
        composeRule.onNodeWithText("Login").assertIsDisplayed()
    }

    @Test
    fun loginScreen_showsValidationErrorsIfFieldsEmpty() {
        composeRule.onNodeWithText("Login").performClick()
        composeRule.onNodeWithText("Email non valida").assertIsDisplayed()
        composeRule.onNodeWithText("Password troppo corta").assertIsDisplayed()
    }

    @Test
    fun loginScreen_navigatesToRegister() {
        composeRule.onNodeWithText("Non hai un account? Registrati").performClick()
    }

    @Test
    fun loginScreen_validInput_performsLogin() {
        composeRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeRule.onNodeWithText("Password").performTextInput("123456")
        composeRule.onNodeWithText("Login").performClick()
        composeRule.onAllNodesWithText("Email non valida").assertCountEquals(0)
        composeRule.onAllNodesWithText("Password troppo corta").assertCountEquals(0)
    }
}
