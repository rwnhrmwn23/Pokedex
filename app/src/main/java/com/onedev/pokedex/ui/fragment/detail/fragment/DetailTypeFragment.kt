package com.onedev.pokedex.ui.fragment.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.pokedex.R
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.databinding.FragmentDetailBaseStatsBinding
import com.onedev.pokedex.databinding.FragmentDetailTypeBinding
import com.onedev.pokedex.ui.fragment.detail.adapter.PokemonTypeAdapter
import com.onedev.pokedex.ui.fragment.detail.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailTypeFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModel()
    private var _binding: FragmentDetailTypeBinding? = null
    private val binding get() = _binding

    companion object {
        fun newInstance(id: String?) = DetailTypeFragment().apply {
            arguments = Bundle().apply {
                putString("id", id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailTypeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = checkNotNull(arguments?.getString("id")).toInt()

        loadDetailPokemon(id)
    }

    private fun loadDetailPokemon(id: Int) {
        binding?.apply {
            detailViewModel.getPokemonDetail(id).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            response.data?.let { dataPokemon ->
                                val pokemonTypeAdapter = PokemonTypeAdapter()
                                rvType.apply {
                                    setHasFixedSize(true)
                                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                                    adapter = pokemonTypeAdapter
                                }
                                pokemonTypeAdapter.setTypePokemon(dataPokemon.types)
                            }
                        }
                        is Resource.Error -> {

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