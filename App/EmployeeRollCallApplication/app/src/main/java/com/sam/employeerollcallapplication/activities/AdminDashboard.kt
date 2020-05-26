package com.sam.employeerollcallapplication.activities

import android.content.Intent
import android.os.Bundle
import com.sam.employeerollcallapplication.R

class AdminDashboard : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
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
