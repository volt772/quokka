package com.kakao.data.datasource.operators

import com.kakao.domain.entity.RemoteKeyEntity


interface RemoteKeyOperator {

    suspend fun deleteRemoteKeys(key: String)

    suspend fun insertRemoteKeys(keys: List<RemoteKeyEntity>)

    suspend fun getRemoteKeyClosestToCurrentPosition(key: String): RemoteKeyEntity?

    suspend fun getFirstRemoteKey(key: String): RemoteKeyEntity?

    suspend fun getLastRemoteKey(key: String): RemoteKeyEntity?
}
