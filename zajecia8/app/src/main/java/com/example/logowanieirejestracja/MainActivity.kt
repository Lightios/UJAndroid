package com.example.logowanieirejestracja

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logowanieirejestracja.authentication.profile.ProfileScreen
import com.example.logowanieirejestracja.authentication.sign_in.GoogleAuthUIClient
import com.example.logowanieirejestracja.authentication.sign_in.SignInScreen
import com.example.logowanieirejestracja.authentication.sign_in.SignInViewModel
import com.example.logowanieirejestracja.ui.theme.LogowanieIRejestracjaTheme
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

private lateinit var auth: FirebaseAuth

class MainActivity : ComponentActivity() {
    private val googleAuthUIClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)


        setContent {
            LogowanieIRejestracjaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsState()
                            
                            LaunchedEffect(key1 = Unit)
                            {
                                if (googleAuthUIClient.getSingedInUser() != null) {
                                    navController.navigate("profile")
                                }
                            }
                            
                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = {result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUIClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )
                            
                            LaunchedEffect(key1 = state.inSignInSuccess)
                            {
                                if (state.inSignInSuccess) {
                                    Toast.makeText(
                                    applicationContext,
                                    "Sign in success",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate("profile")
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUIClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()

                                        )
                                    }
                                }
                            )
                        }
                        composable("profile") {
                            googleAuthUIClient.getSingedInUser()?.let { it1 ->
                                ProfileScreen(
                                    userData = it1,
                                    onSignOutClick = {
                                        lifecycleScope.launch {
                                            googleAuthUIClient.signOut()
                                            Toast.makeText(
                                                applicationContext,
                                                "Sign out success",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            navController.popBackStack()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
