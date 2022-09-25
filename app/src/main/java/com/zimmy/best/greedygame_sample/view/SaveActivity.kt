package com.zimmy.best.greedygame_sample.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zimmy.best.greedygame_sample.adapter.ArticleAdapter
import com.zimmy.best.greedygame_sample.adapter.NewsAdapter
import com.zimmy.best.greedygame_sample.database.ArticleItem
import com.zimmy.best.greedygame_sample.databinding.ActivitySaveBinding
import com.zimmy.best.greedygame_sample.jsondata.Article
import com.zimmy.best.greedygame_sample.viewmodels.ArticleViewModel

class SaveActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveBinding
    private lateinit var viewModel: ArticleViewModel
    private lateinit var articleList: ArrayList<ArticleItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getData()

        binding.back.setOnClickListener {
            finish()
        }

        binding.searchEt.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (binding.articleRv.adapter as ArticleAdapter).filter.filter(newText)
                return false
            }

        })
    }

    private fun getData() {
        viewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
        val linearLayoutManager = LinearLayoutManager(this)
        binding.articleRv.layoutManager = linearLayoutManager
        viewModel.getArticles(this, this)?.observe(this) { data ->
            if (data.isEmpty()) {
                Log.v("list", "empty")
                binding.noInternet.visibility = View.VISIBLE
                binding.linearLayout.visibility = View.GONE
            }
            runOnUiThread {
                articleList = ArrayList(data)
                binding.articleRv.adapter = ArticleAdapter(articleList, this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("onDestroy", "called")
        val size = articleList.size
        articleList.clear()
        binding.articleRv.adapter?.notifyItemRangeRemoved(0, size)
    }
}