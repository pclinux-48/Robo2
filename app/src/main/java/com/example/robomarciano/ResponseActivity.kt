package com.example.robomarciano

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.robomarciano.databinding.ActivityResponseBinding

class ResponseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResponseBinding
    private lateinit var tipo: TipoRobo
    private lateinit var resultado: ResultadoMarciano

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResponseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tipoExtra = intent.getStringExtra(EXTRA_TIPO_ROBO)
        val mensagem = intent.getStringExtra(EXTRA_MENSAGEM).orEmpty()
        tipo = TipoRobo.valueOf(tipoExtra ?: TipoRobo.BASICO.name)
        resultado = ConversaMarciana.processar(tipo, mensagem)

        configurarTela()
    }

    private fun configurarTela() {
        binding.topAppBar.setNavigationOnClickListener {
            concluirRetorno()
        }

        binding.tvResumoSelecao.text = getString(R.string.resposta_robo_em_uso, tipo.titulo)
        binding.tvMensagemEnviada.text = resultado.mensagemUsuario
        binding.tvRespostaRobo.text = resultado.resposta
        binding.tvObservacao.text = if (resultado.encerrouConversa) {
            getString(R.string.resposta_observacao_fim)
        } else {
            getString(R.string.resposta_observacao_padrao)
        }

        binding.btnVoltarPrincipal.setOnClickListener {
            concluirRetorno()
        }

        onBackPressedDispatcher.addCallback(this) {
            concluirRetorno()
        }
    }

    private fun concluirRetorno() {
        val dados = Intent().apply {
            putExtra(EXTRA_COMANDO_REGISTRADO, resultado.mensagemUsuario)
            putExtra(EXTRA_RESPOSTA_REGISTRADA, resultado.resposta)
            putExtra(EXTRA_ENCERROU_CONVERSA, resultado.encerrouConversa)
        }
        setResult(Activity.RESULT_OK, dados)
        finish()
    }

    companion object {
        const val EXTRA_TIPO_ROBO = "extra_tipo_robo"
        const val EXTRA_MENSAGEM = "extra_mensagem"
        const val EXTRA_COMANDO_REGISTRADO = "extra_comando_registrado"
        const val EXTRA_RESPOSTA_REGISTRADA = "extra_resposta_registrada"
        const val EXTRA_ENCERROU_CONVERSA = "extra_encerrou_conversa"
    }
}
