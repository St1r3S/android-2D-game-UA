package com.example.android_2d_game_ua.view_models.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_2d_game_ua.repositories.LeaderboardRepository
import com.example.android_2d_game_ua.view_models.LeaderboardViewModel


class LeaderboardViewModelFactory(private val repository: LeaderboardRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            LeaderboardViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}