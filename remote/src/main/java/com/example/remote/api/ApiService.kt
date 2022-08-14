package com.example.remote.api

import com.example.entity.CompetitionDetailsResponse
import com.example.entity.CompetitionResponse
import com.example.entity.TeamsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("/v2/competitions/")
    suspend fun competitionResult(): Response<CompetitionResponse>

    // TODO: 14/08/2022 May apply token interceptor or Build Config
    @GET("/v2/competitions/{id}")
    suspend fun getCompetitionDetails(
        @Path("id") id: Int, @Header("X-Auth-Token") token:String?="8c2f1b0b6b2d4ee2997ef7729d7f35d5"
    ): Response<CompetitionDetailsResponse>

    // TODO: 14/08/2022 May apply token interceptor or Build Config
    @GET("/v2/competitions/{id}/teams")
    suspend fun getTeams(
        @Path("id") id: Int, @Header("X-Auth-Token") token:String?="8c2f1b0b6b2d4ee2997ef7729d7f35d5"
    ): Response<TeamsResponse>
}