package com.zimmy.best.greedygame_sample.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zimmy.best.greedygame_sample.Konstants
import com.zimmy.best.greedygame_sample.adapter.NewsAdapter
import com.zimmy.best.greedygame_sample.databinding.ActivityHomeBinding
import com.zimmy.best.greedygame_sample.jsondata.Article
import com.zimmy.best.greedygame_sample.viewmodels.NewsViewModel
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var articleList: ArrayList<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        articleList = ArrayList()

        if (!isConnectionAvailable(this)) {
            noInternet()
        } else {
            yesInternet()
            getData()
        }

        //sorting
        val sortArray = arrayOf(Konstants.DATE, Konstants.PUBLICATION)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortSpinner.adapter = adapter
        binding.sortSpinner.onItemSelectedListener
        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                arg0: AdapterView<*>?,
                arg1: View?,
                position: Int,
                id: Long
            ) {
                if (articleList.isEmpty()) {
                    return
                }
                //sort
                if (sortArray[position] == Konstants.DATE) {
                    Collections.sort(articleList, DateComparator())
                    Toast.makeText(
                        this@HomeActivity,
                        "Sorted according to Date",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Collections.sort(articleList, PublicationComparator())
                    Toast.makeText(
                        this@HomeActivity,
                        "Sorted according to Publication",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.newsRv.adapter?.notifyDataSetChanged()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }


        //search
        binding.searchEt.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (articleList.isNotEmpty()) {
                    (binding.newsRv.adapter as NewsAdapter).filter.filter(newText)
                }
                return false
            }

        })

        binding.saved.setOnClickListener {
            startActivity(Intent(this@HomeActivity, SaveActivity::class.java))
        }
    }

    private fun getData() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        val linearLayoutManager = LinearLayoutManager(this)
        binding.newsRv.layoutManager = linearLayoutManager
        viewModel.getNews()?.observe(
            this
        ) { data ->
            articleList = data
            binding.newsRv.adapter = NewsAdapter(articleList, this)
            binding.progress.visibility = View.GONE
            binding.newsRv.visibility = View.VISIBLE
        }
    }

    private fun noInternet() {
        binding.noInternet.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE
        binding.newsRv.visibility = View.GONE
    }

    private fun yesInternet() {
        binding.noInternet.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
        binding.newsRv.visibility = View.VISIBLE
    }

    private fun isConnectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        if (netInfo != null &&
            netInfo.isConnected &&
            netInfo.isConnectedOrConnecting &&
            netInfo.isAvailable
        ) {
            return true
        }
        return false
    }

    internal class DateComparator : Comparator<Article?> {
        // override the compare() method
        override fun compare(s1: Article?, s2: Article?): Int {
            if (s2 != null) {
                return if (s1?.publishedAt!! > s2.publishedAt) 1 else -1
            }
            return 1
        }
    }


    internal class PublicationComparator : Comparator<Article?> {
        // override the compare() method
        override fun compare(s1: Article?, s2: Article?): Int {
            if (s2 != null) {
                return if (s1?.author!! > s2.author) 1 else -1
            }
            return 1
        }
    }
}