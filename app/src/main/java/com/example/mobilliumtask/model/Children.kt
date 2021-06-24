package com.example.mobilliumtask.model


import com.google.gson.annotations.SerializedName

data class Children(
    @SerializedName("children")
    val children: List<Any>?,
    @SerializedName("cover")
    val cover: Cover?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo")
    val logo: Logo?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("parent_category")
    val parentCategory: ParentCategory?,
    @SerializedName("parent_id")
    val parentId: Int?
)