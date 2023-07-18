package com.sw.sw_api_kotlin_project.screen.search

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sw.sw_api_kotlin_project.data.model.entity.RequestStatus
import com.sw.sw_api_kotlin_project.data.model.repository.SearchRepository
import com.sw.sw_api_kotlin_project.data.network.model.Film
import com.sw.sw_api_kotlin_project.data.network.model.People
import com.sw.sw_api_kotlin_project.data.network.model.Planet
import com.sw.sw_api_kotlin_project.data.network.model.Results
import com.sw.sw_api_kotlin_project.screen.base.BaseViewModel
import com.sw.sw_api_kotlin_project.screen.search.SearchFragmentDirections.Companion.actionSearchToFilmsDetail
import com.sw.sw_api_kotlin_project.screen.search.SearchFragmentDirections.Companion.actionSearchToPeopleDetail
import com.sw.sw_api_kotlin_project.screen.search.SearchFragmentDirections.Companion.actionSearchToPlanetDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel() {
    private val _searchResultList = MutableLiveData<List<Results<out Parcelable>>>()
    val searchResultList: LiveData<List<Results<out Parcelable>>> get() = _searchResultList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage get() = _errorMessage

    fun getSearchResult(searchString: String) {
        viewModelScope.launch {
            searchRepository.getSearchResult(searchString).collect { response ->
                when (response.status) {
                    RequestStatus.SUCCESS -> {
                        _isLoading.value = false
                        _searchResultList.value = response.data!!
                    }

                    RequestStatus.ERROR -> {
                        _isLoading.value = false
                        _errorMessage.value = response.message!!
                    }

                    RequestStatus.LOADING -> {
                        _isLoading.value = true
                        _errorMessage.value = ""
                    }
                }
            }
        }
    }


    fun onTapPeople(people: People) =
        addNavigationEvent(actionSearchToPeopleDetail(people))

    fun onTapFilm(film: Film) = addNavigationEvent(
        actionSearchToFilmsDetail(film)
    )

    fun onTapPlanet(planet: Planet) =
        addNavigationEvent(actionSearchToPlanetDetail(planet))
}
