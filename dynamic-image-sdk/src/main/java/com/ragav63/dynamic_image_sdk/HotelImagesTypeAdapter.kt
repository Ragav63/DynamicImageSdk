package com.ragav63.dynamic_image_sdk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlin.collections.get

class HotelImagesTypeAdapter(
    private val event: (UnifiedImage) -> Unit
) : RecyclerView.Adapter<HotelImagesTypeAdapter.ViewHolder>() {

    private var selectedPosition = 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img)
    }

    private val diffUtil = object : DiffUtil.ItemCallback<UnifiedImage>() {
        override fun areItemsTheSame(oldItem: UnifiedImage, newItem: UnifiedImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UnifiedImage, newItem: UnifiedImage): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotel_images_types_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        val context = holder.itemView.context

        // Highlight selection
        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.black_stroke_white_bg)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.white_bg)
        }

        // Load image
        Glide.with(context)
            .load(item.imageName)
            .placeholder(R.drawable.hoteldetailplaceholder)
            .error(R.drawable.hoteldetailplaceholder)
            .into(holder.img)

        // Click listener
        holder.itemView.setOnClickListener {
            val oldPos = selectedPosition
            selectedPosition = holder.adapterPosition
            if (oldPos != RecyclerView.NO_POSITION) notifyItemChanged(oldPos)
            if (selectedPosition != RecyclerView.NO_POSITION) notifyItemChanged(selectedPosition)
            event.invoke(item)
        }
    }

    fun submitList(list: List<UnifiedImage>, resetSelection: Boolean = false) {
        if (resetSelection) selectedPosition = 0
        differ.submitList(list)
    }

    fun setSelectedPosition(position: Int) {
        val oldPos = selectedPosition
        selectedPosition = position
        if (oldPos != RecyclerView.NO_POSITION) notifyItemChanged(oldPos)
        if (selectedPosition != RecyclerView.NO_POSITION) notifyItemChanged(selectedPosition)
    }

    fun getSelectedPosition(): Int = selectedPosition
}
