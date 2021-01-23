package br.com.israelermel.domain.exceptions

sealed class RepositoriesException : RuntimeException() {
    object EmptyRepositoriesBodyException : RepositoriesException()
    object RequestRepositoriesException : RepositoriesException()
    object UnknownRepositoriesException : RepositoriesException()
}