package com.sam.employeerollcallapplication.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.activities.AdminDashboard
import com.sam.employeerollcallapplication.models.RequestedLeave
import com.sam.employeerollcallapplication.repository.ApproveLeaveRepositoryFactory
import com.sam.employeerollcallapplication.utils.DialogUtil
import com.sam.employeerollcallapplication.utils.EmployeeRollLoadingDialog
import com.sam.employeerollcallapplication.viewmodels.*


class ListAdapter(
    private val list: List<RequestedLeave>,
    private val adminDashboard: AdminDashboard
) :
    RecyclerView.Adapter<RequestedLeaveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestedLeaveViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RequestedLeaveViewHolder(inflater, parent, adminDashboard)
    }

    override fun onBindViewHolder(holder: RequestedLeaveViewHolder, position: Int) {
        val requestedLeave: RequestedLeave = list[position]
        holder.bind(requestedLeave, adminDashboard)
    }

    override fun getItemCount(): Int = list.size
}

class RequestedLeaveViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    adminDashboard: AdminDashboard
) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_items, parent, false)) {
    private var mEmployeeId: TextView? = null
    private var mNumberOfDays: TextView? = null
    private var declineButton: Button? = null
    private var confirmButton: Button? = null

    private var progressDialog: EmployeeRollLoadingDialog =
        EmployeeRollLoadingDialog(adminDashboard)

    private var approveLeaveCallRollViewModel: ApproveLeaveCallRollViewModel =
        ViewModelProviders.of(
                adminDashboard,
                ApproveLeaveViewModelFactory(ApproveLeaveRepositoryFactory.createApproveLeaveRepository())
            )
            .get(ApproveLeaveCallRollViewModel::class.java)

    init {

        mEmployeeId = itemView.findViewById(R.id.employeeNumber1)
        mNumberOfDays = itemView.findViewById(R.id.noOfDays)
        declineButton = itemView.findViewById(R.id.declineButton)
        confirmButton = itemView.findViewById(R.id.confirmButton)
    }

    fun bind(requestedLeave: RequestedLeave, adminDashboard: AdminDashboard) {
        mEmployeeId?.text = "Employee No:" + " E-" + requestedLeave.id.toString()
        mNumberOfDays?.text = "Requested Days: " + requestedLeave.numberOfDays.toString()

        confirmButton?.setOnClickListener {
            progressDialog.show("Approving leave request in progress pleas ait...")
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Confirmation")
            builder.setMessage("Are you sure you want to approve this leave?")
            builder.setPositiveButton("OK") { _, _ ->
                approveLeaveCallRollViewModel.approveLeaveRequest.removeObservers(adminDashboard)
                approveLeaveCallRollViewModel.approveLeaveRequest.observe(
                    adminDashboard,
                    Observer { data ->
                        data.let {
                            if (it.status) {
                                progressDialog.hide()
                                DialogUtil.showDialog(
                                    itemView.context,
                                    message = it.message,
                                    positiveButton = R.string.ok
                                )
                            } else {
                                progressDialog.hide()
                                DialogUtil.showDialog(
                                    itemView.context,
                                    message = it.message,
                                    positiveButton = R.string.ok
                                )
                            }
                        }
                    })
                approveLeaveCallRollViewModel.approveLeave(requestedLeave.id, 1)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        declineButton?.setOnClickListener {
            progressDialog.show("Declining leave request in progress pleas ait...")
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Confirmation")
            builder.setMessage("Are you sure you want to decline this leave?")
            builder.setPositiveButton("OK") { _, _ ->
                approveLeaveCallRollViewModel.approveLeaveRequest.removeObservers(adminDashboard)
                approveLeaveCallRollViewModel.approveLeaveRequest.observe(
                    adminDashboard,
                    Observer { data ->
                        data.let {
                            if (it.status) {
                                progressDialog.hide()
                                DialogUtil.showDialog(
                                    itemView.context,
                                    message = it.message,
                                    positiveButton = R.string.ok
                                )
                            } else {
                                progressDialog.hide()
                                DialogUtil.showDialog(
                                    itemView.context,
                                    message = it.message,
                                    positiveButton = R.string.ok
                                )
                            }
                        }
                    })
                approveLeaveCallRollViewModel.approveLeave(requestedLeave.id, 2)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}