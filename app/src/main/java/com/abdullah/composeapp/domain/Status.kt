package com.abdullah.composeapp.domain

sealed class Status<T> {

    data class Loading<T>(val data: T? = null) : Status<T>()

    data class Error<T>(val errorCode: Int, val errorMessage: String, val data: T? = null) : Status<T>()

    data class Success<T>(val data: T? = null) : Status<T>()

    fun isRunning(): Boolean {
        return this is Loading || this is Success
    }
}
