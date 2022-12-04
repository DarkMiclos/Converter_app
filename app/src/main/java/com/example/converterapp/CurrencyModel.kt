package com.example.converterapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrencyModel: ViewModel(){
    val data = MutableLiveData<String>()

    fun setData(newData: String) {
        data.value = newData
    }
}