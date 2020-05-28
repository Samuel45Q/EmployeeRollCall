package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sam.employeerollcallapplication.models.ApproveLeaveResponse
import com.sam.employeerollcallapplication.repository.ApproveLeaveCallRollRepository

class ApproveLeaveCallRollViewModel(private val approveLeaveCallRollRepository: ApproveLeaveCallRollRepository): ViewModel() {

    private val approveLeaveViewData: MutableLiveData<ApproveLeaveResponse> = MutableLiveData()
    val approveLeaveRequest: LiveData<ApproveLeaveResponse> = approveLeaveViewData

    fun approveLeave(id: Int, leaveStatus: Int) {
        approveLeaveCallRollRepository
            .approveLeave(id, leaveStatus)
            .subscribe {
                approveLeaveViewData.postValue(it)
            }
    }
}