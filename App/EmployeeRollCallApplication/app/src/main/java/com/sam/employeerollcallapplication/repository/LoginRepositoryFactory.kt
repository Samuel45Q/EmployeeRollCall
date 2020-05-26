package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.gateway.GateWayNetworkServices
import com.sam.employeerollcallapplication.services.LoginServices

object LoginRepositoryFactory {

    fun createLoginRepository(): LoginCallRollRepository {
        val loginServices =
            GateWayNetworkServices.instance.retrofit.create(LoginServices::class.java)
        return LoginCallRollRepository(loginServices)
    }
}