package com.example.demoapptask.response

data class Data(
    val category_id: Int,
    val createdDtm: String,
    val distance: Double,
    val featuredUpto: String,
    val id: Int,
    val img: String,
    val img_path: String,
    val imgs: String,
    val isDeleted: Int,
    val isFav: Int,
    val isFeatured: Int,
    val isSold: Int,
    val latitude: String,
    val longitude: String,
    val name: String,
    val price: Int,
    val product_condition: String,
    val product_desc: String,
    val updatedDtm: String,
    val user_id: Int
)