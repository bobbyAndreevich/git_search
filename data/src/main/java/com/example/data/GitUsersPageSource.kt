package com.example.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class GitUsersPageSource @AssistedInject constructor(
    private val gitService: GitService,
    @Assisted("query") private val query: String
) : PagingSource<Int, User>() {

    companion object {
        const val accept = "application/vnd.github.v3+json"
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val pageSize = params.loadSize
        val page = params.key ?: 1

        val response: Response<UserListResponseMap>?
        return try {
            response = gitService.getUsersList(accept, query, pageSize, page)
            if (response.isSuccessful) {
                val users = checkNotNull(response.body()).userList.map { it.toUser() }
                val nextKey = if (users.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(users, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: UnknownHostException) {
            LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("query") query: String): GitUsersPageSource
    }

}