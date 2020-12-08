package com.example.keki.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.keki.R
import com.example.keki.adapters.ImageAdapter
import com.example.keki.databinding.FragmentImagePickBinding
import com.example.keki.other.Constants.GRID_SPAN_COUNT
import javax.inject.Inject

class ImagePickFragment @Inject constructor(
     val imageAdapter: ImageAdapter
):Fragment(R.layout.fragment_image_pick) {
   private lateinit var binding: FragmentImagePickBinding
    lateinit var viewModel: ShoppingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        binding = FragmentImagePickBinding.bind(view)
        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurlImageUrl(it)
        }
    }
    private fun setupRecyclerView(){
        binding.rvImages.apply {
            adapter  = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }
}