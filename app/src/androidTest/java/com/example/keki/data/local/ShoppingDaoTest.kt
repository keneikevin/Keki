package com.example.keki.data.local


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.keki.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database:ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup(){
    database =Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        ShoppingItemDatabase::class.java
    ).allowMainThreadQueries().build()
        dao = database.shoppingDAo()
    }

    @Test
    fun insertShoppingItem()= runBlockingTest{
        val shoppingItem = ShoppingItem("Name",1,1F,"url", id = 1)
        dao.insertShoppingItem(shoppingItem)
        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()
    assertThat(allShoppingItems.contains(shoppingItem))
    }
    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("Name",1,1F,"Url", id = 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }
    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem2 = ShoppingItem("Nam",1,2F,"Url", id = 2)
        val shoppingItem3 = ShoppingItem("Nae",1,3F,"Url", id = 3)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)
        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPriceSum).isEqualTo(1*2F+1*3F)
    }

}