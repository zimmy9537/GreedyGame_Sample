package com.zimmy.best.greedygame_sample.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zimmy.best.greedygame_sample.Konstants

@Dao
interface ArticleDao {

    @Insert
    suspend fun insertArticle(articleItem: ArticleItem)

    @Query("DELETE FROM ${Konstants.ARTICLE}")
    suspend fun deleteArticles()

    @Query("SELECT * FROM ${Konstants.ARTICLE}")
    fun getArticle(): LiveData<List<ArticleItem>>
    //livedata used automatically this function considered suspend
}