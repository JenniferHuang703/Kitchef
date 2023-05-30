package com.app.kitchef.domain.model

data class RecipeDetail(
    val likesCount: Int,
    val dishImageUrl: String,
    val cookingTime: Int,
    val dishName: String,
    val dishId: Int,
    val creditText: String,
    val shortInstruction: String,
    val steps: List<Step>,
    val nutrients: List<Nutrient>,
    val ingredients: List<IngredientFull>,
    val isGlutenFree: Boolean,
    val isDairyFree: Boolean,
    val isVegetarian: Boolean
)

data class Step(
    val number: Int,
    val stepInstruction: String
)

data class Nutrient(
    val title: String,
    val amount: Double,
    val unit: String,
    val percentOfDailyNeeds: Double
)

data class IngredientFull(
    val id: Int,
    val imageHalfPath: String,
    val name: String,
    val desc: String
)