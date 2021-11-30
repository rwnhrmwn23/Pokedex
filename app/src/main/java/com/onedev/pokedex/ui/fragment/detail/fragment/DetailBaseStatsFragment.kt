package com.onedev.pokedex.ui.fragment.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.onedev.pokedex.R
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.databinding.FragmentDetailBaseStatsBinding
import com.onedev.pokedex.ui.fragment.detail.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailBaseStatsFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModel()
    private var _binding: FragmentDetailBaseStatsBinding? = null
    private val binding get() = _binding

    companion object {
        fun newInstance(id: String?) = DetailBaseStatsFragment().apply {
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
        _binding = FragmentDetailBaseStatsBinding.inflate(inflater, container, false)
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
                                val dataStatHp = dataPokemon.pokemonHp
                                val dataStatAtk = dataPokemon.pokemonAtk
                                val dataStatDef = dataPokemon.pokemonDef
                                val dataStatSAtk = dataPokemon.pokemonSAtk
                                val dataStatSDef = dataPokemon.pokemonSDef
                                val dataStatSpd = dataPokemon.pokemonSpd

                                if (dataStatHp != null) {
                                    statHp.progress = dataStatHp.toFloat()
                                    statHp.labelText = getString(R.string.stat_base, dataStatHp)
                                }

                                if (dataStatAtk != null) {
                                    statAttack.progress = dataStatAtk.toFloat()
                                    statAttack.labelText = getString(R.string.stat_base, dataStatAtk)
                                }

                                if (dataStatDef != null) {
                                    statDef.progress = dataStatDef.toFloat()
                                    statDef.labelText = getString(R.string.stat_base, dataStatDef)
                                }

                                if (dataStatSAtk != null) {
                                    statSAttack.progress = dataStatSAtk.toFloat()
                                    statSAttack.labelText = getString(R.string.stat_base, dataStatSAtk)
                                }

                                if (dataStatSDef != null) {
                                    statSDef.progress = dataStatSDef.toFloat()
                                    statSDef.labelText = getString(R.string.stat_base, dataStatSDef)
                                }

                                if (dataStatSpd != null) {
                                    statSpd.progress = dataStatSpd.toFloat()
                                    statSpd.labelText = getString(R.string.stat_base, dataStatSpd)
                                }
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