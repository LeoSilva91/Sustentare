package com.android.sustentare.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
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
            TopAppBar(title = { Text("Educação Sustentável", fontSize = 22.sp) })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else if (error.value != null) {
                    Text(text = "Erro ao carregar: ${error.value}")
                } else if (educationalContent.value.isEmpty()) {
                    Text(text = "Nenhum conteúdo disponível.")
                } else {
                    educationalContent.value.forEach { content ->
                        ContentItem(content)
                    }
                }
            }
        }
    )
}

@Composable
fun ContentItem(content: EducationalContent) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = content.title, style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = content.description, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp))
        Spacer(modifier = Modifier.height(8.dp))
        ClickableText(
            text = AnnotatedString("Leia mais"),
            onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(content.link))) },
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
        )
        Text(text = "Publicado em: ${content.date}", style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, color = Color.Gray))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}
