package br.com.israelermel.feature_top_ranked.adapters

import android.view.View
import androidx.paging.LoadState

fun View.setVisibilityByState(state: LoadState) {
    when(state) {
        is LoadState.Loading -> this.visibility = View.VISIBLE
        else -> this.visibility = View.GONE
    }
}

fun View.setVisible(isVisible: Boolean) {
    if(isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}