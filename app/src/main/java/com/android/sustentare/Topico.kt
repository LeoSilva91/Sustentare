package com.android.sustentare

// Definição do modelo de dados Topico
data class Topico(
    val id: Int, // Identificador único do tópico
    val titulo: String, // Título do tópico/desafio
    val descricao: String, // Descrição do desafio
    var concluido: Boolean = false // Status se o desafio foi concluído
)

// Definição da lista de tópicos (desafios)
val topicos = listOf(
    Topico(1, "Uso de garrafas reutilizáveis", "Fotos de funcionários utilizando garrafas de água reutilizáveis."),
    Topico(2, "Economia de energia", "Registrar momentos em que luzes ou dispositivos são desligados."),
    Topico(3, "Incentivo ao transporte sustentável", "Capturar imagens de colaboradores utilizando transporte sustentável."),
    Topico(4, "Reaproveitamento de materiais", "Fotos de colaboradores reutilizando papéis ou outros materiais."),
    // Continue adicionando os outros tópicos
)
