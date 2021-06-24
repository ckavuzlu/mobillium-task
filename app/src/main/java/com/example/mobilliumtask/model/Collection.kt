package com.example.mobilliumtask.model


import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("cover")
    val cover: Cover?,
    @SerializedName("definition")
    val definition: String?,
    @SerializedName("end")
    val end: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo")
    val logo: Logo?,
    @SerializedName("share_url")
    val shareUrl: String?,
    @SerializedName("start")
    val start: String?,
    @SerializedName("title")
    val title: String?
)