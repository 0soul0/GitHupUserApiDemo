package com.sideproject.githupapidemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.sideproject.githupapidemo.component.ProgressDialog
import com.sideproject.githupapidemo.databinding.ActivityMainBinding
import com.sideproject.githupapidemo.model.User
import com.sideproject.githupapidemo.presenter.IViewUsers
import com.sideproject.githupapidemo.presenter.PresenterUsers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), IViewUsers {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserPageAdapter
    private val presenterUsers = PresenterUsers(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setRecycleView()
        presenterUsers.getUsers()
    }

    private fun setRecycleView() {
        binding.rvUsers.let {
            userAdapter = UserPageAdapter()

            userAdapter.setOnItemClickLister { user, _ ->
                val i = Intent(this@MainActivity, UserItemActivity::class.java)
                i.putExtra("userName", user?.login)
                startActivity(i)
            }
            it.adapter = userAdapter
            it.layoutManager = LinearLayoutManager(this)
        }
//        lifecycleScope.launch {
//            userAdapter.loadStateFlow.collect {
//                binding.progressBar.isVisible = it.refresh is LoadState.Loading
//            }

            userAdapter.addLoadStateListener { loadState ->
//                    // show empty list
//                    binding.emptyList.isVisible = loadState.refresh is LoadState.NotLoading && mAdapter.itemCount == 0
//                    // Only show the list if refresh succeeds.
//                    binding.rvPaging.isVisible = loadState.source.refresh is LoadState.NotLoading
                    // Show loading spinner during initial load or refresh.
                    binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
//                    // Show the retry state if initial load or refresh fails.
//                    binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
                }
//        }
    }


    override fun setAdapterData(pagingData: Flow<PagingData<User>>) {
        lifecycleScope.launch {
            pagingData.collect {
                userAdapter.submitData(it)
            }
        }

    }
}