package com.example.example

import com.google.gson.annotations.SerializedName


data class Likes (

  @SerializedName("profiles"             ) var profiles           : ArrayList<Profiles> = arrayListOf(),
  @SerializedName("can_see_profile"      ) var canSeeProfile      : Boolean?            = null,
  @SerializedName("likes_received_count" ) var likesReceivedCount : Int?                = null

)