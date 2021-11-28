package com.example.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.databinding.ItemVpBinding
import com.example.newsapplication.modelSplash.SplashData


class SplashVpAdapter(var list: List<SplashData>):RecyclerView.Adapter<SplashVpAdapter.Vh>() {
     inner class Vh(var itemRvBinding: ItemVpBinding):
             RecyclerView.ViewHolder(itemRvBinding.root){
         fun onBind(splashData: SplashData) {
             itemRvBinding.root.setImageResource(splashData.image)


         }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemVpBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
////        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.vpanim)
//
//        holder.itemView.animation = animation
    }

    override fun getItemCount(): Int {
        return list.size
    }
}