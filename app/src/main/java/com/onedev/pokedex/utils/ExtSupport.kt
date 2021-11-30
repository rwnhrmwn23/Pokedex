package com.onedev.pokedex.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onedev.pokedex.R

object ExtSupport {
    fun Activity.showNavBar() {
        val navBar: BottomNavigationView? = this.findViewById(R.id.bottom_nav_view)
        navBar?.visible()
    }

    fun Activity.hideNavBar() {
        val navBar: BottomNavigationView? = this.findViewById(R.id.bottom_nav_view)
        navBar?.gone()
    }

    fun ImageView.loadImage(url: String, cardView: CardView) {
        Glide.with(context)
            .load(url)
            .error(R.drawable.pokeball)
            .listener(
                GlidePalette.with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            cardView.setCardBackgroundColor(rgb)
                        }
                    }.crossfade(true)
            ).into(this)
    }

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }
}