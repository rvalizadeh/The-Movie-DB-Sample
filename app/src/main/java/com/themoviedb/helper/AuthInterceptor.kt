package com.themoviedb.helper

import com.themoviedb.util.DataConstant
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url().newBuilder().addQueryParameter(
            DataConstant.API_KEY ,
            DataConstant.API_TOKEN).build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}