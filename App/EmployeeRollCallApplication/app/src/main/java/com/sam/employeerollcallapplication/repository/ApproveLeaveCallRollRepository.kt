package com.sam.employeerollcallapplication.repository

import com.sam.employeerollcallapplication.models.ApproveLeaveRequest
import com.sam.employeerollcallapplication.models.ApproveLeaveResponse
import com.sam.employeerollcallapplication.services.ApplyLeaveService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApproveLeaveCallRollRepository(private val applyLeaveService: ApplyLeaveService) {

    fun approveLeave(id: Int, leaveStatus: Int): Observable<ApproveLeaveResponse> {
        return Observable.create { emitter ->
            applyLeaveService.approveLeave(ApproveLeaveRequest(id, leaveStatus))
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