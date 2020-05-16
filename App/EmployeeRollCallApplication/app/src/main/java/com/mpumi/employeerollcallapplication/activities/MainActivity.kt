package com.mpumi.employeerollcallapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.mpumi.employeerollcallapplication.R
import com.mpumi.employeerollcallapplication.utils.ResourceStatus
import com.mpumi.employeerollcallapplication.viewmodels.ViewModels
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private val viewModels: ViewModels by lazy { getViewModel(ViewModels::class.java) }


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
            showProgressDialog("Logging in....")
            val emailText = username.text.toString()
            val textUsername = userPassword.text.toString()
            if (emailText.isEmpty() || textUsername.isEmpty()) {
                showErrorMessage("Failed to login")
                showErrorMessage("Username/Password cannot be empty")
            } else {
                setupViewModel(emailText, textUsername)
            }
        }
    }

    private fun setupViewModel(emailText: String, textUsername: String) {
        viewModels.loginRequestLiveData(emailText, textUsername)
            ?.observe(this, Observer { resource ->
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
                        hideProgressDialog()
                        showErrorMessage("Failed to login")
                    }
                    ResourceStatus.LOADING -> Unit
                }
            })
    }

    private fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideProgressDialog()
        this.finish()
    }
}
