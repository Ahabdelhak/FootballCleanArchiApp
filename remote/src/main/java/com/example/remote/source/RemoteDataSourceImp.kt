package com.example.remote.source

import com.example.data.repository.RemoteDataSource
import com.example.entity.CompetitionDetailsResponse
import com.example.entity.CompetitionResponse
import com.example.entity.TeamsResponse
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

    override suspend fun competitionDetailsResult(id:Int): CompetitionDetailsResponse {
        val response = apiService.getCompetitionDetails(id)
        if (response.code() != 200) throw Exception(response.message())
        val networkData = response.body()
        if (networkData?.errorCode == 404) throw Exception("Error")
        return networkData!!
    }

    override suspend fun competitionTeamsResult(id:Int): TeamsResponse {
        val response = apiService.getTeams(id)
        if (response.code() != 200) throw Exception(response.message())
        val networkData = response.body()
        if (networkData?.errorCode == 404) throw Exception("Error")
        return networkData!!
    }
}