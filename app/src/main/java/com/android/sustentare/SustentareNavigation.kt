package com.android.sustentare

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.sustentare.ui.screen.EducationalScreen
import com.android.sustentare.ui.login.LoginScreen
import com.android.sustentare.ui.login.SignupScreen
import com.android.sustentare.ui.screen.EducationalDetailScreen
import com.android.sustentare.ui.screen.HomeScreen
import com.android.sustentare.ui.viewmodel.AuthStates
import com.android.sustentare.ui.viewmodel.AuthViewModel
import com.android.sustentare.ui.viewmodel.EducationalViewModel


@Composable
fun SustentareNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val educationalViewModel: EducationalViewModel = viewModel()

    val authStates = authViewModel.authStates.observeAsState()
    val startDestination = when (authStates.value) {
        is AuthStates.Authenticated -> "home"
        else -> "login"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = {
            composable("login") {
                LoginScreen(modifier = modifier,navController = navController,authViewModel = authViewModel)
            }
            composable("signup") {
                SignupScreen(modifier = modifier,navController = navController,authViewModel = authViewModel)
            }
            composable("home") {
                HomeScreen(modifier = modifier,navController = navController,authViewModel = authViewModel)
            }
            composable("educational") {
                EducationalScreen(modifier = modifier, navController = navController, authViewModel = authViewModel, educationalViewModel = educationalViewModel)
            }
            composable("educational_detail/{contentLink}") { backStackEntry ->
                val contentLink = backStackEntry.arguments?.getString("contentLink") ?: return@composable
                EducationalDetailScreen(
                    navController = navController,
                    contentLink = contentLink,
                    educationalViewModel = educationalViewModel,
                    authViewModel = authViewModel // Se necessário
                    
                )
            }
        }
    )
}