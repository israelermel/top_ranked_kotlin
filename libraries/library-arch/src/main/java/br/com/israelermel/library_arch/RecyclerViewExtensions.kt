package br.com.israelermel.library_arch

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.configSimpleVerticalDividerDecoration() {
    val decoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
    this.addItemDecoration(decoration)
}