package org.d3if3045.convertsuhu.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3045.convertsuhu.db.SuhuDao

class HistoriViewModel(private val db: SuhuDao) : ViewModel() {
    val data = db.getLastSuhu()
    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}