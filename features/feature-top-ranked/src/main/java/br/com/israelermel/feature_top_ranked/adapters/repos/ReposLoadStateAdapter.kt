package br.com.israelermel.feature_top_ranked.adapters.repos

import android.util.Log
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ReposLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ReposLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        try {
            holder.bind(loadState)
        } catch (e: Exception) {
            Log.e("israel-error", e.message.orEmpty())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ReposLoadStateViewHolder {
        return ReposLoadStateViewHolder.create(parent, retry)
    }
}