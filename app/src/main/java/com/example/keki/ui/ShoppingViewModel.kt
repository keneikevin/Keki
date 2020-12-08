package com.example.keki.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keki.data.local.ShoppingItem
import com.example.keki.data.remote.responses.ImageResponse
import com.example.keki.other.Constants
import com.example.keki.other.Event
import com.example.keki.other.Resource
import com.example.keki.repositories.ShoppingRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ShoppingViewModel @ViewModelInject constructor(
        private val repository: ShoppingRepository
        ):ViewModel() {
        val shoppingItems = repository.observeAllShoppingItems()
        val totalPrice = repository.observeTotalPrice()

        private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
        val images:LiveData<Event<Resource<ImageResponse>>> = _images

        private val _curImageUrl = MutableLiveData<String>()
        val curlImageUrl:LiveData<String> = _curImageUrl

        private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
        val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

        //HOMWORK
        fun setCurlImageUrl(url: String){
                _curImageUrl.postValue(url)
        }
        fun insertShoppingItemToDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
                repository.insertShoppingItem(shoppingItem)
        }
        fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
                repository.deleteShoppingItem(shoppingItem)
        }

        fun insertShoppingItem(name:String, amountString: String, priceString:String){
                if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
                        _insertShoppingItemStatus.postValue(Event(Resource.error("The field cannot be empty", null)))
                        return
                }
                if (name.length > Constants.MAX_NAME_LENGTH){
                        _insertShoppingItemStatus.postValue(Event(Resource.error("The name of the item "+
                                "must not exceed ${Constants.MAX_NAME_LENGTH} characters", null)))
                        return
                }
                if (priceString.length > Constants.MAX_PRICE){
                        _insertShoppingItemStatus.postValue(Event(Resource.error("The price of the item"+
                                "must not exceed ${Constants.MAX_PRICE} characters", null)))
                        return
                }
                val amount = try {
                    amountString.toInt()
                } catch (e: Exception){
                        _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter a valid amount",null)))
                return
                }
                val shoppingItem = ShoppingItem(name, amount, priceString.toFloat(),_curImageUrl.value ?:"")
                insertShoppingItemToDb(shoppingItem)
                setCurlImageUrl("")
                _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
        }
        //HOMWORK
        fun searchForImage(imageQuery:String){
                if (imageQuery.isEmpty()){
                        return
                        }
                _images.value = Event(Resource.loading(null))
                viewModelScope.launch {
                val response = repository.searchForImage(imageQuery)
                        _images.value = Event(response)
                }
        }

}