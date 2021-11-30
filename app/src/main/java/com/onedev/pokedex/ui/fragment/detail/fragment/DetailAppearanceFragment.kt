package com.onedev.pokedex.ui.fragment.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.databinding.FragmentDetailAppearanceBinding
import com.onedev.pokedex.ui.fragment.detail.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailAppearanceFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModel()
    private var _binding: FragmentDetailAppearanceBinding? = null
    private val binding get() = _binding

    companion object {
        fun newInstance(id: String?) = DetailAppearanceFragment().apply {
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
        _binding = FragmentDetailAppearanceBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = checkNotNull(arguments?.getString("id")).toInt()

        loadDetailPokemon(id)
    }

    private fun loadDetailPokemon(id: Int) {
        binding?.apply {
            detailViewModel.getPokemonById(id).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            response.data?.let { dataPokemon ->
                                tvHeight.text = dataPokemon.pokemonHeight.toString()
                                tvWeight.text = dataPokemon.pokemonWeight.toString()
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