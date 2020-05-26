package com.sam.employeerollcallapplication.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.models.TheUser
import com.sam.employeerollcallapplication.repository.ApplyLeaveRepositoryFactory
import com.sam.employeerollcallapplication.viewmodels.ApplyLeaveCallRollViewModel
import com.sam.employeerollcallapplication.viewmodels.ApplyLeaveViewModelFactory
import kotlinx.android.synthetic.main.activity_apply_leave.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class ApplyLeaveActivity : BaseActivity() {

    private var cal: Calendar = Calendar.getInstance()
    private lateinit var applyLeaveCallRollViewModel: ApplyLeaveCallRollViewModel

    private var from: String? = null
    private var to: String? = null
    lateinit var leaveTpe: String
    var id by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyLeaveCallRollViewModel =
            ViewModelProviders.of(
                this,
                ApplyLeaveViewModelFactory(ApplyLeaveRepositoryFactory.createApplyLeaveRepository())
            ).get(ApplyLeaveCallRollViewModel::class.java)

        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        id = sharedPreferences.getInt("employeeId", 0)
        setContentView(R.layout.activity_apply_leave)
        val leaves = resources.getStringArray(R.array.Leaves)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, leaves
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    leaveTpe = leaves[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        val dateSetListenerStartDate =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateStartDateInView()
            }

        val dateSetListenerEndDate =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateEndDateInView()
            }

        start_date!!.setOnClickListener {
            DatePickerDialog(
                this@ApplyLeaveActivity,
                dateSetListenerStartDate,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        end_date!!.setOnClickListener {
            DatePickerDialog(
                this@ApplyLeaveActivity,
                dateSetListenerEndDate,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        setOnClickApplyLeave()
    }

    private fun updateStartDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val apiFormat = "yyyy-MM-dd'T'HH:mm:ss.SS" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val sdf2 = SimpleDateFormat(apiFormat, Locale.US)
        from = sdf2.format(cal.time)
        start_date_text.text = sdf.format(cal.time)
    }

    private fun updateEndDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val apiFormat = "yyyy-MM-dd'T'HH:mm:ss.SS"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val sdf2 = SimpleDateFormat(apiFormat, Locale.US)
        to = sdf2.format(cal.time)
        end_date_text.text = sdf.format(cal.time)
    }

    private fun setOnClickApplyLeave() {
        submitLeave.setOnClickListener {
            showProgressDialog("Leave application in progress....")

            var type = 0

            if (leaveTpe == "Annual") {
                type = 0
            } else if (leaveTpe == "Sick") {
                type = 1
            } else if (leaveTpe == "Unpaid") {
                type = 2
            } else if (leaveTpe == "Study") {
                type = 3
            } else if (leaveTpe == "Maternity") {
                type = 4
            } else if (leaveTpe == "Family Responsibility") {
                type = 5
            }
            if (from.isNullOrEmpty() || to.isNullOrEmpty()) {
                hideProgressDialog()
                showErrorMessage("Please select all leave days")
            } else {
                from?.let { it1 ->
                    to?.let { it2 ->
                        setUpVieModel(
                            it1,
                            it2,
                            type,
                            TheUser(id = id)
                        )
                    }
                }
            }
        }
    }

    private fun setUpVieModel(
        fromLeave: String,
        toLeave: String, type: Int, user: TheUser
    ) {
        applyLeaveCallRollViewModel.applyLeaveRequest.removeObservers(this)
        applyLeaveCallRollViewModel.applyLeaveRequest.observe(
            this,
            androidx.lifecycle.Observer { data ->

                data.let {
                    if (it.status) {
                        hideProgressDialog()
                        val builder = AlertDialog.Builder(this@ApplyLeaveActivity)
                        builder.setTitle("Leave Application")
                        builder.setMessage("Leave requested successfully")
                        builder.setPositiveButton("OK") { _, _ ->
                            navigate()
                        }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                    } else {
                        hideProgressDialog()
                        showErrorMessage("Leave request failed")
                    }
                }

            })
        applyLeaveCallRollViewModel.applyLeave(fromLeave, toLeave, type, user)
    }

    private fun navigate() {
        startActivity(
            Intent(this@ApplyLeaveActivity, Dashboard::class.java)
        )
    }
}
