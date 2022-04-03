package com.example.presentation.dagger

import com.example.data.AuthInterceptor
import com.example.data.GitRepository
import com.example.data.GitService
import com.example.data.GitUsersPageSource
import com.example.domain.GetUsersUseCase
import com.example.domain.IGitRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class GitModule {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @Provides
    fun providesGetUsersUseCase(gitRepository: IGitRepository): GetUsersUseCase {
        return GetUsersUseCase(gitRepository)
    }

    @Provides
    fun providesGitRepository(gitUsersPageSource: GitUsersPageSource.Factory): IGitRepository {
        return GitRepository(gitUsersPageSource)
    }

    @Provides
    fun provideGitService(retrofit: Retrofit): GitService {
        return retrofit.create(GitService::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor())
            .build()

        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

}