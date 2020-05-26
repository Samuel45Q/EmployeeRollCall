package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.models.*
import com.sam.employeerollcallapplication.services.LoginServices
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginCallRollRepository(private val loginServices: LoginServices) {

    fun login(userName: String, password: String): Observable<LoginResponse> {
        return Observable.create { emitter ->
            loginServices.login(LoginRequest(userName, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.body() != null && it.code() == 200) {
                        emitter.onNext(it.body()!!)
                    }
                }, { error ->
                    error.message
                })
        }
    }
}