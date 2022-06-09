package com.example.android_2d_game_ua.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_2d_game_ua.model.user.User
import com.example.android_2d_game_ua.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MenuViewModel(private val repository: UserRepository) : ViewModel() {
    var user: MutableLiveData<User> = MutableLiveData()
    private var auth: FirebaseAuth = Firebase.auth

    fun getUserFromMenu() {
        auth.currentUser?.let {
            repository.getUserByUserId(it.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val dummyUser = User(
                        dataSnapshot.child("userId").value.toString(),
                        dataSnapshot.child("username").value.toString(),
                        dataSnapshot.child("email").value.toString(),
                        dataSnapshot.child("password").value.toString(),
                        dataSnapshot.child("score").value as? Long
                    )
                    user.postValue(dummyUser)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }
}