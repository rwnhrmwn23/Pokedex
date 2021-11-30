package com.onedev.pokedex.ui.fragment.detail.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.gson.Gson
import com.onedev.pokedex.R
import com.onedev.pokedex.core.data.source.Resource
import com.onedev.pokedex.core.domain.model.Pokemon
import com.onedev.pokedex.databinding.FragmentDetailBinding
import com.onedev.pokedex.ui.fragment.detail.adapter.PokemonTypeAdapter
import com.onedev.pokedex.ui.fragment.detail.viewmodel.DetailViewModel
import com.onedev.pokedex.ui.fragment.home.viewmodel.HomeViewModel
import com.onedev.pokedex.utils.ExtSupport.gone
import com.onedev.pokedex.utils.ExtSupport.hideNavBar
import com.onedev.pokedex.utils.ExtSupport.visible
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateToolbar()
        populateView(args.pokemon)

        binding?.fabFavorite?.setOnClickListener(this)
    }

    private fun populateToolbar() {
        requireActivity().hideNavBar()
        (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
    }

    private fun populateView(data: Pokemon) {
        binding?.apply {
            val url = data.pokemonImage
            Glide.with(requireContext())
                .load(url)
                .error(R.drawable.pokeball)
                .listener(
                    GlidePalette.with(url)
                        .use(BitmapPalette.Profile.MUTED_LIGHT)
                        .intoCallBack { palette ->
                            val rgb = palette?.dominantSwatch?.rgb
                            if (rgb != null) {
                                appbar.setBackgroundColor(rgb)
                                collPokemonName.setContentScrimColor(rgb)
                                requireActivity().window.statusBarColor = rgb
                            }
                        }.crossfade(true)
                ).into(imgPokemon)

            tvPokemonId.text = getString(R.string.pokemon_id, data.id)
            collPokemonName.title = data.pokemonName

            loadDetailPokemon(data.id)
        }
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

                                tvHeight.text = dataPokemon.height.toString()
                                tvWeight.text = dataPokemon.weight.toString()


                                val pokemonTypeAdapter = PokemonTypeAdapter()
                                rvType.apply {
                                    setHasFixedSize(true)
                                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                                    adapter = pokemonTypeAdapter
                                }
                                pokemonTypeAdapter.setTypePokemon(dataPokemon.types)

                                Log.d("dataPokemon", "loadDetailPokemon: ${Gson().toJson(dataPokemon)}")
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

    override fun onClick(v: View?) {
        when (v) {
            binding?.fabFavorite -> {
                binding?.apply {
                    fabFavorite.setImageResource(R.drawable.pokemon_favorite)
                }
            }
        }
    }
}