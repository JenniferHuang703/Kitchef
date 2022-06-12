package com.example.testing.data.db.entity.ingredientModel


data class Food(
    val category: String,
    val categoryLabel: String,
    val foodContentsLabel: String,
    val foodId: String,
    val image: String,
    val label: String,
    val nutrients: Nutrients
)