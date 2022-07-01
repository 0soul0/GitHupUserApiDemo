package com.sideproject.githupapidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.sideproject.githupapidemo.databinding.ActvityUserItemBinding
import com.sideproject.githupapidemo.model.UserItem
import com.sideproject.githupapidemo.presenter.IViewUserItem
import com.sideproject.githupapidemo.presenter.PresenterUserItem


class UserItemActivity : AppCompatActivity(), IViewUserItem {

    private lateinit var binding: ActvityUserItemBinding
    private val presenterUsers = PresenterUserItem(this)
    private lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.actvity_user_item)

        setToolbarGoBackPrePage()
        getData()
        presenterUsers.getUserItem(userName)
    }

    private fun getData() {
        val i = intent
        userName = i.getStringExtra("userName") ?: ""
    }

    override fun setAdapterData(data: UserItem) {
        binding.userItem = data
        Glide.with(this).load(data.avatar_url).into(binding.imgUser)
    }

    private fun setToolbarGoBackPrePage() {
        setSupportActionBar(findViewById(R.id.toolbar))
        val actionBar = supportActionBar
        if (actionBar != null) {
            with(actionBar) {
                setHomeAsUpIndicator(R.drawable.ic_baseline_clear_24)
                // showing the back button in action bar
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}