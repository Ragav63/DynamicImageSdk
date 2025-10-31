package com.ragav63.dynamic_image_sdk

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
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
import kotlin.apply
import kotlin.let

/**
 * A reusable manager to dynamically populate a GridLayout
 * with hotel images and adaptive curved corners.
 */
class DynamicImageGridManager(private val context: Context) {

    /**
     * Populates a [GridLayout] with the provided list of [imageUrls].
     *
     * - Supports 1–5 visible images, applies adaptive curved backgrounds.
     * - If more than 5 images exist, shows a "+X" overlay on the last one.
     */
    fun setupHotelImages(grid: GridLayout, imageUrls: List<String>) {
        grid.removeAllViews()

        val totalImages = imageUrls.size
        if (totalImages == 0) return

        val displayImages = kotlin.comparisons.minOf(5, totalImages)
        val density = context.resources.displayMetrics.density
        val normalHeight = (220 * density).toInt()
        val baseHeight = (130 * density).toInt()

        // Determine grid columns based on number of visible images
        grid.columnCount = when (displayImages) {
            1 -> 1
            2 -> 2
            3 -> 3
            4 -> 2
            5 -> 6
            else -> 6
        }

        repeat(displayImages) { index ->
            val url = imageUrls[index]
            val gridParams: GridLayout.LayoutParams
            var backgroundResId: Int? = null

            when (displayImages) {
                1 -> {
                    gridParams = baseParams(normalHeight, index, 0f, 1f)
                    backgroundResId = R.drawable.curve_trs
                }

                2 -> {
                    gridParams = baseParams(normalHeight, index, 0f, 1f)
                    backgroundResId = if (index == 0)
                        R.drawable.top_left_btm_left_curve_trs
                    else
                        R.drawable.top_right_btm_right_curve_trs
                }

                3 -> {
                    gridParams = baseParams(normalHeight, index, 0f, 1f)
                    backgroundResId = when (index) {
                        0 -> R.drawable.top_left_btm_left_curve_trs
                        2 -> R.drawable.top_right_btm_right_curve_trs
                        else -> null
                    }
                }

                4 -> {
                    gridParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = baseHeight
                        rowSpec = GridLayout.spec(index / 2, 1)
                        columnSpec = GridLayout.spec(index % 2, 1, 1f)
                        setMargins(4, 4, 4, 4)
                    }
                    backgroundResId = when (index) {
                        0 -> R.drawable.top_left_curve_trs
                        1 -> R.drawable.top_right_curve_trs
                        2 -> R.drawable.bottom_left_curve_trs
                        else -> R.drawable.bottom_right_curve_trs
                    }
                }

                5 -> {
                    if (index < 2) {
                        gridParams = GridLayout.LayoutParams().apply {
                            width = 0
                            height = baseHeight
                            rowSpec = GridLayout.spec(0, 1)
                            columnSpec = GridLayout.spec(index * 3, 3, 1f)
                            setMargins(4, 4, 4, 4)
                        }
                        backgroundResId = if (index == 0)
                            R.drawable.top_left_curve_trs
                        else
                            R.drawable.top_right_curve_trs
                    } else {
                        gridParams = GridLayout.LayoutParams().apply {
                            width = 0
                            height = baseHeight
                            rowSpec = GridLayout.spec(1, 1)
                            columnSpec = GridLayout.spec((index - 2) * 2, 2, 1f)
                            setMargins(4, 4, 4, 4)
                        }
                        backgroundResId = when (index) {
                            4 -> R.drawable.bottom_right_curve_trs
                            2 -> R.drawable.bottom_left_curve_trs
                            else -> null
                        }
                    }
                }

                else -> gridParams = GridLayout.LayoutParams()
            }

            if (index == 4 && totalImages > 5) {
                grid.addView(createOverlayImage(url, totalImages, backgroundResId), gridParams)
            } else {
                grid.addView(createRegularImage(url, backgroundResId, gridParams))
            }
        }
    }

    // -- internal helpers --

    private fun baseParams(height: Int, index: Int, marginStart: Float, weight: Float): GridLayout.LayoutParams {
        return GridLayout.LayoutParams().apply {
            width = 0
            this.height = height
            columnSpec = GridLayout.spec(index, 1, weight)
            setMargins(4, 4, 4, 4)
        }
    }

    private fun createRegularImage(url: String, backgroundResId: Int?, params: GridLayout.LayoutParams): ImageView {
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

    private fun createOverlayImage(url: String, totalImages: Int, backgroundResId: Int?): FrameLayout {
        val overlayLayout = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            backgroundResId?.let {
                background = ContextCompat.getDrawable(context, it)
                outlineProvider = ViewOutlineProvider.BACKGROUND
                clipToOutline = true
            }
        }

        val image = ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
            clipToOutline = true
        }
        Glide.with(context).load(url).into(image)

        val overlay = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.parseColor("#88000000"))
        }

        val text = TextView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { gravity = Gravity.CENTER }
            text = "+${totalImages - 5}"
            setTextColor(Color.WHITE)
            textSize = 20f
            setTypeface(null, Typeface.BOLD)
        }

        overlayLayout.addView(image)
        overlayLayout.addView(overlay)
        overlayLayout.addView(text)

        return overlayLayout
    }
}
