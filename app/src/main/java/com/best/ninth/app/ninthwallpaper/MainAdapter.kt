package com.best.ninth.app.ninthwallpaper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MainAdapter(private val context: Context, private var dataList: List<Int>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgItem: ImageView = itemView.findViewById(R.id.img_item_main)
        val view_1: View = itemView.findViewById(R.id.view_1)
        val view_2: View = itemView.findViewById(R.id.view_2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getSpanSize(holder,position)
        Glide.with(context)
            .load(NinthUtils.allList[position])
            .thumbnail(0.12f)
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
            .into(holder.imgItem)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun getSpanSize(holder: ViewHolder, position: Int) {
        when (position % 3) {
            1 -> {
                holder.view_1.visibility = View.GONE
            }
            2 -> {
                holder.view_1.visibility = View.GONE
                holder.view_2.visibility = View.GONE
            }
        }
    }
}