package com.app.kitchef

import android.app.Application
import com.app.kitchef.data.di.databaseModule
import com.app.kitchef.data.di.networkModule
import com.app.kitchef.data.di.repositoryModule
import com.app.kitchef.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    viewModelModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                )
            )
        }

    }
}