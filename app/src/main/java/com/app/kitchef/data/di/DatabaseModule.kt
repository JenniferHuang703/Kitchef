package com.app.kitchef.data.di

import com.app.kitchef.data.db.IngredientDatabase
import com.app.kitchef.data.db.RecipeDatabase
import com.app.kitchef.data.network.ConnectivityInterceptor
import com.app.kitchef.data.network.ConnectivityInterceptorImpl
import com.app.kitchef.data.network.ingredient.IngredientNetworkDataSource
import com.app.kitchef.data.network.ingredient.IngredientNetworkDataSourceImpl
import com.app.kitchef.data.network.recipe.RecipeNetworkDataSource
import com.app.kitchef.data.network.recipe.RecipeNetworkDataSourceImpl
import org.koin.dsl.module

val databaseModule = module {
    single { IngredientDatabase(get()) }
    single { RecipeDatabase(get()) }
    factory { get<IngredientDatabase>().currentIngredientDao() }
    factory { get<RecipeDatabase>().currentRecipeDao() }
    single<ConnectivityInterceptor> { ConnectivityInterceptorImpl(get()) }
    single<IngredientNetworkDataSource> { IngredientNetworkDataSourceImpl(get()) }
    single<RecipeNetworkDataSource> { RecipeNetworkDataSourceImpl(get()) }
}