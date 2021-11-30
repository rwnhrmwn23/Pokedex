package com.onedev.pokedex.ui.fragment.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onedev.pokedex.core.domain.model.Pokemon
import com.onedev.pokedex.databinding.LayoutListPokemonBinding
import com.onedev.pokedex.utils.ExtSupport.loadImage

class PokemonFavoriteAdapter : PagedListAdapter<Pokemon, PokemonFavoriteAdapter.PokemonViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Pokemon) -> Unit)? = null

    inner class PokemonViewHolder(private val binding: LayoutListPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Pokemon) {
            binding.apply {
                tvPokemon.text = data.pokemonName
                imgPokemon.loadImage(data.pokemonImage, cardViewLayout)

                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = LayoutListPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)
        if (pokemon != null) {
            holder.bind(pokemon)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }
}