package com.teclast_korea.payhere_entry.data.common

/**
 * A generic class that holds a value or an exception
 */
sealed class ResultData<out R> {
    data class SuccessData<out T>(val data: T) : ResultData<T>()
    data class ErrorData(val exception: Exception) : ResultData<Nothing>()
}

fun <T> ResultData<T>.successOr(fallback: T): T {
    return (this as? ResultData.SuccessData<T>)?.data ?: fallback
}
