package com.example.presentation.ui

import android.app.Application
import com.example.presentation.dagger.ContextModule
import com.example.presentation.dagger.GitModule
import com.example.presentation.ui.login.LoginActivity
import com.example.presentation.ui.main.GitSearchFragment
import com.example.presentation.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GitModule::class, GoogleModule::class, ContextModule::class])
interface ApplicationComponent {

    fun inject(activity: LoginActivity)

    fun inject(fragment: GitSearchFragment)

    fun inject(activity: MainActivity)
}