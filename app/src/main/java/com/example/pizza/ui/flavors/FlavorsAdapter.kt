package com.example.pizza.ui.flavors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pizza.R
import com.example.pizza.data.remote.network.dto.FlavorDTO
import com.example.pizza.databinding.ItemFlavorBinding
import com.example.pizza.ui.OnItemClicked
import kotlinx.android.synthetic.main.item_flavor.view.*
import javax.inject.Inject

class FlavorsAdapter constructor(private val onItemClicked: OnItemClicked<FlavorDTO>, private val flavors: List<FlavorDTO>) : RecyclerView.Adapter<FlavorsAdapter.FlavorViewHolder>() {

    val differ = AsyncListDiffer(this, FlavorsDiffCallback())

    inner class FlavorViewHolder(private val binding: ItemFlavorBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(flavor: FlavorDTO) {
            binding.flavorName.text = flavor.name
            binding.flavorPrice.text = flavor.price.toString()
            updateFlavorStatus(binding.root, flavor)

            binding.btnAdd.setOnClickListener {
                onItemClicked.onItemClicked(flavor)
                updateFlavorStatus(it, flavor)
            }
        }

        private fun updateFlavorStatus(view: View, flavor: FlavorDTO) {
            if (flavors.contains(flavor)) {
                view.btnAdd.text = "Cancel"
                view.btnAdd.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.colorRed
                    )
                )
            }
            else {
                view.btnAdd.text = "Add+"
                view.btnAdd.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.colorPrimary
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlavorViewHolder {
        val binding = ItemFlavorBinding.inflate(LayoutInflater.from(parent.context), parent, false )
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

class FlavorsDiffCallback : DiffUtil.ItemCallback<FlavorDTO>() {
    override fun areItemsTheSame(oldItem: FlavorDTO, newItem: FlavorDTO): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: FlavorDTO, newItem: FlavorDTO): Boolean {
        return oldItem == newItem
    }
}