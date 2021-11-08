package com.example.cvsflicker.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Media( @SerializedName("m")
                  var mediaName : String) : Serializable
