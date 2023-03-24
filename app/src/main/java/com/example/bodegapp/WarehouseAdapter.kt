package com.example.bodegapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.json.JSONArray
import java.text.NumberFormat
import java.util.*

class WarehouseAdapter(private val warehouses: JSONArray, private val ctx: Context) :
    RecyclerView.Adapter<WarehouseAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var description: TextView
        var price: TextView
        var images: ImageCarousel
        var services: RecyclerView

        init {
            images = itemView.findViewById(R.id.carousel_warehouse)
            title = itemView.findViewById(R.id.tv_title)
            description = itemView.findViewById(R.id.tv_billing_period)
            price = itemView.findViewById(R.id.tv_fecha)
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
        /*Picasso.get().load(
            warehouses.getJSONObject(position).getJSONArray("images").getJSONObject(0)
                .getString("src").toString()
        ).into(holder.image)*/

        val list = mutableListOf<CarouselItem>()

        (0 until warehouses.getJSONObject(position).getJSONArray("images").length()).forEach {
            list.add(
                CarouselItem(
                    imageUrl = warehouses.getJSONObject(position).getJSONArray("images").getJSONObject(it).getString("src")
                )
            )
        }

        holder.images.setData(list)

        holder.title.text = warehouses.getJSONObject(position).getString("title")
        holder.description.text = warehouses.getJSONObject(position).getString("description")
        holder.price.text = NumberFormat.getCurrencyInstance(Locale.US)
            .format(warehouses.getJSONObject(position).getDouble("price"))

        holder.services.adapter =
            ServiceAdapter(warehouses.getJSONObject(position).getJSONArray("services"))
        holder.services.layoutManager =
            LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)

        holder.itemView.setOnClickListener {
            /*println(warehouses.getJSONObject(position).getString("title"))
            ctx.startActivity(Intent(ctx, WarehouseDetail::class.java))*/
            val intent = Intent(ctx, WarehouseDetail::class.java)
            intent.putExtra("id", warehouses.getJSONObject(position).getString("id"))
            ctx.startActivity(intent)
        }
    }


}