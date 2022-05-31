package com.example.android_2d_game_ua

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val userId: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val score: Int? = null
) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}