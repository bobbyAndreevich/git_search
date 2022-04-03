package com.example.presentation.ui.main.viewmodels

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.domain.GetUsersUseCase
import kotlinx.coroutines.flow.*
import com.example.domain.User
import javax.inject.Inject

class GitSearchViewModel @Inject constructor(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    var users = searchQuery.map(::newPager).flatMapLatest { it.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun setQuery(query: String) {
        _searchQuery.tryEmit(query)
    }

    private fun newPager(query: String): Pager<Int, User> {
        return Pager(PagingConfig(30, enablePlaceholders = false, initialLoadSize = 30)) {
            getUsersUseCase.invoke(query).also { newPagingSource = it }
        }
    }
}