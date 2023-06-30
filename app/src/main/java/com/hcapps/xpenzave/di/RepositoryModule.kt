package com.hcapps.xpenzave.di

import com.hcapps.xpenzave.data.datastore.DataStoreService
import com.hcapps.xpenzave.data.datastore.DataStoreServiceImpl
import com.hcapps.xpenzave.data.local_source.repository.LocalDatabaseRepository
import com.hcapps.xpenzave.data.local_source.repository.LocalDatabaseRepositoryImpl
import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepository
import com.hcapps.xpenzave.data.remote_source.repository.auth.AuthRepositoryImpl
import com.hcapps.xpenzave.data.remote_source.repository.database.DatabaseRepository
import com.hcapps.xpenzave.data.remote_source.repository.database.DatabaseRepositoryImpl
import com.hcapps.xpenzave.data.remote_source.repository.storage.StorageRepository
import com.hcapps.xpenzave.data.remote_source.repository.storage.StorageRepositoryImpl
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

    @Binds
    abstract fun provideLocalDatabaseRepository(
        localDatabaseRepositoryImpl: LocalDatabaseRepositoryImpl
    ): LocalDatabaseRepository

}