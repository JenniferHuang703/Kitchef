package com.app.kitchef.domain.api

import com.app.kitchef.data.db.entity.spoonacularModel.GetRandomRecipesResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipeDetailResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipesByIngredientsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://api.spoonacular.com/recipes/complexSearch
//https://api.spoonacular.com/recipes/716429/information?apiKey=92b5e93f470646178f054a3725cedc64&includeNutrition=true
//https://api.spoonacular.com/recipes/complexSearch?apiKey=92b5e93f470646178f054a3725cedc64&query=sticky-rice&addRecipeInformation=true

//https://api.spoonacular.com/recipes/random?apiKey=92b5e93f470646178f054a3725cedc64&number=1

//https://api.spoonacular.com/recipes/findByIngredients?apiKey=92b5e93f470646178f054a3725cedc64&ingredients=apples,+flour,+sugar&number=2

interface SpoonacularApiService {
    @GET("random")
    suspend fun getRandomRecipes(
        @Query("number") resultLimit: Int,
    ): GetRandomRecipesResponse

    @GET("findByIngredients")
    suspend fun getRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") resultLimit: Int,
    ): GetRecipesByIngredientsResponse

    @GET("{id}/information")
    suspend fun getRecipeDetail(
        @Path("id") id: Int,
        @Query("includeNutrition") isIncludeNutrition: Boolean
    ): GetRecipeDetailResponse

}