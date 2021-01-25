package com.wooz.countries.ui.countries

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.wooz.countries.R
import com.wooz.countries.domain.entity.Country
import java.util.*

/**
 * Created by wooz on 12/28/20
 */
@BindingAdapter("name")
fun TextView.setName(item: Country?) {
    item?.let {
        text = String.format(Locale.getDefault(), "%s", item.name)
    }
}

@BindingAdapter("capital")
fun TextView.setCapital(item: Country?) {
    item?.let {
        text = item.capital
    }
}

@BindingAdapter("favorite")
fun ImageView.setFavoriteImage(item: Country?) {
    item?.let {
        setImageResource(
            if (item.favorite) {
                R.drawable.ic_baseline_star_24
            } else {
                R.drawable.ic_baseline_star_border_24
            }
        )
    }
}

@BindingAdapter("flag")
fun ImageView.setFlagImage(item: Country?){
    item?.let {
        val requestBuilder = GlideToVectorYou
            .init()
            .with(context)
            .requestBuilder

        requestBuilder
            .load(Uri.parse(it.flag))
            .into(this)
    }
}