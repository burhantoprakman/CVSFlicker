package com.example.cvsflicker.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cvsflicker.R
import com.example.cvsflicker.databinding.ActivityImageDetailsBinding
import com.example.cvsflicker.model.ImageDetail
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class ImageDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageDetailsBinding
    private var imageDetailData : ImageDetail? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_details)
        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this)
        imageDetailData =  intent.getSerializableExtra("imageDetail") as ImageDetail

        val str = imageDetailData?.description
        val doc: Document = Jsoup.parse(str)
        val element: Element? = doc.select("img").first()
        val width = element?.attr("width") // Get the anchor tag element
        val height = element?.attr("height") // Get the anchor tag element
        binding.imageDetail = imageDetailData
        binding.tvWitdhAndHeight.setText("w:$width/h:$height")
    }

}