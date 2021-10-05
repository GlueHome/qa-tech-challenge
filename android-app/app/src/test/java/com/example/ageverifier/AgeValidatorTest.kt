package com.example.ageverifier

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AgeValidatorTest {

    @Test
    fun `Old enough to drink`() {
        val validator = AgeValidator()
        assertTrue(validator.validate(21))
    }

    @Test
    fun `Not old enough to drink`() {
        val validator = AgeValidator()
        assertFalse(validator.validate(12))
    }
}