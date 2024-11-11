package com.example.musicapp

import android.icu.text.StringSearch
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Tag
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var recycler:RecyclerView
    lateinit var adapter:AudioAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<ImageButton>(R.id.searchBtn).setOnClickListener {
            val searchText = findViewById<EditText>(R.id.searchBar).text
            search(searchText.toString().trim())
        }

        recycler=findViewById<RecyclerView>(R.id.recyclerView)
        recycler.setItemViewCacheSize(20)
        adapter= AudioAdapter( this@MainActivity,emptyList())
        recycler.layoutManager=LinearLayoutManager(this@MainActivity)

        fetchData()

    }

    fun search(search:String="a"){
        findViewById<ProgressBar>(R.id.progressBar).isVisible=true
        fetchData(search)
    }



    fun fetchData( search: String="a"){
        CoroutineScope( Dispatchers.IO).launch {
            try {
                val retrofitBuilder = Retrofit.Builder()
                    .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface::class.java)

                val retrofitData = retrofitBuilder.getData(search)

                retrofitData.enqueue(object : Callback<MyData?> {
                    override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                        val dataList = response.body()?.data!!
                        findViewById<ProgressBar>(R.id.progressBar).isVisible=false
//                findViewById<TextView>(R.id.testText).text=dataList.toString()
                        adapter=AudioAdapter(this@MainActivity,dataList)
                        recycler.adapter=adapter

                    }

                    override fun onFailure(call: Call<MyData?>, t: Throwable) {

//                        findViewById<TextView>(R.id.testText).text="failed"
                        Log.d("Tag: onFailure", "onResponse: "+t.message)
                    }
                })
            }catch (e:Exception){
               e.printStackTrace()
            }
        }
    }
}