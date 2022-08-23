package com.nasaimage.data

import com.nasaimage.models.ImageModel
import com.nasaimage.utils.Result

interface ImagesService {
    suspend fun getImages(): Result<List<ImageModel>>
}