package com.example.android_2d_game_ua.model.user

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val userId: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val score: Long? = null
)