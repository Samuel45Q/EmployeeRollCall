package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sam.employeerollcallapplication.repository.LoginCallRollRepository

class LoginViewModelFactory(private val callRollRepository: LoginCallRollRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginCallRollViewModel(callRollRepository) as T
    }
}