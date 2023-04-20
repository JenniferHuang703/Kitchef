package com.app.kitchef.data.di

import com.app.kitchef.presentation.ui.authentication.AuthenticationViewModel
import com.app.kitchef.presentation.ui.home.AddIngredientsViewModel
import com.app.kitchef.presentation.ui.recipeDetail.RecipeDetailViewModel
import com.app.kitchef.presentation.ui.recommendedRecipes.RecommendedRecipesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthenticationViewModel(get()) }
    viewModel { AddIngredientsViewModel(get()) }
    viewModel { RecommendedRecipesViewModel(get()) }
    viewModel { RecipeDetailViewModel(get()) }
}