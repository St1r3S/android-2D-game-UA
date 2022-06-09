package com.example.android_2d_game_ua.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.android_2d_game_ua.R
import com.example.android_2d_game_ua.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.view.*

class RegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        view.tv_login.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        view.findViewById<Button>(R.id.btn_register).setOnClickListener {
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
                    val username: String = et_register_username.text.toString().trim { it <= ' ' }
                    val email: String = et_register_email.text.toString().trim { it <= ' ' }
                    val password: String = et_register_password.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                firebaseUser.updateProfile(
                                    UserProfileChangeRequest.Builder().setDisplayName(username)
                                        .build()
                                )
                                val user = User(firebaseUser.uid, username, email, password, 0)
                                val database: DatabaseReference =
                                    Firebase.database("https://android-2d-game-ua-ff466-default-rtdb.europe-west1.firebasedatabase.app/").reference
                                database.child("Users").child(firebaseUser.uid).setValue(user)

                                Toast.makeText(
                                    context,
                                    "You were registered successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_registrationFragment_to_menuFragment)

                            } else {
                                Toast.makeText(
                                    context,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
        return view
    }
}