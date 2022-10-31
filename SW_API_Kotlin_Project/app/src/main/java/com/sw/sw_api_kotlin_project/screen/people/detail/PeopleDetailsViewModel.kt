package com.sw.sw_api_kotlin_project.screen.people.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sw.sw_api_kotlin_project.screen.base.BaseViewModel
import com.sw.sw_api_kotlin_project.model.entity.Favorite
import com.sw.sw_api_kotlin_project.network.model.People
import com.sw.sw_api_kotlin_project.model.repository.FavoriteRepository
import com.sw.sw_api_kotlin_project.utils.DateFormatter
import com.sw.sw_api_kotlin_project.model.entity.ListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : BaseViewModel() {
    private val _favoriteStatus = MutableLiveData<Boolean>()
    val favoriteStatus: LiveData<Boolean> = _favoriteStatus

    fun getFavoriteState(name: String) {
        viewModelScope.launch {
            _favoriteStatus.value = checkFavoriteState(name)
        }
    }

    fun addOrDeleteFavorite(people: People) {
        viewModelScope.launch {
            val favorite: Favorite? = favoriteRepository.getFavorite(people.name)
            if (favorite == null) {
                favoriteRepository.add(
                    Favorite(
                        name = people.name,
                        listType = ListType.PEOPLE,
                        people = people,
                        film = null,
                        planet = null,
                        registrationDate = DateFormatter.getTodayDateStringYYYYMMDDHHMMSS()
                    )
                )
            } else {
                favoriteRepository.delete(favorite)
            }
            getFavoriteState(people.name)
        }
    }

    private suspend fun checkFavoriteState(name: String): Boolean =
        favoriteRepository.getFavorite(name) != null
}