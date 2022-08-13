package com.example.domain.usecase

import com.example.common.Resource
import com.example.domain.qualifiers.IoDispatcher
import com.example.domain.repository.Repository
import com.example.domain.usecase.core.BaseUseCase
import com.example.entity.CompetitionResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Use Case class for get Result
 */


class CompetitionListUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<CompetitionResponse, Any>() {

    override suspend fun buildRequest(params: Any?): Flow<Resource<CompetitionResponse>> {
        return repository.getCompetitionList().flowOn(dispatcher)
    }
}