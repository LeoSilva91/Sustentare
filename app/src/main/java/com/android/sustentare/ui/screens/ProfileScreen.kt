package com.android.sustentare.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.sustentare.ui.viewmodel.AuthViewModel


@Composable
fun ProfileScreen(authViewModel: AuthViewModel = viewModel()) {
    val userData = authViewModel.userData.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.getUserData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        userData.value?.let { user ->
            Text(text = "Nome: ${user.username}")
            Text(text = "Email: ${user.email}")
        } ?: run {
            Text(text = "Carregando dados...")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { authViewModel.checkLogout() }) {
            Text(text = "Logout")
        }
    }
}


