package com.example.bodegapp

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView


class PlanAdapter(private val plans: Array<String>) :
    RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    private val checkableCardViewList = ArrayList<MaterialCardView>()
    private var planId : String = ""

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val card: MaterialCardView

        init {
            title = itemView.findViewById(R.id.tv_plan_title)
            card = itemView.findViewById(R.id.card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plan_cards, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanAdapter.ViewHolder, position: Int) {
        holder.title.setText(plans[position])
        checkableCardViewList.add(position, holder.card)
        holder.card.setOnClickListener {
            for (checkableCardView in checkableCardViewList) {
                checkableCardView.setChecked(false)
            }
            checkableCardViewList.get(position).setChecked(true)
            setPlanId(position.toString())
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return plans.size
    }

    private fun setPlanId(id : String){
        planId = id
    }

    fun getPlanId(): String{
        return planId
    }
}