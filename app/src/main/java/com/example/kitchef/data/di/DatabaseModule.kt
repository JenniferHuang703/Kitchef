//package com.example.testing.data.di
//
//import com.example.testing.data.db.IngredientDatabase
//import com.example.testing.data.network.ConnectivityInterceptor
//import com.example.testing.data.network.ConnectivityInterceptorImpl
//import com.example.testing.data.network.ingredient.IngredientNetworkDataSource
//import com.example.testing.data.network.ingredient.IngredientNetworkDataSourceImpl
//import com.example.testing.domain.api.IngredientApiService
//import org.koin.android.ext.koin.androidContext
//import org.koin.dsl.module
//
//val databaseModule = module {
////    single { IngredientDatabase(get()) }
////    single { get<IngredientDatabase>().currentIngredientDao() }
//    single { ConnectivityInterceptorImpl(get()) }
//    single { IngredientNetworkDataSourceImpl(get()) }
//    single { IngredientApiService(get()) }
//}