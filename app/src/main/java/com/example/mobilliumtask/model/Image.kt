package com.example.mobilliumtask.model


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("medium")
    val medium: Medium?,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)