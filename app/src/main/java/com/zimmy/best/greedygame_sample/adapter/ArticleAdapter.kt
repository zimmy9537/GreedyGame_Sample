package com.zimmy.best.greedygame_sample.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zimmy.best.greedygame_sample.Konstants
import com.zimmy.best.greedygame_sample.database.ArticleItem
import com.zimmy.best.greedygame_sample.databinding.ArticleItemBinding
import com.zimmy.best.greedygame_sample.jsondata.Article
import com.zimmy.best.greedygame_sample.view.DetailActivity
import java.security.AlgorithmConstraints
import java.util.*
import kotlin.collections.ArrayList

class ArticleAdapter(private val articleList: ArrayList<ArticleItem>, val context: Context) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(), Filterable {

    private var articleListAll: ArrayList<ArticleItem> = ArrayList(articleList)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: MutableList<ArticleItem> = ArrayList()
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
                articleList.clear()
                if (results != null) {
                    articleList.addAll(results.values as Collection<ArticleItem>)
                }
                notifyDataSetChanged()
            }
        }
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(holder) {
            with(articleList[position]) {
                val range: IntRange = IntRange(0, 9)
                Picasso.get().load(this.urlImage).into(binding.newsImage)
                binding.date.text = this.date.slice(range)
                binding.author.text = this.author
                binding.headline.text = this.title



                binding.root.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(
                        Konstants.ARTICLE,
                        Article(
                            this.url,
                            this.urlImage,
                            this.date,
                            this.author,
                            title,
                            this.description
                        )
                    )
                    context.startActivity(intent)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class ArticleViewHolder(val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}