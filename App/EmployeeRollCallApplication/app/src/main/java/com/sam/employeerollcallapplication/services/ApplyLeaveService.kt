package com.sam.employeerollcallapplication.services

import com.sam.employeerollcallapplication.models.ApplyLeaveRequest
import com.sam.employeerollcallapplication.models.ApplyLeaveResponse
import com.sam.employeerollcallapplication.models.ApproveLeaveRequest

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApplyLeaveService {

    @POST("/api/Leave/Apply")
    fun applyLeave(@Body applyLeaveRequest: ApplyLeaveRequest): Observable<Response<ApplyLeaveResponse>>
    fun approveLeave(@Body approveLeave: ApproveLeaveRequest): Observable<Response<ApplyLeaveResponse>>
}