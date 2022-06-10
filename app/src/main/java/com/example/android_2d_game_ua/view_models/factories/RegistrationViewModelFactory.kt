package com.example.android_2d_game_ua.view_models.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_2d_game_ua.repositories.UserRepository
import com.example.android_2d_game_ua.view_models.RegistrationViewModel

class RegistrationViewModelFactory (private val repository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            RegistrationViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}