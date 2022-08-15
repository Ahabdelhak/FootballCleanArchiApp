package com.example.ui.competitionDetails.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.usecase.CompetitionDetailsUseCase
import com.example.domain.usecase.CompetitionTeamsUseCase
import com.example.entity.CompetitionDetailsResponse
import com.example.entity.TeamsResponse
import com.example.feature.core.BaseViewModel
import com.example.ui.competitionDetails.contract.CompetitionDetailsContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val competitionDetailsUseCase: CompetitionDetailsUseCase,
    private val competitionTeamsUseCase: CompetitionTeamsUseCase,
) : BaseViewModel<CompetitionDetailsContract.Event, CompetitionDetailsContract.State, CompetitionDetailsContract.Effect>() {



    override fun createInitialState(): CompetitionDetailsContract.State {
        return CompetitionDetailsContract.State(
            competitionDetailsState = CompetitionDetailsContract.CompetitionDetailsState.Idle,
            competitionTeamsState = CompetitionDetailsContract.CompetitionTeamsState.Idle
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

    private fun <T> combineMerge(vararg flows: Flow<T>) = flow {
        viewModelScope.launch {
            flows.forEach {
                launch {
                    it.onStart { emit(Resource.Loading) }
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
                            is Resource.Success<*> -> {
                                // Set State
                                setState {
                                    copy(
                                        competitionDetailsState = CompetitionDetailsContract.CompetitionDetailsState.Success(
                                            result = it.data as CompetitionDetailsResponse
                                        )
                                    )
                                }

                                setState {
                                    copy(
                                        competitionTeamsState = CompetitionDetailsContract.CompetitionTeamsState.Success(
                                            result = it.data as TeamsResponse
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


                  /*  it.collect {
                        emit(it)
                    }*/
                }
            }
        }
    }

    private  fun getCompetitionDetails(id:Int) {
        viewModelScope.launch {
            val flow1 = competitionDetailsUseCase.execute(id)
                .onStart { emit(Resource.Loading) }
            val flow2 = competitionTeamsUseCase.execute(id)
                .onStart { emit(Resource.Loading) }
            flow1.combine(flow2) { f1, f2 ->
                when (f1) {
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
                                    result = f1.data
                                )
                            )
                        }
                    }
                    is Resource.Error -> {
                        // Set Effect
                        setEffect { CompetitionDetailsContract.Effect.ShowError(message = f1.message) }
                    }
                }
                when (f2) {
                    is Resource.Loading -> {
                        // Set State
                        setState { copy(competitionTeamsState = CompetitionDetailsContract.CompetitionTeamsState.Loading) }
                    }
                    is Resource.Empty -> {
                        // Set State
                        setState { copy(competitionTeamsState = CompetitionDetailsContract.CompetitionTeamsState.Idle) }
                    }
                    is Resource.Success -> {
                        // Set State
                        setState {
                            copy(
                                competitionTeamsState = CompetitionDetailsContract.CompetitionTeamsState.Success(
                                    result = f2.data
                                )
                            )
                        }
                    }
                    is Resource.Error -> {
                        // Set Effect
                        setEffect { CompetitionDetailsContract.Effect.ShowError(message = f2.message) }
                    }
                }
            }.collect { it }
        }


        //combineMerge(competitionDetailsUseCase.execute(id),competitionTeamsUseCase.execute(id))

    /*    viewModelScope.launch {
            competitionDetailsUseCase.execute(id)
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
        }*/
    }

    private fun getCompetitionTeams(id:Int) {
        viewModelScope.launch {
            competitionTeamsUseCase.execute(id)
                .onStart { emit(Resource.Loading) }
                .collect {

                    when (it) {
                        is Resource.Loading -> {
                            // Set State
                            setState { copy(competitionTeamsState = CompetitionDetailsContract.CompetitionTeamsState.Loading) }
                        }
                        is Resource.Empty -> {
                            // Set State
                            setState { copy(competitionTeamsState = CompetitionDetailsContract.CompetitionTeamsState.Idle) }
                        }
                        is Resource.Success -> {
                            // Set State
                            setState {
                                copy(
                                    competitionTeamsState = CompetitionDetailsContract.CompetitionTeamsState.Success(
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