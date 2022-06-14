package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.model.leaderboard.LeaderBoardElem
import com.example.android_2d_game_ua.repositories.LeaderboardRepository

class LeaderboardViewModel(private val repository: LeaderboardRepository) : ViewModel() {
    var leaderBoard: MutableLiveData<List<LeaderBoardElem>> = MutableLiveData()

    init {
        leaderBoard = repository.getLeaderBoard()
    }

    fun getLeaders() {
        repository.getLeaders()
    }

}
