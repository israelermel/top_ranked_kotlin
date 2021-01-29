package br.com.israelermel.domain.models.repositories

enum class ReposKeyParam(val value: String) {
    FILTER("q"),
    SORT("sort"),
    PAGE("page"),
    PER_PAGE("per_page")
}

data class ReposRequest(
    val params: Map<String, String>
)