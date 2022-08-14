package com.example.ui.competitionsResult.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.feature.R
import com.example.feature.core.BaseFragment
import com.example.feature.databinding.FragmentCompetitionResultBinding
import com.example.ui.competitionsResult.contract.CompetitionListContract
import com.example.ui.competitionsResult.vm.CompetitionListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitionResultFragment : BaseFragment<FragmentCompetitionResultBinding>() {


    private val viewModel: CompetitionListViewModel by viewModels()

    private val adapter: CompetetionAdapter by lazy {
        CompetetionAdapter(emptyList()) { item ->
            viewModel.setEvent(CompetitionListContract.Event.OnCompetitionItemClicked(item.id))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCompetitionResultBinding
        get() = FragmentCompetitionResultBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        binding.rvCompetitions.adapter = adapter
        initObservers()
    }


    /**
     * Initialize Observers
     */
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (val state = it.competitionListState) {
                    is CompetitionListContract.CompetitionListState.Idle -> {
                        binding.loadingPb.isVisible = false
                    }
                    is CompetitionListContract.CompetitionListState.Loading -> {
                        binding.loadingPb.isVisible = true
                    }
                    is CompetitionListContract.CompetitionListState.Success -> {
                        val data = state.result
                        adapter.appendList(data.competitions)
                        binding.loadingPb.isVisible = false
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is CompetitionListContract.Effect.ShowError -> {
                        val msg = it.message
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                    is CompetitionListContract.Effect.Navigate -> {

                        val action =
                            CompetitionResultFragmentDirections.actionCompetitionResultFragmentToCompetitionDetailsFragment(
                                it.id.toString()
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}