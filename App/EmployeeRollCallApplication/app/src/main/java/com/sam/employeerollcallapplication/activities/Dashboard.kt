package com.sam.employeerollcallapplication.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.models.TheWorkUser
import com.sam.employeerollcallapplication.repository.ClockInAndOutRemotelyRepositoryFactory
import com.sam.employeerollcallapplication.viewmodels.ClockInAndOutViewModel
import com.sam.employeerollcallapplication.viewmodels.ClockInAndOutViewModelFactory
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*


class Dashboard : BaseActivity() {

    private lateinit var clockInAndOutViewModel: ClockInAndOutViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        clockInAndOutViewModel =
            ViewModelProviders.of(
                    this,
                    ClockInAndOutViewModelFactory(ClockInAndOutRemotelyRepositoryFactory.createClockInAndOutRepository())
                )
                .get(ClockInAndOutViewModel::class.java)

        setUpViews()
        setApplyLeaveOnClick()
        toggleSetUp()
    }

    private fun toggleSetUp() {
        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showProgressDialog("Clocking in please wait...")
                workStatus.text = getString(R.string.clocked_in_remotely)
                clockInAndOutViewModel.clockInAndOutResponse.observe(
                    this,
                    androidx.lifecycle.Observer { data ->
                        data.let {
                            if (data.status) {
                                hideProgressDialog()
                                showErrorMessage(data.message)
                            } else {
                                hideProgressDialog()
                                showErrorMessage(data.message)
                            }
                        }
                    })
                clockInAndOutViewModel.clockInAndOut(
                    0,
                    TheWorkUser(sharedPreferences.getInt("employeeId", 0))
                )

            } else {
                showProgressDialog("Clocking out please wait...")
                workStatus.text = getString(R.string.clocked_out)
                clockInAndOutViewModel.clockInAndOutResponse.observe(
                    this,
                    androidx.lifecycle.Observer { data ->
                        data.let {
                            if (data.status) {
                                hideProgressDialog()
                                showErrorMessage(data.message)
                            } else {
                                hideProgressDialog()
                                showErrorMessage(data.message)
                            }
                        }
                    })
                clockInAndOutViewModel.clockInAndOut(
                    1,
                    TheWorkUser(sharedPreferences.getInt("employeeId", 0))
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViews() {
        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        greetingMessage.text = sharedPreferences.getString("userName", "")?.let {
            getGreetingMessage(
                it
            )
        }
        annual_leaves.text = "Annual Leave: ${sharedPreferences.getInt("annualLeave", 0)} day(s)"
        sick_leave.text = "Sick Leave: ${sharedPreferences.getInt("sickLeave", 0)} day(s)"
        study_leave.text = "Study Leave: ${sharedPreferences.getInt("studyLeave", 0)} day(s)"
        family_leave.text = "Family Leave: ${sharedPreferences.getInt("maternityLeave", 0)} day(s)"
        employeeNumber.text = "Employee Number: E-${sharedPreferences.getInt("employeeId", 0)}"
    }

    private fun setApplyLeaveOnClick() {
        btnClickApplyLeave.setOnClickListener {
            navigateToActivity()
        }
    }

    private fun navigateToActivity() {
        startActivity(Intent(this@Dashboard, ApplyLeaveActivity::class.java))
    }

    private fun getGreetingMessage(name: String): String {
        val c = Calendar.getInstance()
        return when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning $name"
            in 12..15 -> "Good Afternoon $name"
            in 16..20 -> "Good Evening $name"
            in 21..23 -> "Good Night $name"
            else -> "Hello $name"
        }
    }

    override fun onBackPressed() {
        hideProgressDialog()
        getSharedPreferences("user", MODE_PRIVATE)
            .edit()
            .clear()
            .apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}
