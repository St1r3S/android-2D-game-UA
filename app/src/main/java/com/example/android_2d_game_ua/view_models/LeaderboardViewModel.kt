package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.model.leaderboard.LeaderBoardElem
import com.example.android_2d_game_ua.repositories.LeaderboardRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LeaderboardViewModel(private val repository: LeaderboardRepository) : ViewModel() {
    var leaderBoard: MutableLiveData<List<LeaderBoardElem>> = MutableLiveData()
    private val list: MutableList<LeaderBoardElem> = mutableListOf()

    fun getLeaders() {
        repository.getLeaders().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val elem = LeaderBoardElem(
                        postSnapshot.child("username").value.toString(),
                        postSnapshot.child("score").value as Long
                    )
                    list.add(elem)
                }
                list.sortByDescending { it.score }

                leaderBoard.postValue(list)
                //leaderBoard.postValue(list.take(10))
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}
