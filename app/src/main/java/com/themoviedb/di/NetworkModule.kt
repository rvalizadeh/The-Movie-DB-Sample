package com.themoviedb.di

import com.themoviedb.helper.AuthInterceptor
import com.themoviedb.util.DataConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get(), get()) }
    single { provideRetrofit(get()) }
    factory { provideOkHttpLoggerInterceptor() }
}


fun provideOkHttpLoggerInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(DataConstant.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(
    authInterceptor: AuthInterceptor,
    okHttpLoggerInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(okHttpLoggerInterceptor)
        .connectTimeout(2 , TimeUnit.MINUTES)
        .readTimeout(2 , TimeUnit.MINUTES)
        .build()
}

