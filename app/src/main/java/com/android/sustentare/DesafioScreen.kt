package com.android.sustentare

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun DesafioScreen(navController: NavController, topico: Topico) {
    // Estado para armazenar a descrição do desafio
    var descricao by remember { mutableStateOf("") }
    // Estado para controlar se uma imagem foi anexada ou não
    var imagemAnexada by remember { mutableStateOf(false) }

    // Lida com o estado do Scaffold (para mostrar a Snackbar)
    val scaffoldState = rememberScaffoldState()
    // Scope para lidar com operações assíncronas como mostrar a Snackbar
    val coroutineScope = rememberCoroutineScope()

    // Scaffold define a estrutura da tela com barra superior, corpo e outros componentes
    Scaffold(
        scaffoldState = scaffoldState, // Passa o estado do Scaffold
        topBar = {
            // Barra de topo com o título do tópico e o botão de voltar
            TopAppBar(
                title = { Text(topico.titulo) }, // Exibe o título do tópico
                navigationIcon = {
                    // Ícone para voltar à lista de tópicos
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar") // Ícone de seta para voltar
                    }
                }
            )
        },
        content = {
            // Corpo da tela do desafio, permite preencher a descrição e anexar uma imagem
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Descrição:") // Texto indicando o campo de descrição
                // Campo de texto para o usuário escrever a descrição do desafio
                BasicTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .border(1.dp, Color.Gray) // Borda cinza para o campo de texto
                        .padding(8.dp)
                )

                // Botão para anexar imagem (a funcionalidade real de seleção de imagem pode ser adicionada depois)
                Button(
                    onClick = { imagemAnexada = true }, // Atualiza o estado ao anexar uma imagem
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Texto no botão muda de acordo com o estado
                    Text(if (imagemAnexada) "Imagem Anexada" else "Anexar Imagem")
                }

                Spacer(modifier = Modifier.weight(1f)) // Espaçamento flexível para empurrar o botão para o final

                // Botão para salvar o desafio
                Button(
                    onClick = {
                        // Verifica se a descrição não está vazia e se uma imagem foi anexada
                        if (descricao.isNotBlank() && imagemAnexada) {
                            topico.concluido = true // Marca o desafio como concluído
                            navController.popBackStack() // Volta para a lista de tópicos
                        } else {
                            // Mostra uma Snackbar com uma mensagem de erro
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Por favor, preencha a descrição e anexe uma imagem."
                                )
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green), // Botão com fundo verde
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Salvar", color = Color.White) // Texto do botão de salvar
                }
            }
        }
    )
}
