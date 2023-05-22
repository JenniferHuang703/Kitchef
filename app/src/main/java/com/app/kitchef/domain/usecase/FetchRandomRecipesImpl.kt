//package com.app.kitchef.domain.usecase
//
//import android.util.Log
//import com.app.kitchef.data.db.entity.spoonacularModel.GetRandomRecipes200Response
//import com.app.kitchef.domain.api.ApiClient
//import com.app.kitchef.internal.NoConnectivityException
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.withContext
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import kotlin.coroutines.CoroutineContext
//
//
//class FetchRandomRecipesImpl(private val coroutineContext: CoroutineContext = Dispatchers.IO) :
//    FetchRandomRecipes {
//
//    override suspend operator fun invoke(): Flow<GetRandomRecipes200Response> = withContext(coroutineContext) {
//        fetchRandomRecipes()
//    }
//
//    private suspend fun fetchRandomRecipes() {
//        try {
//            val client = ApiClient.apiService
//                .getRecipes(3)
//
//            client.enqueue(object : Callback<GetRandomRecipes200Response> {
//                override fun onResponse(
//                    call: Call<GetRandomRecipes200Response>,
//                    response: Response<GetRandomRecipes200Response>
//                ) {
//                    if (response.isSuccessful) {
//                        Log.d("recipe", "" + response.body())
//                        response.body()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetRandomRecipes200Response>, t: Throwable) {
//                    Log.e("failed", "" + t.message)
//                }
//            })
//
//        } catch (e: NoConnectivityException) {
//            Log.e("Connectivity", "No internet connection", e)
//        }
//    }
//}