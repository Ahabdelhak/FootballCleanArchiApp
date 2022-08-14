package com.example.ui.competitionDetails.contract

import com.example.entity.CompetitionDetailsResponse
import com.example.entity.TeamsResponse
import com.example.feature.core.UiEffect
import com.example.feature.core.UiEvent
import com.example.feature.core.UiState

class CompetitionDetailsContract {

    sealed class Event : UiEvent {
        data class GetCompetitionLDetails(val id:Int) : Event()
    }

    data class State(
        val competitionDetailsState: CompetitionDetailsState,
        val competitionTeamsState: CompetitionTeamsState
    ) : UiState

    sealed class CompetitionDetailsState {
        object Idle : CompetitionDetailsState()
        object Loading : CompetitionDetailsState()
        data class Success(val result : CompetitionDetailsResponse) : CompetitionDetailsState()
    }

    sealed class CompetitionTeamsState {
        object Idle : CompetitionTeamsState()
        object Loading : CompetitionTeamsState()
        data class Success(val result : TeamsResponse) : CompetitionTeamsState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message : String?) : Effect()
    }

}