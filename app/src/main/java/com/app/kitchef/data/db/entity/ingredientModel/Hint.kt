package com.app.kitchef.data.db.entity.ingredientModel


data class Hint(
    val food: Food,
    val measures: List<Measure>
)