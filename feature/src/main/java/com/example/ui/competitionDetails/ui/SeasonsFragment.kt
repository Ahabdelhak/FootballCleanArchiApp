package com.example.ui.competitionDetails.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.entity.Season
import com.example.feature.core.BaseFragment
import com.example.feature.databinding.FragmentSeasonsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeasonsFragment(var season: List<Season>) : BaseFragment<FragmentSeasonsBinding>() {

    private val adapter: SeasonAdapter by lazy {
        SeasonAdapter(emptyList()) { _ ->

        }
    }

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSeasonsBinding
        get() = FragmentSeasonsBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        binding.rvSeasons.adapter = adapter
        adapter.appendList(season)
    }
}