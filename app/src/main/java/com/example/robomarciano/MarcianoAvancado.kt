package com.example.robomarciano

import java.util.Locale

open class MarcianoAvancado : Marciano() {

    override fun responda(comando: String, vararg operandos: Double): String {
        return when (comando.trim().lowercase()) {
            "some" -> calcular(operandos) { valores -> valores.sum() }
            "subtraia" -> calcular(operandos) { valores ->
                valores.drop(1).fold(valores.first()) { acumulado, valor -> acumulado - valor }
            }
            "multiplique" -> calcular(operandos) { valores -> valores.reduce(Double::times) }
            "divida" -> {
                if (operandos.drop(1).any { it == 0.0 }) {
                    "Não posso dividir por zero"
                } else {
                    calcular(operandos) { valores ->
                        valores.drop(1).fold(valores.first()) { acumulado, valor -> acumulado / valor }
                    }
                }
            }
            else -> super.responda(comando)
        }
    }

    private fun calcular(operandos: DoubleArray, operacao: (DoubleArray) -> Double): String {
        if (operandos.size < 2) {
            return "Preciso de pelo menos dois números para calcular"
        }

        val resultado = operacao(operandos)
        return "Essa eu sei: ${formatarNumero(resultado)}"
    }

    private fun formatarNumero(numero: Double): String {
        if (numero % 1.0 == 0.0) {
            return numero.toLong().toString()
        }

        return String.format(Locale.US, "%.2f", numero)
            .trimEnd('0')
            .trimEnd('.')
    }
}
