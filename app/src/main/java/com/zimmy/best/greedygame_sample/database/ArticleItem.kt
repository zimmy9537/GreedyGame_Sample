package com.zimmy.best.greedygame_sample.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zimmy.best.greedygame_sample.Konstants

@Entity(tableName = Konstants.ARTICLE)
data class ArticleItem(
    @PrimaryKey()
    val url: String,
    val urlImage:String,
    val date: String,
    val title: String,
    val author: String,
    val description: String
)
