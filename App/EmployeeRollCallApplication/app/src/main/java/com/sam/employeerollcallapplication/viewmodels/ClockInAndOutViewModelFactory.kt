package com.sam.employeerollcallapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sam.employeerollcallapplication.repository.ClockInAndOutRemotelyRepository

class ClockInAndOutViewModelFactory(private val clockInAndOutRemotelyRepository: ClockInAndOutRemotelyRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClockInAndOutViewModel(clockInAndOutRemotelyRepository) as T
    }
}