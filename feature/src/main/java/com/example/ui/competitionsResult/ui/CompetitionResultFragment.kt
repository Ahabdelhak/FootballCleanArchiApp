package com.example.ui.competitionsResult.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.feature.R
import com.example.ui.competitionsResult.contract.CompetitionListContract
import com.example.ui.competitionsResult.vm.CompetitionListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitionResultFragment : Fragment() {


    private val viewModel: CompetitionListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initObservers()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_competition_result, container, false)
    }


    /**
     * Initialize Observers
     */
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (val state = it.competitionListState) {
                    is CompetitionListContract.CompetitionListState.Idle -> {
                    }
                    is CompetitionListContract.CompetitionListState.Loading -> {

                    }
                    is CompetitionListContract.CompetitionListState.Success -> {
                        val data = state.result
                        Toast.makeText(requireContext(), data.competitions.get(0).name, Toast.LENGTH_SHORT).show()
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
                }
            }
        }
    }
}