package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.repositories.UserRepository

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    var userLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    var loginError: MutableLiveData<String?> = MutableLiveData()

    init{
        userLoggedIn = repository.getLoggedIn()
        loginError = repository.getLoginError()
    }

    fun loginUser(email: String, password: String){
        repository.loginUser(email, password)
    }
}