package com.example.newsapplication.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.Categorymodel.CategoryModel
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ItemCategoryBinding
import com.example.newsapplication.databinding.ItemVpBinding
import com.example.newsapplication.modelSplash.SplashData


class CategoryRvAdapter(var list: List<CategoryModel>,var listener:onItemClick):RecyclerView.Adapter<CategoryRvAdapter.Vh>() {
    var strings=ArrayList<Int>()
    inner class Vh(var itemRvBinding: ItemCategoryBinding):
             RecyclerView.ViewHolder(itemRvBinding.root){

         fun onBind(category: CategoryModel,position: Int) {

           strings= arrayListOf(R.string.business,R.string.entertainment,R.string.general,R.string.health,
               R.string.science,R.string.sport,R.string.technology)
             itemRvBinding.image.setImageResource(category.image)
             itemRvBinding.tv.text=itemRvBinding.root.context.resources.getString(strings[position])
             if(category.saved){
                 itemRvBinding.root.setBackgroundResource(R.drawable.back_cat)
                 itemRvBinding.tv.setTextColor(Color.parseColor("#ffffff"))

             } else {
                 itemRvBinding.root.setBackgroundResource(R.drawable.back_category)
                 itemRvBinding.tv.setTextColor(Color.parseColor("#000000"))

             }

             itemRvBinding.root.setOnClickListener {
                 var f=category.saved
                 if(!f){
                     listener.onAdd(category)
                     itemRvBinding.root.setBackgroundResource(R.drawable.back_cat)
                     itemRvBinding.tv.setTextColor(Color.parseColor("#ffffff"))

                 } else{
                     listener.onRemove(category)
                     itemRvBinding.root.setBackgroundResource(R.drawable.back_category)
                     itemRvBinding.tv.setTextColor(Color.parseColor("#000000"))

                     f=false
                 }
             }


         }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
////        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.vpanim)
//
//        holder.itemView.animation = animation
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface onItemClick{
        fun onAdd( categoryModel: CategoryModel)
        fun onRemove( categoryModel: CategoryModel)

    }
}