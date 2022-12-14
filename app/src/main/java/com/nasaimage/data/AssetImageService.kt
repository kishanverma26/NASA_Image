package com.nasaimage.data

import android.content.Context
import com.nasaimage.models.ImageModel
import com.nasaimage.utils.FileUtils
import com.nasaimage.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AssetImageService(private val context: Context, private val fileUtils: FileUtils) :
    ImagesService {
    override suspend fun getImages(): Result<List<ImageModel>> {
        try {
            return withContext(Dispatchers.IO) {
                val imagesJsonString = fileUtils.loadDataFromAsset(context, "data.json")
                    ?: return@withContext Result.Error(Exception("Images Not Found"))
                val imageList = Json.decodeFromString<List<ImageModel>>(
                    imagesJsonString
                )
                return@withContext Result.Success(imageList)
            }
        } catch (ex: Exception) {
            return Result.Error(ex)
        }
    }
}