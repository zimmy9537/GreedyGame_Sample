package com.zimmy.best.greedygame_sample.view

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.squareup.picasso.Picasso
import com.zimmy.best.greedygame_sample.Konstants
import com.zimmy.best.greedygame_sample.R
import com.zimmy.best.greedygame_sample.database.ArticleDatabase
import com.zimmy.best.greedygame_sample.database.ArticleItem
import com.zimmy.best.greedygame_sample.databinding.ActivityDetailBinding
import com.zimmy.best.greedygame_sample.jsondata.Article
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        article = intent.getSerializableExtra(Konstants.ARTICLE) as Article
        Picasso.get().load(article.urlToImage).into(binding.newsImage)
        binding.headLine.text = article.title
        binding.author.text = article.author
        binding.details.text = article.description

        binding.back.setOnClickListener {
            finish()
        }

        binding.save.setOnClickListener {
            val database: ArticleDatabase = Room.databaseBuilder(
                this@DetailActivity.applicationContext,
                ArticleDatabase::class.java,
                Konstants.ARTICLE_DB
            ).build()
            GlobalScope.launch {
                article.description?.let { it1 ->
                    ArticleItem(
                        article.url,
                        article.urlToImage,
                        article.publishedAt,
                        article.title,
                        article.author,
                        it1
                    )
                }?.let { it2 ->
                    try {
                        database.articleDao().insertArticle(
                            it2
                        )
                        Looper.prepare()
                        Toast.makeText(this@DetailActivity, "Article saved", Toast.LENGTH_SHORT)
                            .show()
                        Looper.loop()
                    } catch (exception: SQLiteConstraintException) {
                        Looper.prepare()
                        Toast.makeText(
                            this@DetailActivity,
                            "You have already saved the activity",
                            Toast.LENGTH_SHORT
                        ).show()
                        Looper.loop()
                    }
                }
            }
        }
    }
}