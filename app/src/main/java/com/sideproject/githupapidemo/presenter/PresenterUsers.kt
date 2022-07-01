package com.sideproject.githupapidemo.presenter

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.sideproject.githupapidemo.repo.GitHupApi
import com.sideproject.githupapidemo.repo.UserPagingSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PresenterUsers(private val context: AppCompatActivity) {

    private val gitHupApi: GitHupApi
    private val iViewUsers: IViewUsers = context as IViewUsers

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(com.sideproject.githupapidemo.repo.Config.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gitHupApi = retrofit.create(GitHupApi::class.java)
    }

    fun getUsers() {
        //pageSize - 每次載入大小
        //initialLoadSize - 初始大小
        val list = Pager(PagingConfig(pageSize = 30, initialLoadSize = 10)) {
            UserPagingSource(gitHupApi)
        }.flow.cachedIn(context.lifecycleScope)
        iViewUsers.setAdapterData(list)
    }


}