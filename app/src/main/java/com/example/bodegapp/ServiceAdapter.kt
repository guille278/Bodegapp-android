package com.example.bodegapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class ServiceAdapter(private val services : JSONArray) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : Button
        init {
            name = itemView.findViewById(R.id.tag_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.service_tag, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return services.length()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = services.getJSONObject(position).getString("name")
    }
}