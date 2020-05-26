package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.gateway.GateWayNetworkServices
import com.sam.employeerollcallapplication.services.SignUpService

object SignUpRepositoryFactory {

    fun createSignUpRepository(): SignUpCallRollRepository {
        val signUpService =
            GateWayNetworkServices.instance.retrofit.create(SignUpService::class.java)
        return SignUpCallRollRepository(signUpService)
    }
}