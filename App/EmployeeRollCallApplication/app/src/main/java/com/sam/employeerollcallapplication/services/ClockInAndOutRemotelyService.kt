package com.sam.employeerollcallapplication.services

import com.sam.employeerollcallapplication.models.ClockInAndOutRequest
import com.sam.employeerollcallapplication.models.ClockInAndOutResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ClockInAndOutRemotelyService {
    @POST("/api/User/WorkRegister")
    fun clockInAndOut(@Body clockInAndOutRequest: ClockInAndOutRequest): Single<Response<ClockInAndOutResponse>>
}