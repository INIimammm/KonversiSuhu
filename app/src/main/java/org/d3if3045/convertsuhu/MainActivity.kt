package org.d3if3045.convertsuhu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import org.d3if3045.convertsuhu.databinding.ActivityMainBinding
import org.d3if3045.convertsuhu.model.HasilKonversi

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    lateinit var fromTemperature : AutoCompleteTextView
    lateinit var toTemperature : AutoCompleteTextView
    lateinit var inputValue : TextInputEditText
    lateinit var buttonConvert : Button
    lateinit var resultConverter : TextView
    lateinit var textResult: TextView
    lateinit var buttonReset : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fromTemperature = binding.convertFrom
        toTemperature = binding.convertTo
        inputValue = binding.inputTemperature
        buttonConvert = binding.buttonConvert
        resultConverter = binding.resultConverter
        textResult = binding.result
        buttonReset = binding.buttonReset

        val temperature = resources.getStringArray(R.array.temperature_units)
        val adapter = ArrayAdapter(this, R.layout.list_item, temperature)

        fromTemperature.setAdapter(adapter)
        toTemperature.setAdapter(adapter)

        buttonConvert.setOnClickListener{hitungSuhu()}
        viewModel.getHasilKonversi().observe(this,{showResult(it)})
        buttonReset.setOnClickListener{reset()}

    }



    fun hitungSuhu(){
        val value = inputValue.text.toString()
        if(TextUtils.isEmpty(value)) {
            Toast.makeText(this, "Please input temperature!", Toast.LENGTH_LONG).show()
            return
        }

        val fromUnit = fromTemperature.text.toString()
        if(fromUnit == null){
            Toast.makeText(this, "Please choose temperature!", Toast.LENGTH_LONG).show()
            return
        }
        val toUnit = toTemperature.text.toString()
        if(toUnit == null) {
            Toast.makeText(this, "Please choose temperature!", Toast.LENGTH_LONG).show()
            return
        }
        viewModel.hitungSuhu(
            value.toFloat(),
            fromUnit,
            toUnit
        )
    }

    private fun showResult(result: HasilKonversi?){
        if (result == null) return
        textResult.visibility = View.VISIBLE
        resultConverter.visibility = View.VISIBLE
        resultConverter.text = result.result.toString()
        buttonReset.visibility = View.VISIBLE
    }
    private fun reset() {
        inputValue.text?.clear()
        textResult.visibility = View.GONE
        resultConverter.visibility = View.GONE
        buttonReset.visibility = View.GONE
    }
}