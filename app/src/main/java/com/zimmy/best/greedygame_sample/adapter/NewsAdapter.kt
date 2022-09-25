package com.zimmy.best.greedygame_sample.adapter

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.squareup.picasso.Picasso
import com.zimmy.best.greedygame_sample.Konstants
import com.zimmy.best.greedygame_sample.R
import com.zimmy.best.greedygame_sample.database.ArticleDatabase
import com.zimmy.best.greedygame_sample.database.ArticleItem
import com.zimmy.best.greedygame_sample.databinding.NewsItemBinding
import com.zimmy.best.greedygame_sample.jsondata.Article
import com.zimmy.best.greedygame_sample.view.DetailActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.AlgorithmConstraints
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(private var newsList: ArrayList<Article>, private var context: Context) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(), Filterable {

    private var articleListAll: ArrayList<Article> = ArrayList(newsList)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: MutableList<Article> = ArrayList()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(articleListAll)
                } else {
                    val filterPattern =
                        constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                    for (ele in articleListAll) {
                        if (ele.author.lowercase(Locale.ROOT)
                                .contains(filterPattern) || ele.title.lowercase(Locale.ROOT)
                                .contains(filterPattern) || ((ele.description)?.lowercase(Locale.ROOT))?.contains(
                                filterPattern
                            ) == true
                        ) {
                            filteredList.add(ele)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraints: CharSequence?, results: FilterResults?) {
                newsList.clear()
                if (results != null) {
                    newsList.addAll(results.values as Collection<Article>)
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        with(holder) {
            with(newsList[position]) {
                val range: IntRange = IntRange(0, 9)
                Picasso.get().load(this.urlToImage).into(binding.newsImage)
                binding.date.text = this.publishedAt.slice(range)
                binding.headLine.text = this.title
                binding.details.text = this.content

                binding.root.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(Konstants.ARTICLE, this)
                    context.startActivity(intent)
                }

                binding.readBt.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(Konstants.ARTICLE, this)
                    context.startActivity(intent)
                }



                binding.saveBt.setOnClickListener {
                    val database: ArticleDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        ArticleDatabase::class.java,
                        Konstants.ARTICLE_DB
                    ).build()
                    GlobalScope.launch {
                        this@with.description?.let { it1 ->
                            ArticleItem(
                                this@with.url,
                                this@with.urlToImage,
                                this@with.publishedAt,
                                this@with.title,
                                this@with.author,
                                it1
                            )
                        }?.let { it2 ->
                            try {
                                database.articleDao().insertArticle(
                                    it2
                                )
                                Looper.prepare()
                                Toast.makeText(context, "Article saved", Toast.LENGTH_SHORT).show()
                                Looper.loop()
                            } catch (exception: SQLiteConstraintException) {
                                Looper.prepare()
                                Toast.makeText(
                                    context,
                                    "You have already saved the article",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Looper.loop()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }


    inner class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

}