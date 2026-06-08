package com.example.robomarciano

class MarcianoPremium(
    private val acaoPersonalizada: AcaoPersonalizada
) : MarcianoAvancado() {

    override fun responda(frase: String): String {
        return if (Regex("\\bagir\\b", RegexOption.IGNORE_CASE).containsMatchIn(frase)) {
            "É pra já!\n${acaoPersonalizada.executar(frase)}"
        } else {
            super.responda(frase)
        }
    }
}
