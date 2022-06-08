package com.example.android_2d_game_ua

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Navigation.findNavController(view)
                .navigate(R.id.action_profileFragment_to_loginFragment)
        } else {
            val userId = currentUser.uid
            val username = currentUser.displayName
            val userEmail = currentUser.email
            val database: DatabaseReference =
                Firebase.database("https://android-2d-game-ua-ff466-default-rtdb.europe-west1.firebasedatabase.app/").reference
            val scoreInfo = database.child("Users").child(currentUser.uid).child("score")
            scoreInfo.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userScore = snapshot.value.toString()
                    view.tv_score.text = "User SCORE :: $userScore"
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            view.tv_user_id.text = "User ID :: $userId"
            view.tv_username_id.text = "Username :: $username"
            view.tv_email_id.text = "Email :: $userEmail"
        }

        return view

    }
}