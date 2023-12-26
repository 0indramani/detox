package com.detox;

import android.view.LayoutInflater
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import android.widget.*;
import android.content.Context

public class AddAppsAdapter : RecyclerView.Adapter<AddAppsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.apps, parent, false)
        return ViewHolder(v!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    
    }

    override fun getItemCount(): Int {
        return 63
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
       
    }
    
    
}