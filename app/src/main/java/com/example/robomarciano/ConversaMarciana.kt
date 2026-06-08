package com.example.robomarciano

data class ResultadoMarciano(
    val mensagemUsuario: String,
    val resposta: String,
    val encerrouConversa: Boolean
)

object ConversaMarciana {

    private val operacoesSuportadas = setOf("some", "subtraia", "multiplique", "divida")

    fun processar(tipo: TipoRobo, textoDigitado: String): ResultadoMarciano {
        val robo = tipo.fabrica()
        val entrada = textoDigitado.trim()
        val exibicaoUsuario = if (entrada.isBlank()) "(silencio)" else entrada

        if (entrada.equals("FIM", ignoreCase = true)) {
            return ResultadoMarciano(
                mensagemUsuario = exibicaoUsuario,
                resposta = "Conversa encerrada. Ate a proxima missao.",
                encerrouConversa = true
            )
        }

        return ResultadoMarciano(
            mensagemUsuario = exibicaoUsuario,
            resposta = interpretarEntrada(entrada, tipo, robo),
            encerrouConversa = false
        )
    }

    private fun interpretarEntrada(entrada: String, tipo: TipoRobo, robo: Marciano): String {
        if (entrada.isBlank()) {
            return robo.responda(entrada)
        }

        val tokens = entrada.split(Regex("\\s+"))
        val operacao = tokens.firstOrNull()?.lowercase().orEmpty()

        if (operacao !in operacoesSuportadas || tipo == TipoRobo.BASICO) {
            return robo.responda(entrada)
        }

        if (tokens.size < 3) {
            return "Use uma operacao seguida de pelo menos dois numeros. Ex.: some 2 3"
        }

        val operandos = tokens.drop(1).map { token ->
            token.replace(',', '.').toDoubleOrNull()
        }

        if (operandos.any { it == null }) {
            return "Use apenas numeros nas operacoes matematicas. Ex.: multiplique 4 2"
        }

        return robo.responda(operacao, *operandos.filterNotNull().toDoubleArray())
    }
}
