package com.sw.sw_api_kotlin_project.model.repository

import com.sw.sw_api_kotlin_project.model.data.PlanetPagingSource
import com.sw.sw_api_kotlin_project.model.entity.Resource
import com.sw.sw_api_kotlin_project.network.SWService
import com.sw.sw_api_kotlin_project.network.model.Planet
import com.sw.sw_api_kotlin_project.network.model.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlanetRepository @Inject constructor(private val swService: SWService) {
    suspend fun getPlanets(page: Int): Flow<Resource<Results<Planet>>> = flow {
        emit(Resource.loading(data = null))
        try {
            val response = swService.getPlanets(page)
            emit(Resource.success(data = response))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "error"))
        }
    }

    fun planetListPagingSource() = PlanetPagingSource(swService)

    suspend fun getPlanetsSearch(search: String) = swService.getPlanetsSearch(search)
}