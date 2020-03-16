package com.themoviedb.datasource.remote

import com.themoviedb.model.NowPlayingResponse

interface NowPlayingRemoteDataSource {

    suspend fun getNowPlayingData(
        int: Int
    ): NowPlayingResponse

}