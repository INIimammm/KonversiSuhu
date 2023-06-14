package org.d3if3045.convertsuhu.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if3045.convertsuhu.R
import org.d3if3045.convertsuhu.databinding.FragmentHitungBinding
import org.d3if3045.convertsuhu.db.SuhuDao
import org.d3if3045.convertsuhu.db.SuhuDb
import org.d3if3045.convertsuhu.model.HasilKonversi


class HitungFragment : Fragment() {

    private lateinit var binding : FragmentHitungBinding
    private val viewModel: HitungViewModel by lazy {
        val db = SuhuDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)

        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(
                R.id.action_hitungFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment)
                return true}
            R.id.menu_penemu_suhu -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_penemuSuhuFragment)
                return true}
        }

        return super.onOptionsItemSelected(item)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View  {
        binding = FragmentHitungBinding.inflate(layoutInflater,container,false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val temperature = resources.getStringArray(R.array.temperature_units)
        val adapter = ArrayAdapter(requireActivity(), R.layout.list_item, temperature)

        binding.convertFrom.setAdapter(adapter)
        binding.convertTo.setAdapter(adapter)

        binding.buttonConvert.setOnClickListener{hitungSuhu()}
        binding.buttonRumus.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_hitungFragment_to_rumusPerhitungan
            )
        }
        viewModel.getHasilKonversi().observe(requireActivity(),{showResult(it)})
        binding.buttonReset.setOnClickListener{reset()}
        binding.buttonShare.setOnClickListener{shareData()}
    }


    fun hitungSuhu(){
        val value = binding.inputTemperature.text.toString()
        if(TextUtils.isEmpty(value)) {
            Toast.makeText(context, "Please input temperature!", Toast.LENGTH_LONG).show()
            return
        }

        val fromUnit = binding.convertFrom.text.toString()
        if(fromUnit == null){
            Toast.makeText(context, "Please choose temperature!", Toast.LENGTH_LONG).show()
            return
        }
        val toUnit = binding.convertTo.text.toString()
        if(toUnit == null) {
            Toast.makeText(context, "Please choose temperature!", Toast.LENGTH_LONG).show()
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
        binding.buttonConvert.visibility = View.GONE
        binding.result.visibility = View.VISIBLE
        binding.resultConverter.visibility = View.VISIBLE
        binding.resultConverter.text = result.result.toString()
        binding.buttonReset.visibility = View.VISIBLE
        binding.buttonRumus.visibility = View.VISIBLE
        binding.buttonShare.visibility = View.VISIBLE
    }
    private fun reset() {
        binding.buttonConvert.visibility = View.VISIBLE
        binding.inputTemperature.text?.clear()
        binding.convertTo.text?.clear()
        binding.convertFrom.text?.clear()
        binding.result.visibility = View.GONE
        binding.resultConverter.visibility = View.GONE
        binding.buttonReset.visibility = View.GONE
        binding.buttonRumus.visibility = View.GONE
        binding.buttonShare.visibility = View.GONE
    }
    private fun shareData() {
        val from = binding.convertFrom.text.toString()
        val to = binding.convertTo.text.toString()
        val suhuAwal = when(from){
            resources.getStringArray(R.array.temperature_units)[0] -> "Kelvin"
            resources.getStringArray(R.array.temperature_units)[1] -> "Celcius"
            else -> "Fahrenheit"
        }
        val suhuAkhir = when(to){
            resources.getStringArray(R.array.temperature_units)[0] -> "Kelvin"
            resources.getStringArray(R.array.temperature_units)[1] -> "Celcius"
            else -> "Fahrenheit"
        }
        val message = getString(R.string.bagikan_template,
            binding.inputTemperature.text,
            suhuAwal,
            suhuAkhir,
            binding.resultConverter.text
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if(shareIntent.resolveActivity(
                requireActivity().packageManager) != null){
            startActivity(shareIntent)
        }
    }
}