package com.onedev.pokedex.ui.fragment.home.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.onedev.pokedex.R
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.databinding.FragmentHomeBinding
import com.onedev.pokedex.ui.fragment.home.adapter.PokemonAdapter
import com.onedev.pokedex.ui.fragment.home.viewmodel.HomeViewModel
import com.onedev.pokedex.utils.ExtSupport.gone
import com.onedev.pokedex.utils.ExtSupport.visible
import org.koin.android.viewmodel.ext.android.viewModel
import com.onedev.pokedex.utils.ExtSupport.showNavBar
import com.miguelcatalan.materialsearchview.MaterialSearchView


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var pokemonAdapter: PokemonAdapter

    companion object {
        private const val LIMIT = 1118
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateToolbar()
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

        binding?.searchView?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isNotEmpty()) loadPokemonByName(query) else loadPokemon()
                return true
            }
        })

        loadPokemon()
    }

    private fun populateToolbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu)

        val item = menu.findItem(R.id.menu_search)
        binding?.searchView?.setMenuItem(item)
    }

    private fun loadPokemon() {
        binding?.apply {
            homeViewModel.getPokemon(LIMIT).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {
                            layoutPokemonNotFound.imgNoPokemon.gone()
                            layoutPokemonNotFound.tvNoPokemon.gone()
                            shimmerViewContainer.startShimmer()
                            shimmerViewContainer.visible()
                            rvPokemon.gone()
                        }
                        is Resource.Success -> {
                            response.data?.let { listPokemon ->
                                pokemonAdapter.submitList(listPokemon)
                                layoutPokemonNotFound.imgNoPokemon.gone()
                                layoutPokemonNotFound.tvNoPokemon.gone()
                                shimmerViewContainer.stopShimmer()
                                shimmerViewContainer.gone()
                                rvPokemon.visible()
                            }
                        }
                        is Resource.Error -> {
                            layoutPokemonNotFound.imgNoPokemon.visible()
                            layoutPokemonNotFound.tvNoPokemon.visible()
                            shimmerViewContainer.stopShimmer()
                            shimmerViewContainer.gone()
                            rvPokemon.gone()
                        }
                    }
                }
            }
        }
    }

    private fun loadPokemonByName(pokemonName: String) {
        binding?.apply {
            layoutPokemonNotFound.imgNoPokemon.gone()
            layoutPokemonNotFound.tvNoPokemon.gone()
            shimmerViewContainer.startShimmer()
            shimmerViewContainer.visible()
            rvPokemon.gone()

            homeViewModel.getPokemonByName(pokemonName).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    if (response.isNotEmpty()) {
                        pokemonAdapter.submitList(response)
                        layoutPokemonNotFound.imgNoPokemon.gone()
                        layoutPokemonNotFound.tvNoPokemon.gone()
                        shimmerViewContainer.stopShimmer()
                        shimmerViewContainer.gone()
                        rvPokemon.visible()
                    } else {
                        layoutPokemonNotFound.imgNoPokemon.visible()
                        layoutPokemonNotFound.tvNoPokemon.visible()
                        shimmerViewContainer.stopShimmer()
                        shimmerViewContainer.gone()
                        rvPokemon.gone()
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