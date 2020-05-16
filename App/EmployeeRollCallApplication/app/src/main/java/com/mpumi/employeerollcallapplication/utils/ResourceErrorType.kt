package com.mpumi.employeerollcallapplication.utils

import androidx.annotation.StringRes
import com.mpumi.employeerollcallapplication.R

enum class ResourceErrorType(@StringRes val message: Int) {
    NONE(R.string.blank),
    GENERAL_DATA_EMPTY(R.string.error_general_data_empty),
    NETWORK(R.string.error_general_data_retrieve),
    INVALID_CREDENTIALS(R.string.error_invalid_login)
}