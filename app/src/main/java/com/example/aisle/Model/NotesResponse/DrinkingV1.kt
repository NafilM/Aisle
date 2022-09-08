package com.example.example

import com.google.gson.annotations.SerializedName


data class DrinkingV1 (

  @SerializedName("id"         ) var id        : Int?    = null,
  @SerializedName("name"       ) var name      : String? = null,
  @SerializedName("name_alias" ) var nameAlias : String? = null

)