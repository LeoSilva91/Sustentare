package com.android.sustentare.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.sustentare.R
import com.android.sustentare.navigation.NavigationItem
import com.android.sustentare.ui.theme.GreenHigh
import com.android.sustentare.ui.theme.GreenLow
import com.android.sustentare.ui.theme.latoFontFamily
import com.android.sustentare.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenLow)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.title),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.Start)
                .offset(x = (-20).dp, y = 100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Agrupar os quadrados de navegação centralizados
        NavigationGrid(navController)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                authViewModel.checkLogout()
                navController.navigate(NavigationItem.Login.route) {
                    popUpTo(NavigationItem.Home.route) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = GreenHigh)
        ) {
            Text(text = "Sair", color = Color.White)
        }
    }
}

@Composable
fun NavigationGrid(navController: NavController) {
    // Usando Box para centralizar o conteúdo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp), // Espaço vertical para centralizar
        contentAlignment = Alignment.Center // Centraliza os cards no Box
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationCard(title = "Educação") {
                    navController.navigate(NavigationItem.Educational.route)
                }
                Spacer(modifier = Modifier.width(8.dp))
                NavigationCard(title = "Cálculo de CO2") {
                    navController.navigate(NavigationItem.Co2EmissionWork.route)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationCard(title = "Perfil") {
                    navController.navigate(NavigationItem.Profile.route)
                }
                Spacer(modifier = Modifier.width(8.dp))
                NavigationCard(title = "Desafios") {
                    navController.navigate(NavigationItem.Challenger.route)
                }
            }
        }
    }
}

@Composable
fun NavigationCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}
