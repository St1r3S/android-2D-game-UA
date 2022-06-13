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
import com.example.android_2d_game_ua.view_models.RegistrationViewModel
import com.example.android_2d_game_ua.view_models.factories.RegistrationViewModelFactory
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.view.*


class RegistrationFragment : Fragment() {
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewModel = ViewModelProvider(
            this,
            RegistrationViewModelFactory(UserRepository())
        ).get(RegistrationViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_registration, container, false)

        view.tv_login.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_registrationFragment_to_loginFragment)
        }

        view.findViewById<Button>(R.id.btn_register).setOnClickListener {
            val username: String = et_register_username.text.toString().trim { it <= ' ' }
            val email: String = et_register_email.text.toString().trim { it <= ' ' }
            val password: String = et_register_password.text.toString().trim { it <= ' ' }

            when {
                TextUtils.isEmpty(et_register_username.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter username!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter password!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    var check = false
                    viewModel.check.removeObservers(viewLifecycleOwner)
                    viewModel.createUser(username, email, password)
                    viewModel.check.observe(viewLifecycleOwner) {
                        if (it == null && check) {
                            viewModel.check.removeObservers(viewLifecycleOwner)
                            Toast.makeText(
                                context,
                                "You were registered successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_registrationFragment_to_menuFragment)
                        } else {
                            if (!check) {
                                Toast.makeText(
                                    context,
                                    it,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            check = true
                        }
                    }
                }
            }
        }
        return view
    }

}