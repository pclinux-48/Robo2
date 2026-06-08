package com.example.robomarciano

enum class TipoRobo(
    val titulo: String,
    val descricao: String,
    val orientacao: String,
    val hintEntrada: String,
    val fabrica: () -> Marciano
) {
    BASICO(
        titulo = "Marciano Básico",
        descricao = "Responde perguntas, gritos, silêncio e frases comuns.",
        orientacao = "Use frases comuns, perguntas, gritos ou mensagens com a palavra 'eu'.",
        hintEntrada = "Digite uma frase para o Marciano Básico",
        fabrica = { Marciano() }
    ),
    AVANCADO(
        titulo = "Marciano Avançado",
        descricao = "Também faz contas com some, subtraia, multiplique e divida.",
        orientacao = "Além da conversa normal, aceite operações como 'some 2 3' e 'divida 10 2'.",
        hintEntrada = "Digite uma frase ou operação matemática",
        fabrica = { MarcianoAvancado() }
    ),
    PREMIUM(
        titulo = "Marciano Premium",
        descricao = "Além das contas, aceita o comando agir com ação personalizada.",
        orientacao = "Converse, faça contas e use 'agir ...' para executar a ação especial.",
        hintEntrada = "Digite uma frase, conta ou ação personalizada",
        fabrica = { MarcianoPremium(RelatorioEspacialAcao()) }
    );

    override fun toString(): String = titulo
}
