package com.app.kitchef.common

import com.app.kitchef.R
import com.app.kitchef.domain.model.Recipe

object StaticRecipeList {
    val predefinedRecipeList = arrayListOf(
        Recipe(label = "Tomato Salt", image = getURLForResource(R.drawable.tomato_salt)),
        Recipe(label = "Grilled Broccoli Pizza", image = getURLForResource(R.drawable.broccoli_tomato)),
        Recipe(label = "Pesto Vegetables", image = getURLForResource(R.drawable.broccoli_carrot))
    )
}