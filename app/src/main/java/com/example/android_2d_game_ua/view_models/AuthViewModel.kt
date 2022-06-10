package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var check: MutableLiveData<Boolean> = MutableLiveData()

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    check.postValue(true)
                } else {
                    check.postValue(false)
                }
            }

    }

    fun logoutUser() {
        auth.signOut()
    }
}