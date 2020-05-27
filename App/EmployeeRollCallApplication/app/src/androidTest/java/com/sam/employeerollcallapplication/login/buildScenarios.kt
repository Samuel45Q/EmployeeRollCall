package com.sam.employeerollcallapplication.login

import com.mauriciotogneri.greencoffee.GreenCoffeeConfig
import com.mauriciotogneri.greencoffee.ScenarioConfig
import java.util.*

fun buildScenarios(name: String): Iterable<ScenarioConfig> = GreenCoffeeConfig()
    .withFeatureFromAssets("assets/$name.feature")
    .scenarios(Locale("en", "GB"))