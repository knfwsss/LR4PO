package com.example.lr4po

import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun oneTerm_isXOver129() {
        assertEquals("1", calculate("129", "1"))
    }

    @Test
    fun twoTerms_addExactly() {
        assertEquals("770", calculate("82689", "2"))
    }

    @Test
    fun zeroX_isZero() {
        assertEquals("0", calculate("0", "3"))
    }

    @Test
    fun negativeX_changesSign() {
        assertEquals("-1", calculate("-129", "1"))
    }

    @Test
    fun comma_isAccepted() {
        assertEquals("1", calculate("129,0", "1"))
    }

    @Test
    fun blankInput_asksForValues() {
        assertEquals("Введите x и n", calculate(" ", "2"))
    }

    @Test
    fun letters_areRejected() {
        assertEquals("Некорректное число x", calculate("abc", "2"))
    }

    @Test
    fun zeroN_isRejected() {
        assertEquals("n должно быть натуральным числом", calculate("1", "0"))
    }

    @Test
    fun fractionalN_isRejected() {
        assertEquals("n должно быть натуральным числом", calculate("1", "2.5"))
    }

    @Test
    fun hugeN_isRejected() {
        assertEquals("n слишком большое для вычисления", calculate("1", "500"))
    }
}
