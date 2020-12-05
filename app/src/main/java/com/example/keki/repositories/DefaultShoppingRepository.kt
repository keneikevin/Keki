package com.example.keki.repositories

import androidx.lifecycle.LiveData
import com.example.keki.data.local.ShoppingDao
import com.example.keki.data.local.ShoppingItem
import com.example.keki.data.remote.responses.ImageResponse
import com.example.keki.other.Resource
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
        private val shoppingDao: ShoppingDao,
        private val pixabayAPI: com.example.keki.data.remote.responses.PixabayAPI
):ShoppingRepository{
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
                val response=pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?:Resource.error("An unKnown error occured", null)}

                else{
                    Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception){
            Resource.error("Couldn't reach the server.Check your internet conection", null)
        }
    }
}