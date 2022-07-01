package com.sideproject.githupapidemo.repo

import com.sideproject.githupapidemo.model.Search
import com.sideproject.githupapidemo.model.UserItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHupApi {
    @GET("search/users?q=fullname:Ruan&per_page=20")
    suspend fun getUsers(@Query("page") page: Int): Response<Search>

    @GET("users/{userName}")
    fun getUserItem(@Path("userName") userName: String): Call<UserItem>

}