package com.suyashshukla.startnewlife.nokot.ViewModel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(private val activity : Activity) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isSignedIn = MutableLiveData<Boolean>(false)
    val isSignedIn: LiveData<Boolean> get() = _isSignedIn

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("425219939383-foffqt18ji090lemb6lb1qud0plf6q6h.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun signInWithGoogle(signInLauncher: (Intent) -> Unit) {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher(signInIntent) // Trigger the launcher
    }

    fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isSignedIn.value = true
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Authentication failed."
                }
            }
        } catch (e: Exception) {
            _errorMessage.value = "Sign-in failed: ${e.message}"
        }
    }
}
