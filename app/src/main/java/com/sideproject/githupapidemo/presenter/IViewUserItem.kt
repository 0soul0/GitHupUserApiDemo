package com.sideproject.githupapidemo.presenter

import com.sideproject.githupapidemo.model.User
import com.sideproject.githupapidemo.model.UserItem

interface IViewUserItem {
    fun setAdapterData(data: UserItem)
}