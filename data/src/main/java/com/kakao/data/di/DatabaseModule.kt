package com.kakao.data.di

import android.content.Context
import androidx.room.Room
import com.kakao.data.db.QkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

/**
 * DatabaseModule
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        callback: QkDatabase.Callback
    ): QkDatabase {

        return Room.databaseBuilder(appContext, QkDatabase::class.java, "quokka.db")
//            .addMigrations(MIGRATION_1_TO_2)
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    /**
     * @Dao
     */
    @Provides
    @Singleton
    fun provideDocumentDao(database: QkDatabase) = database.documentDao()

    @Provides
    @Singleton
    fun provideRemoteKeyDao(database: QkDatabase) = database.remoteKeyDao()

    @Provides
    @Singleton
    fun provideHistoryDao(database: QkDatabase) = database.historyDao()

}
