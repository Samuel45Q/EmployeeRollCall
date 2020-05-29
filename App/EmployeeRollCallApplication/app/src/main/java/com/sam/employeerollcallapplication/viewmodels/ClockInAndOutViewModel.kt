package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sam.employeerollcallapplication.models.ClockInAndOutResponse
import com.sam.employeerollcallapplication.models.TheWorkUser
import com.sam.employeerollcallapplication.repository.ClockInAndOutRemotelyRepository

class ClockInAndOutViewModel(private val clockInAndOutRemotelyRepository: ClockInAndOutRemotelyRepository) :
    ViewModel() {

    private val clockInAndOutViewData: MutableLiveData<ClockInAndOutResponse> = MutableLiveData()
    val clockInAndOutResponse: LiveData<ClockInAndOutResponse> = clockInAndOutViewData

    fun clockInAndOut(
        workStatus: Int,
        user: TheWorkUser
    ) {
        clockInAndOutRemotelyRepository
            .clockInAndOut(workStatus, user)
            .subscribe {
                clockInAndOutViewData.postValue(it)
            }

    }
}