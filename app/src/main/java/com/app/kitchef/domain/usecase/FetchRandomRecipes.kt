package com.app.kitchef.domain.usecase

import com.app.kitchef.data.db.entity.spoonacularModel.GetRandomRecipesResponse
import kotlinx.coroutines.flow.Flow

interface FetchRandomRecipes {

    suspend operator fun invoke(): Flow<GetRandomRecipesResponse>

}