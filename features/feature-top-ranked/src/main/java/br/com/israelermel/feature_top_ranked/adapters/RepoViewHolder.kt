package br.com.israelermel.feature_top_ranked.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.feature_top_ranked.R
import br.com.israelermel.library_arch.extensions.VisibilityState
import br.com.israelermel.library_arch.extensions.setVisibilityState
import br.com.israelermel.library_arch.extensions.setRoundedImage

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imgPhoto: ImageView = view.findViewById(R.id.repo_photo)
    private val txtAuthorName: TextView = view.findViewById(R.id.txt_author)
    private val txtRepoName: TextView = view.findViewById(R.id.txt_repo_name)
    private val txtForksCount: TextView = view.findViewById(R.id.txt_forks_count)
    private val txtStars: TextView = view.findViewById(R.id.txt_stars)

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
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            return RepoViewHolder(view)
        }
    }

}