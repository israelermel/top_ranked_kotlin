package br.com.israelermel.domain.models.repositories

enum class GitHubRepositoriesKeyParam(val value: String) {
    FILTER("q"),
    SORT("sort"),
    PAGE("page")
}

data class GitHubRepositoriesRequest(
    val params: Map<String, String>
)