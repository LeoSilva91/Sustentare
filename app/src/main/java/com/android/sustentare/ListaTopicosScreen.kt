package com.android.sustentare

import androidx.compose.foundation.layout.* // Importa funções para o layout
import androidx.compose.foundation.lazy.LazyColumn // Importa LazyColumn
import androidx.compose.foundation.lazy.items // Importa a função items para listas
import androidx.compose.foundation.text.ClickableText // Para texto clicável
import androidx.compose.material.* // Para usar Scaffold, TopAppBar, etc.
import androidx.compose.material.icons.Icons // Importa o pacote de ícones
import androidx.compose.material.icons.filled.CheckCircle // Importa o ícone de verificação
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.sustentare.Topico // Importa a classe Topico

// Função que exibe a tela com a lista de tópicos/desafios
@Composable
fun ListaTopicosScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Desafios Sustentáveis") }) } // Define o título da página
    ) {
        // LazyColumn exibe os tópicos em uma lista rolável
        LazyColumn(
            modifier = Modifier.padding(16.dp), // Define o espaçamento ao redor da lista
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento entre os itens
        ) {
            // items(topicos) passa a lista de tópicos para exibição
            items(topicos) { topico ->
                // Para cada tópico, chama o TopicoItem
                TopicoItem(navController, topico)
            }
        }
    }
}

// Função que exibe um item de tópico com título e ícone (se concluído)
@Composable
fun TopicoItem(navController: NavHostController, topico: Topico) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, // Espaça os elementos horizontalmente
        modifier = Modifier
            .fillMaxWidth() // O item ocupa toda a largura
            .padding(8.dp) // Adiciona espaçamento em torno do item
    ) {
        // Texto clicável, que navega para a tela de detalhes ao clicar
        ClickableText(
            text = AnnotatedString(topico.titulo),
            onClick = { navController.navigate("desafio/${topico.id}") } // Navegação ao clicar no item
        )
        // Se o tópico foi concluído, mostra um ícone verde
        if (topico.concluido) {
            Icon(
                imageVector = Icons.Default.CheckCircle, // Ícone de verificação
                contentDescription = "Concluído",
                tint = Color.Green // Cor verde para indicar conclusão
            )
        }
    }
}
