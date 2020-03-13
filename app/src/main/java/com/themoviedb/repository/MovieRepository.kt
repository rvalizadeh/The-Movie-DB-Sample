package com.themoviedb.repository

import com.themoviedb.models.NowPlayingResponse
import com.themoviedb.models.ResultWrapper

interface MovieRepository {

    suspend fun nowPlayingData(
        page: Int
    ): ResultWrapper<NowPlayingResponse>

}