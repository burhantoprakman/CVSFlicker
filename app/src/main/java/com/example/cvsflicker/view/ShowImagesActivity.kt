package com.example.cvsflicker.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cvsflicker.R
import com.example.cvsflicker.base.SharedPreferencesHelper
import com.example.cvsflicker.databinding.ActivityShowImagesBinding
import com.example.cvsflicker.viewmodel.ShowImagesViewModel
import kotlin.collections.ArrayList

class ShowImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreference  = SharedPreferencesHelper(this)
        val searchTextList  = sharedPreference.getList()
        lateinit var arrayListOfSearchedText : ArrayList<String>
        lateinit var adapter : ArrayAdapter<String>


            binding = DataBindingUtil.setContentView(this, R.layout.activity_show_images)

        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this)
        binding.imageAndTitleViewModel =getViewModel("")
        val layoutManager =  GridLayoutManager(this,2)
        binding.recyclerviewImageAndTitle.layoutManager = layoutManager


        if (searchTextList != null) {
                //Searched items list view adapter
                arrayListOfSearchedText = ArrayList(searchTextList)
                arrayListOfSearchedText.reverse()
                adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayListOfSearchedText)
                binding.lwSearch.adapter = adapter
                binding.tvRecentSearch.setText("Recent Searches")
                binding.lwSearch.visibility = View.VISIBLE
        }


        binding.btnSearch.setOnClickListener{
            val searchedText = binding.tvSearchText.text.toString()
            binding.imageAndTitleViewModel = getViewModel(searchedText)


            if(searchedText.isNotBlank() ){
                arrayListOfSearchedText.add(0,searchedText)

                if(arrayListOfSearchedText.size > 5){
                    arrayListOfSearchedText.removeAt(arrayListOfSearchedText.size-1)
                }
                searchTextList?.add(searchedText)
                adapter.notifyDataSetChanged()
                sharedPreference.setList(searchTextList!!)
                binding.tvRecentSearch.setText("Recent Searches")
                binding.lwSearch.visibility = View.VISIBLE
            }
        }
        binding.lwSearch.setOnItemClickListener { _, _, position, _ ->
            binding.tvSearchText.setText(arrayListOfSearchedText[position])
            binding.lwSearch.visibility = View.GONE
        }

        binding.lwSearch.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { _, _, position, _ ->
                Log.e("AA",arrayListOfSearchedText.get(position))
                Log.e("AA",""+searchTextList)
                searchTextList?.remove(arrayListOfSearchedText.get(position))
                arrayListOfSearchedText.removeAt(position)
                adapter.notifyDataSetChanged()
                sharedPreference.setList(searchTextList!!)

                if(arrayListOfSearchedText.isEmpty()){
                    binding.tvRecentSearch.setText("You have no recent searches. Enter a search term above")
                }
                true
            }
    }

    fun getViewModel( searchedText : String) : ShowImagesViewModel {
        val viewModel =ViewModelProvider(this).get(ShowImagesViewModel::class.java)
        viewModel.getImageData(searchedText)
        viewModel.getListDataObserver().observe(this, {
            if(it != null){
                viewModel.setAdapterData(it) { imageAndTitle ->
                    val intent = Intent(this, ImageDetailsActivity::class.java)
                    val imageDetailDataBundle = Bundle()
                    imageDetailDataBundle.putSerializable("imageDetail", imageAndTitle)
                    intent.putExtras(imageDetailDataBundle)
                    startActivity(intent)
                }
            }
        })

        viewModel.getToastMessage().observe(this , {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
        return viewModel
    }
}
