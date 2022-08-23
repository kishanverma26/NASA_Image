package com.nasaimage.ui

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.nasaimage.picturesapp.R
import com.nasaimage.picturesapp.databinding.RowImageGridBinding
import com.nasaimage.models.ImageModel
import com.nasaimage.utils.toDisplayFormat

class RVImageListingViewHolder(val binding: RowImageGridBinding) :
    RecyclerView.ViewHolder(binding.root)

class RVImageListingAdapter(
    private val images: List<ImageModel>,
    private val onTap: (Int) -> Unit
) :
    RecyclerView.Adapter<RVImageListingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVImageListingViewHolder {
        val binding =
            RowImageGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RVImageListingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RVImageListingViewHolder, position: Int) {
        Log.e("images","${images[position].url}")

        Glide.with(holder.itemView).load(images[position].url.replace(" ","%20"))
            .listener( object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false;
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.binding.pbar.visibility = View.GONE
                    return false;
                }

            }).placeholder(R.drawable.loading)
            .into(holder.binding.image)
        holder.binding.tvTitle.text = images[position].title
        holder.binding.tvDate.text = images[position].date.toDisplayFormat()
        holder.binding.root.setOnClickListener {
            onTap.invoke(position)
        }
    }

    override fun getItemCount(): Int = images.size
}