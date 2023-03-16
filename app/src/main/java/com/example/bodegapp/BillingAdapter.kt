package com.example.bodegapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.text.NumberFormat
import java.util.*

class BillingAdapter(private val billings: JSONArray) :
    RecyclerView.Adapter<BillingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var period: TextView
        var total: TextView
        init {
            period = itemView.findViewById(R.id.tv_billing_period)
            total = itemView.findViewById(R.id.tv_billing_total)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.billing_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillingAdapter.ViewHolder, position: Int) {
        holder.period.text = "${billings.getJSONObject(position).getJSONObject("pivot").getString("start_date")} - ${billings.getJSONObject(position).getJSONObject("pivot").getString("end_date")}"
        holder.total.text = NumberFormat.getCurrencyInstance(Locale.US).format(billings.getJSONObject(position).getJSONObject("pivot").getDouble("total"))
    }

    override fun getItemCount(): Int {
        return billings.length()
    }
}