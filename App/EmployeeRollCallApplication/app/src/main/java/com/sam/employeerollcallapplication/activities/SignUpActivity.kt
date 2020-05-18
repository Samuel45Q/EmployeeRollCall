package com.sam.employeerollcallapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.utils.ResourceStatus
import com.sam.employeerollcallapplication.viewmodels.ViewModels
import kotlinx.android.synthetic.main.activity_main.btnLogin
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {

    private val viewModels: ViewModels by lazy { getViewModel(ViewModels::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setSignUpOnClick()
        setRequestSignUpOnClick()
    }

    private fun setRequestSignUpOnClick() {
        signUpRequest.setOnClickListener {
            val firstName = firstName.text.toString()
            val lastName = lastName.text.toString()
            val idNoText = idNoText.text.toString()
            val cellphone = cellphone.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            if (firstName.isEmpty() || lastName.isEmpty() || idNoText.isEmpty()
                || cellphone.isEmpty() || email.isEmpty() || password.isEmpty()
            ) {
                Toast.makeText(
                        this@SignUpActivity, "All fields must be filled",
                        Toast.LENGTH_LONG
                    )
                    .show()
            } else {
                setupViewModel(firstName, lastName, idNoText, cellphone, email, password)
            }
        }
    }

    private fun setupViewModel(
        firstName: String, lastName: String, idNumber: String,
        cellphoneNumber: String, email: String, password: String
    ) {

        viewModels.signUpLiveData(firstName, lastName, idNumber, cellphoneNumber, email, password)!!
            .observe(this,
                Observer { resource ->
                    when (resource.status) {
                        ResourceStatus.SUCCESS -> {
                            resource.data?.let {
                                if (it.status) {
                                    startActivity(
                                        Intent(
                                            this@SignUpActivity,
                                            MainActivity::class.java
                                        )
                                    )
                                }
                            }
                        }
                        ResourceStatus.ERROR -> {
                            showErrorMessage("Registration failed")
                        }
                        ResourceStatus.LOADING -> Unit
                    }
                })
    }

    private fun setSignUpOnClick() {
        btnLogin.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModels.clearLiveData()
    }
}
