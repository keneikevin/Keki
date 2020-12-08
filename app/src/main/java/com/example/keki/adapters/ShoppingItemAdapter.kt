package com.example.keki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.keki.R
import com.example.keki.data.local.ShoppingItem
import com.example.keki.databinding.ItemImageBinding
import com.example.keki.databinding.ItemShoppingBinding
import javax.inject.Inject
class ShoppingItemAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>() {
    private lateinit var binding: ItemShoppingBinding
    class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var shoppingItems: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val binding = ItemShoppingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShoppingItemAdapter.ShoppingItemViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }
    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = shoppingItems[position]
        holder.itemView.apply {
            glide.load(shoppingItem.imageUrl).into(binding.ivShoppingImage)

            binding.tvName.text = shoppingItem.name
            val amountText = "${shoppingItem.amount}x"
            binding.tvShoppingItemAmount.text = amountText
            val priceText = "${shoppingItem.price}€"
            binding.tvShoppingItemPrice.text = priceText
        }
    }
}

