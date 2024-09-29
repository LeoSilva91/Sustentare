package com.android.sustentare

import android.util.Log
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
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.android.sustentare.ui.theme.GreenHigh

@Composable
fun ListaTopicosScreen(navController: NavController) {
    val firestore = FirebaseFirestore.getInstance()
    val topicosList = remember { topicos.toMutableStateList() }
    var carregando by remember { mutableStateOf(true) }
    var erroCarregamento by remember { mutableStateOf<String?>(null) }

    // Carregar status de conclusão dos tópicos do Firestore ao inicializar a tela
    LaunchedEffect(Unit) {
        Log.d("Firestore", "Consultando status de conclusão dos tópicos do Firestore...")

        firestore.collection("usuarios")
            .document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .collection("desafios")
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    Log.d("Firestore", "Nenhum status de conclusão encontrado na coleção 'desafios'.")
                } else {
                    for (document in result) {
                        val id = document.getLong("id")?.toInt()
                        val concluido = document.getBoolean("concluido") ?: false

                        // Atualizar o status do tópico na lista fixa
                        if (id != null) {
                            topicosList.find { it.id == id }?.let {
                                it.concluido = concluido
                                Log.d("Firestore", "Atualizado tópico ID=$id para concluído=$concluido")
                            }
                        }
                    }
                }
                carregando = false
            }
            .addOnFailureListener { exception ->
                erroCarregamento = exception.message
                carregando = false
                Log.e("Firestore", "Erro ao carregar status dos tópicos: ", exception)
            }
    }

    if (carregando) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    } else if (erroCarregamento != null) {
        Text(
            text = "Erro ao carregar tópicos: ${erroCarregamento}",
            color = Color.Red,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    } else if (topicosList.isEmpty()) {
        Text(
            text = "Nenhum tópico disponível.",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Barra de título com a seta de voltar e o texto centralizado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                // Ícone de seta para voltar todo verde com seta branca
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(40.dp) // Tamanho da seta
                        .background(GreenHigh, CircleShape) // Fundo verde para o botão
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White // Cor da seta branca
                    )
                }

                // Texto "Lista de Tópicos" centralizado e em negrito
                Text(
                    text = "Lista de Tópicos",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Exibição dos tópicos como botões
            topicosList.forEach { topico ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botão para cada tópico
                    Button(
                        onClick = {
                            navController.navigate("desafio/${topico.id}/${topico.titulo}")
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = GreenHigh)
                    ) {
                        Text(
                            text = topico.titulo,
                            color = Color.White
                        )
                    }

                    // Status de "concluído" ao lado do botão
                    if (topico.concluido) {
                        Spacer(modifier = Modifier.width(8.dp)) // Espaço entre o botão e o ícone de check
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(GreenHigh, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✔",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
