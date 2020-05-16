package com.mpumi.employeerollcallapplication.models

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("NationalId") val idNumber: String,
    @SerializedName("Phone") val cellphoneNumber: String,
    @SerializedName("UserName") val email: String,
    @SerializedName("Password") val password: String
)

data class SignUpResponse(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean
)