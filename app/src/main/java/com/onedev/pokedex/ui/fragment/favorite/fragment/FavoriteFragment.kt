package com.onedev.pokedex.ui.fragment.favorite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.onedev.pokedex.R
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.databinding.FragmentFavoriteBinding
import com.onedev.pokedex.ui.fragment.favorite.viewmodel.FavoriteViewModel
import com.onedev.pokedex.ui.fragment.home.adapter.PokemonAdapter
import com.onedev.pokedex.ui.fragment.home.fragment.HomeFragment
import com.onedev.pokedex.ui.fragment.home.fragment.HomeFragmentDirections
import com.onedev.pokedex.utils.ExtSupport.gone
import com.onedev.pokedex.utils.ExtSupport.showNavBar
import com.onedev.pokedex.utils.ExtSupport.visible
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().showNavBar()

        pokemonAdapter = PokemonAdapter()

        binding?.rvPokemonFavorite?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = pokemonAdapter
        }

        pokemonAdapter.onItemClick = {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }

        loadPokemonFavorite()

    }

    private fun loadPokemonFavorite() {
        binding?.apply {
            layoutPokemonNotFound.imgNoPokemon.gone()
            layoutPokemonNotFound.tvNoPokemon.gone()
            shimmerViewContainer.startShimmer()
            shimmerViewContainer.visible()
            rvPokemonFavorite.gone()

            favoriteViewModel.getPokemonFavorite().observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    if (response.isNotEmpty()) {
                        pokemonAdapter.submitList(response)
                        layoutPokemonNotFound.imgNoPokemon.gone()
                        layoutPokemonNotFound.tvNoPokemon.gone()
                        shimmerViewContainer.stopShimmer()
                        shimmerViewContainer.gone()
                        rvPokemonFavorite.visible()
                    } else {
                        layoutPokemonNotFound.imgNoPokemon.visible()
                        layoutPokemonNotFound.tvNoPokemon.visible()
                        shimmerViewContainer.stopShimmer()
                        shimmerViewContainer.gone()
                        rvPokemonFavorite.gone()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.shimmerViewContainer?.startShimmer()
    }

    override fun onPause() {
        binding?.shimmerViewContainer?.stopShimmer()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}