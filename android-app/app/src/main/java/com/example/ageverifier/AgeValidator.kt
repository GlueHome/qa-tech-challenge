package com.example.ageverifier

class AgeValidator {

    fun validate(age: Int): Boolean {
        return when (age) {
            in 0..18 -> false
            else -> true
        }
    }
}