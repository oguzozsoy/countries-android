package com.wooz.countries.ui.details

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.wooz.countries.domain.entity.CountryDetails

/**
 * Created by wooz on 12/30/20
 */
@BindingAdapter("flag")
fun ImageView.setFlagImage(item: CountryDetails?) {
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