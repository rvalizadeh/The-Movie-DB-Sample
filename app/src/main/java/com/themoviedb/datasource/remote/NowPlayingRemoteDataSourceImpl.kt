package com.themoviedb.datasource.remote

import com.themoviedb.models.NowPlayingResponse

class NowPlayingRemoteDataSourceImpl(private val nowPlayingApi: NowPlayingApi) : NowPlayingRemoteDataSource {

    override suspend fun getNowPlayingData(page: Int): NowPlayingResponse {
        return nowPlayingApi.nowPlayingList(page)
    }
}