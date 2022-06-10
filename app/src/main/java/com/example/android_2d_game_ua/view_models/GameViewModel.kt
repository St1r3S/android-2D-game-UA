package com.example.android_2d_game_ua.view_models

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_2d_game_ua.model.leaderboard.LeaderBoardElem
import com.example.android_2d_game_ua.model.user.User
import com.example.android_2d_game_ua.repositories.UserRepository
import com.example.android_2d_game_ua.view_models.factories.MenuViewModelFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class GameViewModel(private val repository: UserRepository) : ViewModel() {

    var user: MutableLiveData<User> = MutableLiveData()
    private lateinit var viewModel: MenuViewModel

    fun getUser(fragment: Fragment) {
        viewModel = ViewModelProvider(
            fragment,
            MenuViewModelFactory(repository)
        ).get(MenuViewModel::class.java)
        user = viewModel.user
        viewModel.getUserFromMenu()
    }

    fun setScore(uid: String, score: Long) {
        repository.setScore(uid, score)
    }
}