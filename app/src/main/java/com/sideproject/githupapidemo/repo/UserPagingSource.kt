package com.sideproject.githupapidemo.repo


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sideproject.githupapidemo.model.User


class UserPagingSource
    (
    private val gitHupApi: GitHupApi
) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentPage = params.key ?: 1
            val response = gitHupApi.getUsers(currentPage)
            val data = response.body()?.userList ?: emptyList()
            val list = mutableListOf<User>()
            list.addAll(data)
            return LoadResult.Page(
                data = list,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}