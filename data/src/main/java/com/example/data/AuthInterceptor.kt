package com.example.data

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(HEADER, "accept")
            .build()

        return chain.proceed(request)
    }

    private companion object {

        private const val HEADER = "X-Api-Key"
    }
}