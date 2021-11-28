package com.example.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ItemBookmarkBinding
import com.example.newsapplication.databinding.ItemMainrvBinding
import com.example.newsapplication.databinding.ItemVpBinding

import com.example.newsapplication.models.ArticlesItem
import com.squareup.picasso.Picasso


class RecomAdapter(var listener:RecomAdapter.onItemClick):ListAdapter<ArticlesItem,RecomAdapter.Vh>(MyDiffUtill()) {
    inner class Vh(var itemRvBinding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(articlesItem: ArticlesItem) {
            Picasso.get().load(articlesItem.urlToImage).placeholder(R.drawable.placeholder).into(itemRvBinding.image)
            itemRvBinding.title.text=articlesItem.title
            itemRvBinding.root.setOnClickListener {
                listener.onItemClick(articlesItem)
            }

        }

    }
    class MyDiffUtill():DiffUtil.ItemCallback<ArticlesItem>(){
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
           return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(articlesItem = getItem(position))
    }

    interface onItemClick{
        fun onItemClick(articlesItem: ArticlesItem)
    }

}