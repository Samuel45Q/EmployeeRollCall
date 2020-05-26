package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.models.*
import com.sam.employeerollcallapplication.services.ApplyLeaveService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApplyLeaveCallRollRepository(private val applyLeaveService: ApplyLeaveService) {

    fun applyLeave(
        fromLeave: String,
        toLeave: String,
        type: Int,
        user: TheUser
    ): Observable<ApplyLeaveResponse> {
        return Observable.create { emitter ->
            applyLeaveService.applyLeave(
                    ApplyLeaveRequest(
                        Leave(
                            fromDate = fromLeave,
                            toDate = toLeave,
                            type = type,
                            user = user
                        )
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