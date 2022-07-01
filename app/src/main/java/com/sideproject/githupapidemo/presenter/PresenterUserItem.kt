package com.sideproject.githupapidemo.presenter


import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sideproject.githupapidemo.model.UserItem
import com.sideproject.githupapidemo.repo.GitHupApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PresenterUserItem(private val context: AppCompatActivity) {

    private val gitHupApi: GitHupApi
    private val iViewUserItem: IViewUserItem = context as IViewUserItem

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(com.sideproject.githupapidemo.repo.Config.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gitHupApi = retrofit.create(GitHupApi::class.java)
    }

    fun getUserItem(userName:String) {
        context.lifecycleScope.launch(Dispatchers.IO) {

            gitHupApi.getUserItem(userName).enqueue(object : Callback<UserItem> {
                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                    if(response.isSuccessful){
                        iViewUserItem.setAdapterData(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                    Log.v("error", "$t")
                }
            })
        }

    }
}