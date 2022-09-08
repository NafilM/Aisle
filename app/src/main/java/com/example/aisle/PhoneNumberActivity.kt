package com.example.aisle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.aisle.Model.PhoneNumberApiResponse
import com.example.aisle.requestBody.PhoneNumberBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

var combinedMobile = ""
private var progressBar: ProgressBar? = null

class PhoneNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)

        val pre_mobile = findViewById<AppCompatEditText>(R.id.et_pre_phn)
        val mobile = findViewById<AppCompatEditText>(R.id.et_phn)
        val continue_btn = findViewById<TextView>(R.id.tv_continue)
        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar


        continue_btn.setOnClickListener {
            combinedMobile = pre_mobile.text.toString() + mobile.text.toString()
            Log.e("full mob",combinedMobile)
            if (combinedMobile.length != 13) {
                Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show()
            }else {
                login(combinedMobile)
                Log.e("full mob",combinedMobile)
            }
        }

        }

    private fun login(mobile: String){
        progressBar?.visibility = VISIBLE
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val phoneInfo = PhoneNumberBody(mobile)

        retIn.mobileLogin(phoneInfo).enqueue(object :
            retrofit2.Callback<PhoneNumberApiResponse> {
            override fun onFailure(call: Call<PhoneNumberApiResponse>, t: Throwable) {
                progressBar?.visibility = GONE
                Toast.makeText(
                    this@PhoneNumberActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<PhoneNumberApiResponse>, response: Response<PhoneNumberApiResponse>) {
                progressBar?.visibility = GONE
                if (response.body()?.status == true) {
                    Toast.makeText(this@PhoneNumberActivity, "Registration success!", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@PhoneNumberActivity,OtpActivity :: class.java)
                    intent.putExtra("number", combinedMobile)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@PhoneNumberActivity, "Registration failed!"+response.body()?.status, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}