package br.com.israelermel.feature_top_ranked.adapters.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import br.com.israelermel.feature_top_ranked.R
import br.com.israelermel.feature_top_ranked.databinding.LoadStateFooterViewItemBinding
import br.com.israelermel.library_arch.extensions.setVisibilityState
import br.com.israelermel.library_arch.viewstates.VisibilityState

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
            binding.progressBar.setVisibilityState(VisibilityState.GONE)
            binding.retryButton.setVisibilityState(VisibilityState.VISIBLE)
        } else {
            binding.retryButton.setVisibilityState(VisibilityState.GONE)
            binding.progressBar.setVisibilityState(visibleStateWhenLoading(loadState))
            binding.errorMsg.setVisibilityState(visibleStateWhenLoading(loadState))
        }
    }


    private fun visibleStateWhenLoading(loadingState: LoadState): VisibilityState {
        return if (loadingState is LoadState.Loading) {
            VisibilityState.VISIBLE
        } else {
            VisibilityState.GONE
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