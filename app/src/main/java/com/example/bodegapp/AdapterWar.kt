package com.example.bodegapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.json.JSONArray
import java.text.NumberFormat
import java.util.*

class AdapterWar(private val ware: JSONArray) :
    RecyclerView.Adapter<AdapterWar.ViewHolder>() {

    private var expandableList = ArrayList<LinearLayout>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images: ImageCarousel
        var name: TextView
        var period: TextView
        var total: TextView
        var expandable : LinearLayout
        var title : TextView
        var warehouseTotal : TextView
        init {
            images = itemView.findViewById(R.id.imageView2)
            name = itemView.findViewById(R.id.tv_title)
            period = itemView.findViewById(R.id.tv_billing_period)
            total = itemView.findViewById(R.id.tv_fecha)
            expandable = itemView.findViewById(R.id.layout_expandable)
            title = itemView.findViewById(R.id.tv_billing_warehouse_title)
            warehouseTotal = itemView.findViewById(R.id.tv_billing_warehouse_total)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterWar.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_my_warehouse, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterWar.ViewHolder, position: Int) {
        //holder.period.text = "${billings.getJSONObject(position).getJSONObject("pivot").getString("start_date")} - ${billings.getJSONObject(position).getJSONObject("pivot").getString("end_date")}"
        //holder.total.text = NumberFormat.getCurrencyInstance(Locale.US).format(billings.getJSONObject(position).getJSONObject("pivot").getDouble("total"))

        println(ware.getJSONObject(position).getString("title"))
        holder.name.text = ware.getJSONObject(position).getString("title")
        holder.title.text = ware.getJSONObject(position).getString("title")
        holder.period.text = ware.getJSONObject(position).getString("price")

        val list = mutableListOf<CarouselItem>()

        (0 until ware.getJSONObject(position).getJSONArray("images").length()).forEach {
            list.add(
                CarouselItem(
                    imageUrl = ware.getJSONObject(position).getJSONArray("images").getJSONObject(it).getString("src")
                )
            )
        }

        holder.images.setData(list)

        println(ware.getJSONObject(position).getInt("available"))

        if(ware.getJSONObject(position).getInt("available") == 1){
            holder.warehouseTotal.text = "Se encuentra disponible para rentar"
            holder.total.text = "DISPONIBLE"
        }else{
            holder.warehouseTotal.text = "No se encuentra disponible para rentar"
            holder.total.text = "NO DISPONIBLE"
        }
        //holder.available.text = ware.getJSONObject(position).getString("title")
        //holder.warehouseTotal.text = billings.getJSONObject(position).getString("description")
        expandableList.add(position, holder.expandable)

        holder.itemView.setOnClickListener {
            for (i in 0..expandableList.size - 1 ){
                expandableList[i].visibility = View.GONE
                //notifyItemChanged(i)
            }
            holder.expandable.visibility = View.VISIBLE
            //notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int {
        return ware.length()
    }
}