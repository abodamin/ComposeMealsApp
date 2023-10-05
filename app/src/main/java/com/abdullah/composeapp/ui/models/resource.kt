package com.abdullah.composeapp.ui.models

sealed class Resource<out ResultType> {
    /**
     * Creates [Resource] object with child class `Success` status and [data].
     */
    data class Success<ResultType>(var data: ResultType? = null) : Resource<ResultType>()

    /**
     * Creates [Resource] object with child class `Loading` status to notify
     * the UI to showing loading.
     */
    object Loading : Resource<Nothing>()

    /**
     * Creates [Resource] object with `ERROR` status and [throwable] or [message].
     */
    data class Error(
        val throwable: Throwable? = null,
        val message: String? = null,
    ) : Resource<Nothing>()
}