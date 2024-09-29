package com.android.sustentare.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.sustentare.R
import com.android.sustentare.ui.theme.BlueHigh
import com.android.sustentare.ui.theme.GreenHigh
import com.android.sustentare.ui.theme.GreenLow
import com.android.sustentare.ui.theme.Grey60
import com.android.sustentare.ui.theme.latoFontFamily
import com.android.sustentare.ui.viewmodel.AuthStates
import com.android.sustentare.ui.viewmodel.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    val authStates = authViewModel.authStates.observeAsState()
    val context = LocalContext.current
    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(authStates.value) {
        when (authStates.value) {
            is AuthStates.SignupSuccess -> {
                if (!hasNavigated) {
                    hasNavigated = true
                    Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            }
            is AuthStates.Error -> {
                Toast.makeText(context, (authStates.value as AuthStates.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GreenLow)
    ){
        Image(
            painter = painterResource(id = R.drawable.background_signup),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Ajusta a escala da imagem
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.2f) // Define a opacidade aqui
        )

        Image(
            painter = painterResource(id = R.drawable.title),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.TopCenter)
                .offset(x = (-20).dp, y = 100.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Crie sua conta",
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = latoFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
                    .align(Alignment.Start)
                    .offset(x = 55.dp, y = 10.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nome", color = Grey60, fontSize = 20.sp, fontFamily = latoFontFamily) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BlueHigh,
                    unfocusedBorderColor = GreenHigh,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(50.dp),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Grey60, fontSize = 20.sp, fontFamily = latoFontFamily) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BlueHigh,
                    unfocusedBorderColor = GreenHigh,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(50.dp), // Arredondamento aqui
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha", color = Grey60, fontSize = 20.sp, fontFamily = latoFontFamily) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BlueHigh,
                    unfocusedBorderColor = GreenHigh,
                    errorBorderColor = Color.Red,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(50.dp), // Arredondamento aqui
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirme a Senha", color = Grey60, fontSize = 20.sp, fontFamily = latoFontFamily) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BlueHigh,
                    unfocusedBorderColor = GreenHigh,
                    errorBorderColor = Color.Red,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(100.dp),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.7f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.offset(x = (-10).dp)
                ) {
                    Text(
                        text = "Faça login",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = latoFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(onClick = {
                    authViewModel.checkSignup(email, password, confirmPassword, username)
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenHigh
                    )) {
                    Text(
                        text = "Criar Conta",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = latoFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}