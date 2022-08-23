package com.nasaimage.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasaimage.ImagesRepository
import com.nasaimage.models.ImageModel
import com.nasaimage.utils.PageState
import com.nasaimage.utils.Result
import kotlinx.coroutines.launch

class ImageListingViewModel(private val imagesRepository: ImagesRepository) : ViewModel() {


    private val _images = MutableLiveData<List<ImageModel>>(listOf())
    val images: LiveData<List<ImageModel>>
        get() = _images

    private val _pageState = MutableLiveData<PageState>(PageState.Initial)
    val pageState: LiveData<PageState>
        get() = _pageState

    private fun fetchImages() {
        viewModelScope.launch {
            _pageState.postValue(PageState.InProgress)
            when (val result = imagesRepository.getImages()) {
                is Result.Error -> _pageState.postValue(PageState.Error)
                is Result.Success -> {
                    result.data.let {
                        _images.postValue(it.sortedByDescending { it.date })
                        if (it.isEmpty()) {
                            _pageState.postValue(PageState.EmptyData)
                        } else {
                            _pageState.postValue(PageState.Data)
                        }
                    }
                }
            }
        }
    }

    init {
        fetchImages()
    }
}