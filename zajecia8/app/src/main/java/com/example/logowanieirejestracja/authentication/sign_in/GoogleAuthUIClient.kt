package com.example.logowanieirejestracja.authentication.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.example.logowanieirejestracja.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthUIClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("idk", "tu byl bląd")
            if (e is CancellationException) {
                throw e
            }
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult{
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val idToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(idToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = user.uid,
                        username = user.displayName,
                        profilePicture = user.photoUrl.toString()
                    )
                },
                null
            )

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) {
                throw e
            }
            SignInResult(null, e.localizedMessage)
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) {
                throw e
            }
        }
    }

    fun getSingedInUser(): UserData? {
        val user = auth.currentUser ?: return null
        return UserData(
            userId = user.uid,
            username = user.displayName,
            profilePicture = user.photoUrl.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}