package com.example.cvsflicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cvsflicker.R
import com.example.cvsflicker.databinding.ImageItemViewBinding
import com.example.cvsflicker.model.ImageDetail

class ImagesRecyclerViewAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var imagesList  =  ArrayList<ImageDetail>()
    lateinit var listener : (ImageDetail) ->Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemViewBinding.inflate(inflater)
        return ImageListItemViewHolder(binding)
    }

    fun setDataList(imageDetail: ArrayList<ImageDetail>, onItemClickListener: (ImageDetail) -> Unit) {
        this.imagesList = imageDetail
        this.listener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as ImageListItemViewHolder).bind(imagesList[position],listener)
    }

class ImageListItemViewHolder(val binding  : ImageItemViewBinding) : RecyclerView.ViewHolder(binding.root){
    private val titleName : TextView?=  null

        fun bind(imageDetail : ImageDetail, listener : (ImageDetail) -> Unit ){

            binding.image = imageDetail
            titleName?.text = imageDetail.title
            itemView.setOnClickListener {listener(imageDetail)}
            binding.executePendingBindings()
        }

    companion object {
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(image: ImageView, url: String) {
            Glide.with(image)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .fallback(R.drawable.ic_launcher_foreground)
                .into(image)
        }

    }
}