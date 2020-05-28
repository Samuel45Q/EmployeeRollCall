package com.sam.employeerollcallapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.adapter.ListAdapter
import com.sam.employeerollcallapplication.models.User
import kotlinx.android.synthetic.main.activity_admin_dashboard.*


class AdminDashboard : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        val requestedLeave = sharedPreferences.getString("leavesRequested", "")
        val user = Gson().fromJson(requestedLeave, User::class.java)
        Log.d("requestedLeave", "" + user)

        requestedLeavesList.apply {
            layoutManager = LinearLayoutManager(this@AdminDashboard)
            adapter = user.requestedLeave?.let { ListAdapter(it, this@AdminDashboard) }
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
