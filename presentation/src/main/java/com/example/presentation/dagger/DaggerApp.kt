package com.example.presentation.dagger

import android.app.Application
import com.example.presentation.dagger.ContextModule
import com.example.presentation.ui.ApplicationComponent
import com.example.presentation.ui.DaggerApplicationComponent

class DaggerApp : Application() {

    private var _applicationComponent: ApplicationComponent? = null

    val applicationComponent: ApplicationComponent
        get() = checkNotNull(_applicationComponent)

    override fun onCreate() {
        super.onCreate()
        _applicationComponent = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this)).build()
    }
}