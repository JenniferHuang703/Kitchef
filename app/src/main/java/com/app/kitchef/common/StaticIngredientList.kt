package com.app.kitchef.common

import android.net.Uri
import com.app.kitchef.R
import com.app.kitchef.domain.model.Ingredient

object StaticIngredientList {
    val preDefinedIngredientsList = arrayListOf(
        Ingredient(id = 1, image = getURLForResource(R.drawable.egg), name = "Egg"),
        Ingredient(id = 2, image = getURLForResource(R.drawable.tomato), name = "Tomato"),
        Ingredient(id = 3, image = getURLForResource(R.drawable.eggplant), name = "Eggplant"),
        Ingredient(id = 4, image = getURLForResource(R.drawable.cheese), name = "Cheese"),
        Ingredient(id = 5, image = getURLForResource(R.drawable.rice), name = "Rice"),
        Ingredient(id = 6, image = getURLForResource(R.drawable.tofu), name = "Tofu"),
        Ingredient(id = 7, image = getURLForResource(R.drawable.spinach), name = "Spinach"),
        Ingredient(id = 8, image = getURLForResource(R.drawable.broccoli), name = "Broccoli"),
        Ingredient(id = 9, image = getURLForResource(R.drawable.carrot), name = "Carrot"),
        Ingredient(id = 10, image = getURLForResource(R.drawable.mushroom), name = "Mushroom"),
    )
}

fun getURLForResource(resourceId: Int): String {
    //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
    return Uri.parse(
        "android.resource://" + R::class.java.getPackage().name + "/" + resourceId
    ).toString()
}