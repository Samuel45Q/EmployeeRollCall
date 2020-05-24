package com.sam.employeerollcallapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.models.LoginResponse
import com.sam.employeerollcallapplication.utils.DialogUtil
import com.sam.employeerollcallapplication.utils.Resource
import com.sam.employeerollcallapplication.utils.ResourceStatus
import com.sam.employeerollcallapplication.viewmodels.ViewModels
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private val viewModels: ViewModels by lazy { getViewModel(ViewModels::class.java) }
    private var loginLiveData: LiveData<Resource<LoginResponse>>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSignUpOnClick()
        setLoginOnClick()
    }

    private fun setSignUpOnClick() {
        btnSignup.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
        }
    }

    private fun setLoginOnClick() {
        btnClickLogin.setOnClickListener {
            val emailText = username.text.toString()
            val textUsername = userPassword.text.toString()
            removeObservers(emailText, textUsername)
            showProgressDialog("Logging in....")
            if (emailText.isEmpty() || textUsername.isEmpty()) {
                showErrorMessage("Failed to login")
                showErrorMessage("Username/Password cannot be empty")
            } else {
                setupViewModel(emailText, textUsername)
            }
        }
    }

    private fun setupViewModel(username: String, password: String) {
        removeObservers(username, password)
        loginLiveData = viewModels.loginRequestLiveData(username, password)
        loginLiveData?.observe(this, Observer { resource ->
            when (resource.status) {
                ResourceStatus.SUCCESS -> {
                    resource.data?.user.let {
                        if (it!!.status) {
                            hideProgressDialog()
                            val sharedPreferences =
                                getSharedPreferences("user", Context.MODE_PRIVATE)
                            sharedPreferences.edit().apply {
                                putString("userName", "${it.firstName} ${it.lastName}")
                                putInt("employeeId", it.id)
                                putString("role", it.role)
                                putInt("annualLeave", it.leaveBalance.annual)
                                putInt("sickLeave", it.leaveBalance.sick)
                                putInt("studyLeave", it.leaveBalance.study)
                                putInt("maternityLeave", it.leaveBalance.maternity)
                            }.apply()
                            startActivity(Intent(this@MainActivity, Dashboard::class.java))
                        }
                    }
                }
                ResourceStatus.ERROR -> {
                    if (loginLiveData!!.hasObservers()) {
                        loginLiveData!!.removeObservers(this)
                    }
                    hideProgressDialog()
                    showErrorMessage("Failed to login")
                }
                ResourceStatus.LOADING -> Unit
            }
        })
    }

    private fun removeObservers(userName: String, password: String) {
        val observable: LiveData<Resource<LoginResponse>> =
            viewModels.loginRequestLiveData(userName, password)!!
        if (observable.hasObservers()
        ) {
            hideProgressDialog()
            observable.removeObservers(this)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideProgressDialog()
        this.finish()
    }
}

