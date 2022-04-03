package com.example.presentation.ui

import com.example.presentation.dagger.ContextModule
import com.example.presentation.dagger.GitModule
import com.example.presentation.dagger.GoogleModule
import com.example.presentation.ui.login.LoginActivity
import com.example.presentation.ui.main.views.GitSearchFragment
import com.example.presentation.ui.main.views.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GitModule::class, GoogleModule::class, ContextModule::class])
interface ApplicationComponent {

    fun inject(activity: LoginActivity)

    fun inject(fragment: GitSearchFragment)

    fun inject(activity: MainActivity)
}