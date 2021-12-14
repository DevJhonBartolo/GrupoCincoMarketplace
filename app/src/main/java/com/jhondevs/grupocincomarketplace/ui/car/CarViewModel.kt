package com.jhondevs.grupocincomarketplace.ui.car

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Carrito"
    }
    val text: LiveData<String> = _text
}