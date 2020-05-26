package com.sam.employeerollcallapplication.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sam.employeerollcallapplication.R
import com.sam.employeerollcallapplication.repository.SignUpRepositoryFactory
import com.sam.employeerollcallapplication.utils.DialogUtil
import com.sam.employeerollcallapplication.viewmodels.SignUpCallRollViewModel
import com.sam.employeerollcallapplication.viewmodels.SignUpViewModelFactory
import kotlinx.android.synthetic.main.activity_main.btnLogin
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {

    private lateinit var signUpCallRollViewModel: SignUpCallRollViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpCallRollViewModel =
            ViewModelProviders.of(
                this,
                SignUpViewModelFactory(SignUpRepositoryFactory.createSignUpRepository())
            ).get(SignUpCallRollViewModel::class.java)

        setSignUpOnClick()
        setRequestSignUpOnClick()
    }

    private fun setRequestSignUpOnClick() {
        signUpRequest.setOnClickListener {
            showProgressDialog("Registration in progress.....")
            val firstName = firstName.text.toString()
            val lastName = lastName.text.toString()
            val idNoText = idNoText.text.toString()
            val cellphone = cellphone.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            if (firstName.isEmpty() || lastName.isEmpty() || idNoText.isEmpty()
                || cellphone.isEmpty() || email.isEmpty() || password.isEmpty()
            ) {
                hideProgressDialog()
                DialogUtil.showDialog(
                    this,
                    message = "All fields must be filled",
                    positiveButton = R.string.ok
                )
            } else {
                setupViewModel(firstName, lastName, idNoText, cellphone, email, password)
            }
        }
    }

    private fun setupViewModel(
        firstName: String, lastName: String, idNumber: String,
        cellphoneNumber: String, email: String, password: String
    ) {
        signUpCallRollViewModel.signURequest.removeObservers(this)

        signUpCallRollViewModel.signURequest.observe(this, Observer { data ->

            data.let {
                if (it.status) {
                    hideProgressDialog()
                    val builder = AlertDialog.Builder(this@SignUpActivity)
                    builder.setTitle("Registration Confirmation")
                    builder.setMessage("Successfully Registered")
                    builder.setPositiveButton("OK") { _, _ ->
                        navigate()
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                } else {
                    hideProgressDialog()
                    showErrorMessage("Registration Failed")
                }
            }
        })
        signUpCallRollViewModel.signUp(
            firstName,
            lastName,
            idNumber,
            cellphoneNumber,
            email,
            password
        )

    }

    private fun navigate() {
        startActivity(
            Intent(this@SignUpActivity, MainActivity::class.java)
        )
    }

    private fun setSignUpOnClick() {
        btnLogin.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
        }
    }
}
