package com.example.cvsflicker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cvsflicker.adapter.ImagesRecyclerViewAdapter
import com.example.cvsflicker.base.RetrofitInstance
import com.example.cvsflicker.model.ImageDetail
import com.example.cvsflicker.model.MainResponse
import com.example.cvsflicker.repository.ImagesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowImagesViewModel() : ViewModel() {

    private var mainScreenImages : MutableLiveData<MainResponse> = MutableLiveData()
    private var recyclerViewAdapter : ImagesRecyclerViewAdapter = ImagesRecyclerViewAdapter()
    private val toastMessageObserver: MutableLiveData<String> = MutableLiveData()


    fun getListDataObserver() : MutableLiveData<MainResponse>{
        return  mainScreenImages
    }

    fun getAdapter(): ImagesRecyclerViewAdapter {
        return recyclerViewAdapter
    }

    fun setAdapterData(data: MainResponse, onItemClickListener: (ImageDetail) -> Unit) {
        recyclerViewAdapter.setDataList(data.items, onItemClickListener)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun getToastMessage() : LiveData<String>{
        return toastMessageObserver
    }

     fun getImageData(searchedText : String){
     val retrofitInstance = RetrofitInstance.getRetroInstance().create(ImagesRepository::class.java)
         val call = retrofitInstance.getImagesList(searchedText)
         call.enqueue(object : Callback<MainResponse> {
             override fun onResponse(call: Call<MainResponse>, response: Response<MainResponse>) {
                mainScreenImages.postValue(response.body())
             }

             override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                 toastMessageObserver.postValue("Something gone wrong" + t.localizedMessage)
             }
         })

     }
}