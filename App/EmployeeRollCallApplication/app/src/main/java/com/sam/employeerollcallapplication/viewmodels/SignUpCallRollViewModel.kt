package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sam.employeerollcallapplication.models.SignUpResponse
import com.sam.employeerollcallapplication.repository.SignUpCallRollRepository

class SignUpCallRollViewModel(private val signUpCallRollRepository: SignUpCallRollRepository): ViewModel() {

    private val signUpViewData: MutableLiveData<SignUpResponse> = MutableLiveData()
    val signURequest: LiveData<SignUpResponse> = signUpViewData

    fun signUp(
        firstName: String, lastName: String, idNumber: String,
        cellphoneNumber: String, email: String, password: String
    ) {
        signUpCallRollRepository
            .signUp(firstName, lastName, idNumber, cellphoneNumber, email, password)
            .subscribe {
                signUpViewData.postValue(it)
            }
    }
}