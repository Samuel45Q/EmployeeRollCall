package com.mpumi.employeerollcallapplication.utils

object Constants {

    const val BASE_URL = "http://saaba.seanegotech.com"
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
    const val CONNECTION_TIMEOUT = 30L
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

    enum class LeaveStatus(id: Int) {
        Approved(1),
        Rejected(2)
    }

    enum class Type(id: Int) {
        ANNUAL(0),
        SICK(1),
        FAMILY(2),
        STUDY(3),
        MATERNITY(4),
    }
}
