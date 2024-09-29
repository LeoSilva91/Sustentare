package com.android.sustentare.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.sustentare.ui.viewmodel.AuthViewModel
import com.android.sustentare.ui.viewmodel.EducationalContent
import com.android.sustentare.ui.viewmodel.EducationalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationalScreen(
    modifier: Modifier = Modifier,
    educationalViewModel: EducationalViewModel,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val educationalContent = educationalViewModel.educationalContent.observeAsState(listOf())
    val isLoading = educationalViewModel.isLoading.observeAsState(false)
    val error = educationalViewModel.error.observeAsState()

    LaunchedEffect(Unit) {
        educationalViewModel.loadEducationalContent() // Carregar conteúdo ao entrar na tela
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Educação Sustentável", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    if (isLoading.value) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else if (error.value != null) {
                        Text(text = "Erro ao carregar: ${error.value}")
                    } else if (educationalContent.value.isEmpty()) {
                        Text(text = "Nenhum conteúdo disponível.")
                    }
                }

                // Adicionar cada conteúdo na LazyColumn
                items(educationalContent.value) { content ->
                    ContentCard(content)
                }
            }
        }
    )
}

@Composable
fun ContentCard(content: EducationalContent) {
    val context = LocalContext.current

    // Exibir cada conteúdo em formato de "Card"
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                // Ação ao clicar no card (pode adicionar outra ação se necessário)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = content.title,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content.description, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp))
            Spacer(modifier = Modifier.height(8.dp))

            // Link "Leia mais" para abrir no navegador
            ClickableText(
                text = AnnotatedString("Leia mais"),
                onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(content.link))) },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Publicado em: ${content.date}",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, color = Color.Gray)
            )
        }
    }
}