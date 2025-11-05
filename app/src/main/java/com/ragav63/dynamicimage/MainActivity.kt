package com.ragav63.dynamicimage

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ragav63.dynamic_image_sdk.DynamicImageGridView
import com.ragav63.dynamic_image_sdk.OnImageClickListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gridImage = findViewById<DynamicImageGridView>(R.id.gridImages)

        gridImage.setImages(listOf(
            R.drawable.tvshows,
            R.drawable.gots01e01,
            R.drawable.gots01e03,
            R.drawable.gots01e04,
            R.drawable.gots01e05,
            R.drawable.theboys,
        ),false,
            ImageView.ScaleType.FIT_XY
        )

        gridImage.setOnImageClickListener(object : OnImageClickListener {
            override fun onImageClick(index: Int, imageUrl: Any?, allImages: List<Any>?) {
                Log.d("DynamicImageGridView", "Clicked custom index=$index, url=$imageUrl, total=${allImages?.size}")
            }
        })

    }
}