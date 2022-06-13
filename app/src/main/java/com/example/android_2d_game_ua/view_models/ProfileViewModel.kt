package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.model.user.User

class ProfileViewModel() : ViewModel() {

    var user: MutableLiveData<User> = MutableLiveData()

    fun getUser(viewModel: MenuViewModel) {
        user = viewModel.user
        viewModel.getUserFromMenu()
    }
}