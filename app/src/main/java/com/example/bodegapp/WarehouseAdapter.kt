package com.example.bodegapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.squareup.picasso.Picasso
import org.json.JSONArray
import java.text.NumberFormat
import java.util.*

class WarehouseAdapter(private val warehouses: JSONArray, private val ctx: Context) :
    RecyclerView.Adapter<WarehouseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title: TextView
        var description: TextView
        var price: TextView
        var image: ImageView
        var services: RecyclerView

        init {
            image = itemView.findViewById(R.id.imageView0)
            title = itemView.findViewById(R.id.tv_title)
            description = itemView.findViewById(R.id.tv_description)
            price = itemView.findViewById(R.id.tv_price)
            services = itemView.findViewById(R.id.rv_services)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.warehouse_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return warehouses.length()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(
            warehouses.getJSONObject(position).getJSONArray("images").getJSONObject(0)
                .getString("src").toString()
        ).into(holder.image)
        holder.title.text = warehouses.getJSONObject(position).getString("title")
        holder.description.text = warehouses.getJSONObject(position).getString("description")
        holder.price.text = NumberFormat.getCurrencyInstance(Locale.US).format(warehouses.getJSONObject(position).getDouble("price"))

        holder.services.adapter = ServiceAdapter(warehouses.getJSONObject(position).getJSONArray("services"))
        holder.services.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
    }


}