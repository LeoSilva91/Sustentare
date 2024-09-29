package com.android.sustentare.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.sustentare.ui.screens.EducationalScreen
import com.android.sustentare.ui.login.LoginScreen
import com.android.sustentare.ui.login.SignupScreen
import com.android.sustentare.ui.screens.ProfileScreen
import com.android.sustentare.ui.screens.Co2EmissionWorkScreen
import com.android.sustentare.ui.screens.EducationalDetailScreen
import com.android.sustentare.ui.screens.HomeScreen
import com.android.sustentare.ui.viewmodel.AuthStates
import com.android.sustentare.ui.viewmodel.AuthViewModel
import com.android.sustentare.ui.viewmodel.EducationalViewModel


@Composable
fun SustentareNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val educationalViewModel: EducationalViewModel = viewModel()

    val authStates = authViewModel.authStates.observeAsState()
    val startDestination = when (authStates.value) {
        is AuthStates.Authenticated -> NavigationItem.Home.route
        else -> NavigationItem.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Login.route) {
            LoginScreen(modifier = modifier, navController = navController, authViewModel = authViewModel)
        }
        composable(NavigationItem.Signup.route) {
            SignupScreen(modifier = modifier, navController = navController, authViewModel = authViewModel)
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(NavigationItem.Educational.route) {
            EducationalScreen(modifier = modifier, navController = navController, authViewModel = authViewModel, educationalViewModel = educationalViewModel)
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen(authViewModel = authViewModel)
        }
        composable(NavigationItem.EducationalDetail("contentLink").route) { backStackEntry ->
            val contentLink = backStackEntry.arguments?.getString("contentLink") ?: return@composable
            EducationalDetailScreen(
                navController = navController,
                contentLink = contentLink,
                educationalViewModel = educationalViewModel,
                authViewModel = authViewModel
            )
        }
        composable(NavigationItem.Co2EmissionWork.route) {
            Co2EmissionWorkScreen(navController = navController)
        }
    }
}