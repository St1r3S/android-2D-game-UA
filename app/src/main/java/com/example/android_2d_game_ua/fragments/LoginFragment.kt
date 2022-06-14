package com.example.android_2d_game_ua.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.android_2d_game_ua.R
import com.example.android_2d_game_ua.repositories.UserRepository
import com.example.android_2d_game_ua.view_models.AuthViewModel
import com.example.android_2d_game_ua.view_models.factories.AuthViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(UserRepository())
        ).get(AuthViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view.tv_register.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        viewModel.userLogedIn.observe(viewLifecycleOwner) {
            if (it == true) {
                Toast.makeText(
                    context,
                    "You were logged in successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_menuFragment)
            }
        }
        viewModel.loginError.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(
                    context,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            when {
                TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter password!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = et_login_email.text.toString().trim { it <= ' ' }
                    val password: String = et_login_password.text.toString().trim { it <= ' ' }
                    viewModel.loginUser(email, password)
                }
            }
        }
        return view
    }
}