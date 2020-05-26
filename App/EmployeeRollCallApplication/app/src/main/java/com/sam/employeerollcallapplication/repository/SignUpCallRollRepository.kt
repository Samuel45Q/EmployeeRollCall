package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.models.SignUpRequest
import com.sam.employeerollcallapplication.models.SignUpResponse
import com.sam.employeerollcallapplication.services.SignUpService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SignUpCallRollRepository(private val signUpService: SignUpService) {

    fun signUp(
        firstName: String, lastName: String, idNumber: String,
        cellphoneNumber: String, email: String, password: String
    ): Observable<SignUpResponse> {
        return Observable.create { emitter ->
            signUpService.signUp(
                    SignUpRequest(
                        firstName, lastName, idNumber, cellphoneNumber, email, password
                    )
                )
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