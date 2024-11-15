package com.example.logowanieirejestracja.authentication.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.logowanieirejestracja.authentication.sign_in.UserData
import org.jetbrains.annotations.Async

@Composable
fun ProfileScreen(
    userData: UserData,
    onSignOutClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData.profilePicture != null) {
            AsyncImage(
                model = userData.profilePicture,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (userData.username != null) {
            Text(text = userData.username)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(onClick = onSignOutClick) {
            Text(text = "Sign out")
        }
    }
}