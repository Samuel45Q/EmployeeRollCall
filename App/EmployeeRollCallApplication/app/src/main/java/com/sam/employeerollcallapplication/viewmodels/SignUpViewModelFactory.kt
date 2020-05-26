package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sam.employeerollcallapplication.repository.SignUpCallRollRepository

class SignUpViewModelFactory(private val signUpCallRollRepository: SignUpCallRollRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpCallRollViewModel(signUpCallRollRepository) as T
    }
}