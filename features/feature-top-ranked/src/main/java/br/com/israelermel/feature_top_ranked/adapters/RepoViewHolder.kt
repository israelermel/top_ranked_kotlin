package br.com.israelermel.feature_top_ranked.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.feature_top_ranked.databinding.RepoViewItemBinding
import br.com.israelermel.library_arch.extensions.setRoundedImage

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(repo: ReposEntity?) {
        if (repo != null) {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: ReposEntity) {

        repo.owner?.avatarUrl?.let {
            binding.repoPhoto.setRoundedImage(it)
        }

        repo.owner?.login?.let {
            binding.txtAuthor.text = it
        }

        repo.name?.let {
            binding.txtRepoName.text = it
        }

        repo.forksCount?.let {
            binding.txtForksCount.text = it.toString()
        }

        repo.stargazersCount?.let {
            binding.txtStars.text = it.toString()
        }
    }

    companion object {

        private lateinit var binding : RepoViewItemBinding

        fun create(parent: ViewGroup): RepoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            binding = RepoViewItemBinding.inflate(layoutInflater, parent, false)

            return RepoViewHolder(binding.root)
        }
    }

}