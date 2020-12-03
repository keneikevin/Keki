package com.example.keki

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before

import org.junit.Test


class ResourceComparerTest{
    private lateinit var  resourceComparer:ResourceComparer
    private lateinit var context: Context
    @Before
    fun setup(){
        resourceComparer= ResourceComparer()
        context = ApplicationProvider.getApplicationContext<Context>()
    }
    @Test
    fun stringResourceSameAsGivenString(){
        val result = resourceComparer.isEqual(context, R.string.app_name, "Keki")
       assertThat(result).isTrue()
    }
    @Test
    fun stringResourceDifferentAsGivenString(){
        val result = resourceComparer.isEqual(context, R.string.app_name, "keki")
        assertThat(result).isFalse()
    }
    @After
    fun teardown(){

    }
}