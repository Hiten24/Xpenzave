package com.hcapps.xpenzave.di

import android.content.Context
import com.hcapps.xpenzave.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppWriteModule {

    @Provides
    @Singleton
    fun provideAppWriteClient(@ApplicationContext context: Context): Client {
        return Client(context)
            .setEndpoint(Constant.APP_WRITE_ENDPOINT)
            .setProject(Constant.APP_WRITE_PROJECT_ID)
            .setSelfSigned(true)
    }

    @Provides
    @Singleton
    fun provideAppWriteAccount(client: Client): Account {
        return Account(client)
    }

    @Provides
    @Singleton
    fun provideAppWriteDatabase(client: Client): Databases {
        return Databases(client)
    }

}