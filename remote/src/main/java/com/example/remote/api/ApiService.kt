package com.example.remote.api

import com.example.entity.CompetitionResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/v4/competitions/")
    suspend fun competitionResult(): Response<CompetitionResponse>

}