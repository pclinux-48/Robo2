package com.example.robomarciano

class RelatorioEspacialAcao : AcaoPersonalizada {

    override fun executar(comando: String): String {
        val missao = comando.replace(Regex("(?i).*?\\bagir\\b"), "").trim()
        val textoProcessado = missao.ifBlank { "sem instruções adicionais" }
        val palavras = if (missao.isBlank()) 0 else missao.split(Regex("\\s+")).size
        val vogais = Regex("[aeiouáéíóúâêîôûãõ]", RegexOption.IGNORE_CASE)
            .findAll(textoProcessado)
            .count()
        val invertido = textoProcessado.reversed()
        val codigoMarciano = textoProcessado.uppercase().replace(Regex("\\s+"), "-")

        return buildString {
            appendLine("Missão registrada: $textoProcessado")
            appendLine("Palavras detectadas: $palavras")
            appendLine("Vogais detectadas: $vogais")
            appendLine("Texto invertido: $invertido")
            append("Código marciano: $codigoMarciano")
        }
    }
}
