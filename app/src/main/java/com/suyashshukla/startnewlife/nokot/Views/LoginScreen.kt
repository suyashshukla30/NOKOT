package com.suyashshukla.startnewlife.nokot.Views

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.suyashshukla.startnewlife.nokot.ViewModel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), signInLauncher: (Intent) -> Unit) {
    val errorMessage by viewModel.errorMessage.observeAsState()
    val isSignedIn by viewModel.isSignedIn.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to the App")

        Button(onClick = { viewModel.signInWithGoogle(signInLauncher) }) {
            Text("Sign in with Google")
        }

        errorMessage?.let {
            Text(text = it, color = Color.Red)
        }

        isSignedIn?.let {
            if (it) {
                Text(text = "Successfully signed in!")
            } else {
                Text(text = "failed...")
            }
        }
    }
}
