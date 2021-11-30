package com.onedev.pokedex.ui.fragment.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onedev.pokedex.core.data.source.remote.response.DataPokemonType
import com.onedev.pokedex.databinding.LayoutListPokemonTypeBinding

class PokemonTypeAdapter : RecyclerView.Adapter<PokemonTypeAdapter.HomeViewHolder>() {

    private val types = ArrayList<DataPokemonType>()

    @SuppressLint("NotifyDataSetChanged")
    fun setTypePokemon(listType: List<DataPokemonType>?) {
        if (listType == null) return
        types.clear()
        types.addAll(listType)
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: LayoutListPokemonTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataPokemonType) {
            binding.apply {
                tvPokemonType.text = data.type.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = LayoutListPokemonTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(types[position])
    }

    override fun getItemCount(): Int = types.size

}