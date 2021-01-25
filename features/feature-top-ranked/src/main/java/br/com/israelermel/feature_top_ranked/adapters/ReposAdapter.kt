package br.com.israelermel.feature_top_ranked.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.israelermel.domain.models.repositories.RepositoriesBo

class ReposAdapter : PagingDataAdapter<RepositoriesBo, RepoViewHolder>(REPO_COMPARATOR) {

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
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<RepositoriesBo>() {
            override fun areItemsTheSame(oldItem: RepositoriesBo, newItem: RepositoriesBo): Boolean =
                oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: RepositoriesBo, newItem: RepositoriesBo): Boolean =
                oldItem == newItem
        }
    }
}
