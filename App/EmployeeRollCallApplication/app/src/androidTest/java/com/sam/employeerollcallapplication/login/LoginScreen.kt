package com.sam.employeerollcallapplication.login

import com.sam.employeerollcallapplication.Actions
import com.sam.employeerollcallapplication.R
import org.junit.Assume

object LoginScreen {

//    fun goToLoginScreen() {
//        Actions.clickButton(R.id.button_login)
//    }
//
//    fun tapRegisiter() {
//        Actions.clickButton(R.id.button_register)
//    }
//
//    fun tapLogin() {
//        Actions.clickButton(R.id.button_login)
//    }

    fun login(username: String, password: String) {
//        Assume.assumeTrue("UI tests assume that the non web breakout login screen will be used, but it was not launched. The UI tests cannot continue", GetState.isDisplayed(R.id.non_breakout_logout))
        Actions.enterText(R.id.username, username)
        Actions.enterText(R.id.userPassword, password)
        Actions.closeSoftKeyboard(R.id.password)
        Actions.clickButton(R.id.btnClickLogin)
    }
}