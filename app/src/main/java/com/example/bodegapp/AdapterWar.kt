package com.example.bodegapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.json.JSONArray
import java.text.NumberFormat
import java.util.*

class AdapterWar(private val ware: JSONArray) :
    RecyclerView.Adapter<AdapterWar.ViewHolder>() {

    private var expandableList = ArrayList<LinearLayout>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var image: ImageCarousel
        var fecha: TextView

        var title : TextView

        lateinit var available : MaterialButton
        init {
            image = itemView.findViewById(R.id.carousel_warehouse)
            fecha = itemView.findViewById(R.id.tv_fecha)

            available = itemView.findViewById(R.id.tag_dispon)
            title = itemView.findViewById(R.id.tv_title)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterWar.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_my_warehouse, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterWar.ViewHolder, position: Int) {
        //holder.period.text = "${billings.getJSONObject(position).getJSONObject("pivot").getString("start_date")} - ${billings.getJSONObject(position).getJSONObject("pivot").getString("end_date")}"
        //holder.total.text = NumberFormat.getCurrencyInstance(Locale.US).format(billings.getJSONObject(position).getJSONObject("pivot").getDouble("total"))
        println("888888888888888888888888888888888888888888    PUTOOOOOOOOOOOOOOOOOOOOOO")
        println(ware.getJSONObject(position).getString("title"))
        holder.title.text = ware.getJSONObject(position).getString("title")
        holder.fecha.text = ware.getJSONObject(position).getString("title")
        holder.available.text = ware.getJSONObject(position).getString("title")
        //holder.warehouseTotal.text = billings.getJSONObject(position).getString("description")
        //expandableList.add(position, holder.expandable)

       /* holder.itemView.setOnClickListener {
            for (i in 0..expandableList.size - 1 ){
                expandableList[i].visibility = View.GONE
                //notifyItemChanged(i)
            }
            holder.expandable.visibility = View.VISIBLE
            //notifyItemChanged(position)
        }*/

    }

    override fun getItemCount(): Int {
        return ware.length()
    }
}