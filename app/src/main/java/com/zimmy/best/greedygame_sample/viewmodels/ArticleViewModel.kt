package com.zimmy.best.greedygame_sample.viewmodels

import android.content.Context
import androidx.lifecycle.*
import androidx.room.Room
import com.zimmy.best.greedygame_sample.Konstants
import com.zimmy.best.greedygame_sample.database.ArticleDatabase
import com.zimmy.best.greedygame_sample.database.ArticleItem

class ArticleViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<ArrayList<ArticleItem>>? = null

    fun getArticles(
        context: Context,
        lifecycleOwner: LifecycleOwner
    ): LiveData<ArrayList<ArticleItem>>? {
        if (mutableLiveData == null) {
            mutableLiveData = MutableLiveData<ArrayList<ArticleItem>>()
            loadData(context, lifecycleOwner)
        }
        return mutableLiveData
    }

    private fun loadData(context: Context, lifecycleOwner: LifecycleOwner) {
        var articleList: ArrayList<ArticleItem>
        val database: ArticleDatabase = Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            Konstants.ARTICLE_DB
        ).build()

        articleList = ArrayList()
        database.articleDao().getArticle().observe(lifecycleOwner, Observer {
            articleList =
                it as ArrayList<ArticleItem> /* = java.util.ArrayList<com.zimmy.best.greedygame_sample.database.ArticleItem> */
            mutableLiveData?.postValue(articleList)
        })
    }

}