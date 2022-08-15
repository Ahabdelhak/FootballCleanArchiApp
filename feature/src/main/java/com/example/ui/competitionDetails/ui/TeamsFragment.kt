package com.example.ui.competitionDetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.entity.Team
import com.example.feature.core.BaseFragment
import com.example.feature.databinding.FragmentTeamsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsFragment(var data: List<Team>) : BaseFragment<FragmentTeamsBinding>() {

    private val adapter: TeamsAdapter by lazy {
        TeamsAdapter(emptyList()) { item ->

        }
    }

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTeamsBinding
        get() = FragmentTeamsBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        binding.rvTeams.adapter = adapter
        adapter.appendList(data)
    }
}