package com.zimmy.best.greedygame_sample.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleItem::class], version = 1)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun articleDao() : ArticleDao

}