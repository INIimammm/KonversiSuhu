package org.d3if3045.convertsuhu.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3045.convertsuhu.db.SuhuDao
import org.d3if3045.convertsuhu.db.SuhuEntity
import org.d3if3045.convertsuhu.model.HasilKonversi
import org.d3if3045.convertsuhu.model.Suhu

class HitungViewModel (private val db: SuhuDao): ViewModel() {
    private val hasilKonversi = MutableLiveData<HasilKonversi?>()

    val data = db.getLastSuhu()


    fun hitungSuhu(value: Float, fromUnit: String, toUnit: String) {
        val data = Suhu(
            value = value,
            from = fromUnit,
            to = toUnit
        )
        hasilKonversi.value = data.HitungSuhu()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val suhu = SuhuEntity(
                    value = value,
                    from = fromUnit,
                    to = toUnit
                )
                db.insert(suhu)
            }
        }
    }

    fun Suhu.HitungSuhu(): HasilKonversi {
        val inputSuhu = value
        val fromSuhu = from
        val toSuhu = to
        var hasil = 0f

        when (fromSuhu) {
            "Kelvin" -> {
                when (toSuhu) {
                    "Celcius" -> hasil = (value - 273.15).toFloat()
                    "Fahrenheit" -> hasil = ((value - 273.15) * 9 / 5 + 32).toFloat()
                }
            }
            "Celcius" -> {
                when (toSuhu) {
                    "Fahrenheit" -> hasil = (inputSuhu * 9 / 5) + 32
                    "Kelvin" -> hasil = (inputSuhu + 273.15).toFloat()
                }
            }
            "Fahrenheit" -> {
                when (toSuhu) {
                    "Celcius" -> hasil = (value - 32) * 5 / 9
                    "Kelvin" -> hasil = ((value - 32) * 5 / 9 + 273.15).toFloat()
                }
            }
        }
        return HasilKonversi(hasil)

    }

    fun getHasilKonversi(): LiveData<HasilKonversi?> = hasilKonversi
}

