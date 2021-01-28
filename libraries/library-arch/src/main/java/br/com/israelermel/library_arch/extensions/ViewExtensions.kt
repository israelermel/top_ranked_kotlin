package br.com.israelermel.library_arch.extensions

import android.view.View



fun View.setVisibilityState(state: VisibilityState) {
    when(state) {
        VisibilityState.GONE -> this.visibility = View.GONE
        VisibilityState.VISIBLE -> this.visibility = View.VISIBLE
    }
}
