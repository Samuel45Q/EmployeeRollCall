package com.sam.employeerollcallapplication.login

import com.mauriciotogneri.greencoffee.GreenCoffeeSteps
import com.mauriciotogneri.greencoffee.annotations.Then
import com.mauriciotogneri.greencoffee.annotations.When
import com.sam.employeerollcallapplication.UserTestData

class LoginSteps : GreenCoffeeSteps() {

    @When("I enter a valid username and password")
    fun login() = LoginScreen.login(UserTestData.username, UserTestData.userPassword)

//    @Then("I see the dashboard")
//    fun checkDashboardIsLoaded() {
//        DashboardScreen.assertIsOnHomeFragment()
//    }
}