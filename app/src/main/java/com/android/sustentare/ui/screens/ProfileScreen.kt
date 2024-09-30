package com.android.sustentare.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.android.sustentare.navigation.NavigationItem
import com.android.sustentare.ui.viewmodel.AuthViewModel


@Composable
fun ProfileScreen(authViewModel: AuthViewModel = viewModel(), navController: NavController) {
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
        // Cartão para os dados do usuário
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp), // Aumenta a altura do Card
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ícone de perfil
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Ícone de Perfil",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                userData.value?.let { user ->
                    if (user.username.isNotEmpty() && user.email.isNotEmpty()) {
                        Text(
                            text = "Nome: ${user.username}",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(4.dp)) // Espaço entre o nome e o email
                        Text(
                            text = "Email: ${user.email}",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Start // Centraliza o texto
                        )
                    }
                } ?: run {
                    Text(text = "Carregando dados...", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Linha para os botões
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate(NavigationItem.Home.route)},
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text(text = "Voltar")
            }

            Button(
                onClick = { authViewModel.checkLogout() },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text(text = "Logout")
            }
        }
    }
}