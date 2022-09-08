package com.example.aisle

import android.content.Context
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
import com.example.aisle.Model.OtpApiResponse
import com.example.aisle.Model.PhoneNumberApiResponse
import com.example.aisle.requestBody.OtpBody
import com.example.aisle.requestBody.PhoneNumberBody
import retrofit2.Call
import retrofit2.Response

class OtpActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        val combinedMobiletv = findViewById<TextView>(R.id.tv_combined)
        val otptv = findViewById<AppCompatEditText>(R.id.et_otp)
        val continue_btn = findViewById<TextView>(R.id.tv_continue_btn)
        progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar

        val intent = getIntent();
        val combinedMobile = intent.getStringExtra("number")

        combinedMobiletv.text = combinedMobile

        continue_btn.setOnClickListener {

            if (!otptv.text.toString().equals("")){
                if (combinedMobile != null) {
                    val otp = otptv.text.toString()
                    otpVerification(combinedMobile,otp)
                }else{
                    Log.e("full mob","number null")
                }
            }else {
                Toast.makeText(this,"Invalid Input",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun otpVerification(mobile: String,otp: String){
        progressBar?.visibility = VISIBLE
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val otpInfo = OtpBody(mobile,otp)

        retIn.otpVerify(otpInfo).enqueue(object :
            retrofit2.Callback<OtpApiResponse> {
            override fun onFailure(call: Call<OtpApiResponse>, t: Throwable) {
                progressBar?.visibility = GONE
                Toast.makeText(
                    this@OtpActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onResponse(call: Call<OtpApiResponse>, response: Response<OtpApiResponse>) {
                progressBar?.visibility = GONE
                if (response.body()?.token != null) {
                    Toast.makeText(this@OtpActivity, "Verification success!", Toast.LENGTH_SHORT)
                        .show()
                    val token = response.body()?.token
                    val intent = Intent(this@OtpActivity,MainActivity :: class.java)
                    intent.putExtra("token",token)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@OtpActivity, "Registration failed!"+response.body()?.token, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}