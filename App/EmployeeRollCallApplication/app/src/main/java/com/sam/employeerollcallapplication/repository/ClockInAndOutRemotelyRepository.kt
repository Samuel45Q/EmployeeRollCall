package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.models.ClockInAndOutRequest
import com.sam.employeerollcallapplication.models.ClockInAndOutResponse
import com.sam.employeerollcallapplication.models.TheWorkUser
import com.sam.employeerollcallapplication.services.ClockInAndOutRemotelyService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ClockInAndOutRemotelyRepository(private val clockInAndOutRemotelyService: ClockInAndOutRemotelyService) {

    fun clockInAndOut(
        workStatus: Int,
        user: TheWorkUser
    ): Observable<ClockInAndOutResponse> {
        return Observable.create { emitter ->
            clockInAndOutRemotelyService.clockInAndOut(
                    ClockInAndOutRequest(workStatus, user)
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.body() != null && it.code() == 200) {
                        emitter.onNext(it.body()!!)
                    }
                }, { error ->
                    println(error.message)
                })
        }
    }
}