package com.example.aisle

import com.example.aisle.Model.OtpApiResponse
import com.example.aisle.Model.PhoneNumberApiResponse
import com.example.aisle.requestBody.OtpBody
import com.example.aisle.requestBody.PhoneNumberBody
import com.example.example.NotesApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @POST("users/phone_number_login")
    fun mobileLogin(@Body info: PhoneNumberBody): retrofit2.Call<PhoneNumberApiResponse>

    @POST("users/verify_otp")
    fun otpVerify(@Body info: OtpBody): retrofit2.Call<OtpApiResponse>

    @GET("users/test_profile_list")
    fun notes(@Header("Authorization") authorization: String): retrofit2.Call<NotesApiResponse>
}

class RetrofitInstance {
    companion object {
        val BASE_URL: String = "https://testa2.aisle.co/V1/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}