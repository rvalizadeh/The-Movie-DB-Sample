package com.themoviedb.datasource.remote

import com.themoviedb.models.NowPlayingResponse

interface NowPlayingRemoteDataSource {

    suspend fun getNowPlayingData(
        int: Int
    ): NowPlayingResponse

}