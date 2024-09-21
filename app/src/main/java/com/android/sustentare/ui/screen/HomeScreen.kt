package com.android.sustentare.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
fun HomeScreen(modifier: Modifier = Modifier,navController: NavController, authViewModel: AuthViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenLow)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "HOME PAGE",
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = latoFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
                .align(Alignment.Start)
                .offset(x = 125.dp, y = 10.dp)
        )

        Button(
            onClick = {
                authViewModel.checkLogout()
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
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