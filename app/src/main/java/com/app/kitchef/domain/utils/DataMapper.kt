package com.app.kitchef.domain.utils

import com.app.kitchef.data.db.entity.recipeModel.Hit
import com.app.kitchef.data.db.entity.spoonacularModel.GetRandomRecipesInformationResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipesByIngredientsResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipesByIngredientsResponseItem
import com.app.kitchef.domain.model.Recipe

object DataMapper {
    fun mapRecipeResponseToRecipe( input: List<GetRandomRecipesInformationResponse>): List<Recipe> {
        return input.map {
            Recipe(
                it.title ?: "",
                it.image ?: "",
            )
        }
    }

    fun mapRecipeHitResponseToRecipe( input: List<Hit>): List<com.app.kitchef.data.db.entity.recipeModel.Recipe> {
        return input.map {
            com.app.kitchef.data.db.entity.recipeModel.Recipe(
                it.recipe.cuisineType,
                it.recipe.image,
                it.recipe.ingredientLines,
                it.recipe.label,
            )
        }
    }

    fun mapRecipesByIngredientsResponseToRecipe(input: List<GetRecipesByIngredientsResponseItem>): List<Recipe> {
        return input.map {
            Recipe(
                it.title ?: "",
                it.imageUrl ?: ""
            )
        }
    }
}