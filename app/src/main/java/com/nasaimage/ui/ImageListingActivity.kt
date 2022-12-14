package com.nasaimage.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.nasaimage.picturesapp.R
import com.nasaimage.picturesapp.databinding.ActivityImageListingBinding
import com.nasaimage.ui.imageDetails.ImageDetailsActivity
import com.nasaimage.utils.PageState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageListingActivity : AppCompatActivity() {
    private val imagesListingViewModel: ImageListingViewModel by viewModel()
    private lateinit var binding: ActivityImageListingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageListingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listenToPageStateChanges()
    }

    private fun listenToPageStateChanges() {
        imagesListingViewModel.pageState.observe(this) {
            when (it) {
                PageState.Data -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvMessage.visibility = View.GONE
                    binding.rvImageListing.visibility = View.VISIBLE
                    imagesListingViewModel.images.observe(this) {
                        binding.rvImageListing.layoutManager = GridLayoutManager(this, 2)
                        binding.rvImageListing.adapter = RVImageListingAdapter(it) { index ->
                            ImageDetailsActivity.start(this, index)
                        }
                    }
                }
                PageState.EmptyData -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvImageListing.visibility = View.GONE
                    binding.tvMessage.visibility = View.VISIBLE
                    binding.tvMessage.text = getString(R.string.err_empty_data)
                }
                PageState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvImageListing.visibility = View.GONE
                    binding.tvMessage.visibility = View.VISIBLE
                    binding.tvMessage.text = getString(R.string.err_image_fetch)
                }
                PageState.InProgress -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvImageListing.visibility = View.GONE
                    binding.tvMessage.visibility = View.GONE
                }
                else -> {
                }
            }
        }
    }
}