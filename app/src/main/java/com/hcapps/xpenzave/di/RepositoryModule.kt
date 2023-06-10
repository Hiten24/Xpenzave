package com.hcapps.xpenzave.di

import com.hcapps.xpenzave.data.source.remote.repository.auth.AuthRepository
import com.hcapps.xpenzave.data.source.remote.repository.auth.AuthRepositoryImpl
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepositoryImpl
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

    @Binds
    abstract fun provideDatabaseRepository(
        databaseRepositoryImpl: DatabaseRepositoryImpl
    ): DatabaseRepository

}