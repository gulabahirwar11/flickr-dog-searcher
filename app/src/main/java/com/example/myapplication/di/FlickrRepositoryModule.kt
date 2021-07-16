package com.example.myapplication.di

import com.example.myapplication.data.FlickrRepository
import com.example.myapplication.data.network.ApiFactory
import com.example.myapplication.domain.datasouce.FlickrDataSource
import dagger.Module
import dagger.Provides

@Module
class FlickrRepositoryModule {
    @Provides
    fun provideFlickrRepository() : FlickrDataSource {
        return FlickrRepository(ApiFactory.getInstance())
    }
}