package com.example.data

import com.google.gson.annotations.SerializedName

data class UserListResponseMap(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val userList: ArrayList<UserMap>
)