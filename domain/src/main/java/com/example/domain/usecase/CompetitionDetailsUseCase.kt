package com.example.domain.usecase

import com.example.common.Resource
import com.example.domain.qualifiers.IoDispatcher
import com.example.domain.repository.Repository
import com.example.domain.usecase.core.BaseUseCase
import com.example.entity.CompetitionDetailsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CompetitionDetailsUseCase @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<CompetitionDetailsResponse, Int>() {

    override suspend fun buildRequest(params: Int?): Flow<Resource<CompetitionDetailsResponse>> {
        return repository.getCompetitionDetailsResult(id = params!!).flowOn(dispatcher)
    }
}