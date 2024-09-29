package com.android.sustentare

data class Topico(
    val id: Int = 0,
    val titulo: String = "",
    val descricao: String = "",
    var concluido: Boolean = false
)

// Definição da lista de tópicos (desafios)
val topicos = listOf(
    Topico(1, "Uso de garrafas reutilizáveis", "Fotos de funcionários utilizando garrafas de água reutilizáveis."),
    Topico(2, "Economia de energia", "Registrar momentos em que luzes ou dispositivos são desligados."),
    Topico(3, "Incentivo ao transporte sustentável", "Capturar imagens de colaboradores utilizando transporte sustentável."),
    Topico(4, "Reaproveitamento de materiais", "Fotos de colaboradores reutilizando papéis ou outros materiais."),
    Topico(5, "Campanhas de doação", "Registrar a entrega de roupas, alimentos ou equipamentos eletrônicos para reciclagem ou doação, promovendo a economia circular."),
    Topico(6, "Áreas verdes no escritório", "Mostrar espaços de convivência com plantas ou pequenos jardins no ambiente de trabalho, destacando o incentivo à criação de áreas verdes internas."),
    Topico(7, "Redução de impressões", "Capturar o momento em que uma equipe adota o uso de documentos digitais, evitando o uso de papel desnecessário."),
    Topico(8, "Ações de conscientização", "Fotos de eventos de conscientização ambiental no escritório, como palestras sobre sustentabilidade, workshops de reciclagem ou campanhas internas."),
    Topico(9, "Descarte correto de lixo", "Tirar fotos de colaboradores separando o lixo em coletores recicláveis (papel, plástico, vidro e metal) ou mostrando o uso correto de estações de reciclagem."),
    Topico(10, "Uso de canecas no lugar de copos descartáveis", "Colaboradores usando suas próprias canecas de cerâmica ou vidro, substituindo copos descartáveis para café ou água."),
    Topico(11, "Redução de plásticos", "Fotos mostrando o uso de talheres, pratos e sacolas reutilizáveis nos intervalos ou em almoços, em vez de itens de plástico descartável."),
    Topico(12, "Troca de ideias sustentáveis", "Registro de reuniões ou painéis de discussão onde equipes compartilham ideias e práticas de sustentabilidade aplicáveis ao ambiente corporativo.")
)
