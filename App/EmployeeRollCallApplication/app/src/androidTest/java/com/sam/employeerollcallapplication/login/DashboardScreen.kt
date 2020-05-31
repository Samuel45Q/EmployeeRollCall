package com.sam.employeerollcallapplication.login

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.sam.employeerollcallapplication.R

object DashboardScreen {

    fun assertIsOnHomeFragment() {
        Espresso.onView(withId(R.id.dashboard))
            .check(matches(isCompletelyDisplayed()))
    }
}