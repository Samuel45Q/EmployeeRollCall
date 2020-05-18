package com.sam.employeerollcallapplication.services

import com.sam.employeerollcallapplication.models.LoginRequest
import com.sam.employeerollcallapplication.models.LoginResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServices {
    @POST("/api/User/Login")
    fun login(@Body loginRequest: LoginRequest): Observable<Response<LoginResponse>>
}