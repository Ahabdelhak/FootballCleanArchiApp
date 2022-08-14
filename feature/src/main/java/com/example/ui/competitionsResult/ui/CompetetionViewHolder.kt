package com.example.feature.ui.search_result.ui

import com.example.entity.Competition
import com.example.feature.core.BaseViewHolder
import com.example.feature.databinding.ItemCompetetionBinding

/**
 * ViewHolder class for Item
 */
class CompetetionViewHolder constructor(
    private val binding : ItemCompetetionBinding,
    private val click : ((Competition) -> Unit)? = null
) : BaseViewHolder<Competition, ItemCompetetionBinding>(binding) {


    init {
        binding.root.setOnClickListener {
            click?.invoke(getRowItem()!!)
        }
    }

    override fun bind() {


        getRowItem()?.let {

            binding.tvLeagueName.text = it.name
            binding.tvLeagueCode.text = it.code
            binding.tvStartDate.text = it.currentSeason?.startDate
            binding.tvEndDate.text = it.currentSeason?.endDate

            binding.executePendingBindings()

        }
    }
}