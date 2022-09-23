package com.zimmy.best.greedygame_sample.view

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zimmy.best.greedygame_sample.adapter.NewsAdapter
import com.zimmy.best.greedygame_sample.databinding.ActivityHomeBinding
import com.zimmy.best.greedygame_sample.viewmodels.NewsViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (!isConnectionAvailable(this)) {
            noInternet()
        } else {
            yesInternet()
            getData()

        }
    }

    private fun getData() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        val linearLayoutManager = LinearLayoutManager(this)
        binding.newsRv.layoutManager = linearLayoutManager
        viewModel.getNews()?.observe(this
        ) { data ->
            binding.newsRv.adapter = NewsAdapter(data, this)
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
}