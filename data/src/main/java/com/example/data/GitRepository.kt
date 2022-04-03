package com.example.data

import androidx.paging.PagingSource
import com.example.domain.IGitRepository
import com.example.domain.User
import javax.inject.Inject

class GitRepository @Inject constructor(private val gitUsersPageSource: GitUsersPageSource.Factory): IGitRepository {

    override fun getUsersList(searchQuery: String): PagingSource<Int, User> {
        return gitUsersPageSource.create(searchQuery)
    }

}