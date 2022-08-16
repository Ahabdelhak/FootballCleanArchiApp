package com.example.remote.source

import com.example.data.repository.RemoteDataSource
import com.example.entity.CompetitionDetailsResponse
import com.example.entity.CompetitionResponse
import com.example.entity.TeamsResponse
import com.example.remote.api.ApiService
import com.example.remote.api.ErrorMessage
import javax.inject.Inject
import com.google.gson.Gson


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
        if (response.code() != 200) throw Exception(getErrorMessage(response.errorBody()!!.string()))
        val networkData = response.body()
        if (networkData?.errorCode == 404) throw Exception("Error")
        return networkData!!
    }

    override suspend fun competitionTeamsResult(id:Int): TeamsResponse {
        val response = apiService.getTeams(id)
        if (response.code() != 200) throw Exception( getErrorMessage(response.errorBody()!!.string()))
        val networkData = response.body()
        if (networkData?.errorCode == 404) throw Exception("Error")
        return networkData!!
    }

    private fun getErrorMessage(errorBody: String): String? {
        val gson = Gson()
        val errorResponse: ErrorMessage = gson.fromJson(
            errorBody,
            ErrorMessage::class.java)
        return errorResponse.message
    }
}