package com.zimmy.best.greedygame_sample.service

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.room.Room
import com.zimmy.best.greedygame_sample.Konstants
import com.zimmy.best.greedygame_sample.database.ArticleDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AppController : LifecycleObserver, Application() {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        Log.d("AppController", "Foreground")
        isAppInBackground(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        Log.d("AppController", "Background")
        isAppInBackground(true)
        val database: ArticleDatabase
        database = Room.databaseBuilder(
            applicationContext,
            ArticleDatabase::class.java,
            Konstants.ARTICLE_DB
        ).build()
        GlobalScope.launch {
            database.articleDao().deleteArticles()
        }
    }

    // Adding some callbacks for test and log
    interface ValueChangeListener {
        fun onChanged(value: Boolean?)
    }

    private var visibilityChangeListener: ValueChangeListener? = null
    fun setOnVisibilityChangeListener(listener: ValueChangeListener?) {
        visibilityChangeListener = listener
    }

    private fun isAppInBackground(isBackground: Boolean) {
        if (null != visibilityChangeListener) {
            visibilityChangeListener!!.onChanged(isBackground)
        }
    }

    private var mInstance: AppController? = null
    fun getInstance(): AppController? {
        return mInstance
    }


    override fun onCreate() {
        super.onCreate()
        mInstance = this

        // addObserver
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this)
    }

}