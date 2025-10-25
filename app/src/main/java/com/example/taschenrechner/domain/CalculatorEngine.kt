package com.example.taschenrechner.domain

import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class CalculatorEngine @Inject constructor() {
    companion object {
        private val operators = setOf("+", "-", "*", "/", "^", "%")
        private val functions = setOf("sin", "cos", "tan", "log", "ln", "√")
    }

    fun evaluate(expression: String): Result<Double> {
        return try {
            val tokens = tokenize(expression)
            val rpn = toRPN(tokens)
            val value = evalRPN(rpn)
            Result.success(value)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun tokenize(expr: String): List<String> {
        val s = expr.replace(" ", "")
        val tokens = mutableListOf<String>()
        var i = 0
        while (i < s.length) {
            val c = s[i]
            when {
                c.isDigit() || c == '.' -> {
                    val sb = StringBuilder()
                    while (i < s.length && (s[i].isDigit() || s[i] == '.')) {
                        sb.append(s[i])
                        i++
                    }
                    tokens.add(sb.toString())
                }

                c.isLetter() -> {
                    val sb = StringBuilder()
                    while (i < s.length && s[i].isLetter()) {
                        sb.append(s[i]); i++
                    }
                    tokens.add(sb.toString())
                }

                c == '(' || c == ')' -> {
                    tokens.add(c.toString()); i++
                }

                "+-*/^%√,".contains(c) -> {
                    tokens.add(c.toString()); i++
                }

                else -> throw IllegalArgumentException("Invalid char: $c")
            }
        }
        return tokens
    }

    private fun precedence(op: String): Int = when (op) {
        "+", "-" -> 2
        "*", "/", "%" -> 3
        "^" -> 4
        else -> 0
    }


    /**
     * Converts a list of tokens into Reverse Polish Notation (RPN) using the
     * Shunting Yard algorithm by Edsger Dijkstra.
     * This simplifies evaluation by removing the need for parentheses.
     * Learn more: https://en.wikipedia.org/wiki/Reverse_Polish_notation
     */
    private fun toRPN(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val ops = ArrayDeque<String>()
        var prevToken: String? = null // to track previous token

        for (t in tokens) {
            if (prevToken != null && operators.contains(prevToken) && operators.contains(t)) {
                throw IllegalArgumentException("Two operators in a row: '$prevToken$t'")
            }
            when {
                t.toDoubleOrNull() != null -> output.add(t)
                functions.contains(t.lowercase()) -> ops.addFirst(t)
                t == "," -> {
                    while (ops.isNotEmpty() && ops.first() != "(") {
                        output.add(ops.removeFirst())
                    }
                }

                operators.contains(t) -> {
                    // pop operators with higher or equal precedence or functions from the stack
                    while (ops.isNotEmpty() && (
                                (operators.contains(ops.first()) && precedence(ops.first()) >= precedence(
                                    t
                                ))
                                        || functions.contains(ops.first().lowercase())
                                )
                    ) {
                        output.add(ops.removeFirst())
                    }
                    ops.addFirst(t)
                }

                t == "(" -> ops.addFirst(t)
                t == ")" -> { // right parenthesis triggers pop until '('
                    while (ops.isNotEmpty() && ops.first() != "(") output.add(ops.removeFirst())
                    if (ops.isEmpty()) throw IllegalArgumentException("Mismatched parentheses")
                    ops.removeFirst()
                    // if there is a function on top, move it to output
                    if (ops.isNotEmpty() && functions.contains(ops.first().lowercase())) {
                        output.add(ops.removeFirst())
                    }
                }

                else -> throw IllegalArgumentException("Unknown token: $t")
            }
            prevToken = t
        }
        while (ops.isNotEmpty()) {
            val o = ops.removeFirst()
            if (o == "(" || o == ")") throw IllegalArgumentException("Mismatched parentheses")
            output.add(o)
        }
        return output
    }

    /**
     * Evaluates a list of tokens in Reverse Polish Notation (RPN) and returns the result as Double.
     */
    private fun evalRPN(rpn: List<String>): Double {
        val stack = ArrayDeque<Double>()
        for (t in rpn) {
            when {
                t.toDoubleOrNull() != null -> stack.addFirst(t.toDouble()) // push numbers onto the stack
                operators.contains(t) -> { // apply arithmetic operators
                    val b = stack.removeFirstOrNull()
                        ?: throw IllegalArgumentException("Invalid expression")
                    val a = stack.removeFirstOrNull() ?: 0.0
                    val res = when (t) {
                        "+" -> a + b
                        "-" -> a - b
                        "*" -> a * b
                        "/" -> if (b == 0.0) throw ArithmeticException("Division by zero") else a / b
                        "^" -> a.pow(b)
                        "%" -> a % b
                        else -> throw IllegalArgumentException("Unknown op $t")
                    }
                    stack.addFirst(res)
                }

                functions.contains(t.lowercase()) -> { // apply functions to top of the stack
                    val arg = stack.removeFirstOrNull()
                        ?: throw IllegalArgumentException("Missing arg for $t")
                    val res = when (t.lowercase()) {
                        "sin" -> sin(arg)
                        "cos" -> cos(arg)
                        "tan" -> tan(arg)
                        "log" -> log10(arg)
                        "ln" -> ln(arg)
                        "√" -> sqrt(arg)
                        else -> throw IllegalArgumentException("Unknown func $t")
                    }
                    stack.addFirst(res)
                }

                else -> throw IllegalArgumentException("Unexpected token in RPN: $t")
            }
        }
        return stack.firstOrNull() ?: 0.0
    }
}