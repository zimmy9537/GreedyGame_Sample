package com.zimmy.best.greedygame_sample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.zimmy.best.greedygame_sample.Konstants
import com.zimmy.best.greedygame_sample.R
import com.zimmy.best.greedygame_sample.databinding.ActivityDetailBinding
import com.zimmy.best.greedygame_sample.jsondata.Article

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
    }
}