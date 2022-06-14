package com.example.android_2d_game_ua.repositories

import androidx.lifecycle.MutableLiveData
import com.example.android_2d_game_ua.model.leaderboard.LeaderBoardElem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LeaderboardRepository {
    private var leaderBoard: MutableLiveData<List<LeaderBoardElem>> = MutableLiveData()
    private val list: MutableList<LeaderBoardElem> = mutableListOf()
    private val database: DatabaseReference =
        Firebase.database("https://android-2d-game-ua-ff466-default-rtdb.europe-west1.firebasedatabase.app/").reference

    fun getLeaderBoard(): MutableLiveData<List<LeaderBoardElem>> {
        return leaderBoard
    }

    fun getLeaders(): ValueEventListener {
        return database.child("Users").addValueEventListener(object : ValueEventListener {
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
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}