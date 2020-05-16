package com.mpumi.employeerollcallapplication.activities

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mpumi.employeerollcallapplication.R
import com.mpumi.employeerollcallapplication.models.Attachment
import com.mpumi.employeerollcallapplication.models.TheUser
import com.mpumi.employeerollcallapplication.models.User
import com.mpumi.employeerollcallapplication.utils.ResourceStatus
import com.mpumi.employeerollcallapplication.viewmodels.ViewModels
import kotlinx.android.synthetic.main.activity_apply_leave.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class ApplyLeaveActivity : BaseActivity() {

    private var cal: Calendar = Calendar.getInstance()
    private val viewModels: ViewModels by lazy { getViewModel(ViewModels::class.java) }

    lateinit var from: String
    lateinit var to: String
    lateinit var leaveTpe: String
    var id by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        id = sharedPreferences.getInt("employeeId", 0)
        setOnClickApplyLeave()
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
    }

    private fun updateStartDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val apiFormat = "yyyy-mm-ddThh:mm:ss" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        from = apiFormat.format(cal.time)
        start_date_text.text = sdf.format(cal.time)
    }

    private fun updateEndDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val apiFormat = "yyyy-mm-ddThh:mm:ss"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        to = apiFormat.format(cal.time)
        end_date_text.text = sdf.format(cal.time)
    }

    private fun setOnClickApplyLeave() {
        submitLeave.setOnClickListener {
            var type: Int = 0

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
            setUpVieModel(from, to, type, TheUser(id = id))
        }
    }

    private fun setUpVieModel(
        fromLeave: String,
        toLeave: String, type: Int, user: TheUser
    ) {
        viewModels.applyLeaveLive(fromLeave, toLeave, type, user)
            ?.observe(this, androidx.lifecycle.Observer { resource ->
                when (resource.status) {
                    ResourceStatus.SUCCESS -> {
                        resource.data?.let {
                            if (it.status) {
                                startActivity(
                                    Intent(
                                        this@ApplyLeaveActivity,
                                        Dashboard::class.java
                                    )
                                )
                            }
                        }
                    }
                    ResourceStatus.ERROR -> {
                        showErrorMessage("Failed to apply leave")
                    }
                    ResourceStatus.LOADING -> Unit
                }
            })
    }
}
