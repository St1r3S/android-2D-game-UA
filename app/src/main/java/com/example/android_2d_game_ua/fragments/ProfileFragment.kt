package com.example.android_2d_game_ua.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android_2d_game_ua.R
import com.example.android_2d_game_ua.repositories.UserRepository
import com.example.android_2d_game_ua.view_models.MenuViewModel
import com.example.android_2d_game_ua.view_models.ProfileViewModel
import com.example.android_2d_game_ua.view_models.factories.MenuViewModelFactory
import com.example.android_2d_game_ua.view_models.factories.ProfileViewModelFactory
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {
    private lateinit var viewModelProfile: ProfileViewModel
    private lateinit var viewModelMenu: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewModelProfile = ViewModelProvider(
            this,
            ProfileViewModelFactory()
        ).get(ProfileViewModel::class.java)
        viewModelMenu = ViewModelProvider(
            this,
            MenuViewModelFactory(UserRepository())
        ).get(MenuViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        viewModelProfile.getUser(viewModelMenu)
        viewModelProfile.user.observe(viewLifecycleOwner) {
            view.tv_score.text = "User SCORE :: ${it.score}"
            view.tv_user_id.text = "User ID :: ${it.userId}"
            view.tv_username_id.text = "Username :: ${it.username}"
            view.tv_email_id.text = "Email :: ${it.email}"
        }

        return view
    }
}