package com.sam.employeerollcallapplication.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sam.employeerollcallapplication.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*


class Dashboard : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setUpViews()
        setApplyLeaveOnClick()
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
//        val fragmentManager: FragmentManager = supportFragmentManager
//        fragmentManager.apply {
//            fragments.filterIsInstance<ApplyLeaveFragment>().firstOrNull()?.let {
//                beginTransaction().remove(it).commit()
//            }
//            beginTransaction().add(android.R.id.content, ApplyLeaveFragment()).commit()
//        }

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
        super.onBackPressed()
        hideProgressDialog()
        this.finish()
    }
}