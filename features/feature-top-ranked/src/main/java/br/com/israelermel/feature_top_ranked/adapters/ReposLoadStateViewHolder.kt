package br.com.israelermel.feature_top_ranked.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import br.com.israelermel.feature_top_ranked.R
import br.com.israelermel.feature_top_ranked.databinding.LoadStateFooterViewItemBinding
import br.com.israelermel.library_arch.extensions.VisibilityState
import br.com.israelermel.library_arch.extensions.setVisibilityState

class ReposLoadStateViewHolder(
    private val binding: LoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }

        if (loadState is LoadState.Loading) {
            binding.progressBar.setVisibilityState(VisibilityState.VISIBLE)
        } else {
            binding.progressBar.setVisibilityState(VisibilityState.GONE)
            binding.errorMsg.setVisibilityState(VisibilityState.VISIBLE)
            binding.retryButton.setVisibilityState(VisibilityState.VISIBLE)
        }

    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repos_load_state_footer_view_item, parent, false)
            val binding = LoadStateFooterViewItemBinding.bind(view)
            return ReposLoadStateViewHolder(binding, retry)
        }
    }

}