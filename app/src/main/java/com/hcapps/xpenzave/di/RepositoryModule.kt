package com.hcapps.xpenzave.di

import com.hcapps.xpenzave.data.source.remote.repository.AuthRepository
import com.hcapps.xpenzave.data.source.remote.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

}