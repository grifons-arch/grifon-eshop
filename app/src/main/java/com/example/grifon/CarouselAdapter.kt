package com.example.grifon

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView

class CarouselAdapter(
    private val images: List<@DrawableRes Int>,
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carousel_image, parent, false) as ImageView
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    class CarouselViewHolder(private val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {
        fun bind(@DrawableRes imageRes: Int) {
            imageView.setImageResource(imageRes)
        }
    }
}
