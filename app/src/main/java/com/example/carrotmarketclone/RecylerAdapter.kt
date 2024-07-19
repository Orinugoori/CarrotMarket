package com.example.carrotmarketclone

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarketclone.databinding.HolderBinding
import com.example.carrotmarketclone.databinding.TitleHolderBinding


class RecyclerAdapter(
    val mItems: MutableList<MyItem>,
    val itemClick: (MyItem, Int) -> Unit?,
    val itemLongClick: (MyItem, Int) -> Unit?
) : ListAdapter<MyItem, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<MyItem>() {

    override fun areItemsTheSame(oldItem: MyItem, newItem: MyItem): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: MyItem, newItem: MyItem): Boolean {
        return oldItem == newItem
    }

    }) {

        companion object{
            const val  VIEW_TYPE_TITLE = 1
            const val  VIEW_TYPE_REVIEW = 2
        }

        fun removeItem( position : Int) {
            if (position in mItems.indices) {
                mItems.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        fun isHeader(position: Int): Boolean {
        return getItemViewType(position) == RecyclerAdapter.VIEW_TYPE_TITLE
    }

    fun getHeaderLayoutView(list: RecyclerView, position: Int) : View?{
        val item = mItems[position]
        if(item is MyItem.Date){
            val headerView = LayoutInflater.from(list.context).inflate(R.layout.title_holder, list, false)
            val headerBinding = TitleHolderBinding.bind(headerView)
            headerBinding.tvTitleDate.text = item.date
            return headerView
        }
        return null
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TITLE -> {
                val binding = TitleHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                TitleHolder(binding)
            }
            VIEW_TYPE_REVIEW -> {
                val binding =
                    HolderBinding.inflate(LayoutInflater.from(parent.context), parent,false)
                ReViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }


    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mItems[position]
        when (item){
            is MyItem.Date -> {
                val titleHolder = holder as TitleHolder
                titleHolder.bind(item)
            }
            is MyItem.Data -> {
                val reviewHolder = holder as ReViewHolder
                reviewHolder.bind(item)
            }
        }
    }


    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (mItems[position]) {
            is MyItem.Date -> VIEW_TYPE_TITLE
            is MyItem.Data -> VIEW_TYPE_REVIEW
        }
    }

    inner class TitleHolder(private val binding: TitleHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyItem.Date) {
            binding.apply {
                tvTitleDate.text = item.date
            }

        }
    }

    inner class ReViewHolder(private val binding: HolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyItem.Data) {
            binding.apply {
                ivPhoto.setImageResource(item.photo)
                tvName.text = item.name
                tvAddress.text = item.address
                tvPrice.text = priceRegex(item.price.toString())
                tvChat.text = item.chat.toString()
                tvHeart.text = item.likes.toString()
                if (item.interest) {
                    icHeart.setImageResource(R.drawable.ic_heart_colored)
                } else {
                    icHeart.setImageResource(R.drawable.ic_heart)
                }

                layoutHolder.setOnClickListener {
                    itemClick(item, adapterPosition)
                }

                layoutHolder.setOnLongClickListener {
                    itemLongClick(item, adapterPosition)
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






