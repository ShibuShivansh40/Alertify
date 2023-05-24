package com.example.livestock_alert_1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class ItemAdapter(private var items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.imageId)
        @SuppressLint("ResourceType")
        fun bind(item: Item) {
//            itemView.findViewById<TextView>(R.id.id_view).text = item.id
            itemView.findViewById<TextView>(R.id.location_view).text = item.location
            itemView.findViewById<TextView>(R.id.userType_view).text = item.userType
//            itemView.findViewById<TextView>(R.id.imageUrl_view).text = item.imageUrl
//            Glide.with().load(ItemViewHolder.imageUrl).into(holder.newsImage)
//            Glide.with(itemView).load(item.imageUrl).placeholder(R.id.image_view).into(itemView as LinearLayout)
            Picasso.get().load(item.imageUrl).into(imageView)
            itemView.findViewById<TextView>(R.id.date_view).text = item.date.toString()

        }
    }
}
