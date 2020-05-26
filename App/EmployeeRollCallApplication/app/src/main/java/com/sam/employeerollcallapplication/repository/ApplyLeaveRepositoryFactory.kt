package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.gateway.GateWayNetworkServices
import com.sam.employeerollcallapplication.services.ApplyLeaveService
import com.sam.employeerollcallapplication.services.SignUpService

object ApplyLeaveRepositoryFactory {
    fun createApplyLeaveRepository(): ApplyLeaveCallRollRepository {
        val applyLeaveService =
            GateWayNetworkServices.instance.retrofit.create(ApplyLeaveService::class.java)
        return ApplyLeaveCallRollRepository(applyLeaveService)
    }
}