package com.zimmy.best.greedygame_sample.network

import com.zimmy.best.greedygame_sample.Konstants
import com.zimmy.best.greedygame_sample.jsondata.NewsObject
import retrofit2.http.GET

interface NewsService {

    @GET("top-headlines?sources=bbc-news&apiKey=${Konstants.API_KEY}")
    suspend fun getNewsObject(): NewsObject
}
