package com.sam.employeerollcallapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.repository.LoginRepositoryFactory
import com.sam.employeerollcallapplication.viewmodels.LoginCallRollViewModel
import com.sam.employeerollcallapplication.viewmodels.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnSignup
import kotlinx.android.synthetic.main.activity_sign_up.*


class MainActivity : BaseActivity() {

    private lateinit var mainActivityViewModel: LoginCallRollViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel =
            ViewModelProviders.of(
                    this,
                    LoginViewModelFactory(LoginRepositoryFactory.createLoginRepository())
                )
                .get(LoginCallRollViewModel::class.java)
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
            showProgressDialog("Logging in....")
            if (emailText.isEmpty() || textUsername.isEmpty()) {
                showErrorMessage("Username/Password cannot be empty")
            } else {
                setupViewModel(emailText, textUsername)
            }
        }
    }

    private fun setupViewModel(userName: String, password1: String) {
        mainActivityViewModel.loginRequest.removeObservers(this)
        mainActivityViewModel.loginRequest.observe(this, Observer { data ->
            data?.user?.let { user ->
                if (user.status) {
                    hideProgressDialog()
                    if (user.role != null && (user.role == "Admin" || user.role == "admin")) {
                        startActivity(Intent(this@MainActivity, AdminDashboard::class.java))
                    } else {
                        val sharedPreferences =
                            getSharedPreferences("user", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("userName", "${user.firstName} ${user.lastName}")
                            putInt("employeeId", user.id)
                            putString("role", user.role)
                            putInt("annualLeave", user.leaveBalance.annual)
                            putInt("sickLeave", user.leaveBalance.sick)
                            putInt("studyLeave", user.leaveBalance.study)
                            putInt("maternityLeave", user.leaveBalance.maternity)
                        }.apply()
                        startActivity(Intent(this@MainActivity, Dashboard::class.java))
                    }

                } else {
                    hideProgressDialog()
                    showErrorMessage(user.message)
                }
            } ?: showErrorMessage("Invalid Username/Password")

        })
        mainActivityViewModel.login(userName, password1)
    }
}

