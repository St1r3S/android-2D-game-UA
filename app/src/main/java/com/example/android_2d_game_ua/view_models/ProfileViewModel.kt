package com.example.android_2d_game_ua.view_models

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_2d_game_ua.model.user.User
import com.example.android_2d_game_ua.repositories.UserRepository
import com.example.android_2d_game_ua.view_models.factories.MenuViewModelFactory

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    var user: MutableLiveData<User> = MutableLiveData()
    lateinit var viewModel: MenuViewModel
    fun getUser(fragment: Fragment) {
        viewModel = ViewModelProvider(
            fragment,
            MenuViewModelFactory(repository)
        ).get(MenuViewModel::class.java)
        user = viewModel.user
        viewModel.getUserFromMenu()
    }
}