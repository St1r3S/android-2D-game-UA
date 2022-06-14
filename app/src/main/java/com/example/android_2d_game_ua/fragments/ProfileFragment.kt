package com.example.android_2d_game_ua.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.android_2d_game_ua.R
import com.example.android_2d_game_ua.repositories.UserRepository
import com.example.android_2d_game_ua.view_models.MenuViewModel
import com.example.android_2d_game_ua.view_models.ProfileViewModel
import com.example.android_2d_game_ua.view_models.factories.MenuViewModelFactory
import com.example.android_2d_game_ua.view_models.factories.ProfileViewModelFactory
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.button_to_menu

class ProfileFragment : Fragment() {
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var viewModelMenu: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewModelProfile = ViewModelProvider(
            this,
            ProfileViewModelFactory(UserRepository())
        ).get(ProfileViewModel::class.java)
        viewModelMenu = ViewModelProvider(
            this,
            MenuViewModelFactory(UserRepository())
        ).get(MenuViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.button_to_menu.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_profileFragment_to_menuFragment)
        }
        viewModelProfile.getUserData()

        viewModelProfile.user.observe(viewLifecycleOwner) {
            view.tv_score.text = "${it.score}"
            view.tv_username_id.text = "${it.username}"
            view.tv_email_id.text = "${it.email}"
        }

        return view
    }
}