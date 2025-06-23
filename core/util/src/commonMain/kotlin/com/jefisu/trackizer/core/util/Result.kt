package com.jefisu.trackizer.core.util

typealias EmptyResult = Result<Unit, Message>

sealed interface Result<out D, out M : Message> {
    data class Success<out D, out M : Message>(val data: D) : Result<D, M>
    data class Error<out M : Message>(val message: M) : Result<Nothing, M>
}

inline fun <T, M : Message> Result<T, M>.onSuccess(action: (T) -> Unit): Result<T, M> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T, M : Message> Result<T, M>.onError(action: (M) -> Unit): Result<T, M> {
    if (this is Result.Error) action(message)
    return this
}

inline fun <T, R, M : Message> Result<T, M>.mapSuccess(transform: (T) -> R): Result<R, M> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> this
    }
}
