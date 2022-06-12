package com.example.testing.common

import android.net.Uri
import com.example.testing.R
import com.example.testing.domain.model.Ingredient


object StaticIngredientList {
    val preDefinedIngredientsList = arrayListOf(
        Ingredient(image = getURLForResource(R.drawable.egg), title = "Egg"),
        Ingredient(image = getURLForResource(R.drawable.tomato), title = "Tomato"),
        Ingredient(image = getURLForResource(R.drawable.eggplant), title = "Eggplant"),
        Ingredient(image = getURLForResource(R.drawable.cheese), title = "Cheese"),
        Ingredient(image = getURLForResource(R.drawable.rice), title = "Rice"),
        Ingredient(image = getURLForResource(R.drawable.tofu), title = "Tofu"),
        Ingredient(image = getURLForResource(R.drawable.spinach), title = "Spinach"),
        Ingredient(image = getURLForResource(R.drawable.broccoli), title = "Broccoli"),
        Ingredient(image = getURLForResource(R.drawable.carrot), title = "Carrot"),
        Ingredient(image = getURLForResource(R.drawable.mushroom), title = "Mushroom"),
    )
}

fun getURLForResource(resourceId: Int): String {
    //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
    return Uri.parse(
        "android.resource://" + R::class.java.getPackage().getName() + "/" + resourceId
    ).toString()
}