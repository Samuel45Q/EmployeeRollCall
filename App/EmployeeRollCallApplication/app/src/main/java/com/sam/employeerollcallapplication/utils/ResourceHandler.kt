package com.sam.employeerollcallapplication.utils


object ResourceHandler {
    inline fun <InType, OutType> handle(resource: Resource<InType>, buildDataFromResource: (InType?) -> OutType): Resource<OutType> {
        val data = buildDataFromResource(resource.data)
        return when (resource.status) {
            ResourceStatus.LOADING -> Resource.loading(data)
            ResourceStatus.SUCCESS -> Resource.success(data)
            ResourceStatus.ERROR -> Resource.error(resource.errorType, data)
        }
    }

    inline fun <InType, OutType> handleNonNull(resource: Resource<InType>, buildDataFromResource: (InType) -> OutType): Resource<OutType> {
        val data = resource.data?.let(buildDataFromResource)
        return when (resource.status) {
            ResourceStatus.LOADING -> Resource.loading(data)
            ResourceStatus.SUCCESS -> Resource.success(data)
            ResourceStatus.ERROR -> Resource.error(resource.errorType, data)
        }
    }
}