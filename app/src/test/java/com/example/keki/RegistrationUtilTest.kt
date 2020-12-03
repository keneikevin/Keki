package com.example.keki

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest{

    @Test
    //function signature
    fun `valid input returns true`(){
        //test case
        val result = RegistrationUtil.validateRegistrationInput(
            "Pete",
            "123",
            "123"
        )
        //implementation function
        assertThat(result).isTrue()
    }

    @Test
    fun `empty username`(){


    val result = RegistrationUtil.validateRegistrationInput(
        "",
        "123",
        "123"
    )

        assertThat(result).isFalse()
    }
    @Test
    fun `empty password`(){


        val result = RegistrationUtil.validateRegistrationInput(
            "Peter",
            "",
            ""
        )

        assertThat(result).isFalse()
    }


    @Test
    fun `username already exists `(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
        "123",
        "123"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `password contains less than 2 digits`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
            "12",
            "13"
        )
        assertThat(result).isFalse()
    }
        @Test
    fun `password repeated incorrectly`(){
        val result = RegistrationUtil.validateRegistrationInput(
            "Carl",
            "123",
            "121"
        )
            assertThat(result).isFalse()
    }



}