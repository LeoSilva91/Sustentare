package com.android.sustentare

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.sustentare.ui.login.LoginScreen
import com.android.sustentare.ui.login.SignupScreen
import com.android.sustentare.ui.screen.HomeScreen
import com.android.sustentare.ui.viewmodel.AuthStates
import com.android.sustentare.ui.viewmodel.AuthViewModel

@Composable
fun SustentareNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    // Observa o estado de autenticação
    val authStates = authViewModel.authStates.observeAsState()

    // Define a tela inicial com base no estado de autenticação
    val startDestination = when (authStates.value) {
        is AuthStates.Authenticated -> "home"
        else -> "login"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Navegação padrão de autenticação
        composable("login") {
            LoginScreen(modifier = modifier, navController = navController, authViewModel = authViewModel)
        }
        composable("signup") {
            SignupScreen(modifier = modifier, navController = navController, authViewModel = authViewModel)
        }
        composable("home") {
            HomeScreen(modifier = modifier, navController = navController, authViewModel = authViewModel)
        }

        // --- Adições relacionadas aos tópicos e desafios ---

        // Tela de Lista de Tópicos - adicionada à navegação após login
        composable("listaTopicos") {
            ListaTopicosScreen(navController = navController)
        }

        // Tela de Detalhe de Desafio - permite ver o detalhe do tópico e anexar imagem
        composable("desafio/{topicoId}") { backStackEntry ->
            val topicoId = backStackEntry.arguments?.getString("topicoId")?.toInt() ?: 0
            val topico = topicos.find { it.id == topicoId }
            if (topico != null) {
                DesafioScreen(navController = navController, topico = topico)
            }
        }
    }
}
