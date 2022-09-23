package com.zimmy.best.greedygame_sample.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zimmy.best.greedygame_sample.jsondata.Article
import com.zimmy.best.greedygame_sample.network.NewsService
import com.zimmy.best.greedygame_sample.network.RetrofitHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<ArrayList<Article>>? = null

    fun getNews(): LiveData<ArrayList<Article>>? {
        if (mutableLiveData == null) {
            mutableLiveData = MutableLiveData<ArrayList<Article>>()
            loadData()
        }
        return mutableLiveData
    }

    private fun loadData() {
        val newsApi = RetrofitHelper.getInstance().create(NewsService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val newsObject = newsApi.getNewsObject()
            val articleList = newsObject.articles
            mutableLiveData?.postValue(articleList as ArrayList<Article> /* = java.util.ArrayList<com.zimmy.best.greedygame_sample.jsondata.Article> */)
        }
    }
}