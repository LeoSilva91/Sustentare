package com.android.sustentare.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.sustentare.ui.viewmodel.EducationalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationalDetailScreen(
    navController: NavController,
    contentLink: String,
    educationalViewModel: EducationalViewModel
) {
    val educationalDetailContent = educationalViewModel.educationalDetailContent.observeAsState()

    LaunchedEffect(contentLink) {
        educationalViewModel.loadEducationalDetailContent(contentLink)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Conteúdo Educacional") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when {
                educationalDetailContent.value == null -> {
                    Text(text = "Carregando conteúdo...")
                }
                else -> {
                    educationalDetailContent.value?.let { content ->
                        Text(text = content.title, style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = content.description, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Publicado em: ${content.date}", style = MaterialTheme.typography.bodySmall)
                    } ?: run {
                        Text(text = "Conteúdo não encontrado.")
                    }
                }
            }
        }
    }
}