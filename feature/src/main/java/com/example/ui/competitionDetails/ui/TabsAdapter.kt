package com.example.ui.competitionDetails.ui

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.entity.Season
import com.example.entity.Team

// TODO: 15/08/2022 implement viewPager2
internal class TabsAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
     data: List<Team>,
     season: List<Season>
) : FragmentPagerAdapter(fm) {
    val mTeams: List<Team> = data
    val mSeason: List<Season> = season
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SeasonsFragment(mSeason)
            }
            1 -> {
                TeamsFragment(mTeams)
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}