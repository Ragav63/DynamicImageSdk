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
 * A fully custom GridLayout-based view that displays a dynamic hotel image grid.
 * Handles 1–5 image layouts with adaptive curved corners, and shows +X overlay for extra images.
 */
class DynamicImageGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : GridLayout(context, attrs, defStyle) {

    private val density = context.resources.displayMetrics.density
    private val normalHeight = (220 * density).toInt()
    private val baseHeight = (130 * density).toInt()

    /**
     * Public method to set the image list and rebuild the layout.
     */
    fun setImages(imageUrls: List<Any>) {
        removeAllViews()

        val totalImages = imageUrls.size
        if (totalImages == 0) return

        val displayImages = minOf(5, totalImages)

        // Set column count based on display count
        columnCount = when (displayImages) {
            1 -> 1
            2 -> 2
            3 -> 3
            4 -> 2
            5 -> 6
            else -> 6
        }

        repeat(displayImages) { index ->
            val url = imageUrls[index]
            val params: LayoutParams
            var backgroundRes: Int? = null

            when (displayImages) {
                1 -> {
                    params = baseParams(normalHeight, index, 1f)
                    backgroundRes = R.drawable.curve_trs
                }
                2 -> {
                    params = baseParams(normalHeight, index, 1f)
                    backgroundRes = if (index == 0)
                        R.drawable.top_left_btm_left_curve_trs
                    else
                        R.drawable.top_right_btm_right_curve_trs
                }
                3 -> {
                    params = baseParams(normalHeight, index, 1f)
                    backgroundRes = when (index) {
                        0 -> R.drawable.top_left_btm_left_curve_trs
                        2 -> R.drawable.top_right_btm_right_curve_trs
                        else -> null
                    }
                }
                4 -> {
                    params = LayoutParams().apply {
                        width = 0
                        height = baseHeight
                        rowSpec = spec(index / 2, 1)
                        columnSpec = spec(index % 2, 1, 1f)
                        setMargins(4, 4, 4, 4)
                    }
                    backgroundRes = when (index) {
                        0 -> R.drawable.top_left_curve_trs
                        1 -> R.drawable.top_right_curve_trs
                        2 -> R.drawable.bottom_left_curve_trs
                        else -> R.drawable.bottom_right_curve_trs
                    }
                }
                5 -> {
                    if (index < 2) {
                        params = LayoutParams().apply {
                            width = 0
                            height = baseHeight
                            rowSpec = spec(0, 1)
                            columnSpec = spec(index * 3, 3, 1f)
                            setMargins(4, 4, 4, 4)
                        }
                        backgroundRes = if (index == 0)
                            R.drawable.top_left_curve_trs
                        else
                            R.drawable.top_right_curve_trs
                    } else {
                        params = LayoutParams().apply {
                            width = 0
                            height = baseHeight
                            rowSpec = spec(1, 1)
                            columnSpec = spec((index - 2) * 2, 2, 1f)
                            setMargins(4, 4, 4, 4)
                        }
                        backgroundRes = when (index) {
                            4 -> R.drawable.bottom_right_curve_trs
                            2 -> R.drawable.bottom_left_curve_trs
                            else -> null
                        }
                    }
                }
                else -> params = LayoutParams()
            }

            val view = if (index == 4 && totalImages > 5) {
                createOverlayImage(url, totalImages, backgroundRes)
            } else {
                createRegularImage(url, backgroundRes, params)
            }
            view.setOnClickListener {
                onImageClickListener?.onImageClick(index, url, imageUrls)
            }
            addView(view, params)

        }
    }

    // -- internal helpers --

    private fun baseParams(height: Int, index: Int, weight: Float): LayoutParams {
        return LayoutParams().apply {
            width = 0
            this.height = height
            columnSpec = spec(index, 1, weight)
            setMargins(4, 4, 4, 4)
        }
    }

    private fun createRegularImage(url: Any, backgroundResId: Int?, params: LayoutParams): ImageView {
        return ImageView(context).apply {
            layoutParams = params
            scaleType = ImageView.ScaleType.CENTER_CROP
            clipToOutline = true
            backgroundResId?.let {
                background = ContextCompat.getDrawable(context, it)
                outlineProvider = ViewOutlineProvider.BACKGROUND
            }
            Glide.with(context).load(url).into(this)
        }
    }

    private fun createOverlayImage(url: Any, totalImages: Int, backgroundResId: Int?): FrameLayout {
        val frame = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            backgroundResId?.let {
                background = ContextCompat.getDrawable(context, it)
                outlineProvider = ViewOutlineProvider.BACKGROUND
                clipToOutline = true
            }
        }

        val image = ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
            clipToOutline = true
        }
        Glide.with(context).load(url).into(image)

        val overlay = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.parseColor("#88000000"))
        }

        val text = TextView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply { gravity = Gravity.CENTER }
            text = "+${totalImages - 5}"
            setTextColor(Color.WHITE)
            textSize = 20f
            setTypeface(null, Typeface.BOLD)
        }

        frame.addView(image)
        frame.addView(overlay)
        frame.addView(text)
        return frame
    }

    private var onImageClickListener: OnImageClickListener? = null
    fun setOnImageClickListener(listener: OnImageClickListener) {
        this.onImageClickListener = listener
    }

}

interface OnImageClickListener {
    fun onImageClick(index: Int, imageUrl: Any, allImages: List<Any>)
}
