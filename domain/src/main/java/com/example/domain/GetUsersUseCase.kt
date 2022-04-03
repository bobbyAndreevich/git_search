package com.example.domain

import androidx.paging.PagingSource

class GetUsersUseCase (private val gitRepository: IGitRepository) {
    operator fun invoke(query: String): PagingSource<Int, User> {
        return gitRepository.getUsersList(query)
    }
}