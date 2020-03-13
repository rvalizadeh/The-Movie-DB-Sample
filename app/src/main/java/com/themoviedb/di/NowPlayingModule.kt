package com.themoviedb.di

import com.themoviedb.datasource.remote.NowPlayingApi
import com.themoviedb.datasource.remote.NowPlayingRemoteDataSource
import com.themoviedb.datasource.remote.NowPlayingRemoteDataSourceImpl
import com.themoviedb.repository.MovieRepository
import com.themoviedb.repository.MovieRepositoryImpl
import com.themoviedb.viewmodel.NowPlayingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val nowPlayingModule = module {

    fun provideNowPlayingRepository(
        nowPlayingRemoteDataSource: NowPlayingRemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(nowPlayingRemoteDataSource)
    }
    single { provideNowPlayingRepository(get()) }

    fun provideNowPlayingRemoteDataSource(nowPlayingApi: NowPlayingApi): NowPlayingRemoteDataSource {
        return NowPlayingRemoteDataSourceImpl(nowPlayingApi)
    }

    single { provideNowPlayingRemoteDataSource(get()) }

    fun provideNowPlayingApi(retrofit: Retrofit): NowPlayingApi {
        return retrofit.create(NowPlayingApi::class.java)
    }

    factory { provideNowPlayingApi(get()) }

    viewModel { NowPlayingViewModel(get()) }
}