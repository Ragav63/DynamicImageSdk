package com.ragav63.dynamic_image_sdk

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID

class ImageOverviewDialog(
    context: Context,
    private val allImages: List<Any>?,
    private val dialogTitle: String? = null,
    private val scaleType:ImageView.ScaleType? = null,
) : Dialog(context) {


    // UI Components
    private lateinit var recVHotelImageTypes: RecyclerView
    private lateinit var recVHotelRoomImages: RecyclerView
    private lateinit var tvPhotoCount: TextView
    private lateinit var tvRestaurantTitle: TextView
    private lateinit var tvEmptyView: TextView
    private lateinit var btnPrevious: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var back: ImageView

    private lateinit var hotelImagesTypeAdapter: HotelImagesTypeAdapter
    private lateinit var hotelImagesListAdapter: HotelImagesListAdapter

    private var allUnifiedImages: List<UnifiedImage> = emptyList()

    // Add a flag to control the scroll behavior
    private var isUserScrolling = false
    private val TAG = "HotelImagesPage"

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout and find views
        val rootView = layoutInflater.inflate(R.layout.dialog_image_overview, null)
        setContentView(rootView)

        initializeViews(rootView)
        setupDialogWindow()
        setupRecyclerViews()
        setupClickListeners()

        Log.d(TAG, "Hotel values ${allImages}")
        internalReloadData()
    }

    private fun initializeViews(rootView: View) {
        recVHotelImageTypes = rootView.findViewById(R.id.recVHotelImageTypes)
        recVHotelRoomImages = rootView.findViewById(R.id.recVHotelRoomImages)
        tvPhotoCount = rootView.findViewById(R.id.tvPhotoCount)
        tvRestaurantTitle = rootView.findViewById(R.id.tvRestaurantTitle)
        tvEmptyView = rootView.findViewById(R.id.tvEmptyView)
        btnPrevious = rootView.findViewById(R.id.btnPrevious)
        btnNext = rootView.findViewById(R.id.btnNext)
        back = rootView.findViewById(R.id.back)
    }

    private fun setupDialogWindow() {
        window?.apply {
            // Set dialog size to 80% of screen width and 60% of screen height
            val width = (context.resources.displayMetrics.widthPixels * 0.8).toInt()
            val height = (context.resources.displayMetrics.heightPixels * 0.5).toInt()
            setLayout(width, height)

            // Set transparent background for modern look
            setBackgroundDrawableResource(android.R.color.transparent)
        }

        // Prevent dialog from being canceled by outside touch or back button
        setCancelable(false)
    }


    private fun setupClickListeners() {
        back.setOnClickListener {
            dismiss()
        }
    }

    private fun setupRecyclerViews() {
        recVHotelImageTypes.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )

        // Pass a lambda that finds the position of the clicked item in the full list
        hotelImagesTypeAdapter = HotelImagesTypeAdapter { clickedImage ->
            val position = allUnifiedImages.indexOfFirst { it.imageName == clickedImage.imageName }
            if (position != -1) {
                // Set the flag to true to prevent the onScrolled listener from interfering
                isUserScrolling = true

                // Set the selected position immediately for an instantaneous update
                hotelImagesTypeAdapter.setSelectedPosition(position)

                // Scroll the main image RecyclerView to the correct position
                recVHotelRoomImages.smoothScrollToPosition(position)

                // Also scroll the thumbnail list to the new position
                recVHotelImageTypes.smoothScrollToPosition(position)
            }
        }
        recVHotelImageTypes.adapter = hotelImagesTypeAdapter

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recVHotelRoomImages.layoutManager = layoutManager
        recVHotelRoomImages.isNestedScrollingEnabled = false

        hotelImagesListAdapter = HotelImagesListAdapter(scaleType)
        recVHotelRoomImages.adapter = hotelImagesListAdapter

        recVHotelRoomImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                // Reset the flag when scrolling stops
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isUserScrolling = false
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val firstVisiblePos = layoutManager.findFirstVisibleItemPosition()
                val totalItems = allUnifiedImages.size

                if (firstVisiblePos != RecyclerView.NO_POSITION) {
                    val displayPosition = firstVisiblePos + 1
                    tvPhotoCount.text = "$displayPosition / $totalItems"

                    // Only update the thumbnail adapter if the scroll was not user-initiated by a click
                    if (!isUserScrolling) {
                        hotelImagesTypeAdapter.setSelectedPosition(firstVisiblePos)
                        recVHotelImageTypes.smoothScrollToPosition(firstVisiblePos)
                    }

                    // The logic for navigation buttons remains the same
                    val lastVisiblePos = layoutManager.findLastVisibleItemPosition()
                    btnPrevious.visibility =
                        if (firstVisiblePos == 0) View.GONE else View.VISIBLE
                    btnNext.visibility =
                        if (lastVisiblePos == totalItems - 1) View.GONE else View.VISIBLE
                }
            }
        })

        // No need to set the selected position in next/previous button clicks,
        // as the onScrolled listener will handle it once the animation starts.
        btnNext.setOnClickListener {
            val nextPos = layoutManager.findFirstVisibleItemPosition() + 1
            if (nextPos < hotelImagesListAdapter.differ.currentList.size) {
                recVHotelRoomImages.smoothScrollToPosition(nextPos)
            }
        }

        btnPrevious.setOnClickListener {
            val prevPos = layoutManager.findFirstVisibleItemPosition() - 1
            if (prevPos >= 0) {
                recVHotelRoomImages.smoothScrollToPosition(prevPos)
            }
        }
    }

    private fun internalReloadData() {
        allImages?.let {
            allUnifiedImages = it.mapIndexed { index, imageUrl ->
                UnifiedImage(
                    id = UUID.randomUUID().toString(),
                    imageName = imageUrl,
                )
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            hotelImagesTypeAdapter.submitList(allUnifiedImages, resetSelection = true)

            hotelImagesListAdapter.submitList(allUnifiedImages)

            // Initial UI updates after data is loaded
            tvPhotoCount.text = "1 / ${allUnifiedImages.size}"
            tvEmptyView.visibility = if (allUnifiedImages.isEmpty()) View.VISIBLE else View.GONE
            recVHotelRoomImages.visibility = if (allUnifiedImages.isEmpty()) View.GONE else View.VISIBLE
            tvRestaurantTitle.text = dialogTitle ?: "Dialog"
        }, 100)
    }

    // Optional: Add a show method for convenience
    fun showDialog() {
        show()
    }
}

