package com.example.keki.repositories

import androidx.lifecycle.LiveData
import com.example.keki.data.local.ShoppingItem
import com.example.keki.data.remote.responses.ImageResponse
import com.example.keki.other.Resource

interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun observeAllShoppingItems():LiveData<List<ShoppingItem>>
    fun observeTotalPrice():LiveData<Float>
    suspend fun searchForImage(imageQuery:String): Resource<ImageResponse>

}