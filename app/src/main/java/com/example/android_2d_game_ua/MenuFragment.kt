package com.example.android_2d_game_ua

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_registration.view.*

class MenuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        view.findViewById<Button>(R.id.button_logout).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_loginFragment)
        }
        view.findViewById<Button>(R.id.button_game).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_gameFragment)
        }
        view.findViewById<Button>(R.id.button_leaderboard).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_leaderboardFragment)
        }
        view.findViewById<Button>(R.id.button_profile).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_profileFragment)
        }
        return view
    }
}