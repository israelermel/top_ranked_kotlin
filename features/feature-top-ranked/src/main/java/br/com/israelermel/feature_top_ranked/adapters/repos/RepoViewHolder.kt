package br.com.israelermel.feature_top_ranked.adapters.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.feature_top_ranked.R
import br.com.israelermel.feature_top_ranked.databinding.RepoViewItemBinding
import br.com.israelermel.library_arch.extensions.setRoundedImage
import br.com.israelermel.library_arch.extensions.setVisibilityState
import br.com.israelermel.library_arch.viewstates.VisibilityState

class RepoViewHolder(view: RepoViewItemBinding) : RecyclerView.ViewHolder(view.root) {

    fun bind(repo: ReposEntity?) {
        if (repo != null) {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: ReposEntity) {
        repo.owner?.avatarUrl?.let {
            imgPhoto.setRoundedImage(it)
        } ?: imgPhoto.setVisibilityState(VisibilityState.GONE)

        repo.owner?.login?.let {
            txtAuthorName.text = it
        }

        repo.name?.let {
            txtRepoName.text = it
        }

        repo.forksCount?.let {
            txtForksCount.text = it.toString()
        }

        repo.stargazersCount?.let {
            txtStars.text = it.toString()
        }
    }

    companion object {

        lateinit var imgPhoto: ImageView
        lateinit var txtAuthorName: TextView
        lateinit var txtRepoName: TextView
        lateinit var txtForksCount: TextView
        lateinit var txtStars: TextView

        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)

            val binding = RepoViewItemBinding.bind(view)

            imgPhoto = binding.repoPhoto
            txtAuthorName = binding.txtAuthor
            txtRepoName = binding.txtRepoName
            txtForksCount = binding.txtForksCount
            txtStars = binding.txtStars

            return RepoViewHolder(binding)
        }
    }

}