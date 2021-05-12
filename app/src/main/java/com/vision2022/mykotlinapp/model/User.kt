package com.vision2022.mykotlinapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User (
    @Expose
    @SerializedName("username")
    var username: String,

    @Expose
    @SerializedName("password")
    var password: String
)
