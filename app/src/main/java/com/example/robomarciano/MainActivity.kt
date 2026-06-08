package com.example.robomarciano

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.robomarciano.databinding.ActivityMainBinding
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var historicoRepository: HistoricoRepository
    private var tipoSelecionado: TipoRobo? = null
    private val historicoComandos = mutableListOf<String>()

    private val respostaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        binding.etEntrada.text?.clear()

        if (resultado.resultCode != Activity.RESULT_OK) {
            return@registerForActivityResult
        }

        val comando = resultado.data?.getStringExtra(ResponseActivity.EXTRA_COMANDO_REGISTRADO)
        val resposta = resultado.data?.getStringExtra(ResponseActivity.EXTRA_RESPOSTA_REGISTRADA)

        if (!comando.isNullOrBlank()) {
            registrarHistorico(comando)
        }

        if (!resposta.isNullOrBlank()) {
            binding.tvUltimaResposta.text = resposta
        }
    }

    private val operacaoMatematicaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        if (resultado.resultCode != Activity.RESULT_OK) {
            return@registerForActivityResult
        }

        val comando = resultado.data?.getStringExtra(MathOperationActivity.EXTRA_COMANDO_MATEMATICO)
        if (!comando.isNullOrBlank()) {
            binding.etEntrada.setText(comando)
            binding.etEntrada.setSelection(comando.length)
            enviarEntrada(comando)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        historicoRepository = HistoricoRepository(this)

        configurarTela()
    }

    override fun onResume() {
        super.onResume()
        binding.etEntrada.text?.clear()
    }

    private fun configurarTela() {
        configurarSeletorDeRobo()
        configurarCampoDeEntrada()
        exibirEstadoInicial()

        binding.btnEnviar.setOnClickListener {
            enviarEntrada()
        }

        binding.btnLimpar.setOnClickListener {
            limparHistorico()
        }

        binding.btnOperacaoMatematica.setOnClickListener {
            abrirTelaOperacaoMatematica()
        }
    }

    private fun configurarCampoDeEntrada() {
        binding.etEntrada.setOnEditorActionListener { _, actionId, event ->
            val enviouPeloIme = actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE
            val enviouPeloEnter = event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN

            if (enviouPeloIme || enviouPeloEnter) {
                enviarEntrada()
                true
            } else {
                false
            }
        }
    }

    private fun configurarSeletorDeRobo() {
        val opcoes = TipoRobo.entries.toTypedArray()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcoes)

        binding.actvSelecaoRobo.setAdapter(adapter)
        binding.actvSelecaoRobo.setText("", false)

        binding.actvSelecaoRobo.setOnItemClickListener { _, _, position, _ ->
            val novoTipo = opcoes[position]
            if (novoTipo == tipoSelecionado) {
                return@setOnItemClickListener
            }

            tipoSelecionado = novoTipo
            ativarRoboSelecionado(novoTipo)
        }
    }

    private fun exibirEstadoInicial() {
        binding.tvSaudacao.text = getString(R.string.boas_vindas_titulo)
        binding.tvSubtitulo.text = getString(R.string.boas_vindas_descricao)
        binding.tvStatusRobo.text = getString(R.string.status_sem_robo)
        binding.tvDescricao.text = getString(R.string.mensagem_selecione_robo)
        binding.tvUltimaResposta.text = getString(R.string.ultima_resposta_placeholder)
        binding.etEntrada.setText("")
        binding.tilEntrada.hint = getString(R.string.hint_entrada)
        binding.etEntrada.isEnabled = false
        binding.btnEnviar.isEnabled = false
        binding.btnLimpar.isEnabled = false
        binding.btnOperacaoMatematica.isEnabled = false
        binding.btnOperacaoMatematica.visibility = View.GONE
        binding.tvHintEnvio.alpha = 0.65f
        binding.cardEntrada.alpha = 0.88f
        carregarHistorico()
    }

    private fun ativarRoboSelecionado(tipo: TipoRobo) {
        binding.tvSaudacao.text = tipo.titulo
        binding.tvSubtitulo.text = tipo.descricao
        binding.tvStatusRobo.text = getString(R.string.status_com_robo)
        binding.tvDescricao.text = tipo.orientacao
        binding.tvUltimaResposta.text = getString(R.string.ultima_resposta_pronto, tipo.titulo)
        binding.tilEntrada.hint = tipo.hintEntrada
        binding.etEntrada.isEnabled = true
        binding.btnEnviar.isEnabled = true
        binding.btnLimpar.isEnabled = true
        val permiteOperacao = tipo != TipoRobo.BASICO
        binding.btnOperacaoMatematica.isEnabled = permiteOperacao
        binding.btnOperacaoMatematica.visibility = if (permiteOperacao) View.VISIBLE else View.GONE
        binding.tvHintEnvio.alpha = 1f
        binding.cardEntrada.alpha = 1f
        binding.etEntrada.text?.clear()
        carregarHistorico()
    }

    private fun enviarEntrada(textoForcado: String? = null) {
        val tipo = tipoSelecionado ?: return
        val textoDigitado = textoForcado ?: binding.etEntrada.text?.toString().orEmpty()

        val intent = Intent(this, ResponseActivity::class.java).apply {
            putExtra(ResponseActivity.EXTRA_TIPO_ROBO, tipo.name)
            putExtra(ResponseActivity.EXTRA_MENSAGEM, textoDigitado)
        }
        respostaLauncher.launch(intent)
    }

    private fun limparHistorico() {
        binding.etEntrada.text?.clear()
        binding.tvUltimaResposta.text = tipoSelecionado?.let { tipo ->
            getString(R.string.ultima_resposta_pronto, tipo.titulo)
        } ?: getString(R.string.ultima_resposta_placeholder)
        limparHistoricoVisual()
        historicoRepository.limpar()
    }

    private fun limparHistoricoVisual() {
        historicoComandos.clear()
        binding.cgHistorico.removeAllViews()
        binding.tvHistoricoVazio.text = getString(R.string.historico_vazio)
        binding.tvHistoricoVazio.visibility = android.view.View.VISIBLE
    }

    private fun registrarHistorico(comando: String) {
        if (historicoComandos.contains(comando)) {
            historicoComandos.remove(comando)
        }

        historicoComandos.add(0, comando)
        if (historicoComandos.size > 8) {
            historicoComandos.removeLast()
        }
        historicoRepository.salvar(historicoComandos)
        atualizarHistorico()
    }

    private fun atualizarHistorico() {
        binding.cgHistorico.removeAllViews()
        binding.tvHistoricoVazio.visibility = if (historicoComandos.isEmpty()) {
            android.view.View.VISIBLE
        } else {
            android.view.View.GONE
        }

        historicoComandos.forEach { comando ->
            val chip = Chip(this).apply {
                text = comando
                isClickable = true
                isCheckable = false
                setOnClickListener {
                    binding.etEntrada.setText(comando)
                    binding.etEntrada.setSelection(comando.length)
                    enviarEntrada(comando)
                }
            }
            binding.cgHistorico.addView(chip)
        }
    }

    private fun carregarHistorico() {
        historicoComandos.clear()
        historicoComandos.addAll(historicoRepository.carregar().take(8))
        atualizarHistorico()
    }

    private fun abrirTelaOperacaoMatematica() {
        val tipo = tipoSelecionado ?: return
        if (tipo == TipoRobo.BASICO) {
            return
        }

        val intent = Intent(this, MathOperationActivity::class.java).apply {
            putExtra(MathOperationActivity.EXTRA_TIPO_ROBO, tipo.name)
        }
        operacaoMatematicaLauncher.launch(intent)
    }
}
