package com.app.kitchef.data.di

import com.app.kitchef.domain.repository.AddIngredientRepository
import com.app.kitchef.domain.repository.AddIngredientRepositoryImpl
import com.app.kitchef.domain.repository.AuthenticationRepository
import com.app.kitchef.domain.repository.RecipeRepository
import com.app.kitchef.domain.repository.RecipeRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single{ AuthenticationRepository(get()) }
    single{ AddIngredientRepositoryImpl(get()) }
    single<RecipeRepository> { RecipeRepositoryImpl(get(), get())}
    single<AddIngredientRepository> { AddIngredientRepositoryImpl(get())}
}