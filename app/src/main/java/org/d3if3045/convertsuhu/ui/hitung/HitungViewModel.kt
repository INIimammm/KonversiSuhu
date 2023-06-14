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
import org.d3if3045.convertsuhu.model.hitungSuhu

class HitungViewModel (private val db: SuhuDao): ViewModel() {
    private val hasilKonversi = MutableLiveData<HasilKonversi?>()

    fun hitungSuhu(value: Float, fromUnit: String, toUnit: String) {
        val data = SuhuEntity(
            value = value,
            from = fromUnit,
            to = toUnit
        )
        hasilKonversi.value = data.hitungSuhu()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(data)
            }
        }
    }



    fun getHasilKonversi(): LiveData<HasilKonversi?> = hasilKonversi

}

