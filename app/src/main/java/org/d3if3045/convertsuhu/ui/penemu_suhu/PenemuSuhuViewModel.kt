package org.d3if3045.convertsuhu.ui.penemu_suhu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3045.convertsuhu.model.PenemuSuhu
import org.d3if3045.convertsuhu.network.PenemuSuhuApi

class PenemuSuhuViewModel : ViewModel() {
    private val data = MutableLiveData<List<PenemuSuhu>>()
    private val status = MutableLiveData<PenemuSuhuApi.ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(PenemuSuhuApi.ApiStatus.LOADING)
            try {
                data.postValue(PenemuSuhuApi.service.getPenemuSuhu())
                status.postValue(PenemuSuhuApi.ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("Main View Model", "Failure: ${e.message}")
                status.postValue(PenemuSuhuApi.ApiStatus.FAILED)
            }
        }
    }

    fun getData(): LiveData<List<PenemuSuhu>> = data

    fun getStatus(): LiveData<PenemuSuhuApi.ApiStatus> = status
}