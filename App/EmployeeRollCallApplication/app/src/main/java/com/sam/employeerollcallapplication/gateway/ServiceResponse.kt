package com.sam.employeerollcallapplication.gateway

interface ServiceResponse<TResponse> {
    fun onSuccess(data: TResponse?)
    fun onUnauthorised(message: String)
    fun onNetworkError(code: Int, message: String)
    fun onUnknownError(message: String)
}

abstract class NullableServiceResponse<TResponse> : ServiceResponse<TResponse> {
    final override fun onSuccess(data: TResponse?) {
        if (data == null) {
            onSuccessWithNullData()
        } else {
            onSuccessWithData(data)
        }
    }

    protected abstract fun onSuccessWithNullData()

    protected abstract fun onSuccessWithData(data: TResponse)
}