package com.example.keki.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.keki.data.local.ShoppingItem
import com.example.keki.data.remote.responses.ImageResponse
import com.example.keki.other.Resource

class FakeShoppingRepositoryAndroidTest:ShoppingRepository {
    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private var observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError =false
    private fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }
    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }
    fun getTotalPrice():Float{
        return shoppingItems.sumByDouble {it.price.toDouble()}.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
       shoppingItems.add(shoppingItem)
        refreshLiveData()
    }



    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
       shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }


    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
       return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
      return if (shouldReturnNetworkError){
          Resource.error("Error", null)
      } else{
          Resource.success(ImageResponse(listOf(),0,0))
      }
    }
}