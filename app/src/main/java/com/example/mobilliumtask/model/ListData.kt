package com.example.mobilliumtask.model


import com.google.gson.annotations.SerializedName

data class ListData(
    @SerializedName("categories")
    val categories: List<Category>?,
    @SerializedName("collections")
    val collections: List<Collection>?,
    @SerializedName("featured")
    val featured: List<Featured>?,
    @SerializedName("products")
    val products: List<Product>?,
    @SerializedName("shops")
    val shops: List<Shop>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)
