package com.example.keki.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.keki.R
import com.example.keki.adapters.ImageAdapter
import com.example.keki.databinding.FragmentAddShoppingItemBinding
import com.example.keki.other.Status
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AddShoppingItemFragment @Inject constructor(
    val glide: RequestManager
): Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel: ShoppingViewModel
    private lateinit var binding: FragmentAddShoppingItemBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        binding = FragmentAddShoppingItemBinding.bind(view)
        subscribeToObservers()
        binding.btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                binding.etShoppingItemAmount.text.toString(),
                binding.etShoppingItemName.text.toString(),
                binding.etShoppingItemPrice.text.toString()
            )
        }
        binding.ivShoppingImage.setOnClickListener {
        findNavController().navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingFragmentToImagePickFragment()
        )
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurlImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
        private fun subscribeToObservers(){
            viewModel.curlImageUrl.observe(viewLifecycleOwner, Observer {
                glide.load(it).into(binding.ivShoppingImage)
            })
            viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, Observer {
                it.getContentifNothandled()?.let {
                    result->
                    when(result.status) {
                        Status.SUCCESS -> {
                            Snackbar.make(
                                requireView(),
                                "Added Shopping Item",
                                Snackbar.LENGTH_LONG
                            ).show()
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Snackbar.make(
                                requireView(),
                                result.message ?: "An unknown error occcured",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        Status.LOADING -> {

                            /* NO-OP */
                        }
                    }
                }
            })
        }
}