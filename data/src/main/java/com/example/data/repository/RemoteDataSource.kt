package com.example.data.repository

import com.example.entity.CompetitionResponse


/**
 * Methods of Remote Data Source
 */
interface RemoteDataSource {

    suspend fun competitionResult(): CompetitionResponse

}