package com.sam.employeerollcallapplication.models

import com.google.gson.annotations.SerializedName

data class ApplyLeaveRequest(
    @SerializedName("From") val fromDate: String,
    @SerializedName("To") val toDate: String,
    @SerializedName("Motivation") val motivation: String? = null,
    @SerializedName("Type") val type: Int,
    @SerializedName("User") val user: TheUser,
    @SerializedName("Attachment") val attachment: Attachment? = null
)

data class ApplyLeaveResponse(@SerializedName("status") val status: Boolean)

data class Attachment(@SerializedName("Attachment") val attachment: String)


data class ApproveLeaveRequest(
    @SerializedName("Id") val leaveId: Int,
    @SerializedName("LeaveStatus") val leaveStatus: Int
)

data class TheUser(@SerializedName("Id") val id: Int)

data class ApproveLeaveResponse(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean
)