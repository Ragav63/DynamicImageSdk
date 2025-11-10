package com.ragav63.dynamic_image_sdk

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun DynamicImageGridCompose(
    modifier: Modifier = Modifier,
    imageList: List<Any>,
    showDialog: Boolean = false,
    scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP,
    dialogTitle: String? = null,
    onImageClick: ((Int, Any?, List<Any>?) -> Unit)? = null
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            DynamicImageGridView(context).apply {
                setImages(imageList, showDialog, scaleType, dialogTitle)
                setOnImageClickListener(object : OnImageClickListener {
                    override fun onImageClick(
                        index: Int,
                        imageUrl: Any?,
                        allImages: List<Any>?
                    ) {
                        onImageClick?.invoke(index, imageUrl, allImages)
                    }
                })
            }
        },
        update = { view ->
            view.setImages(imageList, showDialog, scaleType, dialogTitle)
        }
    )
}
