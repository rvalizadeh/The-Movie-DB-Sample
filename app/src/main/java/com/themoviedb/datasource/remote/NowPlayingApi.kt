package com.themoviedb.datasource.remote

import com.themoviedb.util.DataConstant
import com.themoviedb.model.NowPlayingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NowPlayingApi {

    @GET(DataConstant.NOW_PLAYING_URL)
    suspend fun nowPlayingList(@Query("page") page: Int
    ): NowPlayingResponse

}