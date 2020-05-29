package com.sam.employeerollcallapplication.models

import com.google.gson.annotations.SerializedName

data class ClockInAndOutRequest(
    @SerializedName("workstatus") val id: Int,
    @SerializedName("User") val user: TheWorkUser
)

data class ClockInAndOutResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)

data class TheWorkUser(@SerializedName("Id") val id: Int)