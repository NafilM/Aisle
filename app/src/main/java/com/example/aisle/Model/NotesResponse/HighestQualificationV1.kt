package com.example.example

import com.google.gson.annotations.SerializedName


data class HighestQualificationV1 (

  @SerializedName("id"              ) var id             : Int?     = null,
  @SerializedName("name"            ) var name           : String?  = null,
  @SerializedName("preference_only" ) var preferenceOnly : Boolean? = null

)