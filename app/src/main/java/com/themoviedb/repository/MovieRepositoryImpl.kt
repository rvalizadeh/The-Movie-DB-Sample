package com.themoviedb.repository

import com.themoviedb.datasource.remote.NowPlayingRemoteDataSource
import com.themoviedb.helper.safeApiCall
import com.themoviedb.model.NowPlayingResponse
import com.themoviedb.model.ResultWrapper

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