package com.example.android_2d_game_ua.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.android_2d_game_ua.R
import com.example.android_2d_game_ua.view_models.AuthViewModel
import com.example.android_2d_game_ua.view_models.factories.AuthViewModelFactory
import kotlinx.android.synthetic.main.fragment_menu.view.*

class MenuFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory()
        ).get(AuthViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.button_logout.setOnClickListener {
            viewModel.logoutUser()
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_loginFragment)
        }

        view.button_game.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_gameFragment)
        }

        view.button_leaderboard.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_leaderboardFragment)
        }

        view.button_profile.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_menuFragment_to_profileFragment)
        }

        return view
    }
}