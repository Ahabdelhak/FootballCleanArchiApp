package com.example.ui.competitionDetails.ui

import com.example.entity.Competition
import com.example.entity.Team
import com.example.entity.TeamsResponse
import com.example.feature.core.BaseViewHolder
import com.example.feature.databinding.ItemCompetetionBinding
import com.example.feature.databinding.ItemTeamsBinding

/**
 * ViewHolder class for Item
 */
class TeamsViewHolder constructor(
    private val binding : ItemTeamsBinding,
    private val click : ((Team) -> Unit)? = null
) : BaseViewHolder<Team, ItemTeamsBinding>(binding) {


    init {
        binding.root.setOnClickListener {
            click?.invoke(getRowItem()!!)
        }
    }

    override fun bind() {


        getRowItem()?.let {

            binding.tvLTeamName.text = it.name

            binding.executePendingBindings()

        }
    }
}