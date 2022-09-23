package com.zimmy.best.greedygame_sample.jsondata

data class NewsObject(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)