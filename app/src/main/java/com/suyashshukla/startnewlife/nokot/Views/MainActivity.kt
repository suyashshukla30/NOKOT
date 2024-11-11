package com.suyashshukla.startnewlife.nokot.Views

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.suyashshukla.startnewlife.nokot.ui.theme.NOKOTTheme
import com.suyashshukla.startnewlife.nokot.ViewModel.LoginViewModel

class MainActivity : ComponentActivity() {
    private val loginViewModel by lazy { LoginViewModel(this) }

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            loginViewModel.handleSignInResult(result.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOKOTTheme {
                Surface {
                    LoginScreen(viewModel = loginViewModel, signInLauncher = signInLauncher::launch)
                }
            }
        }
    }
}
