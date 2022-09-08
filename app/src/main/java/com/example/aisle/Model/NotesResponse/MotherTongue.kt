package com.example.example

import com.google.gson.annotations.SerializedName


data class MotherTongue (

  @SerializedName("id"   ) var id   : Int?    = null,
  @SerializedName("name" ) var name : String? = null

)