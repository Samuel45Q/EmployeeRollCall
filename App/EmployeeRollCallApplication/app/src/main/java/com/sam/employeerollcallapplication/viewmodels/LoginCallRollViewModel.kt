package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sam.employeerollcallapplication.models.*
import com.sam.employeerollcallapplication.repository.LoginCallRollRepository

class LoginCallRollViewModel(private val callRollRepository: LoginCallRollRepository) :
    ViewModel() {

    private val _login: MutableLiveData<LoginResponse> = MutableLiveData()
    val loginRequest: LiveData<LoginResponse> = _login

    fun login(userName: String, password: String) {
        callRollRepository
            .login(userName, password)
            .subscribe {
                _login.postValue(it)
            }
    }
}

