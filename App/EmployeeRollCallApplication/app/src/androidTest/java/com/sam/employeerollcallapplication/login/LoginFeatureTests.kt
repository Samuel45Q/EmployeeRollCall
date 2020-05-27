package com.sam.employeerollcallapplication.login

import com.mauriciotogneri.greencoffee.GreenCoffeeTest
import com.mauriciotogneri.greencoffee.ScenarioConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class LoginFeatureTests(scenario: ScenarioConfig?) : GreenCoffeeTest(scenario) {

    @Test
    fun test() {
        start(LoginSteps())
    }

    companion object {
        @Parameterized.Parameters(name = "{0}")
        @JvmStatic
        fun scenarios(): Iterable<ScenarioConfig> = buildScenarios("login")
    }
}