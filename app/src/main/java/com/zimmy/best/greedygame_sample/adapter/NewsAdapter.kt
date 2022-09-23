package com.zimmy.best.greedygame_sample.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zimmy.best.greedygame_sample.Konstants
import com.zimmy.best.greedygame_sample.R
import com.zimmy.best.greedygame_sample.databinding.NewsItemBinding
import com.zimmy.best.greedygame_sample.jsondata.Article
import com.zimmy.best.greedygame_sample.view.DetailActivity

class NewsAdapter(private var newsList: ArrayList<Article>, private var context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding=NewsItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        with(holder){
            with(newsList[position]){
                Picasso.get().load(this.urlToImage).into(binding.newsImage)
                binding.date.text=this.publishedAt
                binding.headLine.text=this.title
                binding.details.text=this.content

                binding.readBt.setOnClickListener {
                    val intent=Intent(context,DetailActivity::class.java)
                    intent.putExtra(Konstants.ARTICLE,this)
                    context.startActivity(intent)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }


    inner class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

}