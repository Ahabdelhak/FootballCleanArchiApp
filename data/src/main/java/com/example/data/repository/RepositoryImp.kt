package com.example.data.repository

import com.example.common.Resource
import com.example.domain.repository.Repository
import com.example.entity.CompetitionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation class of [Repository]
 */
class RepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : Repository {



    override suspend fun getCompetitionList(): Flow<Resource<CompetitionResponse>> {
        return flow {
            try {
                // Get data from RemoteDataSource
                val data = remoteDataSource.competitionResult()
                // Emit data
                emit(Resource.Success(data))
            } catch (ex : Exception) {
                // If remote request fails
                emit(Resource.Error(ex.message ?:"Error"))
            }
        }
    }

}