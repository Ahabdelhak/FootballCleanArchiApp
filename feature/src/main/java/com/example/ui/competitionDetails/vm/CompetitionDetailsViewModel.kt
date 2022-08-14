package com.example.ui.competitionDetails.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.usecase.CompetitionDetailsUseCase
import com.example.feature.core.BaseViewModel
import com.example.ui.competitionDetails.contract.CompetitionDetailsContract
import kotlinx.coroutines.flow.onStart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCompetitionUseCase: CompetitionDetailsUseCase,
) : BaseViewModel<CompetitionDetailsContract.Event, CompetitionDetailsContract.State, CompetitionDetailsContract.Effect>() {



    override fun createInitialState(): CompetitionDetailsContract.State {
        return CompetitionDetailsContract.State(
            competitionDetailsState = CompetitionDetailsContract.CompetitionDetailsState.Idle
        )
    }

    override fun handleEvent(event: CompetitionDetailsContract.Event) {
        when (event) {
            is CompetitionDetailsContract.Event.GetCompetitionLDetails -> {
                getCompetitionDetails(event.id)
            }
        }
    }

    /**
     * Fetch Details
     */
    private fun getCompetitionDetails(id:Int) {
        viewModelScope.launch {
            getCompetitionUseCase.execute(id)
                .onStart { emit(Resource.Loading) }
                .collect {

                    when (it) {
                        is Resource.Loading -> {
                            // Set State
                            setState { copy(competitionDetailsState = CompetitionDetailsContract.CompetitionDetailsState.Loading) }
                        }
                        is Resource.Empty -> {
                            // Set State
                            setState { copy(competitionDetailsState = CompetitionDetailsContract.CompetitionDetailsState.Idle) }
                        }
                        is Resource.Success -> {
                            // Set State
                            setState {
                                copy(
                                    competitionDetailsState = CompetitionDetailsContract.CompetitionDetailsState.Success(
                                        result = it.data
                                    )
                                )
                            }
                        }
                        is Resource.Error -> {
                            // Set Effect
                            setEffect { CompetitionDetailsContract.Effect.ShowError(message = it.message) }
                        }
                    }
                }
        }
    }

}