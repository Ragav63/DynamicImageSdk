package com.ragav63.dynamic_image_sdk

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

class HotelImagesListAdapter(private val scaleType: ImageView.ScaleType? = null) :
    RecyclerView.Adapter<HotelImagesListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: PhotoView = itemView.findViewById(R.id.img)
    }

    private val diffUtil = object : DiffUtil.ItemCallback<UnifiedImage>() {
        override fun areItemsTheSame(oldItem: UnifiedImage, newItem: UnifiedImage): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UnifiedImage, newItem: UnifiedImage): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotel_images_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = differ.currentList[position]

        // Use the provided scaleType directly, fallback to CENTER_CROP if null
        holder.img.setScaleType(scaleType ?: null)


        // Load image with Glide
        Glide.with(holder.itemView.context)
            .load(image.imageName)
            .placeholder(R.drawable.hoteldetailplaceholder)
            .error(R.drawable.hoteldetailplaceholder)
            .into(holder.img)
    }


    fun submitList(list: List<UnifiedImage>) {
        differ.submitList(list)
    }
}