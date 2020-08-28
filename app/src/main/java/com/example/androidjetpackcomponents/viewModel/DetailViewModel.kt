package com.example.androidjetpackcomponents.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidjetpackcomponents.model.DogBreed

class DetailViewModel:ViewModel() {
    val  dogLiveData = MutableLiveData<DogBreed>()

    fun fetch(){
        val dog = DogBreed("1","A","10","breedGroup","breedFor","high","image")
        dogLiveData.value = dog
    }
}
