package com.onedev.pokedex.ui.fragment.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.databinding.FragmentHomeBinding
import com.onedev.pokedex.ui.fragment.home.adapter.PokemonAdapter
import com.onedev.pokedex.ui.fragment.home.viewmodel.HomeViewModel
import com.onedev.pokedex.utils.ExtSupport.gone
import com.onedev.pokedex.utils.ExtSupport.visible
import org.koin.android.viewmodel.ext.android.viewModel
import com.onedev.pokedex.utils.ExtSupport.showNavBar

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModel()
    private var limit = 1118
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().showNavBar()

        pokemonAdapter = PokemonAdapter()

        binding?.rvPokemon?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = pokemonAdapter
        }

        pokemonAdapter.onItemClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }

        loadPokemon()
    }

    private fun loadPokemon() {
        binding?.apply {
            homeViewModel.getPokemon(limit).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {
                            progressCircular.visible()
                        }
                        is Resource.Success -> {
                            response.data?.let { listPokemon ->
                                pokemonAdapter.submitList(listPokemon)
                                progressCircular.gone()
                            }
                        }
                        is Resource.Error -> {
//                            shimmerViewContainer.stopShimmer()
//                            shimmerViewContainer.gone()
//                            rvBumdes.gone()
//                            imgNoBumdes.visibility()
//                            tvNoBumdes.visibility()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}