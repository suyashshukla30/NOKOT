package com.suyashshukla.startnewlife.nokot.Views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suyashshukla.startnewlife.nokot.Model.Repository.NotesRepository
import com.suyashshukla.startnewlife.nokot.Root.NotesApplication
import androidx.lifecycle.ViewModelProvider
import com.suyashshukla.startnewlife.nokot.ViewModel.NotesViewModel
import com.suyashshukla.startnewlife.nokot.ViewModel.NotesViewModelFactory
import com.suyashshukla.startnewlife.nokot.ui.NotesListScreen

class NotesListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firestore = FirebaseFirestore.getInstance()

        val userId =
            FirebaseAuth.getInstance().currentUser?.email?.substringBefore("@") ?: "unknown_user"


        val repository = NotesRepository(
            (this.applicationContext as NotesApplication).database.NotesDao(),
            firestore,
            userId
        )

        val notesViewModel = ViewModelProvider(
            this,
            NotesViewModelFactory(repository)
        )[NotesViewModel::class.java]

        setContent {
            NotesListScreen(notesViewModel = notesViewModel)
        }
    }
    fun logoutUser(onLogoutComplete: () -> Unit) {
        FirebaseAuth.getInstance().signOut() // Sign out from Firebase
        Log.d("NotesListActivity", "User logged out successfully")

        onLogoutComplete() // Notify UI that logout is complete
    }

}