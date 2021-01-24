package br.com.israelermel.domain.models.repositories

enum class BestProjectsKotlinParam(val value: String) {
    FILTER("q"),
    SORT("sort"),
    PAGE("page")
}

data class BestProjectsKotlinRequest(
    val params: Map<String, String>
)