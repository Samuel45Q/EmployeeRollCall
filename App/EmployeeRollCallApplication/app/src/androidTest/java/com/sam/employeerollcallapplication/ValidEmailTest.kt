package com.sam.employeerollcallapplication

import android.text.TextUtils
import android.util.Patterns
import org.junit.Assert.*
import org.junit.Test


class ValidEmailTest {

    @Test
    fun validEmail_Test_yes() {
        val actual = isValidEmail("sam@gmai.com")
        assertTrue(actual)
    }

    @Test
    fun validEmail_Test_no() {
        val actual = isValidEmail("sam3gmai.com")
        assertFalse(actual)
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}