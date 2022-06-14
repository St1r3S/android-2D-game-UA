package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.model.user.User
import com.example.android_2d_game_ua.repositories.UserRepository

class GameViewModel(private val repository: UserRepository) : ViewModel() {

    var user: MutableLiveData<User> = MutableLiveData()

    init {
        user = repository.getUserLiveData()
    }

    fun getUserData() {
        repository.getUserByUserId()
    }

    fun setScore(uid: String, score: Long) {
        repository.setScore(uid, score)
    }
}