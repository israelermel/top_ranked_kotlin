package br.com.israelermel.feature_top_ranked.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.feature_top_ranked.databinding.RepoViewItemBinding

class ReposAdapter : PagingDataAdapter<ReposEntity, RepoViewHolder>(REPO_COMPARATOR) {

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repoItem = getItem(position)
        if(repoItem != null) {
            holder.bind(repoItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.create(parent)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ReposEntity>() {
            override fun areItemsTheSame(oldItem: ReposEntity, newItem: ReposEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ReposEntity, newItem: ReposEntity): Boolean =
                oldItem == newItem
        }
    }
}
