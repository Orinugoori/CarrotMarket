package com.example.carrotmarketclone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarketclone.databinding.HolderBinding


class RecyclerAdapter(
    val mItems: MutableList<MyItem>,
    val itemClick: (MyItem, Int) -> Unit?,
    val itemLongClick: (MyItem, Int) -> Unit?
) : ListAdapter<MyItem, RecyclerAdapter.Holder>(object : DiffUtil.ItemCallback<MyItem>() {


    override fun areItemsTheSame(oldItem: MyItem, newItem: MyItem): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: MyItem, newItem: MyItem): Boolean {
        return oldItem == newItem
    }


}) {

    fun removeItem(position: Int) {
        if (position in 0 until mItems.size) {
            mItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.Holder {
        val binding = HolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerAdapter.Holder, position: Int) {
        holder.bind(mItems[position])
    }


    override fun getItemCount(): Int {
        return mItems.size
    }


    inner class Holder(private val binding: HolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyItem) {
            binding.apply {
                ivPhoto.setImageResource(item.photo)
                tvName.text = item.name
                tvAddress.text = item.address
                tvPrice.text = priceRegex(item.price.toString())
                tvChat.text = item.chat.toString()
                tvHeart.text = item.likes.toString()
                if(item.interest){
                    icHeart.setImageResource(R.drawable.ic_heart_colored)
                }else{
                    icHeart.setImageResource(R.drawable.ic_heart)
                }


                layoutHolder.setOnClickListener {
                    itemClick(item,adapterPosition)
                }

                layoutHolder.setOnLongClickListener {
                    itemLongClick(item,adapterPosition)
                    true
                }

            }
        }

    }

}

fun priceRegex(priceString: String): String {
    var finalPrice = ""
    if (priceString == "0") {
        finalPrice = "무료 나눔"
    } else {
        val regex = Regex("(\\d)(?=(\\d{3})+(?!\\d))")
        finalPrice = priceString.replace(regex, "$1,") + "원"
    }

    return finalPrice

}






