package com.example.ui.competitionDetails.ui

import com.example.entity.Competition
import com.example.entity.Season
import com.example.feature.core.BaseViewHolder
import com.example.feature.databinding.ItemCompetetionBinding
import com.example.feature.databinding.ItemSeasonBinding

/**
 * ViewHolder class for Item
 */
class SeasonViewHolder constructor(
    private val binding : ItemSeasonBinding,
    private val click : ((Season) -> Unit)? = null
) : BaseViewHolder<Season, ItemSeasonBinding>(binding) {


    init {
        binding.root.setOnClickListener {
            click?.invoke(getRowItem()!!)
        }
    }

    override fun bind() {


        getRowItem()?.let {

            binding.tvSeasonStart.text = "Season Start:${it.startDate}"
            binding.tvSeasonEnd.text = "Season End:${it.endDate}"
            binding.executePendingBindings()

        }
    }
}