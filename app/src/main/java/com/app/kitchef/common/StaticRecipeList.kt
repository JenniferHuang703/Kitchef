package com.app.kitchef.common

import com.app.kitchef.R
import com.app.kitchef.domain.model.Recipe

object StaticRecipeList {
    val predefinedRecipeList = arrayListOf(
        Recipe(recipeId = 1, label = "Tomato Salt", image = getURLForResource(R.drawable.tomato_salt)),
        Recipe(recipeId = 2, label = "Grilled Broccoli Pizza", image = getURLForResource(R.drawable.broccoli_tomato)),
        Recipe(recipeId = 3, label = "Pesto Vegetables", image = getURLForResource(R.drawable.broccoli_carrot))
    )
}