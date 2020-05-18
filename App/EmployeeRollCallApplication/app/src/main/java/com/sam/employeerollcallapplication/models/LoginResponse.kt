package com.sam.employeerollcallapplication.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user") val user: User,
    @SerializedName("token") val token: String
)

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("secondName") val secondName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("nationalId") val nationalId: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("streetNo") val streetNo: String,
    @SerializedName("status") val status: Boolean = false,
    @SerializedName("message") val message: String,
    @SerializedName("surbub") val surbub: String,
    @SerializedName("role") val role: String,
    @SerializedName("city") val city: String,
    @SerializedName("province") val province: String,
    @SerializedName("leaveBalance") val leaveBalance: LeaveBalance,
    @SerializedName("requestedLeave")
    val requestedLeave: List<RequestedLeave>? = null
)

data class LeaveBalance(
    @SerializedName("id") val id: Int,
    @SerializedName("annual") val annual: Int,
    @SerializedName("sick") val sick: Int,
    @SerializedName("study") val study: Int,
    @SerializedName("maternity") val maternity: Int
)

data class LoginRequest(
    @SerializedName("Username") val username: String,
    @SerializedName("Password") val password: String
)


data class RequestedLeave(

    @SerializedName("id") val id: Int,
    @SerializedName("numberOfDays") val numberOfDays: Int,
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("motivation") val motivation: String,
    @SerializedName("leaveStatus") val leaveStatus: Int
)

data class ApiError(@SerializedName("errorMessage") val errorMessage: String)
