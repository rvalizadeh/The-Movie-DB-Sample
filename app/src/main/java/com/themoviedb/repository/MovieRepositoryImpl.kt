package com.themoviedb.repository

import com.themoviedb.datasource.remote.NowPlayingRemoteDataSource
import com.themoviedb.helper.safeApiCall
import com.themoviedb.models.NowPlayingResponse
import com.themoviedb.models.ResultWrapper

class MovieRepositoryImpl (
    private val nowPlayingRemoteDataSource: NowPlayingRemoteDataSource
) : MovieRepository {
    override suspend fun nowPlayingData(page: Int): ResultWrapper<NowPlayingResponse> {
        return safeApiCall {
            nowPlayingRemoteDataSource.getNowPlayingData(
                page
            )
        }
    }
}