package com.example.pizza.ui.summary

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza.data.remote.network.dto.FlavorDTO
import com.example.pizza.databinding.ItemFlavorBinding

class SummaryAdapter constructor() : RecyclerView.Adapter<SummaryAdapter.FlavorViewHolder>() {

    val differ = AsyncListDiffer(this, SummaryDiffCallback())

    inner class FlavorViewHolder(private val binding: ItemFlavorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(flavor: FlavorDTO) {
            binding.flavorName.text = flavor.name
            binding.flavorPrice.text = flavor.price.toString()
            binding.btnAdd.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlavorViewHolder {
        val binding = ItemFlavorBinding.inflate(LayoutInflater.from(parent.context))
        return FlavorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlavorViewHolder, position: Int) {
        val flavor = differ.currentList[position]
        holder.bind(flavor)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

class SummaryDiffCallback : DiffUtil.ItemCallback<FlavorDTO>() {
    override fun areItemsTheSame(oldItem: FlavorDTO, newItem: FlavorDTO): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: FlavorDTO, newItem: FlavorDTO): Boolean {
        return oldItem == newItem
    }
}