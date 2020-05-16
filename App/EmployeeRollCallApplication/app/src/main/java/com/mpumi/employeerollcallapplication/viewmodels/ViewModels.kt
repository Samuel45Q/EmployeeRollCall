package com.mpumi.employeerollcallapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mpumi.employeerollcallapplication.models.*
import com.mpumi.employeerollcallapplication.repository.Repositories
import com.mpumi.employeerollcallapplication.utils.Resource

class ViewModels(application: Application) : AndroidViewModel(application) {

    fun loginRequestLiveData(
        username: String,
        password: String
    ): LiveData<Resource<LoginResponse>>? {
        return Repositories.loginUser(username, password)
    }

    fun signUpLiveData(
        firstName: String, lastName: String, idNumber: String,
        cellphoneNumber: String, email: String, password: String
    ): LiveData<Resource<SignUpResponse>>? {
        return Repositories.signUpLiveData(
            firstName,
            lastName,
            idNumber,
            cellphoneNumber,
            email,
            password
        )
    }

    fun clearLiveData() {
        Repositories.clearLiveData()
    }

    fun applyLeaveLive(
        fromLeave: String,
        toLeave: String, type: Int, user: TheUser
    ): LiveData<Resource<ApplyLeaveResponse>>? {
        return Repositories.applyLeaveLive(fromLeave, toLeave, type, user)
    }
}

