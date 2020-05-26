package com.sam.employeerollcallapplication.services

import com.sam.employeerollcallapplication.models.SignUpRequest
import com.sam.employeerollcallapplication.models.SignUpResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/api/User/Registration")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<Response<SignUpResponse>>
}