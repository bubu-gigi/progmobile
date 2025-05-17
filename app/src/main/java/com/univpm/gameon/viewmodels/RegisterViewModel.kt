package com.univpm.gameon.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.data.collections.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    var registrationState: MutableState<String?> = mutableStateOf(null)

    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(user.email, user.password).await()
                val uid = result.user?.uid

                if (uid != null) {
                    db.collection("users").document(uid).set(user).await()
                    registrationState.value = "SUCCESS"
                } else {
                    registrationState.value = "FAILED: No UID"
                }
            } catch (e: Exception) {
                registrationState.value = "FAILED: ${e.message}"
            }
        }
    }
}
