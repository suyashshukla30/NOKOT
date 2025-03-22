package com.suyashshukla.startnewlife.nokot.ui

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suyashshukla.startnewlife.nokot.R
import com.suyashshukla.startnewlife.nokot.ViewModel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), // Default ViewModel instance
    signInLauncher: (Intent) -> Unit // Function to launch sign-in activity
) {
    // Observe LiveData properties from the ViewModel
    val errorMessage by viewModel.errorMessage.observeAsState()
    val isSignedIn by viewModel.isSignedIn.observeAsState()

    // Define the UI layout

    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.login_image), // Replace with your image
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 10.dp),
            contentScale = ContentScale.Fit // Adjust image scaling
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)), // Makes the column occupy the entire screen
            horizontalAlignment = Alignment.CenterHorizontally, // Centers children horizontally
            verticalArrangement = Arrangement.Center // Centers children vertically
        ) {
            // Welcome message
            Text(text = "Welcome To Nokot",
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
            )

            // Sign-in button
            Button(onClick = {
                Log.d("LoginScreen", "Sign-in button clicked")
                viewModel.signInWithGoogle(signInLauncher) // Initiates Google Sign-In
            }) {
                Text("Sign in with Google")
            }

            // Display error message if it exists
            errorMessage?.let {
                Log.d("LoginScreen", "Error message: $it")
                Text(text = it, color = Color.Red) // Shows the error in red text
            }

            // Display sign-in status if available
            isSignedIn?.let {
                if (it) {
                    Log.d("LoginScreen", "User signed in successfully")
                    Text(text = "Successfully signed in!") // Success message
                } else {
                    Log.d("LoginScreen", "User sign-in failed")

                }
            }
        }
    }
}
