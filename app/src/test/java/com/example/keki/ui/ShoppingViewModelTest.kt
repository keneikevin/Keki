package com.example.keki.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.keki.MainCoroutineRule
import com.example.keki.getOrAwaitValueTest
import com.example.keki.other.Constants
import com.example.keki.other.Status
import com.example.keki.repositories.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ShoppingViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var viewModel: ShoppingViewModel
    @Before
    fun setup(){
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }
    @Test
    fun `insert shopping item with empty field returns error`(){
        viewModel.insertShoppingItem("name","","3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentifNothandled()?.status).isEqualTo(Status.ERROR)
}
    @Test
    fun `insert shopping item with too long name, returns error`() {
        val string = buildString {
            for(i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem(string, "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentifNothandled()?.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insert shopping item with too long price return error`(){
        val string= buildString {
            for (i in 1..Constants.MAX_PRICE + 1)
                append(1)
        }
        viewModel.insertShoppingItem("n","999999999999999999","5.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentifNothandled()?.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `insert shopping item with valid input returns success`(){
        viewModel.insertShoppingItem("name","3","3.0")
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentifNothandled()?.status).isEqualTo(Status.SUCCESS)
    }



}