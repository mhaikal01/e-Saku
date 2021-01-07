package com.wibu.appe_saku.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wibu.appe_saku.*
import com.wibu.appe_saku.model.Santri

class SantriRecyclerAdapter(santriList: List<Santri>, internal var context: Context) : RecyclerView.Adapter<SantriRecyclerAdapter.TaskViewHolder>() {

    internal var santriList: List<Santri> = ArrayList()
    init {
        this.santriList = santriList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.santri_list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val santri = santriList[position]
        holder.name.text = santri.name
        holder.money.text = "Rp. " + santri.money

        holder.itemView.setOnClickListener {
            val i = Intent(context, SantriActivity::class.java)
            i.putExtra("Id", santri.id)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return santriList.size
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tvName) as TextView
        var money: TextView = view.findViewById(R.id.tvMoney) as TextView
    }

}