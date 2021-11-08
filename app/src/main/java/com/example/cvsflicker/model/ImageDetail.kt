package com.example.cvsflicker.model

import java.io.Serializable

data class ImageDetail(var title  :String,
                       var media : Media,
                       var author : String,
                       var description :String) : Serializable