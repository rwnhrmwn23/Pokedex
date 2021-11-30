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
            detailViewModel.getPokemonDetail(id).observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            response.data?.let { dataPokemon ->
                                val dataBaseStat = dataPokemon.stats
                                val dataStatHp = dataBaseStat[0].base_stat.toFloat()
                                val dataStatAtk = dataBaseStat[1].base_stat.toFloat()
                                val dataStatDef = dataBaseStat[2].base_stat.toFloat()
                                val dataStatSAtk = dataBaseStat[3].base_stat.toFloat()
                                val dataStatSDef = dataBaseStat[4].base_stat.toFloat()
                                val dataStatSpd = dataBaseStat[5].base_stat.toFloat()

                                statHp.progress = dataStatHp
                                statHp.labelText = getString(R.string.stat_base, dataStatHp.toInt())

                                statAttack.progress = dataStatAtk
                                statAttack.labelText = getString(R.string.stat_base, dataStatAtk.toInt())

                                statDef.progress = dataStatDef
                                statDef.labelText = getString(R.string.stat_base, dataStatDef.toInt())

                                statSAttack.progress = dataStatSAtk
                                statSAttack.labelText = getString(R.string.stat_base, dataStatSAtk.toInt())

                                statSDef.progress = dataStatSDef
                                statSDef.labelText = getString(R.string.stat_base, dataStatSDef.toInt())

                                statSpd.progress = dataStatSpd
                                statSpd.labelText = getString(R.string.stat_base, dataStatSpd.toInt())
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