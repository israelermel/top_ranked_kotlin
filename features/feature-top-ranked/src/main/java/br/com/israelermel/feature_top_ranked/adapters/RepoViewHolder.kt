package br.com.israelermel.feature_top_ranked.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.israelermel.domain.models.repositories.ReposEntity
import br.com.israelermel.feature_top_ranked.R

class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)
    private val stars: TextView = view.findViewById(R.id.repo_stars)


    private var repo: ReposEntity? = null

    fun bind(repo: ReposEntity?) {
        if (repo == null) {
            val resources = itemView.resources
            name.text = "carregando..."
            description.visibility = View.GONE
            stars.visibility = View.GONE
            stars.text = ""
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo: ReposEntity) {
        this.repo = repo
        name.text = repo.name

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.owner?.login.orEmpty().isNotEmpty()) {
            description.text = repo.owner?.login.orEmpty()
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

        stars.text = repo.forksCount.toString()
//        forks.text = repo.forksCount.toString()

        // if the language is missing, hide the label and the value
//        var languageVisibility = View.GONE
//        if (!repo.language.isNullOrEmpty()) {
//            val resources = this.itemView.context.resources
//            language.text = resources.getString(R.string.language, repo.language)
//            languageVisibility = View.VISIBLE
//        }
//        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repo_view_item, parent, false)
            return RepoViewHolder(view)
        }
    }

}