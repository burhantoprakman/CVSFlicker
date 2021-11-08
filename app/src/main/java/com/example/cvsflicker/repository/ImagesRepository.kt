package com.example.cvsflicker.repository

import com.example.cvsflicker.model.MainResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesRepository {

    @GET("photos_public.gne?format=json&nojsoncallback=1&")
    fun getImagesList(@Query("tags") searchedText : String) : Call<MainResponse>
}