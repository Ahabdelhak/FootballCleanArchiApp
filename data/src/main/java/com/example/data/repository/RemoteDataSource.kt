package com.example.data.repository

import com.example.entity.CompetitionDetailsResponse
import com.example.entity.CompetitionResponse
import com.example.entity.TeamsResponse


/**
 * Methods of Remote Data Source
 */
interface RemoteDataSource {

    suspend fun competitionResult(): CompetitionResponse
    suspend fun competitionDetailsResult(id:Int): CompetitionDetailsResponse
    suspend fun competitionTeamsResult(id:Int): TeamsResponse

}