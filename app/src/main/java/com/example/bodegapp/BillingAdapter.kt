package com.example.bodegapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class BillingAdapter(private val billings: JSONArray) :
    RecyclerView.Adapter<BillingAdapter.ViewHolder>() {

    private var expandableList = ArrayList<LinearLayout>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var period: TextView
        var total: TextView
        var expandable : LinearLayout
        var title : TextView
        var warehouseTotal : TextView
        init {
            period = itemView.findViewById(R.id.tv_billing_period)
            total = itemView.findViewById(R.id.tv_billing_total)
            expandable = itemView.findViewById(R.id.layout_expandable)
            title = itemView.findViewById(R.id.tv_billing_warehouse_title)
            warehouseTotal = itemView.findViewById(R.id.tv_billing_warehouse_total)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.billing_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillingAdapter.ViewHolder, position: Int) {
        holder.period.text = "${billings.getJSONObject(position).getJSONObject("pivot").getString("start_date")} - ${billings.getJSONObject(position).getJSONObject("pivot").getString("end_date")}"
        holder.total.text = NumberFormat.getCurrencyInstance(Locale.US).format(billings.getJSONObject(position).getJSONObject("pivot").getDouble("total"))
        holder.title.text = billings.getJSONObject(position).getString("title")
        holder.warehouseTotal.text = billings.getJSONObject(position).getString("description")
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
        return billings.length()
    }
}