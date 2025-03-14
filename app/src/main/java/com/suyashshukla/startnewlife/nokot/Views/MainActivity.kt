package com.suyashshukla.startnewlife.nokot.Views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import com.google.firebase.auth.FirebaseAuth
import com.suyashshukla.startnewlife.nokot.ui.theme.NOKOTTheme
import com.suyashshukla.startnewlife.nokot.ViewModel.LoginViewModel
import com.suyashshukla.startnewlife.nokot.ui.LoginScreen

class MainActivity : ComponentActivity() {

    // Lazily initializes the LoginViewModel. This ensures it is created only when accessed for the first time.
    private val loginViewModel by lazy {
        Log.d("MainActivity", "LoginViewModel initialized") // Log for debugging ViewModel initialization.
        LoginViewModel(this)
    }

    // Registers an activity result launcher to handle the result from the Google Sign-In activity.
    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("MainActivity", "Sign-in activity result received") // Log when a result is received.
        if (result.resultCode == RESULT_OK) { // Check if the result indicates success.
            Log.d("MainActivity", "Sign-in successful") // Log successful sign-in.
            loginViewModel.handleSignInResult(result.data) // Process the sign-in result using the ViewModel.
            NavigateToNoteScreen()
        } else {
            Log.d("MainActivity", "Sign-in failed or canceled") // Log if sign-in fails or is canceled.
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called") // Log when onCreate is invoked.

        if(FirebaseAuth.getInstance().currentUser != null){
            NavigateToNoteScreen()
        } else {
            setContent { // Set the content view for the activity using Jetpack Compose.
                NOKOTTheme { // Apply the app's theme.
                    val isDarkTheme = isSystemInDarkTheme() // Check if the system is in dark mode.
                    Log.d(
                        "MainActivity",
                        "System dark theme: $isDarkTheme"
                    ) // Log the current theme mode.

                    Surface { // Surface acts as a container for UI elements.
                        Log.d(
                            "MainActivity",
                            "Rendering LoginScreen"
                        ) // Log when rendering the login screen.
                        LoginScreen(
                            viewModel = loginViewModel, // Pass the ViewModel to the LoginScreen composable.
                            signInLauncher = signInLauncher::launch // Pass the sign-in launcher to handle sign-in actions.
                        )
                    }
                }
            }
        }
    }
    fun NavigateToNoteScreen(){
        val intent = Intent(this, NotesListActivity::class.java) // Intent to navigate to NotesListActivity.
        Log.d("MainActivity", "Navigating to NotesListActivity") // Log navigation to the next screen.
        startActivity(intent) // Start NotesListActivity.
        finish()
    }
}
