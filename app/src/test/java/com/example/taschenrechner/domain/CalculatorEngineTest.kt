package com.example.taschenrechner.domain

import junit.framework.TestCase.assertTrue
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorEngineTest {
    private val calc = CalculatorEngine()

    @Test
    fun testBasicOperations() {
        assertEquals(7.0, calc.evaluate("3 + 4").getOrThrow())
        assertEquals(2.0, calc.evaluate("5 - 3").getOrThrow())
        assertEquals(15.0, calc.evaluate("3 * 5").getOrThrow())
        assertEquals(4.0, calc.evaluate("8 / 2").getOrThrow())
        assertEquals(2.0, calc.evaluate("10 % 4").getOrThrow())
        assertEquals(8.0, calc.evaluate("2 ^ 3").getOrThrow())
    }

    @Test
    fun testFunctions() {
        assertEquals(sqrt(16.0), calc.evaluate("√16").getOrThrow())
        assertEquals(0.0, calc.evaluate("sin(0)").getOrThrow(), 1e-9)
        assertEquals(1.0, calc.evaluate("cos(0)").getOrThrow(), 1e-9)
        assertEquals(0.0, calc.evaluate("tan(0)").getOrThrow(), 1e-9)
        assertEquals(2.0, calc.evaluate("log(100)").getOrThrow(), 1e-9) // log10(100)
        assertEquals(1.0, calc.evaluate("ln(${Math.E})").getOrThrow(), 1e-9)
    }

    @Test
    fun testComplexExpression() {
        val expr = "3 + 4 * 2 / (1 - 5) ^ 2"
        assertEquals(3.5, calc.evaluate(expr).getOrThrow(), 1e-9)
    }

    @Test
    fun testInvalidExpression() {
        val result = calc.evaluate("3 + * 5")
        assertTrue(result.isFailure)
    }

    @Test
    fun testDivisionByZero() {
        val result = calc.evaluate("10 / 0")
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is ArithmeticException)
    }
}