package com.example.data

import com.example.domain.User
import com.google.gson.annotations.SerializedName

data class UserMap(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("url") val url: String
) {
    fun toUser() = User(login, avatarUrl, url)
}
