package com.themoviedb.repository

import com.themoviedb.model.NowPlayingResponse
import com.themoviedb.model.ResultWrapper

interface MovieRepository {

    suspend fun nowPlayingData(
        page: Int
    ): ResultWrapper<NowPlayingResponse>

}