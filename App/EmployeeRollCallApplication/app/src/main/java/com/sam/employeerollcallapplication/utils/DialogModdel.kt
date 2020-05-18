package com.sam.employeerollcallapplication.utils

data class DialogModel(var message: String,
                       var title: String? = null,
                       var positiveButton: Int? = null,
                       var negativeButton: Int? = null,
                       var onPositiveButtonClick: DialogOnClick? = null,
                       var onNegativeButtonClick: DialogOnClick? = null)