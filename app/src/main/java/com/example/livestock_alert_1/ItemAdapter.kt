package com.example.livestock_alert_1

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

var PREVIOUS_DATE: String = ""
private var previousResponse: List<Item>? = null

class ItemAdapter(private val context: Context, private var items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

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


//    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<Item>) {
        items = newItems
        // if previousResponse == null -> provide it new item
        // if previousResponse == newItems -> pass
        // else showNotification() & provide it new item

        if(previousResponse != newItems){
            showNotification(context)
            previousResponse = newItems
        }
        notifyDataSetChanged()
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val webAPIUrl: String = "${URL_Captured}images/"
//        private val webAPIUrl: String = "http://4945-43-230-198-50.ngrok-free.app/images/"
        private val imageView: ImageView = itemView.findViewById(R.id.imageId)

        @SuppressLint("ResourceType")
        fun bind(item: Item) {
            itemView.findViewById<TextView>(R.id.location_view).text = item.location
            itemView.findViewById<TextView>(R.id.userType_view).text = item.userType
            itemView.findViewById<TextView>(R.id.notification_view).text = item.notification

            Picasso.get().load(webAPIUrl+item.imageUrl).into(imageView)
            Log.d("ImageUrl" , webAPIUrl+item.imageUrl)
            itemView.findViewById<TextView>(R.id.date_view).text = item.date.toString()
        }
    }
}
