package com.sw.sw_api_kotlin_project.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sw.sw_api_kotlin_project.api.SWServiceClient
import com.sw.sw_api_kotlin_project.base.BaseViewModel
import com.sw.sw_api_kotlin_project.model.films.FilmsRoot
import com.sw.sw_api_kotlin_project.repository.APIRepository
import com.sw.sw_api_kotlin_project.utils.Result
import kotlinx.coroutines.launch

class FilmsListViewModel(private val apiRepository: APIRepository) : BaseViewModel() {

    private val _films = MutableLiveData<FilmsRoot>()
    val films = _films

    fun getFilmsAPI() {
        startLoading()
        viewModelScope.launch {
            val api = SWServiceClient.getService()
            val response = api.films()
            when (val result = apiRepository.getResponse(response)) {
                is Result.Success -> {
                    _films.value = result.data
                    stopLoading()
                }
                is Result.Error -> {
                    result.type
                    stopLoading()
                }
            }
        }
    }
}

class FilmsListViewModelFactory(private val apiRepository: APIRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilmsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilmsListViewModel(apiRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}