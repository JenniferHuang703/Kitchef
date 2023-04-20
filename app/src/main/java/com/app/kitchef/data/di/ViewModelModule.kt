//package com.example.testing.data.di
//
import androidx.lifecycle.ViewModel
import com.app.kitchef.presentation.ui.authentication.AuthenticationViewModel
import com.app.kitchef.presentation.ui.home.AddIngredientsViewModel
import com.app.kitchef.presentation.ui.recipeDetail.RecipeDetailViewModel
import com.app.kitchef.presentation.ui.recommendedRecipes.RecommendedRecipesViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module
//
//
////val viewModelModule = module {
////    viewModel { AddIngredientsViewModel(get()) }
////}
//
val viewModelModule = Kodein.Module(name = "viewModelModule") {
    bind<ViewModel>(tag = AuthenticationViewModel::class.java.simpleName) with provider {
        AuthenticationViewModel(instance())
    }

    bind<ViewModel>(tag = AddIngredientsViewModel::class.java.simpleName) with provider {
        AddIngredientsViewModel(instance())
    }

    bind<ViewModel>(tag = RecommendedRecipesViewModel::class.java.simpleName) with provider {
        RecommendedRecipesViewModel(instance())
    }

    bind<ViewModel>(tag = RecipeDetailViewModel::class.java.simpleName) with provider {
        RecipeDetailViewModel(instance())
    }
}