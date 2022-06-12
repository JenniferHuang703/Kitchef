package com.example.kitchef.data.db.entity.ingredientModel


data class Measure(
    val label: String,
    val qualified: List<Qualified>,
    val uri: String,
    val weight: Double
)