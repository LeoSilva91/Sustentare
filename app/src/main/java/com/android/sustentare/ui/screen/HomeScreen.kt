package com.android.sustentare.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.sustentare.ui.theme.GreenHigh
import com.android.sustentare.ui.theme.GreenLow
import com.android.sustentare.ui.theme.latoFontFamily
import com.android.sustentare.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenLow)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Título da Home Page
        Text(
            text = "HOME PAGE",
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = latoFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 20.dp)
        )

        // Botão para acessar os desafios (tela de desafios)
        Button(
            onClick = { navController.navigate("listaTopicos") }, // Navega para a tela de desafios
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp), // Espaçamento entre os botões
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenHigh
            )
        ) {
            Text(text = "Ver Desafios", color = Color.White)
        }

        // Botão para logout
        Button(
            onClick = {
                authViewModel.checkLogout() // Faz logout
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true } // Remove a tela de home da pilha ao sair
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally), // Centraliza o botão
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenHigh
            )
        ) {
            Text(text = "Sair", color = Color.White)
        }
    }
}
