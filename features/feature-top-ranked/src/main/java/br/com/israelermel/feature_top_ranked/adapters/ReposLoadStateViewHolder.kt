package br.com.israelermel.feature_top_ranked.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import br.com.israelermel.feature_top_ranked.R
import br.com.israelermel.feature_top_ranked.databinding.LoadStateFooterViewItemBinding
import br.com.israelermel.library_arch_extensions.setIsVisible

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

        binding.progressBar.setIsVisible(loadState is LoadState.Loading)
        binding.retryButton.setIsVisible(loadState !is LoadState.Loading)
        binding.errorMsg.setIsVisible(loadState !is LoadState.Loading)

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