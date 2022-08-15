package com.example.ui.competitionDetails.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.Competition
import com.example.entity.Team
import com.example.feature.databinding.ItemCompetetionBinding
import com.example.feature.databinding.ItemTeamsBinding
import com.example.feature.ui.search_result.ui.CompetetionViewHolder

/**
 * Adapter class for RecyclerView
 */
class TeamsAdapter constructor(
    private val result: List<Team>,
    private val clickFunc : ((Team) -> Unit)? = null
) : RecyclerView.Adapter<TeamsViewHolder>() {

    val data:MutableList<Team> = result.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val binding = ItemTeamsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return TeamsViewHolder(binding = binding, click = clickFunc)

    }

    fun appendList(list:List<Team>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.doBindings((data[position]))
        holder.bind()
    }

    override fun getItemCount(): Int {
        return data.size
    }


}

class CompetetionItemDiffUtil : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem == newItem
    }
}