package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sam.employeerollcallapplication.repository.ApplyLeaveCallRollRepository

class ApplyLeaveViewModelFactory(private val applyLeaveCallRollRepository: ApplyLeaveCallRollRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ApplyLeaveCallRollViewModel(applyLeaveCallRollRepository) as T
    }
}