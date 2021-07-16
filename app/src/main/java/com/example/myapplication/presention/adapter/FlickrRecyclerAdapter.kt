package com.example.myapplication.presention.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.domain.entity.Flickr
import com.example.myapplication.presention.adapter.FlickrRecyclerAdapter.FlickrViewHolder
import kotlinx.android.synthetic.main.flickr_item.view.*

class FlickrRecyclerAdapter : RecyclerView.Adapter<FlickrViewHolder>() {
    var flickrList = ArrayList<Flickr>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        return FlickrViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.flickr_item,
                parent, false))
    }

    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        holder.onBind(flickrList[position])
    }

    override fun getItemCount(): Int {
        return flickrList.size
    }

    fun setItems(flickrList: ArrayList<Flickr>) {
        this.flickrList = flickrList;
        notifyDataSetChanged()
    }

    fun addItems(flickrList: ArrayList<Flickr>) {
        this.flickrList.addAll(flickrList);
        notifyDataSetChanged()
    }

    class FlickrViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(flickr: Flickr) {
            val request = RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)

            itemView.item_title.text = flickr.title
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(request)
                    .load("https://farm${flickr.farm}.staticflickr.com/${flickr.server}/${flickr.id}_${flickr.serect}_m.jpg")
                    .into(itemView.item_image)
        }
    }
}