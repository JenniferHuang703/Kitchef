package com.example.testing

import android.app.Application
import com.example.testing.data.db.IngredientDatabase
import com.example.testing.data.network.ConnectivityInterceptor
import com.example.testing.data.network.ConnectivityInterceptorImpl
import com.example.testing.data.network.ingredient.IngredientNetworkDataSource
import com.example.testing.data.network.ingredient.IngredientNetworkDataSourceImpl
import com.example.testing.data.network.recipe.RecipeNetworkDataSource
import com.example.testing.data.network.recipe.RecipeNetworkDataSourceImpl
import com.example.testing.domain.api.IngredientApiService
import com.example.testing.domain.api.RecipeApiService
import com.example.testing.domain.repository.AddIngredientRepository
import com.example.testing.domain.repository.AddIngredientRepositoryImpl
import com.example.testing.domain.repository.RecipeRepository
import com.example.testing.domain.repository.RecipeRepositoryImpl
import com.example.testing.presentation.ui.home.AddIngredientViewModelFactory
import com.example.testing.presentation.ui.recommendedRecipes.RecommendedRecipesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import viewModelModule

class BaseApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@BaseApplication))

        bind() from singleton { IngredientDatabase(instance()) }
        bind() from singleton { instance<IngredientDatabase>().currentIngredientDao() }
        bind<ConnectivityInterceptor>() with singleton{ ConnectivityInterceptorImpl(instance())}
        bind() from singleton { IngredientApiService(instance()) }
        bind<IngredientNetworkDataSource>() with singleton { IngredientNetworkDataSourceImpl(instance()) }
        bind<AddIngredientRepository>() with singleton { AddIngredientRepositoryImpl(instance()) }
        bind() from provider { AddIngredientViewModelFactory(instance())}
        bind() from provider { RecommendedRecipesViewModelFactory(instance())}

        bind() from singleton { RecipeApiService(instance()) }
        bind<RecipeNetworkDataSource>() with singleton { RecipeNetworkDataSourceImpl(instance()) }
        bind<RecipeRepository>() with singleton { RecipeRepositoryImpl(instance()) }

        import(viewModelModule)
    }

}