package com.example.robomarciano

open class Marciano {

    open fun responda(frase: String): String {
        val texto = frase.trim()

        if (texto.isBlank()) {
            return "Não me incomode"
        }

        val ehPergunta = texto.endsWith("?")
        val estaGritando = contemPalavraGritada(texto)

        return when {
            ehPergunta && estaGritando -> "Relaxa, eu sei o que estou fazendo!"
            estaGritando -> "Opa! Calma aí!"
            contemPalavraEu(texto) -> "A responsabilidade é sua"
            ehPergunta -> "Certamente"
            else -> "Tudo bem, como quiser"
        }
    }

    open fun responda(comando: String, vararg operandos: Double): String = responda(comando)

    private fun contemPalavraEu(texto: String): Boolean {
        return Regex("\\beu\\b", RegexOption.IGNORE_CASE).containsMatchIn(texto)
    }

    private fun contemPalavraGritada(texto: String): Boolean {
        return Regex("\\p{L}+").findAll(texto).any { resultado ->
            val palavra = resultado.value
            palavra.any { it.isLetter() } && palavra == palavra.uppercase()
        }
    }
}
