package com.nasaimage.di

import com.nasaimage.ImagesRepository
import com.nasaimage.data.AssetImageService
import com.nasaimage.data.ImagesService
import com.nasaimage.ui.ImageListingViewModel
import com.nasaimage.utils.FileUtils
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    //Application Level dependencies goes here
    single {
        FileUtils()
    }
}

val serviceModules = module {
    //Service Layer dependencies goes here
    single<ImagesService> {
        AssetImageService(get(), get())
    }
}

val repositoryModules = module {
    //Repositories dependencies goes here
    single {
        ImagesRepository(get())
    }
}

val viewModelModules = module {
    //ViewModels dependencies goes here
    viewModel {
        ImageListingViewModel(get())
    }
}