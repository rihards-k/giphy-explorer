package com.test.giphyexplorer.di

import com.test.giphyexplorer.data.GiphyRepository
import com.test.giphyexplorer.data.GiphyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGiphyRepository(impl: GiphyRepositoryImpl): GiphyRepository
}