package com.sam.employeerollcallapplication.services

import com.sam.employeerollcallapplication.models.ApplyLeaveRequest
import com.sam.employeerollcallapplication.models.ApplyLeaveResponse
import com.sam.employeerollcallapplication.models.ApproveLeaveRequest
import com.sam.employeerollcallapplication.models.ApproveLeaveResponse

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApplyLeaveService {

    @POST("/api/Leave/Apply")
    fun applyLeave(@Body applyLeaveRequest: ApplyLeaveRequest): Single<Response<ApplyLeaveResponse>>
    fun approveLeave(@Body approveLeave: ApproveLeaveRequest): Single<Response<ApproveLeaveResponse>>
}