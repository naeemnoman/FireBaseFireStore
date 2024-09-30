package com.example.naeemnoman.firebasefirestore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.naeemnoman.firebasefirestore.model.Data
import com.example.naeemnoman.firebasefirestore.repository.DataRepository

class DataViewModel:ViewModel() {

    private val dataRepository = DataRepository()
    private val datalist:MutableLiveData<List<Data>> = dataRepository.fetchData()
   val dataList : LiveData<List<Data>> get() = datalist


    fun addData(data: Data,onSuccess:() ->Unit,onFailure:()->Unit){
        dataRepository.addData(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }

    fun updateData(data: Data){
        dataRepository.updateData(data)
    }

    fun deleteData(data: Data, onSuccess: () -> Unit, onFailure: () -> Unit){
        dataRepository.deleteData(data)
            .addOnSuccessListener{onSuccess()}
            .addOnFailureListener { onFailure() }
    }

}