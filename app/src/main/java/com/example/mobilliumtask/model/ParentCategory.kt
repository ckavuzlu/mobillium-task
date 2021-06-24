package com.example.mobilliumtask.model


import com.google.gson.annotations.SerializedName

data class ParentCategory(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("parent_category")
    val parentCategory: Any?,
    @SerializedName("parent_id")
    val parentId: Any?
)