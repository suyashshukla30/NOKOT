package com.suyashshukla.startnewlife.nokot.ViewModel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.suyashshukla.startnewlife.nokot.Model.Repository.NotesRepository
import com.suyashshukla.startnewlife.nokot.Root.NotesApplication
import kotlinx.coroutines.launch

class LoginViewModel(private val activity : Activity) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val googleSignInClient: GoogleSignInClient

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isSignedIn = MutableLiveData(false)
    val isSignedIn: LiveData<Boolean> get() = _isSignedIn

    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?> get() = _userId
    var  notesRepository: NotesRepository ?= null;
    private val firestore = FirebaseFirestore.getInstance()

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("425219939383-foffqt18ji090lemb6lb1qud0plf6q6h.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun signInWithGoogle(signInLauncher: (Intent) -> Unit) {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher(signInIntent)
    }

    fun handleSignInResult(data: Intent?) {

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val email = account.email
                    val userId = email?.substringBefore("@")
                    _userId.value = userId
                    _isSignedIn.value = true
                    _errorMessage.value = null
                    if (userId != null) {
                        initializeNotesRepository(userId)
                        syncNotesFromFirestore()
                    }
                } else {
                    _errorMessage.value = "Authentication failed."
                }
            }
        } catch (e: Exception) {
            _errorMessage.value = "Sign-in failed: ${e.message}"
        }
    }

    private fun syncNotesFromFirestore() {
        notesRepository?.let { repository ->
            viewModelScope.launch {
                try {
                    repository.syncNotesFromFirestore()
                } catch (e: Exception) {
                    _errorMessage.value = "Error syncing notes: ${e.message}"
                }
            }
        }    }

    private fun initializeNotesRepository(value: String) {
        val applicationContext = activity.applicationContext as NotesApplication
        notesRepository = NotesRepository(
            applicationContext.database.NotesDao(),
            firestore,
            value
        )
    }
}
