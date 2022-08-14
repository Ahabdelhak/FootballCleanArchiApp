package com.example.ui.competitionDetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.entity.CompetitionDetailsResponse
import com.example.feature.core.BaseFragment
import com.example.feature.databinding.FragmentCompetitionDetailsBinding
import com.example.ui.competitionDetails.contract.CompetitionDetailsContract
import com.example.ui.competitionDetails.vm.CompetitionDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitionDetailsFragment : BaseFragment<FragmentCompetitionDetailsBinding>() {

    private val viewModel: CompetitionDetailsViewModel by viewModels()
    private val args: CompetitionDetailsFragmentArgs by navArgs()


    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCompetitionDetailsBinding
        get() = FragmentCompetitionDetailsBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {

        viewModel.setEvent(CompetitionDetailsContract.Event.GetCompetitionLDetails(args.id.toInt()))
        initObservers()
    }


    /**
     * Initialize Observers
     */
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (val state = it.competitionDetailsState) {
                    is CompetitionDetailsContract.CompetitionDetailsState.Idle -> {
                        binding.loadingPb.isVisible = false
                    }
                    is CompetitionDetailsContract.CompetitionDetailsState.Loading -> {
                        binding.loadingPb.isVisible = true
                    }
                    is CompetitionDetailsContract.CompetitionDetailsState.Success -> {
                        val data = state.result
                        bindDataToView(data)
                        binding.loadingPb.isVisible = false
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is CompetitionDetailsContract.Effect.ShowError -> {
                        val msg = it.message
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun bindDataToView(data: CompetitionDetailsResponse) {
        binding.tvCompetitionName.text = data.name
        binding.tvStartDate.text = data.currentSeason.startDate
        binding.tvEndDate.text = data.currentSeason.endDate
    }


}