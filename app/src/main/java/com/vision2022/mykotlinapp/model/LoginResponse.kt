package com.vision2022.mykotlinapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @Expose
    @SerializedName("token")
    lateinit var token: String
}