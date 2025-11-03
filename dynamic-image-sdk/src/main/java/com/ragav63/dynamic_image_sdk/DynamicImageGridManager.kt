package com.ragav63.dynamic_image_sdk

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

/**
 * SDK version of your dynamic image grid.
 * Matches the layout behavior of the inline "binding.gridHotelImages" version:
 * - Always uses R.drawable.curve_trs as background.
 * - Handles 1–5 images with +X overlay for extra.
 * - Applies consistent 3dp margin.
 */
class DynamicImageGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : GridLayout(context, attrs, defStyle) {

    private val density = context.resources.displayMetrics.density
    private val normalHeight = (220 * density).toInt()
    private val baseHeight = (130 * density).toInt()
    private val marginPx = (3 * density).toInt() // same margin logic as original

    private var onImageClickListener: OnImageClickListener? = null

    fun setImages(imageUrls: List<Any>?) {
        removeAllViews()
        val totalImages = imageUrls?.size ?: 0

        if (totalImages == 0) {
            val params = LayoutParams().apply {
                width = 0
                height = normalHeight
                columnSpec = spec(0, 1, 1f)
                setMargins(marginPx, marginPx, marginPx, marginPx)
            }

            val imageView = ImageView(context).apply {
                layoutParams = params
                scaleType = ImageView.ScaleType.CENTER_CROP
                clipToOutline = true
                background = ContextCompat.getDrawable(context, R.drawable.curve_trs)
                outlineProvider = ViewOutlineProvider.BACKGROUND
            }

            Glide.with(context)
                .load(R.drawable.hoteldetailplaceholder)
                .into(imageView)

            addView(imageView)
            return
        }

        val displayImages = minOf(5, totalImages)

        columnCount = when (displayImages) {
            1 -> 1
            2 -> 2
            3 -> 3
            4 -> 2
            5 -> 6
            else -> 6
        }

        for (index in 0 until displayImages) {
            val url = imageUrls?.get(index)
            val params = LayoutParams().apply {
                width = 0
                height = if (displayImages <= 3) normalHeight else baseHeight
                when (displayImages) {
                    4 -> {
                        rowSpec = spec(index / 2, 1)
                        columnSpec = spec(index % 2, 1, 1f)
                    }
                    5 -> {
                        if (index < 2) {
                            rowSpec = spec(0, 1)
                            columnSpec = spec(index * 3, 3, 1f)
                        } else {
                            rowSpec = spec(1, 1)
                            columnSpec = spec((index - 2) * 2, 2, 1f)
                        }
                    }
                    else -> {
                        columnSpec = spec(index, 1, 1f)
                    }
                }
                setMargins(marginPx, marginPx, marginPx, marginPx)
            }

            if (index == 4 && totalImages > 5) {
                val overlayLayout = FrameLayout(context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    background = ContextCompat.getDrawable(context, R.drawable.curve_trs)
                    outlineProvider = ViewOutlineProvider.BACKGROUND
                    clipToOutline = true
                    isClickable = true
                    isFocusable = true
                    setOnClickListener { onImageClickListener?.onImageClick(index, url, imageUrls) }
                }

                val overlayImage = ImageView(context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    clipToOutline = true
                }

                Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.hoteldetailplaceholder)
                    .into(overlayImage)

                val overlay = View(context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(Color.parseColor("#88000000"))
                }

                val countText = TextView(context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply { gravity = Gravity.CENTER }
                    text = "+${totalImages - 5}"
                    setTextColor(Color.WHITE)
                    textSize = 20f
                    setTypeface(null, Typeface.BOLD)
                }

                overlayLayout.addView(overlayImage)
                overlayLayout.addView(overlay)
                overlayLayout.addView(countText)
                addView(overlayLayout, params)
            } else {
                val imageView = ImageView(context).apply {
                    layoutParams = params
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    clipToOutline = true
                    background = ContextCompat.getDrawable(context, R.drawable.curve_trs)
                    outlineProvider = ViewOutlineProvider.BACKGROUND
                    setOnClickListener { onImageClickListener?.onImageClick(index, url, imageUrls) }
                }

                Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.hoteldetailplaceholder)
                    .into(imageView)

                addView(imageView)
            }
        }
    }

    fun setOnImageClickListener(listener: OnImageClickListener) {
        onImageClickListener = listener
    }
}

interface OnImageClickListener {
    fun onImageClick(index: Int, imageUrl: Any?, allImages: List<Any>?)
}
