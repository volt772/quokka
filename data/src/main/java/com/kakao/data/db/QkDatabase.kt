package com.kakao.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kakao.data.dao.DocumentDao
import com.kakao.data.dao.HistoryDao
import com.kakao.data.dao.RemoteKeyDao
import com.kakao.data.di.ApplicationScope
import com.kakao.domain.entity.DocumentEntity
import com.kakao.domain.entity.HistoryEntity
import com.kakao.domain.entity.RemoteKeyEntity
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider


@Database(
    entities = [
        DocumentEntity::class,
        HistoryEntity::class,
        RemoteKeyEntity::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class QkDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun historyDao(): HistoryDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    class Callback @Inject constructor(
        private val database: Provider<QkDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}
