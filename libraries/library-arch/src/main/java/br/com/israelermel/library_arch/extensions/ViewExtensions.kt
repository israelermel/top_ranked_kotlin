package br.com.israelermel.library_arch.extensions

import android.view.View
import br.com.israelermel.library_arch.viewstates.VisibilityState


fun View.setVisibilityState(state: VisibilityState) {
    when(state) {
        VisibilityState.GONE -> this.visibility = View.GONE
        VisibilityState.VISIBLE -> this.visibility = View.VISIBLE
    }
}
