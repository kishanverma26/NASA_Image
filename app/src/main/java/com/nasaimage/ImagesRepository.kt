package com.nasaimage

import com.nasaimage.data.ImagesService
import com.nasaimage.models.ImageModel
import com.nasaimage.utils.Result

class ImagesRepository(private val imagesService: ImagesService) {
    suspend fun getImages(): Result<List<ImageModel>> = imagesService.getImages()
}