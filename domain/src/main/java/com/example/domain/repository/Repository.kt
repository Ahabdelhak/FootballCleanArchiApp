package com.example.domain.repository

import com.example.common.Resource
import com.example.entity.Competition
import com.example.entity.CompetitionDetailsResponse
import com.example.entity.CompetitionResponse
import com.example.entity.TeamsResponse
import kotlinx.coroutines.flow.Flow

/**
 * Methods of Repository
 */
interface Repository {

    suspend fun getCompetitionList() : Flow<Resource<CompetitionResponse>>
    suspend fun getCompetitionDetailsResult(id:Int) : Flow<Resource<CompetitionDetailsResponse>>
    suspend fun getTeamsResult(id:Int) : Flow<Resource<TeamsResponse>>

}