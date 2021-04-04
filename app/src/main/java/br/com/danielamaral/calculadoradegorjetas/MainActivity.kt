package br.com.danielamaral.calculadoradegorjetas

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import br.com.danielamaral.calculadoradegorjetas.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCalcular.setOnClickListener {
            calcularGorjeta()
        }

        binding.etPrecoServico.setOnKeyListener { v, keyCode, event -> tratarEventosTeclado(v, keyCode) }
    }

    private fun calcularGorjeta() {
        val precoServico = binding.etPrecoServico.text.toString().toDoubleOrNull()
        if (precoServico == null) {
            binding.tvResultado.text = ""
            return
        }
        val percentualComissao = when (binding.rg.checkedRadioButtonId) {
            R.id.opcao_20_porcento -> 0.20
            R.id.opcao_18_porcento -> 0.18
            else -> 0.15
        }

        var gorjeja = percentualComissao * precoServico
        val arredondarConta = binding.swArredondarConta.isChecked

        if (arredondarConta) {
            gorjeja = kotlin.math.ceil(gorjeja)
        }

        val gorjetaFormatada = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(gorjeja)
        binding.tvResultado.text = getString(R.string.total_gorjeta, gorjetaFormatada)
    }

    private fun tratarEventosTeclado(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}