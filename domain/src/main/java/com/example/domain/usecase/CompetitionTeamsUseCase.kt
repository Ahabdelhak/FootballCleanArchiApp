package com.example.domain.usecase

import com.example.common.Resource
import com.example.domain.qualifiers.IoDispatcher
import com.example.domain.repository.Repository
import com.example.domain.usecase.core.BaseUseCase
import com.example.entity.TeamsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CompetitionTeamsUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<TeamsResponse, Int>() {

    override suspend fun buildRequest(params: Int?): Flow<Resource<TeamsResponse>> {
        return repository.getTeamsResult(id = params!!).flowOn(dispatcher)
    }
}