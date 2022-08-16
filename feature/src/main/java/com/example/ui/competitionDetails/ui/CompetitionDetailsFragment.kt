package com.example.ui.competitionDetails.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.entity.CompetitionDetailsResponse
import com.example.entity.Season
import com.example.entity.Team
import com.example.entity.TeamsResponse
import com.example.feature.R
import com.example.feature.core.BaseFragment
import com.example.feature.databinding.FragmentCompetitionDetailsBinding
import com.example.ui.competitionDetails.contract.CompetitionDetailsContract
import com.example.ui.competitionDetails.vm.CompetitionDetailsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompetitionDetailsFragment : BaseFragment<FragmentCompetitionDetailsBinding>() {

    private val viewModel: CompetitionDetailsViewModel by viewModels()
    private val args: CompetitionDetailsFragmentArgs by navArgs()
    private lateinit var competetionData: CompetitionDetailsResponse
    private lateinit var teamsData: TeamsResponse

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
       val job = viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.uiState.collect {
                        when (val state = it.competitionDetailsState) {
                            is CompetitionDetailsContract.CompetitionDetailsState.Idle -> {
                                binding.loadingPb.isVisible = false
                            }
                            is CompetitionDetailsContract.CompetitionDetailsState.Loading -> {
                                binding.loadingPb.isVisible = true
                            }
                            is CompetitionDetailsContract.CompetitionDetailsState.Success -> {
                                competetionData = state.result
                                bindDataToView(competetionData)
                                binding.loadingPb.isVisible = false
                            }
                        }
                    }
                }

                launch {
                    viewModel.uiState.collect {
                        when (val state = it.competitionTeamsState) {
                            is CompetitionDetailsContract.CompetitionTeamsState.Idle -> {
                                binding.loadingPb.isVisible = false
                            }
                            is CompetitionDetailsContract.CompetitionTeamsState.Loading -> {
                                binding.loadingPb.isVisible = true
                            }
                            is CompetitionDetailsContract.CompetitionTeamsState.Success -> {
                                teamsData = state.result
                                binding.loadingPb.isVisible = false
                                initTabs(teamsData.teams,competetionData.seasons)
                            }
                        }
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is CompetitionDetailsContract.Effect.ShowError -> {
                        binding.loadingPb.isVisible = false
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

    private fun initTabs(teams: List<Team>, season: List<Season>) {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.seasons)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.teams)))
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = TabsAdapter(requireContext(), activity?.supportFragmentManager!!,
            binding.tabLayout.tabCount,teams,season)
        binding.viewPager.adapter = adapter
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}