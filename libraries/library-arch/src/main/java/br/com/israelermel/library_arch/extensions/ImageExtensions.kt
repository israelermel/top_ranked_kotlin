package br.com.israelermel.library_arch.extensions

import android.widget.ImageView
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation

fun ImageView.setRoundedImage(url: String?) {
    this.load(url) {
        crossfade(true)
        transformations(CircleCropTransformation())
        diskCachePolicy(CachePolicy.ENABLED)
    }
}