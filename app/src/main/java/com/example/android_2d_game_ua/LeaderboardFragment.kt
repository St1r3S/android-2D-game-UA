package com.example.android_2d_game_ua

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_leaderboard.view.*

class LeaderboardFragment : Fragment() {

    private val list: MutableList<LeaderBoardElem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)
        val adapter = LeaderboardAdapter(layoutInflater)
        view.list.adapter = adapter
        view.list.layoutManager = LinearLayoutManager(context)
        adapter.submitList(null)

        val database: DatabaseReference =
            Firebase.database("https://android-2d-game-ua-ff466-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val scoreInfo = database.child("Users")
        scoreInfo.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val elem = LeaderBoardElem(postSnapshot.child("username").value.toString(),
                        postSnapshot.child("score").value as Long)
                    list.add(elem)
                }
                list.sortByDescending { it.score }
                adapter.submitList(list.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        })



        return view
    }
}