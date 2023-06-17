package com.hcapps.xpenzave.di

import android.content.Context
import androidx.room.Room
import com.hcapps.xpenzave.data.local_source.XpenzaveDatabase
import com.hcapps.xpenzave.data.local_source.dao.BudgetDao
import com.hcapps.xpenzave.data.local_source.dao.CategoryDao
import com.hcapps.xpenzave.data.local_source.dao.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): XpenzaveDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = XpenzaveDatabase::class.java,
            name = XpenzaveDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBudgetDao(db: XpenzaveDatabase): BudgetDao = db.budgetDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db: XpenzaveDatabase): CategoryDao = db.categoryDao()

    @Provides
    @Singleton
    fun provideExpenseDao(db: XpenzaveDatabase): ExpenseDao = db.expenseDao()

}