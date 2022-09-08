package com.example.aisle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.example.aisle.adapter.ImageAdapter
import com.example.example.NotesApiResponse
import com.example.example.Photos
import com.example.example.Profiles
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    // ArrayList of class ItemsViewModel
    var data = ArrayList<Profiles>()
    lateinit var img: ImageView
    lateinit var txt: TextView
    lateinit var recyclerview: RecyclerView
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img = findViewById(R.id.img)
        txt = findViewById(R.id.txt)
        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar

        val intent = getIntent();
        val token = intent.getStringExtra("token")

        recyclerview = findViewById(R.id.rv_images)
        recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)

        val adapter = ImageAdapter(this,data)
        recyclerview.adapter = adapter

        if (token != null) {
            notesApiCall(token)
        }

    }

    private fun notesApiCall(token: String){
        progressBar?.visibility = VISIBLE
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

        retIn.notes(token).enqueue(object :
            retrofit2.Callback<NotesApiResponse> {
            override fun onFailure(call: Call<NotesApiResponse>, t: Throwable) {
                progressBar?.visibility = GONE
                Toast.makeText(
                    this@MainActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<NotesApiResponse>, response: Response<NotesApiResponse>) {
                progressBar?.visibility = GONE
                if (response.isSuccessful) {
                    val body = response.body()
                    Toast.makeText(this@MainActivity, "success!", Toast.LENGTH_SHORT)
                        .show()
                    if (body != null) {
                        Picasso.with(this@MainActivity).load(body.likes?.profiles?.get(0)?.avatar).into(img)
                        txt.text = body.likes?.profiles?.get(0)?.firstName
                        data = body.likes?.profiles!!
                        val adapter = ImageAdapter(this@MainActivity,data)
                        recyclerview.adapter = adapter
                    }
                }
                else{
                    progressBar?.visibility = GONE
                    Toast.makeText(this@MainActivity, "failed!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

}