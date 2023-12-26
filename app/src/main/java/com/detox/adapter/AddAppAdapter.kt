package com.detox.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.*
import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.engine.DiskCacheStrategy
import android.content.pm.PackageManager
import com.bumptech.glide.Glide

import com.detox.dataclass.*
import com.detox.R

public class AddAppAdapter(var ctx: Context, var data: MutableList<PacData>) :
    RecyclerView.Adapter<AddAppAdapter.ViewHolder>() {
    
    var selectedApps: MutableList<String> = BaseObj.allData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.apps, parent, false)
        return ViewHolder(v!!)
    }
    

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.appName.text = ctx.packageManager.getApplicationLabel(
            ctx.packageManager.getApplicationInfo(data[position].name, PackageManager.GET_META_DATA)
        )
        Glide.with(ctx)
            .load(ctx.getPackageManager().getApplicationIcon(data[position].name))
            .dontAnimate()
            .override(70, 70)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.icon)

          holder.isSelected.isChecked = selectedApps.contains(data[position].name)
            
        holder.isSelected.setOnClickListener {
            if(holder.isSelected.isChecked) {
                selectedApps.add(data[position].name)
            } else {
                selectedApps.remove(data[position].name)
            }
            notifyItemChanged(position)
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rootView: RelativeLayout = itemView.findViewById(R.id.root)
        var icon: ImageView = itemView.findViewById(R.id.icon)
        var appName: TextView = itemView.findViewById(R.id.appName)
        var isSelected: CheckBox = itemView.findViewById(R.id.isSelected)
    }
}