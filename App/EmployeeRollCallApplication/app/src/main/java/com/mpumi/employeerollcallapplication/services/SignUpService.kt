package com.mpumi.employeerollcallapplication.services

import com.mpumi.employeerollcallapplication.models.SignUpRequest
import com.mpumi.employeerollcallapplication.models.SignUpResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface SignUpService {
    @POST("/api/User/Registration")
    fun signUp(@Body signUpRequest: SignUpRequest): Observable<Response<SignUpResponse>>
}