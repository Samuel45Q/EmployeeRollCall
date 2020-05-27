package com.sam.employeerollcallapplication.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.models.RequestedLeave
import com.sam.employeerollcallapplication.repository.LoginRepositoryFactory
import com.sam.employeerollcallapplication.viewmodels.ApplyLeaveCallRollViewModel
import com.sam.employeerollcallapplication.viewmodels.LoginCallRollViewModel
import com.sam.employeerollcallapplication.viewmodels.LoginViewModelFactory


class ListAdapter(private val list: List<RequestedLeave>) :
    RecyclerView.Adapter<RequestedLeaveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestedLeaveViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RequestedLeaveViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RequestedLeaveViewHolder, position: Int) {
        val requestedLeave: RequestedLeave = list[position]
        holder.bind(requestedLeave)
    }

    override fun getItemCount(): Int = list.size
}

class RequestedLeaveViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_items, parent, false)) {
    private var mEmployeeId: TextView? = null
    private var mNumberOfDays: TextView? = null
    private var declineButton: Button? = null
    private var confirmButton: Button? = null

    init {
        mEmployeeId = itemView.findViewById(R.id.employeeNumber1)
        mNumberOfDays = itemView.findViewById(R.id.noOfDays)
        declineButton = itemView.findViewById(R.id.declineButton)
        confirmButton = itemView.findViewById(R.id.confirmButton)
    }

    fun bind(requestedLeave: RequestedLeave) {
        mEmployeeId?.text = "Employee No:" + " E-" + requestedLeave.id.toString()
        mNumberOfDays?.text = "Requested Days: " + requestedLeave.numberOfDays.toString()

        confirmButton?.setOnClickListener {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Confirmation")
            builder.setMessage("Approve Leave?")
            builder.setPositiveButton("OK") { _, _ ->
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
}