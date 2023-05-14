package com.example.myapplication.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.models.OrganizationModel
import com.example.myapplication.R

//bill adpoter clas
class OrganizationAdaptor(private val OrganizationList: ArrayList<OrganizationModel>):
    RecyclerView.Adapter<OrganizationAdaptor.ViewHolder>(){

    private lateinit var mlistner : onItemClickListner

    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner){
        mlistner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.organization_data, parent,false)
        return ViewHolder(itemView,mlistner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentOrg = OrganizationList[position]
        holder.tvorganizationName.text = currentOrg.orgName
    }

    override fun getItemCount(): Int {
        return OrganizationList.size
    }
    class ViewHolder(itemView:View,clickListner:onItemClickListner) : RecyclerView.ViewHolder(itemView) {

        val tvorganizationName : TextView = itemView.findViewById(R.id.tvOrganizationName)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }

}
