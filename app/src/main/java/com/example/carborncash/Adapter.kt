package com.example.carborncash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carborncash.fragment.MainFragment
import com.example.carborncash.fragment.pointStoreFragment



class Adapter(private val emplist: ArrayList<Employee>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.items_list, parent, false)



        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return emplist.size
    }

    interface OnItemClickListener {
        fun onItemClick(employee: Employee)
    }

    private var onItemClickListener: OnItemClickListener? = null

    // Set click listener for items
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEmp = emplist[position]

        holder.name.text = currentEmp.name

        holder.star.text = currentEmp.star
        holder.image.setImageResource(currentEmp.image)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(emplist[position])
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.item_image)
        val name: TextView = itemView.findViewById(R.id.item_name)
        val star: TextView = itemView.findViewById(R.id.star)
    }

}

