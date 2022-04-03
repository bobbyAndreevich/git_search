package com.example.domain

import androidx.paging.PagingSource

interface IGitRepository {
    fun getUsersList(searchQuery: String): PagingSource<Int, User>
}