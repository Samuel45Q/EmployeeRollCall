package com.sam.employeerollcallapplication.login

import com.sam.employeerollcallapplication.Actions
import com.sam.employeerollcallapplication.R

object LoginScreen {

    fun login(username: String, password: String) {
        Actions.enterText(R.id.username, username)
        Actions.enterText(R.id.userPassword, password)
        Actions.closeSoftKeyboard(R.id.userPassword)
        Actions.clickButton(R.id.btnClickLogin)
    }
}