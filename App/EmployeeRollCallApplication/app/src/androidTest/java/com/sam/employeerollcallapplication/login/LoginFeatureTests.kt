package com.sam.employeerollcallapplication.login

import androidx.test.rule.ActivityTestRule
import com.mauriciotogneri.greencoffee.GreenCoffeeTest
import com.mauriciotogneri.greencoffee.ScenarioConfig
import com.sam.employeerollcallapplication.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class LoginFeatureTests(scenario: ScenarioConfig?) : GreenCoffeeTest(scenario) {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    private fun getActivity() = activityRule.activity

    @Test
    fun test() {
        getActivity()
        start(LoginSteps())
    }

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun scenarios(): Iterable<ScenarioConfig> = buildScenarios("login")
    }
}