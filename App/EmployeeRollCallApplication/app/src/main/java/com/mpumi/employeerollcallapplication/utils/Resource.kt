package com.mpumi.employeerollcallapplication.utils

data class Resource<out T>(
    val status: ResourceStatus,
    val data: T?,
    val errorType: ResourceErrorType
) {
    fun isSuccessful(): Boolean {
        return status == ResourceStatus.SUCCESS
    }

    fun hasData(): Boolean {
        return data != null
    }

    fun isLoading(): Boolean {
        return status == ResourceStatus.LOADING
    }

    fun hasError(): Boolean {
        return status == ResourceStatus.ERROR
    }

    fun <TOther> convert(buildData: (T) -> TOther): Resource<TOther> {
        return ResourceHandler.handleNonNull(this, buildData)
    }

    fun toErrorIfSuccessfulButWithoutData(): Resource<T> {
        if (this.isSuccessful() && !this.hasData()) {
            return missingData()
        }
        return this
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceStatus.SUCCESS, data, ResourceErrorType.NONE)
        }

        fun <T> error(errorType: ResourceErrorType, data: T?): Resource<T> {
            return Resource(ResourceStatus.ERROR, data, errorType)
        }

        fun <T> missingData() = error(ResourceErrorType.GENERAL_DATA_EMPTY, null as T?)

        fun <T> loading(data: T?): Resource<T> {
            return Resource(ResourceStatus.LOADING, data, ResourceErrorType.NONE)
        }
    }
}

enum class ResourceStatus {
    SUCCESS,
    ERROR,
    LOADING
}
