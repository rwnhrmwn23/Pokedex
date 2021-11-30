package com.onedev.pokedex.ui.fragment.detail.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.onedev.pokedex.ui.fragment.detail.fragment.DetailAppearanceFragment
import com.onedev.pokedex.ui.fragment.detail.fragment.DetailBaseStatsFragment
import com.onedev.pokedex.ui.fragment.detail.fragment.DetailTypeFragment

class PokemonDetailViewPagerAdapter(activity: AppCompatActivity, private val pokemonId: String) :
    FragmentStateAdapter(activity) {

    data class Page(val fragment: () -> Fragment)

    @Suppress("MoveLambdaOutsideParentheses")
    private val pages = listOf(
        Page({ DetailAppearanceFragment.newInstance(pokemonId) }),
        Page({ DetailTypeFragment.newInstance(pokemonId) }),
        Page({ DetailBaseStatsFragment.newInstance(pokemonId) })
    )

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int) = pages[position].fragment()
}