package com.example.keki


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class HomeworkTest{

    @Test
    fun `braces are set correctly`(){
        val result = Homework.checkBraces("78")
        assertThat(result).isTrue()
    }
    @Test
    fun `returns the n-th fibonacci number`(){
        val result = Homework.fib(5)
        assertThat(result).isEqualTo(7)
    }
}