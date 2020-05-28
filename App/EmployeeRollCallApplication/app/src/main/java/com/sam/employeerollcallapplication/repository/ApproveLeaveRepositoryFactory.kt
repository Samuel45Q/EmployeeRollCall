package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.gateway.GateWayNetworkServices
import com.sam.employeerollcallapplication.services.ApplyLeaveService

object ApproveLeaveRepositoryFactory {

    fun createApproveLeaveRepository(): ApproveLeaveCallRollRepository {
        val applyLeaveService =
            GateWayNetworkServices.instance.retrofit.create(ApplyLeaveService::class.java)
        return ApproveLeaveCallRollRepository(applyLeaveService)
    }
}