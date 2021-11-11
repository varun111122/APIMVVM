package com.example.demoapptask.response

data class MyResponse(
    val `data`: List<Data>,
    val error: Boolean,
    val message: String,
    val success: Boolean
)