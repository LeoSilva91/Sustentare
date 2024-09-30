package com.android.sustentare.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.android.sustentare.ui.theme.GreenHigh
import com.android.sustentare.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import com.android.sustentare.navigation.NavigationItem

@Composable
fun DesafioScreen(
    navController: NavController,
    topicoId: Int,
    topicoTitulo: String,
    authViewModel: AuthViewModel,
) {
    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val uid = currentUser?.uid

    var descricao by remember { mutableStateOf("") }
    var mediaUri by remember { mutableStateOf<Uri?>(null) }
    var mediaUrl by remember { mutableStateOf<String?>(null) }
    var concluido by remember { mutableStateOf(false) }
    var erroCarregamento by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Launcher para selecionar mídia da galeria
    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                mediaUri = uri
                Log.d("FirebaseStorage", "URI selecionado: $uri") // Log para verificar URI
            } else {
                Log.e("MediaPicker", "Nenhuma mídia foi selecionada")
            }
        }
    )

    // Carregar dados do Firestore ao inicializar a tela
    LaunchedEffect(Unit) {
        uid?.let { userId ->
            Log.d("DeleteChallenge", "Usuário autenticado: $userId")
            firestore.collection("usuarios").document(userId).collection("desafios")
                .document(topicoId.toString())
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        descricao = document.getString("descricao") ?: ""
                        concluido = document.getBoolean("concluido") ?: false
                        mediaUrl = document.getString("mediaUrl")
                        Log.d("DeleteChallenge", "Desafio encontrado, iniciando exclusão.")
                    }
                }
                .addOnFailureListener { exception ->
                    erroCarregamento = exception.message
                    Log.e("DeleteChallenge", "Desafio não encontrado.")
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Barra de título com a seta de voltar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(40.dp)
                    .background(GreenHigh, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White
                )
            }

            Text(
                text = topicoTitulo,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f),
                maxLines = 1
            )
        }

        // Campo de texto para editar a descrição do desafio
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição do desafio") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Mostrar mídia previamente anexada, se houver
        mediaUrl?.let { url ->
            Spacer(modifier = Modifier.height(16.dp))

            // Exibir imagem ou vídeo usando Coil
            AsyncImage(
                model = url,
                contentDescription = "Mídia Anexada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            )
        }

        // Botão para selecionar uma nova mídia (foto ou vídeo)
        Button(
            onClick = { mediaPickerLauncher.launch("image/* video/*") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Selecionar Foto ou Vídeo")
        }

        // Mostrar miniatura da nova mídia, se houver
        mediaUri?.let { uri ->
            Spacer(modifier = Modifier.height(16.dp))

            // Exibir imagem usando Coil
            AsyncImage(
                model = uri,
                contentDescription = "Mídia Selecionada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            )
        }

// Botão "Salvar" - só habilitado se o campo de descrição não estiver vazio e houver uma mídia anexada
        Button(
            onClick = {
                coroutineScope.launch {
                    uid?.let { userId ->
                        mediaUri?.let { uri ->
                            // Criando a referência do Storage usando o bucket correto
                            val storageRef = storage.getReferenceFromUrl("gs://sustaingreen-62e9e.appspot.com")
                            val fileRef = storageRef.child("usuarios/$userId/desafios/$topicoId/media_${System.currentTimeMillis()}")

                            // Log para verificar URI antes de fazer upload
                            Log.d("FirebaseStorage", "Fazendo upload de mídia do URI: $uri")

                            val uploadTask = fileRef.putFile(uri)

                            uploadTask
                                .addOnSuccessListener {
                                    fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                                        firestore.collection("usuarios").document(userId).collection("desafios")
                                            .document(topicoId.toString())
                                            .set(
                                                mapOf(
                                                    "id" to topicoId,
                                                    "titulo" to topicoTitulo,
                                                    "descricao" to descricao,
                                                    "concluido" to true,
                                                    "usuarioId" to userId,
                                                    "mediaUrl" to downloadUri.toString()
                                                )
                                            )
                                            .addOnSuccessListener {
                                                erroCarregamento = null
                                                concluido = true
                                                navController.popBackStack() // Voltar para a lista de tópicos
                                            }
                                            .addOnFailureListener { exception ->
                                                erroCarregamento = "Erro ao salvar: ${exception.message}"
                                            }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    erroCarregamento = "Erro ao fazer upload da mídia: ${exception.message}"
                                    Log.e("FirebaseStorage", "Erro ao fazer upload: ${exception.message}")
                                }
                        }
                    } ?: run {
                        erroCarregamento = "Usuário não autenticado."
                        Log.e("FirebaseAuth", "Usuário não autenticado.")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenHigh),
            enabled = descricao.isNotBlank() && mediaUri != null // Habilitar botão somente se descrição e mídia estiverem presentes
        ) {
            Text("Salvar", color = Color.White)
        }


// Botão "Deletar Resposta" - atualizado para também deletar a mídia e redirecionar corretamente
        Button(
            onClick = {
                coroutineScope.launch {
                    uid?.let { userId ->
                        // Primeiro, deletar a mídia do Storage, se existir
                        mediaUrl?.let { url ->
                            val storageRef = storage.getReferenceFromUrl(url)
                            storageRef.delete()
                                .addOnSuccessListener {
                                    Log.d("FirebaseStorage", "Mídia deletada com sucesso.")
                                }
                                .addOnFailureListener { exception ->
                                    Log.e("FirebaseStorage", "Erro ao deletar mídia: ${exception.message}")
                                }
                        }

                        // Depois, deletar o documento do Firestore
                        firestore.collection("usuarios").document(userId).collection("desafios")
                            .document(topicoId.toString())
                            .delete()
                            .addOnSuccessListener {
                                Log.d("FirebaseFirestore", "Desafio deletado com sucesso.")
                                // Navegar de volta para a lista de tópicos de forma mais explícita
                                navController.navigate(NavigationItem.Challenger.route) {
                                    popUpTo(NavigationItem.Challenger.route) { inclusive = true }
                                }
                            }
                            .addOnFailureListener { exception ->
                                erroCarregamento = "Erro ao deletar: ${exception.message}"
                                Log.e("FirebaseFirestore", "Erro ao deletar no Firestore: ${exception.message}")
                            }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (concluido) Color.Red else Color.Gray),
            enabled = concluido
        ) {
            Text("Deletar Resposta", color = Color.White)
        }


        // Mostrar erro se houver problema ao salvar, deletar ou carregar
        if (erroCarregamento != null) {
            Text(
                text = "Erro: $erroCarregamento",
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
