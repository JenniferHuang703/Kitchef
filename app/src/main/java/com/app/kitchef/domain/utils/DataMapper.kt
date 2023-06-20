package com.app.kitchef.domain.utils

import com.app.kitchef.data.db.entity.spoonacularModel.GetRandomRecipesInformationResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipeDetailResponse
import com.app.kitchef.data.db.entity.spoonacularModel.GetRecipesByIngredientsResponseItem
import com.app.kitchef.domain.model.IngredientFull
import com.app.kitchef.domain.model.Nutrient
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.domain.model.RecipeDetail
import com.app.kitchef.domain.model.Step

object DataMapper {
    fun mapRecipeResponseToRecipe( input: List<GetRandomRecipesInformationResponse>): List<Recipe> {
        return input.map {
            Recipe(
                it.id,
                it.title ?: "",
                it.image ?: "",
            )
        }
    }

    fun mapRecipesByIngredientsResponseToRecipe(input: List<GetRecipesByIngredientsResponseItem>): List<Recipe> {
        return input.map {
            Recipe(
                (it.id) as Int,
                it.title ?: "",
                it.imageUrl ?: ""
            )
        }
    }

    fun mapRecipeDetail(
        input: GetRecipeDetailResponse
    ) : RecipeDetail {

        val instruction = input.instruction.orEmpty()
        val steps = if (instruction.isNotEmpty()) {
            instruction[0].stepList.map {
                Step(
                    it.number,
                    it.step
                )
            }
        } else {
            listOf()
        }

        val ingredients = input.ingredientList.orEmpty().map {
            IngredientFull(
                it.ingredientId,
                it.ingredientImageUrl ?: "",
                it.ingredientName ?: "Not Available",
                it.description ?: "Not Available"
            )
        }

        val nutrition = input.nutrition
        val nutrientList = nutrition?.nutrients?.map {
            Nutrient(
                it.title ?: "Not Available",
                it.amount ?: 0.0,
                it.unit ?: "",
                it.percentOfDailyNeeds ?: 0.0
            )
        }
            ?: listOf()

        return RecipeDetail(
            input.likesCount ?: -1,
            input.dishImageUrl ?: "",
            input.readyInMinutes ?: -1,
            input.dishName ?: "",
            input.dishId ?: -1,
            input.creditText ?: "Anonymous",
            input.instructions ?: "",
//            steps,
//            nutrientList,
//            ingredients,
            input.glutenFree ?: false,
            input.dairyFree ?: false,
            input.vegetarian ?: false
        )
    }

}