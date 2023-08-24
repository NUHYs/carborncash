package com.example.carborncash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter_App_Rank(private val emplist: ArrayList<Employee_App_Rank>) : RecyclerView.Adapter<Adapter_App_Rank.MyViewHolder>() {

    // This method creates a new ViewHolder object for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for each item and return a new ViewHolder object
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.items_list_app_rank, parent, false)
        return MyViewHolder(itemView)
    }

    // This method returns the total
    // number of items in the data set
    override fun getItemCount(): Int {
        return emplist.size
    }

    // This method binds the data to the ViewHolder object
    // for each item in the RecyclerView
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEmp = emplist[position]
        holder.usage.text = currentEmp.usage
        holder.app_name.text = currentEmp.app_name
        holder.app_image.setImageResource(currentEmp.app_image)
    }

    // This class defines the ViewHolder object for each item in the RecyclerView
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val app_image: ImageView = itemView.findViewById(R.id.app_image)
        val app_name: TextView = itemView.findViewById(R.id.app_name)
        val usage: TextView = itemView.findViewById(R.id.usage)
    }
}
