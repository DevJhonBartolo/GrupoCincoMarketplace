package com.jhondevs.grupocincomarketplace.ui.car

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Car Fragment"
    }
    val text: LiveData<String> = _text
}