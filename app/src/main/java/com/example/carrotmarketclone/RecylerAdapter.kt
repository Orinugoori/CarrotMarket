package com.example.carrotmarketclone

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView


class RecylerAdapter(val mItems : MutableList<MyItem>) : RecyclerView.Adapter<RecylerAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecylerAdapter.Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.holder,parent,false)
        return Holder(itemView)
    }

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }

    var itemClick : ItemClick? = null

    override fun onBindViewHolder(holder: RecylerAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it,position)
        }

        holder.photo.setImageResource(mItems[position].photo)
        holder.name.text = mItems[position].name
        holder.address.text = mItems[position].address
        holder.price.text = priceRegex(mItems[position].price.toString())
        holder.chat.text = mItems[position].chat.toString()
        holder.likes.text = mItems[position].likes.toString()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
       return mItems.size
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val photo : ImageView = view.findViewById(R.id.iv_photo)
        val name : TextView = view.findViewById(R.id.tv_name)
        val address : TextView = view.findViewById(R.id.tv_address)
        val price : TextView = view.findViewById(R.id.tv_price)
        val chat : TextView = view.findViewById(R.id.tv_chat)
        val likes : TextView = view.findViewById(R.id.tv_heart)

    }
}
fun priceRegex(priceString: String): String {
    var finalPrice = ""
    if (priceString == "0") {
        finalPrice = "무료 나눔"
    } else {
        val regex = Regex("(\\d)(?=(\\d{3})+(?!\\d))")
        finalPrice =  priceString.replace(regex, "$1,") + "원"
    }

    return finalPrice

}

