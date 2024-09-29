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
    val topicosList = remember { mutableStateListOf<Topico>() } // lista observável para refletir mudanças em tempo real
    var carregando by remember { mutableStateOf(true) }
    var erroCarregamento by remember { mutableStateOf<String?>(null) }

    // Carregar status de conclusão dos tópicos do Firestore ao inicializar a tela
    LaunchedEffect(Unit) {
        Log.d("Firestore", "Observando status de conclusão dos tópicos no Firestore...")

        firestore.collection("usuarios")
            .document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .collection("desafios")
            .addSnapshotListener { result, exception ->
                if (exception != null) {
                    erroCarregamento = exception.message
                    carregando = false
                    Log.e("Firestore", "Erro ao carregar status dos tópicos: ", exception)
                    return@addSnapshotListener
                }

                // Atualizar a lista de tópicos quando houver mudanças no Firestore
                if (result != null) {
                    topicosList.clear() // Limpar a lista para preencher com dados atualizados
                    for (topico in topicos) {
                        // Verificar se o tópico está concluído no Firestore
                        val documento = result.documents.find { it.getLong("id")?.toInt() == topico.id }
                        val concluido = documento?.getBoolean("concluido") ?: false
                        topicosList.add(topico.copy(concluido = concluido))
                    }
                }
                carregando = false
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
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
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

                    if (topico.concluido) {
                        Spacer(modifier = Modifier.width(8.dp))
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
