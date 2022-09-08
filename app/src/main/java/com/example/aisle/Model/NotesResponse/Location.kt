package com.example.example

import com.google.gson.annotations.SerializedName


data class Location (

  @SerializedName("summary" ) var summary : String? = null,
  @SerializedName("full"    ) var full    : String? = null

)