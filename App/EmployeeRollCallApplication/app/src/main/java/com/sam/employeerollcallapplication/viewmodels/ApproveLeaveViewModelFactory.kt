package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sam.employeerollcallapplication.repository.ApproveLeaveCallRollRepository

class ApproveLeaveViewModelFactory(private val approveLeaveCallRollRepository: ApproveLeaveCallRollRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ApproveLeaveCallRollViewModel(approveLeaveCallRollRepository) as T
    }
}