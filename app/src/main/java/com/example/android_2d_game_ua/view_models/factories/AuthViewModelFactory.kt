package com.example.android_2d_game_ua.view_models.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_2d_game_ua.view_models.AuthViewModel

class AuthViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}