package com.example.keki.data.remote.responses

data class ImageResponse(
    val hits: List<Imageresult>,
    val total: Int,
    val totalHits: Int
)
