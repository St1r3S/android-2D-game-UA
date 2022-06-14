package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.repositories.UserRepository

class RegistrationViewModel(private val repository: UserRepository) : ViewModel() {
    var userLogedIn: MutableLiveData<Boolean> = MutableLiveData()
    var loginError: MutableLiveData<String?> = MutableLiveData()

    init {
        userLogedIn = repository.getLogedIn()
        loginError = repository.getLoginError()
    }

    fun createUser(username: String, email: String, password: String) {
        repository.registerUser(username, email, password)
    }
}