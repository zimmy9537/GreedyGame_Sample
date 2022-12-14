package com.zimmy.best.greedygame_sample.jsondata

import java.io.Serializable

data class Article(
    val author: String,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: Source?,
    val title: String,
    val url: String,
    val urlToImage: String
) : Serializable {

    constructor(
        url: String,
        urlImage: String,
        date: String,
        author: String,
        title: String,
        description:String
    ) : this(author, null, description, date, null, title, url, urlImage)
}