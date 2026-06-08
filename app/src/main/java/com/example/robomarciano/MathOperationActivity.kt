package com.example.robomarciano

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.robomarciano.databinding.ActivityMathOperationBinding

class MathOperationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMathOperationBinding
    private lateinit var tipo: TipoRobo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMathOperationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tipoExtra = intent.getStringExtra(EXTRA_TIPO_ROBO)
        tipo = TipoRobo.valueOf(tipoExtra ?: TipoRobo.AVANCADO.name)

        configurarTela()
    }

    private fun configurarTela() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.tvTipoRobo.text = getString(R.string.math_robo_ativo, tipo.titulo)

        binding.btnMontarComando.setOnClickListener {
            concluirComando()
        }

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }

    private fun concluirComando() {
        val operacao = when (binding.rgOperacoes.checkedRadioButtonId) {
            R.id.rbSomar -> "some"
            R.id.rbSubtrair -> "subtraia"
            R.id.rbMultiplicar -> "multiplique"
            R.id.rbDividir -> "divida"
            else -> "some"
        }

        val valor1 = binding.etValor1.text?.toString().orEmpty().trim()
        val valor2 = binding.etValor2.text?.toString().orEmpty().trim()
        val valor3 = binding.etValor3.text?.toString().orEmpty().trim()

        val numeros = listOf(valor1, valor2, valor3).filter { it.isNotBlank() }

        if (numeros.size < 2) {
            binding.tilValor2.error = getString(R.string.math_erro_minimo)
            return
        }

        binding.tilValor2.error = null

        val comando = buildString {
            append(operacao)
            numeros.forEach { numero ->
                append(" ")
                append(numero.replace(',', '.'))
            }
        }

        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(EXTRA_COMANDO_MATEMATICO, comando)
        )
        finish()
    }

    companion object {
        const val EXTRA_TIPO_ROBO = "extra_tipo_robo"
        const val EXTRA_COMANDO_MATEMATICO = "extra_comando_matematico"
    }
}
