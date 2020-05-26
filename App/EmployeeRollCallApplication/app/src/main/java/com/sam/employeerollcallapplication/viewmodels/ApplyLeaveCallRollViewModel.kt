package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sam.employeerollcallapplication.models.ApplyLeaveResponse
import com.sam.employeerollcallapplication.models.TheUser
import com.sam.employeerollcallapplication.repository.ApplyLeaveCallRollRepository

class ApplyLeaveCallRollViewModel(private val applyLeaveCallRollRepository: ApplyLeaveCallRollRepository) :
    ViewModel() {

    private val applyLeave: MutableLiveData<ApplyLeaveResponse> = MutableLiveData()
    val applyLeaveRequest: LiveData<ApplyLeaveResponse> = applyLeave

    fun applyLeave(
        fromLeave: String,
        toLeave: String,
        type: Int,
        user: TheUser
    ) {
        applyLeaveCallRollRepository
            .applyLeave(fromLeave, toLeave, type, user)
            .subscribe {
                applyLeave.postValue(it)
            }

    }
}