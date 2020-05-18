package com.sam.employeerollcallapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.sam.employeerollcallapplication.gateway.GateWayNetworkServices
import com.sam.employeerollcallapplication.gateway.ServiceResponse
import com.sam.employeerollcallapplication.models.*
import com.sam.employeerollcallapplication.services.ApplyLeaveService
import com.sam.employeerollcallapplication.services.LoginServices
import com.sam.employeerollcallapplication.services.SignUpService
import com.sam.employeerollcallapplication.utils.Resource
import com.sam.employeerollcallapplication.utils.ResourceErrorType
import io.reactivex.Observable
import retrofit2.Response


object Repositories {

    private var loginDetails: MutableLiveData<Resource<LoginResponse>>? = null
    private var signUpLiveData: MutableLiveData<Resource<SignUpResponse>>? = null
    private var applyLeaveLiveData: MutableLiveData<Resource<ApplyLeaveResponse>>? = null
    private var errorMessageLiveData: MutableLiveData<String>? = null

    fun clearLiveData() {
        signUpLiveData?.postValue(null)
        loginDetails?.postValue(null)
    }

    private fun callLoginService(serviceCall: Observable<Response<LoginResponse>>) {
        GateWayNetworkServices.call(serviceCall, object : ServiceResponse<LoginResponse> {
            override fun onUnauthorised(message: String) {
                loginDetails?.postValue(Resource.error(ResourceErrorType.INVALID_CREDENTIALS, null))
            }

            override fun onNetworkError(code: Int, message: String) {
                loginDetails?.postValue(Resource.error(ResourceErrorType.NETWORK, null))
            }

            override fun onUnknownError(message: String) {
                loginDetails?.postValue(Resource.error(ResourceErrorType.NONE, null))
            }

            override fun onSuccess(data: LoginResponse?) {
                if (data != null) {
                    loginDetails?.postValue(Resource.success(data))
                } else {
                    loginDetails?.postValue(Resource.error(ResourceErrorType.NONE, null))
                }
            }
        })
    }

    fun loginUser(username: String, password: String): LiveData<Resource<LoginResponse>>? {
        if (loginDetails == null) {
            loginDetails = MutableLiveData()
            loginDetails?.value = Resource.loading(null)
            val loginService = GateWayNetworkServices.create(LoginServices::class.java)
            callLoginService(loginService.login(LoginRequest(username, password)))
        }
        return loginDetails
    }

    private fun callRegistrationService(serviceCall: Observable<Response<SignUpResponse>>) {
        GateWayNetworkServices.call(serviceCall, object : ServiceResponse<SignUpResponse> {
            override fun onUnauthorised(message: String) {
                loginDetails?.postValue(Resource.error(ResourceErrorType.INVALID_CREDENTIALS, null))
            }

            override fun onNetworkError(code: Int, message: String) {
                loginDetails?.postValue(Resource.error(ResourceErrorType.NETWORK, null))
            }

            override fun onUnknownError(message: String) {
                loginDetails?.postValue(Resource.error(ResourceErrorType.NONE, null))
            }

            override fun onSuccess(data: SignUpResponse?) {
                if (data != null) {
                    signUpLiveData?.postValue(Resource.success(data))
                } else {
                    loginDetails?.postValue(Resource.error(ResourceErrorType.NONE, null))
                }
            }
        })
    }

    fun signUpLiveData(
        firstName: String, lastName: String, idNumber: String,
        cellphoneNumber: String, email: String, password: String
    ): LiveData<Resource<SignUpResponse>>? {
        if (signUpLiveData == null) {
            signUpLiveData = MutableLiveData()
            signUpLiveData?.value = Resource.loading(null)
            val signUpService = GateWayNetworkServices.create(SignUpService::class.java)
            callRegistrationService(
                signUpService.signUp(
                    SignUpRequest(
                        firstName,
                        lastName,
                        idNumber,
                        cellphoneNumber,
                        email,
                        password
                    )
                )
            )
        }
        return signUpLiveData
    }

    fun errorMessageLiveData(): LiveData<String>? {
        if (errorMessageLiveData == null) {
            loginDetails = MediatorLiveData()
        }
        return errorMessageLiveData
    }

    private fun callApplyLeaveService(serviceCall: Observable<Response<ApplyLeaveResponse>>) {
        GateWayNetworkServices.call(serviceCall, object : ServiceResponse<ApplyLeaveResponse> {
            override fun onUnauthorised(message: String) {
                loginDetails?.postValue(Resource.error(ResourceErrorType.INVALID_CREDENTIALS, null))
            }

            override fun onNetworkError(code: Int, message: String) {
                loginDetails?.postValue(Resource.error(ResourceErrorType.NETWORK, null))
            }

            override fun onUnknownError(message: String) {
                loginDetails?.postValue(Resource.error(ResourceErrorType.NONE, null))
            }

            override fun onSuccess(data: ApplyLeaveResponse?) {
                if (data != null) {
                    applyLeaveLiveData?.postValue(Resource.success(data))
                } else {
                    loginDetails?.postValue(Resource.error(ResourceErrorType.NONE, null))
                }
            }
        })
    }

    fun applyLeaveLive(
        fromLeave: String,
        toLeave: String, type: Int, user: TheUser
    ): LiveData<Resource<ApplyLeaveResponse>>? {
        if (applyLeaveLiveData == null) {
            applyLeaveLiveData = MutableLiveData()
            applyLeaveLiveData?.value = Resource.loading(null)
            val applyLeaveService = GateWayNetworkServices.create(ApplyLeaveService::class.java)
            callApplyLeaveService(
                applyLeaveService.applyLeave(
                    ApplyLeaveRequest(
                        fromDate = fromLeave,
                        toDate = toLeave,
                        type = type,
                        user = user
                    )
                )
            )
        }
        return applyLeaveLiveData
    }
}