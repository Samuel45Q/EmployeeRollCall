package com.mpumi.employeerollcallapplication.services

import com.mpumi.employeerollcallapplication.models.LoginRequest
import com.mpumi.employeerollcallapplication.models.LoginResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServices {
    @POST("/api/User/Login")
    fun login(@Body loginRequest: LoginRequest): Observable<Response<LoginResponse>>
}