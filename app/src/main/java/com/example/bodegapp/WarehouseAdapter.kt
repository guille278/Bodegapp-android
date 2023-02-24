package com.example.bodegapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WarehouseAdapter : RecyclerView.Adapter<WarehouseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.warehouse_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}