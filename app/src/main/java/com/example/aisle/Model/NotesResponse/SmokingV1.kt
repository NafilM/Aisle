package com.example.example

import com.google.gson.annotations.SerializedName


data class SmokingV1 (

  @SerializedName("id"         ) var id        : Int?    = null,
  @SerializedName("name"       ) var name      : String? = null,
  @SerializedName("name_alias" ) var nameAlias : String? = null

)