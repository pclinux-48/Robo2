package com.example.robomarciano

import org.junit.Assert.assertEquals
import org.junit.Test

class MarcianoTest {

    private val marciano = Marciano()
    private val avancado = MarcianoAvancado()
    private val premium = MarcianoPremium { "Ação recebida: $it" }

    @Test
    fun respondePergunta() {
        assertEquals("Certamente", marciano.responda("Você está bem?"))
    }

    @Test
    fun respondeGrito() {
        assertEquals("Opa! Calma aí!", marciano.responda("EI MARCIANO"))
    }

    @Test
    fun respondeGritoEmPergunta() {
        assertEquals("Relaxa, eu sei o que estou fazendo!", marciano.responda("EI, VOCÊ ESTÁ AÍ?"))
    }

    @Test
    fun respondeQuandoHaEuNaFrase() {
        assertEquals("A responsabilidade é sua", marciano.responda("Hoje eu resolvo isso"))
    }

    @Test
    fun respondeSilencio() {
        assertEquals("Não me incomode", marciano.responda("   "))
    }

    @Test
    fun respondeOperacaoMatematica() {
        assertEquals("Essa eu sei: 15", avancado.responda("some", 5.0, 10.0))
    }

    @Test
    fun premiumTambemRespondeOperacaoMatematica() {
        assertEquals("Essa eu sei: 20", premium.responda("multiplique", 4.0, 5.0))
    }

    @Test
    fun respondeAcaoPersonalizada() {
        assertEquals("É pra já!\nAção recebida: agir abrir compartimento", premium.responda("agir abrir compartimento"))
    }

    @Test
    fun conversaMarcianaInterpretaOperacaoDoAvancado() {
        val resultado = ConversaMarciana.processar(TipoRobo.AVANCADO, "some 2 3 4")

        assertEquals("some 2 3 4", resultado.mensagemUsuario)
        assertEquals("Essa eu sei: 9", resultado.resposta)
    }

    @Test
    fun conversaMarcianaReconheceFim() {
        val resultado = ConversaMarciana.processar(TipoRobo.BASICO, "FIM")

        assertEquals("FIM", resultado.mensagemUsuario)
        assertEquals("Conversa encerrada. Ate a proxima missao.", resultado.resposta)
    }
}
