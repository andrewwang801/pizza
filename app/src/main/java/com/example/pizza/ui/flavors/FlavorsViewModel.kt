package com.example.pizza.ui.flavors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizza.data.remote.network.dto.FlavorDTO
import com.example.pizza.data.remote.repository.FlavorRepository
import com.example.pizza.data.vo.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlavorsViewModel @Inject constructor(
    private val repository: FlavorRepository
): ViewModel(){

    private val _flavors = MutableLiveData<Resource<List<FlavorDTO>>>()
    val flavors: LiveData<Resource<List<FlavorDTO>>> get() = _flavors

    private val _addedFlavors = mutableListOf<FlavorDTO>()
    val addedFlavors: List<FlavorDTO> get() = _addedFlavors

    fun addFlavor(flavor: FlavorDTO) {
        _addedFlavors.add(flavor)
    }

    fun removeFlavor(flavor: FlavorDTO) {
        _addedFlavors.remove(flavor)
    }

    fun validateFlavors(): Boolean {
        return _addedFlavors.size < 2
    }

    fun getFlavors() {
        viewModelScope.launch {
            repository.getFlavors().collect {
                _flavors.value = it
            }
        }
    }
}