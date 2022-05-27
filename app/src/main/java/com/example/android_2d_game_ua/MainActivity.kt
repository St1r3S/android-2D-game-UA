package com.example.android_2d_game_ua

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val usernameId = intent.getStringExtra("username_id")
        val emailId = intent.getStringExtra("email_id")

        tv_user_id.text = "User ID :: $userId"
        tv_username_id.text = "Username :: $usernameId"
        tv_email_id.text = "Email ID:: $emailId"

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(
                this@MainActivity,
                LoginActivity::class.java
            ))
            finish()
        }
    }
}