package com.example.androidjetpackcomponents.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidjetpackcomponents.model.DogApiService
import com.example.androidjetpackcomponents.model.DogBreed
import com.example.androidjetpackcomponents.model.DogDataBase
import com.example.androidjetpackcomponents.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application): BaseViewModel(application) {
    val filterTextAll = MutableLiveData<String>()
    private var prefHelper = SharedPreferencesHelper()
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L
    private val dogService = DogApiService()
    private val disposable = CompositeDisposable()
    val dogs = MutableLiveData<List<DogBreed>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            dogService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){
                    override fun onSuccess(dogList: List<DogBreed>) {
                        storeDogsLocally(dogList)
                    }

                    override fun onError(e: Throwable) {
                       loading.value = false
                        dogsLoadError.value = true
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun dogRetrived(dogList: List<DogBreed>){
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list : List<DogBreed>){
        launch{
            val dao = DogDataBase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i< list.size){
                list[i].uuid = result[i].toInt()
                ++i
            }
            dogRetrived(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}