package com.example.mobilliumtask.model


import com.google.gson.annotations.SerializedName

data class Featured(
    @SerializedName("cover")
    val cover: Cover?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("sub_title")
    val subTitle: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)