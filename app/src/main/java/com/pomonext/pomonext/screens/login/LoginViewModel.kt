package com.pomonext.pomonext.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.pomonext.pomonext.model.MUser
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading



    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {

            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { task ->
                Log.d(
                    "FB",
                    "signInWithEmailAndPassword: Successful ${task.user.toString().toString()}"
                )
                home()

            }.addOnFailureListener { task ->
                Log.d(
                    "FB",
                    "signInWithEmailAndPassword: Error ${task.message.toString().toString()}"
                )


            }


        }

    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            if (_loading.value == false) {
                _loading.value = true
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { task ->
                    val userId = task.user?.uid
                    val userEmail = task.user?.email

                    if (userId != null && userEmail != null) {
                        createUser(userId, userEmail)
                        home()
                    }

                }.addOnFailureListener { task ->
                    "CreateWithEmailAndPassword: Error ${task.message.toString().toString()}"

                }
                _loading.value = false
            }
        }

    private fun createUser(userId: String, email: String) {
        val MUser = MUser(userId = userId, userEmail = email).toUserMap()
        FirebaseFirestore.getInstance().collection("users").add(MUser)


    }


}