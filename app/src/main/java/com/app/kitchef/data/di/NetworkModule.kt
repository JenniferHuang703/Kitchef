package com.app.kitchef.data.di

import com.app.kitchef.domain.api.SpoonacularApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val SPOON_API_KEY_RECIPE = "92b5e93f470646178f054a3725cedc64"
const val BASE_URL = "https://api.spoonacular.com/recipes/"

val networkModule = module {
    single {
       OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val httpUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("apiKey", SPOON_API_KEY_RECIPE)
                    .build()
                val request = originalRequest.newBuilder()
                    .url(httpUrl)
                    .build()
                chain.proceed(request)
            }
           .connectTimeout(120, TimeUnit.SECONDS)
           .readTimeout(120, TimeUnit.SECONDS)
           .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(SpoonacularApiService::class.java)
    }
}