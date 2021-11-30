package com.onedev.pokedex.ui.fragment.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.tabs.TabLayoutMediator
import com.onedev.pokedex.R
import com.onedev.pokedex.core.domain.model.Pokemon
import com.onedev.pokedex.databinding.FragmentDetailBinding
import com.onedev.pokedex.ui.fragment.detail.adapter.PokemonDetailViewPagerAdapter
import com.onedev.pokedex.ui.fragment.detail.viewmodel.DetailViewModel
import com.onedev.pokedex.utils.ExtSupport.hideNavBar
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModel()
    private var mediator: TabLayoutMediator? = null

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
            tvPokemonId.text = getString(R.string.pokemon_id, data.id)
            collPokemonName.title = data.pokemonName

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

            val viewPagerDetailHeroAdapter = PokemonDetailViewPagerAdapter(activity as AppCompatActivity, args.pokemon.id.toString())
            viewPager.adapter = viewPagerDetailHeroAdapter
            mediator = TabLayoutMediator(tabs, viewPager) {
                    tab, position -> tab.text = resources.getString(TAB_TITLES[position])
            }
            mediator?.attach()

            if (data.pokemonIsFavorite)
                fabFavorite.setImageResource(R.drawable.pokemon_favorite)
            else
                fabFavorite.setImageResource(R.drawable.pokemon_not_favorite)
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

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.appearance,
            R.string.type,
            R.string.base_stats
        )
    }
}