package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.model.user.User
import com.example.android_2d_game_ua.repositories.UserRepository
//import com.example.android_2d_game_ua.view_models.util.RegistrationCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class RegistrationViewModel(private val repository: UserRepository) : ViewModel() {
    var check: MutableLiveData<String> = MutableLiveData()

    fun createUser(username: String, email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    check.postValue(null)
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    firebaseUser.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(username)
                            .build()
                    )
                    val user = User(firebaseUser.uid, username, email, password, 0)
                    repository.createUser(user)

                } else {
                    check.postValue(task.exception!!.message.toString())
                }
            }
    }
}