package com.zhalz.voyageoflife.utils

sealed class ApiResult<F>(
    val data: F? = null,
    val message: String? = null
) {
    class Success<F>(data: F) : ApiResult<F>(data)
    class Error<F>(message: String?) : ApiResult<F>(message = message)
    class Loading<F> : ApiResult<F>()
}