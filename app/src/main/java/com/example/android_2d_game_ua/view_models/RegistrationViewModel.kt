package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.model.user.User
import com.example.android_2d_game_ua.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class RegistrationViewModel(private val repository: UserRepository) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var check: MutableLiveData<Boolean> = MutableLiveData()

    fun createUser(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    firebaseUser.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(username)
                            .build()
                    )
                    val user = User(firebaseUser.uid, username, email, password, 0)
                    repository.createUser(user)
                    check.postValue(true)
                } else {
                    check.postValue(false)
                }
            }

    }
}