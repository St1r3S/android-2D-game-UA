package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.repositories.UserRepository

class MenuViewModel(private val repository: UserRepository) : ViewModel() {

    fun logoutUser() {
        repository.logOut()
    }

}