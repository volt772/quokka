package com.kakao.data.datasource.operators

import com.kakao.data.db.QkDatabase
import com.kakao.domain.entity.RemoteKeyEntity
import javax.inject.Inject

class RemoteKeyOperatorImpl @Inject constructor(
    private val db: QkDatabase
) : RemoteKeyOperator {

    /**
     * RemoteKey 전체삭제
     * @desc 상태 : REFRESH
     */
    override suspend fun deleteRemoteKeys(
        key: String
    ) {
        db.remoteKeyDao().deleteKeys(key = key)
    }

    /**
     * RemoteKey 생성
     * @desc 상태 : REFRESH, APPEND, PREPEND
     */
    override suspend fun insertRemoteKeys(
        keys: List<RemoteKeyEntity>
    ) {
        db.remoteKeyDao().insertAll(keys)
    }

    /**
     * RemoteKey 조회
     * @desc 현재 위치근접
     */
    override suspend fun getRemoteKeyClosestToCurrentPosition(
        key: String
    ) = db.remoteKeyDao().getRemoteKey(key = key)

    /**
     * RemoteKey 조회
     * @desc 처음(상단)
     */
    override suspend fun getFirstRemoteKey(
        key: String
    ) = db.remoteKeyDao().getRemoteKey(key = key)

    /**
     * RemoteKey 조회
     * @desc 마지막(하단)
     */
    override suspend fun getLastRemoteKey(
        key: String
    ) = db.remoteKeyDao().getRemoteKey(key = key)
}
