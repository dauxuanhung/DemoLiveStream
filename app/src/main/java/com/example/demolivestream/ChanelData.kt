package com.example.demolivestream

import com.google.gson.annotations.SerializedName

data class ChanelData(
    @SerializedName("data") val data: String,
    @SerializedName("token") val token: String
)