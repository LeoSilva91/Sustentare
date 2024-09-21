package com.android.sustentare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.android.sustentare.ui.theme.SustentareTheme
import com.android.sustentare.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SustentareTheme {
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    SustentareNavigation(
                        Modifier.padding(innerPadding),
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        authViewModel.checkAuthStatus() // Verifica o estado de autenticação
    }

    override fun onPause() {
        super.onPause()
        authViewModel.startTimeout() // Inicia o timeout quando a Activity sai do primeiro plano
    }

    override fun onResume() {
        super.onResume()
        authViewModel.cancelTimeout() // Cancela o timeout ao retornar para o primeiro plano
    }
}
