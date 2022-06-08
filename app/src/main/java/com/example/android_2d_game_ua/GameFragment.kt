package com.example.android_2d_game_ua

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val currentUser = auth.currentUser
        var userScore: Long = 0L
        if (currentUser == null) {
            Navigation.findNavController(view)
                .navigate(R.id.action_gameFragment_to_loginFragment)
        } else {
            var defaultScore = 0
            view.tv_current_score.text = defaultScore.toString()
            val database: DatabaseReference =
                Firebase.database("https://android-2d-game-ua-ff466-default-rtdb.europe-west1.firebasedatabase.app/").reference
            val scoreInfo = currentUser.let { database.child("Users").child(it.uid).child("score") }
            scoreInfo.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userScore = snapshot.value as Long
                    view.tv_best_score.text = "best score: $userScore"
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            view.findViewById<Button>(R.id.tv_btn_increment).setOnClickListener {
                defaultScore += 100
                view.tv_current_score.text = defaultScore.toString()
            }
            view.findViewById<Button>(R.id.tv_btn_stop).setOnClickListener {
                if (defaultScore > userScore) {
                    scoreInfo.setValue(defaultScore)
                }
                defaultScore = 0
                view.tv_current_score.text = defaultScore.toString()

            }
        }
        return view
    }

}