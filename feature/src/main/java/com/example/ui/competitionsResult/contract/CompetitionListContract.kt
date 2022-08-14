package com.example.ui.competitionsResult.contract


import com.example.entity.CompetitionResponse
import com.example.feature.core.UiEffect
import com.example.feature.core.UiEvent
import com.example.feature.core.UiState

/**
 * Contract of Main Screen
 */
class CompetitionListContract {

    sealed class Event : UiEvent {
        object GetCompetitionList : Event()
        data class OnCompetitionItemClicked(val id : Int) : Event()
    }

    data class State(
        val competitionListState: CompetitionListState
    ) : UiState

    sealed class CompetitionListState {
        object Idle : CompetitionListState()
        object Loading : CompetitionListState()
        data class Success(val result : CompetitionResponse) : CompetitionListState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message : String?) : Effect()
        data class Navigate(val id : Int) : Effect()
    }

}