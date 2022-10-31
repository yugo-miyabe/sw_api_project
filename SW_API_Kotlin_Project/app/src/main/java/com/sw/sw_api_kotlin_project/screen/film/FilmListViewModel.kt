package com.sw.sw_api_kotlin_project.screen.film

import androidx.lifecycle.liveData
import com.sw.sw_api_kotlin_project.screen.base.BaseViewModel
import com.sw.sw_api_kotlin_project.model.repository.FilmsRepository
import com.sw.sw_api_kotlin_project.model.entity.PageType
import com.sw.sw_api_kotlin_project.model.entity.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
) : BaseViewModel() {

    fun getFilms(pageType: PageType) = liveData(Dispatchers.IO) {
        pageParameterFormat(pageType)
        emit(Resource.loading(null))
        try {
            val response = filmsRepository.getFilms(page)
            emit(Resource.success(data = response))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "error"))
        }
    }
}
