package com.sideproject.githupapidemo.presenter

import androidx.paging.PagingData
import com.sideproject.githupapidemo.model.User
import kotlinx.coroutines.flow.Flow

interface IViewUsers {
     fun setAdapterData(pagingData: Flow<PagingData<User>>)
}