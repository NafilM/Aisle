package com.example.example

import com.google.gson.annotations.SerializedName


data class NotesApiResponse (

  @SerializedName("invites" ) var invites : Invites? = Invites(),
  @SerializedName("likes"   ) var likes   : Likes?   = Likes()

)