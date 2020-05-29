package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.gateway.GateWayNetworkServices
import com.sam.employeerollcallapplication.services.ClockInAndOutRemotelyService

object ClockInAndOutRemotelyRepositoryFactory {
    fun createClockInAndOutRepository(): ClockInAndOutRemotelyRepository {
        val clockInAndOutRemotelyService =
            GateWayNetworkServices.instance.retrofit.create(ClockInAndOutRemotelyService::class.java)
        return ClockInAndOutRemotelyRepository(clockInAndOutRemotelyService)
    }
}