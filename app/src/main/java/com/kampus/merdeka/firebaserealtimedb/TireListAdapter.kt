package com.kampus.merdeka.firebaserealtimedb

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TireListAdapter(private val tireList: MutableList<TireModel>,
                      private val onItemClickListener: (TireModel) -> Unit
): RecyclerView.Adapter<TireListAdapter.TireAdapterHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TireAdapterHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tire, parent, false)
        return TireAdapterHolder(view)
    }

    override fun getItemCount(): Int {
        return tireList.count()
    }

    override fun onBindViewHolder(holder: TireAdapterHolder, position: Int) {
        val tire = tireList[position]
        holder.bind(tire)
        holder.itemView.setOnClickListener {
            onItemClickListener(tire)
        }
    }

    fun updateData(newTireList: List<TireModel>) {
        tireList.clear()
        tireList.addAll(newTireList)
        notifyDataSetChanged()
    }

    class TireAdapterHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descTextView: TextView = itemView.findViewById(R.id.descTextView)

        fun bind(tire: TireModel) {
            nameTextView.text = tire.name
            descTextView.text = tire.description
        }
    }
}