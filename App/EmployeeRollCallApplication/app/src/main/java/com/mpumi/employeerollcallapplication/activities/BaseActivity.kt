package com.mpumi.employeerollcallapplication.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mpumi.employeerollcallapplication.R
import com.mpumi.employeerollcallapplication.utils.DialogUtil
import com.mpumi.employeerollcallapplication.utils.EmployeeRollLoadingDialog

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog: EmployeeRollLoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        progressDialog = EmployeeRollLoadingDialog(this)
    }

    protected fun <T : ViewModel> getViewModel(classz: Class<T>) =
        ViewModelProvider(this).get(classz)

    protected fun hideProgressDialog() {
        progressDialog.hide()
    }

    protected fun showProgressDialog(message: String) {
        progressDialog.show(message)
    }

    protected fun showErrorMessage(message: String?) {
        val dialogMessage = message ?: getString(R.string.error_general)
        DialogUtil.showDialog(this, dialogMessage, positiveButton = R.string.ok) { finish() }
    }

}