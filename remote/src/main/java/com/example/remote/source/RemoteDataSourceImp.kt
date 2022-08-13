package com.example.remote.source

import com.example.data.repository.RemoteDataSource
import com.example.entity.CompetitionResponse
import com.example.remote.api.ApiService
import javax.inject.Inject


class RemoteDataSourceImp @Inject constructor(
    private val apiService: ApiService) : RemoteDataSource {


    override suspend fun competitionResult(): CompetitionResponse {
        val response = apiService.competitionResult()
        if (response.code() != 200) throw Exception(response.message())
        val networkData = response.body()
        if (networkData?.error == 404) throw Exception("Error")
        return networkData!!
    }
}