package com.zhalz.voyageoflife.di

import com.zhalz.voyageoflife.data.repository.auth.AuthRepository
import com.zhalz.voyageoflife.data.repository.auth.AuthRepositoryImpl
import com.zhalz.voyageoflife.data.repository.stories.StoriesRepository
import com.zhalz.voyageoflife.data.repository.stories.StoriesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Singleton
    @Binds
    abstract fun bindStoriesRepository(storiesRepositoryImpl: StoriesRepositoryImpl) : StoriesRepository

}