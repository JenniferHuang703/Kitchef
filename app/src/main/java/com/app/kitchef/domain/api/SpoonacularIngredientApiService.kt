package com.app.kitchef.domain.api

import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipeDetailResponse
import com.app.kitchef.data.db.entity.spoonacularModel.IngredientSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//Search ingredient
//https://api.spoonacular.com/food/ingredients/search?apiKey=92b5e93f470646178f054a3725cedc64&query=banana&number=1

//Get ingredient by Id
//https://api.spoonacular.com/food/ingredients/{id}/information

interface SpoonacularIngredientApiService {
    @GET("search")
    suspend fun getSearchedIngredient(
        @Query("query") ingredientName: String,
        @Query("number") numbersOfResults: Int,
    ): IngredientSearchResponse

    @GET("{id}/information")
    suspend fun getIngredientDetail(
        @Path("id") id: Int,
    ): GetRecipeDetailResponse
}