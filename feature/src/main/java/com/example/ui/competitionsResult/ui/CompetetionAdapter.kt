package com.example.ui.competitionsResult.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.Competition
import com.example.feature.databinding.ItemCompetetionBinding
import com.example.feature.ui.search_result.ui.CompetetionViewHolder

/**
 * Adapter class for RecyclerView
 */
class CompetetionAdapter constructor(
    private val result: List<Competition>,
    private val clickFunc : ((Competition) -> Unit)? = null
) : RecyclerView.Adapter<CompetetionViewHolder>() {

    val data:MutableList<Competition> = result.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetetionViewHolder {
        val binding = ItemCompetetionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return CompetetionViewHolder(binding = binding, click = clickFunc)

    }

    fun appendList(list:List<Competition>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CompetetionViewHolder, position: Int) {
        holder.doBindings((data[position]))
        holder.bind()
    }

    override fun getItemCount(): Int {
        return data.size
    }


}

class CompetetionItemDiffUtil : DiffUtil.ItemCallback<Competition>() {
    override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {
        return oldItem == newItem
    }
}