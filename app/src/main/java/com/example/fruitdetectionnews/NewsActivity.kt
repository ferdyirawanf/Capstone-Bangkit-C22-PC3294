package com.example.fruitdetectionnews

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitdetectionnews.adapter.PostAdapter
import com.example.fruitdetectionnews.adapter.PostAdapter2
import com.example.fruitdetectionnews.viewmodel.PostModel
import com.example.fruitdetectionnews.viewmodel.PostModel2

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val toolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.title=""

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerView2)

        val serviceGenerator = ServicesGenerator.apiService1()
        val call = serviceGenerator.getPost()

        val serviceGenerator2 = ServicesGenerator.apiService2()
        val call2 = serviceGenerator2.getPost("ab", "id", "health", "7790ed85f96e4f44a15c59683385fe53")

        call.enqueue(object :Callback<MutableList<PostModel>> {
            override fun onResponse(
                call: Call<MutableList<PostModel>>,
                response: Response<MutableList<PostModel>>
            ) {
                if (response.isSuccessful){
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@NewsActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = PostAdapter(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<PostModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("Error", t.message.toString() )
            }

        })

        call2.enqueue(object :Callback<PostModel2> {
            override fun onResponse(call: Call<PostModel2>, response: Response<PostModel2>) {
                if (response.isSuccessful) {
                    Log.d("TAG", "onResponse: ${response.body()}")
                    recyclerView2.apply {
                        layoutManager = LinearLayoutManager(this@NewsActivity, LinearLayoutManager.VERTICAL, false)
                        adapter = PostAdapter2(response.body()?.articles!!)
                    }
                } else {
                    Log.d("TAG", "onResponseGagal1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostModel2>, t: Throwable) {
                Log.d("TAG", "onResponseGagal2: ${t.message}")
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerView2)
        val serviceGenerator2 = ServicesGenerator.apiService2()



        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search News"
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val call2 = serviceGenerator2.getPost(query, "id", "health", "7790ed85f96e4f44a15c59683385fe53")
                call2.enqueue(object :Callback<PostModel2> {
                    override fun onResponse(call: Call<PostModel2>, response: Response<PostModel2>) {
                        if (response.isSuccessful) {
                            Log.d("TAG", "onResponse: ${response.body()}")
                            recyclerView2.apply {
                                layoutManager = LinearLayoutManager(this@NewsActivity, LinearLayoutManager.VERTICAL, false)
                                adapter = PostAdapter2(response.body()?.articles!!)
                            }
                        } else {
                            Log.d("TAG", "onResponseGagal1: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<PostModel2>, t: Throwable) {
                        Log.d("TAG", "onResponseGagal2: ${t.message}")
                    }

                })

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })
        return true


    }
}