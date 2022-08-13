package com.example.ui.competitionsResult.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.usecase.CompetitionListUseCase
import com.example.feature.core.BaseViewModel
import kotlinx.coroutines.flow.onStart
import com.example.ui.competitionsResult.contract.CompetitionListContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCompetitionUseCase: CompetitionListUseCase,
) : BaseViewModel<CompetitionListContract.Event, CompetitionListContract.State, CompetitionListContract.Effect>() {


    init {
        setEvent(CompetitionListContract.Event.GetCompetitionList)
    }

    override fun createInitialState(): CompetitionListContract.State {
        return CompetitionListContract.State(
            competitionListState = CompetitionListContract.CompetitionListState.Idle
        )
    }

    override fun handleEvent(event: CompetitionListContract.Event) {
        when (event) {
            is CompetitionListContract.Event.GetCompetitionList -> {
                getCompetitionList()
            }
        }
    }

    /**
     * Fetch List
     */
    private fun getCompetitionList() {
        viewModelScope.launch {
            getCompetitionUseCase.execute(Any())
                .onStart { emit(Resource.Loading) }
                .collect {

                    when (it) {
                        is Resource.Loading -> {
                            // Set State
                            setState { copy(competitionListState = CompetitionListContract.CompetitionListState.Loading) }
                        }
                        is Resource.Empty -> {
                            // Set State
                            setState { copy(competitionListState = CompetitionListContract.CompetitionListState.Idle) }
                        }
                        is Resource.Success -> {
                            // Set State
                            setState {
                                copy(
                                    competitionListState = CompetitionListContract.CompetitionListState.Success(
                                        result = it.data
                                    )
                                )
                            }
                        }
                        is Resource.Error -> {
                            // Set Effect
                            setEffect { CompetitionListContract.Effect.ShowError(message = it.message) }
                        }
                    }
                }
        }
    }

}