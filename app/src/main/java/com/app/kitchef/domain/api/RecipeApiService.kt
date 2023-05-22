package com.app.kitchef.domain.api

import com.app.kitchef.data.network.ConnectivityInterceptor
import com.app.kitchef.data.network.recipe.RecipeResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val API_KEY_RECIPE = "446aa19085c168eb53cff289564dbc5b"
const val APP_KEY_RECIPE = "393c071f"
const val TYPE = "public"

//https://api.edamam.com/api/recipes/v2?type=public&q=tomato%20egg&app_id=393c071f&app_key=446aa19085c168eb53cff289564dbc5b&ingr=3


interface RecipeApiService {
    @GET("v2")
    suspend fun getRecipe(
        @Query("q") ingredient: String,
        @Query("ingr") type: Int,
    ): RecipeResponse

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): RecipeApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("app_key", API_KEY_RECIPE)
                    .addQueryParameter("app_id", APP_KEY_RECIPE)
                    .addQueryParameter("type", TYPE)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.edamam.com/api/recipes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeApiService::class.java)
        }
    }
}