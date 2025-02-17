package com.example.kitchef.domain.api

import com.example.kitchef.data.network.ConnectivityInterceptor
import com.example.kitchef.data.network.ingredient.IngredientResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY_INGREDIENT = "1cb66901c1e9c7cba8c1e691ab70148e"
const val APP_KEY_INGREDIENT = "4d13ceca"

//https://api.edamam.com/api/food-database/v2/parser?app_id=4d13ceca&app_key=1cb66901c1e9c7cba8c1e691ab70148e&ingr=broccoli&nutrition-type=cooking

interface IngredientApiService {

    @GET("parser")
    fun getIngredient(
        @Query("ingr") ingredient: String,
        @Query("nutrition-type") type: String = "cooking"
    ): Deferred<IngredientResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): IngredientApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("app_key", API_KEY_INGREDIENT)
                    .addQueryParameter("app_id", APP_KEY_INGREDIENT)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.edamam.com/api/food-database/v2/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IngredientApiService::class.java)
        }
    }

}