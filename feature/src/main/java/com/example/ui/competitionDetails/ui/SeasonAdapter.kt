package com.example.ui.competitionDetails.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.Competition
import com.example.entity.Season
import com.example.feature.databinding.ItemCompetetionBinding
import com.example.feature.databinding.ItemSeasonBinding
import com.example.feature.ui.search_result.ui.CompetetionViewHolder

/**
 * Adapter class for RecyclerView
 */
class SeasonAdapter constructor(
    private val result: List<Season>,
    private val clickFunc : ((Season) -> Unit)? = null
) : RecyclerView.Adapter<SeasonViewHolder>() {

    val data:MutableList<Season> = result.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val binding = ItemSeasonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return SeasonViewHolder(binding = binding, click = clickFunc)

    }

    fun appendList(list:List<Season>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.doBindings((data[position]))
        holder.bind()
    }

    override fun getItemCount(): Int {
        return data.size
    }


}

class SeasonItemDiffUtil : DiffUtil.ItemCallback<Season>() {
    override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
        return oldItem == newItem
    }
}