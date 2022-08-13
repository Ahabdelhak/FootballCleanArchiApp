package com.example.domain.repository

import com.example.common.Resource
import com.example.entity.Competition
import com.example.entity.CompetitionResponse
import kotlinx.coroutines.flow.Flow

/**
 * Methods of Repository
 */
interface Repository {

    suspend fun getCompetitionList() : Flow<Resource<CompetitionResponse>>

}