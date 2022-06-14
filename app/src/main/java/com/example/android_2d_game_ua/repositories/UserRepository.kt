package com.example.android_2d_game_ua.repositories

import androidx.lifecycle.MutableLiveData
import com.example.android_2d_game_ua.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {
    var auth: FirebaseAuth = Firebase.auth
    private var userLogedIn: MutableLiveData<Boolean> = MutableLiveData()
    private var loginError: MutableLiveData<String?> = MutableLiveData()
    private var liveUserData: MutableLiveData<User> = MutableLiveData()
    init {
        if (auth.currentUser != null) {
            userLogedIn.postValue(true)
            loginError.postValue(null)
        }
    }

    private val database: DatabaseReference =
        Firebase.database("https://android-2d-game-ua-ff466-default-rtdb.europe-west1.firebasedatabase.app/").reference

    fun getLogedIn(): MutableLiveData<Boolean> {
        return userLogedIn
    }

    fun getLoginError(): MutableLiveData<String?> {
        return loginError
    }

    fun getUserLiveData(): MutableLiveData<User> {
        return liveUserData
    }

    fun logOut() {
        auth.signOut()
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLogedIn.postValue(true)
                } else {
                    loginError.postValue(task.exception!!.message.toString())
                }
            }
    }

    fun registerUser(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userLogedIn.postValue(true)
                val firebaseUser: FirebaseUser = task.result!!.user!!
                firebaseUser.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(username)
                        .build()
                )
                val user = User(firebaseUser.uid, username, email, password, 0)
                database.child("Users").child(user.userId.toString()).setValue(user)
            } else {
                loginError.postValue(task.exception!!.message.toString())
            }
        }
    }

    fun getUserByUserId(){
        auth.uid?.let {
            database.child("Users").child(it).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val dummyUser = User(
                        dataSnapshot.child("userId").value.toString(),
                        dataSnapshot.child("username").value.toString(),
                        dataSnapshot.child("email").value.toString(),
                        dataSnapshot.child("password").value.toString(),
                        dataSnapshot.child("score").value as? Long
                    )
                    liveUserData.postValue(dummyUser)
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }

    fun setScore(uid: String, score: Long) {
        database.child("Users").child(uid).child("score").setValue(score)
    }

    fun createUser(user: User) {
        database.child("Users").child(user.userId.toString()).setValue(user)
    }
}