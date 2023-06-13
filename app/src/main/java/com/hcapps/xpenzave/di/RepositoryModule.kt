package com.hcapps.xpenzave.di

import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.datastore.DataStoreServiceImpl
import com.hcapps.xpenzave.data.source.remote.repository.auth.AuthRepository
import com.hcapps.xpenzave.data.source.remote.repository.auth.AuthRepositoryImpl
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepository
import com.hcapps.xpenzave.data.source.remote.repository.database.DatabaseRepositoryImpl
import com.hcapps.xpenzave.data.source.remote.repository.storage.StorageRepository
import com.hcapps.xpenzave.data.source.remote.repository.storage.StorageRepositoryImpl
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

    @Binds
    abstract fun provideStorageRepository(
        storageRepositoryImpl: StorageRepositoryImpl
    ): StorageRepository

    @Binds
    abstract fun provideDataStore(
        dataStoreImpl: DataStoreServiceImpl
    ): DataStoreService

}