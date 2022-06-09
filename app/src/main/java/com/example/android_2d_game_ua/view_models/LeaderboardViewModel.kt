package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_2d_game_ua.LeaderBoardElem
import com.example.android_2d_game_ua.repositories.LeaderboardRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class LeaderboardViewModel(private val apiRepository: LeaderboardRepository) : ViewModel() {
    var articles: MutableLiveData<List<LeaderBoardElem>> = MutableLiveData()
    private val list: MutableList<LeaderBoardElem> = mutableListOf()

    fun getLeaders() {
        apiRepository.getLeaders().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val elem = LeaderBoardElem(postSnapshot.child("username").value.toString(),
                        postSnapshot.child("score").value as Long)
                    list.add(elem)
                }
                list.sortByDescending { it.score }
                articles.postValue(list)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        })
    }

}

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